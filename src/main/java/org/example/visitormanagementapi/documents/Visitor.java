package org.example.visitormanagementapi.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "visitors")
public class Visitor {
    @Transient
    public static final String SEQUENCE_NAME = "visitor_sequence";

    @Id
    private String id;
    private String ssn;
    private String firstName;
    private String lastName;
    @HashIndexed
    private String email;

    private String role;
    private TimeSchedule timeSchedule;
    private String clearanceLevel;
}
