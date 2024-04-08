package org.pacs.visitormanagementapi.repositories;

import org.pacs.visitormanagementapi.documents.Visitor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitorRepository extends MongoRepository<Visitor, String> {
    Optional<Visitor> findVisitorByEmail(String email);

    Boolean existsVisitorBySsn(String ssn);
}
