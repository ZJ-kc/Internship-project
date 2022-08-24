package org.hzero.service.api.dto;

/**
 * 待办事项dto
 *
 * @Author : SunYuji
 * @create 2022/8/23 16:12
 */
public class BackLogDTO {
    /**
     * 流程名称
     */
    private String processName;

    /**
     * 流程描述
     */
    private String processDescription;

    /**
     * 申请人
     */
    private String applicantName;

    /**
     * 停留时间
     */
    private String residenceTime;

    public String getProcessName() {
        return processName;
    }

    public BackLogDTO setProcessName(String processName) {
        this.processName = processName;
        return this;
    }

    public String getProcessDescription() {
        return processDescription;
    }

    public BackLogDTO setProcessDescription(String processDescription) {
        this.processDescription = processDescription;
        return this;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public BackLogDTO setApplicantName(String applicantName) {
        this.applicantName = applicantName;
        return this;
    }

    public String getResidenceTime() {
        return residenceTime;
    }

    public BackLogDTO setResidenceTime(String residenceTime) {
        this.residenceTime = residenceTime;
        return this;
    }

    @Override
    public String toString() {
        return "BackLogDTO{" +
                "processName='" + processName + '\'' +
                ", processDescription='" + processDescription + '\'' +
                ", applicantName='" + applicantName + '\'' +
                ", residenceTime='" + residenceTime + '\'' +
                '}';
    }
}
