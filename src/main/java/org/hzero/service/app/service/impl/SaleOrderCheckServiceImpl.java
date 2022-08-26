package org.hzero.service.app.service.impl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hzero.boot.message.entity.Receiver;
import org.hzero.service.domain.entity.*;
import org.hzero.service.domain.repository.*;
import org.hzero.service.infra.mapper.SaleOrderMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

import org.hzero.core.base.BaseAppService;
import org.hzero.core.util.Results;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.hzero.service.app.service.SaleInfoService;
import org.hzero.service.app.service.SaleOrderCheckService;
import org.hzero.service.app.service.SaleOrderService;
import org.hzero.boot.message.MessageClient;

/**
 * @Author: zj
 * @Date: 2022/8/22
 * @Time: 0:36
 */
@Service
public class SaleOrderCheckServiceImpl extends BaseAppService implements SaleOrderCheckService {

    private final SaleOrderService saleOrderService;
    private final SaleInfoService saleInfoService;
    private final SaleOrderRepository saleOrderRepository;
    private final SaleInfoRepository saleInfoRepository;
    private final RepertoryRepository repertoryRepository;
    private final MaterialRepository materialRepository;
    private final SaleOrderMapper saleOrderMapper;
    private final SaleRepository saleRepository;
    private final UserRepository userRepository;
    private final MessageClient messageClient;

    public SaleOrderCheckServiceImpl(SaleOrderService saleOrderService,
                                     SaleInfoService saleInfoService,
                                     SaleOrderRepository saleOrderRepository,
                                     SaleInfoRepository saleInfoRepository,
                                     RepertoryRepository repertoryRepository, MaterialRepository materialRepository, SaleOrderMapper saleOrderMapper, SaleRepository saleRepository, UserRepository userRepository, MessageClient messageClient) {
        this.saleOrderService = saleOrderService;
        this.saleInfoService = saleInfoService;
        this.saleOrderRepository = saleOrderRepository;
        this.saleInfoRepository = saleInfoRepository;
        this.repertoryRepository = repertoryRepository;
        this.materialRepository = materialRepository;
        this.saleOrderMapper = saleOrderMapper;
        this.saleRepository = saleRepository;
        this.userRepository = userRepository;
        this.messageClient = messageClient;
    }

    @Override
    public Page<SaleOrder> list(Long tenantId, String keyword,  SaleOrder saleOrder, PageRequest pageRequest, LocalDate beginDate, LocalDate endDate) {
        return saleOrderService.list(tenantId, keyword, saleOrder, pageRequest, beginDate, endDate);
    }

    @Override
    public ResponseEntity<SaleOrder> getSaleOrderByOrderId(Long tenantId, Long saleOrderId) {
        return saleOrderService.getSaleOrderByOrderId(tenantId, saleOrderId);
    }

    @Override
    public ResponseEntity<List<SaleInfo>> getSaleOrderDetailsByOrderId(Long tenantId, Long saleOrderId) {
        List<SaleInfo> saleOrderDetails = saleInfoService.getSaleOrderDetailsByOrderId(tenantId, saleOrderId).getBody();
        SaleOrder saleOrder=saleOrderRepository.selectByPrimaryKey(saleOrderId);
        Long storeId = saleOrderMapper.selectByPrimaryKey(saleOrderId).getStoreId();
        for(SaleInfo saleInfo:saleOrderDetails){
            Material material = materialRepository.selectByPrimaryKey(saleInfo.getMaterialId());
            Repertory repertory = repertoryRepository.selectByCondition(
                    Condition.builder(Repertory.class)
                            .andWhere(
                                    Sqls.custom()
                                            .andEqualTo(Repertory.FIELD_STORE_ID, storeId)
                                            .andEqualTo(Repertory.FIELD_MATERIAL_ID, material.getMaterialId())
                            )
                            .build()
            ).get(0);
            saleInfo.setRepertory(repertory);
        }
        return Results.success(saleOrderDetails);
    }

