package com.anesuandtatenda.studentlecturerplatform.local;

import com.anesuandtatenda.studentlecturerplatform.model.Spaces;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HourlySegments {

    public static List<Spaces> getHourlySeg(long lecturerId) {

        List<Spaces> spacesList = new ArrayList<>();

        Spaces spaces = new Spaces();
        spaces.setId(1);
        spaces.setStartTime("08:00");
        spaces.setEndTime("09:00");
        spaces.setLecturerId(lecturerId);
        spacesList.add(spaces);

        Spaces spaces2 = new Spaces();
        spaces2.setId(2);
        spaces2.setStartTime("09:00");
        spaces2.setEndTime("10:00");
        spaces.setLecturerId(lecturerId);
        spacesList.add(spaces2);

        Spaces spaces3 = new Spaces();
        spaces3.setId(3);
        spaces3.setStartTime("10:15");
        spaces3.setEndTime("11:15");
        spaces.setLecturerId(lecturerId);
        spacesList.add(spaces3);

        Spaces spaces4 = new Spaces();
        spaces4.setId(4);
        spaces4.setStartTime("11:15");
        spaces4.setEndTime("12:15");
        spaces.setLecturerId(lecturerId);
        spacesList.add(spaces4);

        Spaces spaces5 = new Spaces();
        spaces5.setId(5);
        spaces5.setStartTime("13:00");
        spaces5.setEndTime("14:00");
        spaces.setLecturerId(lecturerId);
        spacesList.add(spaces5);

        Spaces spaces6 = new Spaces();
        spaces6.setId(6);
        spaces6.setStartTime("14:00");
        spaces6.setEndTime("15:00");
        spaces.setLecturerId(lecturerId);
        spacesList.add(spaces6);

        Spaces spaces7 = new Spaces();
        spaces7.setId(7);
        spaces7.setStartTime("15:00");
        spaces7.setEndTime("16:00");
        spaces.setLecturerId(lecturerId);
        spacesList.add(spaces7);

        Spaces spaces8 = new Spaces();
        spaces8.setId(8);
        spaces8.setStartTime("16:00");
        spaces8.setEndTime("17:00");
        spaces.setLecturerId(lecturerId);
        spacesList.add(spaces8);

        spacesList.forEach(x -> x.setLecturerId(lecturerId));

        System.out.println("spacesList: " + spacesList);

        return spacesList;
    }

}

