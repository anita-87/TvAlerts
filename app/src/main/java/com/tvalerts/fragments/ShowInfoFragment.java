package com.tvalerts.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tvalerts.R;
import com.tvalerts.domains.Show;
import com.tvalerts.utils.strings.StringsUtils;

import org.parceler.Parcels;

import java.text.DateFormat;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowInfoFragment extends Fragment {
    private static final String ARG_TV_SHOW = "TvShow";

    private Show show;
    private TextView networkTextView;
    private TextView typeTextView;
    private TextView statusTextView;
    private TextView languageTextView;
    private TextView genresTextView;
    private TextView premieredTextView;
    private TextView scheduledTextView;
    private TextView summaryTextView;

    private OnFragmentInteractionListener mListener;

    public ShowInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ShowInfoFragment.
     */
    public static ShowInfoFragment newInstance(Show show) {
        ShowInfoFragment fragment = new ShowInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_TV_SHOW, Parcels.wrap(show));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            show = Parcels.unwrap(getArguments().getParcelable(ARG_TV_SHOW));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_info, container, false);
        initVisualElements(view);
        fillVisualElements();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void initVisualElements(View view) {
        networkTextView = view.findViewById(R.id.tv_show_details_network);
        typeTextView = view.findViewById(R.id.tv_show_details_type);
        statusTextView = view.findViewById(R.id.tv_show_details_status);
        languageTextView = view.findViewById(R.id.tv_show_details_language);
        genresTextView = view.findViewById(R.id.tv_show_details_genres);
        premieredTextView = view.findViewById(R.id.tv_show_details_premiered);
        scheduledTextView = view.findViewById(R.id.tv_show_details_schedule);
        summaryTextView = view.findViewById(R.id.tv_show_details_summary);
    }

    private void fillVisualElements() {
        networkTextView.setText(StringsUtils.returnValueIfEmpty(show.getNetwork().toString()));
        typeTextView.setText(StringsUtils.returnValueIfEmpty(show.getType()));
        statusTextView.setText(StringsUtils.returnValueIfEmpty(show.getStatus()));
        languageTextView.setText(StringsUtils.returnValueIfEmpty(show.getLanguage()));
        genresTextView.setText(StringsUtils.returnValueIfEmpty(
                TextUtils.join(", ", show.getGenres())));
        premieredTextView.setText(StringsUtils.returnValueIfEmpty(
                DateFormat.getDateInstance().format(show.getPremiered())));
        scheduledTextView.setText(StringsUtils.returnValueIfEmpty(show.getSchedule().toString()));
        summaryTextView.setText(Html.fromHtml(show.getSummary()));
    }
}
