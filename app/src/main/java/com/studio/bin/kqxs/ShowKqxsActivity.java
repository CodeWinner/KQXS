package com.studio.bin.kqxs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.studio.bin.kqxs.adapter.KqxsInforAdapter;
import com.studio.bin.kqxs.entity.KqxsEntity;
import com.studio.bin.kqxs.network.NetworkProvider;
import com.studio.bin.kqxs.router.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowKqxsActivity extends AppCompatActivity {

    private RecyclerView recycleViewKqxs;
    private KqxsInforAdapter adapter;
    private ProgressBar progressBarShowKqxs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_kqxs);

        progressBarShowKqxs = findViewById(R.id.progressBarShowKqxs);
        recycleViewKqxs = findViewById(R.id.recycleViewKqxs);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        APIService apiService = NetworkProvider.getInstance().create(APIService.class);
        apiService.getKQXS().enqueue(new Callback<KqxsEntity>() {
            @Override
            public void onResponse(Call<KqxsEntity> call, Response<KqxsEntity> response) {
                adapter = new KqxsInforAdapter(response.body(), getApplicationContext());

                recycleViewKqxs.setVisibility(View.VISIBLE);
                progressBarShowKqxs.setVisibility(View.GONE);

                recycleViewKqxs.setAdapter(adapter);
                recycleViewKqxs.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onFailure(Call<KqxsEntity> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Đã có lỗi xảy ra! Kiểm tra kết nối mạng của bạn", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ShowKqxsActivity.this, ChooseOptionActivity.class);
        startActivity(intent);
        ShowKqxsActivity.this.finish();
        overridePendingTransition(R.animator.slide_in_left,R.animator.slide_out_right);
    }
}