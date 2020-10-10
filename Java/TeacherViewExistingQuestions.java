/* This is the package name. */
package com.example.healthylivingapp;

/* These are the Android classes used in the TeacherViewExistingQuestions.java class. */
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/* These are the Android classes which have been imported in order for the app to connect to the Firebase realtime database. */
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/* Inheritance is used as it allows the TeacherViewExistingQuestions.java class to inherit the public and protected methods and variables from the superclass and to
override methods. 'AppCompatActivity' is the base class for activities which uses the support library action bar feature. */
public class TeacherViewExistingQuestions extends AppCompatActivity {
/* I have created private variables which define all of the widgets used in the activity_teacher_view_existing_questions.xml file. */
    private TextView qNumber, question, choice1, choice2, choice3, choice4, answer;
    private Button next;
    private int questionNumber = 1;

/* I have created two objects of 'DatabaseReference' which will be used to read data from specific locations in the Firebase database. */
    DatabaseReference rootRef, nodeRef;

/* This implements the 'onCreate()' method from the Activity class. This method is called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/* This specifies which layout is to be used, in this case the activity_teacher_view_existing_questions.xml file. */
        setContentView(R.layout.activity_teacher_view_existing_questions);
/* I have requested for the screen orientation to be portrait. */
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
/* The methods 'setUpUIViews' and 'retrieveQuestions' is accessed. */
        setUpUIViews();
        retrieveQuestions();

/* An event listener has been used. This method is called when the 'view' has been triggered by the user's interaction with the 'next' button. The callback
method 'View.OnClickListener' has been used so that when the user touches this button, the remaining lines of code are executed. */
        next.setOnClickListener(new View.OnClickListener() {
            @Override
/* 'public void' means that the method is visible and can be called from other objects. The use of 'void' means that this method has no return value and is called
when the button is touched. When this button is activated, the method 'retrieveQuestions' is accessed.*/
            public void onClick(View view) {
                retrieveQuestions();
            }
        });
    }

    private void setUpUIViews(){
/* All of the variables are assigned to their corresponding ID attribute (widget) from the activity_teacher_view_existing_questions.xml file. */
        qNumber = (TextView)findViewById(R.id.tvQuestionNumber);
        question = (TextView)findViewById(R.id.tvQuestion);
        choice1 = (TextView)findViewById(R.id.etChoice1);
        choice2 = (TextView)findViewById(R.id.etChoice2);
        choice3 = (TextView)findViewById(R.id.etChoice3);
        choice4 = (TextView)findViewById(R.id.etChoice4);
        answer = (TextView)findViewById(R.id.tvAnswer);
        next = (Button)findViewById(R.id.btnNext);
    }

    private void retrieveQuestions(){
/* This method is used to retrieve the questions from the Firebase database. */
/* The object 'rootRef' gets the reference of the database and points to the root of the Firebase database. */
        rootRef = FirebaseDatabase.getInstance().getReference();
/* The object 'nodeRef' is the child of 'rootRef' and is now able to access the question by using '.child("Question Number " + questionNumber)'. */
        nodeRef = rootRef.child("Question Number " + questionNumber);

/* After the two objects have been instantiated, the program then retrieves the reference to the following nodes 'questionNumber', 'question', 'choice1', 'choice2',
'choice3', 'choice4' and 'answer' by using 'nodeRef.child' to access the particular node. */
/* The data read from each node is stored as a string variable and stored in the corresponding TextView widget. */
        nodeRef.child(String.valueOf("questionNumber")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dbqnumber = dataSnapshot.getValue(String.class);
                qNumber.setText("Question: " + dbqnumber);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        nodeRef.child("question").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dbquestion = dataSnapshot.getValue(String.class);
                question.setText("Question: " + dbquestion);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        nodeRef.child("choice1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dbchoice1 = dataSnapshot.getValue(String.class);
                choice1.setText("Choice 1: "+ dbchoice1);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        nodeRef.child("choice2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dbchoice2 = dataSnapshot.getValue(String.class);
                choice2.setText("Choice 2: " + dbchoice2);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        nodeRef.child("choice3").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dbchoice3 = dataSnapshot.getValue(String.class);
                choice3.setText("Choice 3: " + dbchoice3);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        nodeRef.child("choice4").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dbchoice4 = dataSnapshot.getValue(String.class);
                choice4.setText("Choice 4: " + dbchoice4);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        nodeRef.child("answer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dbanswer = dataSnapshot.getValue(String.class);
                answer.setText("Answer: " + dbanswer);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
/* The integer variable 'questionNumber' is incremented by 1 after the question has been displayed in the TextView widgets. */
        questionNumber = questionNumber + 1;
    }
}