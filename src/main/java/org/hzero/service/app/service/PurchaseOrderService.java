package org.hzero.service.app.service;

import java.time.LocalDate;
import java.util.List;

import org.hzero.service.api.dto.PurchaseOrderDTO;
import org.hzero.service.domain.entity.PurchaseInfo;
import org.hzero.service.domain.entity.PurchaseOrder;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * 应用服务
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
public interface PurchaseOrderService {

    /**
    * 查询参数
    *
    * @param tenantId 租户ID
    * @param purchaseOrder 
    * @param pageRequest 分页
    * @param startDate 开始日期
    * @param endDate 结束日期
    * @return 列表
    */
    Page<PurchaseOrder> list(Long tenantId, PurchaseOrder purchaseOrder, PageRequest pageRequest, LocalDate startDate, LocalDate endDate);

    /**
     * 通过id查询采购订单
     * @param tenantId 租户ID
     * @param purchaseOrderId
     * @return
     */
    ResponseEntity<PurchaseOrder> getPurchaseOrderByOrderId(Long tenantId, Long purchaseOrderId);

    /**
     * 创建采购订单
     * @param organizationId
     * @param purchaseOrder
     * @return
     */
    ResponseEntity<PurchaseOrder> addPurchaseOrder(Long organizationId, PurchaseOrder purchaseOrder);

    /**
     * 修改采购订单状态
     * @param organizationId
     * @param purchaseOrderId
     * @return
     */
    ResponseEntity<?> updateOrderState(Long organizationId, Long purchaseOrderId);

    /**
     * 删除采购订单
     * @param organizationId
     * @param purchaseOrderId
     * @return
     */
    ResponseEntity<?> deletePurchaseOrder(Long organizationId, Long purchaseOrderId);

    /**
     * 导出采购订单
     * @param purchaseOrderIds
     * @return
     */
    List<PurchaseOrderDTO> exportPurchaseOrder(Integer[] purchaseOrderIds);

    /**
     * 导入采购订单
     * @param file
     * @return
     */
    ResponseEntity<?> importPurchaseOrder(MultipartFile file);
}
