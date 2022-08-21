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
@Table(name = "hff_store")
public class Store extends AuditDomain {

    public static final String FIELD_STORE_ID = "storeId";
    public static final String FIELD_STROE_CODE = "stroeCode";
    public static final String FIELD_STORE_NAME = "storeName";
    public static final String FIELD_STORE_ADDRESS = "storeAddress";

//
// 业务方法(按public protected private顺序排列)
// ------------------------------------------------------------------------------

//
// 数据库字段
// ------------------------------------------------------------------------------


    @ApiModelProperty("仓库id")
    @Id
    @GeneratedValue
    private Long storeId;

    @ApiModelProperty(value = "仓库编码", required = true)
    private String storeCode;

    @ApiModelProperty(value = "仓库名称", required = true)
    private String storeName;
    @ApiModelProperty(value = "仓库地址", required = true)
    private String storeAddress;
    @ApiModelProperty(value = "1 启用 0未启用", required = true)
    private String storeState;

//
// 非数据库字段
// ------------------------------------------------------------------------------

//
// getter/setter
// ------------------------------------------------------------------------------

    /**
     * @return 仓库id
     */
    public Long getStoreId() {
        return storeId;
    }

    public Store setStoreId(Long storeId) {
        this.storeId = storeId;
        return this;
    }

    /**
     * @return 仓库编码
     */
    public String getStroeCode() {
        return storeCode;
    }

    public Store setStoreCode(String storeCode) {
        this.storeCode = storeCode;
        return this;
    }

    /**
     * @return 仓库名称
     */
    public String getStoreName() {
        return storeName;
    }

    public Store setStoreName(String storeName) {
        this.storeName = storeName;
        return this;
    }

    /**
     * @return 仓库地址
     */
    public String getStoreAddress() {
        return storeAddress;
    }

    public Store setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
        return this;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public String getStoreState() {
        return storeState;
    }

    public void setStoreState(String storeState) {
        this.storeState = storeState;
    }
}
