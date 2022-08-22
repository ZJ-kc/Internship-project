package org.hzero.service.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
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
@Table(name = "hff_repertory")
public class Repertory extends AuditDomain {

    public static final String FIELD_REPERTORY_ID = "repertoryId";
    public static final String FIELD_MATERIAL_ID = "materialId";
    public static final String FIELD_STORE_ID = "storeId";
    public static final String FIELD_REPERTORY_NUMBER = "repertoryNumber";
    public static final String FIELD_WAIT_REPERTORY_NUMBER = "waitRepertoryNumber";
    public static final String FIELD_ABLE_REPERTORY_NUMBER = "ableRepertoryNumber";

//
// 业务方法(按public protected private顺序排列)
// ------------------------------------------------------------------------------

//
// 数据库字段
// ------------------------------------------------------------------------------


    @ApiModelProperty("库存id")
    @Id
    @GeneratedValue
    private Long repertoryId;
    @ApiModelProperty(value = "物料id，外键")
    private Long materialId;
    @ApiModelProperty(value = "仓库id，外键")
    private Long storeId;
    @ApiModelProperty(value = "库存数")
    private Long repertoryNumber;
    @ApiModelProperty(value = "待发货库存数")
    private Long waitRepertoryNumber;
    @ApiModelProperty(value = "可发货库存数")
    private Long ableRepertoryNumber;

    @Transient
    private Store store;
    @Transient
    private Material material;
    @Transient
    private String storeName;
    @Transient
    private String materialCode;
    @Transient
    private String materialDescription;
    @Transient
    private String materialUnit;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public String getMaterialUnit() {
        return materialUnit;
    }

    public void setMaterialUnit(String materialUnit) {
        this.materialUnit = materialUnit;
    }

    public static String getFieldRepertoryId() {
        return FIELD_REPERTORY_ID;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
//
// 非数据库字段
// ------------------------------------------------------------------------------

//
// getter/setter
// ------------------------------------------------------------------------------

    /**
     * @return 库存id
     */
    public Long getRepertoryId() {
        return repertoryId;
    }

    public Repertory setRepertoryId(Long repertoryId) {
        this.repertoryId = repertoryId;
        return this;
    }

    /**
     * @return 物料id，外键
     */
    public Long getMaterialId() {
        return materialId;
    }

    public Repertory setMaterialId(Long materialId) {
        this.materialId = materialId;
        return this;
    }

    /**
     * @return 仓库id，外键
     */
    public Long getStoreId() {
        return storeId;
    }

    public Repertory setStoreId(Long storeId) {
        this.storeId = storeId;
        return this;
    }

    /**
     * @return 库存数
     */
    public Long getRepertoryNumber() {
        return repertoryNumber;
    }

    public Repertory setRepertoryNumber(Long repertoryNumber) {
        this.repertoryNumber = repertoryNumber;
        return this;
    }

    /**
     * @return 待发货库存数
     */
    public Long getWaitRepertoryNumber() {
        return waitRepertoryNumber;
    }

    public Repertory setWaitRepertoryNumber(Long waitRepertoryNumber) {
        this.waitRepertoryNumber = waitRepertoryNumber;
        return this;
    }

    /**
     * @return 可发货库存数
     */
    public Long getAbleRepertoryNumber() {
        return ableRepertoryNumber;
    }

    public Repertory setAbleRepertoryNumber(Long ableRepertoryNumber) {
        this.ableRepertoryNumber = ableRepertoryNumber;
        return this;
    }

    @Override
    public String toString() {
        return "Repertory{" +
                "repertoryId=" + repertoryId +
                ", materialId=" + materialId +
                ", storeId=" + storeId +
                ", repertoryNumber=" + repertoryNumber +
                ", waitRepertoryNumber=" + waitRepertoryNumber +
                ", ableRepertoryNumber=" + ableRepertoryNumber +
                ", store=" + store +
                ", material=" + material +
                '}';
    }
}
