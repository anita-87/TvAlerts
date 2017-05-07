package com.tvalerts.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.cnleon.tvalerts.R;
import com.tvalerts.domain.Show;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anita on 6/05/17.
 */

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ShowViewHolder> implements Filterable {

    private List<Show> mShowsData;
    private List<Show> mFilteredShowsData;

    public ShowAdapter(List<Show> showList) {
        mShowsData = showList;
        mFilteredShowsData = showList;
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
        String showName = mFilteredShowsData.get(position).getName();
        showViewHolder.listItemShowTextView.setText(showName);
    }

    @Override
    public int getItemCount() {
        if (null == mFilteredShowsData) return 0;
        return mFilteredShowsData.size();
    }

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
    }

    public void setShowData(List<Show> showsData) {
        mFilteredShowsData = showsData;
        notifyDataSetChanged();
    }

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
