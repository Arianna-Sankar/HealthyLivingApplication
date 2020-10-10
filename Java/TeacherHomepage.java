/* This is the package name. */
package com.example.healthylivingapp;

/* These are the Android classes used in the TeacherHomepage.java class. */
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/* Inheritance is used as it allows the TeacherHomepage.java class to inherit the public and protected methods and variables from the superclass and to override methods.
'AppCompatActivity' is the base class for activities which uses the support library action bar feature. */
public class TeacherHomepage extends AppCompatActivity {
/* I have created private variables which define all of the widgets used in the activity_teacher_homepage.xml file. */
    private Button learn, activities, quiz, progress, logOut;

/* This implements the 'onCreate()' method from the Activity class. This method is called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/* This specifies which layout is to be used, in this case the activity_teacher_homepage.xml file. */
        setContentView(R.layout.activity_teacher_homepage);
/* I have requested for the screen orientation to be portrait. */
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
/* The function 'setUpUIViews' is accessed. */
        setUpUIViews();

/* Event listeners have been used. These methods are called when the 'view' has been triggered by the user's interaction with the 'learn', 'activities', 'quiz',
'progress' and 'logOut' button. The callback method 'View.OnClickListener' has been used so that when the user touches the button, the remaining lines of code are
executed. */
/* 'public void' means that the method is visible and can be called from other objects. The use of 'void' means that this method has no return value and is called
when the button is touched. 'Intent' is used which is a messaging object that requests an action from another activity. */
/* 'startActivity' represents a single screen in an app. I have started a new instance of activity by passing an 'intent' to start the activity. An 'intent' describes
what activity will be carried out next. */
        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeacherHomepage.this,LearningMenu.class);
                startActivity(intent);
            }
        });

        activities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeacherHomepage.this,ActivitiesMenu.class);
                startActivity(intent);
            }
        });

        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeacherHomepage.this,TeacherSetQuiz.class);
                startActivity(intent);
            }
        });

        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeacherHomepage.this,TeacherViewProgress.class);
                startActivity(intent);
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeacherHomepage.this,Login.class);
                startActivity(intent);
            }
        });
    }

    private void setUpUIViews(){
/* All of the variables are assigned to their corresponding ID attribute (widget) from the activity_teacher_homepage.xml file. */
        learn = (Button)findViewById(R.id.btnLearn);
        activities = (Button)findViewById(R.id.btnActivities);
        quiz = (Button)findViewById(R.id.btnQuiz);
        progress = (Button)findViewById(R.id.btnMyProgress);
        logOut = (Button)findViewById(R.id.btnSignOut);
    }
}