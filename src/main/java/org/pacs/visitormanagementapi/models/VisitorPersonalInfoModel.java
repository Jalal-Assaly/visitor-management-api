package org.pacs.visitormanagementapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VisitorPersonalInfoModel {
    @JsonProperty("ID")
    private String id;
    @JsonProperty("SSN")
    private String ssn;
    @JsonProperty("FN")
    private String firstName;
    @JsonProperty("LN")
    private String lastName;
    @JsonProperty("EM")
    private String email;
}
