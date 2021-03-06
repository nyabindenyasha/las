package com.anesuandtatenda.studentlecturerplatform.model;

import com.anesuandtatenda.studentlecturerplatform.local.BeanUtil;
import com.anesuandtatenda.studentlecturerplatform.service.UserAccountService;
import lombok.val;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author GustTech
 */
@Entity
@Table(name = "appointments")
public class Appointments implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

//    @Column(name = "date_uuid")
//    private String dateUuid;

    @Column(name = "is_open")
    private boolean isOpen;

    @Size(max = 250)
    @Column(name = "comment")
    private String comment;

    @Column(name = "is_approaved")
    private boolean isApproaved;

    @NotNull
    @Column(name = "anticipated_duration")
    private long anticipatedDuration;

    @Column(name = "approved_duration")
    private long approvedDuration;

    @Transient
    private UserAccount appointmentWithObj;

    private long appointmentWith;

    @Transient
    private UserAccount appointmentByObj;

    private long appointmentBy;

    public Appointments() {
    }

    public Appointments(Integer id) {
        this.id = id;
    }

    public Appointments(Integer id, Date date, boolean isOpen, boolean isApproaved, long anticipatedDuration) {
        this.id = id;
        this.date = date;
        this.isOpen = isOpen;
        this.isApproaved = isApproaved;
        this.anticipatedDuration = anticipatedDuration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean getIsApproaved() {
        return isApproaved;
    }

    public void setIsApproaved(boolean isApproaved) {
        this.isApproaved = isApproaved;
    }

    public long getAnticipatedDuration() {
        return anticipatedDuration;
    }

    public void setAnticipatedDuration(long anticipatedDuration) {
        this.anticipatedDuration = anticipatedDuration;
    }

    public long getAppointmentWith() {
        return appointmentWith;
    }

    public UserAccount getAppointmentWithObj() {
        return appointmentWithObj;
    }

    public UserAccount getAppointmentByObj() {
        return appointmentByObj;
    }

    public void setAppointmentWith(long appointmentWith) {
        this.appointmentWith = appointmentWith;
    }

    public long getAppointmentBy() {
        return appointmentBy;
    }

    public void setAppointmentBy(long appointmentBy) {
        this.appointmentBy = appointmentBy;
    }

    public long getApprovedDuration() {
        return approvedDuration;
    }

    public void setApprovedDuration(long approvedDuration) {
        this.approvedDuration = approvedDuration;
    }


    public static Appointments fromCommand(Appointments request) {

        if (request == null) {
            return null;
        }

        Appointments appointments = new Appointments();
        appointments.setDate(request.getDate());
        appointments.setIsOpen(request.getIsOpen());
        appointments.setComment(request.getComment());
        appointments.setIsApproaved(request.getIsApproaved());
        appointments.setApprovedDuration(request.approvedDuration);
        appointments.setAppointmentBy(request.getAppointmentBy());
        appointments.setAppointmentWith(request.getAppointmentWith());
        appointments.setAnticipatedDuration(request.getAnticipatedDuration());

        return appointments;
    }

    public void update(Appointments updateRequest) {
        this.setDate(updateRequest.getDate());
        this.setIsOpen(updateRequest.getIsOpen());
        this.setComment(updateRequest.getComment());
        this.setIsApproaved(updateRequest.getIsApproaved());
        this.setApprovedDuration(updateRequest.getApprovedDuration());
        this.setAppointmentBy(updateRequest.getAppointmentBy());
        this.setAppointmentWith(updateRequest.getAppointmentWith());
        this.setAnticipatedDuration(updateRequest.getAnticipatedDuration());
    }

    @Override
    public String toString() {
        return "Appointments{" +
                "id=" + id +
                ", date=" + date +
                ", isOpen=" + isOpen +
                ", comment='" + comment + '\'' +
                ", isApproaved=" + isApproaved +
                ", anticipatedDuration=" + anticipatedDuration +
                ", appointmentWith=" + appointmentWith +
                ", appointmentBy=" + appointmentBy +
                '}';
    }

    @PostLoad
    public void postLoad() {
        val appointmentWithObj = (this.appointmentWith == 0) ? null : BeanUtil.getBean(UserAccountService.class)
                .findById(this.appointmentWith);
        this.appointmentWithObj = appointmentWithObj;

        val appointmentByObj = (this.appointmentBy == 0) ? null : BeanUtil.getBean(UserAccountService.class)
                .findById(this.appointmentBy);
        this.appointmentByObj = appointmentByObj;
    }
}
