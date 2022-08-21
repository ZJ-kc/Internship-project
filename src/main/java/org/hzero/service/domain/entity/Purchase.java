package org.hzero.service.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;

import io.choerodon.mybatis.domain.AuditDomain;
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
@Table(name = "hff_purchase")
public class Purchase extends AuditDomain {

    public static final String FIELD_PURCHASE_ID = "purchaseId";
    public static final String FIELD_PURCHASE_CODE = "purchaseCode";
    public static final String FIELD_PURCHASE_NAME = "purchaseName";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_WORK_STATE = "workState";

//
// 业务方法(按public protected private顺序排列)
// ------------------------------------------------------------------------------

//
// 数据库字段
// ------------------------------------------------------------------------------


    @ApiModelProperty("采购员id")
    @Id
    @GeneratedValue
    private Long purchaseId;
    @ApiModelProperty(value = "采购员编码", required = true)
    private String purchaseCode;
    @ApiModelProperty(value = "采购员名称", required = true)
    private String purchaseName;
    @ApiModelProperty(value = "用户id，外键", required = true)
    private Long userId;
    @ApiModelProperty(value = "是否在职，1在职，0离职", required = true)
    private Integer workState;

//
// 非数据库字段
// ------------------------------------------------------------------------------

//
// getter/setter
// ------------------------------------------------------------------------------

    /**
     * @return 采购员id
     */
    public Long getPurchaseId() {
        return purchaseId;
    }

    public Purchase setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
        return this;
    }

    /**
     * @return 采购员编码
     */
    public String getPurchaseCode() {
        return purchaseCode;
    }

    public Purchase setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
        return this;
    }

    /**
     * @return 采购员名称
     */
    public String getPurchaseName() {
        return purchaseName;
    }

    public Purchase setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName;
        return this;
    }

    /**
     * @return 用户id，外键
     */
    public Long getUserId() {
        return userId;
    }

    public Purchase setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    /**
     * @return 是否在职，1在职，0离职
     */
    public Integer getWorkState() {
        return workState;
    }

    public Purchase setWorkState(Integer workState) {
        this.workState = workState;
        return this;
    }
}
