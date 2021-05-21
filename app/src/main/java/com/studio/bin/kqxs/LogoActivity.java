package com.studio.bin.kqxs;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LogoActivity extends AppCompatActivity {
    private static final int SPLASH_DISPLAY_TIME = 1500;
    private TextView textContextVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        textContextVersion = findViewById(R.id.textContextVersion);
        forceUpdate();
        new Handler().postDelayed(new Runnable() {
            public void run() {

                /* Create an intent that will start the main activity. */
                Intent mainIntent = new Intent(LogoActivity.this,
                        WellComeActivity.class);

                startActivity(mainIntent);
                /* Finish splash activity so user cant go back to it. */
                LogoActivity.this.finish();

                     /* Apply our splash exit (fade out) and main
                        entry (fade in) animation transitions. */
                overridePendingTransition(R.animator.slide_in_top,R.animator.slide_out_bottom);
            }
        }, SPLASH_DISPLAY_TIME);
    }

    public void forceUpdate(){
        PackageManager packageManager = this.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo =packageManager.getPackageInfo(getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String currentVersion = packageInfo.versionName;
        textContextVersion.setText("B.I.N Studio: " + currentVersion);
    }
}
