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
@Table(name = "hff_client")
public class Client extends AuditDomain {

    public static final String FIELD_CLIENT_ID = "clientId";
    public static final String FIELD_CLIENT_CODE = "clientCode";
    public static final String FIELD_CLIENT_NAME = "clientName";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";

//
// 业务方法(按public protected private顺序排列)
// ------------------------------------------------------------------------------

//
// 数据库字段
// ------------------------------------------------------------------------------


    @ApiModelProperty("客户id")
    @Id
    @GeneratedValue
    private Long clientId;
    @ApiModelProperty(value = "客户编码", required = true)
    private String clientCode;
    @ApiModelProperty(value = "客户名称", required = true)
    private String clientName;
    @ApiModelProperty(value = "所属公司，外键", required = true)
    private Long companyId;
    @ApiModelProperty(value = "在职标识，1在职，0离职", required = true)
    private Integer enabledFlag;

//
// 非数据库字段
// ------------------------------------------------------------------------------

//
// getter/setter
// ------------------------------------------------------------------------------

    /**
     * @return 客户id
     */
    public Long getClientId() {
        return clientId;
    }

    public Client setClientId(Long clientId) {
        this.clientId = clientId;
        return this;
    }

    /**
     * @return 客户编码
     */
    public String getClientCode() {
        return clientCode;
    }

    public Client setClientCode(String clientCode) {
        this.clientCode = clientCode;
        return this;
    }

    /**
     * @return 客户名称
     */
    public String getClientName() {
        return clientName;
    }

    public Client setClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    /**
     * @return 所属公司，外键
     */
    public Long getCompanyId() {
        return companyId;
    }

    public Client setCompanyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    /**
     * @return 在职标识，1在职，0离职
     */
    public Integer getEnabledFlag() {
        return enabledFlag;
    }

    public Client setEnabledFlag(Integer enabledFlag) {
        this.enabledFlag = enabledFlag;
        return this;
    }
}
