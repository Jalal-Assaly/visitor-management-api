package org.pacs.visitormanagementapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.pacs.visitormanagementapi.documents.TimeSchedule;

@Data
public class VisitorAttributesModel {
    private String id;
    private String role;
    private String department;
    private TimeSchedule timeSchedule;
    private String clearanceLevel;

    public VisitorAttributesModel(String id, String department, TimeSchedule timeSchedule, String clearanceLevel) {
        this.id = id;
        this.role = "Visitor";
        this.department = department;
        this.timeSchedule = timeSchedule;
        this.clearanceLevel = clearanceLevel;
    }
}
