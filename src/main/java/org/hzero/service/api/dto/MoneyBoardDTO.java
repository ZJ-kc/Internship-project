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

    public void setPurchaseOrderDay(BigDecimal purchaseOrderDay) {
        this.purchaseOrderDay = purchaseOrderDay;
    }

    public BigDecimal getPurchaseOrderMonth() {
        return purchaseOrderMonth;
    }

    public void setPurchaseOrderMonth(BigDecimal purchaseOrderMonth) {
        this.purchaseOrderMonth = purchaseOrderMonth;
    }

    public BigDecimal getPurchaseOrderYear() {
        return purchaseOrderYear;
    }

    public void setPurchaseOrderYear(BigDecimal purchaseOrderYear) {
        this.purchaseOrderYear = purchaseOrderYear;
    }

    public BigDecimal getSaleOrderDay() {
        return saleOrderDay;
    }

    public void setSaleOrderDay(BigDecimal saleOrderDay) {
        this.saleOrderDay = saleOrderDay;
    }

    public BigDecimal getSaleOrderMonth() {
        return saleOrderMonth;
    }

    public void setSaleOrderMonth(BigDecimal saleOrderMonth) {
        this.saleOrderMonth = saleOrderMonth;
    }

    public BigDecimal getSaleOrderYear() {
        return saleOrderYear;
    }

    public void setSaleOrderYear(BigDecimal saleOrderYear) {
        this.saleOrderYear = saleOrderYear;
    }

}
