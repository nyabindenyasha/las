package com.anesuandtatenda.studentlecturerplatform.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author GustTech
 */
@Entity
@Table(name = "time_slot_lecturer")
public class TimeSlotLecturer implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(name = "day_of_week")
	private int dayOfWeek;

	@JoinColumn(name = "account_id", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Account account;

	@JoinColumn(name = "time_slot_id", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private TimeSlots timeSlots;

	public TimeSlotLecturer() {
	}

	public TimeSlotLecturer(Integer id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public TimeSlots getTimeSlots() {
		return timeSlots;
	}

	public void setTimeSlots(TimeSlots timeSlots) {
		this.timeSlots = timeSlots;
	}

	@Override
	public String toString() {
		return "TimeSlotLecturer{" +
				"id=" + id +
				"dayOfWeek=" + dayOfWeek +
				", account=" + account +
				", timeSlots=" + timeSlots +
				'}';
	}
}
