package org.hzero.service.app.service;

import java.util.List;

import org.hzero.service.api.dto.MoneyBoardDTO;

/**
 * 采购、销售订单金额看板
 *
 * @Author : SunYuji
 * @create 2022/8/23 16:36
 */
public interface PurchaseAndSaleService {
    /**
     * 查询采购、销售金额
     * @return
     */
    MoneyBoardDTO getMoneyBoard();
}
