package com.tvalerts.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tvalerts.R;
import com.tvalerts.adapters.CastAdapter;
import com.tvalerts.domains.Cast;

import org.parceler.Parcels;

import java.util.Arrays;
import java.util.List;

import static com.tvalerts.utils.constants.Constants.ARG_CAST;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowCastFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowCastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowCastFragment extends Fragment {
    private List<Cast> cast;
    private ListView castListView;

    private OnFragmentInteractionListener mListener;

    public ShowCastFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ShowCastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowCastFragment newInstance(Cast[] cast) {
        ShowCastFragment fragment = new ShowCastFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CAST, Parcels.wrap(List.class, Arrays.asList(cast)));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cast = Parcels.unwrap(getArguments().getParcelable(ARG_CAST));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_cast, container, false);
        initVisualElements(view);
        fillVisualElements(view);
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
        this.castListView = view.findViewById(R.id.lv_cast_members);
        CastAdapter castAdapter = new CastAdapter(getContext(), cast);
        castListView.setAdapter(castAdapter);
    }

    private void fillVisualElements (View view) {

    }
}
