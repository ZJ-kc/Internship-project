package org.hzero.service.app.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.choerodon.mybatis.pagehelper.PageHelper;
import org.hzero.core.base.BaseAppService;

import org.hzero.core.util.Results;
import org.hzero.excel.entity.Column;
import org.hzero.excel.helper.ExcelHelper;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.hzero.service.api.dto.PurchaseOrderDTO;
import org.hzero.service.app.service.PurchaseOrderService;
import org.hzero.service.domain.entity.*;
import org.hzero.service.domain.repository.*;
import org.hzero.service.infra.listener.PurchaseInfoExcelListener;
import org.hzero.service.infra.listener.PurchaseOrderExcelListener;
import org.hzero.service.infra.mapper.PurchaseOrderMapper;
import org.hzero.service.infra.util.PurchaseInfoImportConstantUtils;
import org.hzero.service.infra.util.PurchaseOrderImportConstantUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 * 应用服务默认实现
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
@Service
public class PurchaseOrderServiceImpl extends BaseAppService implements PurchaseOrderService {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderServiceImpl.class);

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderMapper purchaseOrderMapper;
    private final PurchaseInfoRepository purchaseInfoRepository;
    private final MaterialRepository materialRepository;
    private final PurchaseRepository purchaseRepository;
    private final SupplierRepository supplierRepository;
    private final StoreRepository storeRepository;

    @Autowired
    public PurchaseOrderServiceImpl(PurchaseOrderRepository purchaseOrderRepository,
                                    PurchaseOrderMapper purchaseOrderMapper,
                                    PurchaseInfoRepository purchaseInfoRepository,
                                    RepertoryRepository repertoryRepository,
                                    MaterialRepository materialRepository,
                                    PurchaseRepository purchaseRepository,
                                    SupplierRepository supplierRepository,
                                    StoreRepository storeRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderMapper = purchaseOrderMapper;
        this.purchaseInfoRepository = purchaseInfoRepository;
        this.materialRepository = materialRepository;
        this.purchaseRepository = purchaseRepository;
        this.supplierRepository = supplierRepository;
        this.storeRepository = storeRepository;
    }

    @Override
    public Page<PurchaseOrder> list(Long tenantId, PurchaseOrder purchaseOrder, PageRequest pageRequest,
                                    LocalDate startDate, LocalDate endDate) {
        return PageHelper.doPageAndSort(pageRequest, () -> purchaseOrderRepository.getPurchaseOrderPage(tenantId, purchaseOrder, startDate, endDate));
    }

    @Override
    public ResponseEntity<PurchaseOrder> getPurchaseOrderByOrderId(Long tenantId, Long purchaseOrderId) {
        PurchaseOrder purchaseOrder = this.purchaseOrderMapper.getPurchaseOrderByOrderId(tenantId, purchaseOrderId);
        Supplier supplier = supplierRepository.selectByPrimaryKey(purchaseOrder.getSupplierId());
        Purchase purchase = purchaseRepository.selectByPrimaryKey(purchaseOrder.getPurchaseId());
        Store store = storeRepository.selectByPrimaryKey(purchaseOrder.getStoreId());
        purchaseOrder.setPurchaseName(purchase.getPurchaseName());
        purchaseOrder.setSupplierName(supplier.getSupplierName());
        purchaseOrder.setStoreAddress(store.getStoreAddress());
        purchaseOrder.setStoreName(store.getStoreName());
        return Results.success(purchaseOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<PurchaseOrder> addPurchaseOrder(Long organizationId, PurchaseOrder purchaseOrder) {
        purchaseOrderRepository.addPurchaseOrder(organizationId, purchaseOrder);
        return Results.success(purchaseOrder);
    }

    /**
     * TODO 发送提价信息
     * @param organizationId
     * @param purchaseOrderId
     * @return
     */
    @Override
    public ResponseEntity<?> updateOrderState(Long organizationId, Long purchaseOrderId) {
        PurchaseOrder order = purchaseOrderRepository.selectByPrimaryKey(purchaseOrderId);
        order.setPurchaseOrderState(1);

        int count = purchaseOrderRepository.updateOptional(order, PurchaseOrder.FIELD_PURCHASE_ORDER_STATE);
        if(1 == count) {
            return Results.success("提交成功");
        }
        return Results.error();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deletePurchaseOrder(Long organizationId, Long purchaseOrderId) {
        purchaseInfoRepository.batchDelete(purchaseInfoRepository
                .selectByCondition(Condition.builder(PurchaseInfo.class)
                        .andWhere(Sqls.custom()
                                .andEqualTo("purchaseOrderId", purchaseOrderId))
                        .build()));
        int count = purchaseOrderRepository.deleteByPrimaryKey(purchaseOrderId);
        if(1 == count) {
            return Results.success();
        }
        return Results.error();
    }

    @Override
    public List<PurchaseOrderDTO> exportPurchaseOrder(Integer[] purchaseOrderIds) {

        return purchaseOrderRepository.exportPurchaseOrder(purchaseOrderIds);
    }

    @Override
    public ResponseEntity<?> importPurchaseOrder(MultipartFile file) {
        List<Column> purchaseOrderNames = new ArrayList<>();
        List<Column> purchaseInfoNames = new ArrayList<>();

        purchaseOrderNames.add(new Column().setIndex(0).setName(PurchaseOrderImportConstantUtils.FIELD_PURCHASE_ORDER_NUMBER).setColumnType(Column.STRING));
        purchaseOrderNames.add(new Column().setIndex(1).setName(PurchaseOrderImportConstantUtils.FIELD_SUPPLIER_NAME).setColumnType(Column.STRING));
        purchaseOrderNames.add(new Column().setIndex(2).setName(PurchaseOrderImportConstantUtils.FIELD_PURCHASE_ORDER_DATE).setColumnType(Column.DATE).setFormat("yyyy/MM/dd"));
        purchaseOrderNames.add(new Column().setIndex(3).setName(PurchaseOrderImportConstantUtils.FIELD_STORE_NAME).setColumnType(Column.STRING));
        purchaseOrderNames.add(new Column().setIndex(4).setName(PurchaseOrderImportConstantUtils.FIELD_STORE_ADDRESS).setColumnType(Column.STRING));
        purchaseOrderNames.add(new Column().setIndex(5).setName(PurchaseOrderImportConstantUtils.FIELD_PURCHASE_NAME).setColumnType(Column.STRING));
        purchaseOrderNames.add(new Column().setIndex(6).setName(PurchaseOrderImportConstantUtils.FIELD_CURRENCY_SYMBOL).setColumnType(Column.STRING));
        purchaseOrderNames.add(new Column().setIndex(7).setName(PurchaseOrderImportConstantUtils.FIELD_PURCHASE_ORDER_SUM_PRICE).setColumnType(Column.DECIMAL));
        purchaseOrderNames.add(new Column().setIndex(8).setName(PurchaseOrderImportConstantUtils.FIELD_PURCHASE_ORDER_STATE).setColumnType(Column.STRING));

        purchaseInfoNames.add(new Column().setIndex(0).setName(PurchaseInfoImportConstantUtils.FIELD_PURCHASE_ORDER_NUMBER).setColumnType(Column.STRING));
        purchaseInfoNames.add(new Column().setIndex(1).setName(PurchaseInfoImportConstantUtils.FIELD_PURCHASE_INFO_LINE_NUMBER).setColumnType(Column.LONG));
        purchaseInfoNames.add(new Column().setIndex(2).setName(PurchaseInfoImportConstantUtils.FIELD_MATERIAL_CODE).setColumnType(Column.STRING));
        purchaseInfoNames.add(new Column().setIndex(3).setName(PurchaseInfoImportConstantUtils.FIELD_MATERIAL_PRICE).setColumnType(Column.DECIMAL));
        purchaseInfoNames.add(new Column().setIndex(4).setName(PurchaseInfoImportConstantUtils.FIELD_MATERIAL_UNIT).setColumnType(Column.STRING));
        purchaseInfoNames.add(new Column().setIndex(5).setName(PurchaseInfoImportConstantUtils.FIELD_PURCHASE_NUMBER).setColumnType(Column.LONG));
        purchaseInfoNames.add(new Column().setIndex(6).setName(PurchaseInfoImportConstantUtils.FIELD_PURCHASE_INFO_SUM_PRICE).setColumnType(Column.DECIMAL));
        purchaseInfoNames.add(new Column().setIndex(7).setName(PurchaseInfoImportConstantUtils.FIELD_STORE_STATE).setColumnType(Column.STRING));
        purchaseInfoNames.add(new Column().setIndex(8).setName(PurchaseInfoImportConstantUtils.FIELD_PURCHASE_INFO_REMARK).setColumnType(Column.STRING));
        try {
            ExcelHelper.read(file.getInputStream(), new PurchaseOrderExcelListener(purchaseOrderRepository, supplierRepository, storeRepository, purchaseRepository), purchaseOrderNames, 0, 1);
            ExcelHelper.read(file.getInputStream(), new PurchaseInfoExcelListener(materialRepository, purchaseInfoRepository, purchaseOrderRepository), purchaseInfoNames, 1, 1);
        } catch (IOException e) {
            logger.error("PurchaseOrderServiceImpl ----->{}", e.getMessage());
        }
        return Results.success();
    }
}
