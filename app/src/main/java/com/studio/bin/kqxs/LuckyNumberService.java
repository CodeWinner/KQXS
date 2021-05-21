package com.studio.bin.kqxs;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.studio.bin.kqxs.AcessData.CAL_NUMBER;
import static com.studio.bin.kqxs.AcessData.LUCKY_NUMBER;

public class LuckyNumberService extends Service {
    private static final int MAX_COUNT = 2000;
    private static final int BOUND_LUCKY = 99;
    private int i = 0;
    final Handler handler = new Handler();
    private static List<Integer> luckyNumberList;

    public LuckyNumberService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        luckyNumberList = new ArrayList<>();

        setDefaultLuckyNumber();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Thread timer = new Thread() {
            public void run(){

                while (true) {
                    try {
                        Random rand = new Random();
                        int position = rand.nextInt(BOUND_LUCKY);

                        int countAtPosition = luckyNumberList.get(position) + 1;
                        luckyNumberList.set(position, countAtPosition);

                        LUCKY_NUMBER = listLuckyNumber().get(0);
                        i++;
                        CAL_NUMBER = i;
                        Thread.sleep(50);
                    } catch (Exception e) {
                        Random rand = new Random();
                        LUCKY_NUMBER = rand.nextInt(BOUND_LUCKY);
                    }
                }

            }
        };
        timer.start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    private void setDefaultLuckyNumber(){
        for (int i = 0; i < 100; i++) {
            luckyNumberList.add(0);
        }
    }

    private List<Integer> listLuckyNumber(){
        List<Integer> maxNumber = new ArrayList<>();
        int max = getMax();
        for (int i = 0; i < 100; i++) {
            if(luckyNumberList.get(i) == max) {
                maxNumber.add(i);
            }
        }
        return maxNumber;
    }

    public int getMax(){
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < luckyNumberList.size(); i++){
            if(luckyNumberList.get(i) > max){
                max = luckyNumberList.get(i);
            }
        }
        return max;
    }

    public List<Integer> getListHighValue(List<Integer> luckyNumberList){
        List<Integer> listValue = new ArrayList<>();
        List<Integer> tempList = luckyNumberList;
        Collections.sort(tempList);

        listValue.add(tempList.get(tempList.size()-1));
        for (int i = tempList.size()-1; i >= 0; i--) {
            // Hight 2
            if (!listValue.contains(tempList.get(i))){
                listValue.add(tempList.get(i));
            }

            if (listValue.size() == 10) {
                return listValue;
            }
        }
        return listValue;
    }
}
