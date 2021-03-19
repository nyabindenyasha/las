package com.anesuandtatenda.studentlecturerplatform.local.requests;

import lombok.Data;

@Data
public class TimeSlotLecturerRequest {

    private long dayOfWeek;

    private long lecturerId;

    private long timeSlotId;
}
