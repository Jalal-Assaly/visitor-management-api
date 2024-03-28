package org.pacs.visitormanagementapi.models.mappers;

import org.pacs.visitormanagementapi.documents.Visitor;
import org.pacs.visitormanagementapi.models.VisitorModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisitorMapper {
    VisitorModel toVisitorModel(Visitor visitor);
    Visitor toVisitor(VisitorModel visitorModel);
}
