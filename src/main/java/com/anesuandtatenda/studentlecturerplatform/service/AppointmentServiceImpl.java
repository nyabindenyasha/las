package com.anesuandtatenda.studentlecturerplatform.service;

import com.anesuandtatenda.studentlecturerplatform.local.Utils;
import com.anesuandtatenda.studentlecturerplatform.local.exceptions.AppointmentBookingFailedException;
import com.anesuandtatenda.studentlecturerplatform.local.exceptions.InvalidRequestException;
import com.anesuandtatenda.studentlecturerplatform.local.requests.AppointmentRequest;
import com.anesuandtatenda.studentlecturerplatform.local.requests.AppointmentRequestMobile;
import com.anesuandtatenda.studentlecturerplatform.local.requests.ApproveAppointmentRequest;
import com.anesuandtatenda.studentlecturerplatform.model.*;
import com.anesuandtatenda.studentlecturerplatform.model.enums.ValidateAppointmentResponses;
import com.anesuandtatenda.studentlecturerplatform.repo.*;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

@Service
public class AppointmentServiceImpl extends BaseServiceImpl<Appointments, AppointmentRequest, AppointmentRequest> implements AppointmentService {

    final AppointmentRepository appointmentRepository;

    final UserAccountRepository userAccountRepository;

    final LecturersBookingsRepository lecturersBookingsRepository;

    final TimeSlotRepository timeSlotRepository;

    final TimeSlotLecturerRepository timeSlotLecturerRepository;

    final SpacesRepository spacesRepository;

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

        boolean detailsExists = appointmentRepository.existsById(request.getId());

        if (detailsExists) {
            throw new InvalidRequestException("Appointment with the same name already exists");
        }

        Account appointmentBy = userAccountRepository.getOne(request.getAppointmentBy());

        Account appointmentWith = userAccountRepository.getOne(request.getAppointmentWith());

        Appointments appointment = new Appointments();

        appointment.setAppointmentBy(appointmentBy.getId());

        appointment.setAppointmentWith(appointmentWith.getId());

        appointment.setAnticipatedDuration(request.getAnticipatedDuration());

        appointment.setDate(request.getDate());

        //    appointment.fromCommand(appointment);

        Appointments validateAppointmentBookingResult = validateAppointmentBooking(appointment);

