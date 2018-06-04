package com.tvalerts.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.tvalerts.activities.ShowActivity;
import com.tvalerts.domains.Show;

import java.util.List;

/**
 * Adapter class for the Tv shows RecyclerView.
 *
 * Adapters provide a binding from an app-specific data set to views
 * that are displayed within a RecyclerView.
 */

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder> {

    /**
     * Simple TAG for logging purposes
     */
    private static final String TAG = TvShowAdapter.class.getSimpleName();
    /**
     * The context of the adapter. Usually the class that holds the RecyclerView.
     */
    private Context mContext;
    /**
     * List of Tv shows that are shown.
     */
    private List<Show> mShowsList;

    /**
     * Public constructor for the adapter.
     * @param context The context of the adapter.
     */
    public TvShowAdapter(Context context) {
        this.mContext = context;
    }

    /**
     * Method that gets called when the RecyclerView needs a new ViewHolder
     * of the given type to represent an item.
     * @param parent The ViewGroup into which the new View
     *               will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @Override
    public TvShowAdapter.TvShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tv_show_list_row, parent, false);
        return  new TvShowViewHolder(view);
    }

    /**
     * Method called by the RecyclerView to display the data at the specified position.
     * This method updates the content of the itemView to reflect the item at the given position.
     * @param holder The ViewHolder which should be updated to represent the contents of the item
     *               at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(TvShowAdapter.TvShowViewHolder holder, int position) {
        Show show = mShowsList.get(position);
        if (show.getImage() != null && show.getImage().getMedium() != null) {
            Glide.with(mContext)
                    .load(show.getImage().getMedium())
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.showIcon);
        } else {
            Glide.with(mContext)
                    .load(mContext.getDrawable(R.drawable.ic_live_tv_black))
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.showIcon);
        }
        holder.showName.setText(show.getName());
        if (show.getNetwork() != null && show.getNetwork().getName() != null) {
            holder.showStatus.setText(show.getNetwork().getName());
        } else {
            holder.showStatus.setText(R.string.string_unknown_network);
        }

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mShowsList.size();
    }

    public void setShowsList(List<Show> showsList) {
        this.mShowsList = showsList;
        notifyItemChanged(0, showsList.size());
    }

    /**
     * Inner class used to represent the information shown in the view for each show.
     */
    class TvShowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /**
         * ImageView that represents the Tv Show icon or image.
         */
        ImageView showIcon;
        /**
         * TextView that represents the Tv Show name.
         */
        TextView showName;
        /**
         * TextView that represents the Tv Show status.
         */
        TextView showStatus;

        /**
         * Public constructor of the ViewHolder.
         * @param view The individual view that represents an item in the RecyclerView.
         */
        public TvShowViewHolder(View view) {
            super(view);
            showIcon = view.findViewById(R.id.iv_show_icon);
            showName = view.findViewById(R.id.tv_show_name);
            showStatus = view.findViewById(R.id.tv_show_network);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Show showSelected = mShowsList.get(getAdapterPosition());
            Log.d(TAG, "Show selected is: " + showSelected.getName());
            Intent intent = new Intent(mContext, ShowActivity.class);
            intent.putExtra("SHOW_ID", showSelected.getId());
            mContext.startActivity(intent);
        }
    }
}
