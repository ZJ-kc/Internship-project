package org.hzero.service.app.service;

import java.util.List;

import org.hzero.service.api.dto.BackLogDTO;
import org.hzero.service.api.dto.BestSaleDTO;
import org.hzero.service.api.dto.MoneyBoardDTO;
import org.hzero.service.api.dto.PurchaseAndSaleStateDTO;

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

    /**
     * 查询近半年来采购销售情况
     * @return
     */
    List<PurchaseAndSaleStateDTO> listPurchaseSaleState();

    /**
     * 本月明星销售成员
     * @return
     */
    List<BestSaleDTO> listBestSale();

    /**
     * 待办事项
     * @return
     */
    List<BackLogDTO> listBacklog();
}