        return validateAppointmentBookingResult;
    }

    @Override
    public Appointments createFromMobile(AppointmentRequestMobile request) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        LocalDateTime dateTime = LocalDateTime.parse(request.getDateString(), formatter);

        request.setDate(Utils.convertToDateViaInstant(dateTime));

        boolean detailsExists = appointmentRepository.existsById(request.getId());

        if (detailsExists) {
            throw new InvalidRequestException("Appointment with the same name already exists");
        }

        Account appointmentBy = userAccountRepository.getOne(request.getAppointmentBy());

        Account appointmentWith = userAccountRepository.getOne(request.getAppointmentWith());

        Appointments appointment = new Appointments();

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
    public Appointments update(AppointmentRequest updateDto) {
        return null;
    }

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

        Account student = userAccountRepository.getOne(appointment.getAppointmentBy());

        Account lecturer = userAccountRepository.getOne(request.getLecturerId());

        if (request.isApproved()) {

            appointment.setIsApproaved(true);

            //          booking.setApproaved(true);

            //          lecturersBookingsRepository.save(booking);
        }

        appointment.setIsApproaved(true);

        System.out.println(appointment);

        appointmentRepository.save(appointment);

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

    Appointments validateAppointmentBooking(Appointments appointment) {

        System.out.println(Utils.convertToLocalTime(appointment.getDate()));

        List<LecturersBookings> lecturersBookings = lecturersBookingsRepository.findByDateAndLecturer(Utils.convertToLocalDate(appointment.getDate()), appointment.getAppointmentWith());

        String spaceStartTime = Utils.convertToLocalTime(appointment.getDate()).toString();
        String spaceEndTime = Utils.convertToLocalTime(appointment.getDate()).plusMinutes(appointment.getAnticipatedDuration()).toString();

        if (lecturersBookings.size() > 1) {
            LecturersBookings lastBookingForThatDay = lecturersBookings.get(lecturersBookings.size() - 1);

            long dayOfWeek = Utils.convertToLocalDate(appointment.getDate()).getDayOfWeek().getValue();

            long timeSlotsId = timeSlotLecturerRepository.findByDayOfWeekAndLecturerId(dayOfWeek, appointment.getAppointmentWith()).get(0).getTimeSlotId();

            TimeSlots freeTimeSlotForLecturerOnThatDay = timeSlotRepository.getOne(timeSlotsId);

            LocalTime endTimeOfLastBookingForThatDay = lastBookingForThatDay.getApproavedEndTime();

            if (endTimeOfLastBookingForThatDay.plusMinutes(appointment.getAnticipatedDuration()).isBefore(freeTimeSlotForLecturerOnThatDay.getEndTime())) {

                appointment.setIsApproaved(false);
                appointment.setApprovedDuration(appointment.getAnticipatedDuration());

                Appointments appointmentSaved = appointmentRepository.save(appointment);

                Spaces spaces = new Spaces();
                spaces.setStartTime(spaceStartTime);
                spaces.setEndTime(spaceEndTime);
                spaces.setLecturerId(appointment.getAppointmentWith());
                spacesRepository.save(spaces);

                LecturersBookings lecturersBooking = new LecturersBookings();
                lecturersBooking.setTimeslot(freeTimeSlotForLecturerOnThatDay);
                lecturersBooking.setApproaved(false);      //still waiting for lecturer's approval
                lecturersBooking.setAppointment(appointmentSaved);
                lecturersBooking.setDate(Utils.convertToLocalDate(appointment.getDate()));
                lecturersBooking.setApprovedDuration(appointment.getAnticipatedDuration());
                lecturersBooking.setAppointmentWith(appointment.getAppointmentBy());
                lecturersBooking.setApproavedEndTime(endTimeOfLastBookingForThatDay.plusMinutes(appointment.getAnticipatedDuration()));
                lecturersBooking.setLecturer(appointment.getAppointmentWith());

                lecturersBookingsRepository.save(lecturersBooking);
                return appointmentSaved;
            } else
                throw new AppointmentBookingFailedException(ValidateAppointmentResponses.Response1.getAppointmentResponse());
        } else {

            long dayOfWeek = Utils.convertToLocalDate(appointment.getDate()).getDayOfWeek().getValue();

            List<TimeSlotLecturer> timeSlotLecturers = timeSlotLecturerRepository.findByDayOfWeekAndLecturerId(dayOfWeek, appointment.getAppointmentWith());

            if (timeSlotLecturers.size() == 0)
                throw new AppointmentBookingFailedException(ValidateAppointmentResponses.Response2.getAppointmentResponse());

            else {
                TimeSlots freeTimeSlotForLecturerOnThatDay = timeSlotRepository.getOne(timeSlotLecturers.get(0).getTimeSlotId());

                System.out.println("xxx");

                System.out.println(freeTimeSlotForLecturerOnThatDay);

                LocalTime time = Utils.convertToLocalTime(appointment.getDate());

                TimeSlots tm = evaluateTimeSlotByTime(Utils.convertToLocalTime(appointment.getDate()));

                System.out.println(evaluateTimeSlotByTime(Utils.convertToLocalTime(appointment.getDate())));

                if (freeTimeSlotForLecturerOnThatDay.getId() == evaluateTimeSlotByTime(Utils.convertToLocalTime(appointment.getDate())).getId()) {

                    appointment.setIsApproaved(false);
                    appointment.setApprovedDuration(appointment.getAnticipatedDuration());

                    Appointments appointmentSaved = appointmentRepository.save(appointment);

                    Spaces spaces = new Spaces();
                    spaces.setStartTime(spaceStartTime);
                    spaces.setEndTime(spaceEndTime);
                    spaces.setLecturerId(appointment.getAppointmentWith());
                    spacesRepository.save(spaces);

                    LecturersBookings lecturersBooking = new LecturersBookings();
                    lecturersBooking.setTimeslot(freeTimeSlotForLecturerOnThatDay);
                    lecturersBooking.setApproaved(false);      //still waiting for lecturer's approval
                    lecturersBooking.setAppointment(appointmentSaved);
                    lecturersBooking.setDate(Utils.convertToLocalDate(appointment.getDate()));
                    lecturersBooking.setApprovedDuration(appointment.getAnticipatedDuration());
                    lecturersBooking.setAppointmentWith(appointment.getAppointmentBy());
                    lecturersBooking.setApproavedEndTime(freeTimeSlotForLecturerOnThatDay.getStartTime().plusMinutes(appointment.getAnticipatedDuration()));
                    lecturersBooking.setLecturer(appointment.getAppointmentWith());

                    lecturersBookingsRepository.save(lecturersBooking);
                    return appointmentSaved;
                } else
                    throw new AppointmentBookingFailedException(ValidateAppointmentResponses.Response3.getAppointmentResponse());
            }

        }
    }


