package com.example.trackmystudy.model;

import androidx.room.Entity;


import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "exams")
public class ExamEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private Date examTime;

    public ExamEntity(String name, Date examTime) {
        this.name = name;
        this.examTime = examTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }



    public Date getExamTime() {
        return examTime;
    }

    public void setExamTime(Date examTime) {
        this.examTime = examTime;
    }
}
