package com.tvalerts.dialogs;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tvalerts.R;
import com.tvalerts.domain.Episode;

import org.w3c.dom.Text;

/**
 * Created by anita on 1/03/16.
 */
public class EpisodeDetailsDialog extends DialogFragment {

    private static final String TAG = "EpisodeDetailsDialog";

    private Episode episode;

    public static EpisodeDetailsDialog newInstance(Episode episode){
        EpisodeDetailsDialog episodeDetailsDialog = new EpisodeDetailsDialog();
        Bundle arg = new Bundle();
        arg.putParcelable("episode", episode);
        episodeDetailsDialog.setArguments(arg);
        return episodeDetailsDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        episode = getArguments().getParcelable("episode");
        return createEpisodeDetailsDialog();
    }

    private AlertDialog createEpisodeDetailsDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.episode_info, null);

        TextView showName = (TextView) view.findViewById(R.id.show_name_in_episode);
        showName.setText(episode.getShow());

        TextView episodeName = (TextView) view.findViewById(R.id.episode_name_in_episode);
        episodeName.setText(episode.getName());

        TextView seasonName = (TextView) view.findViewById(R.id.episode_season);
        seasonName.setText(episode.getSeason());

        TextView episodeNumber = (TextView) view.findViewById(R.id.episode_number);
        episodeNumber.setText(episode.getNumber());

        TextView airDate = (TextView) view.findViewById(R.id.episode_airdate);
        airDate.setText(episode.getAirdate());

        TextView airTime = (TextView) view.findViewById(R.id.episode_airtime);
        airTime.setText(episode.getAirtime());

        TextView summary = (TextView) view.findViewById(R.id.episode_summary);
        if (episode.getSummary() == "")
            summary.setText(R.string.no_summary_available);
        else
            summary.setText(episode.getSummary());

        builder.setView(view);

        return builder.create();
    }
}
