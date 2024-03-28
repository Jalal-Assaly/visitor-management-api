package org.example.visitormanagementapi.models.mappers;

import org.example.visitormanagementapi.documents.Visitor;
import org.example.visitormanagementapi.models.VisitorModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisitorMapper {
    VisitorModel toVisitorModel(Visitor visitor);
    Visitor toVisitor(VisitorModel visitorModel);
}
