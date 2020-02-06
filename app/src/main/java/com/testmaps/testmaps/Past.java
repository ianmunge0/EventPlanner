package com.testmaps.testmaps;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Past extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private List<employee> employeeList;

    public Past() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // try app bundle / ensure sharedPref data is always updated on class initialized
        String vieweventsfromhomepage = "past";
        new ViewPastEventsAsyncTask(getContext()).execute(vieweventsfromhomepage);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_past, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        loaddata();
        adapter = new RecyclerAdapter(employeeList);
        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(employee emp, int position) {

                Toast.makeText(getContext(), emp.getName(), Toast.LENGTH_LONG).show();

            }
        });
        recyclerView.setAdapter(adapter);

        return root;
    }

    private void loaddata() {

        employeeList = new ArrayList<>();

        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("usernamepref", Context.MODE_PRIVATE);

        int endloop = sharedPref.getInt("noofpastevents", 1);
        int endloopminusone = endloop - 1;
        String[] eventsArray = new String[endloop];
        String[] asWhoArray = new String[endloop];

        for(int s = 0; s<=endloopminusone; s++){
            String stringS = Integer.toString(s);

            eventsArray[s] = sharedPref.getString("pasttitle"+stringS, "(No Past Events)");
            asWhoArray[s] = sharedPref.getString("pastaswho"+stringS, "");

            employeeList.add(new employee(eventsArray[s], asWhoArray[s]));
        }


    }
}







