package org.hzero.service.app.service.impl;

import java.util.List;

import org.hzero.core.base.BaseAppService;

import org.hzero.core.util.Results;
import org.hzero.service.app.service.PurchaseInfoService;
import org.hzero.service.domain.entity.Material;
import org.hzero.service.domain.entity.PurchaseInfo;
import org.hzero.service.domain.entity.PurchaseOrder;
import org.hzero.service.domain.repository.MaterialRepository;
import org.hzero.service.domain.repository.PurchaseInfoRepository;
import org.hzero.service.domain.repository.RepertoryRepository;
import org.hzero.service.infra.mapper.PurchaseOrderMapper;
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
public class PurchaseInfoServiceImpl extends BaseAppService implements PurchaseInfoService {

    private final PurchaseInfoRepository purchaseInfoRepository;
    private final PurchaseOrderMapper purchaseOrderMapper;
    private final RepertoryRepository repertoryRepository;
    private final MaterialRepository materialRepository;

    @Autowired
    public PurchaseInfoServiceImpl(PurchaseInfoRepository purchaseInfoRepository,
                                   PurchaseOrderMapper purchaseOrderMapper, RepertoryRepository repertoryRepository, MaterialRepository materialRepository) {
        this.purchaseInfoRepository = purchaseInfoRepository;
        this.purchaseOrderMapper = purchaseOrderMapper;
        this.repertoryRepository = repertoryRepository;
        this.materialRepository = materialRepository;
    }

    @Override
    public ResponseEntity<List<PurchaseInfo>> getPurchaseOrderDetailsByOrderId(Long tenantId, Long purchaseOrderId) {
        List<PurchaseInfo> purchaseInfoList = this.purchaseInfoRepository.getPurchaseOrderDetailsByOrderId(purchaseOrderId);
        for(PurchaseInfo purchaseInfo: purchaseInfoList) {
            Material material = materialRepository.selectByPrimaryKey(purchaseInfo.getMaterialId());
            purchaseInfo.setMaterialCode(material.getMaterialCode());
            purchaseInfo.setMaterialDescription(material.getMaterialDescription());
            purchaseInfo.setMaterialPrice(material.getMaterialPrice());
            purchaseInfo.setMaterialUnit(material.getMaterialUnit());
        }
        return Results.success(purchaseInfoList);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> putStorage(Long organizationId, PurchaseOrder purchaseOrder) {
        if(null == purchaseOrder || null == purchaseOrder.getChildren() || purchaseOrder.getChildren().size() == 0) {
            return Results.error("订单或物料信息不能为空");
        }
        purchaseInfoRepository.updateStorageState(purchaseOrder.getChildren());
        repertoryRepository.addStorage(purchaseOrder);
        return Results.success("入库成功");
    }
}
