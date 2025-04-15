package com.example.trackmystudy.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Arrays;
import java.util.List;

@Entity(tableName = "courses")
@TypeConverters({Converters.class})
public class CourseEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private List<Integer> ctMarks;

    public CourseEntity(String name) {
        this.name = name;
        this.ctMarks = Arrays.asList(0, 0, 0, 0);
    }

    public CourseEntity() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCourseName() {
        return name;
    }




    public List<Integer> getCtMarks() {
        return ctMarks;
    }

    public void setCtMarks(List<Integer> ctMarks) {
        this.ctMarks = ctMarks;
    }
}
