package com.example;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.hzero.service.HffServiceApplication;
import org.hzero.service.domain.entity.PurchaseInfo;
import org.hzero.service.domain.entity.PurchaseOrder;
import org.hzero.service.domain.repository.PurchaseInfoRepository;
import org.hzero.service.domain.repository.PurchaseOrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

        System.out.println(purchaseOrders);
    }
}
