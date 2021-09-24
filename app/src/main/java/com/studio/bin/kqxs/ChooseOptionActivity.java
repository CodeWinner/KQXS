package com.studio.bin.kqxs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;


public class ChooseOptionActivity extends AppCompatActivity {
    private static final int LOAD_INTERNET = 6;
    private static final int LOAD_TIME = 1;

    private Button btnAction;
    private Spinner spnArea;
    private Spinner spnUnit;
    private TextView textNote;
    private ProgressBar progressBarLoad;
    private Button btnLuckyNumber;
    private Button btnDonate;
    private Button btnShowKqxs;

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
    private String stringNote = "Kiểm tra kết nối internet . . .";
    public AlertDialog.Builder alertDialogBuilder;

    public int areaNumber = 0;
    public int unitNumber = 0;
    public int unit = 0;

    private Button btnNoSo;
    private AdView mAdView;

    private InterstitialAd mInterstitialAd_video;

    private FirebaseDatabase database;
    private KonfettiView konfettiView;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_option);

        textNote = findViewById(R.id.textNote);
        btnNoSo = findViewById(R.id.btnNoSo);
        konfettiView = findViewById(R.id.konfettiView);

        btnShowKqxs = findViewById(R.id.btnShowKqxs);

        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("kqxs");


        Date dt = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        final String currentDate = dateFormat.format(dt);

        SharedPreferences shareResult = getSharedPreferences("KQXS", MODE_PRIVATE);
        String dateQuay = shareResult.getString("DATE_QUAY", "");

        int countQuay = 0;

        if (currentDate.equals(dateQuay)) {
            countQuay = shareResult.getInt("COUNT_QUAY", 0);
        }

        if ((3 - countQuay) > 0) {
            btnNoSo.setText("Nổ Số (" + (3 - countQuay) + ")");
        } else {
            btnNoSo.setText("Nổ Số");
        }

        final int finalCountQuay = countQuay;
        btnNoSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalCountQuay >= 3) {
                    final SharedPreferences shareResult = getSharedPreferences("KQXS", MODE_PRIVATE);
                    final String dateNo = shareResult.getString("DATE_RS", "");
                    final String valueRs = shareResult.getString("VALUE_RS", "");

                    if (currentDate.equals(dateNo)) {
                        showDialogNoSo(dateNo, valueRs);
                        //textNote.setText("Bạn cần nâng cấp VIP để mở khóa tính năng này !");
                    } else {
                        textNote.setText("Hãy quay lại vào khoảng thời gian sau 15h20 hằng ngày !");
                    }
                } else {
                    textNote.setText("Bạn cần thực hiện quay số ít nhất 3 lần để mở khóa tính năng này !");
                }
            }
        });

        btnShowKqxs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseOptionActivity.this, ShowKqxsActivity.class);
                startActivity(intent);
                ChooseOptionActivity.this.finish();
                overridePendingTransition(R.animator.slide_in_right,R.animator.slide_out_left);
            }
        });

        //textNote.setText("Value is: " + dateNo + " - " + valueRs);
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                KetQuaDto value = new KetQuaDto();
                try {
                    value = dataSnapshot.getValue(KetQuaDto.class);
                } catch (DatabaseException exception) {

                }

                // set messae
                if (value.getMessage() != null && value.getMessage() != "") {
                   textNote.setText(value.getMessage());
                }

                final SharedPreferences shareResult = getSharedPreferences("KQXS", MODE_PRIVATE);
                final String id = shareResult.getString("NO_RS", "");

                if(!id.equals(value.getNo())) {

                    SharedPreferences share = getSharedPreferences("KQXS", MODE_PRIVATE);
                    SharedPreferences.Editor editor = share.edit();
                    editor.putString("NO_RS", value.getNo());
                    editor.putString("DATE_RS", value.getDate());
                    editor.putString("VALUE_RS", value.getValue());
                    editor.commit();
                }
                //Log.i("DB_FB", "Value is: " + value.getValue() + " - " + value.getDate());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i("DB_FB", "Failed to read value.", error.toException());
            }
        });

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Add qc
        MobileAds.initialize(this,
                getString(R.string.id_app));
        // Initialize the Mobile Ads SDK

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        final SharedPreferences shareLucky = getSharedPreferences("KQXS", MODE_PRIVATE);
        final int luckyNumber = shareLucky.getInt("LUCKY_NUMBER", 0);
        final String dateExecute = shareLucky.getString("DATE_EXE", null);

        btnLuckyNumber = findViewById(R.id.btnLuckyNumber);
        btnDonate = findViewById(R.id.btnDonate);

        btnDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseOptionActivity.this, DonatGuideActivity.class);
                startActivity(intent);
                ChooseOptionActivity.this.finish();
                overridePendingTransition(R.animator.slide_in_bottom, R.animator.slide_out_top);
            }
        });

        if (!currentDateandTime.equals(dateExecute)) {
            btnLuckyNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ChooseOptionActivity.this, LuckyNumberActivity.class);
                    startActivity(intent);
                    ChooseOptionActivity.this.finish();
                    overridePendingTransition(R.animator.slide_in_bottom, R.animator.slide_out_top);
                }
            });
        } else {
            btnLuckyNumber.setText("Con số may mắc hôm nay của bạn là : " + luckyNumber);
            btnLuckyNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(ChooseOptionActivity.this, "Quay lại vào ngày mai !", Toast.LENGTH_SHORT).show();
                }
            });
        }

        Intent intent = new Intent(ChooseOptionActivity.this, LuckyNumberService.class);
        startService(intent);

        // Find Banner ad
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        // Display Banner ad
        mAdView.loadAd(adRequest);

        mInterstitialAd_video = new InterstitialAd(this);
        mInterstitialAd_video.setAdUnitId(getString(R.string.id_qc_all));
        mInterstitialAd_video.loadAd(new AdRequest.Builder().build());

        // Set an AdListener.
        mInterstitialAd_video.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                //Here set a flag to know that your
            }

            @Override
            public void onAdClosed() {
                // Proceed to the next activity.
                Intent intent = new Intent(ChooseOptionActivity.this, ProcessResultActivity.class);
                startActivity(intent);
                ChooseOptionActivity.this.finish();
                overridePendingTransition(R.animator.slide_in_bottom, R.animator.slide_out_top);
            }
        });
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
                        if (mInterstitialAd_video.isLoaded()) {
                            mInterstitialAd_video.show();
                        }else {
                            Intent intent = new Intent(ChooseOptionActivity.this, ProcessResultActivity.class);
                            startActivity(intent);
                            ChooseOptionActivity.this.finish();
                            overridePendingTransition(R.animator.slide_in_bottom, R.animator.slide_out_top);
                        }
                    }
                });

                //
                SharedPreferences share = getSharedPreferences("KQXS", MODE_PRIVATE);
                SharedPreferences.Editor editor = share.edit();
                editor.putInt("AREA", areaNumber);
                editor.putInt("UNIT", unitNumber);
                editor.commit();

                new CheckInternet(ChooseOptionActivity.this, textNote).execute();

            }
        });
    }

    private void firebaseAnalysClick(String event, String type) {

        // [START image_view_event]
        Bundle bundle = new Bundle();
        bundle.putString("value", event);
        bundle.putString("type", type);
        mFirebaseAnalytics.logEvent(event, bundle);
        bundle.clear();
    }

    private void showDialogNoSo(final String date, final String value) {
        LayoutInflater li = LayoutInflater.from(ChooseOptionActivity.this);
        View promptsView = li.inflate(R.layout.dialog_no_so,
                null);
        alertDialogBuilder = new AlertDialog.Builder(
                this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final TextView textDateNS = promptsView.findViewById(R.id.dateNoSo);
        final TextView textValueNS = promptsView.findViewById(R.id.valueNoSo);
        textDateNS.setVisibility(View.GONE);
        textValueNS.setVisibility(View.GONE);
        final ProgressBar progressBarNS = promptsView.findViewById(R.id.progressBarNS);
        progressBarNS.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBarNS.setVisibility(View.GONE);
                textDateNS.setText("Nổ số ngày : " + date);
                textValueNS.setText(value);
                textDateNS.setVisibility(View.VISIBLE);
                textValueNS.setVisibility(View.VISIBLE);

                konfettiView.build()
                        .addColors(getApplicationContext().getResources().getColor(R.color.PB1),
                                getApplicationContext().getResources().getColor(R.color.PB2),
                                getApplicationContext().getResources().getColor(R.color.PB3))
                        .setDirection(0.0, 359.0)
                        .setSpeed(1f, 5f)
                        .setFadeOutEnabled(true)
                        .setTimeToLive(2000L)
                        .addShapes(Shape.Square.INSTANCE, Shape.Circle.INSTANCE)
                        .addSizes(new Size(12, 5f))
                        .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                        .streamFor(300, 5000L);
            }
        }, 3000);
        firebaseAnalysClick("no_so", "no_so");
        alertDialogBuilder.show();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public String getCaDao() {
        Random random = new Random();
        int temp = random.nextInt(17);
        String cadao = "~ Chúc bạn may mắn ! ~";
        switch (temp) {
            case 1:
                cadao = getString(R.string.cadao_2);
                break;
            case 2:
                cadao = getString(R.string.cadao_4);
                break;
            case 3:
                cadao = getString(R.string.cadao_7);
                break;
            case 4:
                cadao = getString(R.string.cadao_8);
                break;
            case 5:
                cadao = getString(R.string.cadao_9);
                break;
            case 6:
                cadao = getString(R.string.cadao_11);
                break;
            case 7:
                cadao = getString(R.string.cadao_12);
                break;
            case 8:
                cadao = getString(R.string.cadao_13);
                break;
            case 9:
                cadao = getString(R.string.cadao_14);
                break;
            case 10:
                cadao = getString(R.string.cadao_15);
                break;
            case 11:
                cadao = getString(R.string.cadao_16);
                break;
            case 12:
                cadao = "Mẹo : Quay ít nhất 3 lần mỗi ngày giúp tính toán chính xác hơn !";
                break;
            case 13:
                cadao = "Mẹo : Soi cầu là tính năng tuyệt vời dành cho phiên bản VIP mà chúng tôi cung cấp . Những con số có tính chính xác cao mà chúng tôi gợi ý cho tất cả mọi người ở phiên bản VIP!";
                break;
            case 14:
                cadao = "Mẹo : Tất cả kết quả con số ở tính năng NỔ SỐ ở tất cả các máy đều giống nhau !";
                break;
            case 15:
                cadao = "Mẹo : Nâng cấp VIP giúp loại bỏ quảng cáo và tăng tốc độ xử lý nhanh hơn gấp 4 lần !";
                break;
            case 16:
                cadao = "Mẹo : Đón chờ tính năng nổ số vào khung giờ 16h20 - 17h hằng ngày!";
                break;
        }
        if("".equals(cadao)){
            cadao = "~ Good luck for you ~";
        }
        return cadao;
    }

    public void closeDialog() {
        dialogProcess.cancel();
    }

    public void showDialog() {
        if (alertDialogBuilder != null) {
            alertDialogBuilder.show();
        }
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
                try {
                    showDialog();
                }catch (Exception e){
                }
            }
        }
    }
}
