package com.studio.bin.kqxs;

import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;


public class ChooseOptionActivity extends AppCompatActivity {
    private Button btnAction;
    private Spinner spnArea;
    private Spinner spnUnit;

    private final String[] arrArea = {"Miền Bắc","Miền Trung","Miền Nam"};
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
    private ArrayAdapter<String> spinerAreaAdapterUnitCentral ;
    private ArrayAdapter<String> spinerAreaAdapterUnitNam ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_option);
        spnArea = findViewById(R.id.spnArea);
        spnUnit = findViewById(R.id.spnUnit);

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

        spnArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                areaSelect = arrArea[position];

                if("Miền Bắc".equals(areaSelect)){
                    spnUnit.setVisibility(View.GONE);

                }else {
                    spnUnit.setVisibility(View.VISIBLE);

                    if("Miền Trung".equals(areaSelect)){

                        spinerAreaAdapterUnitCentral.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                        spnUnit.setAdapter(spinerAreaAdapterUnitCentral);
                    }else{

                        spinerAreaAdapterUnitNam.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                        spnUnit.setAdapter(spinerAreaAdapterUnitNam);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if("Miền Bắc".equals(areaSelect)){

                    spnUnit.setVisibility(View.GONE);
                }else {

                    spnUnit.setVisibility(View.VISIBLE);
                }
            }
        });


        btnAction = findViewById(R.id.btnAction);
    }

}
