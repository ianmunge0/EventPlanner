package com.testmaps.testmaps;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {


    private List<employee> employeeList;
    private OnItemClickListener listener;

    public RecyclerAdapter(List<employee> employeeList) {
        this.employeeList = employeeList;
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recycler,parent,false);
        return new RecyclerViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        employee emp = employeeList.get(position);
        holder.bind(emp,position);

    }

    @Override
    public int getItemCount() {

        if (employeeList == null)
        {
            return 0;
        }

        return employeeList.size();

    }

    public interface OnItemClickListener {
        void onItemClick(employee emp, int position);
    }

}
