package com.hackingbuzz.facebookintegration2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;


/**
 * Created by Ajay Mehta on 5/3/2017.
 */

public class SecondActivity extends AppCompatActivity {

    TextView mEmail, mName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        FacebookSdk.sdkInitialize(getApplicationContext());

        Button bkt = (Button) findViewById(R.id.logoutbtn);
        ImageView profilePicture = (ImageView) findViewById(R.id.iv_profilepic);
         mEmail = (TextView) findViewById(R.id.tv_email);
         mName = (TextView) findViewById(R.id.tv_name);

        mName.setText(MyFragment.name);

        profilePicture.setImageBitmap(MyFragment.resized);




        bkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                startActivity(new Intent(getApplicationContext() , MainActivity.class));
            }
        });

    }
}
