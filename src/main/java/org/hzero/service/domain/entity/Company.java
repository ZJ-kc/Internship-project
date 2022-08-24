package org.hzero.service.domain.entity;

import java.util.List;
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
@Table(name = "hff_company")
public class Company extends AuditDomain {

    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_COMPANY_CODE = "companyCode";
    public static final String FIELD_COMPANY_NAME = "companyName";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";

//
// 业务方法(按public protected private顺序排列)
// ------------------------------------------------------------------------------

//
// 数据库字段
// ------------------------------------------------------------------------------


    @ApiModelProperty("公司id")
    @Id
    @GeneratedValue
    private Long companyId;
    @ApiModelProperty(value = "公司编码", required = true)
    private String companyCode;
    @ApiModelProperty(value = "公司名称", required = true)
    private String companyName;
    @ApiModelProperty(value = "启用标识，1启用，0未启用", required = true)
    private Integer enabledFlag;

    @Transient
    private List<Client> children;

    @Transient
    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Client> getChildren() {
        return children;
    }

    public void setChildren(List<Client> children) {
        this.children = children;
    }

    //
// 非数据库字段
// ------------------------------------------------------------------------------

//
// getter/setter
// ------------------------------------------------------------------------------

    /**
     * @return 公司id
     */
    public Long getCompanyId() {
        return companyId;
    }

    public Company setCompanyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    /**
     * @return 公司编码
     */
    public String getCompanyCode() {
        return companyCode;
    }

    public Company setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    /**
     * @return 公司名称
     */
    public String getCompanyName() {
        return companyName;
    }

    public Company setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    /**
     * @return 启用标识，1启用，0未启用
     */
    public Integer getEnabledFlag() {
        return enabledFlag;
    }

    public Company setEnabledFlag(Integer enabledFlag) {
        this.enabledFlag = enabledFlag;
        return this;
    }
}
