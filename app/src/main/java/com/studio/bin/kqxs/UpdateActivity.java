package com.studio.bin.kqxs;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    public Button buttonSkip;
    public Button buttonUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        buttonSkip = findViewById(R.id.buttonSkip);
        buttonUpdate = findViewById(R.id.buttonUpdate);


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMarket();
            }
        });

        buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (WellComeActivity.mInterstitialAd.isLoaded()) {
                    WellComeActivity.mInterstitialAd.show();
                } else {
                    Intent intent = new Intent(UpdateActivity.this, ChooseOptionActivity.class);
                    startActivity(intent);
                    UpdateActivity.this.finish();
                    overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);
                }
            }
        });
    }

    private void launchMarket() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Unable to find market app", Toast.LENGTH_LONG).show();
        }
    }
}


