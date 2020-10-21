package com.anesuandtatenda.studentlecturerplatform.local.requests;

import lombok.Data;

@Data
public class TimeSlotsRequest {

    private long id;

    private String name;

    private String startTime;

    private String endTime;

}
