package org.hzero.service.api.controller.v1;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import io.swagger.annotations.Api;
import org.hzero.core.util.Results;
import org.hzero.core.base.BaseController;
import org.hzero.export.annotation.ExcelExport;
import org.hzero.export.vo.ExportParam;
import org.hzero.service.api.dto.PurchaseOrderDTO;
import org.hzero.service.app.service.PurchaseOrderService;
import org.hzero.service.config.SwaggerApiConfig;
import org.hzero.service.domain.entity.PurchaseOrder;
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

    @ApiOperation(value = "导出采购订单")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionPublic = true)
//    @GetMapping(value = "/export", produces = "application/octet-stream")
    @GetMapping(value = "/export")
    @ExcelExport(value = PurchaseOrderDTO.class, fillType = "single-sheet")
    public ResponseEntity<List<PurchaseOrderDTO>> export(Integer[] purchaseOrderIds,ExportParam exportParam, HttpServletResponse response) {
//        response.setHeader("content-type","application/octet-stream");
        List<PurchaseOrderDTO> orderList = purchaseOrderService.exportPurchaseOrder(purchaseOrderIds);
        return Results.success(orderList);
//        ExportParams params = new ExportParams("采购订单表","采购订单表", ExcelType.HSSF);
//        Workbook workbook = ExcelExportUtil.exportExcel(params, PurchaseOrderDTO.class, orderList);
//        ServletOutputStream outputStream = null;
//        try {
//            response.setHeader("content-type","application/octet-stream");
//            response.setHeader("content-disposition","attachment;filename=" + URLEncoder.encode("采购订单表.xls","UTF-8"));
//            outputStream = response.getOutputStream();
//            workbook.write(outputStream);
//        } catch (Exception e){
////            logger.error("EmployeeController===============>{}",e.getMessage());
//        } finally {
//            if(null!=outputStream) {
//                try {
//                    outputStream.close();
//                } catch (Exception e){
////                    logger.error("EmployeeController===============>{}",e.getMessage());
//                }
//            }
//        }
    }

    @ApiOperation(value = "导入采购订单")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionPublic = true)
    @PostMapping("/import")
    public ResponseEntity<?> importPurchaseOrder(MultipartFile file) {
        return purchaseOrderService.importPurchaseOrder(file);
    }

    @ApiOperation(value = "测试打印")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionPublic = true)
    @GetMapping("/pdf")
    public void testPdf(HttpServletResponse response) throws DocumentException, IOException {
        //设置响应格式等
        response.setContentType("application/pdf");
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        Map<String,Object> map = new HashMap<>();
        //设置纸张规格为A4纸
        Rectangle rect = new Rectangle(PageSize.A4);
        //创建文档实例
        Document doc=new Document(rect);
        //添加中文字体
        BaseFont bfChinese=BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        //设置字体样式
        Font textFont = new Font(bfChinese,11, Font.NORMAL); //正常
        //Font redTextFont = new Font(bfChinese,11,Font.NORMAL,Color.RED); //正常,红色
        Font boldFont = new Font(bfChinese,11,Font.BOLD); //加粗
        //Font redBoldFont = new Font(bfChinese,11,Font.BOLD,Color.RED); //加粗,红色
        Font firsetTitleFont = new Font(bfChinese,22,Font.BOLD); //一级标题
        Font secondTitleFont = new Font(bfChinese,15,Font.BOLD, CMYKColor.BLUE); //二级标题
        Font underlineFont = new Font(bfChinese,11,Font.UNDERLINE); //下划线斜体
        //设置字体
        com.itextpdf.text.Font FontChinese24 = new com.itextpdf.text.Font(bfChinese, 24, com.itextpdf.text.Font.BOLD);
        com.itextpdf.text.Font FontChinese18 = new com.itextpdf.text.Font(bfChinese, 18, com.itextpdf.text.Font.BOLD);
        com.itextpdf.text.Font FontChinese16 = new com.itextpdf.text.Font(bfChinese, 16, com.itextpdf.text.Font.BOLD);
        com.itextpdf.text.Font FontChinese12 = new com.itextpdf.text.Font(bfChinese, 12, com.itextpdf.text.Font.NORMAL);
        com.itextpdf.text.Font FontChinese11Bold = new com.itextpdf.text.Font(bfChinese, 11, com.itextpdf.text.Font.BOLD);
        com.itextpdf.text.Font FontChinese11 = new com.itextpdf.text.Font(bfChinese, 11, com.itextpdf.text.Font.ITALIC);
        com.itextpdf.text.Font FontChinese11Normal = new com.itextpdf.text.Font(bfChinese, 11, com.itextpdf.text.Font.NORMAL);

        //设置要导出的pdf的标题
        String title = "霸道流氓气质";
        response.setHeader("Content-disposition","attachment; filename=".concat(String.valueOf(URLEncoder.encode(title + ".pdf", "UTF-8"))));
        OutputStream out = response.getOutputStream();
        PdfWriter.getInstance(doc,out);
        doc.open();
        doc.newPage();
        //新建段落
        //使用二级标题 颜色为蓝色
        Paragraph p1 = new Paragraph("二级标题", secondTitleFont);
        //设置行高
        p1.setLeading(0);
        //设置标题居中
        p1.setAlignment(Element.ALIGN_CENTER);
        //将段落添加到文档上
        doc.add(p1);
        //设置一个空的段落，行高为18  什么内容都不显示
        Paragraph blankRow1 = new Paragraph(18f, " ", FontChinese11);
        doc.add(blankRow1);
        //新建表格 列数为2
        PdfPTable table1 = new PdfPTable(2);
        //给表格设置宽度
        int width1[] = {80,60};
        table1.setWidths(width1);
        //新建单元格
        String name="霸道的程序猿";
        String gender="男";
        //给单元格赋值 每个单元格为一个段落，每个段落的字体为加粗
        PdfPCell cell11 = new PdfPCell(new Paragraph("姓名：  "+name,boldFont));
        PdfPCell cell12 = new PdfPCell(new Paragraph("性别：  "+gender,boldFont));
        //设置单元格边框为0
        cell11.setBorder(0);
        cell12.setBorder(0);
        table1.addCell(cell11);
        table1.addCell(cell12);
        doc.add(table1);
        PdfPTable table3 = new PdfPTable(2);
        table3.setWidths(width1);
        PdfPCell cell15 = new PdfPCell(new Paragraph("博客主页： https://blog.csdn.net/BADAO_LIUMANG_QIZHI  ",boldFont));
        PdfPCell cell16 = new PdfPCell(new Paragraph("当前时间：  "+new Date().toString(),boldFont));
        cell15.setBorder(0);
        cell16.setBorder(0);
        table3.addCell(cell15);
        table3.addCell(cell16);
        doc.add(table3);
        doc.close();

    }

    @ApiOperation(value = "打印采购订单")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionPublic = true)
    @GetMapping("/export/pdf")
    public void exportPurchasePdf(@PathVariable("organizationId") Long organizationId,
                                  Long purchaseOrderId, HttpServletResponse response) throws IOException, DocumentException {

        //设置响应格式等
        response.setContentType("application/pdf");
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setHeader("Content-Disposition", "attachment; filename=".concat(String.valueOf(URLEncoder.encode( "采购订单.pdf", "UTF-8"))));
        purchaseOrderService.exportPurchasePdf(organizationId, purchaseOrderId, response);
//        OutputStream out = null;
//        try {
//
//            //设置响应格式等
//            response.setContentType("application/pdf");
////            response.setHeader("Expires", "0");
////            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
//            response.setHeader("Content-Disposition", "attachment; filename=" + new String("采购订单.pdf".getBytes(), StandardCharsets.ISO_8859_1));
////            response.setHeader("Pragma", "public");
//
//            purchaseOrderService.exportPurchasePdf(organizationId, purchaseOrderId, response);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if(out != null) {
//                    out.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

}