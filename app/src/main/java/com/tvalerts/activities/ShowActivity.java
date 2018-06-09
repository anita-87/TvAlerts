package com.tvalerts.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tvalerts.R;
import com.tvalerts.adapters.ShowPagerAdapter;
import com.tvalerts.domains.Show;
import com.tvalerts.network.TvMazeClient;
import com.tvalerts.utils.glide.CropTransformation;
import com.tvalerts.utils.glide.GlideUtils;
import com.tvalerts.utils.window.WindowUtils;

import java.lang.ref.WeakReference;

public class ShowActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        // TODO: replace SHOW_ID as a constant somewhere
        String id = intent.getStringExtra("SHOW_ID");

        // Setup the variables
        tabLayout = findViewById(R.id.tl_show);
        viewPager = findViewById(R.id.vp_show);

        NestedScrollView nestedScrollView = findViewById(R.id.nsv_show);
        nestedScrollView.setFillViewport(true);
        new LoadTvShow(this).execute(id);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        // No implementation needed.
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        // No implementation needed.
    }

    private void initViewElements(Show show) {
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_info));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_recent_actors));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ShowPagerAdapter adapter = new ShowPagerAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount(), show);
        viewPager.setAdapter(adapter);
        tabLayout.addOnTabSelectedListener(this);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    public static class LoadTvShow extends AsyncTask<String, Void, Show> {

        private final String TAG = LoadTvShow.class.getSimpleName();

        private WeakReference<ShowActivity> activityWeakReference;

        LoadTvShow(ShowActivity context) {
            this.activityWeakReference = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            ShowActivity activity = activityWeakReference.get();
            if (activity != null) {
                ProgressBar progressBar = activity.findViewById(R.id.pb_show);
                progressBar.setVisibility(View.VISIBLE);
            } else {
                Log.e(TAG, "There was an error trying to get the reference to the activity");
            }
        }

        @Override
        protected Show doInBackground(String... params) {
            String id = params[0];
            try {
                return TvMazeClient.getTvShowById(id);
            } catch (Exception e) {
                Log.e(TAG, "There was an error fetching the show with 'id': " + id);
                return  null;
            }
        }

        @Override
        protected void onPostExecute(Show show) {
            ShowActivity activity = activityWeakReference.get();
            if (activity != null) {
                ProgressBar progressBar = activity.findViewById(R.id.pb_show);
                Toolbar toolbar = activity.findViewById(R.id.tb_show);
                ImageView imageView = activity.findViewById(R.id.iv_show_header);
                progressBar.setVisibility(View.INVISIBLE);
                if (show != null) {
                    toolbar.setTitle(show.getName());
                    Glide.with(activity)
                            .load(show.getImage().getOriginal())
                            .apply(RequestOptions.bitmapTransform(
                                    new CropTransformation(
                                            GlideUtils.dip2px(activity,
                                                    WindowUtils.getElementMetricInDp(
                                                            activity,
                                                            imageView.getWidth())),
                                            GlideUtils.dip2px(activity,
                                                    WindowUtils.getElementMetricInDp(
                                                            activity, imageView.getHeight())),
                                    CropTransformation.CropType.TOP)))
                            .into(imageView);
                    activity.initViewElements(show);
                } else {
                    // TODO: show error message here
                }
            } else {
                Log.e(TAG, "There was an error trying to get the reference to the activity");
            }
        }
    }

}
