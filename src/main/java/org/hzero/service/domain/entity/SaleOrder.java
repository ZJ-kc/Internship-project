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
@Table(name = "hff_sale_order")
public class SaleOrder extends AuditDomain {

    public static final String FIELD_SALE_ORDER_ID = "saleOrderId";
    public static final String FIELD_SALE_ORDER_NUMBER = "saleOrderNumber";
    public static final String FIELD_SALE_ID = "saleId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_CLIENT_ID = "clientId";
    public static final String FIELD_SALE_ADDRESS = "saleAddress";
    public static final String FIELD_STORE_ID = "storeId";
    public static final String FIELD_SALE_ORDER_STATE = "saleOrderState";
    public static final String FIELD_SALE_ORDER_DATE = "saleOrderDate";
    public static final String FIELD_CURRENCY_ID = "currencyId";
    public static final String FIELD_SALE_ORDER_SUM_PRICE = "saleOrderSumPrice";
    public static final String FIELD_ORGANIZATION_ID = "organizationId";

//
// 业务方法(按public protected private顺序排列)
// ------------------------------------------------------------------------------

//
// 数据库字段
// ------------------------------------------------------------------------------


    @ApiModelProperty("销售订单id")
    @Id
    @GeneratedValue
    private Long saleOrderId;
    @ApiModelProperty(value = "销售单号", required = true)
    private String saleOrderNumber;
    @ApiModelProperty(value = "销售员id，外键", required = true)
    private Long saleId;
    @ApiModelProperty(value = "公司id，外键", required = true)
    private Long companyId;
    @ApiModelProperty(value = "客户id，外键", required = true)
    private Long clientId;
    @ApiModelProperty(value = "销售地址（自己填）", required = true)
    private String saleAddress;
    @ApiModelProperty(value = "发货仓库id，外键", required = true)
    private Long storeId;
    @ApiModelProperty(value = "销售订单状态：默认0未提交，1待审核，2审核通过，3审核拒绝", required = true)
    private Integer saleOrderState;
    @ApiModelProperty(value = "销售订单创建日期", required = true)
    private LocalDate saleOrderDate;
    @ApiModelProperty(value = "币种，外键", required = true)
    private String currency;
    @ApiModelProperty(value = "销售订单总金额", required = true)
    private BigDecimal saleOrderSumPrice;
    @ApiModelProperty(value = "组织id，外键", required = true)
    private Long organizationId;

    @Transient
    private List<SaleInfo> children;

    @Transient
    private Sale sale;

    @Transient
    private Company company;

    @Transient
    private Store store;

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    //
// 非数据库字段
// ------------------------------------------------------------------------------

//
// getter/setter
// ------------------------------------------------------------------------------

    /**
     * @return 销售订单id
     */
    public Long getSaleOrderId() {
        return saleOrderId;
    }

    public SaleOrder setSaleOrderId(Long saleOrderId) {
        this.saleOrderId = saleOrderId;
        return this;
    }

    /**
     * @return 销售单号
     */
    public String getSaleOrderNumber() {
        return saleOrderNumber;
    }

    public SaleOrder setSaleOrderNumber(String saleOrderNumber) {
        this.saleOrderNumber = saleOrderNumber;
        return this;
    }

    /**
     * @return 销售员id，外键
     */
    public Long getSaleId() {
        return saleId;
    }

    public SaleOrder setSaleId(Long saleId) {
        this.saleId = saleId;
        return this;
    }

    /**
     * @return 公司id，外键
     */
    public Long getCompanyId() {
        return companyId;
    }

    public SaleOrder setCompanyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    /**
     * @return 客户id，外键
     */
    public Long getClientId() {
        return clientId;
    }

    public SaleOrder setClientId(Long clientId) {
        this.clientId = clientId;
        return this;
    }

    /**
     * @return 销售地址（自己填）
     */
    public String getSaleAddress() {
        return saleAddress;
    }

    public SaleOrder setSaleAddress(String saleAddress) {
        this.saleAddress = saleAddress;
        return this;
    }

    /**
     * @return 发货仓库id，外键
     */
    public Long getStoreId() {
        return storeId;
    }

    public SaleOrder setStoreId(Long storeId) {
        this.storeId = storeId;
        return this;
    }

    /**
     * @return 销售订单状态：默认0未提交，1待审核，2审核通过，3审核拒绝
     */
    public Integer getSaleOrderState() {
        return saleOrderState;
    }

    public SaleOrder setSaleOrderState(Integer saleOrderState) {
        this.saleOrderState = saleOrderState;
        return this;
    }

    /**
     * @return 销售订单创建日期
     */
    public LocalDate getSaleOrderDate() {
        return saleOrderDate;
    }

    public SaleOrder setSaleOrderDate(LocalDate saleOrderDate) {
        this.saleOrderDate = saleOrderDate;
        return this;
    }

    /**
     * @return 币种，外键
     */
    public String getCurrency() {
        return currency;
    }

    public SaleOrder setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    /**
     * @return 销售订单总金额
     */
    public BigDecimal getSaleOrderSumPrice() {
        return saleOrderSumPrice;
    }

    public SaleOrder setSaleOrderSumPrice(BigDecimal saleOrderSumPrice) {
        this.saleOrderSumPrice = saleOrderSumPrice;
        return this;
    }

    /**
     * @return 组织id，外键
     */
    public Long getOrganizationId() {
        return organizationId;
    }

    public SaleOrder setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
        return this;
    }

    public List<SaleInfo> getChildren() {
        return children;
    }



    public void setChildren(List<SaleInfo> children) {
        this.children = children;
    }

//    @Override
//    public String toString() {
//        return "SaleOrder{" +
//                "saleOrderId=" + saleOrderId +
//                ", saleOrderNumber='" + saleOrderNumber + '\'' +
//                ", saleId=" + saleId +
//                ", companyId=" + companyId +
//                ", clientId=" + clientId +
//                ", saleAddress='" + saleAddress + '\'' +
//                ", storeId=" + storeId +
//                ", saleOrderState=" + saleOrderState +
//                ", saleOrderDate=" + saleOrderDate +
//                ", currencyId=" + currency +
//                ", saleOrderSumPrice=" + saleOrderSumPrice +
//                ", organizationId=" + organizationId +
//                ", children=" + children +
//                '}';
//    }


    @Override
    public String toString() {
        return "SaleOrder{" +
                "saleOrderId=" + saleOrderId +
                ", saleOrderNumber='" + saleOrderNumber + '\'' +
                ", saleId=" + saleId +
                ", companyId=" + companyId +
                ", clientId=" + clientId +
                ", saleAddress='" + saleAddress + '\'' +
                ", storeId=" + storeId +
                ", saleOrderState=" + saleOrderState +
                ", saleOrderDate=" + saleOrderDate +
                ", currency='" + currency + '\'' +
                ", saleOrderSumPrice=" + saleOrderSumPrice +
                ", organizationId=" + organizationId +
                ", children=" + children +
                '}';
    }
}
