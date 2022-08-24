package org.hzero.service.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;

import io.choerodon.mybatis.domain.AuditDomain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
@ApiModel("")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "hff_purchase_order")
public class PurchaseOrder extends AuditDomain {

    public static final String FIELD_PURCHASE_ORDER_ID = "purchaseOrderId";
    public static final String FIELD_PURCHASE_ORDER_NUMBER = "purchaseOrderNumber";
    public static final String FIELD_SUPPLIER_ID = "supplierId";
    public static final String FIELD_STORE_ID = "storeId";
    public static final String FIELD_STORE_ADDRESS = "storeAddress";
    public static final String FIELD_PURCHASE_ID = "purchaseId";
    public static final String FIELD_CURRENCY = "currency";
    public static final String FIELD_ORGANIZATION_ID = "organizationId";
    public static final String FIELD_PURCHASE_ORDER_SUM_PRICE = "purchaseOrderSumPrice";
    public static final String FIELD_PURCHASE_ORDER_STATE = "purchaseOrderState";
    public static final String FIELD_PURCHASE_ORDER_DATE = "purchaseOrderDate";

//
// 业务方法(按public protected private顺序排列)
// ------------------------------------------------------------------------------

//
// 数据库字段
// ------------------------------------------------------------------------------


    @ApiModelProperty("采购订单id")
    @Id
    @GeneratedValue
    private Long purchaseOrderId;

    @ApiModelProperty(value = "采购订单号", required = true)
    private String purchaseOrderNumber;

    @ApiModelProperty(value = "供应商，外键", required = true)
    private Long supplierId;

    @ApiModelProperty(value = "收货仓库，外键", required = true)
    private Long storeId;

    @ApiModelProperty(value = "仓库地址/收货地址", required = true)
    private String storeAddress;

    @ApiModelProperty(value = "采购员，外键", required = true)
    private Long purchaseId;

    @ApiModelProperty(value = "币种，外键", required = true)
    private String currency;

    @ApiModelProperty(value = "组织id，外键", required = true)
    private Long organizationId;

    @ApiModelProperty(value = "此采购订单总金额", required = true)
    private BigDecimal purchaseOrderSumPrice;

    @ApiModelProperty(value = "采购订单状态：默认0未提交，1待审核，2审核通过，3审核拒绝", required = true)
        private Integer purchaseOrderState;

    @ApiModelProperty(value = "采购订单创建日期", required = true)
    private LocalDate purchaseOrderDate;

    @Transient
    private List<PurchaseInfo> children;

    @Transient
    private Store store;
    @Transient
    private Supplier supplier;
    @Transient
    private Purchase purchase;


//
// 非数据库字段
// ------------------------------------------------------------------------------

//
// getter/setter
// ------------------------------------------------------------------------------

    /**
     * @return 采购订单id
     */
    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public PurchaseOrder setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
        return this;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    /**
     * @return 采购订单号
     */
    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public PurchaseOrder setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
        return this;
    }

    /**
     * @return 供应商，外键
     */
    public Long getSupplierId() {
        return supplierId;
    }

    public PurchaseOrder setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
        return this;
    }

    /**
     * @return 收货仓库，外键
     */
    public Long getStoreId() {
        return storeId;
    }

    public PurchaseOrder setStoreId(Long storeId) {
        this.storeId = storeId;
        return this;
    }

    /**
     * @return 仓库地址/收货地址
     */
    public String getStoreAddress() {
        return storeAddress;
    }

    public PurchaseOrder setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
        return this;
    }

    /**
     * @return 采购员，外键
     */
    public Long getPurchaseId() {
        return purchaseId;
    }

    public PurchaseOrder setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
        return this;
    }

    /**
     * @return 币种，外键
     */
    public String getCurrency() {
        return currency;
    }

    public PurchaseOrder setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    /**
     * @return 组织id，外键
     */
    public Long getOrganizationId() {
        return organizationId;
    }

    public PurchaseOrder setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
        return this;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    /**
     * @return 此采购订单总金额
     */
    public BigDecimal getPurchaseOrderSumPrice() {
        return purchaseOrderSumPrice;
    }

    public PurchaseOrder setPurchaseOrderSumPrice(BigDecimal purchaseOrderSumPrice) {
        this.purchaseOrderSumPrice = purchaseOrderSumPrice;
        return this;
    }

    /**
     * @return 采购订单状态：默认0未提交，1待审核，2审核通过，3审核拒绝
     */
    public Integer getPurchaseOrderState() {
        return purchaseOrderState;
    }

    public PurchaseOrder setPurchaseOrderState(Integer purchaseOrderState) {
        this.purchaseOrderState = purchaseOrderState;
        return this;
    }

    /**
     * @return 采购订单创建日期
     */
    public LocalDate getPurchaseOrderDate() {
        return purchaseOrderDate;
    }

    public void setPurchaseOrderDate(LocalDate purchaseOrderDate) {
        this.purchaseOrderDate = purchaseOrderDate;
    }

    public List<PurchaseInfo> getChildren() {
        return children;
    }

    public void setChildren(List<PurchaseInfo> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "PurchaseOrder{" +
                "purchaseOrderId=" + purchaseOrderId +
                ", purchaseOrderNumber='" + purchaseOrderNumber + '\'' +
                ", supplierId=" + supplierId +
                ", storeId=" + storeId +
                ", storeAddress='" + storeAddress + '\'' +
                ", purchaseId=" + purchaseId +
                ", currencyId=" + currency +
                ", organizationId=" + organizationId +
                ", purchaseOrderSumPrice=" + purchaseOrderSumPrice +
                ", purchaseOrderState=" + purchaseOrderState +
                ", purchaseOrderDate=" + purchaseOrderDate +
                ", children=" + children +
                '}';
    }
}
