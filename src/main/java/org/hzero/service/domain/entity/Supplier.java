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
 * @author yuji.sun@zknow.com 2022-08-21 10:41:27
 */
@ApiModel("")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "hff_supplier")
public class Supplier extends AuditDomain {

    public static final String FIELD_SUPPLIER_ID = "supplierId";
    public static final String FIELD_SUPPLIER_CODE = "supplierCode";
    public static final String FIELD_SUPPLIER_NAME = "supplierName";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";

//
// 业务方法(按public protected private顺序排列)
// ------------------------------------------------------------------------------

//
// 数据库字段
// ------------------------------------------------------------------------------


    @ApiModelProperty("供应商id")
    @Id
    @GeneratedValue
    private Long supplierId;
    @ApiModelProperty(value = "供应商编码", required = true)
    private String supplierCode;
    @ApiModelProperty(value = "供应商姓名", required = true)
    private String supplierName;
    @ApiModelProperty(value = "启用标识。1启用，0未启用", required = true)
    private Integer enabledFlag;

//
// 非数据库字段
// ------------------------------------------------------------------------------

//
// getter/setter
// ------------------------------------------------------------------------------

    /**
     * @return 供应商id
     */
    public Long getSupplierId() {
        return supplierId;
    }

    public Supplier setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
        return this;
    }

    /**
     * @return 供应商编码
     */
    public String getSupplierCode() {
        return supplierCode;
    }

    public Supplier setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
        return this;
    }

    /**
     * @return 供应商姓名
     */
    public String getSupplierName() {
        return supplierName;
    }

    public Supplier setSupplierName(String supplierName) {
        this.supplierName = supplierName;
        return this;
    }

    /**
     * @return 启用标识。1启用，0未启用
     */
    public Integer getEnabledFlag() {
        return enabledFlag;
    }

    public Supplier setEnabledFlag(Integer enabledFlag) {
        this.enabledFlag = enabledFlag;
        return this;
    }
}
