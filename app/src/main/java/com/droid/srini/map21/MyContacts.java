package com.droid.srini.map21;

/**
 * Created by srinivasane on 2/16/2015.
 */

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class MyContacts extends ListActivity {
        public void onCreate(Bundle icicle) {
            super.onCreate(icicle);
            /*String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                    "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                    "Linux", "OS/2" };*/
            // use your custom layout
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.contacts, R.id.label, getContacts());
            setListAdapter(adapter);
        }

        @Override
        protected void onListItemClick(ListView l, View v, int position, long id) {
            String item = (String) getListAdapter().getItem(position);
            Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
        }

        public LinkedList<String> getContacts(){
            //testing contacts
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
            LinkedList<String> contacts = new LinkedList<String>();
                while (phones.moveToNext())
                {

                    String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contacts.add( name + '(' + phoneNumber + ')' );

                }

            return contacts;
        }

    }


