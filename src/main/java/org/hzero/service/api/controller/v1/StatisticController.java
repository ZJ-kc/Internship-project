package org.hzero.service.api.controller.v1;

import java.util.List;

import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hzero.core.util.Results;
import org.hzero.service.api.dto.MoneyBoardDTO;
import org.hzero.service.app.service.PurchaseAndSaleService;
import org.hzero.service.config.SwaggerApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 统计数据
 * @Author : SunYuji
 * @create 2022/8/23 14:08
 */
@Api(SwaggerApiConfig.COMPANY)
@RestController("StatisticController.v1" )
@RequestMapping("/v1/{organizationId}/statistic" )
public class StatisticController {

    private final PurchaseAndSaleService purchaseAndSaleService;

    @Autowired
    public StatisticController(PurchaseAndSaleService purchaseAndSaleService) {
        this.purchaseAndSaleService = purchaseAndSaleService;
    }

    @ApiOperation(value = "查询采购、销售金额")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionPublic = true)
    @GetMapping("/money-board/list")
    public ResponseEntity<MoneyBoardDTO> moneyBoardList() {
        return Results.success(purchaseAndSaleService.getMoneyBoard());
    }
}
