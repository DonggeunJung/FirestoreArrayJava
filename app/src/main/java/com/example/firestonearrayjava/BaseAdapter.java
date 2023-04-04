package com.example.firestonearrayjava;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseAdapter extends RecyclerView.Adapter {

    int layoutItem = -1;

    public BaseAdapter(int layoutItem, ItemEvent itemEvent) {
        this.layoutItem = layoutItem;
        this.itemEvent = itemEvent;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(layoutItem, parent, false);
        return new BaseVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        View itemView = ((BaseVH)holder).itemView;
        setClickable(itemView, position);
    }

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

}
