package com.example.andoridnusa;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity {

    private FloatingActionButton fabAddTask;
    private RecyclerView taskRecyclerView;
    private ArrayList<ToDo> taskList;
    private ToDoAdapter adapter;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fabAddTask = findViewById(R.id.fabAddTask);
        taskRecyclerView = findViewById(R.id.taskRecyclerView);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);

        taskList = new ArrayList<>();
        adapter = new ToDoAdapter(this, taskList);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskRecyclerView.setAdapter(adapter);

        loadTasks();

        fabAddTask.setOnClickListener(v -> openAddTaskDialog());

        bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                return true;
            } else if (id == R.id.nav_settings) {
                startActivity(new Intent(HomeActivity.this, SettingActivity.class));
                return true;
            }
            return false;
        });
    }

    private void openAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_task, null);
        builder.setView(view);

        EditText etTaskName = view.findViewById(R.id.etTaskName);
        EditText etTaskDeadline = view.findViewById(R.id.etTaskDeadline);
        view.findViewById(R.id.btnPickDateTime).setOnClickListener(v -> pickDateTime(etTaskDeadline));

        view.findViewById(R.id.btnSaveTask).setOnClickListener(v -> {
            String taskText = etTaskName.getText().toString().trim();
            String deadlineText = etTaskDeadline.getText().toString().trim();

            if (!taskText.isEmpty() && !deadlineText.isEmpty()) {
                String id = UUID.randomUUID().toString();
                ToDo task = new ToDo(id, taskText, deadlineText);
                FirebaseDatabase.getInstance().getReference("tasks").child(id).setValue(task);
                Toast.makeText(HomeActivity.this, "Tugas Ditambahkan", Toast.LENGTH_SHORT).show();
            }
        });

        builder.create().show();
    }

    private void pickDateTime(EditText etDeadline) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePicker = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    TimePickerDialog timePicker = new TimePickerDialog(this,
                            (view1, hourOfDay, minute) -> {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);
                                String datetime = DateFormat.format("MM-dd HH:mm", calendar).toString();
                                etDeadline.setText(datetime);
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            DateFormat.is24HourFormat(this));
                    timePicker.show();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }

    private void loadTasks() {
        FirebaseDatabase.getInstance().getReference("tasks")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        taskList.clear();
                        for (DataSnapshot data : snapshot.getChildren()) {
                            ToDo task = data.getValue(ToDo.class);
                            if (task != null) {
                                taskList.add(task);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // error handling
                    }
                });
    }
}
