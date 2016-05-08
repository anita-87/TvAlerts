package com.tvalerts.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tvalerts.fragments.CastInformationFragment;
import com.tvalerts.fragments.ShowInformationFragment;

/**
 * Created by anita on 6/02/16.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int numOfTabs){
        super(fm);
        this.mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                ShowInformationFragment tab1 = new ShowInformationFragment();
                return tab1;
            case 1:
                CastInformationFragment tab2 = new CastInformationFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.mNumOfTabs;
    }
}
