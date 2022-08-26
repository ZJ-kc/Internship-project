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
 * @author yuji.sun@zknow.com 2022-08-25 14:40:10
 */
@ApiModel("")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "iam_role")
public class Role extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_CODE = "code";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_FD_LEVEL = "fdLevel";
    public static final String FIELD_H_TENANT_ID = "hTenantId";
    public static final String FIELD_H_INHERIT_ROLE_ID = "hInheritRoleId";
    public static final String FIELD_H_PARENT_ROLE_ID = "hParentRoleId";
    public static final String FIELD_H_PARENT_ROLE_ASSIGN_LEVEL = "hParentRoleAssignLevel";
    public static final String FIELD_H_PARENT_ROLE_ASSIGN_LEVEL_VAL = "hParentRoleAssignLevelVal";
    public static final String FIELD_IS_ENABLED = "isEnabled";
    public static final String FIELD_IS_MODIFIED = "isModified";
    public static final String FIELD_IS_ENABLE_FORBIDDEN = "isEnableForbidden";
    public static final String FIELD_IS_BUILT_IN = "isBuiltIn";
    public static final String FIELD_IS_ASSIGNABLE = "isAssignable";
    public static final String FIELD_H_LEVEL_PATH = "hLevelPath";
    public static final String FIELD_H_INHERIT_LEVEL_PATH = "hInheritLevelPath";
    public static final String FIELD_CREATED_BY_TENANT_ID = "createdByTenantId";
    public static final String FIELD_TPL_ROLE_NAME = "tplRoleName";
    public static final String FIELD_ROLE_HIERARCHY = "roleHierarchy";
    public static final String FIELD_ENABLE_ROLE_PERMISSION = "enableRolePermission";
    public static final String FIELD_ROLE_TYPE_CODE = "roleTypeCode";

//
// 业务方法(按public protected private顺序排列)
// ------------------------------------------------------------------------------

//
// 数据库字段
// ------------------------------------------------------------------------------


    @ApiModelProperty("")
    @Id
    @GeneratedValue
    private Long id;
    @ApiModelProperty(value = "角色名", required = true)
    @NotBlank
    private String name;
    @ApiModelProperty(value = "角色编码", required = true)
    @NotBlank
    private String code;
    @ApiModelProperty(value = "角色描述full description")
    private String description;
    @ApiModelProperty(value = "角色级别", required = true)
    @NotBlank
    private String fdLevel;
    @ApiModelProperty(value = "所属租户ID")
    private Long hTenantId;
    @ApiModelProperty(value = "继承角色ID")
    private Long hInheritRoleId;
    @ApiModelProperty(value = "父级角色ID")
    private Long hParentRoleId;
    @ApiModelProperty(value = "父级角色分配层级")
    private String hParentRoleAssignLevel;
    @ApiModelProperty(value = "父级角色分配值")
    private Long hParentRoleAssignLevelVal;
    @ApiModelProperty(value = "是否启用。1启用，0未启用", required = true)
    @NotNull
    private Integer isEnabled;
    @ApiModelProperty(value = "是否可以修改。1表示可以，0不可以", required = true)
    @NotNull
    private Integer isModified;
    @ApiModelProperty(value = "是否可以被禁用", required = true)
    @NotNull
    private Integer isEnableForbidden;
    @ApiModelProperty(value = "是否内置。1表示是，0表示不是", required = true)
    @NotNull
    private Integer isBuiltIn;
    @ApiModelProperty(value = "是否禁止在更高的层次上分配，禁止project role在organization上分配。1表示可以，0表示不可以", required = true)
    @NotNull
    private Integer isAssignable;
    @ApiModelProperty(value = "")
    private String hLevelPath;
    @ApiModelProperty(value = "角色继承生成层次关系")
    private String hInheritLevelPath;
    @ApiModelProperty(value = "创建者租户ID")
    private Long createdByTenantId;
    @ApiModelProperty(value = "模板角色的子角色名称")
    private String tplRoleName;
    @ApiModelProperty(value = "角色体系，0-多级版(默认) 1-单级版", required = true)
    @NotNull
    private Integer roleHierarchy;
    @ApiModelProperty(value = "角色是否能操作数据权限等功能", required = true)
    @NotNull
    private Integer enableRolePermission;
    @ApiModelProperty(value = "角色类型：GEN-普通角色，SG-安全组角色", required = true)
    @NotBlank
    private String roleTypeCode;

//
// 非数据库字段
// ------------------------------------------------------------------------------

