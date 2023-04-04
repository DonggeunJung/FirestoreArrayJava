package com.example.firestonearrayjava;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StudentsAdapter extends BaseAdapter {
    List<MainActivity.Student> list = null;

    public void setList(List<MainActivity.Student> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.student_item, parent, false);
        return new BaseVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(list == null || list.size() <= position) return;
        MainActivity.Student student = list.get(position);
        View itemView = ((BaseVH)holder).itemView;
        TextView tvStudent = itemView.findViewById(R.id.tvStudent);
        tvStudent.setText(student.getInfo());

        View layoutMain = itemView.findViewById(R.id.layoutMain);
        setClickable(layoutMain, position);
    }

    @Override
    public int getItemCount() {
        if(list == null)
            return 0;
        return list.size();
    }

}
