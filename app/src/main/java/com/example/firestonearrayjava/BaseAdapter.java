package com.example.firestonearrayjava;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseAdapter extends RecyclerView.Adapter {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {}

    @Override
    public int getItemCount() {
        return 0;
    }

    protected void setClickable(View v, int position) {
        v.setTag(position);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemEvent != null) {
                    int index = (int) view.getTag();
                    itemEvent.onClickItem(index);
                }
            }
        });
    }

    class BaseVH extends RecyclerView.ViewHolder {
        public View itemView;

        public BaseVH(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }

    public interface ItemEvent {
        void onClickItem(int index);
    }

    ItemEvent itemEvent = null;

    public void setListener(ItemEvent itemEvent) {
        this.itemEvent = itemEvent;
    }

}
