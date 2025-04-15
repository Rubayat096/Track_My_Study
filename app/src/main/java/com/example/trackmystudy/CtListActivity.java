package com.example.trackmystudy;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trackmystudy.model.AppDatabase;
import com.example.trackmystudy.model.CourseDao;
import com.example.trackmystudy.model.CourseEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CtListActivity extends AppCompatActivity {

    private List<CourseEntity> courseList;
    private CourseAdapter adapter;
    private CourseDao courseDao;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ct_list);



        AppDatabase db = AppDatabase.getInstance(this);
        courseDao = db.courseDao();

        rv = findViewById(R.id.rvCourses);
        rv.setLayoutManager(new LinearLayoutManager(this));


        loadCourses();


        FloatingActionButton fab = findViewById(R.id.fabAddCourse);
        fab.setOnClickListener(v -> showAddCourseDialog());

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();
                CourseEntity selectedCourse = courseList.get(pos);

                if (direction == ItemTouchHelper.RIGHT) {
                    // Swipe right: delete with confirmation
                    new AlertDialog.Builder(CtListActivity.this)
                            .setTitle("Delete Course")
                            .setMessage("Are you sure you want to delete \"" + selectedCourse.getName() + "\"?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                AsyncTask.execute(() -> {
                                    courseDao.delete(selectedCourse);
                                    runOnUiThread(() -> loadCourses());
                                });
                            })
                            .setNegativeButton("Cancel", (dialog, which) -> {
                                adapter.notifyItemChanged(pos); // Undo swipe
                            })
                            .setCancelable(false)
                            .show();

                } else if (direction == ItemTouchHelper.LEFT) {
                    // Swipe left: edit course name
                    EditText input = new EditText(CtListActivity.this);
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    input.setText(selectedCourse.getName());

                    new AlertDialog.Builder(CtListActivity.this)
                            .setTitle("Edit Course")
                            .setView(input)
                            .setPositiveButton("Update", (dialog, which) -> {
                                String updatedName = input.getText().toString().trim();
                                if (!updatedName.isEmpty()) {
                                    selectedCourse.setName(updatedName);
                                    AsyncTask.execute(() -> {
                                        courseDao.update(selectedCourse);
                                        runOnUiThread(() -> loadCourses());
                                    });
                                }
                            })
                            .setNegativeButton("Cancel", (dialog, which) -> {
                                adapter.notifyItemChanged(pos); // Undo swipe
                            })
                            .setCancelable(false)
                            .show();
                }
            }
        }).attachToRecyclerView(rv);
    }

    private void showAddCourseDialog() {
        EditText input = new EditText(this);
        input.setHint("Enter course name");
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        new AlertDialog.Builder(this)
                .setTitle("Add New Course")
                .setView(input)
                .setPositiveButton("Save", (dialog, which) -> {
                    String courseName = input.getText().toString().trim();
                    if (!courseName.isEmpty()) {
                        CourseEntity newCourse = new CourseEntity(courseName);
                        AsyncTask.execute(() -> {
                            courseDao.insert(newCourse);
                            runOnUiThread(() -> loadCourses());
                        });
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }




    private void loadCourses() {
        AsyncTask.execute(() -> {
            courseList = courseDao.getAll();
            runOnUiThread(() -> {
                if (adapter == null) {
                    adapter = new CourseAdapter(courseList);
                    rv.setAdapter(adapter);
                } else {
                    adapter.updateCourses(courseList);
                }
            });
        });
    }
}
