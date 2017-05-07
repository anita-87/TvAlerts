package com.tvalerts.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cnleon.tvalerts.R;
import com.tvalerts.domain.Show;

import java.util.List;

/**
 * Created by anita on 6/05/17.
 */

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ShowViewHolder> {

    private List<Show> mShowsData;

    public ShowAdapter() {
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
        String showName = mShowsData.get(position).getName();
        showViewHolder.listItemShowTextView.setText(showName);
    }

    @Override
    public int getItemCount() {
        if (null == mShowsData) return 0;
        return mShowsData.size();
    }

    public void setShowData(List<Show> showsData) {
        mShowsData = showsData;
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
