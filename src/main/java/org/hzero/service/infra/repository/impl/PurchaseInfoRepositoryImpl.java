package org.hzero.service.infra.repository.impl;

import java.util.List;

import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.hzero.service.domain.entity.PurchaseInfo;
import org.hzero.service.domain.entity.PurchaseOrder;
import org.hzero.service.domain.repository.PurchaseInfoRepository;
import org.hzero.service.infra.mapper.PurchaseInfoMapper;
import org.springframework.stereotype.Component;
import org.hzero.service.infra.mapper.PurchaseOrderMapper;

/**
 *  资源库实现
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
@Component
public class PurchaseInfoRepositoryImpl extends BaseRepositoryImpl<PurchaseInfo> implements PurchaseInfoRepository {

    private PurchaseInfoMapper purchaseInfoMapper;

    public PurchaseInfoRepositoryImpl(PurchaseInfoMapper purchaseInfoMapper) {
        this.purchaseInfoMapper = purchaseInfoMapper;
    }

    @Override
    public List<PurchaseInfo> getPurchaseOrderDetailsByOrderId(Long purchaseOrderId) {
        return this.purchaseInfoMapper.getPurchaseOrderDetailsByOrderId(purchaseOrderId);
    }

    @Override
    public void updateStorageState(List<PurchaseInfo> purchaseInfoList) {

        for(PurchaseInfo purchaseInfo: purchaseInfoList) {
            PurchaseInfo info = purchaseInfoMapper.selectByPrimaryKey(purchaseInfo.getPurchaseInfoId());
            info.setStorageState(1);
            purchaseInfoMapper.updateOptional(info, PurchaseInfo.FIELD_STORAGE_STATE);
        }
    }
}
