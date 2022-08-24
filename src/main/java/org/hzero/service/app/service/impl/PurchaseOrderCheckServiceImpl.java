package org.hzero.service.app.service.impl;

import java.time.LocalDate;
import java.util.List;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.core.base.BaseAppService;
import org.hzero.core.util.Results;
import org.hzero.service.app.service.PurchaseInfoService;
import org.hzero.service.app.service.PurchaseOrderCheckService;
import org.hzero.service.app.service.PurchaseOrderService;
import org.hzero.service.domain.entity.PurchaseInfo;
import org.hzero.service.domain.entity.PurchaseOrder;
import org.hzero.service.domain.repository.PurchaseOrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @Author : SunYuji
 * @create 2022/8/21 18:57
 */
@Service
public class PurchaseOrderCheckServiceImpl extends BaseAppService implements PurchaseOrderCheckService {

    private final PurchaseOrderService purchaseOrderService;
    private final PurchaseInfoService purchaseInfoService;
    private final PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrderCheckServiceImpl(PurchaseOrderService purchaseOrderService,
                                         PurchaseInfoService purchaseInfoService,
                                         PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderService = purchaseOrderService;
        this.purchaseInfoService = purchaseInfoService;
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Override
    public Page<PurchaseOrder> list(Long tenantId, String keyword, PurchaseOrder purchaseOrder, PageRequest pageRequest, LocalDate beginDate, LocalDate endDate) {
        return purchaseOrderService.list(tenantId, keyword, purchaseOrder, pageRequest, beginDate, endDate);
    }

    @Override
    public ResponseEntity<PurchaseOrder> getPurchaseOrderByOrderId(Long tenantId, Long purchaseOrderId) {
        return purchaseOrderService.getPurchaseOrderByOrderId(tenantId, purchaseOrderId);
    }

    @Override
    public ResponseEntity<List<PurchaseInfo>> getPurchaseOrderDetailsByOrderId(Long tenantId, Long purchaseOrderId) {
        return purchaseInfoService.getPurchaseOrderDetailsByOrderId(tenantId, purchaseOrderId);
    }

    /**
     * TODO 发送审核结果
     * @param tenantId
     * @param purchaseOrderId
     * @return
     */
    @Override
    public ResponseEntity<?> checkOrderAgree(Long tenantId, Long purchaseOrderId) {
        PurchaseOrder order = purchaseOrderRepository.selectByPrimaryKey(purchaseOrderId);
        if(1 != order.getPurchaseOrderState()) {
            return Results.error("error");
        }
        order.setPurchaseOrderState(2);

        int count = purchaseOrderRepository.updateOptional(order, PurchaseOrder.FIELD_PURCHASE_ORDER_STATE);
        if(1 == count) {
            return Results.success("success");
        }
        return Results.error("error");
    }

    /**
     * TODO 发送审核结果
     * @param tenantId
     * @param purchaseOrderId
     * @return
     */
    @Override
    public ResponseEntity<?> checkOrderReject(Long tenantId, Long purchaseOrderId) {
        PurchaseOrder order = purchaseOrderRepository.selectByPrimaryKey(purchaseOrderId);
        if(1 != order.getPurchaseOrderState()) {
            return Results.error("error");
        }
        order.setPurchaseOrderState(3);

        int count = purchaseOrderRepository.updateOptional(order, PurchaseOrder.FIELD_PURCHASE_ORDER_STATE);
        if(1 == count) {
            return Results.success("success");
        }
        return Results.error("error");
    }
}
