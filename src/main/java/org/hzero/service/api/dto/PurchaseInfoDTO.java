package org.hzero.service.api.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : SunYuji
 * @create 2022/8/21 23:01
 */
@ExcelSheet(zh = "订单详情", en = "PurchaseDetail")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PurchaseInfoDTO {

    @ExcelColumn(zh = "行号", en = "Line Number")
    private Long purchaseLineNumber;

    @ExcelColumn(zh = "物料编码", en = "Material Code")
    private String materialCode;

    @ExcelColumn(zh = "单价", en = "Price")
    private BigDecimal materialPrice;

    @ExcelColumn(zh = "采购数量", en = "Purchase Number")
    private Long purchaseNumber;

    @ExcelColumn(zh = "金额", en = "Sum Price")
    private BigDecimal purchaseInfoSumPrice;

    @ExcelColumn(zh = "备注", en = "Remark")
    private String purchaseInfoRemark;

    public Long getPurchaseLineNumber() {
        return purchaseLineNumber;
    }

    public void setPurchaseLineNumber(Long purchaseLineNumber) {
        this.purchaseLineNumber = purchaseLineNumber;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public BigDecimal getMaterialPrice() {
        return materialPrice;
    }

    public void setMaterialPrice(BigDecimal materialPrice) {
        this.materialPrice = materialPrice;
    }

    public Long getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(Long purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public BigDecimal getPurchaseInfoSumPrice() {
        return purchaseInfoSumPrice;
    }

    public void setPurchaseInfoSumPrice(BigDecimal purchaseInfoSumPrice) {
        this.purchaseInfoSumPrice = purchaseInfoSumPrice;
    }

    public String getPurchaseInfoRemark() {
        return purchaseInfoRemark;
    }

    public void setPurchaseInfoRemark(String purchaseInfoRemark) {
        this.purchaseInfoRemark = purchaseInfoRemark;
    }

    @Override
    public String toString() {
        return "PurchaseInfoDTO{" +
                "purchaseLineNumber=" + purchaseLineNumber +
                ", materialCode='" + materialCode + '\'' +
                ", materialPrice=" + materialPrice +
                ", purchaseNumber=" + purchaseNumber +
                ", purchaseInfoSumPrice=" + purchaseInfoSumPrice +
                ", purchaseInfoRemark='" + purchaseInfoRemark + '\'' +
                '}';
    }
}
