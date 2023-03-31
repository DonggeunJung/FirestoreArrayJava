package com.example.firestonearrayjava;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StudentsAdapter extends RecyclerView.Adapter {
    List<MainActivity.Student> students = null;

    public StudentsAdapter(ItemEvent itemEvent) {
        this.itemEvent = itemEvent;
    }

    public void setStudents(List<MainActivity.Student> students) {
        this.students = students;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.student_item, parent, false);
        return new StudentVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((StudentVH)holder).bind(position);
    }

    @Override
    public int getItemCount() {
        if(students == null)
            return 0;
        return students.size();
    }

    class StudentVH extends RecyclerView.ViewHolder {
        View itemView;
        int index = 0;
        MainActivity.Student student;

        public StudentVH(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        public void bind(int index) {
            if(students == null || students.size() <= index) return;
            this.index = index;
            student = students.get(index);
            View layoutMain = itemView.findViewById(R.id.layoutMain);
            TextView tvStudent = itemView.findViewById(R.id.tvStudent);
            tvStudent.setText(student.getInfo());

            layoutMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(itemEvent != null)
                        itemEvent.onClickItem(index, student);
                }
            });
        }
    }

    public interface ItemEvent {
        void onClickItem(int index, MainActivity.Student student);
    }

    ItemEvent itemEvent = null;
}
