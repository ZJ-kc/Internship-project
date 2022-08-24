package org.hzero.service.infra.mapper;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import org.hzero.service.domain.entity.SaleOrder;
import io.choerodon.mybatis.common.BaseMapper;

/**
 * Mapper
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
@Repository
public interface SaleOrderMapper extends BaseMapper<SaleOrder> {

    /**
     * 查询销售订单分页
     * @param tenantId
     * @param saleOrder
     * @param startDate
     * @param endDate
     * @return
     */
    List<SaleOrder> getSaleOrderPage(@Param("tenantId") Long tenantId, @Param("keyword") String keyword, @Param("saleOrder") SaleOrder saleOrder, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     *  根据订单id查询销售订单
     * @param tenantId
     * @param saleOrderId
     * @return
     */
    SaleOrder getSaleOrderByOrderId(@Param("tenantId") Long tenantId, @Param("saleOrderId") Long saleOrderId);

}
