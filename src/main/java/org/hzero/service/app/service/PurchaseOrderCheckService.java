package org.hzero.service.app.service;

import java.time.LocalDate;
import java.util.List;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.service.domain.entity.PurchaseInfo;
import org.hzero.service.domain.entity.PurchaseOrder;
import org.springframework.http.ResponseEntity;

/**
 * 采购经理审核服务
 *
 * @Author : SunYuji
 * @create 2022/8/21 18:56
 */
public interface PurchaseOrderCheckService {
    /**
     * 分页查询采购订单
     * @param tenantId 租户id
     * @param purchaseOrder 采购订单
     * @param pageRequest 分页
     * @param beginDate 订单开始日期
     * @param endDate 订单结束日期
     * @return
     */
    Page<PurchaseOrder> list(Long tenantId, PurchaseOrder purchaseOrder, PageRequest pageRequest, LocalDate beginDate, LocalDate endDate);

    /**
     * 通过id查询采购订单
     * @param tenantId
     * @param purchaseOrderId
     * @return
     */
    ResponseEntity<PurchaseOrder> getPurchaseOrderByOrderId(Long tenantId, Long purchaseOrderId);

    /**
     * 根据订单id查询订单详情数据
     * @param tenantId
     * @param purchaseOrderId
     * @return
     */
    ResponseEntity<List<PurchaseInfo>> getPurchaseOrderDetailsByOrderId(Long tenantId, Long purchaseOrderId);

    /**
     * 审核通过
     * @param tenantId
     * @param purchaseOrderId
     * @return
     */
    ResponseEntity<?> checkOrderAgree(Long tenantId, Long purchaseOrderId);

    /**
     * 审核拒绝
     * @param tenantId
     * @param purchaseOrderId
     * @return
     */
    ResponseEntity<?> checkOrderReject(Long tenantId, Long purchaseOrderId);
}
