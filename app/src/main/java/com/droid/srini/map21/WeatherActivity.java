package com.droid.srini.map21;

/**
 * Created by srinivasane on 2/18/2015.
 */
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WeatherActivity extends FragmentActivity
{

    private WebView webView;
    private String OPEN_WEATHER_MAP_API_URL =
            "http://api.openweathermap.org/data/2.5/weather?mode=html&units=imperial&lat=";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        webView = (WebView) findViewById(R.id.webViewWeather);
        webView.getSettings().setJavaScriptEnabled(true);
        //Coordinates oCoordinates = new Coordinates(this);
        OPEN_WEATHER_MAP_API_URL = OPEN_WEATHER_MAP_API_URL + getIntent().getExtras().getString("latitude") + "&lon=" + getIntent().getExtras().getString("longitude");
        webView.loadUrl(OPEN_WEATHER_MAP_API_URL);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setPadding(0, 0, 0, 0);
        webView.setInitialScale(getScale());

    }
    private int getScale(){
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        Double val = new Double(width)/new Double(findViewById(R.id.webViewWeather).getWidth());
        val = val * 100d;
        return val.intValue();
    }


}