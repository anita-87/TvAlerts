package com.tvalerts.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tvalerts.R;
import com.tvalerts.domains.Show;

import org.parceler.Parcels;


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
    // TODO: Rename and change types and number of parameters
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
        TextView showSummary = view.findViewById(R.id.tv_show_details_summary);
        showSummary.setText(Html.fromHtml(show.getSummary()));
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
}
