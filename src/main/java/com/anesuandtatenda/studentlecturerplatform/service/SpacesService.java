package com.anesuandtatenda.studentlecturerplatform.service;

import com.anesuandtatenda.studentlecturerplatform.model.Spaces;

import java.util.Collection;

public interface SpacesService extends BaseService<Spaces, Spaces, Spaces> {

    Collection<Spaces> findByLecturerId(long lecturerId);

    Collection<Spaces> evaluateByLecturerId(long lecturerId, int dayOfWeek);

}
