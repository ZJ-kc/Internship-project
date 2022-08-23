package org.hzero.service.app.service.impl;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import org.hzero.core.base.BaseAppService;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.hzero.service.api.dto.BackLogDTO;
import org.hzero.service.api.dto.BestSaleDTO;
import org.hzero.service.api.dto.MoneyBoardDTO;
import org.hzero.service.api.dto.PurchaseAndSaleStateDTO;
import org.hzero.service.app.service.PurchaseAndSaleService;
import org.hzero.service.domain.entity.Purchase;
import org.hzero.service.domain.entity.PurchaseOrder;
import org.hzero.service.domain.entity.Sale;
import org.hzero.service.domain.entity.SaleOrder;
import org.hzero.service.domain.repository.PurchaseOrderRepository;
import org.hzero.service.domain.repository.PurchaseRepository;
import org.hzero.service.domain.repository.SaleOrderRepository;
import org.hzero.service.domain.repository.SaleRepository;
import org.hzero.service.infra.util.PriceComputeUtils;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : SunYuji
 * @create 2022/8/23 16:37
 */
@Service
public class PurchaseAndSaleServiceImpl extends BaseAppService implements PurchaseAndSaleService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SaleOrderRepository saleOrderRepository;
    private final SaleRepository saleRepository;
    private final PurchaseRepository purchaseRepository;

    public PurchaseAndSaleServiceImpl(PurchaseOrderRepository purchaseOrderRepository,
                                      SaleOrderRepository saleOrderRepository,
                                      SaleRepository saleRepository,
                                      PurchaseRepository purchaseRepository) {

        this.purchaseOrderRepository = purchaseOrderRepository;
        this.saleOrderRepository = saleOrderRepository;
        this.saleRepository = saleRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public MoneyBoardDTO getMoneyBoard() {
        MoneyBoardDTO boardDTO = new MoneyBoardDTO();
        // 今日采购金额
        BigDecimal todayPurchasePrice = getTodayPurchasePrice();
        // 本月采购金额
        BigDecimal monthPurchasePrice = getMonthPurchasePrice();
        // 本年采购金额
        BigDecimal yearPurchasePrice = getYearPurchasePrice();
        // 今日销售金额
        BigDecimal todaySalePrice = getTodaySalePrice();
        // 本月销售金额
        BigDecimal monthSalePrice = getMonthSalePrice();
        // 本年销售金额
        BigDecimal yearSalePrice = getYearSalePrice();

        boardDTO.setPurchaseOrderDay(todayPurchasePrice)
                .setPurchaseOrderMonth(monthPurchasePrice)
                .setPurchaseOrderYear(yearPurchasePrice)
                .setSaleOrderDay(todaySalePrice)
                .setSaleOrderMonth(monthSalePrice)
                .setSaleOrderYear(yearSalePrice);

        return boardDTO;
    }

    @Override
    public List<PurchaseAndSaleStateDTO> listPurchaseSaleState() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM");
        List<PurchaseAndSaleStateDTO> stateList = new ArrayList<>();

        LocalDate localDate = LocalDate.now();
        for(int i = 0; i < 6; i++) {
            getStateList(localDate, formatter, stateList);
            localDate = localDate.minusMonths(1L);
        }
        return stateList;
    }

    @Override
    public List<BestSaleDTO> listBestSale() {
        List<BestSaleDTO> bestSaleList = new ArrayList<>();
        LocalDate monthStart = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate monthEnd = LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());
        List<Sale> sales = saleRepository.selectAll();
        for(Sale sale: sales) {
            List<SaleOrder> saleOrders = saleOrderRepository.selectByCondition(
                    Condition.builder(SaleOrder.class)
                            .andWhere(
                                    Sqls.custom()
                                            .andEqualTo(SaleOrder.FIELD_SALE_ID, sale.getSaleId())
                                            .andBetween(SaleOrder.FIELD_SALE_ORDER_DATE, monthStart, monthEnd)
                            )
                            .build()
            );
            BigDecimal salePrice = PriceComputeUtils.computeSaleOrderPrice(saleOrders);
            bestSaleList.add(new BestSaleDTO().setSaleName(sale.getSaleName()).setSaleOrderSumPrice(salePrice));
        }

        bestSaleList.sort((o1, o2) -> o1.getSaleOrderSumPrice().divide(o2.getSaleOrderSumPrice(), 10, BigDecimal.ROUND_HALF_DOWN).longValue() > 0 ? 0 : 1);
        return bestSaleList;
    }

    @Override
    public List<BackLogDTO> listBacklog() {
        List<BackLogDTO> backLogList = new ArrayList<>();
        List<SaleOrder> awaitSaleOrders = saleOrderRepository.select(SaleOrder.FIELD_SALE_ORDER_STATE, 1);
        List<PurchaseOrder> awaitPurchaseOrders = purchaseOrderRepository.select(PurchaseOrder.FIELD_PURCHASE_ORDER_STATE, 1);

        for(PurchaseOrder purchaseOrder: awaitPurchaseOrders) {
            Purchase purchase = purchaseRepository.select(Purchase.FIELD_PURCHASE_ID, purchaseOrder.getPurchaseId()).get(0);

            String residenceTime = "";
            LocalDateTime updateTime = LocalDateTime.ofInstant(purchaseOrder.getLastUpdateDate().toInstant(), ZoneId.systemDefault());
            LocalDateTime localDateTime = LocalDateTime.now();
            Long duration = Duration.between(updateTime, localDateTime).toHours();
            if(duration >= 24) {
                residenceTime = Duration.between(updateTime, localDateTime).toDays() + "天";
            } else {
                residenceTime = duration + "小时";
            }

            BackLogDTO  backLog = new BackLogDTO();
            backLog.setProcessName("采购单审批")
                    .setProcessDescription("审批已提交的采购订单")
                    .setApplicantName(purchase.getPurchaseName())
                    .setResidenceTime(residenceTime);

            backLogList.add(backLog);
        }

        for(SaleOrder saleOrder: awaitSaleOrders) {
            Sale sale = saleRepository.select(Sale.FIELD_SALE_ID, saleOrder.getSaleOrderId()).get(0);

            String residenceTime = "";
            LocalDateTime updateTime = LocalDateTime.ofInstant(saleOrder.getLastUpdateDate().toInstant(), ZoneId.systemDefault());
            LocalDateTime localDateTime = LocalDateTime.now();
            Long duration = Duration.between(updateTime, localDateTime).toHours();
            if(duration >= 24) {
                residenceTime = Duration.between(updateTime, localDateTime).toDays() + "天";
            } else {
                residenceTime = duration + "小时";
            }

            BackLogDTO  backLog = new BackLogDTO();
            backLog.setProcessName("销售单审批")
                    .setProcessDescription("审批已提交的销售订单")
                    .setApplicantName(sale.getSaleName())
                    .setResidenceTime(residenceTime);

            backLogList.add(backLog);
        }
        return backLogList;
    }

    private void getStateList(LocalDate localDate, DateTimeFormatter formatter, List<PurchaseAndSaleStateDTO> stateList) {
        LocalDate monthStart = localDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate monthEnd = localDate.with(TemporalAdjusters.firstDayOfNextMonth());
        BigDecimal purchasePrice = getPurchasePriceByMonthScope(monthStart, monthEnd);
        BigDecimal salePrice = getSalePriceByMonthScope(monthStart, monthEnd);

        stateList.add(new PurchaseAndSaleStateDTO()
                .setName("采购金额")
                .setPrice(purchasePrice)
                .setDate(localDate.format(formatter)));

        stateList.add(new PurchaseAndSaleStateDTO()
                .setName("销售金额")
                .setPrice(salePrice)
                .setDate(localDate.format(formatter)));
    }

    /**
     * 获取近半年某一月份的采购订单金额
     * @param monthStart
     * @param monthEnd
     * @return
     */
    private BigDecimal getPurchasePriceByMonthScope(LocalDate monthStart, LocalDate monthEnd) {
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.selectByCondition(
                Condition.builder(PurchaseOrder.class).andWhere(
                        Sqls.custom()
                                .andEqualTo(PurchaseOrder.FIELD_PURCHASE_ORDER_STATE, 2)
                                .andBetween(PurchaseOrder.FIELD_PURCHASE_ORDER_DATE, monthStart, monthEnd)
                ).build()
        );

        return PriceComputeUtils.computePurchaseOrderPrice(purchaseOrders);
    }

    /**
     * 获取近半年某一月份的销售订单金额
     * @param monthStart
     * @param monthEnd
     * @return
     */
    private BigDecimal getSalePriceByMonthScope(LocalDate monthStart, LocalDate monthEnd) {
        List<SaleOrder> saleOrders = saleOrderRepository.selectByCondition(
                Condition.builder(SaleOrder.class).andWhere(
                        Sqls.custom()
                                .andEqualTo(SaleOrder.FIELD_SALE_ORDER_STATE, 2)
                                .andBetween(SaleOrder.FIELD_SALE_ORDER_DATE, monthStart, monthEnd)
                ).build()
        );

        return PriceComputeUtils.computeSaleOrderPrice(saleOrders);
    }

    /**
     * 获取今日采购金额
     * @return
     */
    private BigDecimal getTodayPurchasePrice() {
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now().plusDays(1L), LocalTime.MIN);
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.selectByCondition(
                Condition.builder(PurchaseOrder.class).andWhere(
                        Sqls.custom()
                                .andEqualTo(PurchaseOrder.FIELD_PURCHASE_ORDER_STATE, 2)
                                .andBetween(PurchaseOrder.FIELD_PURCHASE_ORDER_DATE, todayStart, todayEnd)
                ).build()
        );

        return PriceComputeUtils.computePurchaseOrderPrice(purchaseOrders);
    }

    /**
     * 获取本月采购金额
     * @return
     */
    private BigDecimal getMonthPurchasePrice() {
        LocalDate monthStart = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate monthEnd = LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.selectByCondition(
                Condition.builder(PurchaseOrder.class).andWhere(
                        Sqls.custom()
                                .andEqualTo(PurchaseOrder.FIELD_PURCHASE_ORDER_STATE, 2)
                                .andBetween(PurchaseOrder.FIELD_PURCHASE_ORDER_DATE, monthStart, monthEnd)
                ).build()
        );

        return PriceComputeUtils.computePurchaseOrderPrice(purchaseOrders);
    }

    /**
     * 获取本年采购金额
     * @return
     */
    private BigDecimal getYearPurchasePrice() {
        LocalDate yearStart = LocalDate.now().with(TemporalAdjusters.firstDayOfYear());
        LocalDate yearEnd = LocalDate.now().with(TemporalAdjusters.firstDayOfNextYear());
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.selectByCondition(
                Condition.builder(PurchaseOrder.class).andWhere(
                        Sqls.custom()
                                .andEqualTo(PurchaseOrder.FIELD_PURCHASE_ORDER_STATE, 2)
                                .andBetween(PurchaseOrder.FIELD_PURCHASE_ORDER_DATE, yearStart, yearEnd)
                ).build()
        );

        return PriceComputeUtils.computePurchaseOrderPrice(purchaseOrders);
    }

    /**
     * 获取今日销售金额
     * @return
     */
    private BigDecimal getTodaySalePrice() {
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now().plusDays(1L), LocalTime.MIN);
        List<SaleOrder> saleOrders = saleOrderRepository.selectByCondition(
                Condition.builder(SaleOrder.class).andWhere(
                        Sqls.custom()
                                .andEqualTo(SaleOrder.FIELD_SALE_ORDER_STATE, 2)
                                .andBetween(SaleOrder.FIELD_SALE_ORDER_DATE, todayStart, todayEnd)
                ).build()
        );

        return PriceComputeUtils.computeSaleOrderPrice(saleOrders);
    }

    /**
     * 获取本月销售金额
     * @return
     */
    private BigDecimal getMonthSalePrice() {
        LocalDate monthStart = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate monthEnd = LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());
        List<SaleOrder> saleOrders = saleOrderRepository.selectByCondition(
                Condition.builder(SaleOrder.class).andWhere(
                        Sqls.custom()
                                .andEqualTo(SaleOrder.FIELD_SALE_ORDER_STATE, 2)
                                .andBetween(SaleOrder.FIELD_SALE_ORDER_DATE, monthStart, monthEnd)
                ).build()
        );

        return PriceComputeUtils.computeSaleOrderPrice(saleOrders);
    }

    /**
     * 获取本年销售金额
     * @return
     */
    private BigDecimal getYearSalePrice() {
        LocalDate yearStart = LocalDate.now().with(TemporalAdjusters.firstDayOfYear());
        LocalDate yearEnd = LocalDate.now().with(TemporalAdjusters.firstDayOfNextYear());
        List<SaleOrder> saleOrders = saleOrderRepository.selectByCondition(
                Condition.builder(SaleOrder.class).andWhere(
                        Sqls.custom()
                                .andEqualTo(SaleOrder.FIELD_SALE_ORDER_STATE, 2)
                                .andBetween(SaleOrder.FIELD_SALE_ORDER_DATE, yearStart, yearEnd)
                ).build()
        );

        return PriceComputeUtils.computeSaleOrderPrice(saleOrders);
    }
}
