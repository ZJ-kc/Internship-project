package org.hzero.service.app.service.impl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.boot.message.MessageClient;
import org.hzero.boot.message.entity.Receiver;
import org.hzero.core.base.BaseAppService;
import org.hzero.core.util.Results;
import org.hzero.service.app.service.PurchaseInfoService;
import org.hzero.service.app.service.PurchaseOrderCheckService;
import org.hzero.service.app.service.PurchaseOrderService;
import org.hzero.service.domain.entity.Purchase;
import org.hzero.service.domain.entity.PurchaseInfo;
import org.hzero.service.domain.entity.PurchaseOrder;
import org.hzero.service.domain.entity.User;
import org.hzero.service.domain.repository.PurchaseOrderRepository;
import org.hzero.service.domain.repository.PurchaseRepository;
import org.hzero.service.domain.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @Author : SunYuji
 * @create 2022/8/21 18:57
 */
@Service
public class PurchaseOrderCheckServiceImpl extends BaseAppService implements PurchaseOrderCheckService {

    private final PurchaseOrderService purchaseOrderService;
    private final PurchaseInfoService purchaseInfoService;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final MessageClient messageClient;

    public PurchaseOrderCheckServiceImpl(PurchaseOrderService purchaseOrderService,
                                         PurchaseInfoService purchaseInfoService,
                                         PurchaseOrderRepository purchaseOrderRepository,
                                         PurchaseRepository purchaseRepository,
                                         UserRepository userRepository,
                                         MessageClient messageClient) {
        this.purchaseOrderService = purchaseOrderService;
        this.purchaseInfoService = purchaseInfoService;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
        this.messageClient = messageClient;
    }

    @Override
    public Page<PurchaseOrder> list(Long tenantId, String keyword, PurchaseOrder purchaseOrder, PageRequest pageRequest, LocalDate beginDate, LocalDate endDate) {
        return purchaseOrderService.list(tenantId, keyword, purchaseOrder, pageRequest, beginDate, endDate);
    }

    @Override
    public ResponseEntity<PurchaseOrder> getPurchaseOrderByOrderId(Long tenantId, Long purchaseOrderId) {
        return purchaseOrderService.getPurchaseOrderByOrderId(tenantId, purchaseOrderId);
    }

    @Override
    public ResponseEntity<List<PurchaseInfo>> getPurchaseOrderDetailsByOrderId(Long tenantId, Long purchaseOrderId) {
        return purchaseInfoService.getPurchaseOrderDetailsByOrderId(tenantId, purchaseOrderId);
    }

    /**
     * TODO 发送审核结果
     * @param tenantId
     * @param purchaseOrderId
     * @return
     */
    @Override
    public ResponseEntity<?> checkOrderAgree(Long tenantId, Long purchaseOrderId) {
        PurchaseOrder order = purchaseOrderRepository.selectByPrimaryKey(purchaseOrderId);
        if(1 != order.getPurchaseOrderState()) {
            return Results.error("error");
        }
        order.setPurchaseOrderState(2);

        int count = purchaseOrderRepository.updateOptional(order, PurchaseOrder.FIELD_PURCHASE_ORDER_STATE);
        if(1 == count) {

            Purchase purchase = purchaseRepository.selectByPrimaryKey(order.getPurchaseId());
            User user = userRepository.selectByPrimaryKey(purchase.getUserId());

            String serverCode= "PURCHASE";
            // 消息模板编码
            String messageTemplateCode="HFF_EMAIL_APPROVE";
            // 指定消息接收人邮箱
            Receiver receiver = new Receiver().setEmail(user.getEmail());
            List<Receiver> receiverList = Collections.singletonList(receiver);
            // 消息模板参数
            Map<String, String> args = new HashMap<>(2);
            args.put("param", "采购");
            messageClient.sendEmail(tenantId, serverCode, messageTemplateCode, receiverList, args);

            return Results.success("success");
        }
        return Results.error("error");
    }

    /**
     * TODO 发送审核结果
     * @param tenantId
     * @param purchaseOrderId
     * @return
     */
    @Override
    public ResponseEntity<?> checkOrderReject(Long tenantId, Long purchaseOrderId) {
        PurchaseOrder order = purchaseOrderRepository.selectByPrimaryKey(purchaseOrderId);
        if(1 != order.getPurchaseOrderState()) {
            return Results.error("error");
        }
        order.setPurchaseOrderState(3);

        int count = purchaseOrderRepository.updateOptional(order, PurchaseOrder.FIELD_PURCHASE_ORDER_STATE);
        if(1 == count) {

            Purchase purchase = purchaseRepository.selectByPrimaryKey(order.getPurchaseId());
            User user = userRepository.selectByPrimaryKey(purchase.getUserId());

            String serverCode= "PURCHASE";
            // 消息模板编码
            String messageTemplateCode="HFF_EMAIL_REJECT";
            // 指定消息接收人邮箱
            Receiver receiver = new Receiver().setEmail(user.getEmail());
            List<Receiver> receiverList = Collections.singletonList(receiver);
            // 消息模板参数
            Map<String, String> args = new HashMap<>(2);
            args.put("param", "采购");
            messageClient.sendEmail(tenantId, serverCode, messageTemplateCode, receiverList, args);

            return Results.success("success");
        }
        return Results.error("error");
    }
}
