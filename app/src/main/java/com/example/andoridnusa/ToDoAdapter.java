package com.example.andoridnusa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {

    private Context context;
    private List<ToDo> toDoList;

    public ToDoAdapter(Context context, List<ToDo> toDoList) {
        this.context = context;
        this.toDoList = toDoList;
    }

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new ToDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position) {
        ToDo task = toDoList.get(position);
        holder.taskText.setText(task.getTask());

        holder.doneButton.setOnClickListener(v -> {
            FirebaseDatabase.getInstance().getReference("tasks").child(task.getId()).removeValue();
        });
    }

    @Override
    public int getItemCount() {
        return toDoList.size();
    }

    static class ToDoViewHolder extends RecyclerView.ViewHolder {
        TextView taskText;
        Button doneButton;

        public ToDoViewHolder(@NonNull View itemView) {
            super(itemView);
            taskText = itemView.findViewById(R.id.taskText);
            doneButton = itemView.findViewById(R.id.btnDone);
        }
    }
}
