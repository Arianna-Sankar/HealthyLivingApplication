/* This is the package name. */
package com.example.healthylivingapp;

/* These are the Android classes used in the ActivitiesMenu.java class. */
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/* Inheritance is used as it allows the ActivitiesMenu.java class to inherit the public and protected methods and variables from the superclass and to override methods.
'AppCompatActivity' is the base class for activities which uses the support library action bar feature. */
public class ActivitiesMenu extends AppCompatActivity {
/* I have created private variables which define all of the widgets used in the activity_activities_menu.xml file. */
    private Button eating, exercise;

/* This implements the 'onCreate()' method from the Activity class. This method is called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/* This specifies which layout is to be used, in this case the activity_activities_menu.xml file. */
        setContentView(R.layout.activity_activities_menu);
/* I have requested for the screen orientation to be portrait. */
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
/* The method 'setUpUIViews' is accessed. */
        setUpUIViews();

/* Event listeners have been used. These methods are called when the 'view' has been triggered by the user's interaction with the 'eating' and 'exercise' button. The
callback method 'View.OnClickListener' has been used so that when the user touches the button, the remaining lines of code are executed. */
/* 'public void' means that the method is visible and can be called from other objects. The use of 'void' means that this method has no return value and is called
when the button is touched. 'Intent' is used which is a messaging object that requests an action from another activity. */
/* 'startActivity' represents a single screen in an app. I have started a new instance of activity by passing an 'intent' to start the activity. An 'intent'
describes what activity will be carried out next. */
        eating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivitiesMenu.this,ActivityEating.class);
                startActivity(intent);
            }
        });

        exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivitiesMenu.this,ActivityExercise.class);
                startActivity(intent);
            }
        });
    }

    private void setUpUIViews() {
/* All of the variables are assigned to their corresponding ID attribute (widget) from the activity_activities_menu.xml file. */
        eating = (Button)findViewById(R.id.btnEating);
        exercise = (Button)findViewById(R.id.btnExercise);
    }
}