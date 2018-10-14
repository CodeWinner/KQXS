package com.studio.bin.kqxs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.io.IOException;
import java.util.Random;


public class ChooseOptionActivity extends AppCompatActivity {
    private static final int LOAD_INTERNET = 6;
    private static final int LOAD_TIME = 1;

    private Button btnAction;
    private Spinner spnArea;
    private Spinner spnUnit;
    private TextView textNote;
    private ProgressBar progressBarLoad;
    private final String[] arrArea = {"Miền Bắc", "Miền Trung", "Miền Nam"};
    private String[] arrSounth = {
            "Kiên Giang",
            "Lâm Đồng",
            "Tiền Giang",
            "Cà mau",
            "Đồng Tháp",
            "TP.HCM",
            "Bạc Liêu",
            "Bến Tre",
            "Vũng tàu",
            "Cần Thơ",
            "Đồng nai",
            "Sóc Trăng",
            "An Giang",
            "Bình Thuận",
            "Tây Ninh",
            "Bình Dương",
            "Trà Vinh",
            "Vĩnh Long",
            "Bình Phước",
            "Hậu Giang",
            "Long An"
    };
    private String[] arrCentral = {
            "Kon Tum",
            "Khánh Hòa",
            "Phú Yên",
            "Thừa Thiên Huế",
            "Đắck Lắc",
            "Quảng Nam",
            "Đà Nẵng",
            "Bình Định",
            "Quảng Bình",
            "Quảng Trị",
            "Gia Lai",
            "Ninh Thuận",
            "Đắc Nông",
            "Quảng Ngãi",
    };

    private String areaSelect = "";
    private ArrayAdapter<String> spinerAreaAdapterUnitCentral;
    private ArrayAdapter<String> spinerAreaAdapterUnitNam;
    public Dialog dialogProcess;
    private String stringNote = "Kiểm tra kết nối internet . ";
    public AlertDialog.Builder alertDialogBuilder;

    public int areaNumber = 0;
    public int unitNumber = 0;
    public int unit = 0;

    private AdView mAdView;

    private InterstitialAd mInterstitialAd_video;

