package com.example.trackmystudy.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExamDao {
    @Query("SELECT * FROM exams ORDER BY examTime ASC")
    List<ExamEntity> getAll();



    @Insert
    long insert(ExamEntity exam);

    @Delete
    void delete(ExamEntity exam);
}
