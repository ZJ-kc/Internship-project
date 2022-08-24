package org.hzero.service.app.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hzero.core.base.BaseAppService;

import org.hzero.core.util.Results;
import org.hzero.excel.entity.Column;
import org.hzero.excel.helper.ExcelHelper;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.hzero.service.api.dto.SaleOrderDTO;
import org.hzero.service.app.service.SaleInfoService;
import org.hzero.service.app.service.SaleOrderService;
import org.hzero.service.domain.entity.*;
import org.hzero.service.domain.repository.*;
import org.hzero.service.infra.listener.SaleInfoExcelListener;
import org.hzero.service.infra.listener.SaleOrderExcelListener;
import org.hzero.service.infra.mapper.SaleOrderMapper;
import org.hzero.service.infra.util.SaleInfoImportConstantUtils;
import org.hzero.service.infra.util.SaleOrderImportConstantUtils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import com.itextpdf.text.pdf.BaseFont;

/**
 * 应用服务默认实现
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
@Service
public class SaleOrderServiceImpl extends BaseAppService implements SaleOrderService {

    private static final Logger logger = LoggerFactory.getLogger(SaleOrderServiceImpl.class);
    private final SaleOrderRepository saleOrderRepository;
    private final SaleOrderMapper saleOrderMapper;
    private final SaleInfoRepository saleInfoRepository;
    private final MaterialRepository materialRepository;
    private final SaleRepository saleRepository;
    private final CompanyRepository companyRepository;
    private final StoreRepository storeRepository;

    private final ClientRepository clientRepository;

    private final SaleInfoService saleInfoService;

    @Autowired
    public SaleOrderServiceImpl(SaleOrderRepository saleOrderRepository,
                                SaleOrderMapper saleOrderMapper,
                                SaleInfoRepository saleInfoRepository,
                                RepertoryRepository repertoryRepository,
                                MaterialRepository materialRepository,
                                SaleRepository saleRepository,
                                CompanyRepository companyRepository,
                                StoreRepository storeRepository,
                                ClientRepository clientRepository, SaleInfoService saleInfoService) {
        this.saleOrderRepository = saleOrderRepository;
        this.saleOrderMapper = saleOrderMapper;
        this.saleInfoRepository = saleInfoRepository;
        this.materialRepository = materialRepository;
        this.saleRepository = saleRepository;
        this.companyRepository = companyRepository;
        this.storeRepository = storeRepository;
        this.clientRepository=clientRepository;
        this.saleInfoService = saleInfoService;
    }

    @Override
    public Page<SaleOrder> list(Long tenantId, String keyword, SaleOrder saleOrder, PageRequest pageRequest, LocalDate startDate, LocalDate endDate) {
        return PageHelper.doPageAndSort(pageRequest, () -> saleOrderRepository.getSaleOrderPage(tenantId, keyword, saleOrder, startDate, endDate));

    }

    @Override
    public ResponseEntity<SaleOrder> getSaleOrderByOrderId(Long tenantId, Long saleOrderId) {
        SaleOrder saleOrder = this.saleOrderMapper.getSaleOrderByOrderId(tenantId, saleOrderId);
        Company company = companyRepository.selectByPrimaryKey(saleOrder.getCompanyId());
        Sale sale = saleRepository.selectByPrimaryKey(saleOrder.getSaleId());
        Store store = storeRepository.selectByPrimaryKey(saleOrder.getStoreId());

//        List<Client> clients = clientRepository.select(Client.FIELD_COMPANY_ID, company.getCompanyId());
//        company.setChildren(clients);
        Client client = clientRepository.selectByPrimaryKey(saleOrder.getClientId());
        company.setClient(client);
//        saleOrder.setSaleName(sale.getSaleName());
//        saleOrder.setCompanyName(company.getCompanyName());
//        saleOrder.setClientName(client.getClientName());
//        saleOrder.setStoreName(store.getStoreName());
        saleOrder.setSale(sale);
        saleOrder.setCompany(company);
        saleOrder.setStore(store);
        return Results.success(saleOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<SaleOrder> addSaleOrder(Long organizationId, SaleOrder saleOrder) {
        saleOrderRepository.addSaleOrder(organizationId, saleOrder);
        return Results.success(saleOrder);
    }

    /**
     * 待做：发送提示消息
     * @param organizationId
     * @param saleOrderId
     * @return
     */
    @Override
    public ResponseEntity<?> updateOrderState(Long organizationId, Long saleOrderId) {
        SaleOrder order = saleOrderRepository.selectByPrimaryKey(saleOrderId);
        order.setSaleOrderState(1);

        int count = saleOrderRepository.updateOptional(order, SaleOrder.FIELD_SALE_ORDER_STATE);
        if(1 == count) {
            return Results.success("提交成功");
        }
        return Results.error();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deleteSaleOrder(Long organizationId, Long saleOrderId) {
        saleInfoRepository.batchDelete(saleInfoRepository
                .selectByCondition(Condition.builder(SaleInfo.class)
                        .andWhere(Sqls.custom()
                                .andEqualTo("saleOrderId", saleOrderId))
                        .build()));
        int count = saleOrderRepository.deleteByPrimaryKey(saleOrderId);
        if(1 == count) {
            return Results.success();
        }
        return Results.error();
    }

    @Override
    public List<SaleOrderDTO> exportSaleOrder(Integer[] saleOrderIds) {
        return saleOrderRepository.exportSaleOrder(saleOrderIds);
    }

    @Override
    public ResponseEntity<?> importSaleOrder(MultipartFile file) {
        List<Column> saleOrderNames = new ArrayList<>();
        List<Column> saleInfoNames = new ArrayList<>();

        saleOrderNames.add(new Column().setIndex(0).setName(SaleOrderImportConstantUtils.FIELD_SALE_ORDER_NUMBER).setColumnType(Column.STRING));
        saleOrderNames.add(new Column().setIndex(1).setName(SaleOrderImportConstantUtils.FIELD_COMPANY_NAME).setColumnType(Column.STRING));
        saleOrderNames.add(new Column().setIndex(2).setName(SaleOrderImportConstantUtils.FIELD_CLIENT_NAME).setColumnType(Column.STRING));
        saleOrderNames.add(new Column().setIndex(3).setName(SaleOrderImportConstantUtils.FIELD_SALE_ORDER_DATE).setColumnType(Column.DATE).setFormat("yyyy/MM/dd"));
        saleOrderNames.add(new Column().setIndex(4).setName(SaleOrderImportConstantUtils.FIELD_STORE_NAME).setColumnType(Column.STRING));
        saleOrderNames.add(new Column().setIndex(5).setName(SaleOrderImportConstantUtils.FIELD_SALE_ADDRESS).setColumnType(Column.STRING));
        saleOrderNames.add(new Column().setIndex(6).setName(SaleOrderImportConstantUtils.FIELD_SALE_NAME).setColumnType(Column.STRING));
        //saleOrderNames.add(new Column().setIndex().setName(SaleOrderImportConstantUtils.FIELD_CLIENT_NAME).setColumnType(Column.STRING));
        saleOrderNames.add(new Column().setIndex(7).setName(SaleOrderImportConstantUtils.FIELD_CURRENCY_SYMBOL).setColumnType(Column.STRING));
        saleOrderNames.add(new Column().setIndex(8).setName(SaleOrderImportConstantUtils.FIELD_SALE_ORDER_SUM_PRICE).setColumnType(Column.DECIMAL));
        saleOrderNames.add(new Column().setIndex(9).setName(SaleOrderImportConstantUtils.FIELD_SALE_ORDER_STATE).setColumnType(Column.STRING));



        saleInfoNames.add(new Column().setIndex(0).setName(SaleInfoImportConstantUtils.FIELD_SALE_ORDER_NUMBER).setColumnType(Column.STRING));
        saleInfoNames.add(new Column().setIndex(1).setName(SaleInfoImportConstantUtils.FIELD_SALE_INFO_LINE_NUMBER).setColumnType(Column.LONG));
        saleInfoNames.add(new Column().setIndex(2).setName(SaleInfoImportConstantUtils.FIELD_MATERIAL_CODE).setColumnType(Column.STRING));
        saleInfoNames.add(new Column().setIndex(3).setName(SaleInfoImportConstantUtils.FIELD_MATERIAL_PRICE).setColumnType(Column.DECIMAL));
        saleInfoNames.add(new Column().setIndex(4).setName(SaleInfoImportConstantUtils.FIELD_MATERIAL_UNIT).setColumnType(Column.STRING));
        saleInfoNames.add(new Column().setIndex(5).setName(SaleInfoImportConstantUtils.FIELD_SALE_NUMBER).setColumnType(Column.LONG));
        saleInfoNames.add(new Column().setIndex(6).setName(SaleInfoImportConstantUtils.FIELD_SALE_INFO_SUM_PRICE).setColumnType(Column.DECIMAL));
        saleInfoNames.add(new Column().setIndex(7).setName(SaleInfoImportConstantUtils.FIELD_DELIVERY_STATE).setColumnType(Column.STRING));
        saleInfoNames.add(new Column().setIndex(8).setName(SaleInfoImportConstantUtils.FIELD_SALE_INFO_REMARK).setColumnType(Column.STRING));
        try {
            ExcelHelper.read(file.getInputStream(), new SaleOrderExcelListener(saleOrderRepository, companyRepository, storeRepository, saleRepository,clientRepository), saleOrderNames, 0, 1);
            ExcelHelper.read(file.getInputStream(), new SaleInfoExcelListener(materialRepository, saleInfoRepository, saleOrderRepository), saleInfoNames, 1, 1);
        } catch (IOException e) {
            logger.error("SaleOrderServiceImpl ----->{}", e.getMessage());
        }
        return Results.success();
    }


    @Override
    public void exportSalePdf(Long organizationId, Long saleOrderId, HttpServletResponse response) throws IOException, DocumentException, com.itextpdf.text.DocumentException {

        SaleOrder saleOrder = this.getSaleOrderByOrderId(organizationId, saleOrderId).getBody();
//        SaleOrder saleOrder = this.saleOrderMapper.getSaleOrderByOrderId(organizationId, saleOrderId);
//        Company company = companyRepository.selectByPrimaryKey(saleOrder.getCompanyId());
//        Client client = clientRepository.selectByPrimaryKey(saleOrder.getClientId());

        List<SaleInfo> orderDetailList = saleInfoService.getSaleOrderDetailsByOrderId(organizationId, saleOrderId).getBody();

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
        Paragraph paragraph = new Paragraph("销售订单详情", new Font(baseFont, 20, Font.BOLD));
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
        PdfPTable table4 = new PdfPTable(1);

        //给表格设置宽度
        int[] width1 = {40, 40, 40};
        table1.setWidths(width1);
        table2.setWidths(width1);
        table3.setWidths(width1);
        table4.setWidths(new int[]{40});

        // 基本信息第一行
        PdfPCell cell11 = new PdfPCell(new Paragraph("销售单号：  " + saleOrder.getSaleOrderNumber(), normalFont));
        PdfPCell cell12 = new PdfPCell(new Paragraph("公司名：  " + saleOrder.getCompany().getCompanyName(), normalFont));
        PdfPCell cell13 = new PdfPCell(new Paragraph("客户名：  "+ saleOrder.getCompany().getClient().getClientName(), normalFont));
        //设置单元格边框为0
        cell11.setBorder(0);
        cell12.setBorder(0);
        cell13.setBorder(0);
        table1.addCell(cell11);
        table1.addCell(cell12);
        table1.addCell(cell13);
        document.add(table1);

        //基本信息第二行
        PdfPCell cell21 = new PdfPCell(new Paragraph("发货仓库：  " + saleOrder.getStore().getStoreName(), normalFont));
        PdfPCell cell22 = new PdfPCell(new Paragraph("收货地址：  " + saleOrder.getSaleAddress(), normalFont));
        PdfPCell cell23 = new PdfPCell(new Paragraph("订单日期：  "+ saleOrder.getSaleOrderDate(), normalFont));


        //设置单元格边框为0
        cell21.setBorder(0);
        cell22.setBorder(0);
        cell23.setBorder(0);
        table2.addCell(cell21);
        table2.addCell(cell22);
        table2.addCell(cell23);
        document.add(table2);
        //基本信息第三行
        PdfPCell cell31 = new PdfPCell(new Paragraph("币种：  " + saleOrder.getCurrency(), normalFont));
        PdfPCell cell32 = new PdfPCell(new Paragraph("总金额：  " + saleOrder.getSaleOrderSumPrice(), normalFont));

        Integer state=saleOrder.getSaleOrderState();
        String saleOrderState = "";
        if(0 == state) {
            saleOrderState = "未提交";
        } else if(1 == state) {
            saleOrderState = "待审批";
        } else if(2 == state) {
            saleOrderState = "审批通过";
        }else if(3 == state) {
            saleOrderState = "审批拒绝";
        }

        PdfPCell cell33 = new PdfPCell(new Paragraph("订单状态：  "+ saleOrderState, normalFont));


        // 设置单元格边框为0
        cell31.setBorder(0);
        cell32.setBorder(0);
        cell33.setBorder(0);
        table3.addCell(cell31);
        table3.addCell(cell32);
        table3.addCell(cell33);
        document.add(table3);

        //基本信息第四行
        PdfPCell cell41 = new PdfPCell(new Paragraph("销售员：  "+ saleOrder.getSale().getSaleName(), normalFont));
        cell41.setBorder(0);
        table4.addCell(cell41);
        document.add(table4);



        Paragraph paragraph2 = new Paragraph("物料信息", new Font(baseFont, 18, Font.BOLD));
        paragraph.setAlignment(Element.ALIGN_LEFT);
        paragraph2.setSpacingBefore(10);
        paragraph2.setSpacingAfter(10);
        document.add(paragraph2);

        // 添加物料详情
        PdfPTable table5 = new PdfPTable(9);
        //给表格设置宽度
        int[] width2 = {20, 40, 20, 20, 20, 20, 20, 20, 40};

        table5.setWidths(width2);
        PdfPCell cell51 = new PdfPCell(new Paragraph("行号", normalFont));
        PdfPCell cell52 = new PdfPCell(new Paragraph("物料编码", normalFont));
        PdfPCell cell53 = new PdfPCell(new Paragraph("物料描述", normalFont));
        PdfPCell cell54 = new PdfPCell(new Paragraph("单价", normalFont));
        PdfPCell cell55 = new PdfPCell(new Paragraph("物料单位", normalFont));
        PdfPCell cell56 = new PdfPCell(new Paragraph("销售数量", normalFont));
        PdfPCell cell57 = new PdfPCell(new Paragraph("金额", normalFont));
        PdfPCell cell58 = new PdfPCell(new Paragraph("发货状态", normalFont));
        PdfPCell cell59 = new PdfPCell(new Paragraph("备注", normalFont));

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

        int i = 1;
        for(SaleInfo orderDetail: orderDetailList) {
            PdfPTable table6 = new PdfPTable(9);

            table6.setWidths(width2);
            PdfPCell cell61 = new PdfPCell(new Paragraph(i + "", normalFont));
            PdfPCell cell62 = new PdfPCell(new Paragraph(orderDetail.getMaterial().getMaterialCode(), normalFont));
            PdfPCell cell63 = new PdfPCell(new Paragraph(orderDetail.getMaterial().getMaterialDescription(), normalFont));
            PdfPCell cell64 = new PdfPCell(new Paragraph(orderDetail.getMaterial().getMaterialPrice()+"", normalFont));
            PdfPCell cell65 = new PdfPCell(new Paragraph(orderDetail.getMaterial().getMaterialUnit(), normalFont));
            PdfPCell cell66 = new PdfPCell(new Paragraph(orderDetail.getSaleNumber()+"", normalFont));
            PdfPCell cell67 = new PdfPCell(new Paragraph(orderDetail.getSaleInfoSumPrice()+"", normalFont));
            PdfPCell cell68 = new PdfPCell(new Paragraph(orderDetail.getDeliveryState()==0 ? "未发货" : "已发货", normalFont));
            PdfPCell cell69 = new PdfPCell(new Paragraph(orderDetail.getSaleInfoRemark() , normalFont));

            table6.addCell(cell61);
            table6.addCell(cell62);
            table6.addCell(cell63);
            table6.addCell(cell64);
            table6.addCell(cell65);
            table6.addCell(cell66);
            table6.addCell(cell67);
            table6.addCell(cell68);
            table6.addCell(cell69);

            document.add(table6);

            i++;
        }
        document.close();
    }
}
