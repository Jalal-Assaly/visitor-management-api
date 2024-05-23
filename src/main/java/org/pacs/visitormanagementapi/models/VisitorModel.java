package org.pacs.visitormanagementapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.pacs.visitormanagementapi.documents.TimeSchedule;

@Data
@AllArgsConstructor
public class VisitorModel {

    @JsonProperty("ID")
    private String id;

    @NotBlank(message = "SSN cannot be blank")
    @Pattern(regexp = "^\\d{3}-\\d{2}-\\d{4}$", message = "SSN does not follow the standard format XXX-XX-XXXX")
    @JsonProperty("SSN")
    private String ssn;

    @NotBlank(message = "First Name cannot be blank")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First Name must include letters only")
    @JsonProperty("FN")
    private String firstName;

    @NotBlank(message = "Last Name cannot be blank")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last Name must include letters only")
    @JsonProperty("LN")
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Pattern(regexp = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+", message = "Email address has an unsuitable format")
    @JsonProperty("EM")
    private String email;

    @NotBlank(message = "Department cannot be null")
    @JsonProperty("DP")
    @Size(max = 20, message = "Department field exceeds the limit of 20 characters")
    private String department;

    @Valid
    @JsonProperty("TS")
    private TimeSchedule timeSchedule;

    @NotBlank(message = "Clearance Level cannot be blank")
    @JsonProperty("CL")
    private String clearanceLevel;
}
