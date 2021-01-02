package com.anesuandtatenda.studentlecturerplatform.service;

import com.anesuandtatenda.studentlecturerplatform.local.SmtpMailSender;
import com.anesuandtatenda.studentlecturerplatform.local.Utils;
import com.anesuandtatenda.studentlecturerplatform.local.exceptions.InvalidRequestException;
import com.anesuandtatenda.studentlecturerplatform.local.requests.AppointmentRequest;
import com.anesuandtatenda.studentlecturerplatform.local.requests.AppointmentRequestMobile;
import com.anesuandtatenda.studentlecturerplatform.local.requests.ApproveAppointmentRequest;
import com.anesuandtatenda.studentlecturerplatform.model.*;
import com.anesuandtatenda.studentlecturerplatform.repo.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class AppointmentServiceImpl extends BaseServiceImpl<Appointments, AppointmentRequest, AppointmentRequest> implements AppointmentService {

    final AppointmentRepository appointmentRepository;

    final UserAccountRepository userAccountRepository;

    final LecturersBookingsRepository lecturersBookingsRepository;

    final TimeSlotRepository timeSlotRepository;

    final TimeSlotLecturerRepository timeSlotLecturerRepository;

    final SpacesRepository spacesRepository;

    @Autowired
    private SmtpMailSender smtpMailSender;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, UserAccountRepository userAccountRepository,
                                  LecturersBookingsRepository lecturersBookingsRepository, TimeSlotRepository timeSlotRepository,
                                  TimeSlotLecturerRepository timeSlotLecturerRepository, SpacesRepository spacesRepository) {
        super(appointmentRepository);
        this.appointmentRepository = appointmentRepository;
        this.userAccountRepository = userAccountRepository;
        this.lecturersBookingsRepository = lecturersBookingsRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.timeSlotLecturerRepository = timeSlotLecturerRepository;
        this.spacesRepository = spacesRepository;
    }


    @Override
    protected Class<Appointments> getEntityClass() {
        return Appointments.class;
    }

    @Override
    public Appointments create(AppointmentRequest request) {

        System.out.println("create");

//        val detailsExists = appointmentRepository.findByDateUuid(request.getDate().getTime());
//
//        System.out.println("detailsExists: " + detailsExists);
//
//        if (detailsExists != null) {
//            throw new InvalidRequestException("Appointment already created");
//        }

        UserAccount appointmentBy = userAccountRepository.getOne(request.getAppointmentBy());

        UserAccount appointmentWith = userAccountRepository.getOne(request.getAppointmentWith());

        Appointments appointment = new Appointments();

//        appointment.setDateUuid(Utils.convertToLocalDate(appointment.getDate()).getMonthValue().);

        appointment.setAppointmentBy(appointmentBy.getId());

        appointment.setAppointmentWith(appointmentWith.getId());

        appointment.setAnticipatedDuration(request.getAnticipatedDuration());

        Date dateWithHoursSubtracted = Utils.addHoursToJavaUtilDate(request.getDate(), -2);

        appointment.setDate(dateWithHoursSubtracted);

        //    appointment.fromCommand(appointment);

        Appointments validateAppointmentBookingResult = validateAppointmentBooking(appointment);

        return validateAppointmentBookingResult;
    }

    @Override
    public Appointments createFromMobile(AppointmentRequestMobile request) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime dateTime = LocalDateTime.parse(request.getDateString(), formatter);

        request.setDate(Utils.convertToDateViaInstant(dateTime));

//        val detailsExists = appointmentRepository.findByDateUuid(request.getDate().getTime());
//
//        if (detailsExists != null) {
//            throw new InvalidRequestException("Appointment with the same name already exists");
//        }

        UserAccount appointmentBy = userAccountRepository.getOne(request.getAppointmentBy());

        UserAccount appointmentWith = userAccountRepository.getOne(request.getAppointmentWith());

        Appointments appointment = new Appointments();