//
// getter/setter
// ------------------------------------------------------------------------------

    /**
     * @return
     */
    public Long getId() {
        return id;
    }

    public Role setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * @return 角色名
     */
    public String getName() {
        return name;
    }

    public Role setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return 角色编码
     */
    public String getCode() {
        return code;
    }

    public Role setCode(String code) {
        this.code = code;
        return this;
    }

    /**
     * @return 角色描述full description
     */
    public String getDescription() {
        return description;
    }

    public Role setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * @return 角色级别
     */
    public String getFdLevel() {
        return fdLevel;
    }

    public Role setFdLevel(String fdLevel) {
        this.fdLevel = fdLevel;
        return this;
    }

    /**
     * @return 所属租户ID
     */
    public Long getHTenantId() {
        return hTenantId;
    }

    public Role setHTenantId(Long hTenantId) {
        this.hTenantId = hTenantId;
        return this;
    }

    /**
     * @return 继承角色ID
     */
    public Long getHInheritRoleId() {
        return hInheritRoleId;
    }

    public Role setHInheritRoleId(Long hInheritRoleId) {
        this.hInheritRoleId = hInheritRoleId;
        return this;
    }

    /**
     * @return 父级角色ID
     */
    public Long getHParentRoleId() {
        return hParentRoleId;
    }

    public Role setHParentRoleId(Long hParentRoleId) {
        this.hParentRoleId = hParentRoleId;
        return this;
    }

    /**
     * @return 父级角色分配层级
     */
    public String getHParentRoleAssignLevel() {
        return hParentRoleAssignLevel;
    }

    public Role setHParentRoleAssignLevel(String hParentRoleAssignLevel) {
        this.hParentRoleAssignLevel = hParentRoleAssignLevel;
        return this;
    }

    /**
     * @return 父级角色分配值
     */
    public Long getHParentRoleAssignLevelVal() {
        return hParentRoleAssignLevelVal;
    }

    public Role setHParentRoleAssignLevelVal(Long hParentRoleAssignLevelVal) {
        this.hParentRoleAssignLevelVal = hParentRoleAssignLevelVal;
        return this;
    }

    /**
     * @return 是否启用。1启用，0未启用
     */
    public Integer getIsEnabled() {
        return isEnabled;
    }

    public Role setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    /**
     * @return 是否可以修改。1表示可以，0不可以
     */
    public Integer getIsModified() {
        return isModified;
    }

    public Role setIsModified(Integer isModified) {
        this.isModified = isModified;
        return this;
    }

    /**
     * @return 是否可以被禁用
     */
    public Integer getIsEnableForbidden() {
        return isEnableForbidden;
    }

    public Role setIsEnableForbidden(Integer isEnableForbidden) {
        this.isEnableForbidden = isEnableForbidden;
        return this;
    }

    /**
     * @return 是否内置。1表示是，0表示不是
     */
    public Integer getIsBuiltIn() {
        return isBuiltIn;
    }

    public Role setIsBuiltIn(Integer isBuiltIn) {
        this.isBuiltIn = isBuiltIn;
        return this;
    }

    /**
     * @return 是否禁止在更高的层次上分配，禁止project role在organization上分配。1表示可以，0表示不可以
     */
    public Integer getIsAssignable() {
        return isAssignable;
    }

    public Role setIsAssignable(Integer isAssignable) {
        this.isAssignable = isAssignable;
        return this;
    }

    /**
     * @return
     */
    public String getHLevelPath() {
        return hLevelPath;
    }

    public Role setHLevelPath(String hLevelPath) {
        this.hLevelPath = hLevelPath;
        return this;
    }

    /**
     * @return 角色继承生成层次关系
     */
    public String getHInheritLevelPath() {
        return hInheritLevelPath;
    }

    public Role setHInheritLevelPath(String hInheritLevelPath) {
        this.hInheritLevelPath = hInheritLevelPath;
        return this;
    }

    /**
     * @return 创建者租户ID
     */
    public Long getCreatedByTenantId() {
        return createdByTenantId;
    }

    public Role setCreatedByTenantId(Long createdByTenantId) {
        this.createdByTenantId = createdByTenantId;
        return this;
    }

    /**
     * @return 模板角色的子角色名称
     */
    public String getTplRoleName() {
        return tplRoleName;
    }

    public Role setTplRoleName(String tplRoleName) {
        this.tplRoleName = tplRoleName;
        return this;
    }

    /**
     * @return 角色体系，0-多级版(默认) 1-单级版
     */
    public Integer getRoleHierarchy() {
        return roleHierarchy;
    }

    public Role setRoleHierarchy(Integer roleHierarchy) {
        this.roleHierarchy = roleHierarchy;
        return this;
    }

    /**
     * @return 角色是否能操作数据权限等功能
     */
    public Integer getEnableRolePermission() {
        return enableRolePermission;
    }

    public Role setEnableRolePermission(Integer enableRolePermission) {
        this.enableRolePermission = enableRolePermission;
        return this;
    }

    /**
     * @return 角色类型：GEN-普通角色，SG-安全组角色
     */
    public String getRoleTypeCode() {
        return roleTypeCode;
    }

    public Role setRoleTypeCode(String roleTypeCode) {
        this.roleTypeCode = roleTypeCode;
        return this;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", fdLevel='" + fdLevel + '\'' +
                ", hTenantId=" + hTenantId +
                ", hInheritRoleId=" + hInheritRoleId +
                ", hParentRoleId=" + hParentRoleId +
                ", hParentRoleAssignLevel='" + hParentRoleAssignLevel + '\'' +
                ", hParentRoleAssignLevelVal=" + hParentRoleAssignLevelVal +
                ", isEnabled=" + isEnabled +
                ", isModified=" + isModified +
                ", isEnableForbidden=" + isEnableForbidden +
                ", isBuiltIn=" + isBuiltIn +
                ", isAssignable=" + isAssignable +
                ", hLevelPath='" + hLevelPath + '\'' +
                ", hInheritLevelPath='" + hInheritLevelPath + '\'' +
                ", createdByTenantId=" + createdByTenantId +
                ", tplRoleName='" + tplRoleName + '\'' +
                ", roleHierarchy=" + roleHierarchy +
                ", enableRolePermission=" + enableRolePermission +
                ", roleTypeCode='" + roleTypeCode + '\'' +
                '}';
    }
}
