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
        return (MainActivity.Student)list.get(index);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(list == null || list.size() <= position) return;
        super.onBindViewHolder(holder, position);
        MainActivity.Student student = getData(position);
        View itemView = ((BaseVH)holder).itemView;

        TextView tvStudent = itemView.findViewById(R.id.tvStudent);
        tvStudent.setText(student.getInfo());
    }

}
