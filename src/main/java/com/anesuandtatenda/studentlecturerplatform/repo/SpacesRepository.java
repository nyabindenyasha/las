package com.anesuandtatenda.studentlecturerplatform.repo;

import com.anesuandtatenda.studentlecturerplatform.model.Spaces;

import java.util.Collection;

public interface SpacesRepository extends BaseRepository<Spaces> {

    Collection<Spaces> findByLecturerId(long lecturerId);
}
