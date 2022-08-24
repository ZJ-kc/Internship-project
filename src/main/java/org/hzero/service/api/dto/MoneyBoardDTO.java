package org.hzero.service.api.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 采购销售金额看板
 *
 * @Author : SunYuji
 * @create 2022/8/23 15:47
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MoneyBoardDTO {
    private BigDecimal purchaseOrderDay;
    private BigDecimal purchaseOrderMonth;
    private BigDecimal purchaseOrderYear;
    private BigDecimal saleOrderDay;
    private BigDecimal saleOrderMonth;
    private BigDecimal saleOrderYear;

    public BigDecimal getPurchaseOrderDay() {
        return purchaseOrderDay;
    }

    public MoneyBoardDTO setPurchaseOrderDay(BigDecimal purchaseOrderDay) {
        this.purchaseOrderDay = purchaseOrderDay;
        return this;
    }

    public BigDecimal getPurchaseOrderMonth() {
        return purchaseOrderMonth;
    }

    public MoneyBoardDTO setPurchaseOrderMonth(BigDecimal purchaseOrderMonth) {
        this.purchaseOrderMonth = purchaseOrderMonth;
        return this;
    }

    public BigDecimal getPurchaseOrderYear() {
        return purchaseOrderYear;
    }

    public MoneyBoardDTO setPurchaseOrderYear(BigDecimal purchaseOrderYear) {
        this.purchaseOrderYear = purchaseOrderYear;
        return this;
    }

    public BigDecimal getSaleOrderDay() {
        return saleOrderDay;
    }

    public MoneyBoardDTO setSaleOrderDay(BigDecimal saleOrderDay) {
        this.saleOrderDay = saleOrderDay;
        return this;
    }

    public BigDecimal getSaleOrderMonth() {
        return saleOrderMonth;
    }

    public MoneyBoardDTO setSaleOrderMonth(BigDecimal saleOrderMonth) {
        this.saleOrderMonth = saleOrderMonth;
        return this;
    }

    public BigDecimal getSaleOrderYear() {
        return saleOrderYear;
    }

    public MoneyBoardDTO setSaleOrderYear(BigDecimal saleOrderYear) {
        this.saleOrderYear = saleOrderYear;
        return this;
    }

    @Override
    public String toString() {
        return "MoneyBoardDTO{" +
                "purchaseOrderDay=" + purchaseOrderDay +
                ", purchaseOrderMonth=" + purchaseOrderMonth +
                ", purchaseOrderYear=" + purchaseOrderYear +
                ", saleOrderDay=" + saleOrderDay +
                ", saleOrderMonth=" + saleOrderMonth +
                ", saleOrderYear=" + saleOrderYear +
                '}';
    }
}
