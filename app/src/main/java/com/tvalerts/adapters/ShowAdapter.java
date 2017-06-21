package com.tvalerts.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.FilterQueryProvider;
import android.widget.Filterable;
import android.widget.TextView;

import com.cnleon.tvalerts.R;
import com.tvalerts.activities.SearchActivity;

/**
 * Created by anita on 6/05/17.
 */

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ShowViewHolder> implements Filterable, CursorFilter.CursorFilterClient {

    private final Context mContext;
    private Cursor mOriginalCursor;
    private Cursor mCursor;
    private CursorFilter mCursorFilter;
    private FilterQueryProvider mFilterQueryProvider;

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

    @Override
    public Filter getFilter() {
        if (mCursorFilter == null) {
            mCursorFilter = new CursorFilter(this);
        }
        return mCursorFilter;
    }

    @Override
    public CharSequence convertToString(Cursor cursor) {
        return cursor == null ? "" : cursor.toString();
    }

    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
        if (mFilterQueryProvider != null) {
            return mFilterQueryProvider.runQuery(constraint);
        }

        return mCursor;
    }

    @Override
    public Cursor getCursor() {
        return mCursor;
    }

    @Override
    public void changeCursor(Cursor cursor) {
        swapCursor(cursor);
    }

    public void setmFilterQueryProvider(FilterQueryProvider mFilterQueryProvider) {
        this.mFilterQueryProvider = mFilterQueryProvider;
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

class CursorFilter extends Filter {

    CursorFilterClient mClient;

    CursorFilter(CursorFilterClient client) {
        mClient = client;
    }

    @Override
    public CharSequence convertResultToString(Object resultValue) {
        return mClient.convertToString((Cursor) resultValue);
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        Cursor cursor = mClient.runQueryOnBackgroundThread(constraint);

        FilterResults results = new FilterResults();
        if (cursor != null) {
            results.count = cursor.getCount();
            results.values = cursor;
        } else {
            results.count = 0;
            results.values = null;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        Cursor oldCursor = mClient.getCursor();

        if (results.values != null && results.values != oldCursor) {
            mClient.changeCursor((Cursor) results.values);
        }
    }

    interface CursorFilterClient {
        CharSequence convertToString(Cursor cursor);

        Cursor runQueryOnBackgroundThread(CharSequence constraint);

        Cursor getCursor();

        void changeCursor(Cursor cursor);
    }
}

