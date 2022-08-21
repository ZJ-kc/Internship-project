package org.hzero.service.infra.repository.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hzero.core.util.Results;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.hzero.service.domain.entity.PurchaseInfo;
import org.hzero.service.domain.entity.PurchaseOrder;
import org.hzero.service.domain.repository.PurchaseInfoRepository;
import org.hzero.service.domain.repository.PurchaseOrderRepository;
import org.hzero.service.infra.mapper.PurchaseOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    public PurchaseOrderRepositoryImpl(PurchaseOrderMapper purchaseOrderMapper, PurchaseInfoRepository purchaseInfoRepository) {
        this.purchaseOrderMapper = purchaseOrderMapper;
        this.purchaseInfoRepository = purchaseInfoRepository;
    }

    @Override
    public List<PurchaseOrder> getPurchaseOrderPage(Long tenantId, PurchaseOrder purchaseOrder, LocalDate startDate,
                                                    LocalDate endDate) {

        return this.purchaseOrderMapper.getPurchaseOrderPage(tenantId, purchaseOrder, startDate, endDate);
    }

    @Override
    public void addPurchaseOrder(Long tenantId, PurchaseOrder purchaseOrder) {
        if(null != purchaseOrder.getPurchaseOrderId()){
            // 更新订单信息
            PurchaseOrder purchaseOrder1 = this
                    .selectByPrimaryKey(purchaseOrder.getPurchaseOrderId());
            purchaseOrder1.setSupplierId(purchaseOrder.getSupplierId());
            purchaseOrder1.setStoreId(purchaseOrder.getStoreId());
            purchaseOrder1.setStoreAddress(purchaseOrder.getStoreAddress());
            purchaseOrder1.setPurchaseId(purchaseOrder.getPurchaseId());
            purchaseOrder1.setCurrencyId(purchaseOrder.getCurrencyId());

            this.updateByPrimaryKey(purchaseOrder1);

            // 删除订单详情信息
            purchaseInfoRepository.batchDelete(purchaseInfoRepository
                    .selectByCondition(Condition.builder(PurchaseInfo.class)
                            .andWhere(Sqls.custom()
                                    .andEqualTo("purchaseOrderId", purchaseOrder.getPurchaseOrderId()))
                            .build()));
        } else {
            purchaseOrder.setPurchaseOrderDate(LocalDateTime.now());
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
}
