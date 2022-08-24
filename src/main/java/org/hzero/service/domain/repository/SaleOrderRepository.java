package org.hzero.service.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.hzero.mybatis.base.BaseRepository;
import org.hzero.service.api.dto.PurchaseOrderDTO;
import org.hzero.service.api.dto.SaleOrderDTO;
import org.hzero.service.domain.entity.SaleOrder;

/**
 * 资源库
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
public interface SaleOrderRepository extends BaseRepository<SaleOrder> {

    /**
     * 分页查询销售订单
     * @param tenantId
     * @param saleOrder
     * @param startDate
     * @param endDate
     * @return
     */
    List<SaleOrder> getSaleOrderPage(Long tenantId, String keyword, SaleOrder saleOrder, LocalDate startDate, LocalDate endDate);


    /**
     * 保存销售订单
     * @param organizationId
     * @param saleOrder
     */
    void addSaleOrder(Long organizationId, SaleOrder saleOrder);

//    List<SaleOrderDTO> exportSaleOrder(Integer[] saleOrderIds);

    /**
     * 导出销售订单
     * @param saleOrderIds
     * @return
     */
    List<SaleOrderDTO> exportSaleOrder(Integer[] saleOrderIds);
}
