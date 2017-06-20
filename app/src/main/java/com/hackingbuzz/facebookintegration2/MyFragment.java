package com.hackingbuzz.facebookintegration2;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoginStatusCallback;
import com.facebook.Profile;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;



/**
 * Created by Avi Mehta on 5/3/2017.
 */

public class MyFragment extends Fragment {

    public static Bitmap resized;

    public static String name;

    CallbackManager callbackManager;

    LoginButton loginButton;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  FacebookSdk.setApplicationId("");
        FacebookSdk.sdkInitialize(getActivity());
        // Other app specific specialization
    }



    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myfragment, container, false);


        // getiing hash key baby
Button btnHashkey = (Button) view.findViewById(R.id.btn_hashkey);
        btnHashkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageInfo info;
                try {
                    info = getActivity().getPackageManager().getPackageInfo("com.hackingbuzz.facebookintegration", PackageManager.GET_SIGNATURES);
                    for (Signature signature : info.signatures) {
                        MessageDigest md;
                        md = MessageDigest.getInstance("SHA");
                        md.update(signature.toByteArray());
                        String something = new String(Base64.encode(md.digest(), 0));
                        //String something = new String(Base64.encodeBytes(md.digest()));
                        Log.e("hash key", something);
                    }
                } catch (PackageManager.NameNotFoundException e1) {
                    Log.e("name not found", e1.toString());
                } catch (NoSuchAlgorithmException e) {
                    Log.e("no such an algorithm", e.toString());
                } catch (Exception e) {
                    Log.e("exception", e.toString());
                }

            }
        });





        loginButton = (LoginButton) view.findViewById(R.id.login_button);
      //  loginButton.setReadPermissions("email");
        // If using in a fragment
        loginButton.setFragment(this);
        // Other app specific specialization

        callbackManager = CallbackManager.Factory.create();

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code

              startActivity(new Intent(getActivity(), SecondActivity.class));

                Profile profile = Profile.getCurrentProfile();

                if(profile !=null) {



                     name =("Hello" +" "+ profile.getFirstName() + " "+ profile.getLastName());

                    new LoadProfileImage().execute(profile.getProfilePictureUri(200, 200).toString());






                }


            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code

                Toast.makeText(getActivity(), "Error Occured! ", Toast.LENGTH_SHORT).show();
            }
        });



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile"));


            }

        });

        return view;

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * Background Async task to load user profile picture from url
     * */
    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadProfileImage() {

        }

        protected Bitmap doInBackground(String... uri) {
            String url = uri[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {

            if (result != null) {


                 resized = Bitmap.createScaledBitmap(result,200,200, true);
            //    bmImage.setImageBitmap(ImageHelper.getRoundedCornerBitmap(getContext(),resized,250,200,200, false, false, false, false));

            }
        }
    }


}