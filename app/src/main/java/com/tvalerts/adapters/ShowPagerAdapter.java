package com.tvalerts.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tvalerts.domains.Show;
import com.tvalerts.fragments.ShowCastFragment;
import com.tvalerts.fragments.ShowInfoFragment;

/**
 * Class that implements the FragmentPagerAdapter to represent each page as a Fragment
 * that is persistently kept in the fragment manager as long as the user can return to the page.
 */
public class ShowPagerAdapter extends FragmentPagerAdapter {
    /**
     * Private variable to hold the number of tabs available.
     */
    private int numberOfTabs;

    private Show show;

    /**
     * Constructor for the class.
     * @param fragmentManager - instance of the fragment manager to handle the fragments.
     * @param numberOfTabs - the number of tabs available.
     */
    public ShowPagerAdapter(FragmentManager fragmentManager, int numberOfTabs, Show show) {
        super(fragmentManager);
        this.numberOfTabs = numberOfTabs;
        this.show = show;
    }

    /**
     * Return the Fragment associated with a specified position.
     * @param position - the position to obtain the associated fragment.
     * @return the actual fragment associated with the given position.
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                // Show Information Tab
                return ShowInfoFragment.newInstance(show);
            case 1:
                // Cast Information Tab
                return ShowCastFragment.newInstance(show.getCast().getCast());
            default:
                return null;
        }
    }

    /**
     * Returns the number of tabs for this given page adapter.
     * @return the number of tabs.
     */
    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
