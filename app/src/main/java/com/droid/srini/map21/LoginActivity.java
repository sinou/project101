package com.droid.srini.map21;

import android.app.Fragment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by srinivasane on 1/24/2015.
 */
public class LoginActivity extends Activity{

    private String mPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_layout);

        //make network calls async later!
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Capture sign in from layout
        Button signInButton = (Button)findViewById(R.id.signInButton);
        // Register the onClick listener with sign in button
        signInButton.setOnClickListener(mSignInListener);

        // Capture sign up from layout
        Button signUpButton = (Button)findViewById(R.id.signUpButton);
        // Register the onClick listener with sign in button
        signUpButton.setOnClickListener(mSignUpListener);


        TelephonyManager tMgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        mPhoneNumber = tMgr.getLine1Number();

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

            HttpClient client = new DefaultHttpClient();
            HttpPost httpPost= new HttpPost(getString(R.string.URL_OTP));

            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("mobileNumber", mPhoneNumber));
            //pairs.add(new BasicNameValuePair("key2", "value2"));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            HttpResponse response = null;
            try {
                response = client.execute(httpPost);
            } catch (IOException e) {
                e.printStackTrace();
            }

            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){

                // Capture sign up from layout
                Button verifyButton = (Button)findViewById(R.id.verifyButton);

                EditText otpText = (EditText)findViewById(R.id.otpText);

                verifyButton.setVisibility(View.VISIBLE);
                otpText.setVisibility(View.VISIBLE);

                // Register the onClick listener with sign in button
                verifyButton.setOnClickListener(mVerifyListener);

            }
        }
    };

    // Create an anonymous implementation of OnClickListener for verify OTP action
    private View.OnClickListener mVerifyListener = new View.OnClickListener() {
        public void onClick(View v) {

            HttpClient client = new DefaultHttpClient();
            HttpPost post= new HttpPost(getString(R.string.URL_verification));
            EditText otpText = (EditText)findViewById(R.id.otpText);

            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("mobileNumber", mPhoneNumber));
            pairs.add(new BasicNameValuePair("verificationCode", otpText.getText().toString()));
            try {
                post.setEntity(new UrlEncodedFormEntity(pairs));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            HttpResponse response = null;
            try {
                response = client.execute(post);
            } catch (IOException e) {
                e.printStackTrace();
            }

            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }

        }
    };

}
