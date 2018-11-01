package com.tvalerts.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    /**
     * The list of cast members of the Tv Show.
     */
    private List<Cast> cast;
    /**
     * Listener of an interaction with the fragment.
     */
    private OnFragmentInteractionListener mListener;

    /**
     * Required empty public constructor of the fragment.
     */
    public ShowCastFragment() {
    }

    /**
     * Factory method to create a new instance of this fragment with the cast array.
     *
     * @param cast - the array of casts to be shown.
     * @return A new instance of fragment ShowCastFragment.
     */
    public static ShowCastFragment newInstance(Cast[] cast) {
        ShowCastFragment fragment = new ShowCastFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CAST, Parcels.wrap(List.class, Arrays.asList(cast)));
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Method that gets called when the Fragment is created.
     *
     * @param savedInstanceState - the elements passed to the fragment.
     *                           In this case the array of cast members for the Tv Show.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cast = Parcels.unwrap(getArguments().getParcelable(ARG_CAST));
        }
    }

    /**
     * Method that gets called when the view is created.
     *
     * @param inflater           - The LayoutInflater object that can be used
     *                           to inflate any views in the fragment,
     * @param container          - If non-null, this is the parent view that the fragment's
     *                           UI should be attached to. The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState - If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_cast, container, false);
        initVisualElements(view);
        return view;
    }

    /**
     * Called when the fragment is no longer attached to its activity.
     * This is called after onDestroy().
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
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /**
     * Initializes the visual elements in the fragment.
     *
     * @param view - the view their elements are being initialized.
     */
    private void initVisualElements(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_cast_members);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        CastAdapter castAdapter = new CastAdapter(getContext());
        castAdapter.setCastList(cast);
        recyclerView.setAdapter(castAdapter);
    }

}
