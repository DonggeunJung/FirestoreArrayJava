package com.example.firestonearrayjava;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StudentsAdapter extends BaseAdapter {
    List<MainActivity.Student> list = null;

    public StudentsAdapter(int layoutItem, ItemEvent itemEvent) {
        super(layoutItem, itemEvent);
    }

    public void setList(List<MainActivity.Student> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(list == null || list.size() <= position) return;
        super.onBindViewHolder(holder, position);
        MainActivity.Student student = list.get(position);
        View itemView = ((BaseVH)holder).itemView;
        TextView tvStudent = itemView.findViewById(R.id.tvStudent);
        tvStudent.setText(student.getInfo());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

}
