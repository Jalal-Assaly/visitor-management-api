package org.example.visitormanagementapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.visitormanagementapi.documents.TimeSchedule;

@Data
@AllArgsConstructor
public class VisitorModel {
    private String id;
    private String ssn;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private TimeSchedule timeSchedule;
    private String clearanceLevel;
}
