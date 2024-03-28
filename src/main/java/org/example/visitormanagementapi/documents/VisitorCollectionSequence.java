package org.example.visitormanagementapi.documents;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "visitorSequence")
@Data
@AllArgsConstructor
public class VisitorCollectionSequence {
    @Id
    private String id;
    private long seq;
}