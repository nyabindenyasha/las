package com.anesuandtatenda.studentlecturerplatform.model;

import com.anesuandtatenda.studentlecturerplatform.local.requests.TimeSlotsRequest;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

/**
 *
 * @author GustTech
 */
@Entity
@Table(name = "time_slots")
public class TimeSlots implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Size(max = 50)
	@Column(name = "name")
	private String name;

	@NotNull
	@Column(name = "start_time")
	private LocalTime startTime;

	@NotNull
	@Column(name = "end_time")
	private LocalTime endTime;

	public TimeSlots() {
	}

	public TimeSlots(Integer id) {
		this.id = id;
	}

	public TimeSlots(Integer id, LocalTime startTime, LocalTime endTime) {
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}


	public static TimeSlots fromCommand(TimeSlotsRequest request) {

		if (request == null) {
			return null;
		}

		TimeSlots timeSlot = new TimeSlots();
		timeSlot.setName(request.getName());
		timeSlot.setStartTime(LocalTime.parse(request.getStartTime()));
		timeSlot.setEndTime(LocalTime.parse(request.getEndTime()));

		return timeSlot;
	}

	public void update(TimeSlotsRequest updateRequest) {
		this.setName(updateRequest.getName());
		this.setStartTime(LocalTime.parse(updateRequest.getStartTime()));
		this.setEndTime(LocalTime.parse(updateRequest.getEndTime()));
	}

	@Override
	public String toString() {
		return "TimeSlots{" +
				"id=" + id +
				", name='" + name + '\'' +
				", startTime=" + startTime +
				", endTime=" + endTime +
				'}';
	}
}
