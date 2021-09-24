package com.studio.bin.kqxs;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static com.studio.bin.kqxs.AcessData.LUCKY_NUMBER;

public class ShowResultActivity extends AppCompatActivity implements View.OnClickListener {
        private ProgressBar progressBarShowRessult;
    private TextView txt0, txt1, txt2, txt3, txt4, txt5, txt6, txt7, txt8,txtNoteX;
    private LinearLayout layout8;

    private Button btnYes;
    private Button btnNo;

    private ImageButton ibtnRate;

    private AdView mAdView;


    public AlertDialog.Builder alertDialogBuilder;

    public boolean isRate;

    private InterstitialAd mInterstitialAd;

    // Analys
    private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);

        // Add qc
        MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.id_qc_video));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        btnYes = findViewById(R.id.btnYes);
        btnYes.setOnClickListener(this);

        btnNo = findViewById(R.id.btnNo);
        btnNo.setOnClickListener(this);

        ibtnRate = findViewById(R.id.ibtnRate);
        ibtnRate.setOnClickListener(this);

        txt0 = findViewById(R.id.txt0);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        txt4 = findViewById(R.id.txt4);
        txt5 = findViewById(R.id.txt5);
        txt6 = findViewById(R.id.txt6);
        txt7 = findViewById(R.id.txt7);
        txt8 = findViewById(R.id.txt8);
        txtNoteX = findViewById(R.id.txtNoteX);

        progressBarShowRessult = findViewById(R.id.progressBarShowRessult);
        layout8 = findViewById(R.id.layout8);
        if (AcessData.AREA_CODE == 1) {
            layout8.setVisibility(View.GONE);
        }
        if(AcessData.PROCESS_SHOW == 1){
            new ShowResult().execute();
        }else {
            txtNoteX.setText("Click vào đây để quay .");
            btnNo.setVisibility(View.GONE);
        }

        Date dt = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dateFormat.format(dt);
        SharedPreferences shareResult = getSharedPreferences("KQXS", MODE_PRIVATE);
        String dateQuay = shareResult.getString("DATE_QUAY", "");
        int countQuay = 0;

        if (currentDate.equals(dateQuay)) {
            countQuay = shareResult.getInt("COUNT_QUAY", 0);
        }

        countQuay++;
        SharedPreferences share = getSharedPreferences("KQXS", MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("DATE_QUAY", currentDate);
        editor.putInt("COUNT_QUAY", countQuay);
        editor.commit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        LayoutInflater li = LayoutInflater.from(ShowResultActivity.this);
        View promptsView = li.inflate(R.layout.dialog_rate,
                null);
        alertDialogBuilder = new AlertDialog.Builder(
                this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        Button btnOK = promptsView.findViewById(R.id.btnOkRating);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMarket();
                SharedPreferences share = getSharedPreferences("RATE", MODE_PRIVATE);
                SharedPreferences.Editor editor = share.edit();
                editor.putBoolean("isRATE", true);
                editor.commit();
            }
        });

        SharedPreferences share = getSharedPreferences("RATE", MODE_PRIVATE);
        isRate = share.getBoolean("isRATE", false);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnYes:
                Intent intent = new Intent(ShowResultActivity.this, ChooseOptionActivity.class);
                startActivity(intent);
                ShowResultActivity.this.finish();
                overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);
                break;
            case R.id.btnNo:
                ShowResultActivity.this.finish();
                break;
            case R.id.ibtnRate:
                launchMarket();
                break;
        }
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

    public void onBackPressed() {
        Intent intent = new Intent(ShowResultActivity.this, ChooseOptionActivity.class);
        startActivity(intent);
        ShowResultActivity.this.finish();
        overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);
    }
    public void showDialog() {
        alertDialogBuilder.show();
    }

    private void firebaseAnalysClick(String event, String type) {

        // [START image_view_event]
        Bundle bundle = new Bundle();
        bundle.putString("value", event);
        bundle.putString("type", type);
        mFirebaseAnalytics.logEvent(event, bundle);
        bundle.clear();
    }

    public class ShowResult extends AsyncTask<Void, String, Void> {
        String result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarShowRessult.setVisibility(View.VISIBLE);
            btnYes.setEnabled(false);
            btnNo.setEnabled(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Random random = new Random();
            if (AcessData.AREA_CODE == 1) {
                for (int i = 1; i <= 8; i++) {
                    switch (i) {
                        case 1: // Giai 7
                            int award71 = random.nextInt(100);
                            result += String.format("%02d", award71);
                            result += " - ";

                            int award72 = random.nextInt(100);
                            result += String.format("%02d", award72);
                            result += " - ";

                            int award73 = random.nextInt(100);
                            result += String.format("%02d", award73);
                            result += " - ";

                            int award74 = random.nextInt(100);
                            result += String.format("%02d", award74);
                            firebaseAnalysClick("lucky_number_"
                                    + result.substring(result.length()-2, result.length()), "lucky_number");
                            break;
                        case 2: //giai 6
                            int award61 = random.nextInt(1000);
                            result += String.format("%03d", award61);
                            result += " - ";

                            int award62 = random.nextInt(1000);
                            result += String.format("%03d", award62);
                            result += " - ";

                            int award63 = random.nextInt(1000);
                            result += String.format("%03d", award63);
                            firebaseAnalysClick("lucky_number_"
                                    + result.substring(result.length()-2, result.length()), "lucky_number");
                            break;
                        case 3: // Giai 5
                            int award51 = random.nextInt(10000);
                            result += String.format("%04d", award51);
                            result += " - ";

                            int award52 = random.nextInt(10000);
                            result += String.format("%04d", award52);
                            result += " - ";

                            int award53 = random.nextInt(10000);
                            result += String.format("%04d", award53);
                            result += " - ";

                            int award54 = random.nextInt(10000);
                            result += String.format("%04d", award54);
                            result += " - ";

                            int award55 = random.nextInt(10000);
                            result += String.format("%04d", award55);
                            result += " - ";

                            int award56 = random.nextInt(10000);
                            result += String.format("%04d", award56);
                            firebaseAnalysClick("lucky_number_"
                                    + result.substring(result.length()-2, result.length()), "lucky_number");
                            break;
                        case 4: // Giai 4
                            int award41 = random.nextInt(10000);
                            result += String.format("%04d", award41);
                            result += " - ";

                            int award42 = random.nextInt(10000);
                            result += String.format("%04d", award42);
                            result += " - ";

                            int award43 = random.nextInt(10000);
                            result += String.format("%04d", award43);
                            result += " - ";

                            int award44 = random.nextInt(10000);
                            result += String.format("%04d", award44);
                            firebaseAnalysClick("lucky_number_"
                                    + result.substring(result.length()-2, result.length()), "lucky_number");
                            break;
                        case 5: // Giai 3
                            int award31 = random.nextInt(100000);
                            result += String.format("%05d", award31);
                            result += " - ";

                            int award32 = random.nextInt(100000);
                            result += String.format("%05d", award32);
                            result += " - ";

                            int award33 = random.nextInt(100000);
                            result += String.format("%05d", award33);
                            result += " - ";

                            int award34 = random.nextInt(100000);
                            result += String.format("%05d", award34);
                            result += " - ";

                            int award35 = random.nextInt(100000);
                            result += String.format("%05d", award35);
                            result += " - ";

                            int award36 = random.nextInt(100000);
                            result += String.format("%05d", award36);
                            firebaseAnalysClick("lucky_number_"
                                    + result.substring(result.length()-2, result.length()), "lucky_number");
                            break;
                        case 6: // Giai 1
                            int award21 = random.nextInt(100000);
                            result += String.format("%05d", award21);
                            result += " - ";

                            int award22 = random.nextInt(100000);
                            result += String.format("%05d", award22);
                            firebaseAnalysClick("lucky_number_"
                                    + result.substring(result.length()-2, result.length()), "lucky_number");
                            break;
                        case 7: // Giai 1
                            int award1 = random.nextInt(100000);
                            result += String.format("%05d", award1);
                            break;
                        case 8: // Giai dac biet
                            int awarddb = random.nextInt(100000);
                            result += String.format("%05d", awarddb);
                            firebaseAnalysClick("lucky_number_"
                                    + result.substring(result.length()-2, result.length()), "lucky_number");
                            break;

                    }
                    try {
                        if (i == 8) {
                            Thread.sleep(2500);
                        } else {
                            Thread.sleep(1000);
                        }
                        Thread.sleep(1500);
                        publishProgress(i + "", result);
                        result = "";
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            } else {
                for (int i = 1; i <= 9; i++) {
                    switch (i) {
                        case 1: // Giai 8
                            int award81 = random.nextInt(100);
                            result += String.format("%02d", award81);
                            break;
                        case 2: //giai 7
                            int award71 = random.nextInt(1000);
                            result += String.format("%03d", award71);
                            break;
                        case 3: // Giai 6
                            int award61 = random.nextInt(10000);
                            result += String.format("%04d", award61);
                            result += " - ";

                            int award62 = random.nextInt(10000);
                            result += String.format("%04d", award62);
                            result += " - ";

                            int award63 = random.nextInt(10000);
                            result += String.format("%04d", award63);

                            break;
                        case 4: // Giai 5
                            int award51 = random.nextInt(10000);
                            result += String.format("%04d", award51);
                            break;
                        case 5: // Giai 4
                            int award41 = random.nextInt(100000);
                            result += String.format("%05d", award41);
                            result += " - ";

                            int award42 = random.nextInt(100000);
                            result += String.format("%05d", award42);
                            result += " - ";

                            int award43 = random.nextInt(100000);
                            result += String.format("%05d", award43);
                            result += " - ";

                            int award44 = random.nextInt(100000);
                            result += String.format("%05d", award44);
                            result += " - ";

                            int award45 = random.nextInt(100000);
                            result += String.format("%05d", award45);
                            result += " - ";

                            int award46 = random.nextInt(100000);
                            result += String.format("%05d", award46);
                            result += " - ";

                            int award47 = random.nextInt(100000);
                            result += String.format("%05d", award47);
                            break;
                        case 6: // Giai 3
                            int award31 = random.nextInt(100000);
                            result += String.format("%05d", award31);
                            result += " - ";

                            int award32 = random.nextInt(100000);
                            result += String.format("%05d", award32);
                            break;
                        case 7: // Giai 2
                            int award2 = random.nextInt(100000);
                            result += String.format("%05d", award2);
                            break;
                        case 8: // Giai 1
                            int award1 = random.nextInt(100000);
                            result += String.format("%05d", award1);
                            break;
                        case 9: // Giai dac biet
                            int awarddb = random.nextInt(1000000);
                            result += String.format("%05d", awarddb);
                            firebaseAnalysClick("lucky_number_" + result.substring(result.length()-2, result.length()), "lucky_number");
                            break;

                    }
                    try {
                        if (i == 9) {
                            Thread.sleep(2500);
                        } else {
                            Thread.sleep(1000);
                        }
                        Thread.sleep(1500);
                        publishProgress(i + "", result);
                        result = "";
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            int awardCode = Integer.parseInt(values[0]);
            String awardResult = values[1];
            if (AcessData.AREA_CODE == 1) {
                switch (awardCode) {
                    case 1:
                        txt7.setText(awardResult);
                        break;
                    case 2:
                        txt6.setText(awardResult);
                        break;
                    case 3:
                        txt5.setText(awardResult);
                        break;
                    case 4:
                        txt4.setText(awardResult);
                        break;
                    case 5:
                        txt3.setText(awardResult);
                        break;
                    case 6:
                        txt2.setText(awardResult);
                        break;
                    case 7:
                        txt1.setText(awardResult);
                        break;
                    case 8:
                        txt0.setText(awardResult);
                        break;
                }
            } else {
                switch (awardCode) {
                    case 1:
                        txt8.setText(awardResult);
                        break;
                    case 2:
                        txt7.setText(awardResult);
                        break;
                    case 3:
                        txt6.setText(awardResult);
                        break;
                    case 4:
                        txt5.setText(awardResult);
                        break;
                    case 5:
                        txt4.setText(awardResult);
                        break;
                    case 6:
                        txt3.setText(awardResult);
                        break;
                    case 7:
                        txt2.setText(awardResult);
                        break;
                    case 8:
                        txt1.setText(awardResult);
                        break;
                    case 9:
                        txt0.setText(awardResult);
                        break;
                }
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBarShowRessult.setVisibility(View.GONE);
            btnYes.setEnabled(true);
            btnNo.setEnabled(true);

            if(isRate == false){
                try {
                    showDialog();
                }catch (Exception e){
                }
            }
            if ( mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }
    }
}
