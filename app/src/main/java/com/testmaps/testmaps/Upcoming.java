package com.testmaps.testmaps;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Upcoming extends Fragment {

    private RecyclerView recyclerView2;
    private RecyclerAdapter adapter2;
    private List<employee> employeeList2;

    public Upcoming() {
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
        String vieweventss = "upcoming";
        new ViewUpcomingEventsAsyncTask(getContext()).execute(vieweventss);
        View root = inflater.inflate(R.layout.activity_upcoming, container, false);

        recyclerView2 = root.findViewById(R.id.recyclerView2);
        recyclerView2.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView2.setLayoutManager(layoutManager);
        loaddata();
        adapter2 = new RecyclerAdapter(employeeList2);
        adapter2.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(employee emp, int position) {

                Toast.makeText(getContext(), emp.getName(), Toast.LENGTH_LONG).show();
            }
        });
        recyclerView2.setAdapter(adapter2);

        return root;
    }

    private void loaddata() {

        employeeList2 = new ArrayList<>();

        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("usernamepref", Context.MODE_PRIVATE);

        int endloop2 = sharedPref.getInt("noofupcomingevents", 1);
        int endloopminusone2 = endloop2 - 1;

        String[] eventsArray2 = new String[endloop2];
        String[] asWhoArray2 = new String[endloop2];

        for(int w = 0; w<=endloopminusone2; w++){
            String stringw = Integer.toString(w);

            eventsArray2[w] = sharedPref.getString("upcomingtitle"+stringw, "(No Upcoming Events)");
            asWhoArray2[w] = sharedPref.getString("upcomingaswho"+stringw, "");

            employeeList2.add(new employee(eventsArray2[w],asWhoArray2[w]));
        }

    }
}
