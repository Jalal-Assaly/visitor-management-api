package org.pacs.visitormanagementapi.documents;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;
import java.util.Set;

@Data
@AllArgsConstructor
public class TimeSchedule {
    @JsonProperty("ST")
    @NotNull(message = "Start time can not be null")
    private LocalTime startTime;
    @JsonProperty("ET")
    @NotNull(message = "The end time can not be null")
    private LocalTime endTime;
    @JsonProperty("DW")
    @NotEmpty(message = "The week schedule can not be empty")
    private Set<String> daysOfWeek;
}