    private Tracker mTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_option);

        // Analys
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("ChooseOptionActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        // Add qc
        MobileAds.initialize(this,
                getString(R.string.id_app));
        // Initialize the Mobile Ads SDK
        MobileAds.initialize(this,
                getString(R.string.id_qc_test));

        // Find Banner ad
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        // Display Banner ad
        mAdView.loadAd(adRequest);

        mInterstitialAd_video = new InterstitialAd(this);
        mInterstitialAd_video.setAdUnitId(getString(R.string.id_qc_video));
        mInterstitialAd_video.loadAd(new AdRequest.Builder().build());

        spnArea = findViewById(R.id.spnArea);
        spnUnit = findViewById(R.id.spnUnit);
        btnAction = findViewById(R.id.btnAction);
        textNote = findViewById(R.id.textNote);
        progressBarLoad = findViewById(R.id.progressBarLoad);



        final ArrayAdapter<String> spinerAreaAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_expandable_list_item_1,
                arrArea);

        spinerAreaAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnArea.setAdapter(spinerAreaAdapter);

        // Miền trung
        spinerAreaAdapterUnitCentral = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_expandable_list_item_1,
                arrCentral);

        // Miền nam
        spinerAreaAdapterUnitNam = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_expandable_list_item_1,
                arrSounth);

        SharedPreferences share = getSharedPreferences("KQXS", MODE_PRIVATE);
        int area = share.getInt("AREA", 0);
        unit = share.getInt("UNIT", 0);
        spnArea.setSelection(area);


        spnArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                areaSelect = arrArea[position];
                areaNumber = position;

                if ("Miền Bắc".equals(areaSelect)) {
                    spnUnit.setVisibility(View.GONE);
                    AcessData.AREA_CODE = 1;


                } else {
                    spnUnit.setVisibility(View.VISIBLE);

                    if ("Miền Trung".equals(areaSelect)) {
                        AcessData.AREA_CODE = 2;
                        new ProcessLoadUnit(spnUnit, progressBarLoad, textNote).execute();
                        spinerAreaAdapterUnitCentral.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                        spnUnit.setAdapter(spinerAreaAdapterUnitCentral);
                        spnUnit.setSelection(unit);


                    } else {
                        AcessData.AREA_CODE = 3;
                        new ProcessLoadUnit(spnUnit, progressBarLoad, textNote).execute();
                        spinerAreaAdapterUnitNam.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                        spnUnit.setAdapter(spinerAreaAdapterUnitNam);
                        spnUnit.setSelection(unit);

                    }
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if ("Miền Bắc".equals(areaSelect)) {

                    spnUnit.setVisibility(View.GONE);
                } else {

                    spnUnit.setVisibility(View.VISIBLE);
                }
            }
        });

        spnUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                unitNumber = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater li = LayoutInflater.from(ChooseOptionActivity.this);
                View promptsView = li.inflate(R.layout.dialog_check_connect_internet,
                        null);
                alertDialogBuilder = new AlertDialog.Builder(
                        ChooseOptionActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);
                Button btnOK = promptsView.findViewById(R.id.btnOkDialog);
                TextView txtCaDao = promptsView.findViewById(R.id.txtCaDao);
                txtCaDao.setText(getCaDao());
                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ChooseOptionActivity.this, ProcessResultActivity.class);
                        startActivity(intent);
                        ChooseOptionActivity.this.finish();
                        overridePendingTransition(R.animator.slide_in_bottom, R.animator.slide_out_top);
                    }
                });

                //
                SharedPreferences share = getSharedPreferences("KQXS", MODE_PRIVATE);
                SharedPreferences.Editor editor = share.edit();
                editor.putInt("AREA", areaNumber);
                editor.putInt("UNIT", unitNumber);
                editor.commit();
                //
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("AREA")
                        .setAction(areaNumber+"")
                        .build());
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("UNIT")
                        .setAction(unitNumber+"")
                        .build());

                new CheckInternet(ChooseOptionActivity.this, textNote).execute();

            }
        });


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public String getCaDao() {
        Random random = new Random();
        int temp = random.nextInt(16);
        String cadao = "";
        switch (temp) {
            case 0:
                cadao = getString(R.string.cadao_1);
                break;
            case 1:
                cadao = getString(R.string.cadao_2);
                break;
            case 2:
                cadao = getString(R.string.cadao_3);
                break;
            case 3:
                cadao = getString(R.string.cadao_4);
                break;
            case 4:
                cadao = getString(R.string.cadao_5);
                break;
            case 6:
                cadao = getString(R.string.cadao_7);
                break;
            case 7:
                cadao = getString(R.string.cadao_8);
                break;
            case 8:
                cadao = getString(R.string.cadao_9);
                break;
            case 10:
                cadao = getString(R.string.cadao_11);
                break;
            case 11:
                cadao = getString(R.string.cadao_12);
                break;
            case 12:
                cadao = getString(R.string.cadao_13);
                break;
            case 13:
                cadao = getString(R.string.cadao_14);
                break;
            case 14:
                cadao = getString(R.string.cadao_15);
                break;
            case 15:
                cadao = getString(R.string.cadao_16);
                break;
        }
        return cadao;
    }

    public void closeDialog() {
        dialogProcess.cancel();
    }

    public void showDialog() {
        alertDialogBuilder.show();
    }

    public boolean isConnected() throws InterruptedException, IOException {
        String command = "ping -c 1 google.com";
        return (Runtime.getRuntime().exec(command).waitFor() == 0);
    }

    public class ProcessLoadUnit extends AsyncTask<Void, Void, Void> {

        public Spinner spnUnit;
        public ProgressBar progressBarLoad;
        public TextView textNoteProcess;

        public ProcessLoadUnit(Spinner spnUnit, ProgressBar progressBarLoad, TextView textNoteProcess) {
            this.spnUnit = spnUnit;
            this.progressBarLoad = progressBarLoad;
            this.textNoteProcess = textNoteProcess;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            spnUnit.setVisibility(View.GONE);
            progressBarLoad.setVisibility(View.VISIBLE);
            textNoteProcess.setText(R.string.secon_note
            );

            btnAction.setEnabled(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < LOAD_TIME; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            spnUnit.setVisibility(View.VISIBLE);
            progressBarLoad.setVisibility(View.GONE);
            btnAction.setEnabled(true);
        }
    }

    public class CheckInternet extends AsyncTask<Void, String, Boolean> {

        int count = 0;
        String value = "";
        Activity contextParent;
        TextView textNoteProcess;

        public CheckInternet(Activity contextParent, TextView textNoteProcess) {
            this.contextParent = contextParent;
            this.textNoteProcess = textNoteProcess;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarLoad.setVisibility(View.VISIBLE);
            btnAction.setEnabled(false);
            textNoteProcess.setText(stringNote);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            boolean isConnectInternet = false;
            try {
                if (isConnected()) {
                    isConnectInternet = true;
                }
            } catch (InterruptedException e) {
                e.getMessage();
            } catch (IOException e) {
                e.getMessage();
            }
            for (int i = 0; i < LOAD_INTERNET; i++) {
                try {
                    count++;
                    switch (count) {
                        case 1:
                            value = ".";
                            break;
                        case 2:
                            value = ". .";
                            break;
                        case 3:
                            value = ". . .";
                            break;
                        case 4:
                            value = ". . . .";
                            break;
                        case 5:
                            value = ". . . . .";
                            break;
                    }
                    publishProgress(value);

                    Thread.sleep(1000);
                    if (count >= 3 && isConnectInternet == true) {
                        return true;
                    }
                    if (count >= 6) {
                        return false;
                    }

                } catch (InterruptedException e) {
                    //e.printStackTrace();
                    return false;
                }
            }
            return false;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            textNoteProcess.setText(stringNote + values[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            btnAction.setEnabled(true);
            progressBarLoad.setVisibility(View.GONE);
            if (!aBoolean) {
                textNoteProcess.setText("Không thể kết nối internet . Xin hãy kiểm tra lại !");
            } else {
                textNoteProcess.setText("Kết nối internet thành công.");
                showDialog();
                if (mInterstitialAd_video.isLoaded()) {
                    mInterstitialAd_video.show();
                }
            }
        }
    }
}
