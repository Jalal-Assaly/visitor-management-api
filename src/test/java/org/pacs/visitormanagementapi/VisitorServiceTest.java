package org.pacs.visitormanagementapi;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pacs.visitormanagementapi.documents.TimeSchedule;
import org.pacs.visitormanagementapi.documents.Visitor;
import org.pacs.visitormanagementapi.models.VisitorAttributesModel;
import org.pacs.visitormanagementapi.models.VisitorModel;
import org.pacs.visitormanagementapi.models.VisitorPersonalInfoModel;
import org.pacs.visitormanagementapi.models.mappers.VisitorMapper;
import org.pacs.visitormanagementapi.repositories.VisitorRepository;
import org.pacs.visitormanagementapi.services.VisitorService;
import org.springframework.data.mongodb.core.MongoOperations;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VisitorServiceTest {

    @Mock
    private VisitorRepository visitorRepository;
    @Mock
    private VisitorMapper visitorMapper;
    @Mock
    private MongoOperations mongoOperations;

    @InjectMocks
    private VisitorService visitorService;


    // Some properties
    Visitor testVisitor1;
    VisitorModel testVisitorModel1;
    VisitorPersonalInfoModel testVisitorPersonalInfoModel1;
    VisitorAttributesModel testVisitorAttributesModel1;
    Visitor testVisitor2;
    VisitorModel testVisitorModel2;
    VisitorPersonalInfoModel testVisitorPersonalInfoModel2;
    VisitorAttributesModel testVisitorAttributesModel2;
    List<Visitor> testVisitorList;

    @BeforeEach
    void init() {
        // Initialize some time schedules
        TimeSchedule timeSchedule1 = new TimeSchedule(
                LocalTime.of(9, 0),
                LocalTime.of(17, 0),
                new HashSet<>(List.of("Friday")));

        TimeSchedule timeSchedule2 = new TimeSchedule(
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                new HashSet<>(List.of("Monday")));

        // Initial Setup - Visitor Entities
        testVisitor1 = new Visitor(
                "3",
                "432-33-3425",
                "Mike",
                "Johns",
                "mike.johns@example.com",
                "HR",
                timeSchedule1,
                "Level 1");

        testVisitorModel1 = new VisitorModel(
                "3",
                "432-33-3425",
                "Mike",
                "Johns",
                "mike.johns@example.com",
                "HR",
                timeSchedule1,
                "Level 1");

        testVisitorPersonalInfoModel1 = new VisitorPersonalInfoModel(
                "3",
                "432-33-3425",
                "Mike",
                "Johns",
                "mike.johns@example.com"
        );

        testVisitorAttributesModel1 = new VisitorAttributesModel(
                "3",
                "HR",
                timeSchedule1,
                "Level 1"
        );

        testVisitor2 = new Visitor(
                "53",
                "543-93-5322",
                "Laury",
                "Parker",
                "laury.parker@example.com",
                "IT",
                timeSchedule2,
                "Level 2");


        testVisitorModel2 = new VisitorModel(
                "53",
                "543-93-5322",
                "Laury",
                "Parker",
                "laury.parker@example.com",
                "IT",
                timeSchedule2,
                "Level 2");

        testVisitorPersonalInfoModel2 = new VisitorPersonalInfoModel(
                "53",
                "543-93-5322",
                "Laury",
                "Parker",
                "laury.parker@example.com"
        );

        testVisitorAttributesModel2 = new VisitorAttributesModel(
                "53",
                "IT",
                timeSchedule2,
                "Level 2"
        );


        // Initial Setup - Visitors List
        testVisitorList = Arrays.asList(testVisitor1, testVisitor2);
    }

    @Test void testGetAllVisitors() {
        when(visitorRepository.findAll()).thenReturn(testVisitorList);
        when(visitorMapper.toVisitorModel(testVisitor1)).thenReturn(testVisitorModel1);
        when(visitorMapper.toVisitorModel(testVisitor2)).thenReturn(testVisitorModel2);

        List<VisitorModel> actualVisitors = visitorService.getAllVisitors();

        verify(visitorRepository).findAll();
        assertThat(actualVisitors).hasSize(2);
        assertThat(actualVisitors.get(0)).usingRecursiveComparison().isEqualTo(testVisitorModel1);
        assertThat(actualVisitors.get(1)).usingRecursiveComparison().isEqualTo(testVisitorModel2);
    }

    @Test void testGetVisitorPersonalInfo_Success() {
        when(visitorRepository.findVisitorByEmail(testVisitor1.getEmail())).thenReturn(Optional.of(testVisitor1));

        VisitorPersonalInfoModel actualVisitorPersonalInfoModel =
                visitorService.getVisitorPersonalInfo(testVisitor1.getEmail());

        verify(visitorRepository).findVisitorByEmail(any(String.class));
        assertThat(actualVisitorPersonalInfoModel).usingRecursiveComparison().isEqualTo(testVisitorPersonalInfoModel1);
    }

    @Test void testGetVisitorPersonalInfo_NotFound() {
        when(visitorRepository.findVisitorByEmail("xyz")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> visitorService.getVisitorPersonalInfo("xyz"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Visitor does not exist");
    }

    @Test void testGetVisitorAttributes_Success() {
        when(visitorRepository.findVisitorByEmail(testVisitor2.getEmail())).thenReturn(Optional.of(testVisitor2));

        VisitorAttributesModel actualVisitorAttributes =
                visitorService.getVisitorAttributes(testVisitor2.getEmail());

        verify(visitorRepository).findVisitorByEmail(any(String.class));
        assertThat(actualVisitorAttributes).usingRecursiveComparison().isEqualTo(testVisitorAttributesModel2);
    }

    @Test void testGetVisitorAttributes_NotFound() {
        when(visitorRepository.findVisitorByEmail("xyz")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> visitorService.getVisitorAttributes("xyz"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Visitor does not exist");
    }

    @Test void testAddVisitor_Success() {
        // Arrange
        when(visitorMapper.toVisitor(testVisitorModel1)).thenReturn(testVisitor1);
        when(visitorRepository.existsVisitorBySsn(testVisitor1.getSsn())).thenReturn(false);

        // Act
        visitorService.addVisitor(testVisitorModel1);

        // Verify & Assert
        verify(visitorMapper).toVisitor(any(VisitorModel.class));
        verify(visitorRepository).existsVisitorBySsn(any(String.class));
        verify(visitorRepository).save(any(Visitor.class));
    }

    @Test void testAddVisitor_AlreadyExists() {
        // Arrange
        when(visitorRepository.existsVisitorBySsn(testVisitor1.getSsn())).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> visitorService.addVisitor(testVisitorModel1))
                .isInstanceOf(EntityExistsException.class)
                .hasMessage("Visitor already exists");

        // Verify No interaction
        verify(visitorRepository, never()).insert(any(Visitor.class));
    }

    @Test void updateVisitor_Success() {
        // Arrange
        VisitorModel updatedVisitorModel = new VisitorModel("53",
                "543-93-5322",
                "Laury",
                "Parker",
                "laury.parker@example.com",
                "IT",
                new TimeSchedule(LocalTime.of(15, 0),   // Changed
                        LocalTime.of(16, 0),            // Changed
                        new HashSet<>(List.of("Tuesday"))),     // Changed
                "Level 2");
        when(visitorRepository.findById(testVisitor2.getId())).thenReturn(Optional.of(testVisitor2));

        // Act
        visitorService.updateVisitor(updatedVisitorModel, "53");

        // Verify & Assert
        verify(visitorRepository).findById(any(String.class));
        verify(visitorRepository).save(any(Visitor.class));
        assertThat(testVisitor2.getTimeSchedule()).isEqualTo(updatedVisitorModel.getTimeSchedule());
    }

    @Test void updateVisitor_NotFound() {
        // Arrange
        VisitorModel updatedVisitorModel = new VisitorModel("xyz",
                "543-93-5322",
                "Laury",
                "Parker",
                "laury.parker@example.com",
                "IT",
                new TimeSchedule(LocalTime.of(15, 0),
                        LocalTime.of(16, 0),        // Changed appointment hour
                        new HashSet<>(List.of("Tuesday"))), // Changed days of work
                "Level 2");
        when(visitorRepository.findById("xyz")).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> visitorService.updateVisitor(updatedVisitorModel, "xyz"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Visitor was not found");

        // Verify No interaction
        verify(visitorRepository, never()).save(any(Visitor.class));
    }

    @Test void updateVisitor_IdPathRequestMismatch() {
        // Arrange
        VisitorModel updatedVisitorModel = new VisitorModel("xyz",
                "543-93-5322",
                "Laury",
                "Parker",
                "laury.parker@example.com",
                "IT",
                new TimeSchedule(LocalTime.of(15, 0),
                        LocalTime.of(16, 0),        // Changed appointment hour
                        new HashSet<>(List.of("Tuesday"))), // Changed days of work
                "Level 2");

        // Act & Assert
        assertThatThrownBy(() -> visitorService.updateVisitor(updatedVisitorModel, "53"))
                .isInstanceOf(ValidationException.class)
                .hasMessage("The Path ID and Request ID not matching");

        // Verify No interaction
        verify(visitorRepository, never()).save(any(Visitor.class));
    }

    @Test void deleteVisitor_Success() {
        // Arrange
        when(visitorRepository.findById(testVisitor2.getId())).thenReturn(Optional.of(testVisitor2));

        // Act
        visitorService.deleteVisitor(testVisitor2.getId());

        // Verify & Assert
        verify(visitorRepository).findById(testVisitor2.getId());
        verify(visitorRepository).delete(testVisitor2);
    }

    @Test void deleteVisitor_NotFound() {
        // Arrange
        when(visitorRepository.findById("xyz")).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> visitorService.deleteVisitor("xyz"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Visitor was not found");

        //Verify
        verify(visitorRepository, never()).delete(any(Visitor.class));
    }
}
