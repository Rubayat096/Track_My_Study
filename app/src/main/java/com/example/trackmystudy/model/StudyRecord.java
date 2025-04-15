package com.example.trackmystudy.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "study_records")
public class StudyRecord {
    @PrimaryKey
    @NonNull
    public String date;



    public long totalSeconds;

    public StudyRecord(@NonNull String date, long totalSeconds) {
        this.date = date;
        this.totalSeconds = totalSeconds;
    }
}
