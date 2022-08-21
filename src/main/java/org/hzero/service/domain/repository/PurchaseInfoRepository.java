package org.hzero.service.domain.repository;

import java.util.List;

import org.hzero.mybatis.base.BaseRepository;
import org.hzero.service.domain.entity.PurchaseInfo;
import org.hzero.service.domain.entity.PurchaseOrder;

/**
 * 资源库
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
public interface PurchaseInfoRepository extends BaseRepository<PurchaseInfo> {

    /**
     * 根据采购订单id获取订单详情
     * @param purchaseOrderId
     * @return
     */
    List<PurchaseInfo> getPurchaseOrderDetailsByOrderId(Long purchaseOrderId);

    /**
     * 更新物料入库状态
     * @param purchaseInfoList
     */
    void updateStorageState(List<PurchaseInfo> purchaseInfoList);
}
