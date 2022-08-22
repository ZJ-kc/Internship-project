package org.hzero.service.app.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.choerodon.mybatis.pagehelper.PageHelper;
import org.hzero.core.base.BaseAppService;

import org.hzero.core.util.Results;
import org.hzero.excel.entity.Column;
import org.hzero.excel.helper.ExcelHelper;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.hzero.service.api.dto.PurchaseInfoDTO;
import org.hzero.service.api.dto.PurchaseOrderDTO;
import org.hzero.service.app.service.PurchaseOrderService;
import org.hzero.service.domain.entity.Material;
import org.hzero.service.domain.entity.Purchase;
import org.hzero.service.domain.entity.PurchaseInfo;
import org.hzero.service.domain.entity.PurchaseOrder;
import org.hzero.service.domain.repository.*;
import org.hzero.service.infra.listener.PurchaseInfoExcelListener;
import org.hzero.service.infra.listener.PurchaseOrderExcelListener;
import org.hzero.service.infra.mapper.PurchaseOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 应用服务默认实现
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
@Service
public class PurchaseOrderServiceImpl extends BaseAppService implements PurchaseOrderService {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderServiceImpl.class);

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderMapper purchaseOrderMapper;
    private final PurchaseInfoRepository purchaseInfoRepository;
    private final MaterialRepository materialRepository;
    private final PurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseOrderServiceImpl(PurchaseOrderRepository purchaseOrderRepository,
                                    PurchaseOrderMapper purchaseOrderMapper,
                                    PurchaseInfoRepository purchaseInfoRepository,
                                    RepertoryRepository repertoryRepository,
                                    MaterialRepository materialRepository, PurchaseRepository purchaseRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderMapper = purchaseOrderMapper;
        this.purchaseInfoRepository = purchaseInfoRepository;
        this.materialRepository = materialRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public Page<PurchaseOrder> list(Long tenantId, PurchaseOrder purchaseOrder, PageRequest pageRequest,
                                    LocalDate startDate, LocalDate endDate) {
        return PageHelper.doPageAndSort(pageRequest, () -> purchaseOrderRepository.getPurchaseOrderPage(tenantId, purchaseOrder, startDate, endDate));
    }

    @Override
    public ResponseEntity<PurchaseOrder> getPurchaseOrderByOrderId(Long tenantId, Long purchaseOrderId) {
        PurchaseOrder purchaseOrder = this.purchaseOrderMapper.getPurchaseOrderByOrderId(tenantId, purchaseOrderId);
        return Results.success(purchaseOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<PurchaseOrder> addPurchaseOrder(Long organizationId, PurchaseOrder purchaseOrder) {
        purchaseOrderRepository.addPurchaseOrder(organizationId, purchaseOrder);
        return Results.success(purchaseOrder);
    }

    /**
     * TODO 发送提价信息
     * @param organizationId
     * @param purchaseOrderId
     * @return
     */
    @Override
    public ResponseEntity<?> updateOrderState(Long organizationId, Long purchaseOrderId) {
        PurchaseOrder order = purchaseOrderRepository.selectByPrimaryKey(purchaseOrderId);
        order.setPurchaseOrderState(1);

        int count = purchaseOrderRepository.updateOptional(order, PurchaseOrder.FIELD_PURCHASE_ORDER_STATE);
        if(1 == count) {
            return Results.success("提交成功");
        }
        return Results.error();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deletePurchaseOrder(Long organizationId, Long purchaseOrderId) {
        purchaseInfoRepository.batchDelete(purchaseInfoRepository
                .selectByCondition(Condition.builder(PurchaseInfo.class)
                        .andWhere(Sqls.custom()
                                .andEqualTo("purchaseOrderId", purchaseOrderId))
                        .build()));
        int count = purchaseOrderRepository.deleteByPrimaryKey(purchaseOrderId);
        if(1 == count) {
            return Results.success();
        }
        return Results.error();
    }

    @Override
    public List<PurchaseOrderDTO> exportPurchaseOrder(Integer[] purchaseOrderIds) {
        return purchaseOrderRepository.exportPurchaseOrder(purchaseOrderIds);
    }

    @Override
    public ResponseEntity<?> importPurchaseOrder(MultipartFile file) {
        List<Column> purchaseOrderNames = new ArrayList<>();
        List<Column> purchaseInfoNames = new ArrayList<>();

        purchaseOrderNames.add(new Column().setIndex(0).setName("purchaseOrderNumber").setColumnType(Column.STRING));
        purchaseOrderNames.add(new Column().setIndex(1).setName("supplierNumber").setColumnType(Column.STRING));
        purchaseOrderNames.add(new Column().setIndex(2).setName("purchaseOrderDate").setColumnType(Column.STRING).setFormat("yyyy/MM/dd"));
        purchaseOrderNames.add(new Column().setIndex(3).setName("storeName").setColumnType(Column.STRING));
        purchaseOrderNames.add(new Column().setIndex(4).setName("storeAddress").setColumnType(Column.STRING));
        purchaseOrderNames.add(new Column().setIndex(5).setName("purchaseName").setColumnType(Column.STRING));
        purchaseOrderNames.add(new Column().setIndex(6).setName("currencySymbol").setColumnType(Column.STRING));
        purchaseOrderNames.add(new Column().setIndex(7).setName("purchaseOrderSumPrice").setColumnType(Column.DECIMAL));
        purchaseOrderNames.add(new Column().setIndex(8).setName("purchaseOrderState").setColumnType(Column.STRING));

        purchaseInfoNames.add(new Column().setIndex(0).setName("purchaseOrderNumber").setColumnType(Column.STRING));
        purchaseInfoNames.add(new Column().setIndex(1).setName("purchaseInfoLineNumber").setColumnType(Column.LONG));
        purchaseInfoNames.add(new Column().setIndex(2).setName("materialCode").setColumnType(Column.STRING));
        purchaseInfoNames.add(new Column().setIndex(3).setName("materialPrice").setColumnType(Column.DECIMAL));
        purchaseInfoNames.add(new Column().setIndex(4).setName("materialUnit").setColumnType(Column.STRING));
        purchaseInfoNames.add(new Column().setIndex(5).setName("purchaseNumber").setColumnType(Column.LONG));
        purchaseInfoNames.add(new Column().setIndex(6).setName("purchaseInfoSumPrice").setColumnType(Column.DECIMAL));
        purchaseInfoNames.add(new Column().setIndex(7).setName("storageState").setColumnType(Column.STRING));
        purchaseInfoNames.add(new Column().setIndex(8).setName("purchaseInfoRemark").setColumnType(Column.STRING));
        try {
            ExcelHelper.read(file.getInputStream(), new PurchaseOrderExcelListener(purchaseOrderRepository), purchaseOrderNames, 0, 1);
            ExcelHelper.read(file.getInputStream(), new PurchaseInfoExcelListener(), purchaseInfoNames, 1, 1);
        } catch (IOException e) {
            logger.error("PurchaseOrderServiceImpl ----->{}", e.getMessage());
        }
        return Results.success();
    }
}
