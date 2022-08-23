package org.hzero.service.api.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : SunYuji
 * @create 2022/8/23 16:01
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PurchaseAndSaleStateDTO {
    private String name;
    private String date;
    private BigDecimal price;

    public String getName() {
        return name;
    }

    public PurchaseAndSaleStateDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDate() {
        return date;
    }

    public PurchaseAndSaleStateDTO setDate(String date) {
        this.date = date;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public PurchaseAndSaleStateDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    @Override
    public String toString() {
        return "PurchaseAndSaleStateDTO{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", price=" + price +
                '}';
    }
}
