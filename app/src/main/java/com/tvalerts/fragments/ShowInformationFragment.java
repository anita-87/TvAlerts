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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tvalerts.R;
import com.tvalerts.activities.ShowActivity;
import com.tvalerts.domain.Show;

/**
 * Created by anita on 5/02/16.
 */
public class ShowInformationFragment extends Fragment {

    private static final String TAG = "ShowInformationFragment";

    private ShowActivity parentActivity;

    private Show show;
    private TextView showName;
    private TextView showStatus;
    private ImageView imageView;
    private TextView showNetwork;
    private TextView showSchedule;
    private TextView showSummary;
    private FloatingActionButton addButton;

    public ShowInformationFragment(){
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

        //Get the show from the parent activity
        this.show = this.parentActivity.getShow();

        //Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.show_information_fragment, container, false);

        //Setup the 'showName'
        this.showName = (TextView) view.findViewById(R.id.show_name);
        this.setShowName();

        //Setup the 'showGender'
        this.showStatus = (TextView) view.findViewById(R.id.show_status);
        this.setShowStatus();

        this.imageView = (ImageView) view.findViewById(R.id.imageView);
        this.setImageView();

        this.showNetwork = (TextView) view.findViewById(R.id.show_network);
        this.setShowNetwork();

        this.showSchedule = (TextView) view.findViewById(R.id.show_schedule);
        this.setShowSchedule();

        this.showSummary = (TextView) view.findViewById(R.id.show_summary);
        this.setShowSummary();

        this.addButton = (FloatingActionButton) view.findViewById(R.id.add_show_fab);
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
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

    private void setShowName(){
        this.showName.setText(this.show.getName());
    }

    private void setShowStatus(){
        this.showStatus.setText(this.show.getStatus().toString());
    }

    private void setImageView(){
        Picasso.with(getActivity().getBaseContext())
                .load(this.show.getImage().getMedium())
                .resize(110, 154)
                .centerCrop()
                .into(this.imageView);
    }

    private void setShowNetwork(){
        this.showNetwork.setText(this.show.getNetwork().getName());
    }

    private void setShowSchedule(){
        this.showSchedule.setText(this.show.getSchedule().toString());
    }

    private void setShowSummary(){
        this.showSummary.setText(this.show.getSummaryNoFormat());
    }
}
