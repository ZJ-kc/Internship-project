package org.hzero.service.api.controller.v1;

import java.time.LocalDate;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.annotation.SortDefault;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.pagehelper.domain.Sort;
import io.choerodon.swagger.annotation.Permission;

import org.hzero.core.util.Results;
import org.hzero.service.app.service.SaleOrderCheckService;
import org.hzero.service.config.SwaggerApiConfig;
import org.hzero.service.domain.entity.SaleInfo;
import org.hzero.service.domain.entity.SaleOrder;

/**
 * 销售经理审核接口
 * @Author: zj
 * @Date: 2022/8/22
 * @Time: 0:29
 */
@Api(SwaggerApiConfig.PURCHASE_ORDER)
@RestController("saleOrderCheckController.v1" )
@RequestMapping("/v1/{organizationId}/sale-order-check" )
public class SaleOrderCheckController {

    private final SaleOrderCheckService saleOrderCheckService;


    @Autowired
    public SaleOrderCheckController(SaleOrderCheckService saleOrderCheckService) {
        this.saleOrderCheckService = saleOrderCheckService;
    }

    @ApiOperation(value = "分页查询销售订单")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionPublic = true)
    @GetMapping("/paging")
    public ResponseEntity<Page<SaleOrder>> list(@PathVariable("organizationId") Long organizationId,
                                                String keyword,
                                                SaleOrder saleOrder,
                                                @ApiIgnore @SortDefault(value = SaleOrder.FIELD_SALE_ORDER_DATE,
                                                            direction = Sort.Direction.DESC) PageRequest pageRequest,
                                                LocalDate beginDate, LocalDate endDate) {
        Page<SaleOrder> list = saleOrderCheckService.list(organizationId, keyword, saleOrder, pageRequest, beginDate, endDate);
        return Results.success(list);
    }

    @ApiOperation(value = "通过id查询销售订单")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionPublic = true)
    @GetMapping("/{saleOrderId}")
    public ResponseEntity<SaleOrder> getSaleOrderByOrderId(@PathVariable("organizationId") Long organizationId,
                                                                   @PathVariable Long saleOrderId) {
        return saleOrderCheckService.getSaleOrderByOrderId(organizationId, saleOrderId);
    }

    @ApiOperation(value = "根据订单id查询订单详情数据")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionPublic = true)
    @GetMapping("/sale-order-details/by-order-id")
    public ResponseEntity<List<SaleInfo>> getSaleOrderDetailsByOrderId(@PathVariable("organizationId") Long organizationId,
                                                                       @RequestParam Long saleOrderId) {
        return saleOrderCheckService.getSaleOrderDetailsByOrderId(organizationId, saleOrderId);
    }

    @ApiOperation(value = "审核通过")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PutMapping("/check-order/agree")
    public ResponseEntity<?> checkOrderAgree(@PathVariable("organizationId") Long organizationId,
                                             Long saleOrderId) {
        return saleOrderCheckService.checkOrderAgree(organizationId, saleOrderId);
    }

    @ApiOperation(value = "审核拒绝")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PutMapping("/check-order/reject")
    public ResponseEntity<?> checkOrderReject(@PathVariable("organizationId") Long organizationId,
                                              Long saleOrderId) {
        return saleOrderCheckService.checkOrderReject(organizationId, saleOrderId);
    }

}
