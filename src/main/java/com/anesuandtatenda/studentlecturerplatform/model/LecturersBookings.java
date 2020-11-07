package com.anesuandtatenda.studentlecturerplatform.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
@Entity
@Table(name = "lecturers_bookings")
public class LecturersBookings {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @NotNull
    @Column(name = "date")
    private LocalDate date;

    @Size(max = 250)
    @Column(name = "comment")
    private String comment;

    @Column(name = "is_approaved")
    private boolean isApproaved = true;

    @Column(name = "approved_duration")
    private int approvedDuration;

    @Column(name = "approaved_end_time")
    private LocalTime approavedEndTime;

    @JoinColumn(name = "appointment_with", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account appointmentWith;

    @JoinColumn(name = "lecturer_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account lecturer;

    @JoinColumn(name = "appointment_id", referencedColumnName = "id")
    @OneToOne(optional = false)
    private Appointments appointment;

    @JoinColumn(name = "timeslot_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TimeSlots timeslot;

    public static LecturersBookings fromCommand(LecturersBookings request) {

        if (request == null) {
            return null;
        }

        LecturersBookings lecturersBookings = new LecturersBookings();
        lecturersBookings.setDate(request.getDate());
        lecturersBookings.setTimeslot(request.getTimeslot());
        lecturersBookings.setComment(request.getComment());
       // lecturersBookings.setAppointment(request.getAppointment());
        lecturersBookings.setAppointmentWith(request.getAppointmentWith());
        lecturersBookings.setAppointmentWith(request.getAppointmentWith());
        lecturersBookings.setApprovedDuration(request.getApprovedDuration());

        return lecturersBookings;
    }

    public void update(LecturersBookings updateRequest) {

        this.setDate(updateRequest.getDate());
        this.setTimeslot(updateRequest.getTimeslot());
        this.setComment(updateRequest.getComment());
      //  this.setAppointment(updateRequest.getAppointment());
        this.setAppointmentWith(updateRequest.getAppointmentWith());
        this.setAppointmentWith(updateRequest.getAppointmentWith());
        this.setApprovedDuration(updateRequest.getApprovedDuration());
    }

}
