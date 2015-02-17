package com.droid.srini.map21;

import android.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

        // Capture our button from layout
        Button button = (Button)findViewById(R.id.signInButton);
        // Register the onClick listener with the implementation above
        button.setOnClickListener(mSignInListener);

    }

    // Create an anonymous implementation of OnClickListener
    private View.OnClickListener mSignInListener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
    };

}
