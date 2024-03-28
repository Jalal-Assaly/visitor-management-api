package org.pacs.visitormanagementapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.pacs.visitormanagementapi.documents.TimeSchedule;

@Data
@AllArgsConstructor
public class VisitorAttributesModel {
    private String id;
    private String role;
    private TimeSchedule timeSchedule;
    private String clearanceLevel;
}
