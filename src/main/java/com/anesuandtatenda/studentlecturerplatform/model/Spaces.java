package com.anesuandtatenda.studentlecturerplatform.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "spaces")
public class Spaces {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @Column(name = "start_time")
    private String startTime;

    @NotNull
    @Column(name = "end_time")
    private String endTime;

    @NotNull
    @Column(name = "lecturer_id")
    private long lecturerId;

    @Transient
    private String result;

    @Transient
    private String color;

}
