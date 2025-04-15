package com.example.trackmystudy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trackmystudy.model.CourseEntity;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private final List<CourseEntity> courseList;

    public CourseAdapter(List<CourseEntity> courseList) {
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        CourseEntity course = courseList.get(position);
        String courseName = course.getCourseName(); // from entity
        holder.tvCourseName.setText(courseName);

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, CtDetailActivity.class);
            intent.putExtra("course_name", courseName); // pass name
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public void updateCourses(List<CourseEntity> newCourses) {
        courseList.clear();
        courseList.addAll(newCourses);
        notifyDataSetChanged();
    }



    static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView tvCourseName;

        CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
        }
    }
}
