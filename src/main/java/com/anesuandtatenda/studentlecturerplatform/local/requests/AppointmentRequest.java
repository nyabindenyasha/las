package com.anesuandtatenda.studentlecturerplatform.local.requests;

import lombok.Data;

import java.util.Date;

@Data
public class AppointmentRequest {

    private long id;

    private Date date;

    private boolean isOpen;

    private String comment;

    private boolean isApproaved;

    private int anticipatedDuration;

    private long appointmentWith;

    private long appointmentBy;
}
