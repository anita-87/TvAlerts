package com.tvalerts.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tvalerts.R;
import com.tvalerts.activities.ShowActivity;
import com.tvalerts.adapters.PersonListAdapter;
import com.tvalerts.domain.Cast;
import com.tvalerts.domain.Person;
import com.tvalerts.domain.Show;
import com.tvalerts.tasks.CastSeachAsyncTask;
import com.tvalerts.tasks.SearchShowAsyncTask;

import java.util.List;

/**
 * Created by anita on 5/02/16.
 */
public class CastInformationFragment extends Fragment {

    private static final String TAG = "CastInformationFragment";

    private ShowActivity parentActivity;
    private PersonListAdapter arrayAdapter;
    private FloatingActionButton addButton;

    public CastInformationFragment(){
        //Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        this.arrayAdapter = this.parentActivity.getArrayAdapter();

        new CastSeachAsyncTask(getActivity(), arrayAdapter, this.parentActivity.getShow().getId()).execute();

        View view = inflater.inflate(R.layout.show_cast_information_fragment, container, false);

        ListView listView = (ListView) view.findViewById(R.id.castList);
        listView.setTextFilterEnabled(true);
        listView.setAdapter(this.arrayAdapter);

        addButton = (FloatingActionButton) view.findViewById(R.id.add_show_fab_cast);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.saveShow();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.parentActivity = (ShowActivity) activity;
    }
}
