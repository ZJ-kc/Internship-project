package org.hzero.service.app.service.impl;

import java.util.List;

import org.hzero.core.base.BaseAppService;
import org.hzero.service.api.dto.MoneyBoardDTO;
import org.hzero.service.app.service.PurchaseAndSaleService;
import org.hzero.service.domain.repository.PurchaseRepository;
import org.hzero.service.domain.repository.SaleRepository;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : SunYuji
 * @create 2022/8/23 16:37
 */
@Service
public class PurchaseAndSaleServiceImpl extends BaseAppService implements PurchaseAndSaleService {

    private final PurchaseRepository purchaseRepository;
    private final SaleRepository saleRepository;

    public PurchaseAndSaleServiceImpl(PurchaseRepository purchaseRepository,
                                      SaleRepository saleRepository) {
        this.purchaseRepository = purchaseRepository;
        this.saleRepository = saleRepository;
    }

    @Override
    public MoneyBoardDTO getMoneyBoard() {

        return null;
    }
}
