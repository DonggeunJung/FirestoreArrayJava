package com.example.firestonearrayjava;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StudentsAdapter extends BaseAdapter {

    public StudentsAdapter(int layoutItem, ItemEvent itemEvent) {
        super(layoutItem, itemEvent);
    }

    public MainActivity.Student getData(int index) {
        return (MainActivity.Student)super.getData(index);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        View itemView = setClickable(holder, position);
        if(itemView == null) return;
        MainActivity.Student student = getData(position);

        TextView tvStudent = itemView.findViewById(R.id.tvStudent);
        tvStudent.setText(student.getInfo());
    }

}
