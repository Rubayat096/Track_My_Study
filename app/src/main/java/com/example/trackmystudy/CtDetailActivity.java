package com.example.trackmystudy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class CtDetailActivity extends AppCompatActivity {

    TextView tvCourseName;
    EditText etCt1, etCt2, etCt3, etCt4;
    Button btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ct_detail);



        tvCourseName = findViewById(R.id.tvCourseName);
        etCt1 = findViewById(R.id.etCt1);
        etCt2 = findViewById(R.id.etCt2);
        etCt3 = findViewById(R.id.etCt3);
        etCt4 = findViewById(R.id.etCt4);
        btnCalculate = findViewById(R.id.btnCalculate);



        String courseName = getIntent().getStringExtra("course_name");
        if (courseName != null) {
            tvCourseName.setText(courseName);
        }







        btnCalculate.setOnClickListener(v -> {
            try {
                float ct1 = Float.parseFloat(etCt1.getText().toString());
                float ct2 = Float.parseFloat(etCt2.getText().toString());
                float ct3 = Float.parseFloat(etCt3.getText().toString());
                float ct4 = Float.parseFloat(etCt4.getText().toString());

                float total = ct1 + ct2 + ct3 + ct4;
                float lowest = Math.min(Math.min(ct1, ct2), Math.min(ct3, ct4));
                float best3Average = (total - lowest) / 3f;

                Toast.makeText(
                        this,
                        "Average of Best 3 CTs: " + String.format("%.2f", best3Average),
                        Toast.LENGTH_LONG
                ).show();

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter all CT marks!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
