package com.studio.bin.kqxs;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LogoActivity extends AppCompatActivity {
    private static final int SPLASH_DISPLAY_TIME = 1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
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
}
