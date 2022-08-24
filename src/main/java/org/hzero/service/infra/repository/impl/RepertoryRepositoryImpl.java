package org.hzero.service.infra.repository.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hzero.core.util.Results;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.hzero.service.domain.entity.*;
import org.hzero.service.domain.repository.*;
import org.hzero.service.domain.vo.RepertoryParam;
import org.hzero.service.infra.mapper.RepertoryMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * 资源库实现
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
@Component
public class RepertoryRepositoryImpl extends BaseRepositoryImpl<Repertory> implements RepertoryRepository {

    private final RepertoryMapper repertoryMapper;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseInfoRepository purchaseInfoRepository;
    private final SaleOrderRepository saleOrderRepository;
    private final SaleInfoRepository saleInfoRepository;

    public RepertoryRepositoryImpl(RepertoryMapper repertoryMapper,
                                   PurchaseOrderRepository purchaseOrderRepository,
                                   PurchaseInfoRepository purchaseInfoRepository,
                                   SaleOrderRepository saleOrderRepository,
                                   SaleInfoRepository saleInfoRepository) {
        this.repertoryMapper = repertoryMapper;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseInfoRepository = purchaseInfoRepository;
        this.saleOrderRepository = saleOrderRepository;
        this.saleInfoRepository = saleInfoRepository;
    }

    @Override
    public void addStorage(Long purchaseOrderId, Long[] purchaseInfoIds) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.selectByPrimaryKey(purchaseOrderId);
        Long storeId = purchaseOrder.getStoreId();

        List<PurchaseInfo> purchaseInfoList = purchaseInfoRepository.selectByIds(StringUtils.join(purchaseInfoIds, ","));
//        List<PurchaseInfo> purchaseInfoList = purchaseOrder.getChildren();
        for (PurchaseInfo purchaseInfo : purchaseInfoList) {
            Long materialId = purchaseInfo.getMaterialId();
            // 判断库存某仓库是否存在该物料
            List<Repertory> repertoryList = repertoryMapper.selectByCondition(Condition.builder(Repertory.class)
                    .andWhere(
                            Sqls.custom()
                                    .andEqualTo(Repertory.FIELD_STORE_ID, storeId)
                                    .andEqualTo(Repertory.FIELD_MATERIAL_ID, materialId)
                    )
                    .build());
            // 若为空，则创建新的库存，否则，在原有物料库存的基础上添加
            if (null == repertoryList || repertoryList.size() == 0) {
                Repertory repertory = new Repertory().setMaterialId(materialId)
                        .setStoreId(storeId)
                        .setAbleRepertoryNumber(purchaseInfo.getPurchaseNumber())
                        .setWaitRepertoryNumber(0L)
                        .setRepertoryNumber(purchaseInfo.getPurchaseNumber());

                repertoryMapper.insertSelective(repertory);
            } else {
                Repertory repertory = repertoryList.get(0);
                repertory.setAbleRepertoryNumber(repertory.getAbleRepertoryNumber() + purchaseInfo.getPurchaseNumber());
                repertory.setRepertoryNumber(repertory.getAbleRepertoryNumber() + repertory.getWaitRepertoryNumber());

                repertoryMapper.updateOptional(repertory, Repertory.FIELD_REPERTORY_NUMBER, Repertory.FIELD_ABLE_REPERTORY_NUMBER);
            }
        }
    }

    @Override
    public List<Repertory> getRepertoryPage(RepertoryParam repertoryParam) {
        List<Repertory> repertoryPage = repertoryMapper.getRepertoryPage(repertoryParam);
        for(Repertory repertory: repertoryPage) {
            repertory.setStoreName(repertory.getStore().getStoreName());
            repertory.setMaterialCode(repertory.getMaterial().getMaterialCode());
            repertory.setMaterialDescription(repertory.getMaterial().getMaterialDescription());
            repertory.setMaterialUnit(repertory.getMaterial().getMaterialUnit());
        }
        return repertoryPage;
    }

    @Override
    public ResponseEntity<?> outStorage(Long saleOrderId, Long[] saleInfoIds) {
        SaleOrder saleOrder = saleOrderRepository.selectByPrimaryKey(saleOrderId);
        Long storeId = saleOrder.getStoreId();

        List<SaleInfo> saleInfoList = saleInfoRepository.selectByIds(StringUtils.join(saleInfoIds, ","));
//        Long storeId = saleOrder.getStoreId();
//        List<SaleInfo> saleInfoList = saleOrder.getChildren();
        for (SaleInfo saleInfo : saleInfoList) {
            Long materialId = saleInfo.getMaterialId();
            // 判断库存某仓库是否存在该物料
            List<Repertory> repertoryList = repertoryMapper.selectByCondition(Condition.builder(Repertory.class)
                    .andWhere(
                            Sqls.custom()
                                    .andEqualTo(Repertory.FIELD_STORE_ID, storeId)
                                    .andEqualTo(Repertory.FIELD_MATERIAL_ID, materialId)
                    )
                    .build());
            // 若为空，则说明库存不存在
            if (null == repertoryList || repertoryList.isEmpty()) {
                return Results.error("库存不存在");
            }
            Repertory repertory = repertoryList.get(0);
            repertory.setWaitRepertoryNumber(repertory.getWaitRepertoryNumber() - saleInfo.getSaleNumber());
            repertory.setRepertoryNumber(repertory.getAbleRepertoryNumber() + repertory.getWaitRepertoryNumber());
            repertoryMapper.updateOptional(repertory, Repertory.FIELD_WAIT_REPERTORY_NUMBER, Repertory.FIELD_REPERTORY_NUMBER);
        }
        return Results.success();
    }
}
