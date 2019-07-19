package com.testmaps.testmaps;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

/*public class ViewPagerAdapter extends FragmentPagerAdapter{

    final int PAGE_COUNT = 3;

    //Constructor of the class
    Context viewpageradaptercontext;
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    // This method will be invoked when a page is requested to create
    @Override
    public Fragment getItem(int arg0) {
        Bundle data = new Bundle();
        switch(arg0){
            //tab1 selected
            case 0:
                //Past past = new Past(viewpageradaptercontext);
                Past past = new Past();
                data.putInt("current_page", arg0+1);
                past.setArguments(data);
                return past;

            //tab2 selected
            case 1:
                Current current = new Current();
                data.putInt("current_page", arg0+1);
                current.setArguments(data);
                return current;

            //tab3 selected
            case 2:
                Upcoming upcoming = new Upcoming();
                data.putInt("current_page", arg0+1);
                upcoming.setArguments(data);
                return upcoming;
        }

        return null;
    }

    // Returns the number of pages
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}*/
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                return new Past();//.newInstance("FirstFragment, Instance 1"); without new
            case 1:
                return new Current();//.newInstance("SecondFragment, Instance 1"); without new
            case 2:
                return new Upcoming();//.newInstance("SecondFragment, Instance 2"); without new
        }

        return null;
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}