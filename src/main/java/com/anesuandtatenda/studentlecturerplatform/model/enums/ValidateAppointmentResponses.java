package com.anesuandtatenda.studentlecturerplatform.model.enums;

public enum ValidateAppointmentResponses {

    Response1("NO_MORE_TIME_SLOT_FOR_THE_DAY(FULLY_BOOKED)"),
    Response2("LECTURER_HAS_NO_TIME_SLOT_TODAY"),
    Response3("LECTURER_NOT_FREE_AT_YOUR_REQUESTED_TIME");

    private final String responseName;

    ValidateAppointmentResponses(String responseName) {
        this.responseName = responseName;
    }

    public String getAppointmentResponse() {
        return responseName;
    }
}
