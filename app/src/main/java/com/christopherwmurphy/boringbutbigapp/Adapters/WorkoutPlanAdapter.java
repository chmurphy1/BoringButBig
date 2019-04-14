package com.christopherwmurphy.boringbutbigapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.christopherwmurphy.boringbutbigapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorkoutPlanAdapter extends BaseExpandableListAdapter {

    private List<String> header;
    //private List<ArrayList<String>> rows;

    private HashMap<Integer, List<String>> rows;
    private Context context;

    public WorkoutPlanAdapter(List<String> header, HashMap<Integer, List<String>> rows, Context context) {
        this.header = header;
        this.rows = rows;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return header.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return (rows.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return header.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return (rows.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String title = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.workout_plan_list_group, null);
        }
        TextView text = (TextView) convertView.findViewById(R.id.groupTitle);
        text.setText(title);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String list = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.workout_plan_list_items, null);
        }
        TextView text = (TextView) convertView.findViewById(R.id.workoutItems);
        text.setText(list);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
