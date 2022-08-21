package org.hzero.service.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;

import io.choerodon.mybatis.domain.AuditDomain;

import java.math.BigDecimal;

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
@Table(name = "hff_sale_info")
public class SaleInfo extends AuditDomain {

    public static final String FIELD_SALE_INFO_ID = "saleInfoId";
    public static final String FIELD_SALE_LINE_NUMBER = "saleLineNumber";
    public static final String FIELD_SALE_ORDER_ID = "saleOrderId";
    public static final String FIELD_MATERIAL_ID = "materialId";
    public static final String FIELD_SALE_NUMBER = "saleNumber";
    public static final String FIELD_SALE_INFO_SUM_PRICE = "saleInfoSumPrice";
    public static final String FIELD_DELIVERY_STATE = "deliveryState";
    public static final String FIELD_SALE_INFO_REMARK = "saleInfoRemark";

//
// 业务方法(按public protected private顺序排列)
// ------------------------------------------------------------------------------

//
// 数据库字段
// ------------------------------------------------------------------------------


    @ApiModelProperty("销售详情id")
    @Id
    @GeneratedValue
    private Long saleInfoId;
    @ApiModelProperty(value = "销售详情行号", required = true)
    private Long saleLineNumber;
    @ApiModelProperty(value = "销售订单号，外键", required = true)
    private Long saleOrderId;
    @ApiModelProperty(value = "物料id，外键", required = true)
    private Long materialId;
    @ApiModelProperty(value = "销售数量", required = true)
    private Long saleNumber;
    @ApiModelProperty(value = "此销售详情总金额", required = true)
    private BigDecimal saleInfoSumPrice;
    @ApiModelProperty(value = "发货状态，0未发货，1已发货", required = true)
    private Integer deliveryState;
    @ApiModelProperty(value = "此销售详情备注", required = true)
    private String saleInfoRemark;

//
// 非数据库字段
// ------------------------------------------------------------------------------

//
// getter/setter
// ------------------------------------------------------------------------------

    /**
     * @return 销售详情id
     */
    public Long getSaleInfoId() {
        return saleInfoId;
    }

    public SaleInfo setSaleInfoId(Long saleInfoId) {
        this.saleInfoId = saleInfoId;
        return this;
    }

    /**
     * @return 销售详情行号
     */
    public Long getSaleLineNumber() {
        return saleLineNumber;
    }

    public SaleInfo setSaleLineNumber(Long saleLineNumber) {
        this.saleLineNumber = saleLineNumber;
        return this;
    }

    /**
     * @return 销售订单号，外键
     */
    public Long getSaleOrderId() {
        return saleOrderId;
    }

    public SaleInfo setSaleOrderId(Long saleOrderId) {
        this.saleOrderId = saleOrderId;
        return this;
    }

    /**
     * @return 物料id，外键
     */
    public Long getMaterialId() {
        return materialId;
    }

    public SaleInfo setMaterialId(Long materialId) {
        this.materialId = materialId;
        return this;
    }

    /**
     * @return 销售数量
     */
    public Long getSaleNumber() {
        return saleNumber;
    }

    public SaleInfo setSaleNumber(Long saleNumber) {
        this.saleNumber = saleNumber;
        return this;
    }

    /**
     * @return 此销售详情总金额
     */
    public BigDecimal getSaleInfoSumPrice() {
        return saleInfoSumPrice;
    }

    public SaleInfo setSaleInfoSumPrice(BigDecimal saleInfoSumPrice) {
        this.saleInfoSumPrice = saleInfoSumPrice;
        return this;
    }

    /**
     * @return 发货状态，0未发货，1已发货
     */
    public Integer getDeliveryState() {
        return deliveryState;
    }

    public SaleInfo setDeliveryState(Integer deliveryState) {
        this.deliveryState = deliveryState;
        return this;
    }

    /**
     * @return 此销售详情备注
     */
    public String getSaleInfoRemark() {
        return saleInfoRemark;
    }

    public SaleInfo setSaleInfoRemark(String saleInfoRemark) {
        this.saleInfoRemark = saleInfoRemark;
        return this;
    }

    @Override
    public String toString() {
        return "SaleInfo{" +
                "saleInfoId=" + saleInfoId +
                ", saleLineNumber=" + saleLineNumber +
                ", saleOrderId=" + saleOrderId +
                ", materialId=" + materialId +
                ", saleNumber=" + saleNumber +
                ", saleInfoSumPrice=" + saleInfoSumPrice +
                ", deliveryState=" + deliveryState +
                ", saleInfoRemark='" + saleInfoRemark + '\'' +
                '}';
    }
}
