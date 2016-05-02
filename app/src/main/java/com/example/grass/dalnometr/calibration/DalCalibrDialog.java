package com.example.grass.dalnometr.calibration;

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

import com.example.grass.dalnometr.R;


/**
 * Created by Grass on 10.03.2016.
 */
public class DalCalibrDialog extends DialogFragment implements View.OnClickListener {

    public static final String APP_PREFERENCES = "meteringData";
    private SharedPreferences mSettings;
    private EditText editHeight;
    private EditText editLength;
    private View view;
    DalCalibrActivity activity;
    private AlertDialog dialog;

    public double eyeHeight;
    public double eyeLength;

    public void setMeteringActivity(DalCalibrActivity activity){
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d("crea", "createDialog");

        mSettings = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        view  = inflater.inflate(R.layout.dialog_meter, null);
        builder.setView(view);

        editHeight   = (EditText)   view.findViewById(R.id.editHeight);
        editLength   = (EditText)   view.findViewById(R.id.editLength);
        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(this);

        dialog     = builder.create();
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void saveData(float eyeHeight, float eyeLength){
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putFloat("eyeHeight", eyeHeight);
        editor.putFloat("eyeLength", eyeLength);

        editor.commit();
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {

        return super.show(transaction, tag);
    }

    @Override
    public void onClick(View v) {
        try {
            double eyeHeight = Double.parseDouble(editHeight.getText().toString());
            double eyeLength = Double.parseDouble(editLength.getText().toString());
            if (eyeHeight != 0) {
                saveData(eyeHeight,eyeLength);
                    dialog.dismiss();
                }
            }catch(Exception e){}
    }

    public void saveData(double eyeHeight, double eyeLength){
        //saveData(Float.valueOf("" + eyeHeight),Float.valueOf("" + eyeLength));
        this.eyeHeight = eyeHeight;
        this.eyeLength = eyeLength;
        activity.startTask(eyeHeight);
    }
}
