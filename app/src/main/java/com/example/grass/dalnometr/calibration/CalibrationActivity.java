package com.example.grass.dalnometr.calibration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.grass.dalnometr.R;
import com.example.grass.dalnometr.list.Item;
import com.example.grass.dalnometr.list.ItemAdapter;

import java.util.ArrayList;

public class CalibrationActivity extends Activity implements View.OnClickListener {
    ItemAdapter adapter;
    ArrayList<Item> list;
    int colibrCount = 0;
    SharedPreferences spAccurate;
    int itemPoistion = 0;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibration);
        context = this;
        list= new ArrayList<Item>();
        adapter = new ItemAdapter(this,list);
        spAccurate = getSharedPreferences("ACCURATE", MODE_PRIVATE);

        if(spAccurate.contains("colibrCount")!=true) {
            spAccurate.edit().putString("colibrCount", "0");
            spAccurate.edit().apply();
        }
        else
            colibrCount = Integer.parseInt(spAccurate.getString("colibrCount",""));

        importAccuracy(colibrCount);

        ListView view = (ListView)findViewById(R.id.listView);
        if(view!=null)
        view.setAdapter(adapter);
        Button but  = (Button)findViewById(R.id.button4);
        but.setOnClickListener(this);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemPoistion = position+1;
                Intent intent = new Intent(context, DalCalibrActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button4:
                Intent intent = new Intent(this, DalCalibrActivity.class);
                startActivityForResult(intent,1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data!=null){
            double angle  = 0.0;
            double length = 0.0;
            double eyeLength = 0.0;
            double eyeHeight= 0.0;
            angle = data.getDoubleExtra("angle",angle);
            length = data.getDoubleExtra("length",length);
            eyeLength = data.getDoubleExtra("eyeLength",eyeLength);
            eyeHeight = data.getDoubleExtra("eyeHeight",eyeHeight);
            double accurate = roundNumber(1-(length/eyeLength),2);

            adapter.notifyDataSetChanged();
            if(itemPoistion ==0) {
                addAccuracy(++colibrCount, angle, eyeLength, eyeHeight, accurate, length);
                list.add(new Item(eyeHeight,length,angle,eyeLength,accurate));
            }
            else{
                list.get(itemPoistion-1).setAngle(angle);
                list.get(itemPoistion-1).setError(accurate);
                list.get(itemPoistion-1).setHeight(eyeHeight);
                list.get(itemPoistion-1).setMeger(length);
                list.get(itemPoistion-1).setuMerge(eyeLength);
                adapter.notifyDataSetChanged();
                addAccuracy(itemPoistion,angle,eyeLength,eyeHeight,accurate,length);
                itemPoistion = 0;
            }
            Log.d("log","angle = "+angle+"length = "+length);
        }
    }

    private void addAccuracy(int id,double angle,double eyeLength,double eyeHeight,double accurate, double length){
        SharedPreferences.Editor editor = spAccurate.edit();
            editor.putString("angle"+id,""+angle);
            editor.putString("eyeLength"+id,""+eyeLength);
            editor.putString("eyeHeight"+id,""+eyeHeight);
            editor.putString("accurate"+id,""+accurate);
            editor.putString("length"+id,""+length);
            if(id>colibrCount)
                colibrCount++;
            editor.putString("colibrCount",""+colibrCount);
            editor.putString("accurate",""+countDisp());
        editor.apply();

    }
    private void importAccuracy(int count){

        for(int i  = 1;i <= count; i++){
            double angle = Double.parseDouble(spAccurate.getString("angle"+i,""));
            double eyeLength = Double.parseDouble(spAccurate.getString("eyeLength"+i,""));
            double eyeHeight = Double.parseDouble(spAccurate.getString("eyeHeight"+i,""));
            double accurate = Double.parseDouble(spAccurate.getString("accurate"+i,""));
            double length = Double.parseDouble(spAccurate.getString("length"+i,""));
            list.add(new Item(eyeHeight,length,angle,eyeLength,accurate));
        }
    }

    private double countDisp(){
        double sum  = 0;
        double disp = 0;
        for(Item item : list)
            sum = item.getError()/list.size();
        for(Item item : list){
            disp+=Math.pow(item.getError() - sum,2)/list.size();
        }
        return disp;
    }
    public double roundNumber(double number, double accurancy) {
        accurancy = Math.pow(10, accurancy);
        number = Math.round(number * accurancy);

        return number / accurancy;
    }
}
