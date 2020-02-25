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

public class Current extends Fragment {

    private RecyclerView recyclerView1;
    private RecyclerAdapter adapter1;
    private List<employee> employeeList1;

    public Current() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // try app bundle / ensure sharedPref data is always updated on class initialized

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String vieweventsfromviewpast = "current";
        new ViewCurrentEventsAsyncTask(getContext()).execute(vieweventsfromviewpast);
        View root = inflater.inflate(R.layout.activity_current, container, false);

        recyclerView1 = root.findViewById(R.id.recyclerView1);
        recyclerView1.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView1.setLayoutManager(layoutManager);
        loaddata();
        adapter1 = new RecyclerAdapter(employeeList1);
        adapter1.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(employee emp, int position) {

                Toast.makeText(getContext(), emp.getName(), Toast.LENGTH_LONG).show();
            }
        });
        recyclerView1.setAdapter(adapter1);

        return root;
    }

    private void loaddata() {

        employeeList1 = new ArrayList<>();

        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("usernamepref", Context.MODE_PRIVATE);

        int endloop1 = sharedPref.getInt("noofcurrentevents", 1);
        int endloopminusone1 = endloop1 - 1;

        String[] eventsArray1 = new String[endloop1];
        String[] asWhoArray1 = new String[endloop1];

        for(int t = 0; t<=endloopminusone1; t++){
            String stringt = Integer.toString(t);

            eventsArray1[t] = sharedPref.getString("currenttitle"+stringt, "(No Current Events)");
            asWhoArray1[t] = sharedPref.getString("currentaswho"+stringt, "");

            employeeList1.add(new employee(eventsArray1[t],asWhoArray1[t]));
        }

    }
}
