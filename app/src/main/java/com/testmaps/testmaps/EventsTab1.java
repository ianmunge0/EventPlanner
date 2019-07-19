package com.testmaps.testmaps;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/*public class EventsTab1 extends FragmentActivity {

    ActionBar mActionBar;
    ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_tab1);


        mActionBar = getActionBar();


        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


        mPager = (ViewPager) findViewById(R.id.pager);


        FragmentManager fm = getSupportFragmentManager();


        ViewPager.SimpleOnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mActionBar.setSelectedNavigationItem(position);
            }
        };


        mPager.setOnPageChangeListener(pageChangeListener);


        ViewPagerAdapter fragmentPagerAdapter = new ViewPagerAdapter(fm);


        mPager.setAdapter(fragmentPagerAdapter);

        mActionBar.setDisplayShowTitleEnabled(true);


        ActionBar.TabListener tabListener = new ActionBar.TabListener() {

            @Override
            public void onTabReselected(Tab arg0,
                                        android.app.FragmentTransaction arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTabSelected(Tab tab,
                                      android.app.FragmentTransaction ft) {
                mPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(Tab tab,
                                        android.app.FragmentTransaction ft) {
                // TODO Auto-generated method stub

            }
        };


        Tab tab = mActionBar.newTab()
                .setText("PAST")
                //.setIcon(R.drawable.android)
                .setTabListener(tabListener);

        mActionBar.addTab(tab);


        tab = mActionBar.newTab()
                .setText("CURRENT")
                //.setIcon(R.drawable.apple)
                .setTabListener(tabListener);

        mActionBar.addTab(tab);

        //tab 3
        tab = mActionBar.newTab()
                .setText("UPCOMING")
                //.setIcon(R.drawable.apple)
                .setTabListener(tabListener);

        mActionBar.addTab(tab);


    }

}*/
public class EventsTab1 extends AppCompatActivity {

    // Declaring Your View and Variables
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Past", "Current", "Upcoming"};
    int Numboftabs = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_tab1);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(android.R.color.holo_blue_light);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}