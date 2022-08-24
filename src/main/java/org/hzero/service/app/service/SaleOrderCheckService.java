package org.hzero.service.app.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

import org.hzero.service.domain.entity.SaleInfo;
import org.hzero.service.domain.entity.SaleOrder;

/**
 * @Author: zj
 * @Date: 2022/8/22
 * @Time: 0:34
 */
public interface SaleOrderCheckService {

    /**
     * 分页查询销售订单
     * @param tenantId 租户id
     * @param keyword
     * @param saleOrder 销售订单
     * @param pageRequest 分页
     * @param beginDate 订单开始日期
     * @param endDate 订单结束日期
     * @return
     */
    Page<SaleOrder> list(Long tenantId, String keyword, SaleOrder saleOrder, PageRequest pageRequest, LocalDate beginDate, LocalDate endDate);

    /**
     * 通过id查询销售订单
     * @param tenantId
     * @param saleOrderId
     * @return
     */
    ResponseEntity<SaleOrder> getSaleOrderByOrderId(Long tenantId, Long saleOrderId);

    /**
     * 根据订单id查询详细数据
     * @param tenantId
     * @param saleOrderId
     * @return
     */
    ResponseEntity<List<SaleInfo>> getSaleOrderDetailsByOrderId(Long tenantId, Long saleOrderId);

    /**
     * 审核通过
     * @param tenantId
     * @param saleOrderId
     * @return
     */
    ResponseEntity<?> checkOrderAgree(Long tenantId, Long saleOrderId);

    /**
     * 审核拒绝
     * @param tenantId
     * @param saleOrderId
     * @return
     */
    ResponseEntity<?> checkOrderReject(Long tenantId, Long saleOrderId);
}