//        appointment.setDateUuid(appointment.getDate().getTime());

        appointment.setAppointmentBy(appointmentBy.getId());

        appointment.setAppointmentWith(appointmentWith.getId());

        appointment.setAnticipatedDuration(request.getAnticipatedDuration());

        appointment.setDate(request.getDate());

        //    appointment.fromCommand(appointment);

        Appointments validateAppointmentBookingResult = validateAppointmentBooking(appointment);

        return validateAppointmentBookingResult;
    }

    @Override
    public Collection<Appointments> findByAppointmentWithId(long id) {
        return appointmentRepository.findByAppointmentWith(id);
    }

    @Override
    public Collection<Appointments> findByAppointmentById(long id) {
        return appointmentRepository.findByAppointmentBy(id);
    }

    @Override
    public boolean existsByDate(Date date) {
        return appointmentRepository.existsByDate(date);
    }

    @Override
    public Appointments update(AppointmentRequest updateDto) {
        return null;
    }

    @SneakyThrows
    @Override
    public Appointments approveBooking(ApproveAppointmentRequest request) {

        boolean appointmentDetailsExists = appointmentRepository.existsById(request.getAppointmentId());

        Appointments appointment = findById(request.getAppointmentId());

        //  LecturersBookings lecturerBooking = lecturersBookingsRepository.findByDateAndLecturerId(appointment.getDate(), )

        boolean lecturerDetailsExists = lecturersBookingsRepository.existsById(request.getLecturerBookingId());

//        if (!appointmentDetailsExists || !lecturerDetailsExists) {
//            throw new InvalidRequestException("Appointment not found");
//        }


        if (!appointmentDetailsExists) {
            throw new InvalidRequestException("Appointment not found");
        }

        //    LecturersBookings booking = lecturersBookingsRepository.findById(request.getLecturerBookingId()).get();

        UserAccount student = userAccountRepository.getOne(appointment.getAppointmentBy());

        UserAccount lecturer = userAccountRepository.getOne(request.getLecturerId());

        if (request.isApproved()) {

            appointment.setIsApproaved(true);

            //          booking.setApproaved(true);

            //          lecturersBookingsRepository.save(booking);
        }

        appointment.setIsApproaved(true);

        System.out.println(appointment);

        appointmentRepository.save(appointment);

        smtpMailSender.send(student.getEmail(), "Appointment Approval Result", "Hie " + student.getFirstName() + " " + student.getLastName() +
                " Your appointment with " + lecturer.getFirstName() + " " + lecturer.getLastName() + " has been approved.");

        return appointmentRepository.save(appointment);
    }


    @Override
    public void delete(Long id) {
        try {
            super.delete(id);
        } catch (ConstraintViolationException var3) {
            throw new InvalidRequestException("You can not delete this record is might be used by another record");
        }
    }

    @SneakyThrows
    Appointments validateAppointmentBooking(Appointments appointment) {

        System.out.println(Utils.convertToLocalTime(appointment.getDate()));

        UserAccount lecturer = userAccountRepository.getOne(appointment.getAppointmentWith());

        UserAccount student = userAccountRepository.getOne(appointment.getAppointmentBy());

        appointment.setIsApproaved(false);
        appointment.setApprovedDuration(appointment.getAnticipatedDuration());

        Appointments appointmentSaved = appointmentRepository.save(appointment);

        smtpMailSender.send(lecturer.getEmail(), "Appointment Request From Student", "Hie " + lecturer.getFirstName() + " " + lecturer.getLastName() +
                " Your student " + student.getFirstName() + " " + student.getLastName() + " is requesting to meet up with you on " + appointment.getDate().toString());

        Spaces spaces = new Spaces();
        spaces.setStartTime(Utils.convertToLocalTime(appointment.getDate()).toString());
        spaces.setEndTime(Utils.convertToLocalTime(appointment.getDate()).plusMinutes(60).toString());
        spaces.setLecturerId(appointment.getAppointmentWith());
        spacesRepository.save(spaces);

        LecturersBookings lecturersBooking = new LecturersBookings();
        lecturersBooking.setTimeslot(evaluateTimeSlotByTime(Utils.convertToLocalTime(appointment.getDate())));
        lecturersBooking.setApproaved(false);      //still waiting for lecturer's approval
        lecturersBooking.setAppointment(appointmentSaved);
        lecturersBooking.setDate(Utils.convertToLocalDate(appointment.getDate()));
        lecturersBooking.setApprovedDuration(appointment.getAnticipatedDuration());
        lecturersBooking.setAppointmentWith(appointment.getAppointmentBy());
        lecturersBooking.setApproavedEndTime(Utils.convertToLocalTime(appointment.getDate()).plusMinutes(appointment.getAnticipatedDuration()));
        lecturersBooking.setLecturer(appointment.getAppointmentWith());

        lecturersBookingsRepository.save(lecturersBooking);
        return appointmentSaved;

    }


    TimeSlots evaluateTimeSlotByTime(LocalTime time) {

        Collection<TimeSlots> all = timeSlotRepository.findAll();

        System.out.println(timeSlotRepository.findAll());

        if (time.isAfter(LocalTime.parse("07:59")) && time.isBefore(LocalTime.parse("10:00"))) {
            System.out.println(((List<TimeSlots>) all).get(0));
            TimeSlots t = all.parallelStream().findFirst().get();
            return t;
//            TimeSlots t = timeSlotRepository.findByStartTime(LocalTime.parse("08:00"));
//            return timeSlotRepository.findByStartTime(LocalTime.parse("08:00"));
        } else if (time.isAfter(LocalTime.parse("10:14")) && time.isBefore(LocalTime.parse("12:15")))
            //  return timeSlotRepository.findByStartTime(LocalTime.parse("10:15"));
            return timeSlotRepository.findByStartTime(LocalTime.parse("10:15"));

        else if (time.isAfter(LocalTime.parse("12:59")) && time.isBefore(LocalTime.parse("15:00")))
//            return timeSlotRepository.findByStartTime(LocalTime.parse("13:00"));
            return timeSlotRepository.findByStartTime(LocalTime.parse("13:00"));

        else
//            return timeSlotRepository.findByStartTime(LocalTime.parse("15:00"));
            return timeSlotRepository.findByStartTime(LocalTime.parse("15:00"));
    }

}
