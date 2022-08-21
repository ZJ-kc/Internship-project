package org.hzero.service.infra.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.hzero.service.domain.entity.PurchaseInfo;
import io.choerodon.mybatis.common.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * Mapper
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
@Repository
public interface PurchaseInfoMapper extends BaseMapper<PurchaseInfo> {

    /**
     * 根据采购订单id获取订单详情
     * @param purchaseOrderId
     * @return
     */
    List<PurchaseInfo> getPurchaseOrderDetailsByOrderId(@Param("purchaseOrderId") Long purchaseOrderId);
}
