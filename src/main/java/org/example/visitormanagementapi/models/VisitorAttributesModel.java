package org.example.visitormanagementapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.visitormanagementapi.documents.TimeSchedule;

import java.util.Map;

@Data
@AllArgsConstructor
public class VisitorAttributesModel {
    private String id;
    private String role;
    private TimeSchedule timeSchedule;
    private String clearanceLevel;
}
