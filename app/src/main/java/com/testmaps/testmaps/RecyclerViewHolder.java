package com.testmaps.testmaps;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {


    private TextView name;
    private TextView address;
    private employee emp;
    private int position;

    public RecyclerViewHolder(View itemView, final RecyclerAdapter.OnItemClickListener listener) {
        super(itemView);

        name = itemView.findViewById(R.id.name);
        address = itemView.findViewById(R.id.address);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onItemClick(emp,position);

            }
        });

    }

    public void bind(employee emp, int position)
    {
        this.emp = emp;
        this.position = position;
        name.setText(emp.getName());
        address.setText(emp.getAddress());

    }


}
