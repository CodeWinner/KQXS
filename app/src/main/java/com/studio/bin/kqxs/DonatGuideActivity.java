package com.studio.bin.kqxs;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DonatGuideActivity extends AppCompatActivity {

    public Button btnYesDonate,btnNoDonate;
    private TextView txtGuideDonate;
    public int CLICK_YES = 0;
    // Analys
    private FirebaseAnalytics mFirebaseAnalytics;

    private String SKU = "kqxs_binnk_1995_v.1.0.1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donat_guide);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        btnYesDonate = findViewById(R.id.btnYesDonate);
        btnNoDonate = findViewById(R.id.btnNoDonate);
        txtGuideDonate = findViewById(R.id.txtGuideDonate);

        btnNoDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAnalysClick("UPGRATE_VIP_NO", "UPGRATE_VIP");
                Intent intent = new Intent(DonatGuideActivity.this, ChooseOptionActivity.class);
                startActivity(intent);
                DonatGuideActivity.this.finish();
                overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);
            }
        });

        btnYesDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMarket();
            }
        });
    }

    private void launchMarket() {
        firebaseAnalysClick("UPGRATE_VIP_YES", "UPGRATE_VIP");
        Uri uri = Uri.parse("market://details?id=com.studio.bin.kqxsvip");
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Unable to find market app", Toast.LENGTH_LONG).show();
        }
    }

    private void firebaseAnalysClick(String event, String type) {

        // [START image_view_event]
        Bundle bundle = new Bundle();
        bundle.putString("value", event);
        bundle.putString("type", type);
        mFirebaseAnalytics.logEvent(event, bundle);
        bundle.clear();
    }

}
