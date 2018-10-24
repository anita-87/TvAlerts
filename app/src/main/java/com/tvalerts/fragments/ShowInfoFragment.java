package com.tvalerts.fragments;

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

import static com.tvalerts.utils.constants.Constants.ARG_TV_SHOW;


/**
 * ShowInfoFragment is simple {@link Fragment} subclass, that contains all the Tv Show information.
 *
 * Use the {@link ShowInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowInfoFragment extends Fragment {
    /**
     * The Tv Show that this fragment shows information about.
     */
    private Show show;
    /**
     * TextView that holds the network information of the Tv Show.
     */
    private TextView networkTextView;
    /**
     * TextView that holds the type of the Tv Show.
     */
    private TextView typeTextView;
    /**
     * TextView that holds the status of the Tv Show.
     */
    private TextView statusTextView;
    /**
     * TextView that holds the language of the Tv Show.
     */
    private TextView languageTextView;
    /**
     * TextView that holds the genres of the Tv Show.
     */
    private TextView genresTextView;
    /**
     * TextView that holds the date when the Tv Show premiered.
     */
    private TextView premieredTextView;
    /**
     * TextView that holds the scheduled time when the Tv Show is shown on Tv.
     */
    private TextView scheduledTextView;
    /**
     * TextView that holds a summary the Tv Show.
     */
    private TextView summaryTextView;
    /**
     * Listener for the FragmentInteractionListener.
     */
    private OnFragmentInteractionListener mListener;

    /**
     * Required empty public constructor
     */
    public ShowInfoFragment() {

    }

    /**
     * Factory method to create a new instance of
     * this fragment using the provided show parameter.
     *
     * @param show the show this fragment is going to shown information about.
     * @return A new instance of fragment ShowInfoFragment.
     */
    public static ShowInfoFragment newInstance(Show show) {
        ShowInfoFragment fragment = new ShowInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_TV_SHOW, Parcels.wrap(show));
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Method executed when the fragment is created.
     * @param savedInstanceState bundle with saved information passed to the fragment.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            show = Parcels.unwrap(getArguments().getParcelable(ARG_TV_SHOW));
        }
    }

    /**
     * Method executed when the view of the fragment is created.
     * @param inflater the inflater used to inflate the fragment view.
     * @param container the viewGroup that holds the view that is being built now.
     * @param savedInstanceState bundle with saved information passed to the fragment.
     * @return
     */
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

    /**
     * Method that gets executed when the fragment is detached.
     */
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

    /**
     * Initializes all the visual elements of the view to its proper objects.
     * @param view the view to search in them all the visual elements.
     */
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

    /**
     * Fills in all the visual elements, putting in the proper text on each of them.
     */
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
