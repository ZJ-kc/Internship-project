package org.hzero.service.api.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import org.hzero.core.base.BaseConstants;
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;
import org.hzero.service.domain.entity.PurchaseInfo;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : SunYuji
 * @create 2022/8/21 22:45
 */
@ExcelSheet(zh = "采购订单", en = "PurchaseOrder")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PurchaseOrderDTO {


    @ExcelColumn(zh = "订单编号", en = "Order Number", showInChildren = true)
    private String purchaseOrderNumber;

    @ExcelColumn(zh = "供应商", en = "Supplier Name")
    private String supplierName;

    @ExcelColumn(zh = "采购员", en = "Purchase Name")
    private String purchaseName;

    @ExcelColumn(zh = "订单状态", en = "Order State")
    private String purchaseOrderState;

    @ExcelColumn(zh = "订单日期", en = "Order Date", pattern = BaseConstants.Pattern.DATE)
    private LocalDateTime purchaseOrderDate;

    @ExcelColumn(zh = "订单详情", en = "Order details", child = true)
    private List<PurchaseInfoDTO> purchaseInfoList;

    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getPurchaseName() {
        return purchaseName;
    }

    public void setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName;
    }

    public String getPurchaseOrderState() {
        return purchaseOrderState;
    }

    public void setPurchaseOrderState(String purchaseOrderState) {
        this.purchaseOrderState = purchaseOrderState;
    }

    public LocalDateTime getPurchaseOrderDate() {
        return purchaseOrderDate;
    }

    public void setPurchaseOrderDate(LocalDateTime purchaseOrderDate) {
        this.purchaseOrderDate = purchaseOrderDate;
    }

    public List<PurchaseInfoDTO> getPurchaseInfoList() {
        return purchaseInfoList;
    }

    public void setPurchaseInfoList(List<PurchaseInfoDTO> purchaseInfoList) {
        this.purchaseInfoList = purchaseInfoList;
    }

    @Override
    public String toString() {
        return "PurchaseOrderDTO{" +
                "purchaseOrderNumber='" + purchaseOrderNumber + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", purchaseName='" + purchaseName + '\'' +
                ", purchaseOrderState='" + purchaseOrderState + '\'' +
                ", purchaseOrderDate=" + purchaseOrderDate +
                ", purchaseInfoList=" + purchaseInfoList +
                '}';
    }
}
