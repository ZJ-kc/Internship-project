package org.hzero.service.api.controller.v1;

import java.util.List;

import io.swagger.annotations.Api;
import org.hzero.core.base.BaseController;
import org.hzero.service.app.service.SaleInfoService;
import org.hzero.service.config.SwaggerApiConfig;
import org.hzero.service.domain.entity.SaleInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;

import io.swagger.annotations.ApiOperation;

/**
 *  管理 API
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
@Api(SwaggerApiConfig.SALE_INFO)
@RestController("saleInfoController.v1" )
@RequestMapping("/v1/{organizationId}/sale-info" )
public class SaleInfoController extends BaseController {

    private final SaleInfoService saleInfoService;
    @Autowired
    public SaleInfoController(SaleInfoService saleInfoService) {
        this.saleInfoService = saleInfoService;
    }

    @ApiOperation(value = "根据订单id查询订单详情数据")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/sale-order-details/by-order-id")
    public ResponseEntity<List<SaleInfo>> getSaleOrderDetailsByOrderId(@PathVariable("organizationId") Long organizationId,
                                                                       @RequestParam Long saleOrderId) {
        return saleInfoService.getSaleOrderDetailsByOrderId(organizationId, saleOrderId);
    }

    @ApiOperation(value = "物料发货/出库")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PutMapping("/out-storage")
    public ResponseEntity<?> outStorage(@PathVariable("organizationId") Long organizationId,
                                        Long saleOrderId,Long[] saleInfoIds) {
        return saleInfoService.outStorage(organizationId, saleOrderId,saleInfoIds);
    }

}
