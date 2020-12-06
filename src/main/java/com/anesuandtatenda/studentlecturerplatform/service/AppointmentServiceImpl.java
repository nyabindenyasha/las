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

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, UserAccountRepository userAccountRepository,
                                  LecturersBookingsRepository lecturersBookingsRepository, TimeSlotRepository timeSlotRepository,
                                  TimeSlotLecturerRepository timeSlotLecturerRepository) {
        super(appointmentRepository);
        this.appointmentRepository = appointmentRepository;
        this.userAccountRepository = userAccountRepository;
        this.lecturersBookingsRepository = lecturersBookingsRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.timeSlotLecturerRepository = timeSlotLecturerRepository;
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

        appointment.setAppointmentBy(appointmentBy);

        appointment.setAppointmentWith(appointmentWith);

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

        appointment.setAppointmentBy(appointmentBy);

        appointment.setAppointmentWith(appointmentWith);

        appointment.setAnticipatedDuration(request.getAnticipatedDuration());

        appointment.setDate(request.getDate());

        //    appointment.fromCommand(appointment);

        Appointments validateAppointmentBookingResult = validateAppointmentBooking(appointment);

        return validateAppointmentBookingResult;
    }

    @Override
    public Collection<Appointments> findByAppointmentWithId(long id) {
        return appointmentRepository.findByAppointmentWithId(id);
    }

    @Override
    public Collection<Appointments> findByAppointmentById(long id) {
        return appointmentRepository.findByAppointmentById(id);
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

        Account student = userAccountRepository.getOne(appointment.getAppointmentBy().getId());

        Account lecturer = userAccountRepository.getOne(request.getLecturerId());

        if(request.isApproved()) {

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

    Appointments validateAppointmentBooking(Appointments appointment){

        System.out.println(Utils.convertToLocalTime(appointment.getDate()));

        List<LecturersBookings> lecturersBookings = lecturersBookingsRepository.findByDateAndLecturerId(Utils.convertToLocalDate(appointment.getDate()), appointment.getAppointmentWith().getId());

        if(lecturersBookings.size() > 1){
            LecturersBookings lastBookingForThatDay = lecturersBookings.get(lecturersBookings.size()-1);

            int dayOfWeek = Utils.convertToLocalDate(appointment.getDate()).getDayOfWeek().getValue();

            TimeSlots freeTimeSlotForLecturerOnThatDay = timeSlotLecturerRepository.findByDayOfWeekAndAccountId(dayOfWeek, appointment.getAppointmentWith().getId()).get(0).getTimeSlots();

            LocalTime endTimeOfLastBookingForThatDay = lastBookingForThatDay.getApproavedEndTime();

            if(endTimeOfLastBookingForThatDay.plusMinutes(appointment.getAnticipatedDuration()).isBefore(freeTimeSlotForLecturerOnThatDay.getEndTime())){

                appointment.setIsApproaved(false);
                appointment.setApprovedDuration(appointment.getAnticipatedDuration());

                Appointments appointmentSaved = appointmentRepository.save(appointment);

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
            }

            else
                throw new AppointmentBookingFailedException(ValidateAppointmentResponses.Response1.getAppointmentResponse());
        }

        else {

            int dayOfWeek = Utils.convertToLocalDate(appointment.getDate()).getDayOfWeek().getValue();

            List<TimeSlotLecturer> timeSlotLecturers = timeSlotLecturerRepository.findByDayOfWeekAndAccountId(dayOfWeek, appointment.getAppointmentWith().getId());

            if(timeSlotLecturers.size() == 0)
                throw new AppointmentBookingFailedException(ValidateAppointmentResponses.Response2.getAppointmentResponse());

            else {
                TimeSlots freeTimeSlotForLecturerOnThatDay = timeSlotLecturers.get(0).getTimeSlots();

                if(freeTimeSlotForLecturerOnThatDay.getId() == evaluateTimeSlotByTime(Utils.convertToLocalTime(appointment.getDate())).getId()){

                    appointment.setIsApproaved(false);
                    appointment.setApprovedDuration(appointment.getAnticipatedDuration());

                    Appointments appointmentSaved = appointmentRepository.save(appointment);

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
                }
                else
                    throw new AppointmentBookingFailedException(ValidateAppointmentResponses.Response3.getAppointmentResponse());
            }

        }
    }


    TimeSlots evaluateTimeSlotByTime(LocalTime time){

        if(time.isAfter(LocalTime.parse("08:00")) && time.isBefore(LocalTime.parse("10:00")))
         //   return timeSlotRepository.findByStartTime(LocalTime.parse("08:00"));
            return timeSlotRepository.findAll().get(0);

        else if(time.isAfter(LocalTime.parse("10:15")) && time.isBefore(LocalTime.parse("12:15")))
          //  return timeSlotRepository.findByStartTime(LocalTime.parse("10:15"));
            return timeSlotRepository.findAll().get(1);

        else if(time.isAfter(LocalTime.parse("13:00")) && time.isBefore(LocalTime.parse("15:00")))
//            return timeSlotRepository.findByStartTime(LocalTime.parse("13:00"));
            return timeSlotRepository.findAll().get(2);

        else
//            return timeSlotRepository.findByStartTime(LocalTime.parse("15:00"));
            return timeSlotRepository.findAll().get(3);
    }

}
