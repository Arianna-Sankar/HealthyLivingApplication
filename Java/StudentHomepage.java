/* This is the package name. */
package com.example.healthylivingapp;

/* These are the Android classes used in the StudentHomepage.java class. */
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/* These are the Android classes which have been imported in order for the app to connect to the Firebase realtime database. */
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/* Inheritance is used as it allows the StudentHomepage.java class to inherit the public and protected methods and variables from the superclass and to override methods.
'AppCompatActivity' is the base class for activities which uses the support library action bar feature. */
public class StudentHomepage extends AppCompatActivity {
/* I have created private variables which define all of the widgets used in the activity_student_homepage.xml file. */
    private Button learn, activities, quiz, progress, logOut;
    private TextView displayName;
    private String passUserName, counter;

/* This implements the 'onCreate()' method from the Activity class. This method is called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/* This specifies which layout is to be used, in this case the activity_student_homepage.xml file. */
        setContentView(R.layout.activity_student_homepage);
/* I have requested for the screen orientation to be portrait. */
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
/* The method 'setUpUIViews' is accessed. */
        setUpUIViews();

/* I have retrieved the user's username from the Login.java class. This has been achieved by using 'getIntent().getStringExtra("username")'. The use of "username",
is a reference which I created in the Login.java class, therefore by calling this reference I am able to retrieve the username object. The object is stored in the
string variable 'userName'. I then appended this string to the TextView 'displayName' where all characters are converted to upperCase, because this string will be
appended alongside the message 'WELCOME BACK '. */
        Intent intent = getIntent();
        String userName = getIntent().getStringExtra("username");
        displayName.append(userName.toUpperCase());
/* The string variable 'userName' is assigned to the variable 'passUserName' which will be passed to the StudentCompleteQuiz.java and StudentViewProgress.java classes. */
        passUserName = userName;

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
                Intent intent = new Intent(StudentHomepage.this,LearningMenu.class);
                startActivity(intent);
            }
        });

        activities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentHomepage.this,ActivitiesMenu.class);
                startActivity(intent);
            }
        });

        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/* The user's username is passed to the student's quiz feature. This has been implemented using 'intent.putExtra' to send a copy of the object between the two classes,
StudentHomepage.java and StudentCompleteQuiz.java. Note, "username" has been used to provide a reference so that from the student's quiz feature, the object is retrieved
by referencing "username". */
                Intent intent = new Intent(getApplicationContext(), StudentCompleteQuiz.class);
                intent.putExtra("username", passUserName);
                startActivity(intent);
            }
        });

        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/* I have created two objects of 'DatabaseReference'. The object 'rootRef' gets the reference of the database and points to the root of the Firebase database. */
                DatabaseReference rootRef, nodeRef;
                rootRef = FirebaseDatabase.getInstance().getReference();
/* The object 'nodeRef' is the child of 'rootRef' and is now able to access the user's information by using '.child(passUserName)'. */
                nodeRef = rootRef.child(passUserName);
/* After the two objects have been instantiated, the program then retrieves the reference to the 'counter' node with the value of an empty string which was created in
the CreateAnAccount.java class. To access this particular node I have used 'nodeRef.child'. The purpose of this is to determine whether the user has yet completed their
first quiz. This has been implemented to solve a problem where if the user has never completed a quiz and clicks on the 'progress' button, the app would crash because
the StudentViewProgress.java class requests a counter to be displayed but because a counter has never been created, the app crashes. Therefore by creating the counter in
the CreateAnAccount.java class this allows an 'if else' statement to be executed where if the counter (created in the CreateAnAccount.java class) is still an empty string
then the user is not allowed to enter the progress feature until they have completed the quiz because once they do this then data will be stored in the counter allowing
access to the progress feature. */
/* The data read from the node is stored as a string variable. */
                nodeRef.child("counter").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String dbquiznumber = dataSnapshot.getValue(String.class);
                        counter = dbquiznumber;
                        if(counter.equals("")){
                            Toast.makeText(StudentHomepage.this, "You must complete a quiz before you can view your progress", Toast.LENGTH_SHORT).show();
                        }else{
/* The user's username is passed to the student's progress feature. This has been implemented using 'intent.putExtra' to send a copy of the object between the two classes,
StudentHomepage.java and StudentViewProgress.java. Note, "username" has been used to provide a reference so that from the student's progress feature, the object is
retrieved by referencing "username". */
                            Intent intent = new Intent(getApplicationContext(), StudentViewProgress.class);
                            intent.putExtra("username", passUserName);
                            startActivity(intent);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentHomepage.this,Login.class);
                startActivity(intent);
            }
        });
    }

    private void setUpUIViews(){
/* All of the variables are assigned to their corresponding ID attribute (widget) from the activity_student_homepage.xml file. */
        displayName = (TextView)findViewById(R.id.tvDisplayUserName);
        learn = (Button)findViewById(R.id.btnLearn);
        activities = (Button)findViewById(R.id.btnActivities);
        quiz = (Button)findViewById(R.id.btnQuiz);
        progress = (Button)findViewById(R.id.btnMyProgress);
        logOut = (Button)findViewById(R.id.btnSignOut);
    }
}