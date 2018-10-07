package com.studio.bin.kqxs;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.Random;

public class ProcessResultActivity extends AppCompatActivity {
    private static final int NUM_PROCESS_MIN = 30;
    private static final int NUM_PROCESS_MAX = 36;

    private TextView txtCountProcess;
    private ProgressBar progressBarCaculator;
    private TextView txtProcess;
    private ImageButton imageButtonContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_result);
        txtCountProcess = findViewById(R.id.txtCountProcess);
        progressBarCaculator = findViewById(R.id.progressBarCaculator);
        txtProcess = findViewById(R.id.txtProcess);
        imageButtonContinue = findViewById(R.id.imageButtonContinue);
        new RunProcess(txtCountProcess, progressBarCaculator, txtProcess).execute();

        imageButtonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RunProcess(txtCountProcess, progressBarCaculator, txtProcess).execute();
            }
        });

    }

    public boolean isConnected() throws InterruptedException, IOException {
        String command = "ping -c 1 google.com";
        return (Runtime.getRuntime().exec(command).waitFor() == 0);
    }

    public int getRandomIntInBetween(int min, int max) {

        Random r = new Random();
        return min + r.nextInt(max - min);
    }

    public int[] getListTime2s(int numMax) {
        int[] arrNum = new int[numMax];
        for (int i = 0; i < numMax; i++) {
            Random r = new Random();
            int sNum = r.nextInt(99);
            arrNum[i] = sNum;
        }
        return arrNum;
    }

    public boolean checkInArr(int number, int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (number == arr[i]) {
                return true;
            }
        }
        return false;
    }

    public class RunProcess extends AsyncTask<Void, Integer, Boolean> {
        private TextView txtCountProcess;
        private ProgressBar progressBarCaculator;
        private TextView txtProcess;

        public String countProcessString = "0%";
        public String notifiProcess = "Bắt đầu tính toán .";

        public int countProcessUpdate = 0;
        public int jumpTimeUpdate = 0;
        public int processUpdate = 0;

        public RunProcess(TextView txtCountProcess, ProgressBar progressBarCaculator, TextView txtProcess) {
            this.txtCountProcess = txtCountProcess;
            this.progressBarCaculator = progressBarCaculator;
            this.txtProcess = txtProcess;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            txtCountProcess.setText(countProcessString);
            txtProcess.setText(notifiProcess);
            progressBarCaculator.setProgress(0);
            imageButtonContinue.setVisibility(View.GONE);

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            int maxPrcess = getRandomIntInBetween(NUM_PROCESS_MIN, NUM_PROCESS_MAX);
            int jumpTime = maxPrcess * 1000 / 100;
            int process = jumpTime;
            int[] listTime2s = getListTime2s(5);
            //Log.i("listTime2s", listTime2s[0] + " --- " + listTime2s[1] + " --- " + listTime2s[2] + " --- " + listTime2s[3] + " --- " + listTime2s[4] + " --- ");
            for (int i = 1; i < 101; i++) {
                if (i == 99 || checkInArr(i, listTime2s) == true) {
                    //Log.i("concat", checkInArr(i, listTime2s) + "");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {

                    if (isConnected()) {
                        publishProgress(i, jumpTime, process * i);
                    } else {
                        publishProgress(i, jumpTime, process * i);
                        return false;
                    }
                    Thread.sleep(jumpTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            countProcessUpdate = values[0];
            jumpTimeUpdate = values[1];
            processUpdate = values[2];
            //Log.i("Result", "count : " + countProcessUpdate + "___jumpTime : " + jumpTimeUpdate + "____process : " + processUpdate);
            progressBarCaculator.setMax(100);

            notifiProcess = (countProcessUpdate >= 1 && countProcessUpdate < 3) ? "Bắt đầu tính toán . ."
                    : (countProcessUpdate >= 3 && countProcessUpdate < 5) ? "Bắt đầu tính toán . . ."
                    : (countProcessUpdate >= 5 && countProcessUpdate < 7) ? "Xin hãy chờ "
                    : (countProcessUpdate >= 9 && countProcessUpdate < 9) ? "Xin hãy chờ "
                    : (countProcessUpdate >= 9 && countProcessUpdate < 11) ? "Xin hãy chờ . "
                    : (countProcessUpdate >= 11 && countProcessUpdate < 13) ? "Xin hãy chờ . ."
                    : (countProcessUpdate >= 13 && countProcessUpdate < 15) ? "Xin hãy chờ . . ."
                    : (countProcessUpdate >= 15 && countProcessUpdate < 19) ? "Hãy đảm bảo kết nối Internet."
                    : (countProcessUpdate >= 19 && countProcessUpdate < 26) ? "Thời gian tính toán có thể lên tới 55s."
                    : (countProcessUpdate >= 26 && countProcessUpdate < 33) ? "Khả năng chính xác lên tới 75%."
                    : (countProcessUpdate >= 33 && countProcessUpdate < 39) ? "Thực hiện app ngày nhiều hơn 3 lần."
                    : (countProcessUpdate >= 39 && countProcessUpdate < 45) ? "Giúp kết quả chính xác hơn."
                    : (countProcessUpdate >= 45 && countProcessUpdate < 51) ? "Hãy đảm bảo kết nối Internet."
                    : (countProcessUpdate >= 51 && countProcessUpdate < 53) ? "Xin hãy chờ "
                    : (countProcessUpdate >= 53 && countProcessUpdate < 56) ? "Xin hãy chờ ."
                    : (countProcessUpdate >= 56 && countProcessUpdate < 59) ? "Xin hãy chờ . ."
                    : (countProcessUpdate >= 59 && countProcessUpdate < 62) ? "Xin hãy chờ . . ."
                    : (countProcessUpdate >= 62 && countProcessUpdate < 64) ? "Xin hãy chờ ."
                    : (countProcessUpdate >= 64 && countProcessUpdate < 67) ? "Xin hãy chờ . ."
                    : (countProcessUpdate >= 67 && countProcessUpdate < 69) ? "Xin hãy chờ . . ."
                    : (countProcessUpdate >= 69 && countProcessUpdate < 72) ? "Xử lý kết quả ."
                    : (countProcessUpdate >= 72 && countProcessUpdate < 74) ? "Xử lý kết quả . ."
                    : (countProcessUpdate >= 74 && countProcessUpdate < 77) ? "Đồng bộ hóa . "
                    : (countProcessUpdate >= 77 && countProcessUpdate < 80) ? "Đồng bộ hóa . ."
                    : (countProcessUpdate >= 80 && countProcessUpdate < 82) ? "Đồng bộ hóa . . ."
                    : (countProcessUpdate >= 82 && countProcessUpdate < 85) ? "Đồng bộ hóa . "
                    : (countProcessUpdate >= 85 && countProcessUpdate < 88) ? "Đồng bộ hóa . ."
                    : (countProcessUpdate >= 88 && countProcessUpdate < 94) ? "Đồng bộ hóa . . ."
                    : (countProcessUpdate >= 94 && countProcessUpdate < 101) ? "Hoàn thành." : "";

            txtProcess.setText(notifiProcess);
            countProcessString = countProcessUpdate + "%";
            txtCountProcess.setText(countProcessString);

            progressBarCaculator.setProgress(countProcessUpdate);

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (aBoolean) {
                countProcessString = 100 + "%";
                txtCountProcess.setText(countProcessString);
                txtProcess.setText(notifiProcess);
                AcessData.PROCESS_SHOW = 1;
                Intent intent = new Intent(ProcessResultActivity.this, ShowResultActivity.class);
                startActivity(intent);
                ProcessResultActivity.this.finish();
                overridePendingTransition(R.animator.slide_in_left, R.animator.slide_out_right);


            } else {
                notifiProcess = "Không thể hoàn thành. Kiểm tra kết nối internet...";
                txtCountProcess.setText("Oops!");
                txtProcess.setText(notifiProcess);
                imageButtonContinue.setVisibility(View.VISIBLE);
                AcessData.PROCESS_SHOW = 0;
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Nothing
    }
}
