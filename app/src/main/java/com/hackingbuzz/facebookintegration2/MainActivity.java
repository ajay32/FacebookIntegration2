package com.hackingbuzz.facebookintegration2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment fragment = fragmentManager.findFragmentById(R.id.activity_main);  // so he trying to get fragment..if there one is present on activity layout...if not that we will put our won fragment...it is just for checking purpose..


        if (fragment == null) {
            fragment = new MyFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_space, fragment)     // doing transcation with its tag..coz we just using one fragment ..n we not gonna need to find this fragment again in future..
                    .commit();
        }
    }
}