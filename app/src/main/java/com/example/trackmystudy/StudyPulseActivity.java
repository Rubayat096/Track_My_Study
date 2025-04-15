package com.example.trackmystudy;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.Button;



import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trackmystudy.model.AppDatabase;
import com.example.trackmystudy.model.StudyRecord;
import com.example.trackmystudy.model.StudyRecordDao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StudyPulseActivity extends AppCompatActivity {

    private TextView tvTimer, tvTodayTotal;
    private Button btnStartStop;

    private boolean running = false;
    private long startTime = 0L;
    private long accumulated = 0L; // ms
    private Handler handler = new Handler();
    private Runnable updateRunnable;

    private StudyRecordDao recordDao;
    private String todayKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_pulse);

        tvTimer      = findViewById(R.id.tvTimer);
        btnStartStop = findViewById(R.id.btnStartStop);
        tvTodayTotal = findViewById(R.id.tvTodayTotal);

        recordDao = AppDatabase.getInstance(this).studyRecordDao();
        todayKey = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(new Date());

        loadTodayTotal();

        btnStartStop.setOnClickListener(v -> {
            if (!running) startTimer();
            else stopTimer();
        });

        updateRunnable = new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.elapsedRealtime() - startTime + accumulated;
                tvTimer.setText(formatMs(elapsed));
                handler.postDelayed(this, 500);
            }
        };
    }

    private void loadTodayTotal() {
        StudyRecord rec = recordDao.getForDate(todayKey);
        long totalMs = rec == null ? 0L : rec.totalSeconds * 1000L;
        tvTodayTotal.setText("Today’s Study: " + formatMs(totalMs));
    }

    private void startTimer() {
        running = true;
        btnStartStop.setText("Stop");
        startTime = SystemClock.elapsedRealtime();
        handler.post(updateRunnable);
    }

    private void stopTimer() {
        running = false;
        handler.removeCallbacks(updateRunnable);
        long sessionMs = SystemClock.elapsedRealtime() - startTime;
        accumulated = 0L;

        // Update DB
        StudyRecord rec = recordDao.getForDate(todayKey);
        long prev = rec == null ? 0L : rec.totalSeconds;
        long newTotal = prev + sessionMs/1000L;
        StudyRecord newRec = new StudyRecord(todayKey, newTotal);
        recordDao.insert(newRec);

        // Reset display
        tvTimer.setText("00:00:00");
        btnStartStop.setText("Start");
        loadTodayTotal();
    }



    private String formatMs(long ms) {
        int totalSec = (int)(ms / 1000);
        int hrs = totalSec / 3600;
        int mins = (totalSec % 3600) / 60;
        int secs = totalSec % 60;
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hrs, mins, secs);
    }
}
