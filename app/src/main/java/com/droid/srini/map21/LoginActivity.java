package com.droid.srini.map21;

import android.app.Fragment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;

/**
 * Created by srinivasane on 1/24/2015.
 */
public class LoginActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_layout);

        // Capture sign in from layout
        Button signInButton = (Button)findViewById(R.id.signInButton);
        // Register the onClick listener with sign in button
        signInButton.setOnClickListener(mSignInListener);

        // Capture sign up from layout
        Button signUpButton = (Button)findViewById(R.id.signUpButton);
        // Register the onClick listener with sign in button
        signUpButton.setOnClickListener(mSignUpListener);

    }

    // Create an anonymous implementation of OnClickListener for sign in action
    private View.OnClickListener mSignInListener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
    };

    // Create an anonymous implementation of OnClickListener for sign up action
    private View.OnClickListener mSignUpListener = new View.OnClickListener() {
        public void onClick(View v) {

            TelephonyManager tMgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            String mPhoneNumber = tMgr.getLine1Number();
            //make call to http://54.69.75.83/snapwaze/api/getAcessCode
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
    };

}
