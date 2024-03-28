package org.example.visitormanagementapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VisitorPersonalInfoModel {
    private String id;
    private String ssn;
    private String firstName;
    private String lastName;
    private String email;
}
