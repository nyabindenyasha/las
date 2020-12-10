package com.anesuandtatenda.studentlecturerplatform;

import com.anesuandtatenda.studentlecturerplatform.local.requests.TimeSlotsRequest;
import com.anesuandtatenda.studentlecturerplatform.model.TimeSlots;
import com.anesuandtatenda.studentlecturerplatform.repo.TimeSlotRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final TimeSlotRepository timeSlotRepository;

    CommandLineAppStartupRunner(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("CommandLineRunner");

        Collection<TimeSlots> timeSlotsCollection = timeSlotRepository.findAll();

        if (timeSlotsCollection.isEmpty()) {

            TimeSlotsRequest timeSlotsRequest = new TimeSlotsRequest();

            timeSlotsRequest.setName("FIRST");
            timeSlotsRequest.setStartTime("08:00");
            timeSlotsRequest.setEndTime("10:00");

            TimeSlots timeSlots = TimeSlots.fromCommand(timeSlotsRequest);
            timeSlotRepository.save(timeSlots);

            timeSlotsRequest.setName("SECOND");
            timeSlotsRequest.setStartTime("10:15");
            timeSlotsRequest.setEndTime("12:15");

            timeSlots = TimeSlots.fromCommand(timeSlotsRequest);
            timeSlotRepository.save(timeSlots);

            timeSlotsRequest.setName("THIRD");
            timeSlotsRequest.setStartTime("13:00");
            timeSlotsRequest.setEndTime("15:00");

            timeSlots = TimeSlots.fromCommand(timeSlotsRequest);
            timeSlotRepository.save(timeSlots);

            timeSlotsRequest.setName("FOURTH");
            timeSlotsRequest.setStartTime("15:00");
            timeSlotsRequest.setEndTime("17:00");

            timeSlots = TimeSlots.fromCommand(timeSlotsRequest);
            timeSlotRepository.save(timeSlots);
        }


    }

}
