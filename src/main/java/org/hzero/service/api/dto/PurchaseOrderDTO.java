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
    private LocalDate purchaseOrderDate;

    @ExcelColumn(zh = "订单详情", en = "Order details", child = true)
    private List<PurchaseInfoDTO> purchaseInfoList;

    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public PurchaseOrderDTO setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
        return this;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public PurchaseOrderDTO setSupplierName(String supplierName) {
        this.supplierName = supplierName;
        return this;
    }

    public String getPurchaseName() {
        return purchaseName;
    }

    public PurchaseOrderDTO setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName;
        return this;
    }

    public String getPurchaseOrderState() {
        return purchaseOrderState;
    }

    public PurchaseOrderDTO setPurchaseOrderState(String purchaseOrderState) {
        this.purchaseOrderState = purchaseOrderState;
        return this;
    }

    public LocalDate getPurchaseOrderDate() {
        return purchaseOrderDate;
    }

    public PurchaseOrderDTO setPurchaseOrderDate(LocalDate purchaseOrderDate) {
        this.purchaseOrderDate = purchaseOrderDate;
        return this;
    }

    public List<PurchaseInfoDTO> getPurchaseInfoList() {
        return purchaseInfoList;
    }

    public PurchaseOrderDTO setPurchaseInfoList(List<PurchaseInfoDTO> purchaseInfoList) {
        this.purchaseInfoList = purchaseInfoList;
        return this;
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
