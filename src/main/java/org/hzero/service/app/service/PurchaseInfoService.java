package org.hzero.service.app.service;

import java.util.List;

import org.hzero.service.domain.entity.PurchaseInfo;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.service.domain.entity.PurchaseOrder;
import org.springframework.http.ResponseEntity;

/**
 * 应用服务
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
public interface PurchaseInfoService {

    /**
     * 根据订单id查询订单详情数据
     * @param tenantId 租户id
     * @param purchaseOrderId 采购订单id
     * @return
     */
    ResponseEntity<List<PurchaseInfo>> getPurchaseOrderDetailsByOrderId(Long tenantId, Long purchaseOrderId);

    /**
     * 物料入库
     * @param organizationId
     * @param purchaseOrderId
     * @param purchaseInfoIds
     * @return
     */
    ResponseEntity<?> putStorage(Long organizationId, Long purchaseOrderId, Long[] purchaseInfoIds);
}
