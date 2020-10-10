/* This is the package name. */
package com.example.healthylivingapp;

/* These are the Android classes used in the ActivityExercise.java class. */
import android.content.pm.ActivityInfo;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/* Inheritance is used as it allows the ActivityExercise.java class to inherit the public and protected methods and variables from the superclass and to override methods.
'AppCompatActivity' is the base class for activities which uses the support library action bar feature. */
public class ActivityExercise extends AppCompatActivity {
/* I have created private variables which define all of the widgets used in the activity_activity_exercise.xml file. */
    private Button firstGo, secondGo;

/* This implements the 'onCreate()' method from the Activity class. This method is called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/* This specifies which layout is to be used, in this case the activity_activity_exercise.xml file. */
        setContentView(R.layout.activity_activity_exercise);
/* I have requested for the screen orientation to be portrait. */
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
/* The method 'setUpUIViews' is accessed. */
        setUpUIViews();

/* Event listeners have been used. These methods are called when the 'view' has been triggered by the user's interaction with the 'firstGo' and 'secondGo' button. The
callback method 'View.OnClickListener' has been used so that when the user touches the button, the remaining lines of code are executed. */
/* 'public void' means that the method is visible and can be called from other objects. The use of 'void' means that this method has no return value and is called
when the button is touched. 'Intent' is used which is a messaging object that requests an action from another activity. */
        firstGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/* I have included a method where a timer is built into the button. Once the button is activated, a countdown will start from 30 seconds. */
                new CountDownTimer(30000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        firstGo.setText("TIME REMAINING: " + millisUntilFinished / 1000);
                    }
/* When the 30 seconds have finished, a message will be displayed to alert the user. */
                    public void onFinish() {
                        firstGo.setText("TIMES UP!");
                    }
                }.start();
            }
        });

        secondGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CountDownTimer(30000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        secondGo.setText("TIME REMAINING: " + millisUntilFinished / 1000);
                    }
                    public void onFinish() {
                        secondGo.setText("TIMES UP!");
                    }
                }.start();
            }
        });
    }

    private void setUpUIViews() {
/* All of the variables are assigned to their corresponding ID attribute (widget) from the activity_activity_exercise.xml file. */
        firstGo = (Button)findViewById(R.id.btnFirstGo);
        secondGo = (Button)findViewById(R.id.btnSecondGo);
    }
}