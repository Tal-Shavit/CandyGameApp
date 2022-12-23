package com.example.hw1.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw1.Interfaces.CallBack_Location;
import com.example.hw1.Models.DataBase;
import com.example.hw1.Models.UserItems;
import com.example.hw1.MySP;
import com.example.hw1.R;
import com.example.hw1.RecorsAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Fragment_List extends Fragment {

    private DataBase newDb2;
    private RecyclerView fragmentList_RV_records;
    private UserItems userItems;
    private RecorsAdapter recAdapter;

    protected View view;
    private ArrayList<UserItems> userItemsArrayList = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<Integer> scores = new ArrayList<>();

    private CallBack_Location callBack_location;

    public static Fragment_List newInstance() {
        Fragment_List fragment_list = new Fragment_List();
        return fragment_list;
    }

    public void setCallBack_location(CallBack_Location callBack_location){
        this.callBack_location = callBack_location;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       return view = inflater.inflate(R.layout.fragment_list, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        dataInitialize();
        findViews(view);
        initRecycler();
    }

    private void initRecycler() {
        fragmentList_RV_records.setLayoutManager(new LinearLayoutManager(getContext()));//,LinearLayoutManager.VERTICAL,false
        fragmentList_RV_records.setHasFixedSize(true);
        recAdapter = new RecorsAdapter(getContext(),userItemsArrayList,callBack_location);
        fragmentList_RV_records.setAdapter(recAdapter);
        recAdapter.notifyDataSetChanged();
    }


    private void dataInitialize() {
        String fromJSON2 = MySP.getInstance(getContext()).getStringSP("MY_DB1","");
        newDb2 = new Gson().fromJson(fromJSON2,DataBase.class);

        if(newDb2 == null){
            newDb2 = new DataBase();
        }

        if(newDb2 != null){
            for (int i = 0; i < newDb2.getUserItems().size(); i++) {
                names.add(newDb2.getUserItems().get(i).getName());
                scores.add(newDb2.getUserItems().get(i).getScore());
                double lat = newDb2.getUserItems().get(i).getLat();
                double lon = newDb2.getUserItems().get(i).getLon();
                userItems = new UserItems(names.get(i),scores.get(i),lat,lon);
                userItemsArrayList.add(userItems);
            }
        }
    }

    private void findViews(View view) {
        fragmentList_RV_records = view.findViewById(R.id.fragmentList_RV_records);
    }
}

