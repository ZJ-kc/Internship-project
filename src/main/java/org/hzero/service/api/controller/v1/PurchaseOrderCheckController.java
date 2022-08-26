package org.hzero.service.api.controller.v1;

import java.time.LocalDate;
import java.util.List;

import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.annotation.SortDefault;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.pagehelper.domain.Sort;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hzero.core.util.Results;
import org.hzero.service.app.service.PurchaseInfoService;
import org.hzero.service.app.service.PurchaseOrderCheckService;
import org.hzero.service.app.service.PurchaseOrderService;
import org.hzero.service.config.SwaggerApiConfig;
import org.hzero.service.domain.entity.PurchaseInfo;
import org.hzero.service.domain.entity.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 采购经理审核接口
 *
 * @Author : SunYuji
 * @create 2022/8/21 18:30
 */
@Api(SwaggerApiConfig.PURCHASE_ORDER)
@RestController("purchaseOrderCheckController.v1" )
@RequestMapping("/v1/{organizationId}/purchase-order-check" )
public class PurchaseOrderCheckController {

    private final PurchaseOrderCheckService purchaseOrderCheckService;

    @Autowired
    public PurchaseOrderCheckController(PurchaseOrderCheckService purchaseOrderCheckService) {
        this.purchaseOrderCheckService = purchaseOrderCheckService;
    }

    @ApiOperation(value = "分页查询采购订单")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/paging")
    public ResponseEntity<Page<PurchaseOrder>> list(@PathVariable("organizationId") Long organizationId,
                                                    String keyword,
                                                    PurchaseOrder purchaseOrder,
                                                    @ApiIgnore @SortDefault(value = PurchaseOrder.FIELD_PURCHASE_ORDER_DATE,
                                                            direction = Sort.Direction.DESC) PageRequest pageRequest,
                                                    LocalDate beginDate, LocalDate endDate) {
        Page<PurchaseOrder> list = purchaseOrderCheckService.list(organizationId, keyword, purchaseOrder, pageRequest, beginDate, endDate);
        return Results.success(list);
    }

    @ApiOperation(value = "通过id查询采购订单")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/{purchaseOrderId}")
    public ResponseEntity<PurchaseOrder> getPurchaseOrderByOrderId(@PathVariable("organizationId") Long organizationId,
                                                                   @PathVariable Long purchaseOrderId) {
        return purchaseOrderCheckService.getPurchaseOrderByOrderId(organizationId, purchaseOrderId);
    }

    @ApiOperation(value = "根据订单id查询订单详情数据")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/purchase-order-details/by-order-id")
    public ResponseEntity<List<PurchaseInfo>> getPurchaseOrderDetailsByOrderId(@PathVariable("organizationId") Long organizationId,
                                                                               @RequestParam Long purchaseOrderId) {
        return purchaseOrderCheckService.getPurchaseOrderDetailsByOrderId(organizationId, purchaseOrderId);
    }

    @ApiOperation(value = "审核通过")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PutMapping("/check-order/agree")
    public ResponseEntity<?> checkOrderAgree(@PathVariable("organizationId") Long organizationId,
                                              Long purchaseOrderId) {
        return purchaseOrderCheckService.checkOrderAgree(organizationId, purchaseOrderId);
    }

    @ApiOperation(value = "审核拒绝")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PutMapping("/check-order/reject")
    public ResponseEntity<?> checkOrderReject(@PathVariable("organizationId") Long organizationId,
                                             Long purchaseOrderId) {
        return purchaseOrderCheckService.checkOrderReject(organizationId, purchaseOrderId);
    }
}
