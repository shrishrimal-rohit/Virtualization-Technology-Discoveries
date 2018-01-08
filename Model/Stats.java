package PerformanceMonitoring.Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@JsonAutoDetect
public class Stats {

    @Id
    @JsonProperty
    private String id;
    //private String timestamp;
    private String vmName;
    private String timestamp;
    private Integer cpuUsage;
    private Integer memoryUsage;
    private Integer netUsage;
    private Integer diskUsage;

    public Stats(){}

    public Stats(//@JsonProperty("timestamp") String timestamp,
                 @JsonProperty("vmName") String vmName,
                 @JsonProperty("timestamp") String timestamp,
                 @JsonProperty("cpuUsage") Integer cpuUsage,
                 @JsonProperty("memoryUsage") Integer memoryUsage,
                 @JsonProperty("netUsage") Integer netUsage,
                 @JsonProperty("diskUsage") Integer diskUsage)
                {
                   // this.timestamp = timestamp;
                    this.vmName = vmName;
                    this.timestamp = timestamp;
                    this.cpuUsage = cpuUsage;
                    this.memoryUsage = memoryUsage;
                    this.netUsage = netUsage;
                    this.diskUsage = diskUsage;
                }

    public Stats(String id,
                 //String timestamp,
                 String vmName,
                 String timestamp,
                 Integer cpuUsage,
                 Integer memoryUsage,
                 Integer netUsage,
                 Integer diskUsage)
    {
        this.id = id;
        this.vmName = vmName;
        this.timestamp = timestamp;
        this.cpuUsage = cpuUsage;
        this.memoryUsage = memoryUsage;
        this.netUsage = netUsage;
        this.diskUsage = diskUsage;

    }


    //getter and setters

    public Integer getDiskUsage() { return diskUsage;   }

    public void setDiskUsage(Integer diskUsage) {   this.diskUsage = diskUsage; }

    public Integer getNetUsage() { return netUsage; }

    public void setNetUsage(Integer netUsage) { this.netUsage = netUsage;   }

    public Integer getMemoryUsage() {   return memoryUsage; }

    public void setMemoryUsage(Integer memoryUsage) {   this.memoryUsage = memoryUsage; }

    public Integer getCpuUsage() {  return cpuUsage;    }

    public void setCpuUsage(Integer cpuUsage) { this.cpuUsage = cpuUsage;   }

    public String gettimestamp() {  return timestamp;   }

    public void settimestamp(String timestamp) {    this.timestamp = timestamp; }

    public String getVmName() { return vmName;  }

    public void setVmName(String vmName) {  this.vmName = vmName;   }

    public String getId() { return id;  }

    public void setId(String id) {  this.id = id;   }


}
