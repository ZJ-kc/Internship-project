package org.hzero.service.api.controller.v1;

import java.util.List;

import io.swagger.annotations.Api;
import org.hzero.core.base.BaseController;
import org.hzero.service.app.service.PurchaseInfoService;
import org.hzero.service.config.SwaggerApiConfig;
import org.hzero.service.domain.entity.PurchaseInfo;
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
@Api(SwaggerApiConfig.PURCHASE_INFO)
@RestController("purchaseInfoController.v1" )
@RequestMapping("/v1/{organizationId}/purchase-info" )
public class PurchaseInfoController extends BaseController {

    private final PurchaseInfoService purchaseInfoService;
    @Autowired
    public PurchaseInfoController(PurchaseInfoService purchaseInfoService) {
        this.purchaseInfoService = purchaseInfoService;
    }

    @ApiOperation(value = "根据订单id查询订单详情数据")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/purchase-order-details/by-order-id")
    public ResponseEntity<List<PurchaseInfo>> getPurchaseOrderDetailsByOrderId(@PathVariable("organizationId") Long organizationId,
                                                                               @RequestParam Long purchaseOrderId) {
        return purchaseInfoService.getPurchaseOrderDetailsByOrderId(organizationId, purchaseOrderId);
    }

    @ApiOperation(value = "物料入库")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PutMapping("/put-storage")
    public ResponseEntity<?> putStorage(@PathVariable("organizationId") Long organizationId,
                                        Long purchaseOrderId, Long[] purchaseInfoIds) {
        return purchaseInfoService.putStorage(organizationId, purchaseOrderId, purchaseInfoIds);
    }






//    @ApiOperation(value = "采购订单详情")
//    @Permission(level = ResourceLevel.ORGANIZATION, permissionPublic = true)
//    @GetMapping("/order/details")
//    public ResponseEntity<PurchaseOrder> getOrderDetails(@PathVariable("organizationId") Long organizationId,
//                                                         @RequestParam Integer purchaseOrderId) {
//        PurchaseOrder purchaseOrder = purchaseInfoService.getDetailsByOrderId(organizationId, purchaseOrderId);
//        return Results.success(purchaseOrder);
//    }

//    @Permission(level = ResourceLevel.ORGANIZATION, permissionPublic = true)
//    @GetMapping("/test")
//    public ResponseEntity<PurchaseOrder> test(@PathVariable("organizationId") Long organizationId, @RequestParam Integer purchaseOrderId) {
//        PurchaseOrder purchaseOrder = purchaseInfoService.getDetailsByOrderId(organizationId, purchaseOrderId);
//        System.out.println(purchaseOrder);
////        return "organizationId="+organizationId+", purchaseOrderId="+purchaseOrderId;
//        return Results.success(purchaseOrder);
//    }

}
