package org.hzero.service.api.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 明星销售员dto
 *
 * @Author : SunYuji
 * @create 2022/8/23 15:51
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BestSaleDTO {
    private String saleName;
    private BigDecimal saleOrderSumPrice;

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public BigDecimal getSaleOrderSumPrice() {
        return saleOrderSumPrice;
    }

    public void setSaleOrderSumPrice(BigDecimal saleOrderSumPrice) {
        this.saleOrderSumPrice = saleOrderSumPrice;
    }
}
