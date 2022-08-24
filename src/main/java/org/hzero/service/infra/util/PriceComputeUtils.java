package org.hzero.service.infra.util;

import java.math.BigDecimal;
import java.util.List;

import org.hzero.service.domain.entity.Purchase;
import org.hzero.service.domain.entity.PurchaseOrder;
import org.hzero.service.domain.entity.Sale;
import org.hzero.service.domain.entity.SaleOrder;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : SunYuji
 * @create 2022/8/23 18:56
 */
public class PriceComputeUtils {
    public static BigDecimal computePurchaseOrderPrice(List<PurchaseOrder> orders) {
        BigDecimal sumPrice = new BigDecimal(0);
        for(PurchaseOrder order: orders) {
            sumPrice = sumPrice.add(order.getPurchaseOrderSumPrice());
        }
        return sumPrice;
    }

    public static BigDecimal computeSaleOrderPrice(List<SaleOrder> orders) {
        BigDecimal sumPrice = new BigDecimal(0);
        for(SaleOrder order: orders) {
            sumPrice = sumPrice.add(order.getSaleOrderSumPrice());
        }
        return sumPrice;
    }
}