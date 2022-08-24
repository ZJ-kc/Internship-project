package org.hzero.service.api.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.hzero.core.base.BaseConstants;
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;

/**
 * @Author: zj
 * @Date: 2022/8/22
 * @Time: 23:52
 */
@ExcelSheet(zh = "销售订单", en = "SaleOrder")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SaleOrderDTO {


    @ExcelColumn(zh = "订单编号", en = "Order Number", showInChildren = true)
    private String saleOrderNumber;

    @ExcelColumn(zh = "公司", en = "Company Name")
    private String companyName;

    @ExcelColumn(zh = "客户", en = "Client Name")
    private String clientName;

    @ExcelColumn(zh = "销售员", en = "Sale Name")
    private String saleName;

    @ExcelColumn(zh = "订单状态", en = "Order State")
    private String saleOrderState;

    @ExcelColumn(zh = "订单日期", en = "Order Date", pattern = BaseConstants.Pattern.DATE)
    private LocalDate saleOrderDate;

    @ExcelColumn(zh = "订单详情", en = "Order details", child = true)
    private List<SaleInfoDTO> saleInfoList;

    public String getSaleOrderNumber() {
        return saleOrderNumber;
    }

    public SaleOrderDTO setSaleOrderNumber(String saleOrderNumber) {
        this.saleOrderNumber = saleOrderNumber;
        return this;
    }

    public String getCompanyName() {
        return companyName;
    }

    public SaleOrderDTO setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public String getClientName() {
        return clientName;
    }

    public SaleOrderDTO setClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public String getSaleName() {
        return saleName;
    }

    public SaleOrderDTO setSaleName(String saleName) {
        this.saleName = saleName;
        return this;
    }

    public String getSaleOrderState() {
        return saleOrderState;
    }

    public SaleOrderDTO setSaleOrderState(String saleOrderState) {
        this.saleOrderState = saleOrderState;
        return this;
    }

    public LocalDate getSaleOrderDate() {
        return saleOrderDate;
    }

    public SaleOrderDTO setSaleOrderDate(LocalDate saleOrderDate) {
        this.saleOrderDate = saleOrderDate;
        return this;
    }

    public List<SaleInfoDTO> getSaleInfoList() {
        return saleInfoList;
    }

    public SaleOrderDTO setSaleInfoList(List<SaleInfoDTO> saleInfoList) {
        this.saleInfoList = saleInfoList;
        return this;
    }

    @Override
    public String toString() {
        return "SaleOrderDTO{" +
                "saleOrderNumber='" + saleOrderNumber + '\'' +
                ", companyName='" + companyName + '\'' +
                ", clientName='" + clientName + '\'' +
                ", saleName='" + saleName + '\'' +
                ", saleOrderState='" + saleOrderState + '\'' +
                ", saleOrderDate=" + saleOrderDate +
                ", saleInfoList=" + saleInfoList +
                '}';
    }
}