package com.example.trackmystudy;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trackmystudy.model.ExamEntity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import java.util.Locale;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ExamViewHolder> {

    private List<ExamEntity> exams;
    private Handler handler = new Handler(Looper.getMainLooper());

    public ExamAdapter(List<ExamEntity> exams) {
        this.exams = exams;
        startCountdownUpdater();
    }

    private void startCountdownUpdater() {
        handler.postDelayed(() -> {
            notifyDataSetChanged();
            startCountdownUpdater(); // repeat
        }, 60000); // update every 1 minute
    }

    @NonNull
    @Override
    public ExamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exam, parent, false);
        return new ExamViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamViewHolder holder, int position) {
        ExamEntity exam = exams.get(position);
        holder.name.setText(exam.getName());

        // Updated the method name from getExamDate() to getExamTime()
        Date examDate = exam.getExamTime();  // Correct method name
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault());
        holder.date.setText(sdf.format(examDate));

        long diff = examDate.getTime() - System.currentTimeMillis();
        if (diff <= 0) {
            holder.countdown.setText("Started/Passed");
        } else {


            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff / (1000 * 60 * 60)) % 24;
            long mins = (diff / (1000 * 60)) % 60;
            holder.countdown.setText(days + "d " + hours + "h " + mins + "m");


        }
    }

    @Override
    public int getItemCount() {
        return exams.size();
    }

    public static class ExamViewHolder extends RecyclerView.ViewHolder {
        TextView name, date, countdown;

        public ExamViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvExamName);
            date = itemView.findViewById(R.id.tvExamDate);
            countdown = itemView.findViewById(R.id.tvCountdown);
        }
    }
}
