package com.example.grass.dalnometr.validation;


import android.content.Context;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.grass.dalnometr.Constants;


/**
 * Created by Yaroslav on 08.03.2016.
 */
public class MyValidator extends AsyncTask<Void,Void,Boolean> {

    String imei;
    TelephonyManager manager;
    ValidationCallback callback;
    Context context;

    public MyValidator(Context context, ValidationCallback callback) {
        super();
        manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        imei = manager.getDeviceId();
        this.context = context;
        this.callback = callback;
        Log.d(Constants.TAG, "MyValidator: " + "IMEI" + imei);
    }




    @Override
    protected Boolean doInBackground(Void... params) {
        Log.d(Constants.TAG, "doInBackground() returned: " );
        MyValidation myValidation = new MyValidation();
        return myValidation.isValid(imei,context);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        callback.valid(aBoolean);
    }
}
