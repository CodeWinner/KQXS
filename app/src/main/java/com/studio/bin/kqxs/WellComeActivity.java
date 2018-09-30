package com.studio.bin.kqxs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WellComeActivity extends AppCompatActivity {
    private Button btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_come);
        btnNext = findViewById(R.id.btnNext);
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
