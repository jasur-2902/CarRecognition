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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                SplashActivity.this.finish();

            }
        }, 3000);


    }


}
