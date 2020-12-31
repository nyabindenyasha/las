package com.anesuandtatenda.studentlecturerplatform;

import com.anesuandtatenda.studentlecturerplatform.local.requests.ProgramCreateRequest;
import com.anesuandtatenda.studentlecturerplatform.local.requests.TimeSlotsRequest;
import com.anesuandtatenda.studentlecturerplatform.model.Facaulty;
import com.anesuandtatenda.studentlecturerplatform.model.Programs;
import com.anesuandtatenda.studentlecturerplatform.model.TimeSlots;
import com.anesuandtatenda.studentlecturerplatform.model.enums.Role;
import com.anesuandtatenda.studentlecturerplatform.service.*;
import com.anesuandtatenda.studentlecturerplatform.web.requests.DepartmentRequest;
import com.anesuandtatenda.studentlecturerplatform.web.requests.UserAccountRequest;
import lombok.val;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final TimeSlotService timeSlotService;

    private final FacaultyService facaultyService;

    private final DepartmentService departmentService;

    private final ProgramService programService;

    private final UserAccountService userAccountService;

    CommandLineAppStartupRunner(TimeSlotService timeSlotService, FacaultyService facaultyService, DepartmentService departmentService,
                                ProgramService programService, UserAccountService userAccountService) {
        this.timeSlotService = timeSlotService;
        this.facaultyService = facaultyService;
        this.departmentService = departmentService;
        this.programService = programService;
        this.userAccountService = userAccountService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("CommandLineRunner");

        Collection<TimeSlots> timeSlotsCollection = timeSlotService.findAll();

        if (timeSlotsCollection.isEmpty()) {

            TimeSlotsRequest timeSlotsRequest = new TimeSlotsRequest();

            timeSlotsRequest.setName("FIRST");
            timeSlotsRequest.setStartTime("08:00");
            timeSlotsRequest.setEndTime("10:00");

            timeSlotService.create(timeSlotsRequest);

            timeSlotsRequest.setName("SECOND");
            timeSlotsRequest.setStartTime("10:15");
            timeSlotsRequest.setEndTime("12:15");

            timeSlotService.create(timeSlotsRequest);

            timeSlotsRequest.setName("THIRD");
            timeSlotsRequest.setStartTime("13:00");
            timeSlotsRequest.setEndTime("15:00");

            timeSlotService.create(timeSlotsRequest);

            timeSlotsRequest.setName("FOURTH");
            timeSlotsRequest.setStartTime("15:00");
            timeSlotsRequest.setEndTime("17:00");

            timeSlotService.create(timeSlotsRequest);
        }

        boolean facaultyExists = facaultyService.existsByName("SCHOOL OF BUSINESS");

        if (!facaultyExists) {
            Facaulty facaulty = new Facaulty();
            facaulty.setName("SCHOOL OF BUSINESS");
            facaulty.setDescription("SCHOOL OF BUSINESS");
            facaultyService.create(facaulty);
        }

        boolean departmentExists = departmentService.existsByName("ACCOUNTING");

        if (!departmentExists) {
            DepartmentRequest department = new DepartmentRequest();
            department.setName("ACCOUNTING");
            department.setDescription("ACCOUNTING");
            val facaulty = facaultyService.getByName("SCHOOL OF BUSINESS");
            department.setFacaultyId(facaulty.getId());
            departmentService.create(department);
        }

        boolean programExists = programService.existsByName("QWAB");

        if (!programExists) {
            ProgramCreateRequest program = new ProgramCreateRequest();
            program.setName("QWAB");
            program.setDescription("QWAB");
            val department = departmentService.getByName("ACCOUNTING");
            program.setDepartmentId(department.getId());
            programService.create(program);
        }

        boolean adminExists = userAccountService.existByRegNumber("AD1234M");

        if (!adminExists) {
            UserAccountRequest adminAccountRequest = new UserAccountRequest();
            adminAccountRequest.setFirstName("Anesu");
            adminAccountRequest.setLastName("Mufushwa");
            adminAccountRequest.setPassword("admin");
            adminAccountRequest.setRegNumber("AD1234M");
            adminAccountRequest.setEmail("");
            adminAccountRequest.setProgramId(0);
            adminAccountRequest.setRole(Role.ADMIN);
            adminAccountRequest.setYear(0);
            userAccountService.create(adminAccountRequest);
        }

        boolean lecturerExists = userAccountService.existByRegNumber("LE1234R");

        if (!lecturerExists) {
            UserAccountRequest lecturerAccountRequest = new UserAccountRequest();
            lecturerAccountRequest.setFirstName("Mercy");
            lecturerAccountRequest.setLastName("Chinyuku");
            lecturerAccountRequest.setPassword("lecturer");
            lecturerAccountRequest.setRegNumber("LE1234R");
            lecturerAccountRequest.setEmail("nyabindenyasha@gmail.com");
            Programs program = programService.getByName("QWAB");
            lecturerAccountRequest.setProgramId(program.getId());
            lecturerAccountRequest.setRole(Role.LECTURER);
            lecturerAccountRequest.setYear(0);
            userAccountService.create(lecturerAccountRequest);
        }

        boolean studentExists = userAccountService.existByRegNumber("AD1234M");

        if (!studentExists) {
            UserAccountRequest studentAccountRequest = new UserAccountRequest();
            studentAccountRequest.setFirstName("Tatenda");
            studentAccountRequest.setLastName("Nderema");
            studentAccountRequest.setPassword("student");
            studentAccountRequest.setRegNumber("AD1234M");
            studentAccountRequest.setEmail("nyabindenyashae@gmail.com");
            Programs program = programService.getByName("QWAB");
            studentAccountRequest.setProgramId(program.getId());
            studentAccountRequest.setRole(Role.STUDENT);
            studentAccountRequest.setYear(4);
            userAccountService.create(studentAccountRequest);
        }

    }

}
