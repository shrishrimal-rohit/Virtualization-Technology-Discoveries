package PerformanceMonitoring.Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Varun on 5/5/2015.
 */
@Document
@JsonAutoDetect
public class VMTask {
    @JsonProperty
    private String vmName;
    @JsonProperty
    private String vmTask;
    @JsonProperty
    private String vmTaskStatus;

    @JsonProperty("vmName")
    public String getVmName() {
        return vmName;
    }

    @JsonProperty("vmName")
    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    @JsonProperty("vmTask")
    public String getVmTask() {
        return vmTask;
    }

    @JsonProperty("vmTask")
    public void setVmTask(String vmTask) {
        this.vmTask = vmTask;
    }

    @JsonProperty("vmTaskStatus")
    public String getVmTaskStatus() {
        return vmTaskStatus;
    }

    @JsonProperty("vmTaskStatus")
    public void setVmTaskStatus(String vmTaskStatus) {
        this.vmTaskStatus = vmTaskStatus;
    }
}
