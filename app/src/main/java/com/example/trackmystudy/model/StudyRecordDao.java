package com.example.trackmystudy.model;

import androidx.room.Dao;
import androidx.room.Insert;

import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import androidx.room.Update;


@Dao
public interface StudyRecordDao {
    @Query("SELECT * FROM study_records WHERE date = :date")
    StudyRecord getForDate(String date);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(StudyRecord record);

    @Update
    void update(StudyRecord record);
}
