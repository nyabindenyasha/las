package com.anesuandtatenda.studentlecturerplatform.local.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class AppointmentRequest {

    private long id;

    @JsonIgnore
    private String dateString;

    private Date date;

    private boolean isOpen;

    private String comment;

    private boolean isApproaved;

    private long anticipatedDuration;

    private long appointmentWith;

    private long appointmentBy;
}
