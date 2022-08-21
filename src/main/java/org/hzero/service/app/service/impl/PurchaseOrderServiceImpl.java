package org.hzero.service.app.service.impl;

import java.time.LocalDate;
import java.util.List;

import io.choerodon.mybatis.pagehelper.PageHelper;
import org.hzero.core.base.BaseAppService;

import org.hzero.core.util.Results;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.hzero.mybatis.util.Sqls;
import org.hzero.service.app.service.PurchaseOrderService;
import org.hzero.service.domain.entity.PurchaseInfo;
import org.hzero.service.domain.entity.PurchaseOrder;
import org.hzero.service.domain.repository.PurchaseInfoRepository;
import org.hzero.service.domain.repository.PurchaseOrderRepository;
import org.hzero.service.domain.repository.RepertoryRepository;
import org.hzero.service.infra.mapper.PurchaseOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

/**
 * 应用服务默认实现
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
@Service
public class PurchaseOrderServiceImpl extends BaseAppService implements PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderMapper purchaseOrderMapper;
    private final PurchaseInfoRepository purchaseInfoRepository;
    private final RepertoryRepository repertoryRepository;

    @Autowired
    public PurchaseOrderServiceImpl(PurchaseOrderRepository purchaseOrderRepository,
                                    PurchaseOrderMapper purchaseOrderMapper,
                                    PurchaseInfoRepository purchaseInfoRepository,
                                    RepertoryRepository repertoryRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderMapper = purchaseOrderMapper;
        this.purchaseInfoRepository = purchaseInfoRepository;
        this.repertoryRepository = repertoryRepository;
    }

    @Override
    public Page<PurchaseOrder> list(Long tenantId, PurchaseOrder purchaseOrder, PageRequest pageRequest,
                                    LocalDate startDate, LocalDate endDate) {
        return PageHelper.doPageAndSort(pageRequest, () -> purchaseOrderRepository.getPurchaseOrderPage(tenantId, purchaseOrder, startDate, endDate));
    }

    @Override
    public ResponseEntity<PurchaseOrder> getPurchaseOrderByOrderId(Long tenantId, Long purchaseOrderId) {
        PurchaseOrder purchaseOrder = this.purchaseOrderMapper.getPurchaseOrderByOrderId(tenantId, purchaseOrderId);
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
}
