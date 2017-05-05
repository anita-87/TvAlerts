package com.cnleon.tvalerts.activities;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.cnleon.tvalerts.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // Set up the Navigation Up
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Method that Creates the Options in the menu
     *
     * @param menu - the menu to be created
     * @return true, since the menu was created
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_search, menu);
        // TODO: investigate if there is a better method to do this:
        // updating the color of the icon to white.
        Drawable searchIcon = menu.getItem(0).getIcon();
        searchIcon.mutate();
        searchIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);
        return true;
    }
}
