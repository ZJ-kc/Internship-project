package org.hzero.service.app.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import org.hzero.service.domain.entity.SaleInfo;
import org.hzero.service.domain.entity.SaleOrder;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
/**
 * 应用服务
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
public interface SaleInfoService {

    /**
     * 根据订单id查询订单详细信息
     * @param organizationId
     * @param saleOrderId
     * @return
     */
    ResponseEntity<List<SaleInfo>> getSaleOrderDetailsByOrderId(Long organizationId, Long saleOrderId);


    /**
     * 物料发货
     * @param organizationId
     * @param saleOrderId
     * @param saleInfoIds
     * @return
     */
    ResponseEntity<?> outStorage(Long organizationId, Long saleOrderId, Long[] saleInfoIds);
}
