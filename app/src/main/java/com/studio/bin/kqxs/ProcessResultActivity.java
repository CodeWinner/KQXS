package com.studio.bin.kqxs;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_result);
        txtCountProcess = findViewById(R.id.txtCountProcess);
        progressBarCaculator = findViewById(R.id.progressBarCaculator);
        txtProcess = findViewById(R.id.txtProcess);

        new RunProcess(txtCountProcess,progressBarCaculator,txtProcess).execute();
    }

    public boolean isConnected() throws InterruptedException, IOException {
        String command = "ping -c 1 google.com";
        return (Runtime.getRuntime().exec(command).waitFor() == 0);
    }

    public int getRandomIntInBetween(int min, int max)
    {

        Random r = new Random();
        return min+r.nextInt(max-min);
    }

    public class RunProcess extends AsyncTask<Void,Integer,Boolean>{
        private TextView txtCountProcess;
        private ProgressBar progressBarCaculator;
        private TextView txtProcess;

        public String countProcessString = "0%";
        public String notifiProcess = "Bắt đầu tính toán .";
        public RunProcess(TextView txtCountProcess,ProgressBar progressBarCaculator,TextView txtProcess){
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
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            int maxPrcess = getRandomIntInBetween(NUM_PROCESS_MIN,NUM_PROCESS_MAX);
            for (int i = 0; i < maxPrcess ; i++){
                try {
                    if(isConnected()) {
                        publishProgress(i,maxPrcess);
                    }else {
                        publishProgress(i,maxPrcess);
                        return false;
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int countProcess = values[0];
            int maxProcess = values[1];
            progressBarCaculator.setMax(maxProcess);

            switch (countProcess){
                case 1:
                    notifiProcess = "Bắt đầu tính toán . .";
                    break;
                case 2:
                    notifiProcess = "Bắt đầu tính toán . . .";
                    break;
                case 3:
                    notifiProcess = "Xin hãy chờ ";
                    break;
                case 4:
                    notifiProcess = "Xin hãy chờ ";
                    break;
                case 5:
                    notifiProcess = "Xin hãy chờ . ";
                    break;
                case 6:
                    notifiProcess = "Xin hãy chờ . .";
                    break;
                case 7:
                    notifiProcess = "Xin hãy chờ . . .";
                    break;
                case 8:
                    notifiProcess = "Hãy đảm bảo kết nối Internet.";
                    break;
                case 10:
                    notifiProcess = "Thời gian tính toán có thể lên tới 45s.";
                    break;
                case 12:
                    notifiProcess = "Khả năng chính xác lên tới 75%.";
                    break;
                case 14:
                    notifiProcess = "Thực hiện app ngày nhiều hơn 3 lần.";
                    break;
                case 16:
                    notifiProcess = "Giúp kết quả chính xác hơn.";
                    break;
                case 18:
                    notifiProcess = "Hãy đảm bảo kết nối Internet.";
                    break;
                case 20:
                    notifiProcess = "Xin hãy chờ ";
                    break;
                case 21:
                    notifiProcess = "Xin hãy chờ .";
                    break;
                case 22:
                    notifiProcess = "Xin hãy chờ . .";
                    break;
                case 23:
                    notifiProcess = "Server response . ";
                    break;
                case 24:
                    notifiProcess = "Server response . .";
                    break;
                case 25:
                    notifiProcess = "Server response . . .";
                    break;
                case 26:
                    notifiProcess = "Server response . . . true";
                    break;
                case 27:
                    notifiProcess = "Xử lý kết quả . ";
                    break;
                case 28:
                    notifiProcess = "Xử lý kết quả . .";
                    break;
                case 29:
                    notifiProcess = "Đồng bộ hóa . ";
                    break;
                case 30:
                    notifiProcess = "Đồng bộ hóa . .";
                    break;
                case 31:
                    notifiProcess = "Đồng bộ hóa . . .";
                    break;
                case 32:
                    notifiProcess = "Đồng bộ hóa .";
                    break;
                case 33:
                    notifiProcess = "Đồng bộ hóa . .";
                    break;
                case 34:
                    notifiProcess = "Đồng bộ hóa . . .";
                    break;
                case 35:
                    notifiProcess = "Đồng bộ hóa .";
                    break;
            }
            txtProcess.setText(notifiProcess);
            countProcessString = countProcess +"%";
            txtCountProcess.setText(countProcessString);

            progressBarCaculator.setProgress(countProcess);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(aBoolean){
                countProcessString = 100 +"%";
                txtCountProcess.setText(countProcessString);
                countProcessString = "Hoàn thành...";
                txtCountProcess.setText(countProcessString);
            }else {
                countProcessString = "Oops! Không thể hoàn thành. Kiểm tra kết nối internet...";
                txtCountProcess.setText(countProcessString);
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Nothing
    }
}
