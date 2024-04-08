package org.pacs.visitormanagementapi.services;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.pacs.visitormanagementapi.documents.Visitor;
import org.pacs.visitormanagementapi.documents.VisitorSequence;
import org.pacs.visitormanagementapi.models.VisitorAttributesModel;
import org.pacs.visitormanagementapi.models.VisitorModel;
import org.pacs.visitormanagementapi.models.VisitorPersonalInfoModel;
import org.pacs.visitormanagementapi.models.mappers.VisitorMapper;
import org.pacs.visitormanagementapi.repositories.VisitorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
@Validated
@RequiredArgsConstructor
public class VisitorService {

    private final VisitorRepository visitorRepository;
    private final VisitorMapper visitorMapper;
    private final MongoOperations mongoOperations;

    public List<Visitor> getAllVisitors() {
        return visitorRepository.findAll();
    }

    public VisitorPersonalInfoModel getVisitorPersonalInfo(String email) {
        Visitor visitor = visitorRepository.findVisitorByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Visitor does not exist"));
        return new VisitorPersonalInfoModel(
                visitor.getId(), visitor.getSsn(), visitor.getFirstName(), visitor.getLastName(), visitor.getEmail());
    }

    public VisitorAttributesModel getVisitorAttributes(String email) {
        Visitor visitor = visitorRepository.findVisitorByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Visitor does not exist"));

        return new VisitorAttributesModel(
                visitor.getId(), visitor.getDepartment(), visitor.getTimeSchedule(),
                visitor.getClearanceLevel());
    }

    public void addVisitor(@Valid VisitorModel visitorModel) {
        visitorModel.setId(generateSequence());
        if(visitorRepository.existsVisitorBySsn(visitorModel.getSsn())) {
            throw new EntityExistsException("Visitor already exists");
        } else {
            visitorRepository.insert(visitorMapper.toVisitor(visitorModel));
        }
    }

    public void updateVisitor(@Valid VisitorModel visitorModel, String id) {
        if(!id.equals(visitorModel.getId())) throw new ValidationException("The Path ID and Request ID not matching");
        Visitor visitor = visitorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Visitor was not found"));
        BeanUtils.copyProperties(visitorModel, visitor, "id");
        visitorRepository.save(visitor);
    }

    public void deleteVisitor(String id) {
        Visitor visitorCredentials = visitorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Visitor was not found"));
        visitorRepository.delete(visitorCredentials);
    }

    private String generateSequence() {
        VisitorSequence counter = mongoOperations.findAndModify(query(where("_id").is(Visitor.SEQUENCE_NAME)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                VisitorSequence.class);
        return String.valueOf(!Objects.isNull(counter) ? counter.getSeq() : 1);
    }
}
