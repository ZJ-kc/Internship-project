package org.hzero.service.domain.repository;

import java.util.List;

import org.hzero.mybatis.base.BaseRepository;
import org.hzero.service.domain.entity.SaleInfo;

/**
 * 资源库
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
public interface SaleInfoRepository extends BaseRepository<SaleInfo> {

    /**
     * 根据销售订单id获取订单详情
     * @param saleOrderId
     * @return
     */
    List<SaleInfo> getSaleOrderDetailsByOrderId(Long saleOrderId);

    /**
     * 更新物料发货状态
     * @param saleOrderId
     * @param saleInfoIds
     */
    Boolean updateStorageState(Long saleOrderId, Long[] saleInfoIds);
}
