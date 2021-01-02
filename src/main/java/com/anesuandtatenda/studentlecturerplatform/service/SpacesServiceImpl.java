package com.anesuandtatenda.studentlecturerplatform.service;

import com.anesuandtatenda.studentlecturerplatform.local.HourlySegments;
import com.anesuandtatenda.studentlecturerplatform.local.exceptions.InvalidRequestException;
import com.anesuandtatenda.studentlecturerplatform.model.Spaces;
import com.anesuandtatenda.studentlecturerplatform.model.TimeSlotLecturer;
import com.anesuandtatenda.studentlecturerplatform.model.TimeSlots;
import com.anesuandtatenda.studentlecturerplatform.repo.SpacesRepository;
import com.anesuandtatenda.studentlecturerplatform.repo.TimeSlotLecturerRepository;
import com.anesuandtatenda.studentlecturerplatform.repo.TimeSlotRepository;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.Collection;
import java.util.List;

@Service
public class SpacesServiceImpl extends BaseServiceImpl<Spaces, Spaces, Spaces> implements SpacesService {

    private final SpacesRepository spacesRepository;

    private final TimeSlotLecturerRepository timeSlotLecturerRepository;

    private final TimeSlotRepository timeSlotRepository;

    static List<Spaces> spaces;

    SpacesServiceImpl(SpacesRepository spacesRepository, TimeSlotLecturerRepository timeSlotLecturerRepository, TimeSlotRepository timeSlotRepository) {
        super(spacesRepository);
        this.spacesRepository = spacesRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.timeSlotLecturerRepository = timeSlotLecturerRepository;
    }

    @Override
    protected Class<Spaces> getEntityClass() {
        return Spaces.class;
    }

    @Override
    public Spaces create(Spaces request) {

        return spacesRepository.save(request);
    }


    @Override
    public Spaces update(Spaces request) {

        Spaces spaces = findById(request.getId());

        return spacesRepository.save(spaces);
    }

    @Override
    public void delete(Long id) {
        try {
            super.delete(id);
        } catch (ConstraintViolationException var3) {
            throw new InvalidRequestException("You can not delete this record is might be used by another record");
        }
    }

    @Override
    public Collection<Spaces> findByLecturerId(long lecturerId) {
        return spacesRepository.findByLecturerId(lecturerId);
    }

    @Override
    public Collection<Spaces> evaluateByLecturerId(long lecturerId, int dayOfWeek) {

        spaces = HourlySegments.getHourlySeg(lecturerId);

        System.out.println(spaces);

        spaces.forEach(spaces1 -> {
            spaces1.setColor("red");
            spaces1.setResult("occupied");
        });

        Collection<TimeSlotLecturer> timeSlotLecturer = timeSlotLecturerRepository.findByDayOfWeekAndLecturerId(dayOfWeek, lecturerId);

        timeSlotLecturer.forEach(timeSlotLecturer1 -> {
            TimeSlots timeSlots = timeSlotRepository.getOne(timeSlotLecturer1.getTimeSlotId());
            removeSpaces(timeSlots);
        });

        Collection<Spaces> spacestaken = spacesRepository.findByLecturerId(lecturerId);

        spacestaken.forEach(spaces1 -> {
            removeSpaces2(spaces1);
        });

        return spaces;
    }

    void removeSpaces(TimeSlots timeSlots) {

        if (timeSlots.getId() == 1) {
            spaces.forEach(spaces1 -> {
                if (spaces1.getId() == 1) {
                    spaces1.setColor("white");
                    spaces1.setResult("free");
                }
                if (spaces1.getId() == 2) {
                    spaces1.setColor("white");
                    spaces1.setResult("free");
                }
            });
        }

        if (timeSlots.getId() == 2) {
            spaces.forEach(spaces1 -> {
                if (spaces1.getId() == 3) {
                    spaces1.setColor("white");
                    spaces1.setResult("free");
                }
                if (spaces1.getId() == 4) {
                    spaces1.setColor("white");
                    spaces1.setResult("free");
                }
            });
        }

        if (timeSlots.getId() == 3) {
            spaces.forEach(spaces1 -> {
                if (spaces1.getId() == 5) {
                    spaces1.setColor("white");
                    spaces1.setResult("free");
                }
                if (spaces1.getId() == 6) {
                    spaces1.setColor("white");
                    spaces1.setResult("free");
                }
            });
        }

        if (timeSlots.getId() == 4) {
            spaces.forEach(spaces1 -> {
                if (spaces1.getId() == 7) {
                    spaces1.setColor("white");
                    spaces1.setResult("free");
                }
                if (spaces1.getId() == 8) {
                    spaces1.setColor("white");
                    spaces1.setResult("free");
                }
            });
        }

    }

    void removeSpaces2(Spaces sp) {

        spaces.forEach(spaces1 -> {
            if (sp.getStartTime().equals(spaces1.getStartTime())) {
                spaces1.setColor("red");
                spaces1.setResult("occuppied");
            }
        });

    }

}

