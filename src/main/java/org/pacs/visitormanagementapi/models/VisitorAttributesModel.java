package org.pacs.visitormanagementapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.pacs.visitormanagementapi.documents.TimeSchedule;

@Data
public class VisitorAttributesModel {
    @JsonProperty("ID")
    private String id;
    @JsonProperty("RL")
    private String role;
    @JsonProperty("DP")
    private String department;
    @JsonProperty("TS")
    private TimeSchedule timeSchedule;
    @JsonProperty("CL")
    private String clearanceLevel;

    public VisitorAttributesModel(String id, String department, TimeSchedule timeSchedule, String clearanceLevel) {
        this.id = id;
        this.role = "Visitor";
        this.department = department;
        this.timeSchedule = timeSchedule;
        this.clearanceLevel = clearanceLevel;
    }
}
