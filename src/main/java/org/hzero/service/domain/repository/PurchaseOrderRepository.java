package org.hzero.service.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.hzero.mybatis.base.BaseRepository;
import org.hzero.service.api.dto.PurchaseOrderDTO;
import org.hzero.service.domain.entity.PurchaseOrder;
import org.springframework.http.ResponseEntity;

/**
 * 资源库
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
public interface PurchaseOrderRepository extends BaseRepository<PurchaseOrder> {

    /**
     * 分页查询采购订单
     * @param tenantId
     * @param purchaseOrder
     * @param startDate
     * @param endDate
     * @return
     */
    List<PurchaseOrder> getPurchaseOrderPage(Long tenantId, PurchaseOrder purchaseOrder, LocalDate startDate, LocalDate endDate);

    /**
     * 保存采购订单
     * @param tenantId
     * @param purchaseOrder
     * @return
     */
    void addPurchaseOrder(Long tenantId, PurchaseOrder purchaseOrder);

    /**
     * 导出采购订单
     * @param purchaseOrderIds
     * @return
     */
    List<PurchaseOrderDTO> exportPurchaseOrder(Integer[] purchaseOrderIds);
}
