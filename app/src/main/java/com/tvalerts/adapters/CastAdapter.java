package com.tvalerts.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tvalerts.R;
import com.tvalerts.domains.Cast;
import com.tvalerts.domains.Image;

import java.util.List;

/**
 * Adapter class for the Cast members ListView.
 *
 * Adapters provide a binding from an app-specific data set to views
 * that are displayed within a ListView.
 */
public class CastAdapter extends BaseAdapter implements View.OnClickListener {
    /**
     * Simple tag for loggin purposes.
     */
    private static final String TAG = CastAdapter.class.getSimpleName();
    /**
     * The context of the adapter. Usually the class that holds the ListView.
     */
    private Context mContext;
    /**
     * List of a Tv Show cast.
     */
    private List<Cast> cast;
    /**
     * Instantiates a layout XML file into its corresponding View objects.
     */
    private LayoutInflater layoutInflater;

    /**
     * Public constructor for the adapter.
     * @param context The context of the adapter.
     * @param cast The list of cast members for a Tv Show.
     */
    public CastAdapter(Context context, List<Cast> cast) {
        this.mContext = context;
        this.cast = cast;
        this.layoutInflater = (LayoutInflater.from(context));
    }

    /**
     * Returns the size of the Cast members size.
     * @return the size of the adapter array.
     */
    @Override
    public int getCount() {
        return cast.size();
    }

    /**
     * Returns an item of the adapter
     * @param i the index of the element to query.
     * @return null, since this method is not implemented.
     */
    @Override
    public Object getItem(int i) {
        return null;
    }

    /**
     * Returns an item identifier for the adapter.
     * @param i he index of the element to query.
     * @return 0, since this method is not implemented.
     */
    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * Inflates the view for the adapter.
     * @param i the index of the element being built.
     * @param view the view to be built.
     * @param viewGroup special view that can contain other views
     * @return the built view with all the elements.
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.cast_list_row, null);
        buildViewElements(view, cast.get(i), i);
        return view;
    }

    /**
     * Method that handles the clicks on elements of the adapter.
     * @param view the view that was clicked.
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_cast_icon:
                Log.d(TAG, "Clicked on icon!");
                break;
             default:
                 Log.d(TAG, "Clicked on any other element in the view!");
                 this.openCastMemberInBrowser((int) view.getTag());
                 break;
        }
    }

    /**
     * Builds all the elements of the view setting the proper text or images.
     * @param view the view to be built.
     * @param cast the cast member that has to be included for that view.
     */
    private void buildViewElements (View view, Cast cast, int index) {
        // Cast Icon
        ImageView castImage = view.findViewById(R.id.iv_cast_icon);
        Image image = cast.getPerson().getImage();
        if (image != null) {
            Glide.with(mContext)
                    .load(image.getMedium())
                    .apply(RequestOptions.circleCropTransform())
                    .into(castImage);
        } else {
            Glide.with(mContext)
                    .load(mContext.getDrawable(R.drawable.ic_live_tv_black))
                    .apply(RequestOptions.circleCropTransform())
                    .into(castImage);
        }
        castImage.setTag(index);
        castImage.setOnClickListener(this);
        // Cast Actor Name
        TextView castNameTextView = view.findViewById(R.id.tv_cast_actor_name);
        castNameTextView.setText(cast.getPerson().getName());
        castNameTextView.setTag(index);
        castNameTextView.setOnClickListener(this);
        // Cast Character
        TextView castCharacterTextView = view.findViewById(R.id.tv_cast_character);
        castCharacterTextView.setText(cast.getCharacter().getName());
        castCharacterTextView.setTag(index);
        castCharacterTextView.setOnClickListener(this);
    }

    /**
     * Starts a new activity that opens the system web browser
     * to show more information about the actor.
     * @param index the index of the element that was clicked in the adapter.
     */
    private void openCastMemberInBrowser(int index) {
        Cast cast = this.cast.get(index);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(cast.getPerson().getUrl()));
        mContext.startActivity(intent);
    }
}
