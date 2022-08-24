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
@Table(name = "hff_purchase_info")
public class PurchaseInfo extends AuditDomain {

    public static final String FIELD_PURCHASE_INFO_ID = "purchaseInfoId";
    public static final String FIELD_PURCHASE_LINE_NUMBER = "purchaseLineNumber";
    public static final String FIELD_PURCHASE_ORDER_ID = "purchaseOrderId";
    public static final String FIELD_MATERIAL_ID = "materialId";
    public static final String FIELD_PURCHASE_NUMBER = "purchaseNumber";
    public static final String FIELD_PURCHASE_INFO_SUM_PRICE = "purchaseInfoSumPrice";
    public static final String FIELD_STORAGE_STATE = "storageState";
    public static final String FIELD_PURCHASE_INFO_REMARK = "purchaseInfoRemark";

//
// 业务方法(按public protected private顺序排列)
// ------------------------------------------------------------------------------

//
// 数据库字段
// ------------------------------------------------------------------------------


    @ApiModelProperty("采购订单详情id")
    @Id
    @GeneratedValue
    private Long purchaseInfoId;

    @ApiModelProperty(value = "采购订单详情行号", required = true)
    private Long purchaseLineNumber;

    @ApiModelProperty(value = "采购订单号，外键", required = true)
    private Long purchaseOrderId;

    @ApiModelProperty(value = "物料id，外键", required = true)
    private Long materialId;

    @ApiModelProperty(value = "采购数量", required = true)
    private Long purchaseNumber;

    @ApiModelProperty(value = "此采购详情总金额", required = true)
    private BigDecimal purchaseInfoSumPrice;

    @ApiModelProperty(value = "入库状态，默认0未入库，1已入库", required = true)
    private Integer storageState;

    @ApiModelProperty(value = "此采购详情备注", required = true)
    private String purchaseInfoRemark;

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Transient
    private Material material;

//
// 非数据库字段
// ------------------------------------------------------------------------------

//
// getter/setter
// ------------------------------------------------------------------------------

    /**
     * @return 采购订单详情id
     */
    public Long getPurchaseInfoId() {
        return purchaseInfoId;
    }

    public PurchaseInfo setPurchaseInfoId(Long purchaseInfoId) {
        this.purchaseInfoId = purchaseInfoId;
        return this;
    }

    /**
     * @return 采购订单详情行号
     */
    public Long getPurchaseLineNumber() {
        return purchaseLineNumber;
    }

    public PurchaseInfo setPurchaseLineNumber(Long purchaseLineNumber) {
        this.purchaseLineNumber = purchaseLineNumber;
        return this;
    }

    /**
     * @return 采购订单号，外键
     */
    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public PurchaseInfo setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
        return this;
    }

    /**
     * @return 物料id，外键
     */
    public Long getMaterialId() {
        return materialId;
    }

    public PurchaseInfo setMaterialId(Long materialId) {
        this.materialId = materialId;
        return this;
    }

    /**
     * @return 采购数量
     */
    public Long getPurchaseNumber() {
        return purchaseNumber;
    }

    public PurchaseInfo setPurchaseNumber(Long purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
        return this;
    }

    /**
     * @return 此采购详情总金额
     */
    public BigDecimal getPurchaseInfoSumPrice() {
        return purchaseInfoSumPrice;
    }

    public PurchaseInfo setPurchaseInfoSumPrice(BigDecimal purchaseInfoSumPrice) {
        this.purchaseInfoSumPrice = purchaseInfoSumPrice;
        return this;
    }

    /**
     * @return 入库状态，默认0未入库，1已入库
     */
    public Integer getStorageState() {
        return storageState;
    }

    public PurchaseInfo setStorageState(Integer storageState) {
        this.storageState = storageState;
        return this;
    }

    /**
     * @return 此采购详情备注
     */
    public String getPurchaseInfoRemark() {
        return purchaseInfoRemark;
    }

    public PurchaseInfo setPurchaseInfoRemark(String purchaseInfoRemark) {
        this.purchaseInfoRemark = purchaseInfoRemark;
        return this;
    }

    @Override
    public String toString() {
        return "PurchaseInfo{" +
                "purchaseInfoId=" + purchaseInfoId +
                ", purchaseLineNumber=" + purchaseLineNumber +
                ", purchaseOrderId=" + purchaseOrderId +
                ", materialId=" + materialId +
                ", purchaseNumber=" + purchaseNumber +
                ", purchaseInfoSumPrice=" + purchaseInfoSumPrice +
                ", storageState=" + storageState +
                ", purchaseInfoRemark='" + purchaseInfoRemark + '\'' +
                '}';
    }
}
