package com.czh.snail.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.czh.snail.R;
import com.czh.snail.views.main.MainActivity;
import com.czh.snail.views.splash.SplashActivity;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;

/**
 * 异常捕获页面
 */
public class CustomErrorActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerror);
        //**IMPORTANT**
        //The custom error activity in this sample is uglier than the default one and just
        //for demonstration purposes, please don't copy it to your project!
        //We recommend taking the original library's DefaultErrorActivity as a basis.
        //Of course, you are free to implement it as you wish in your application.

        //These three methods are available for you to use:
        //CustomActivityOnCrash.getStackTraceFromIntent(getIntent()): gets the stack trace as a string
        //CustomActivityOnCrash.getAllErrorDetailsFromIntent(context, getIntent()): returns all error details including stacktrace as a string
        //CustomActivityOnCrash.getRestartActivityClassFromIntent(getIntent()): returns the class of the restart activity to launch, or null if none

        //Now, treat here the error as you wish. If you allow the user to restart the app,
        //don't forget to finish the activity, otherwise you will get the CustomErrorActivity
        //on the activity stack and it will be visible again under some circumstances.

        TextView errorDetailsText = (TextView) findViewById(R.id.error_details);
        errorDetailsText.setText(CustomActivityOnCrash.getStackTraceFromIntent(getIntent()));

        Button restartButton = (Button) findViewById(R.id.restart_button);
        final Button finishButton = (Button) findViewById(R.id.finish_button);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomErrorActivity.this, SplashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.newIntent(CustomErrorActivity.this, true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }


}
