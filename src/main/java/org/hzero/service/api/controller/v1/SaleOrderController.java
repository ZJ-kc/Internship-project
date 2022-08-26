package org.hzero.service.api.controller.v1;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import org.hzero.core.util.Results;
import org.hzero.core.base.BaseController;
import org.hzero.export.annotation.ExcelExport;
import org.hzero.export.vo.ExportParam;
import org.hzero.service.api.dto.SaleOrderDTO;
import org.hzero.service.app.service.SaleOrderService;
import org.hzero.service.config.SwaggerApiConfig;
import org.hzero.service.domain.entity.SaleOrder;

import org.dom4j.DocumentException;
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
@Api(SwaggerApiConfig.SALE_ORDER)
@RestController("saleOrderController.v1" )
@RequestMapping("/v1/{organizationId}/sale-order" )
public class SaleOrderController extends BaseController {

    private final SaleOrderService saleOrderService;
    @Autowired
    public SaleOrderController(SaleOrderService saleOrderService) {
        this.saleOrderService = saleOrderService;
    }

    @ApiOperation(value = "分页查询销售订单")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/paging")
    public ResponseEntity<Page<SaleOrder>> list(@PathVariable("organizationId") Long organizationId,
                                                String keyword,
                                                SaleOrder saleOrder,
                                                @ApiIgnore @SortDefault(value = SaleOrder.FIELD_SALE_ORDER_DATE,
                                                direction = Sort.Direction.DESC) PageRequest pageRequest,
                                                LocalDate beginDate, LocalDate endDate) {
        Page<SaleOrder> list = saleOrderService.list(organizationId, keyword, saleOrder, pageRequest, beginDate, endDate);
        return Results.success(list);
    }

    @ApiOperation(value = "通过id查询销售订单")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/{saleOrderId}")
    public ResponseEntity<SaleOrder> getSaleOrderByOrderId(@PathVariable("organizationId") Long organizationId,
                                                           @PathVariable Long saleOrderId) {
        return saleOrderService.getSaleOrderByOrderId(organizationId, saleOrderId);
    }

    @ApiOperation(value = "保存销售订单")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PostMapping("/add")
    public ResponseEntity<SaleOrder> addSaleOrder(@PathVariable("organizationId") Long organizationId,
                                                  @RequestBody SaleOrder saleOrder) {
        return saleOrderService.addSaleOrder(organizationId, saleOrder);
    }

    @ApiOperation(value = "提交销售订单")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PutMapping("/update-order-state")
    public ResponseEntity<?> updateOrderState(@PathVariable("organizationId") Long organizationId,
                                              Long saleOrderId) {
        return saleOrderService.updateOrderState(organizationId, saleOrderId);
    }

    @ApiOperation(value = "删除销售订单")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @DeleteMapping("/{saleOrderId}")
    public ResponseEntity<?> deleteSaleOrder(@PathVariable("organizationId") Long organizationId,
                                             @PathVariable("saleOrderId") Long saleOrderId) {
        return saleOrderService.deleteSaleOrder(organizationId, saleOrderId);
    }

    @ApiOperation(value = "导出销售订单")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionPublic = true)
    @GetMapping(value = "/export")
    @ExcelExport(SaleOrderDTO.class)
    public ResponseEntity<List<SaleOrderDTO>> export(Integer[] saleOrderIds, ExportParam exportParam, HttpServletResponse response) {
        List<SaleOrderDTO> orderList = saleOrderService.exportSaleOrder(saleOrderIds);
        return Results.success(orderList);
    }

    @ApiOperation(value = "导入销售订单")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionPublic = true)
    @PostMapping("/import")
    public ResponseEntity<?> importSaleOrder(MultipartFile file) {
        return saleOrderService.importSaleOrder(file);
    }


    @ApiOperation(value = "打印销售订单")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionPublic = true)
    @GetMapping("/export/pdf")
    public void exportSalePdf(@PathVariable("organizationId") Long organizationId,
                                  Long saleOrderId, HttpServletResponse response) throws IOException, DocumentException, com.itextpdf.text.DocumentException {

        //设置响应格式等
        response.setContentType("application/pdf");
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setHeader("Content-Disposition", "attachment; filename=".concat(String.valueOf(URLEncoder.encode( "销售订单.pdf", "UTF-8"))));
        saleOrderService.exportSalePdf(organizationId, saleOrderId, response);
    }

}
