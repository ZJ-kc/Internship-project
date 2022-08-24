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
 * @author jiao.zhao@zknown.com 2022-08-23 17:39:12
 */
@ApiModel("")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "hff_card")
public class Card extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_I = "i";
    public static final String FIELD_X = "x";
    public static final String FIELD_Y = "y";
    public static final String FIELD_W = "w";
    public static final String FIELD_H = "h";
    public static final String FIELD_IS_ON = "isOn";

//
// 业务方法(按public protected private顺序排列)
// ------------------------------------------------------------------------------

//
// 数据库字段
// ------------------------------------------------------------------------------


    @ApiModelProperty("唯一编码")
    @Id
    @GeneratedValue
    private Long id;
    @ApiModelProperty(value = "名称", required = true)
    @NotBlank
    private String i;
    @ApiModelProperty(value = "x坐标", required = true)
    @NotNull
    private Long x;
    @ApiModelProperty(value = "y坐标", required = true)
    @NotNull
    private Long y;
    @ApiModelProperty(value = "宽", required = true)
    @NotNull
    private Long w;
    @ApiModelProperty(value = "高", required = true)
    @NotNull
    private Long h;
    @ApiModelProperty(value = "是否启用，0未启用，1启用，默认1", required = true)
    @NotNull
    private Boolean isOn;

//
// 非数据库字段
// ------------------------------------------------------------------------------

//
// getter/setter
// ------------------------------------------------------------------------------

    /**
     * @return 唯一编码
     */
    public Long getId() {
        return id;
    }

    public Card setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * @return 名称
     */
    public String getI() {
        return i;
    }

    public Card setI(String i) {
        this.i = i;
        return this;
    }

    /**
     * @return x坐标
     */
    public Long getX() {
        return x;
    }

    public Card setX(Long x) {
        this.x = x;
        return this;
    }

    /**
     * @return y坐标
     */
    public Long getY() {
        return y;
    }

    public Card setY(Long y) {
        this.y = y;
        return this;
    }

    /**
     * @return 宽
     */
    public Long getW() {
        return w;
    }

    public Card setW(Long w) {
        this.w = w;
        return this;
    }

    /**
     * @return 高
     */
    public Long getH() {
        return h;
    }

    public Card setH(Long h) {
        this.h = h;
        return this;
    }

    /**
     * @return 是否启用，0未启用，1启用，默认1
     */
    public Boolean getIsOn() {
        return isOn;
    }

    public Card setIsOn(Boolean isOn) {
        this.isOn = isOn;
        return this;
    }
}
