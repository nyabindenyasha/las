package com.anesuandtatenda.studentlecturerplatform.local.requests;

import lombok.Data;

@Data
public class ApproveAppointmentRequest {

    private long appointmentId;

    private long lecturerId;

    private long lecturerBookingId;

    private boolean isApproved;

}
