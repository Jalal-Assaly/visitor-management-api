package org.pacs.visitormanagementapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.pacs.visitormanagementapi.documents.TimeSchedule;

@Data
@AllArgsConstructor
public class VisitorModel {

    //todo: Add validation annotations
    private String id;
    private String ssn;
    private String firstName;
    private String lastName;
    private String email;
    private String department;
    private TimeSchedule timeSchedule;
    private String clearanceLevel;
}
