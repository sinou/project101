package com.droid.srini.map21;

import android.app.Fragment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
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
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by srinivasane on 1/24/2015.
 */
public class LoginActivity extends Activity{

    private String mPhoneNumber;
    private String quotes = "";

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

            JSONObject object = new JSONObject();

            try {
                object.put("mobileNumber", mPhoneNumber);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //List<NameValuePair> pairs = new ArrayList<NameValuePair>();

            //pairs.add(new BasicNameValuePair(quotes+"mobileNumber"+quotes, quotes+mPhoneNumber+quotes));
            //pairs.add(new BasicNameValuePair("key2", "value2"));
            try {
                //httpPost.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
                StringEntity se = new StringEntity(object.toString());
                se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                httpPost.setEntity(se);
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

            JSONObject object = new JSONObject();

            try {
                object.put("mobileNumber", mPhoneNumber);
                object.put("verificationCode", otpText.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                //post.setEntity(new UrlEncodedFormEntity(pairs));
                StringEntity se = new StringEntity(object.toString());
                se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                post.setEntity(se);
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
                HttpClient client1 = new DefaultHttpClient();
                HttpPost httpPost1 = new HttpPost(getString(R.string.URL_update_contacts));
                JSONObject contactsObject = getContactsJSON();
                try {
                    StringEntity se = new StringEntity(contactsObject.toString());
                    se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    httpPost1.setEntity(se);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                response = null;
                try {
                    response = client.execute(post);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
            }

        }
    };

    private JSONObject getContactsJSON(){

        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);

        ArrayList<JSONObject> contactList = new ArrayList<JSONObject>();
        JSONObject contacts, contact = new JSONObject();
        try {
            while (phones.moveToNext()) {

                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contact.put("phoneNumber", phoneNumber);
                contact.put("name", name);
                contactList.add(contact);
                contact = new JSONObject();
            }
            contacts = new JSONObject();
            contacts.put("mobileNumber", mPhoneNumber);
            contacts.put("contacts", contactList);
        }catch(JSONException e){
               //TODO: Handle exception
        }

        return null;
    }

}
