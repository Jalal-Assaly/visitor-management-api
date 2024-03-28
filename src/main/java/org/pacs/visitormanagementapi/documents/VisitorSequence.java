package org.pacs.visitormanagementapi.documents;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "visitorsSequence")
@Data
@AllArgsConstructor
public class VisitorSequence {
    @Id
    private String id;
    private long seq;
}