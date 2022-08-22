package org.hzero.service.api.controller.v1;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.Api;
import org.hzero.core.util.Results;
import org.hzero.core.base.BaseController;
import org.hzero.excel.helper.ExcelHelper;
import org.hzero.export.annotation.ExcelExport;
import org.hzero.export.vo.ExportParam;
import org.hzero.service.api.dto.PurchaseOrderDTO;
import org.hzero.service.app.service.PurchaseOrderService;
import org.hzero.service.config.SwaggerApiConfig;
import org.hzero.service.domain.entity.PurchaseInfo;
import org.hzero.service.domain.entity.PurchaseOrder;
import org.hzero.service.infra.listener.PurchaseOrderExcelListener;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.annotation.SortDefault;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.pagehelper.domain.Sort;
import io.choerodon.swagger.annotation.Permission;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

/**
 *  管理 API
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
@Api(SwaggerApiConfig.PURCHASE_ORDER)
@RestController("purchaseOrderController.v1" )
@RequestMapping("/v1/{organizationId}/purchase-order" )
public class PurchaseOrderController extends BaseController {

    private final PurchaseOrderService purchaseOrderService;
    @Autowired
    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @ApiOperation(value = "分页查询采购订单")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionPublic = true)
    @GetMapping("/paging")
    public ResponseEntity<Page<PurchaseOrder>> list(@PathVariable("organizationId") Long organizationId,
                                                    PurchaseOrder purchaseOrder,
                                                    @ApiIgnore @SortDefault(value = PurchaseOrder.FIELD_PURCHASE_ORDER_DATE,
                                                    direction = Sort.Direction.DESC) PageRequest pageRequest,
                                                    LocalDate beginDate, LocalDate endDate) {
        Page<PurchaseOrder> list = purchaseOrderService.list(organizationId, purchaseOrder, pageRequest, beginDate, endDate);
        return Results.success(list);
    }

    @ApiOperation(value = "通过id查询采购订单")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionPublic = true)
    @GetMapping("/{purchaseOrderId}")
    public ResponseEntity<PurchaseOrder> getPurchaseOrderByOrderId(@PathVariable("organizationId") Long organizationId,
                                                                   @PathVariable Long purchaseOrderId) {
        return purchaseOrderService.getPurchaseOrderByOrderId(organizationId, purchaseOrderId);
    }

    @ApiOperation(value = "保存采购订单")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionPublic = true)
    @PostMapping("/add")
    public ResponseEntity<PurchaseOrder> addPurchaseOrder(@PathVariable("organizationId") Long organizationId,
                                                          @RequestBody PurchaseOrder purchaseOrder) {
        return purchaseOrderService.addPurchaseOrder(organizationId, purchaseOrder);
    }

    @ApiOperation(value = "提交采购订单")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionPublic = true)
    @PutMapping("/update-order-state")
    public ResponseEntity<?> updateOrderState(@PathVariable("organizationId") Long organizationId,
                                              Long purchaseOrderId) {
        return purchaseOrderService.updateOrderState(organizationId, purchaseOrderId);
    }

    @ApiOperation(value = "删除采购订单")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionPublic = true)
    @DeleteMapping("/{purchaseOrderId}")
    public ResponseEntity<?> deletePurchaseOrder(@PathVariable("organizationId") Long organizationId,
                                                 @PathVariable("purchaseOrderId") Long purchaseOrderId) {
        return purchaseOrderService.deletePurchaseOrder(organizationId, purchaseOrderId);
    }

    // TODO 导出失败
    @ApiOperation(value = "导出采购订单")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionPublic = true)
    @GetMapping("/export")
    @ExcelExport(PurchaseOrderDTO.class)
    public ResponseEntity<List<PurchaseOrderDTO>> export(Integer[] purchaseOrderIds, ExportParam exportParam,
                                                         HttpServletResponse response) {
        if(null == purchaseOrderIds) {
            return Results.error();
        }
        return Results.success(purchaseOrderService.exportPurchaseOrder(purchaseOrderIds));
    }

    @ApiOperation(value = "导入采购订单")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionPublic = true)
    @PostMapping("/import")
    public ResponseEntity<?> importPurchaseOrder(MultipartFile file) {
        return purchaseOrderService.importPurchaseOrder(file);
    }
}
