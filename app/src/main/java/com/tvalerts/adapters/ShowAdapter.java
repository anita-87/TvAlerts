package com.tvalerts.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cnleon.tvalerts.R;
import com.tvalerts.activities.SearchActivity;

/**
 * Created by anita on 6/05/17.
 */

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ShowViewHolder> {

    private final Context mContext;
    private Cursor mCursor;

    public ShowAdapter(@NonNull Context context) {
        mContext = context;
    }

    @Override
    public ShowViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.show_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentInmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentInmediately);
        ShowViewHolder viewHolder = new ShowViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ShowViewHolder showViewHolder, int position) {
        mCursor.moveToPosition(position);
        String showName = mCursor.getString(SearchActivity.INDEX_SHOW_NAME);
        showViewHolder.listItemShowTextView.setText(showName);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    /*
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilteredShowsData = mShowsData;
                } else {
                    List<Show> filteredList = new ArrayList<>();
                    for (Show show : mShowsData) {
                        if (show.getName().toLowerCase().contains(charString)) {
                            filteredList.add(show);
                        }
                    }
                    mFilteredShowsData = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredShowsData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mFilteredShowsData = (List<Show>) results.values;
                notifyDataSetChanged();
            }
        };
    }*/

    class ShowViewHolder extends RecyclerView.ViewHolder {

        TextView listItemShowTextView;

        public ShowViewHolder(View itemView) {
            super(itemView);
            listItemShowTextView = (TextView) itemView.findViewById(R.id.tv_item_show);
        }

        void bind(int listIndex) {
            listItemShowTextView.setText(String.valueOf(listIndex));
        }
    }
}
