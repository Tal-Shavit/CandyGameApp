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
import com.example.hw1.Models.UserItems;

import java.util.ArrayList;

public class RecorsAdapter extends RecyclerView.Adapter<RecorsAdapter.MyViewHolder> {


    private Context context;
    private ArrayList<UserItems> userItemsArrayList;
    private UserItems userItems;
    private CallBack_Location callBack_location;

    public RecorsAdapter(Context context, ArrayList<UserItems> userItemsArrayList,CallBack_Location callBack_location) {
        this.context = context;
        this.userItemsArrayList = userItemsArrayList;
        this.callBack_location = callBack_location;
    }

    public void setCallBack_location(CallBack_Location callBack_location){
        this.callBack_location = callBack_location;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_recyclerview_row, parent, false);
        return new MyViewHolder(view);//, callBack_location
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        userItems = userItemsArrayList.get(position);
        holder.fragmentList_LBL_name.setText(userItems.getName());
        holder.fragmentList_LBL_score.setText(""+userItems.getScore());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callBack_location != null){
                    callBack_location.locationReady(userItems);
                    Log.d("LALA", userItems.getName());
                }
            }
        });
        /*holder.fragmentList_LBL_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = userItems.getName();
                double lat = userItems.getLat();
                double lon = userItems.getLon();
                Log.d("LALA", name);

               //callBack_location.locationReady(lat,lon,name);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return userItemsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView fragmentList_LBL_score;
        TextView fragmentList_LBL_name;
        //private CardView cardView;

        public MyViewHolder(@NonNull View itemView){// ,CallBack_Location callBack_location
            super((itemView));
            fragmentList_LBL_name = itemView.findViewById(R.id.fragmentList_LBL_name);
            fragmentList_LBL_score = itemView.findViewById(R.id.fragmentList_LBL_score);
            //cardView = itemView.findViewById(R.id.fragmentList_LBL_name);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(callBack_location != null) {
                        int position = getAdapterPosition();
                        String name = userItemsArrayList.get(position).getName();
                        double lat = userItemsArrayList.get(position).getLat();
                        double lon = userItemsArrayList.get(position).getLon();
                        callBack_location.locationReady(lat,lon,name);
                    }
                }
            });*/
        }
    }

    public UserItems getItems(int i){
        return userItemsArrayList.get(i);
    }


    /*public void onClick(View itemView){
        itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(callBack_location != null) {
                        int position = getAdapterPosition();
                        String name = userItemsArrayList.get(position).getName();
                        double lat = userItemsArrayList.get(position).getLat();
                        double lon = userItemsArrayList.get(position).getLon();
                        callBack_location.locationReady(lat,lon,name);
                    }
                }
            });
    }*/

}
