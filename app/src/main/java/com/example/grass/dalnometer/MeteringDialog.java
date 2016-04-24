package com.example.grass.metering;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Grass on 10.03.2016.
 */
public class MeteringDialog extends DialogFragment implements View.OnClickListener {

    public static final String APP_PREFERENCES = "meteringData";
    private SharedPreferences mSettings;
    private EditText editText;
    private View view;
    MeteringActivity activity;
    private AlertDialog dialog;

    private double eyeHeight;
    HashMap<String,Double> map;

    public void setMeteringActivity(MeteringActivity activity){
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    public void setViews(){
        map        = getData();
        if(map!=null) {
            editText.setText("" + map.get("eyeHeight"));
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d("crea", "createDialog");

        mSettings = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        view  = inflater.inflate(R.layout.dialog_metering, null);
        builder.setView(view);

        editText   = (EditText)   view.findViewById(R.id.editHeight);
        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(this);

        dialog     = builder.create();
        setViews();

        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void saveData(float eyeHeight){
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putFloat("eyeHeight", eyeHeight);

        editor.commit();
    }
    private HashMap<String,Double> getData(){
        if (mSettings==null)
            return null;
        if (mSettings.getAll().size() ==0 )
            return null;

        double eyeHeight   = 0;

        Map map = mSettings.getAll();
        eyeHeight  = Double.valueOf(""+map.get("eyeHeight"));

        HashMap<String,Double> data = null;
        if(eyeHeight != 0) {
            data = new HashMap();
            data.put("eyeHeight", eyeHeight);
        }
        return data;
    }



    @Override
    public int show(FragmentTransaction transaction, String tag) {

        setViews();
        return super.show(transaction, tag);
    }

    @Override
    public void onClick(View v) {
        try {
            double eyeHeight = Double.parseDouble(editText.getText().toString());
            if (eyeHeight != 0) {
                saveData(eyeHeight);
                    dialog.dismiss();
                }
            }catch(Exception e){}
    }

    public void saveData(double eyeHeight){
        saveData(Float.valueOf("" + eyeHeight));
        activity.startTask(eyeHeight);
        this.eyeHeight = eyeHeight;
    }

    public double[] getParams(){
        return new double[]{eyeHeight};
    }

}
