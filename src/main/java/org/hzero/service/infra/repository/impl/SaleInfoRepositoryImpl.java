package org.hzero.service.infra.repository.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.service.domain.entity.PurchaseInfo;
import org.hzero.service.domain.entity.SaleInfo;
import org.hzero.service.domain.repository.SaleInfoRepository;
import org.hzero.service.infra.mapper.SaleInfoMapper;

import org.springframework.stereotype.Component;

/**
 *  资源库实现
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
@Component
public class SaleInfoRepositoryImpl extends BaseRepositoryImpl<SaleInfo> implements SaleInfoRepository {


    private SaleInfoMapper saleInfoMapper;

    public SaleInfoRepositoryImpl(SaleInfoMapper saleInfoMapper) {
        this.saleInfoMapper = saleInfoMapper;
    }

    @Override
    public List<SaleInfo> getSaleOrderDetailsByOrderId(Long saleOrderId) {
        return this.saleInfoMapper.getSaleOrderDetailsByOrderId(saleOrderId);
    }

    @Override
    public Boolean updateStorageState(Long saleOrderId, Long[] saleInfoIds) {
        // 验证物料详情数据是否存在
        List<Long> infoIds = this.select(SaleInfo.FIELD_SALE_ORDER_ID, saleOrderId)
                .stream()
                .map(SaleInfo::getSaleInfoId)
                .collect(Collectors.toList());
        List<Long> saleInfoList = new ArrayList<>(Arrays.asList(saleInfoIds));
        saleInfoList.removeAll(infoIds);
        if(saleInfoList.size() > 0) {
            return false;
        }

        for(Long saleInfo: saleInfoIds) {
            SaleInfo info = saleInfoMapper.selectByPrimaryKey(saleInfo);
            info.setDeliveryState(1);
            saleInfoMapper.updateOptional(info, SaleInfo.FIELD_DELIVERY_STATE);
        }

        return true;
    }
}
