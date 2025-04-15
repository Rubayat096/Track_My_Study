package com.example.trackmystudy.model;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class Converters {
    private static final Gson gson = new Gson();



    @TypeConverter
    public static String fromList(List<Integer> list) {
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<Integer> toList(String json) {
        if (json == null) return null;
        Type type = new TypeToken<List<Integer>>() {}.getType();
        return gson.fromJson(json, type);
    }





    @TypeConverter
    public static Long fromDate(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }
}
