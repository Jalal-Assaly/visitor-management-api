package org.pacs.visitormanagementapi.controllers;

import lombok.RequiredArgsConstructor;
import org.pacs.visitormanagementapi.documents.Visitor;
import org.pacs.visitormanagementapi.models.VisitorAttributesModel;
import org.pacs.visitormanagementapi.models.VisitorModel;
import org.pacs.visitormanagementapi.models.VisitorPersonalInfoModel;
import org.pacs.visitormanagementapi.services.VisitorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visitor")
public class VisitorController {

    private final VisitorService visitorService;

    @GetMapping("/list")
    public ResponseEntity<List<Visitor>> getAllVisitors() {
        List<Visitor> visitors = visitorService.getAllVisitors();
        return new ResponseEntity<>(visitors, HttpStatus.OK);
    }

    @GetMapping("/find/info")
    public ResponseEntity<VisitorPersonalInfoModel> getVisitorPersonalInfo(@RequestParam String email) {
        VisitorPersonalInfoModel visitorPersonalInfo = visitorService.getVisitorPersonalInfo(email);
        return new ResponseEntity<>(visitorPersonalInfo, HttpStatus.OK);
    }

    @GetMapping("/find/attributes")
    public ResponseEntity<VisitorAttributesModel> getVisitorAttributes(@RequestParam String email) {
        VisitorAttributesModel visitorAttributes = visitorService.getVisitorAttributes(email);
        return new ResponseEntity<>(visitorAttributes, HttpStatus.OK);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Void> createNewVisitorAttributes(@RequestBody VisitorModel visitorModel) {
        visitorService.addVisitor(visitorModel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(path = "/update/id/{id}")
    public ResponseEntity<Void> updateVisitorAttributes(@RequestBody VisitorModel visitorModel, @PathVariable String id) {
        visitorService.updateVisitor(visitorModel, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/id/{id}")
    public ResponseEntity<Void> deleteVisitorAttributes(@PathVariable String id) {
        visitorService.deleteVisitorAttributes(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
