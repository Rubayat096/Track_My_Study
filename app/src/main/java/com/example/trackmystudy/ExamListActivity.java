package com.example.trackmystudy;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trackmystudy.model.AppDatabase;
import com.example.trackmystudy.model.ExamDao;
import com.example.trackmystudy.model.ExamEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExamListActivity extends AppCompatActivity {

    private RecyclerView rv;
    private ExamAdapter adapter;
    private ExamDao examDao;
    private List<ExamEntity> examList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_list);

        AppDatabase db = AppDatabase.getInstance(this);
        examDao = db.examDao();

        rv = findViewById(R.id.rvExams);
        rv.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fabAddExam);
        fab.setOnClickListener(v -> showAddExamDialog());

        loadExams();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();
                ExamEntity exam = examList.get(pos);

                new AlertDialog.Builder(ExamListActivity.this)
                        .setTitle("Delete Exam")
                        .setMessage("Delete \"" + exam.getName() + "\"?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            AsyncTask.execute(() -> {
                                examDao.delete(exam);
                                runOnUiThread(() -> loadExams());
                            });
                        })
                        .setNegativeButton("No", (dialog, which) -> adapter.notifyItemChanged(pos))
                        .show();
            }
        }).attachToRecyclerView(rv);
    }

    private void loadExams() {
        examList = examDao.getAll();
        adapter = new ExamAdapter(examList);
        rv.setAdapter(adapter);
    }

    private void showAddExamDialog() {
        EditText inputName = new EditText(this);
        inputName.setHint("Enter Exam Name");
        inputName.setInputType(InputType.TYPE_CLASS_TEXT);

        new AlertDialog.Builder(this)
                .setTitle("New Exam")
                .setView(inputName)
                .setPositiveButton("Next", (dialog, which) -> {
                    String name = inputName.getText().toString().trim();
                    if (name.isEmpty()) {
                        Toast.makeText(this, "Name can't be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    final Calendar calendar = Calendar.getInstance();

                    DatePickerDialog datePicker = new DatePickerDialog(this,
                            (view, year, month, dayOfMonth) -> {
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                TimePickerDialog timePicker = new TimePickerDialog(this,
                                        (view1, hourOfDay, minute) -> {
                                            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                            calendar.set(Calendar.MINUTE, minute);
                                            calendar.set(Calendar.SECOND, 0);

                                            ExamEntity exam = new ExamEntity(name, calendar.getTime());
                                            examDao.insert(exam);
                                            loadExams();
                                        }, 12, 0, false);
                                timePicker.show();
                            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    datePicker.show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
