package org.hzero.service.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;

import java.util.Date;

import io.choerodon.mybatis.domain.AuditDomain;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author yuji.sun@zknow.com 2022-08-25 16:06:28
 */
@ApiModel("")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "iam_member_role")
public class MemberRole extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_ROLE_ID = "roleId";
    public static final String FIELD_MEMBER_ID = "memberId";
    public static final String FIELD_MEMBER_TYPE = "memberType";
    public static final String FIELD_SOURCE_ID = "sourceId";
    public static final String FIELD_SOURCE_TYPE = "sourceType";
    public static final String FIELD_H_ASSIGN_LEVEL = "hAssignLevel";
    public static final String FIELD_H_ASSIGN_LEVEL_VALUE = "hAssignLevelValue";
    public static final String FIELD_START_DATE_ACTIVE = "startDateActive";
    public static final String FIELD_END_DATE_ACTIVE = "endDateActive";

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
    @ApiModelProperty(value = "角色id", required = true)
    @NotNull
    private Long roleId;
    @ApiModelProperty(value = "成员id,可以是userId,clientId等，与member_type对应", required = true)
    @NotNull
    private Long memberId;
    @ApiModelProperty(value = "成员类型，默认为user，值集：HIAM.MEMBER_TYPE")
    private String memberType;
    @ApiModelProperty(value = "创建该记录的源id，可以是projectId,也可以是organizarionId等", required = true)
    @NotNull
    private Long sourceId;
    @ApiModelProperty(value = "创建该记录的源类型，sit/organization/project/user等", required = true)
    @NotBlank
    private String sourceType;
    @ApiModelProperty(value = "分配层级")
    private String hAssignLevel;
    @ApiModelProperty(value = "分配层级值")
    private Long hAssignLevelValue;
    @ApiModelProperty(value = "有效期起")
    private Date startDateActive;
    @ApiModelProperty(value = "有效期止")
    private Date endDateActive;

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

    public MemberRole setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * @return 角色id
     */
    public Long getRoleId() {
        return roleId;
    }

    public MemberRole setRoleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    /**
     * @return 成员id, 可以是userId, clientId等，与member_type对应
     */
    public Long getMemberId() {
        return memberId;
    }

    public MemberRole setMemberId(Long memberId) {
        this.memberId = memberId;
        return this;
    }

    /**
     * @return 成员类型，默认为user，值集：HIAM.MEMBER_TYPE
     */
    public String getMemberType() {
        return memberType;
    }

    public MemberRole setMemberType(String memberType) {
        this.memberType = memberType;
        return this;
    }

    /**
     * @return 创建该记录的源id，可以是projectId,也可以是organizarionId等
     */
    public Long getSourceId() {
        return sourceId;
    }

    public MemberRole setSourceId(Long sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    /**
     * @return 创建该记录的源类型，sit/organization/project/user等
     */
    public String getSourceType() {
        return sourceType;
    }

    public MemberRole setSourceType(String sourceType) {
        this.sourceType = sourceType;
        return this;
    }

    /**
     * @return 分配层级
     */
    public String getHAssignLevel() {
        return hAssignLevel;
    }

    public MemberRole setHAssignLevel(String hAssignLevel) {
        this.hAssignLevel = hAssignLevel;
        return this;
    }

    /**
     * @return 分配层级值
     */
    public Long getHAssignLevelValue() {
        return hAssignLevelValue;
    }

    public MemberRole setHAssignLevelValue(Long hAssignLevelValue) {
        this.hAssignLevelValue = hAssignLevelValue;
        return this;
    }

    /**
     * @return 有效期起
     */
    public Date getStartDateActive() {
        return startDateActive;
    }

    public MemberRole setStartDateActive(Date startDateActive) {
        this.startDateActive = startDateActive;
        return this;
    }

    /**
     * @return 有效期止
     */
    public Date getEndDateActive() {
        return endDateActive;
    }

    public MemberRole setEndDateActive(Date endDateActive) {
        this.endDateActive = endDateActive;
        return this;
    }
}
