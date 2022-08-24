package org.hzero.service.infra.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import org.hzero.service.domain.entity.SaleInfo;
import io.choerodon.mybatis.common.BaseMapper;

/**
 * Mapper
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
@Repository
public interface SaleInfoMapper extends BaseMapper<SaleInfo> {

    /**
     * 根据销售订单id获取订单详情
     * @param saleOrderId
     * @return
     */
    List<SaleInfo> getSaleOrderDetailsByOrderId(@Param("saleOrderId") Long saleOrderId);
}
