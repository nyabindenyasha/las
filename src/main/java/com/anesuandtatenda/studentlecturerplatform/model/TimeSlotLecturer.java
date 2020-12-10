package com.anesuandtatenda.studentlecturerplatform.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author GustTech
 */
@Data
@Entity
@Table(name = "time_slot_lecturer")
public class TimeSlotLecturer implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "day_of_week")
	private long dayOfWeek;

	private long lecturerId;

	@Column(name = "time_slots_id")
	private long timeSlotId;

}
