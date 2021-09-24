package com.studio.bin.kqxs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;

import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class GuideActivity extends AppCompatActivity {
    private Button btnNext;
    public ProgressBar progressBar;

    private static final int IMMEDIATE_APP_UPDATE_REQ_CODE = 123;
    private AppUpdateManager appUpdateManager;
    private InstallStateUpdatedListener installStateUpdatedListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        btnNext = findViewById(R.id.btnNext);
        progressBar = findViewById(R.id.progressBar);

        appUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());
        checkUpdate();
        installStateUpdatedListener = state -> {
            if (state.installStatus() == InstallStatus.INSTALLED) {
                removeInstallStateUpdateListener();
            }
        };
        appUpdateManager.registerListener(installStateUpdatedListener);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                if (WellComeActivity.mInterstitialAd.isLoaded()) {
                    WellComeActivity.mInterstitialAd.show();
                } else {
                    Intent intentWC = new Intent(GuideActivity.this, ChooseOptionActivity.class);
                    startActivity(intentWC);

                    GuideActivity.this.finish();
                    overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);
                }
                }

        });

        // Set an AdListener.
        WellComeActivity.mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                //Here set a flag to know that your
            }

            @Override
            public void onAdClosed() {
                // Proceed to the next activity.
                // Next page
                Intent intentWC = new Intent(GuideActivity.this, ChooseOptionActivity.class);
                startActivity(intentWC);

                GuideActivity.this.finish();
                overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);
            }
        });
    }


    private void checkUpdate() {
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                startUpdateFlow(appUpdateInfo);
            }
        });
    }

    private void startUpdateFlow(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, this, IMMEDIATE_APP_UPDATE_REQ_CODE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    private void removeInstallStateUpdateListener() {
        if (appUpdateManager != null) {
            appUpdateManager.unregisterListener(installStateUpdatedListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMMEDIATE_APP_UPDATE_REQ_CODE) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Update canceled by user!", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(),"Update success!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Update Failed!", Toast.LENGTH_LONG).show();
                checkUpdate();
            }
        }
    }
}
