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
@Table(name = "hff_material")
public class Material extends AuditDomain {

    public static final String FIELD_MATERIAL_ID = "materialId";
    public static final String FIELD_MATERIAL_CODE = "materialCode";
    public static final String FIELD_MATERIAL_DESCRIPTION = "materialDescription";
    public static final String FIELD_MATERIAL_UNIT = "materialUnit";
    public static final String FIELD_MATERIAL_PRICE = "materialPrice";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";

//
// 业务方法(按public protected private顺序排列)
// ------------------------------------------------------------------------------

//
// 数据库字段
// ------------------------------------------------------------------------------


    @ApiModelProperty("物料id")
    @Id
    @GeneratedValue
    private Long materialId;
    @ApiModelProperty(value = "物料编码", required = true)
    private String materialCode;
    @ApiModelProperty(value = "物料描述", required = true)
    private String materialDescription;
    @ApiModelProperty(value = "物料单位", required = true)
    private String materialUnit;
    @ApiModelProperty(value = "物料单价", required = true)
    private BigDecimal materialPrice;
    @ApiModelProperty(value = "是否启用，1启用，0未启用", required = true)
    private Integer enabledFlag;

//
// 非数据库字段
// ------------------------------------------------------------------------------

//
// getter/setter
// ------------------------------------------------------------------------------

    /**
     * @return 物料id
     */
    public Long getMaterialId() {
        return materialId;
    }

    public Material setMaterialId(Long materialId) {
        this.materialId = materialId;
        return this;
    }

    /**
     * @return 物料编码
     */
    public String getMaterialCode() {
        return materialCode;
    }

    public Material setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
        return this;
    }

    /**
     * @return 物料描述
     */
    public String getMaterialDescription() {
        return materialDescription;
    }

    public Material setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
        return this;
    }

    /**
     * @return 物料单位
     */
    public String getMaterialUnit() {
        return materialUnit;
    }

    public Material setMaterialUnit(String materialUnit) {
        this.materialUnit = materialUnit;
        return this;
    }

    /**
     * @return 物料单价
     */
    public BigDecimal getMaterialPrice() {
        return materialPrice;
    }

    public Material setMaterialPrice(BigDecimal materialPrice) {
        this.materialPrice = materialPrice;
        return this;
    }

    /**
     * @return 是否启用，1启用，0未启用
     */
    public Integer getEnabledFlag() {
        return enabledFlag;
    }

    public Material setEnabledFlag(Integer enabledFlag) {
        this.enabledFlag = enabledFlag;
        return this;
    }
}
