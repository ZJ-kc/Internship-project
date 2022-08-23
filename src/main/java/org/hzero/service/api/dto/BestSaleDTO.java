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

    public BestSaleDTO setSaleName(String saleName) {
        this.saleName = saleName;
        return this;
    }

    public BigDecimal getSaleOrderSumPrice() {
        return saleOrderSumPrice;
    }

    public BestSaleDTO setSaleOrderSumPrice(BigDecimal saleOrderSumPrice) {
        this.saleOrderSumPrice = saleOrderSumPrice;
        return this;
    }

    @Override
    public String toString() {
        return "BestSaleDTO{" +
                "saleName='" + saleName + '\'' +
                ", saleOrderSumPrice=" + saleOrderSumPrice +
                '}';
    }
}