//    Appointments validateAppointmentTheOne(Appointments appointment){
//        Spaces spaces = new Spaces();
//        spaces.setStartTime(Utils.convertToLocalDate(appointment.getDate()).toString());
//        spaces.setEndTime((Utils.convertToLocalDate(appointment.getDate())).plusMinutes(appointment.getAnticipatedDuration()).toString());
//
//        Appointments appointmentSaved = appointmentRepository.save(appointment);
//
//        LecturersBookings lecturersBooking = new LecturersBookings();
//        lecturersBooking.setTimeslot(freeTimeSlotForLecturerOnThatDay);
//        lecturersBooking.setApproaved(false);      //still waiting for lecturer's approval
//        lecturersBooking.setAppointment(appointmentSaved);
//        lecturersBooking.setDate(Utils.convertToLocalDate(appointment.getDate()));
//        lecturersBooking.setApprovedDuration(appointment.getAnticipatedDuration());
//        lecturersBooking.setAppointmentWith(appointment.getAppointmentBy());
//        lecturersBooking.setApproavedEndTime(freeTimeSlotForLecturerOnThatDay.getStartTime().plusMinutes(appointment.getAnticipatedDuration()));
//        lecturersBooking.setLecturer(appointment.getAppointmentWith());
//
//        lecturersBookingsRepository.save(lecturersBooking);
//        return appointmentSaved;
//    }


    TimeSlots evaluateTimeSlotByTime(LocalTime time) {

        Collection<TimeSlots> all = timeSlotRepository.findAll();

        System.out.println(timeSlotRepository.findAll());

        if (time.isAfter(LocalTime.parse("08:00")) && time.isBefore(LocalTime.parse("10:00"))) {
            System.out.println(((List<TimeSlots>) all).get(0));
            TimeSlots t = all.parallelStream().findFirst().get();
            return t;
//            TimeSlots t = timeSlotRepository.findByStartTime(LocalTime.parse("08:00"));
//            return timeSlotRepository.findByStartTime(LocalTime.parse("08:00"));
        } else if (time.isAfter(LocalTime.parse("10:15")) && time.isBefore(LocalTime.parse("12:15")))
            //  return timeSlotRepository.findByStartTime(LocalTime.parse("10:15"));
            return timeSlotRepository.findByStartTime(LocalTime.parse("10:15"));

        else if (time.isAfter(LocalTime.parse("13:00")) && time.isBefore(LocalTime.parse("15:00")))
//            return timeSlotRepository.findByStartTime(LocalTime.parse("13:00"));
            return timeSlotRepository.findByStartTime(LocalTime.parse("13:00"));

        else
//            return timeSlotRepository.findByStartTime(LocalTime.parse("15:00"));
            return timeSlotRepository.findByStartTime(LocalTime.parse("15:00"));
    }

}
