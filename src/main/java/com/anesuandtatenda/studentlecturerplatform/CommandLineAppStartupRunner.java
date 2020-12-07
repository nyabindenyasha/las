package com.anesuandtatenda.studentlecturerplatform;

import com.anesuandtatenda.studentlecturerplatform.local.requests.TimeSlotsRequest;
import com.anesuandtatenda.studentlecturerplatform.model.TimeSlots;
import com.anesuandtatenda.studentlecturerplatform.service.TimeSlotService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final TimeSlotService timeSlotService;

    CommandLineAppStartupRunner(TimeSlotService timeSlotService){
        this.timeSlotService = timeSlotService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("CommandLineRunner");

        Collection<TimeSlots> timeSlotsCollection = timeSlotService.findAll();

        if (timeSlotsCollection.isEmpty() || timeSlotsCollection.size() == 0) {

            TimeSlotsRequest timeSlotsRequest = new TimeSlotsRequest();

            timeSlotsRequest.setName("FIRST");
            timeSlotsRequest.setStartTime("08:00");
            timeSlotsRequest.setStartTime("10:00");

            timeSlotService.create(timeSlotsRequest);

            timeSlotsRequest.setName("SECOND");
            timeSlotsRequest.setStartTime("10:15");
            timeSlotsRequest.setStartTime("12:15");

            timeSlotService.create(timeSlotsRequest);

            timeSlotsRequest.setName("THIRD");
            timeSlotsRequest.setStartTime("13:00");
            timeSlotsRequest.setStartTime("15:00");

            timeSlotService.create(timeSlotsRequest);

            timeSlotsRequest.setName("FIRST");
            timeSlotsRequest.setStartTime("15:00");
            timeSlotsRequest.setStartTime("17:00");

            timeSlotService.create(timeSlotsRequest);
        }


    }

}
