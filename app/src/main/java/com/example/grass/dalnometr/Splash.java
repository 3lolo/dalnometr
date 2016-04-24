package com.example.grass.dalnometr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;



/**
 * Created by Grass on 12.03.2016.
 */
public class Splash extends Activity {

    private Context context ;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this;
        Thread logoTimer = new Thread()
        {
            public void run()
            {
                try
                {
                    int logoTimer = 0;
                    while(logoTimer < 5000)
                    {
                        sleep(100);
                        logoTimer = logoTimer +100;
                    };

                    Intent intent = new Intent(context,MeteringActivity.class);
                    startActivity(intent);
                }
                catch (InterruptedException e)
                {
                    // TODO: автоматически сгенерированный блок catch.
                    e.printStackTrace();
                }
                finally
                {
                    finish();
                }
            }
        };
        logoTimer.start();
    }
}
