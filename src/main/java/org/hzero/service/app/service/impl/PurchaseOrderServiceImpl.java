package org.hzero.service.app.service.impl;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import io.choerodon.mybatis.pagehelper.PageHelper;
import org.hzero.core.base.BaseAppService;

import org.hzero.core.util.Results;
import org.hzero.excel.entity.Column;
import org.hzero.excel.helper.ExcelHelper;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.hzero.service.api.dto.PurchaseOrderDTO;
import org.hzero.service.app.service.PurchaseInfoService;
import org.hzero.service.app.service.PurchaseOrderService;
import org.hzero.service.domain.entity.*;
import org.hzero.service.domain.repository.*;
import org.hzero.service.infra.listener.PurchaseInfoExcelListener;
import org.hzero.service.infra.listener.PurchaseOrderExcelListener;
import org.hzero.service.infra.mapper.PurchaseOrderMapper;
import org.hzero.service.infra.util.PurchaseInfoImportConstantUtils;
import org.hzero.service.infra.util.PurchaseOrderImportConstantUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 * 应用服务默认实现
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
@Service
public class PurchaseOrderServiceImpl extends BaseAppService implements PurchaseOrderService {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderServiceImpl.class);

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderMapper purchaseOrderMapper;
    private final PurchaseInfoRepository purchaseInfoRepository;
    private final MaterialRepository materialRepository;
    private final PurchaseRepository purchaseRepository;
    private final SupplierRepository supplierRepository;
    private final StoreRepository storeRepository;
    private final PurchaseInfoService purchaseInfoService;

    @Autowired
    public PurchaseOrderServiceImpl(PurchaseOrderRepository purchaseOrderRepository,
                                    PurchaseOrderMapper purchaseOrderMapper,
                                    PurchaseInfoRepository purchaseInfoRepository,
                                    RepertoryRepository repertoryRepository,
                                    MaterialRepository materialRepository,
                                    PurchaseRepository purchaseRepository,
                                    SupplierRepository supplierRepository,
                                    StoreRepository storeRepository,
                                    PurchaseInfoService purchaseInfoService) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderMapper = purchaseOrderMapper;
        this.purchaseInfoRepository = purchaseInfoRepository;
        this.materialRepository = materialRepository;
        this.purchaseRepository = purchaseRepository;
        this.supplierRepository = supplierRepository;
        this.storeRepository = storeRepository;
        this.purchaseInfoService = purchaseInfoService;
    }

    @Override
    public Page<PurchaseOrder> list(Long tenantId, String keyword, PurchaseOrder purchaseOrder, PageRequest pageRequest,
                                    LocalDate startDate, LocalDate endDate) {
        return PageHelper.doPageAndSort(pageRequest, () -> purchaseOrderRepository.getPurchaseOrderPage(tenantId, keyword, purchaseOrder, startDate, endDate));
    }

    @Override
    public ResponseEntity<PurchaseOrder> getPurchaseOrderByOrderId(Long tenantId, Long purchaseOrderId) {
        PurchaseOrder purchaseOrder = this.purchaseOrderMapper.getPurchaseOrderByOrderId(tenantId, purchaseOrderId);
        Supplier supplier = supplierRepository.selectByPrimaryKey(purchaseOrder.getSupplierId());
        Purchase purchase = purchaseRepository.selectByPrimaryKey(purchaseOrder.getPurchaseId());
        Store store = storeRepository.selectByPrimaryKey(purchaseOrder.getStoreId());
        purchaseOrder.setStoreAddress(store.getStoreAddress());
        purchaseOrder.setStore(store);
        purchaseOrder.setSupplier(supplier);
        purchaseOrder.setPurchase(purchase);
        return Results.success(purchaseOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<PurchaseOrder> addPurchaseOrder(Long organizationId, PurchaseOrder purchaseOrder) {
        purchaseOrderRepository.addPurchaseOrder(organizationId, purchaseOrder);
        return Results.success(purchaseOrder);
    }

    /**
     * TODO 发送提交信息
     * @param organizationId
     * @param purchaseOrderId
     * @return
     */
    @Override
    public ResponseEntity<?> updateOrderState(Long organizationId, Long purchaseOrderId) {
        PurchaseOrder order = purchaseOrderRepository.selectByPrimaryKey(purchaseOrderId);
        order.setPurchaseOrderState(1);

        int count = purchaseOrderRepository.updateOptional(order, PurchaseOrder.FIELD_PURCHASE_ORDER_STATE);
        if(1 == count) {
            return Results.success("提交成功");
        }
        return Results.error();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deletePurchaseOrder(Long organizationId, Long purchaseOrderId) {
        purchaseInfoRepository.batchDelete(purchaseInfoRepository
                .selectByCondition(Condition.builder(PurchaseInfo.class)
                        .andWhere(Sqls.custom()
                                .andEqualTo(PurchaseOrder.FIELD_PURCHASE_ORDER_ID, purchaseOrderId))
                        .build()));
        int count = purchaseOrderRepository.deleteByPrimaryKey(purchaseOrderId);
        if(1 == count) {
            return Results.success();
        }
        return Results.error();
    }

    @Override
    public List<PurchaseOrderDTO> exportPurchaseOrder(Integer[] purchaseOrderIds) {
        return purchaseOrderRepository.exportPurchaseOrder(purchaseOrderIds);
    }

    @Override
    public ResponseEntity<?> importPurchaseOrder(MultipartFile file) {
        List<Column> purchaseOrderNames = new ArrayList<>();
        List<Column> purchaseInfoNames = new ArrayList<>();

        purchaseOrderNames.add(new Column().setIndex(0).setName(PurchaseOrderImportConstantUtils.FIELD_PURCHASE_ORDER_NUMBER).setColumnType(Column.STRING));
        purchaseOrderNames.add(new Column().setIndex(1).setName(PurchaseOrderImportConstantUtils.FIELD_SUPPLIER_NAME).setColumnType(Column.STRING));
        purchaseOrderNames.add(new Column().setIndex(2).setName(PurchaseOrderImportConstantUtils.FIELD_PURCHASE_ORDER_DATE).setColumnType(Column.DATE).setFormat("yyyy/MM/dd"));
        purchaseOrderNames.add(new Column().setIndex(3).setName(PurchaseOrderImportConstantUtils.FIELD_STORE_NAME).setColumnType(Column.STRING));
        purchaseOrderNames.add(new Column().setIndex(4).setName(PurchaseOrderImportConstantUtils.FIELD_STORE_ADDRESS).setColumnType(Column.STRING));
        purchaseOrderNames.add(new Column().setIndex(5).setName(PurchaseOrderImportConstantUtils.FIELD_PURCHASE_NAME).setColumnType(Column.STRING));
        purchaseOrderNames.add(new Column().setIndex(6).setName(PurchaseOrderImportConstantUtils.FIELD_CURRENCY_SYMBOL).setColumnType(Column.STRING));
        purchaseOrderNames.add(new Column().setIndex(7).setName(PurchaseOrderImportConstantUtils.FIELD_PURCHASE_ORDER_SUM_PRICE).setColumnType(Column.DECIMAL));
        purchaseOrderNames.add(new Column().setIndex(8).setName(PurchaseOrderImportConstantUtils.FIELD_PURCHASE_ORDER_STATE).setColumnType(Column.STRING));

        purchaseInfoNames.add(new Column().setIndex(0).setName(PurchaseInfoImportConstantUtils.FIELD_PURCHASE_ORDER_NUMBER).setColumnType(Column.STRING));
        purchaseInfoNames.add(new Column().setIndex(1).setName(PurchaseInfoImportConstantUtils.FIELD_PURCHASE_INFO_LINE_NUMBER).setColumnType(Column.LONG));
        purchaseInfoNames.add(new Column().setIndex(2).setName(PurchaseInfoImportConstantUtils.FIELD_MATERIAL_CODE).setColumnType(Column.STRING));
        purchaseInfoNames.add(new Column().setIndex(3).setName(PurchaseInfoImportConstantUtils.FIELD_MATERIAL_PRICE).setColumnType(Column.DECIMAL));
        purchaseInfoNames.add(new Column().setIndex(4).setName(PurchaseInfoImportConstantUtils.FIELD_MATERIAL_UNIT).setColumnType(Column.STRING));
        purchaseInfoNames.add(new Column().setIndex(5).setName(PurchaseInfoImportConstantUtils.FIELD_PURCHASE_NUMBER).setColumnType(Column.LONG));
        purchaseInfoNames.add(new Column().setIndex(6).setName(PurchaseInfoImportConstantUtils.FIELD_PURCHASE_INFO_SUM_PRICE).setColumnType(Column.DECIMAL));
        purchaseInfoNames.add(new Column().setIndex(7).setName(PurchaseInfoImportConstantUtils.FIELD_STORE_STATE).setColumnType(Column.STRING));
        purchaseInfoNames.add(new Column().setIndex(8).setName(PurchaseInfoImportConstantUtils.FIELD_PURCHASE_INFO_REMARK).setColumnType(Column.STRING));
        try {
            ExcelHelper.read(file.getInputStream(), new PurchaseOrderExcelListener(purchaseOrderRepository, supplierRepository, storeRepository, purchaseRepository), purchaseOrderNames, 0, 1);
            ExcelHelper.read(file.getInputStream(), new PurchaseInfoExcelListener(materialRepository, purchaseInfoRepository, purchaseOrderRepository), purchaseInfoNames, 1, 1);
        } catch (IOException e) {
            logger.error("PurchaseOrderServiceImpl ----->{}", e.getMessage());
        }
        return Results.success();
    }

    @Override
    public void exportPurchasePdf(Long organizationId, Long purchaseOrderId, HttpServletResponse response) throws IOException, DocumentException {
//        PurchaseOrder purchaseOrder = purchaseOrderMapper.getPurchaseOrderByOrderId(organizationId, purchaseOrderId);
        PurchaseOrder purchaseOrder = this.getPurchaseOrderByOrderId(organizationId, purchaseOrderId).getBody();
        List<PurchaseInfo> orderDetailList = purchaseInfoService.getPurchaseOrderDetailsByOrderId(organizationId, purchaseOrderId).getBody();

        OutputStream os = response.getOutputStream();
        /* 解决中文无法显示：使用iTextAsian.jar包中的字体 */
        BaseFont baseFont = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font normalFont = new Font(baseFont, 10, Font.NORMAL);
        Font boldFont = new Font(baseFont, 10, Font.BOLD);
        /* 第一步 创建文档实例 自定义页面大小使用 */
        Rectangle rect = new Rectangle(PageSize.A4);
        Document document = new Document(rect);
        /* 第二步 获取PdfWriter实例 */
        PdfWriter.getInstance(document, os);
        document.setMargins(32, 32, 36, 36);

        /* 第三步 打开文档 */
        document.open();
        document.newPage();
        /* 第四步 添加段落内容 */
        Paragraph paragraph = new Paragraph("采购订单详情", new Font(baseFont, 20, Font.BOLD));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        /* 上一段落与下一段落的间距加大10个单位 */
        paragraph.setSpacingAfter(10);
        document.add(paragraph);

        Paragraph paragraph1 = new Paragraph("基本信息", new Font(baseFont, 18, Font.BOLD));
        paragraph.setAlignment(Element.ALIGN_LEFT);
        paragraph1.setSpacingAfter(10);
        document.add(paragraph1);

        //新建表格 列数为3
        PdfPTable table1 = new PdfPTable(3);
        PdfPTable table2 = new PdfPTable(3);
        PdfPTable table3 = new PdfPTable(3);

        //给表格设置宽度
        int[] width1 = {40, 40, 40};
        table1.setWidths(width1);
        table2.setWidths(width1);
        table2.setWidths(width1);

        // 基本信息第一行
        PdfPCell cell11 = new PdfPCell(new Paragraph("采购单号：  " + purchaseOrder.getPurchaseOrderNumber(), normalFont));
        PdfPCell cell12 = new PdfPCell(new Paragraph("供应商：  " + purchaseOrder.getSupplier().getSupplierName(), normalFont));
        PdfPCell cell13 = new PdfPCell(new Paragraph("订单日期：  "+ purchaseOrder.getPurchaseOrderDate(), normalFont));
        //设置单元格边框为0
        cell11.setBorder(0);
        cell12.setBorder(0);
        cell13.setBorder(0);
        table1.addCell(cell11);
        table1.addCell(cell12);
        table1.addCell(cell13);
        document.add(table1);

        PdfPCell cell21 = new PdfPCell(new Paragraph("收货仓库：  " + purchaseOrder.getStore().getStoreName(), normalFont));
        PdfPCell cell22 = new PdfPCell(new Paragraph("收货地址：  " + purchaseOrder.getStoreAddress(), normalFont));
        PdfPCell cell23 = new PdfPCell(new Paragraph("采购员：  "+ purchaseOrder.getPurchase().getPurchaseName(), normalFont));
        //设置单元格边框为0
        cell21.setBorder(0);
        cell22.setBorder(0);
        cell23.setBorder(0);
        table2.addCell(cell21);
        table2.addCell(cell22);
        table2.addCell(cell23);
        document.add(table2);

        PdfPCell cell31 = new PdfPCell(new Paragraph("币种：  " + purchaseOrder.getCurrency(), normalFont));
        PdfPCell cell32 = new PdfPCell(new Paragraph("总金额：  " + purchaseOrder.getPurchaseOrderSumPrice(), normalFont));
        PdfPCell cell33 = new PdfPCell(new Paragraph("订单状态：  "+ (purchaseOrder.getPurchaseOrderState() == 0 ? "未提交" : "已提交"), normalFont));
        // 设置单元格边框为0
        cell31.setBorder(0);
        cell32.setBorder(0);
        cell33.setBorder(0);
        table3.addCell(cell31);
        table3.addCell(cell32);
        table3.addCell(cell33);
        document.add(table3);


        Paragraph paragraph2 = new Paragraph("物料信息", new Font(baseFont, 18, Font.BOLD));
        paragraph.setAlignment(Element.ALIGN_LEFT);
        paragraph2.setSpacingBefore(10);
        paragraph2.setSpacingAfter(10);
        document.add(paragraph2);

        // 添加物料详情
        PdfPTable table4 = new PdfPTable(9);
        //给表格设置宽度
        int[] width2 = {20, 40, 20, 20, 20, 20, 20, 20, 40};

        table4.setWidths(width2);
        PdfPCell cell41 = new PdfPCell(new Paragraph("行号", normalFont));
        PdfPCell cell42 = new PdfPCell(new Paragraph("物料编码", normalFont));
        PdfPCell cell43 = new PdfPCell(new Paragraph("物料描述", normalFont));
        PdfPCell cell44 = new PdfPCell(new Paragraph("单价", normalFont));
        PdfPCell cell45 = new PdfPCell(new Paragraph("物料单位", normalFont));
        PdfPCell cell46 = new PdfPCell(new Paragraph("采购数量", normalFont));
        PdfPCell cell47 = new PdfPCell(new Paragraph("金额", normalFont));
        PdfPCell cell48 = new PdfPCell(new Paragraph("入库状态", normalFont));
        PdfPCell cell49 = new PdfPCell(new Paragraph("备注", normalFont));

        table4.addCell(cell41);
        table4.addCell(cell42);
        table4.addCell(cell43);
        table4.addCell(cell44);
        table4.addCell(cell45);
        table4.addCell(cell46);
        table4.addCell(cell47);
        table4.addCell(cell48);
        table4.addCell(cell49);

        document.add(table4);

        int i = 1;
        for(PurchaseInfo orderDetail: orderDetailList) {
            PdfPTable table5 = new PdfPTable(9);

            table5.setWidths(width2);
            PdfPCell cell51 = new PdfPCell(new Paragraph(i + "", normalFont));
            PdfPCell cell52 = new PdfPCell(new Paragraph(orderDetail.getMaterial().getMaterialCode(), normalFont));
            PdfPCell cell53 = new PdfPCell(new Paragraph(orderDetail.getMaterial().getMaterialDescription(), normalFont));
            PdfPCell cell54 = new PdfPCell(new Paragraph(orderDetail.getMaterial().getMaterialPrice()+"", normalFont));
            PdfPCell cell55 = new PdfPCell(new Paragraph(orderDetail.getMaterial().getMaterialUnit(), normalFont));
            PdfPCell cell56 = new PdfPCell(new Paragraph(orderDetail.getPurchaseNumber()+"", normalFont));
            PdfPCell cell57 = new PdfPCell(new Paragraph(orderDetail.getPurchaseInfoSumPrice()+"", normalFont));
            PdfPCell cell58 = new PdfPCell(new Paragraph(orderDetail.getStorageState()==0 ? "未入库" : "已入库", normalFont));
            PdfPCell cell59 = new PdfPCell(new Paragraph(orderDetail.getPurchaseInfoRemark() , normalFont));

            table5.addCell(cell51);
            table5.addCell(cell52);
            table5.addCell(cell53);
            table5.addCell(cell54);
            table5.addCell(cell55);
            table5.addCell(cell56);
            table5.addCell(cell57);
            table5.addCell(cell58);
            table5.addCell(cell59);

            document.add(table5);

            i++;
        }
        document.close();
    }
}
