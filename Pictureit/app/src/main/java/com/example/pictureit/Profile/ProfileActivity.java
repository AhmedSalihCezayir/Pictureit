package com.example.pictureit.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.pictureit.R;


public class ProfileActivity extends AppCompatActivity {

    //Constants
    private static final String TAG = "ProfileActivity";
    private static final int ACTIVITY_NUMBER = 2;

    //Variables
    private Context mContext;

    //Widgets
    private ProgressBar mProgressBar;
    private ImageView profileImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(TAG, "onCreate: started.");
        mContext = ProfileActivity.this;

        init();
    }

    /**
     * This method manages frames inside of the Profile Activity
     */
    private void init() {
        Log.d(TAG, "init: inflating " + getString(R.string.profile_fragment));

        ProfileFragment fragment = new ProfileFragment();
        FragmentTransaction transaction = ProfileActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(getString(R.string.profile_fragment));
        transaction.commit();
    }
}
