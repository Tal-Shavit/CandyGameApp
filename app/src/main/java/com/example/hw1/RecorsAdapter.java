package com.example.hw1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw1.Interfaces.CallBack_Location;
import com.example.hw1.Interfaces.RecyclerViewInterface;
import com.example.hw1.Models.UserItems;

import java.util.ArrayList;

public class RecorsAdapter extends RecyclerView.Adapter<RecorsAdapter.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    private Context context;
    private ArrayList<UserItems> userItemsArrayList;
    private UserItems userItems;

    public RecorsAdapter(Context context, ArrayList<UserItems> userItemsArrayList,RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.userItemsArrayList = userItemsArrayList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_recyclerview_row, parent, false);
        return new MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        userItems = userItemsArrayList.get(position);
        holder.fragmentList_LBL_name.setText(userItems.getName());
        holder.fragmentList_LBL_score.setText(""+userItems.getScore());
    }

    @Override
    public int getItemCount() {
        return userItemsArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView fragmentList_LBL_score;
        TextView fragmentList_LBL_name;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface){
            super((itemView));
            fragmentList_LBL_name = itemView.findViewById(R.id.fragmentList_LBL_name);
            fragmentList_LBL_score = itemView.findViewById(R.id.fragmentList_LBL_score);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                            recyclerViewInterface.onItemClick(position);
                    }
                }
            });
        }
    }
}
