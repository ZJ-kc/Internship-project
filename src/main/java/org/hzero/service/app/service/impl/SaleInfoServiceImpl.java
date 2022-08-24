package org.hzero.service.app.service.impl;

import java.util.List;

import org.hzero.core.base.BaseAppService;

import org.hzero.core.util.Results;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.hzero.service.app.service.SaleInfoService;
import org.hzero.service.domain.entity.*;
import org.hzero.service.domain.repository.MaterialRepository;
import org.hzero.service.domain.repository.RepertoryRepository;
import org.hzero.service.domain.repository.SaleInfoRepository;
import org.hzero.service.infra.mapper.SaleOrderMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 应用服务默认实现
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
@Service
public class SaleInfoServiceImpl extends BaseAppService implements SaleInfoService {

    private final SaleInfoRepository saleInfoRepository;
    private final SaleOrderMapper saleOrderMapper;
    private final RepertoryRepository repertoryRepository;
    private final MaterialRepository materialRepository;

    @Autowired
    public SaleInfoServiceImpl(SaleInfoRepository saleInfoRepository,
                               SaleOrderMapper saleOrderMapper,
                               RepertoryRepository repertoryRepository,
                               MaterialRepository materialRepository) {
        this.saleInfoRepository = saleInfoRepository;
        this.saleOrderMapper=saleOrderMapper;
        this.repertoryRepository=repertoryRepository;
        this.materialRepository = materialRepository;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<List<SaleInfo>> getSaleOrderDetailsByOrderId(Long organizationId, Long saleOrderId) {
        List<SaleInfo> saleInfoList = this.saleInfoRepository.getSaleOrderDetailsByOrderId(saleOrderId);
        for(SaleInfo saleInfo: saleInfoList) {
            Material material = materialRepository.selectByPrimaryKey(saleInfo.getMaterialId());
//            saleInfo.setMaterialCode(material.getMaterialCode());
//            saleInfo.setMaterialDescription(material.getMaterialDescription());
//            saleInfo.setMaterialPrice(material.getMaterialPrice());
//            saleInfo.setMaterialUnit(material.getMaterialUnit());
//            List<Repertory> repertoryList=repertoryRepository.selectByCondition(Condition.builder(Material.class).andWhere(
//                    Sqls.custom()
//                            .andEqualTo("materialId", )
//                            .orLike("employeeName", "王")
//            ).build());
//            Repertory repertory=repertoryRepository.selectByPrimaryKey()

            saleInfo.setMaterial(material);
        }
        return Results.success(saleInfoList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> outStorage(Long organizationId, Long saleOrderId, Long[] saleInfoIds) {
        if(null == saleOrderId ||  saleInfoIds.length == 0) {
            return Results.error("订单或物料信息不能为空");
        }
        Boolean storageState = saleInfoRepository.updateStorageState(saleOrderId, saleInfoIds);
        if(!storageState) {
            Results.error("物料信息不存在");
        }
        repertoryRepository.outStorage(saleOrderId, saleInfoIds);
        return Results.success("发货成功");
    }
}