    /**
     * TODO 待做：发送审核结果
     * 审核通过
     * @param tenantId
     * @param saleOrderId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> checkOrderAgree(Long tenantId, Long saleOrderId) {
        SaleOrder order = saleOrderRepository.selectByPrimaryKey(saleOrderId);
        if(1 != order.getSaleOrderState()) {
            return Results.error("error");
        }

        // 判断库存是否充足
        List<SaleInfo> saleInfos = saleInfoRepository.select(SaleInfo.FIELD_SALE_ORDER_ID, saleOrderId);
        for(SaleInfo saleInfo: saleInfos) {
            Repertory repertory = repertoryRepository.selectByCondition(
                    Condition.builder(Repertory.class)
                            .andWhere(
                                    Sqls.custom()
                                            .andEqualTo(Repertory.FIELD_STORE_ID, order.getStoreId())
                                            .andEqualTo(Repertory.FIELD_MATERIAL_ID, saleInfo.getMaterialId())
                            )
                            .build()
            ).get(0);
            if(repertory.getAbleRepertoryNumber() < saleInfo.getSaleNumber()) {
                return Results.error("库存不足");
            }

            repertory.setAbleRepertoryNumber(repertory.getAbleRepertoryNumber()-saleInfo.getSaleNumber());
            repertory.setWaitRepertoryNumber(repertory.getWaitRepertoryNumber()+saleInfo.getSaleNumber());

            repertoryRepository.updateOptional(repertory, Repertory.FIELD_ABLE_REPERTORY_NUMBER, Repertory.FIELD_WAIT_REPERTORY_NUMBER);
        }


        order.setSaleOrderState(2);

        int count = saleOrderRepository.updateOptional(order, SaleOrder.FIELD_SALE_ORDER_STATE);
        if(1 == count) {
            Sale sale = saleRepository.selectByPrimaryKey(order.getSaleId());
            User user = userRepository.selectByPrimaryKey((sale.getUserId()));

            //：邮箱编码
            String serverCode = "SALE";

            //消息模板
            String messageTemplateCode = "HFF_EMAIL_APPROVE";

            //指定消息接收人邮箱
            Receiver receiver = new Receiver().setEmail(user.getEmail());
            List<Receiver> receiverList= Collections.singletonList(receiver);
            //消息模板参数
            Map<String,String> args = new HashMap<>(2);
            args.put("param", "销售");
            messageClient.sendEmail(tenantId, serverCode, messageTemplateCode, receiverList, args);

            return Results.success("success");
        }
        return Results.error("error");
    }

    /**
     * 审核拒绝
     * @param tenantId
     * @param saleOrderId
     * @return
     */
    @Override
    public ResponseEntity<?> checkOrderReject(Long tenantId, Long saleOrderId) {
        SaleOrder order = saleOrderRepository.selectByPrimaryKey(saleOrderId);
        if(1 != order.getSaleOrderState()) {
            return Results.error("error");
        }
        order.setSaleOrderState(3);

        int count = saleOrderRepository.updateOptional(order, SaleOrder.FIELD_SALE_ORDER_STATE);
        if(1 == count) {

            Sale sale = saleRepository.selectByPrimaryKey(order.getSaleId());
            User user=userRepository.selectByPrimaryKey((sale.getUserId()));

            //：邮箱编码
            String serverCode="SALE";

            //消息模板
            String messageTemplateCode="HFF_EMAIL_REJECT";

            //指定消息接收人邮箱
            Receiver receiver =new Receiver().setEmail(user.getEmail());
            List<Receiver> receiverList= Collections.singletonList(receiver);
            //消息模板参数
            Map<String,String> args=new HashMap<>(2);
            args.put("param","销售");
            messageClient.sendEmail(tenantId,serverCode,messageTemplateCode,receiverList,args);
            return Results.success("success");
        }
        return Results.error("error");
    }

}
