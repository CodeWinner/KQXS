package com.studio.bin.kqxs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class WellComeActivity extends AppCompatActivity {
    private Button btnNext;
    public static InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_come);
        btnNext = findViewById(R.id.btnNext);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.id_qc_all));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Next page
                Intent intentWC = new Intent(WellComeActivity.this,GuideActivity.class);
                startActivity(intentWC);
                WellComeActivity.this.finish();
                overridePendingTransition(R.animator.slide_in_right,R.animator.slide_out_left);
            }
        });
    }
}
