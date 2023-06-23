package com.example.healthsupporter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ExerciseListAdapter extends BaseAdapter {

    private Context context;
    private List<Exercise> exerciseList;

    public ExerciseListAdapter(Context context, List<Exercise> exerciseList) {
        this.context = context;
        this.exerciseList = exerciseList;
    }

    @Override
    public int getCount() {
        if (exerciseList == null) {
            return 0;
        }
        return exerciseList.size();
    }

    @Override
    public Object getItem(int position) {
        if (exerciseList == null || position >= exerciseList.size()) {
            return null;
        }
        return exerciseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_exercise, parent, false);
        }

        // Get exercise at the specified position
        Exercise exercise = (Exercise) getItem(position);

        if (exercise != null) {
            // Set exercise name, sets, and reps
            TextView exerciseNameTextView = convertView.findViewById(R.id.exerciseName);
            TextView setsTextView = convertView.findViewById(R.id.sets_text_view);
            TextView repsTextView = convertView.findViewById(R.id.reps_text_view);

            exerciseNameTextView.setText(exercise.getName());
            setsTextView.setText("set: " + String.valueOf(exercise.getSets())); // Add "set: " prefix
            repsTextView.setText("rep: " + String.valueOf(exercise.getReps())); // Add "rep: " prefix
        }

        return convertView;
    }

}
