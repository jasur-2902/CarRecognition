package uz.shukurov.carrecognition;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageView;

import uz.shukurov.carrecognition.WalkThrough.WalkThrough;

public class SplashActivity extends AppCompatActivity {

    ImageView mImageAnimation;
    WebView webView;

    public boolean isFirstStart;
    Context mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        webView = findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/index.html");

        checkIsFirstTime();


    }

    private void checkIsFirstTime() {
        //  Intro App Initialize SharedPreferences
        SharedPreferences getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());

        //  Create a new boolean and preference and set it to true
        isFirstStart = getSharedPreferences.getBoolean("firstStart", true);

        //  Check either activity or app is open very first time or not and do action
        if (isFirstStart) {

            //  Launch application introduction screen
            Intent i = new Intent(SplashActivity.this, WalkThrough.class);
            startActivity(i);
            SharedPreferences.Editor e = getSharedPreferences.edit();
            e.putBoolean("firstStart", false);
            e.apply();
            finish();
        }
        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    SplashActivity.this.finish();

                }
            }, 3000);
        }
    }


}
