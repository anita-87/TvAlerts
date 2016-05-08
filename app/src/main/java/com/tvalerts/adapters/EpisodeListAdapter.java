package com.tvalerts.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tvalerts.R;
import com.tvalerts.domain.Episode;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by anita on 23/02/16.
 */
public class EpisodeListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mLayoutInfater;
    private ArrayList<Episode> mEntries = new ArrayList<Episode>();

    public EpisodeListAdapter(Context context){
        this.context = context;
        this.mLayoutInfater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mEntries.size();
    }

    @Override
    public Object getItem(int position) {
        return mEntries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout itemView;

        if(convertView == null){
            itemView = (LinearLayout) mLayoutInfater.inflate(R.layout.episode_list_item, parent, false);
        } else {
            itemView = (LinearLayout) convertView;
        }

        TextView showNameTextView = (TextView) itemView.findViewById(R.id.episode_show);
        TextView episodeNameTextView = (TextView) itemView.findViewById(R.id.episode_name);

        showNameTextView.setText(mEntries.get(position).getShow());
        episodeNameTextView.setText(mEntries.get(position).getName());

        return itemView;
    }



    public void clear(){
        this.mEntries.clear();
    }

    public void updateEntries(ArrayList<Episode> entries){
        this.mEntries = entries;
        notifyDataSetChanged();
    }
}
