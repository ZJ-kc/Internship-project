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
@Table(name = "hff_sale")
public class Sale extends AuditDomain {

    public static final String FIELD_SALE_ID = "saleId";
    public static final String FIELD_SALE_CODE = "saleCode";
    public static final String FIELD_SALE_NAME = "saleName";
    public static final String FIELD_WORK_STATE = "workState";
    public static final String FIELD_USER_ID = "userId";

//
// 业务方法(按public protected private顺序排列)
// ------------------------------------------------------------------------------

//
// 数据库字段
// ------------------------------------------------------------------------------


    @ApiModelProperty("销售员id")
    @Id
    @GeneratedValue
    private Long saleId;
    @ApiModelProperty(value = "销售员编码", required = true)
    private String saleCode;
    @ApiModelProperty(value = "销售员名称", required = true)
    private String saleName;
    @ApiModelProperty(value = "在职标识，1在职，0离职", required = true)
    private Integer workState;
    @ApiModelProperty(value = "用户id，外键", required = true)
    private Long userId;

//
// 非数据库字段
// ------------------------------------------------------------------------------

//
// getter/setter
// ------------------------------------------------------------------------------

    /**
     * @return 销售员id
     */
    public Long getSaleId() {
        return saleId;
    }

    public Sale setSaleId(Long saleId) {
        this.saleId = saleId;
        return this;
    }

    /**
     * @return 销售员编码
     */
    public String getSaleCode() {
        return saleCode;
    }

    public Sale setSaleCode(String saleCode) {
        this.saleCode = saleCode;
        return this;
    }

    /**
     * @return 销售员名称
     */
    public String getSaleName() {
        return saleName;
    }

    public Sale setSaleName(String saleName) {
        this.saleName = saleName;
        return this;
    }

    /**
     * @return 在职标识，1在职，0离职
     */
    public Integer getWorkState() {
        return workState;
    }

    public Sale setWorkState(Integer workState) {
        this.workState = workState;
        return this;
    }

    /**
     * @return 用户id，外键
     */
    public Long getUserId() {
        return userId;
    }

    public Sale setUserId(Long userId) {
        this.userId = userId;
        return this;
    }


}
