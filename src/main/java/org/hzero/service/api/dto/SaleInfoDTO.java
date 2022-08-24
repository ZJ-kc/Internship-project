package org.hzero.service.api.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;

/**
 * @Author: zj
 * @Date: 2022/8/22
 * @Time: 23:57
 */
@ExcelSheet(zh = "订单详情", en = "SaleDetail")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SaleInfoDTO {

    @ExcelColumn(zh = "行号", en = "Line Number")
    private Long saleLineNumber;

    @ExcelColumn(zh = "物料编码", en = "Material Code")
    private String materialCode;

    @ExcelColumn(zh = "单价", en = "Price")
    private String materialPrice;

    @ExcelColumn(zh = "销售数量", en = "Sale Number")
    private Long saleNumber;

    @ExcelColumn(zh = "金额", en = "Sum Price")
    private String saleInfoSumPrice;

    @ExcelColumn(zh = "备注", en = "Remark")
    private String saleInfoRemark;

    public Long getSaleLineNumber() {
        return saleLineNumber;
    }

    public void setSaleLineNumber(Long saleLineNumber) {
        this.saleLineNumber = saleLineNumber;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public SaleInfoDTO setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
        return this;
    }

    public String getMaterialPrice() {
        return materialPrice;
    }

    public SaleInfoDTO setMaterialPrice(String materialPrice) {
        this.materialPrice = materialPrice;
        return this;
    }

    public Long getSaleNumber() {
        return saleNumber;
    }

    public SaleInfoDTO setSaleNumber(Long saleNumber) {
        this.saleNumber = saleNumber;
        return this;
    }


    public String getSaleInfoSumPrice() {
        return saleInfoSumPrice;
    }

    public SaleInfoDTO setSaleInfoSumPrice(String saleInfoSumPrice) {
        this.saleInfoSumPrice = saleInfoSumPrice;
        return this;
    }

    public String getSaleInfoRemark() {
        return saleInfoRemark;
    }

    public SaleInfoDTO setSaleInfoRemark(String saleInfoRemark) {
        this.saleInfoRemark = saleInfoRemark;
        return this;
    }

    @Override
    public String toString() {
        return "SaleInfoDTO{" +
                "saleLineNumber=" + saleLineNumber +
                ", materialCode='" + materialCode + '\'' +
                ", materialPrice=" + materialPrice +
                ", saleNumber=" + saleNumber +
                ", saleInfoSumPrice=" + saleInfoSumPrice +
                ", saleInfoRemark='" + saleInfoRemark + '\'' +
                '}';
    }
}
