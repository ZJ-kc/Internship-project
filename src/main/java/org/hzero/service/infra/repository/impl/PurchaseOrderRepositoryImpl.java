package org.hzero.service.infra.repository.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.hzero.service.api.dto.PurchaseInfoDTO;
import org.hzero.service.api.dto.PurchaseOrderDTO;
import org.hzero.service.domain.entity.*;
import org.hzero.service.domain.repository.*;
import org.hzero.service.infra.mapper.PurchaseOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  资源库实现
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
@Component
public class PurchaseOrderRepositoryImpl extends BaseRepositoryImpl<PurchaseOrder> implements PurchaseOrderRepository {

    private final PurchaseOrderMapper purchaseOrderMapper;
    private final PurchaseInfoRepository purchaseInfoRepository;
    private final MaterialRepository materialRepository;
    private final PurchaseRepository purchaseRepository;
    private final SupplierRepository supplierRepository;

    @Autowired
    public PurchaseOrderRepositoryImpl(PurchaseOrderMapper purchaseOrderMapper,
                                       PurchaseInfoRepository purchaseInfoRepository, MaterialRepository materialRepository, PurchaseRepository purchaseRepository, SupplierRepository supplierRepository) {
        this.purchaseOrderMapper = purchaseOrderMapper;
        this.purchaseInfoRepository = purchaseInfoRepository;
        this.materialRepository = materialRepository;
        this.purchaseRepository = purchaseRepository;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List<PurchaseOrder> getPurchaseOrderPage(Long tenantId, PurchaseOrder purchaseOrder, LocalDate startDate,
                                                    LocalDate endDate) {

        List<PurchaseOrder> purchaseOrderPage = this.purchaseOrderMapper.getPurchaseOrderPage(tenantId, purchaseOrder, startDate, endDate);
        for(PurchaseOrder order: purchaseOrderPage) {
            Supplier supplier = supplierRepository.selectByPrimaryKey(order.getSupplierId());
            Purchase purchase = purchaseRepository.selectByPrimaryKey(order.getPurchaseId());
            order.setPurchaseName(purchase.getPurchaseName());
            order.setSupplierName(supplier.getSupplierName());
        }
        return purchaseOrderPage;
    }

    @Override
    public void addPurchaseOrder(Long tenantId, PurchaseOrder purchaseOrder) {
        if(null != purchaseOrder.getPurchaseOrderId()){
            // 更新订单信息
            PurchaseOrder purchaseOrder1 = this
                    .selectByPrimaryKey(purchaseOrder.getPurchaseOrderId());
            if(null != purchaseOrder.getStoreId()) {
                purchaseOrder1.setStoreId(purchaseOrder.getStoreId());
            }

            if(null != purchaseOrder.getPurchaseId()) {
                purchaseOrder1.setPurchaseId(purchaseOrder.getPurchaseId());
            }

            this.updateByPrimaryKey(purchaseOrder1);

            // 删除订单详情信息
            purchaseInfoRepository.batchDelete(purchaseInfoRepository
                    .selectByCondition(Condition.builder(PurchaseInfo.class)
                            .andWhere(Sqls.custom()
                                    .andEqualTo("purchaseOrderId", purchaseOrder.getPurchaseOrderId()))
                            .build()));
        } else {
            purchaseOrder.setPurchaseOrderDate(LocalDate.now());
            purchaseOrder.setOrganizationId(tenantId);
            this.insertSelective(purchaseOrder);
        }
        if(purchaseOrder.getChildren() != null && purchaseOrder.getChildren().size() > 0) {
            for(PurchaseInfo purchaseInfo: purchaseOrder.getChildren()) {
                purchaseInfo.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
                purchaseInfoRepository.insertSelective(purchaseInfo);
            }
        }
    }

    @Override
    public List<PurchaseOrderDTO> exportPurchaseOrder(Integer[] purchaseOrderIds) {
        List<PurchaseOrderDTO> purchaseOrderDTOList = new ArrayList<>();
        List<PurchaseOrder> purchaseOrderList;

        String ids = StringUtils.join(purchaseOrderIds, ",");
        if(null == purchaseOrderIds || 0 == purchaseOrderIds.length) {
            purchaseOrderList = this.selectAll();
        } else {
            purchaseOrderList = this.selectByIds(ids);
        }
        for(PurchaseOrder order: purchaseOrderList) {
            PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO();
            List<PurchaseInfoDTO> purchaseInfoDTOList = new ArrayList<>();

            List<PurchaseInfo> details = purchaseInfoRepository.select("purchaseOrderId", order.getPurchaseOrderId());
            for(PurchaseInfo detail: details) {
                PurchaseInfoDTO purchaseInfoDTO = new PurchaseInfoDTO();

                Material material = materialRepository.select(Material.FIELD_MATERIAL_ID, detail.getMaterialId()).get(0);

                purchaseInfoDTO.setMaterialCode(material.getMaterialCode());
                purchaseInfoDTO.setMaterialPrice(material.getMaterialPrice());
                purchaseInfoDTO.setPurchaseInfoRemark(detail.getPurchaseInfoRemark());
                purchaseInfoDTO.setPurchaseInfoSumPrice(detail.getPurchaseInfoSumPrice());
                purchaseInfoDTO.setPurchaseLineNumber(detail.getPurchaseLineNumber());
                purchaseInfoDTO.setPurchaseNumber(detail.getPurchaseNumber());

                purchaseInfoDTOList.add(purchaseInfoDTO);
            }

            Purchase purchase = purchaseRepository.select(Purchase.FIELD_PURCHASE_ID, order.getPurchaseId()).get(0);
            Supplier supplier = supplierRepository.select(Supplier.FIELD_SUPPLIER_ID, order.getSupplierId()).get(0);

            purchaseOrderDTO.setPurchaseName(purchase.getPurchaseName());
            purchaseOrderDTO.setPurchaseOrderNumber(order.getPurchaseOrderNumber());
            purchaseOrderDTO.setSupplierName(supplier.getSupplierName());
            purchaseOrderDTO.setPurchaseOrderDate(order.getPurchaseOrderDate());
            purchaseOrderDTO.setPurchaseInfoList(purchaseInfoDTOList);
            purchaseOrderDTO.setPurchaseOrderState(order.getPurchaseOrderState() == 0 ? "未提交":"已提交");

            purchaseOrderDTOList.add(purchaseOrderDTO);
        }
        System.out.println(purchaseOrderDTOList);
        return purchaseOrderDTOList;
    }
}
