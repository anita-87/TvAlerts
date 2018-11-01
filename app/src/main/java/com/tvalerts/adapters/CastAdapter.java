package com.tvalerts.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tvalerts.R;
import com.tvalerts.activities.ImageFullScreenActivity;
import com.tvalerts.domains.Cast;

import org.parceler.Parcels;

import java.util.List;

import static com.tvalerts.utils.constants.Constants.ARG_CAST;

/**
 * Adapter class for the Cast members ListView.
 * <p>
 * Adapters provide a binding from an app-specific data set to views
 * that are displayed within a ListView.
 */
public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {
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
    private List<Cast> mCast;

    /**
     * Public constructor for the adapter.
     *
     * @param context The context of the adapter.
     */
    public CastAdapter(Context context) {
        this.mContext = context;
    }

    /**
     * Method that gets called when the RecyclerView needs a new ViewHolder.
     *
     * @param parent   - The ViewGroup into which the new View will be added
     *                 after it is bound to an adapter position.
     * @param viewType - The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @Override
    public CastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cast_list_row, parent, false);
        return new CastViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder   - The ViewHolder which should be updated to represent
     *                 the contents of the item at the given position in the data set.
     * @param position - The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(CastViewHolder holder, int position) {
        Cast cast = mCast.get(position);
        if (cast.getPerson().getImage() != null
                && cast.getPerson().getImage().getMedium() != null) {
            Glide.with(mContext)
                    .load(cast.getPerson().getImage().getOriginal())
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.castIcon);
        }
        holder.castActor.setText(cast.getPerson().getName());
        holder.castCharacter.setText(cast.getCharacter().getName());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mCast.size();
    }

    public void setCastList(List<Cast> castList) {
        this.mCast = castList;
        notifyItemChanged(0, castList.size());
    }


    /**
     * Class that describes an item view and metadata
     * about its place in the CastAdapter RecyclerView.
     * <p>
     * It also implements the View.OnClickListener interface to handle clicks in the items.
     */
    class CastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /**
         * The ImageView that holds the cast actor picture.
         */
        ImageView castIcon;
        /**
         * The TextView that holds the name of the actor.
         */
        TextView castActor;
        /**
         * The TextView that holds the name of the character the actor plays.
         */
        TextView castCharacter;

        /**
         * Constructor for the CastViewHolder class.
         *
         * @param view - the view element that is being built.
         */
        CastViewHolder(View view) {
            super(view);
            castIcon = view.findViewById(R.id.iv_cast_icon);
            castIcon.setOnClickListener(this);
            castActor = view.findViewById(R.id.tv_cast_actor_name);
            castActor.setOnClickListener(this);
            castCharacter = view.findViewById(R.id.tv_cast_character);
            castCharacter.setOnClickListener(this);
        }

        /**
         * Method that handles the clicks performs on the ViewHolder.
         *
         * @param view - the single view that was clicked in the RecyclerView.
         */
        @Override
        public void onClick(View view) {
            Cast cast = mCast.get(getAdapterPosition());
            if (view.getId() == castIcon.getId()) {
                Log.d(TAG, "Icon clicked!");
                this.openCastImageFullScreen(cast);
            } else {
                Log.d(TAG, "Any other part clicked");
                this.openCastMemberInBrowser(cast);
            }
        }

        /**
         * Opens a new activity that shows the image of the acter in full size.
         *
         * @param cast - the cast object to show information.
         */
        private void openCastImageFullScreen(Cast cast) {
            Intent intent = new Intent(mContext, ImageFullScreenActivity.class);
            intent.putExtra(ARG_CAST, Parcels.wrap(cast));
            mContext.startActivity(intent);
        }

        /**
         * Starts a new activity that opens the system web browser
         * to show more information about the actor.
         *
         * @param cast - the cast object to show information.
         */
        private void openCastMemberInBrowser(Cast cast) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(cast.getPerson().getUrl()));
            mContext.startActivity(intent);
        }
    }
}
