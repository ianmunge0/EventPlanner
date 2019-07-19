package com.testmaps.testmaps;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class ListViewContactAdapter extends BaseAdapter {
    private static ArrayList<ListViewContactItem> listContact=null;

    private LayoutInflater mInflater;
    private Context context;

    public ListViewContactAdapter(Context photosFragment, ArrayList<ListViewContactItem> results){
        listContact = results;
        //   mInflater = LayoutInflater.from(photosFragment);
        this.context=photosFragment;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listContact.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return listContact.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.list_view_items, null);
            holder = new ViewHolder();
            //holder.txtpickuppoint = (TextView) convertView.findViewById(R.id.rd_header_text);
            holder.txtarea = (TextView) convertView.findViewById(R.id.area);
            //holder.txtlandmark = (TextView) convertView.findViewById(R.id.landmark);
            holder.txtstreetname = (TextView) convertView.findViewById(R.id.streetname);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //holder.txtpickuppoint.setText(listContact.get(position).getPickUpPoint());
        holder.txtarea.setText(listContact.get(position).getArea());
        //holder.txtlandmark.setText(listContact.get(position).getLandmark());
        holder.txtstreetname.setText(listContact.get(position).getStreetName());

        return convertView;
    }

    static class ViewHolder{
        TextView txtpickuppoint, txtarea,txtlandmark,txtstreetname;
    }
}
