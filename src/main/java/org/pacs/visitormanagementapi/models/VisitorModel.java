package org.pacs.visitormanagementapi.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.pacs.visitormanagementapi.documents.TimeSchedule;

@Data
@AllArgsConstructor
public class VisitorModel {

    private String id;

    @NotBlank(message = "SSN cannot be blank")
    @Pattern(regexp = "^\\d{3}-\\d{2}-\\d{4}$", message = "SSN does not follow the standard format")
    private String ssn;

    @NotBlank(message = "First Name cannot be blank")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First Name must include letters only")
    private String firstName;

    @NotBlank(message = "Last Name cannot be blank")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last Name must include letters only")
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Given Email address has an unsuitable format")
    private String email;

    @NotBlank(message = "Department cannot be null")
    private String department;

    @Valid
    private TimeSchedule timeSchedule;

    @NotBlank(message = "Clearance Level cannot be blank")
    private String clearanceLevel;
}
