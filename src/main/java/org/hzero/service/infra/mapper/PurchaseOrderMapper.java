package org.hzero.service.infra.mapper;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.hzero.service.domain.entity.PurchaseInfo;
import org.hzero.service.domain.entity.PurchaseOrder;
import io.choerodon.mybatis.common.BaseMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

/**
 * Mapper
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
@Repository
public interface PurchaseOrderMapper extends BaseMapper<PurchaseOrder> {
    /**
     * 查询采购订单分页
     * @param tenantId
     * @param purchaseOrder
     * @param startDate
     * @param endDate
     * @return
     */
    List<PurchaseOrder> getPurchaseOrderPage(@Param("tenantId") Long tenantId, @Param("purchaseOrder") PurchaseOrder purchaseOrder, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     *  根据订单id查询采购订单
     * @param tenantId
     * @param purchaseOrderId
     * @return
     */
    PurchaseOrder getPurchaseOrderByOrderId(@Param("tenantId") Long tenantId, @Param("purchaseOrderId") Long purchaseOrderId);
}
