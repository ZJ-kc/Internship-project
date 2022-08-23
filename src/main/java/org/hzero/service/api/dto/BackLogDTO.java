package org.hzero.service.api.dto;

/**
 * 待办事项dto
 *
 * @Author : SunYuji
 * @create 2022/8/23 16:12
 */
public class BackLogDTO {
    private String processName;
    private String processDescription;
    private String applicantName;
    private String residenceTime;

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessDescription() {
        return processDescription;
    }

    public void setProcessDescription(String processDescription) {
        this.processDescription = processDescription;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getResidenceTime() {
        return residenceTime;
    }

    public void setResidenceTime(String residenceTime) {
        this.residenceTime = residenceTime;
    }
}
