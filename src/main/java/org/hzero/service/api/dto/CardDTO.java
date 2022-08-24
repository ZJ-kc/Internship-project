package org.hzero.service.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;


public class CardDTO {

    private Long id;

    private String i;

    private Long x;

    private Long y;

    private Long w;

    private Long h;

    private Boolean isOn;

    public Long getId() {
        return id;
    }

    public CardDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getI() {
        return i;
    }

    public CardDTO setI(String i) {
        this.i = i;
        return this;
    }

    public Long getX() {
        return x;
    }

    public CardDTO setX(Long x) {
        this.x = x;
        return this;
    }

    public Long getY() {
        return y;
    }

    public CardDTO setY(Long y) {
        this.y = y;
        return this;
    }

    public Long getW() {
        return w;
    }

    public CardDTO setW(Long w) {
        this.w = w;
        return this;
    }

    public Long getH() {
        return h;
    }

    public CardDTO setH(Long h) {
        this.h = h;
        return this;
    }

    public Boolean getIsOn() {
        return isOn;
    }

    public CardDTO setIsOn(Boolean isOn) {
        this.isOn = isOn;
        return this;
    }

    @Override
    public String toString() {
        return "CardDTO{" +
                "id=" + id +
                ", i='" + i + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", w=" + w +
                ", h=" + h +
                ", isOn=" + isOn +
                '}';
    }
}

