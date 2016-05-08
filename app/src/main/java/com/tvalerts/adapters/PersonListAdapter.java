package com.tvalerts.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tvalerts.R;
import com.tvalerts.domain.Person;

import java.util.ArrayList;

/**
 * Created by anita on 15/02/16.
 */
public class PersonListAdapter extends BaseAdapter {

    private static final String DRAWABLE = "drawable://";

    private Context context;
    private LayoutInflater mLayoutInfater;
    private ArrayList<Person> mEntries = new ArrayList<Person>();

    public PersonListAdapter(Context context){
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
        RelativeLayout itemView;

        if(convertView == null){
            itemView = (RelativeLayout) mLayoutInfater.inflate(R.layout.cast_list_item, parent, false);
        } else {
            itemView = (RelativeLayout) convertView;
        }

        ImageView imageView = (ImageView) itemView.findViewById(R.id.castImage);
        TextView textView = (TextView) itemView.findViewById(R.id.castName);

        String personName = mEntries.get(position).getName();
        textView.setText(personName);

        if (mEntries.get(position).getImage() == null || mEntries.get(position).getImage().getMedium() == null){
            //If the Person does not have medium image. load a default one.
            Picasso.with(this.context)
                    .load(R.drawable.ic_person_black_24dp)
                    .resize(120, 120)
                    .centerCrop()
                    .into(imageView);
        } else {
            Picasso.with(this.context)
                    .load(mEntries.get(position).getImage().getMedium())
                    .resize(120, 120)
                    .centerCrop()
                    .into(imageView);
        }

        return itemView;
    }

    public void clear(){
        this.mEntries.clear();
    }

    public void updateEntries(ArrayList<Person> entries){
        this.mEntries = entries;
        notifyDataSetChanged();
    }

}
