package PerformanceMonitoring.Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Varun on 5/5/2015.
 */
@Document
@JsonAutoDetect
public class VM {

    @JsonProperty
    private String name;

    @JsonProperty
    private String guestOS;

    @JsonProperty
    private String guestState;

    @JsonProperty
    private String powerState;

    @JsonProperty
    private String hostName;

    @JsonProperty
    private String hostMemoryUsage;

    @JsonProperty
    private String guestMemoryUsage;

    @JsonProperty
    private String guestCPUUsage;

    @JsonProperty
    private String guestUpTime;

    public VM(){

    }

    @JsonProperty("guestOS")
    public String getGuestOS() {
        return guestOS;
    }

    @JsonProperty("guestOS")
    public void setGuestOS(String guestOS) {
        this.guestOS = guestOS;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("guestState")
    public String getGuestState() {
        return guestState;
    }

    @JsonProperty("guestState")
    public void setGuestState(String guestState) {
        this.guestState = guestState;
    }

    @JsonProperty("powerState")
    public String getPowerState() {
        return powerState;
    }

    @JsonProperty("powerState")
    public void setPowerState(String powerState) {
        this.powerState = powerState;
    }

    @JsonProperty("hostName")
    public String getHostName() {
        return hostName;
    }

    @JsonProperty("hostName")
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    @JsonProperty("hostMemoryUsage")
    public String getHostMemoryUsage() {
        return hostMemoryUsage;
    }

    @JsonProperty("hostMemoryUsage")
    public void setHostMemoryUsage(String hostMemoryUsage) {
        this.hostMemoryUsage = hostMemoryUsage;
    }

    @JsonProperty("guestMemoryUsage")
    public String getGuestMemoryUsage() {
        return guestMemoryUsage;
    }

    @JsonProperty("guestMemoryUsage")
    public void setGuestMemoryUsage(String guestMemoryUsage) {
        this.guestMemoryUsage = guestMemoryUsage;
    }

    @JsonProperty("guestCPUUsage")
    public String getGuestCPUUsage() {
        return guestCPUUsage;
    }

    @JsonProperty("guestCPUUsage")
    public void setGuestCPUUsage(String guestCPUUsage) {
        this.guestCPUUsage = guestCPUUsage;
    }

    @JsonProperty("guestUpTime")
    public String getGuestUpTime() {
        return guestUpTime;
    }

    @JsonProperty("guestUpTime")
    public void setGuestUpTime(String guestUpTime) {
        this.guestUpTime = guestUpTime;
    }
}
