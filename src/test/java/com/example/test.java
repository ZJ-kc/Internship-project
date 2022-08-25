package com.example;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.hzero.boot.message.MessageClient;
import org.hzero.boot.message.entity.Receiver;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.hzero.service.HffServiceApplication;
import org.hzero.service.api.dto.BackLogDTO;
import org.hzero.service.api.dto.BestSaleDTO;
import org.hzero.service.api.dto.MoneyBoardDTO;
import org.hzero.service.api.dto.PurchaseAndSaleStateDTO;
import org.hzero.service.app.service.PurchaseAndSaleService;
import org.hzero.service.domain.entity.PurchaseInfo;
import org.hzero.service.domain.entity.PurchaseOrder;
import org.hzero.service.domain.repository.PurchaseInfoRepository;
import org.hzero.service.domain.repository.PurchaseOrderRepository;
import org.hzero.service.infra.util.PriceComputeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.hzero.boot.message.MessageClient;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : SunYuji
 * @create 2022/8/21 15:00
 */
@SpringBootTest(classes = {HffServiceApplication.class})
@RunWith(SpringRunner.class)
public class test {

    @Resource
    private PurchaseOrderRepository purchaseOrderRepository;
    @Resource
    private PurchaseInfoRepository purchaseInfoRepository;

    @Test
    public void test() {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setStoreAddress("未知");
        purchaseOrder.setPurchaseOrderNumber("sLSDKJFLDS").setStoreAddress("a")
                .setOrganizationId(1L)
                .setSupplierId(1L)
//                .setCurrency()

                .setPurchaseId(1L)
                .setPurchaseOrderState(0)
                .setPurchaseOrderSumPrice(new BigDecimal("1.23"))
                .setPurchaseOrderDate(LocalDate.now());
//        purchaseOrderRepository.insert(purchaseOrder);
        purchaseOrderRepository.insertSelective(purchaseOrder);
        System.out.println(purchaseOrder.getPurchaseOrderId());
    }

    @Test
    public void test1() {
        List<PurchaseInfo> purchaseOrderId = purchaseInfoRepository.selectByCondition(Condition.builder(PurchaseInfo.class)
                .andWhere(
                        Sqls.custom().andEqualTo("purchaseOrderId", 1)
                )
                .build());
//        purchaseInfoRepository.batchDelete();
    }

    @Test
    public void test2() {
        Integer[] purchaseOrderIds = new Integer[2];
        purchaseOrderIds[0] = 1;
        purchaseOrderIds[1] = 2;
//        purchaseOrderIds[1] = 1;
        String ids = StringUtils.join(purchaseOrderIds, ",").toString();
//        for(int i =0; i< purchaseOrderIds.length;i++) {
//
//        }
        System.out.println(ids);
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.selectByIds(ids);
        System.out.println(purchaseOrderList);
    }

    @Test
    public void test3() {
        LocalDateTime today_start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime today_end = LocalDateTime.of(LocalDate.now().plusDays(1L), LocalTime.MIN);
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.selectByCondition(
                Condition.builder(PurchaseOrder.class).andWhere(
                        Sqls.custom()
                                .andEqualTo(PurchaseOrder.FIELD_PURCHASE_ORDER_STATE, 2)
                                .andBetween(PurchaseOrder.FIELD_PURCHASE_ORDER_DATE, today_start, today_end)
                ).build()
        );

        BigDecimal bigDecimal = PriceComputeUtils.computePurchaseOrderPrice(purchaseOrders);
        System.out.println(bigDecimal);

        System.out.println(purchaseOrders);
    }

    @Test
    public void test4() {
        System.out.println(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));
        System.out.println(LocalDateTime.now().with(TemporalAdjusters.firstDayOfYear()));
        System.out.println(LocalDate.now().with(TemporalAdjusters.firstDayOfYear()));
        System.out.println(LocalDate.now().with(TemporalAdjusters.firstDayOfNextYear()));
        System.out.println(LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth()));
    }

    @Resource
    private PurchaseAndSaleService purchaseAndSaleService;

    @Test
    public void test5() {
        MoneyBoardDTO moneyBoard = purchaseAndSaleService.getMoneyBoard();
        System.out.println(moneyBoard);
    }

    @Test
    public void test6() {
        List<PurchaseAndSaleStateDTO> list = purchaseAndSaleService.listPurchaseSaleState();
        System.out.println(list);
    }

    @Test
    public void test7() {
        List<BestSaleDTO> bestSaleDTOS = purchaseAndSaleService.listBestSale();
        System.out.println(bestSaleDTOS);
    }

    @Test
    public void test8() {
        List<BackLogDTO> backLogDTOS = purchaseAndSaleService.listBacklog();
        System.out.println(backLogDTOS);
    }

    @Resource
    private MessageClient messageClient;

    @Test
    public void test9() {
        long tenantId = 355L;
        // 邮箱账户编码
        String serverCode= "PURCHASE";
        // 消息模板编码
        String messageTemplateCode="HWKF_EMAIL_APPROVE";
        // 指定消息接收人邮箱
        Receiver receiver = new Receiver().setEmail("541699620@qq.com");
        List<Receiver> receiverList = Collections.singletonList(receiver);
        // 消息模板参数
        Map<String, String> args = new HashMap<>(2);
        args.put("param", "123456");
        messageClient.sendEmail(tenantId, serverCode, messageTemplateCode, receiverList, null);
    }

    @Test
    public void test10() {
//        PurchaseOrder order = purchaseOrderRepository.selectByPrimaryKey(1L);
//        order.setPurchaseOrderSumPrice(new BigDecimal(123456));

//        PurchaseOrder order = new PurchaseOrder().setStoreAddress("未知地点");
//        purchaseOrderRepository.insertSelective(order);
        purchaseOrderRepository.deleteByPrimaryKey(65L);
//        purchaseOrderRepository.updateByPrimaryKey(order);
    }
}
