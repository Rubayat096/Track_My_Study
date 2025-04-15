package com.example.trackmystudy.model;

import androidx.room.Dao;
import androidx.room.Delete;


import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface CourseDao {
    @Query("SELECT * FROM courses")
    List<CourseEntity> getAll();

    @Insert
    long insert(CourseEntity course);

    @Update
    void update(CourseEntity course);

    @Delete
    void delete(CourseEntity course);
}
