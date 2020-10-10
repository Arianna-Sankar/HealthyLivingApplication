/* This is the package name. */
package com.example.healthylivingapp;

/* These are the Android classes used in the TeacherSetQuiz.java class. */
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/* These are the Android classes which have been imported in order for the app to connect to the Firebase realtime database. */
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/* Inheritance is used as it allows the TeacherSetQuiz.java class to inherit the public and protected methods and variables from the superclass and to override methods.
'AppCompatActivity' is the base class for activities which uses the support library action bar feature. */
public class TeacherSetQuiz extends AppCompatActivity {
/* I have created private variables which define all of the widgets used in the activity_teacher_set_quiz.xml file. */
    private EditText qNumber, question, choice1, choice2, choice3, choice4, answer;
    private Button save, existingQuestions;
    private String sQNumber, sQuestion, sChoice1, sChoice2, sChoice3, sChoice4, sAnswer, exists;

/* I have created two objects of 'DatabaseReference' which will be used to read data from specific locations in the Firebase database. */
    DatabaseReference rootRef, nodeRef;

/* This implements the 'onCreate()' method from the Activity class. This method is called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/* This specifies which layout is to be used, in this case the activity_teacher_set_quiz.xml file. */
        setContentView(R.layout.activity_teacher_set_quiz);
/* I have requested for the screen orientation to be portrait. */
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
/* The methods 'setUpUIViews' and 'validate' is accessed. */
        setUpUIViews();
        validate();

/* Event listeners have been used. These methods are called when the 'view' has been triggered by the user's interaction with the 'save' and 'existingQuestions' button.
The callback method 'View.OnClickListener' has been used so that when the user touches the button, the remaining lines of code are executed. */
/* 'public void' means that the method is visible and can be called from other objects. The use of 'void' means that this method has no return value and is called
when the button is touched. */
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/* In the 'if else' statement below, the method 'validate' is accessed. If the result of this method is true, the method 'sendQuestion' is accessed where all
details entered for this question is added to the Firebase database (this is part of the content management system). Otherwise, if the result of 'validate' is false,
then an error message is displayed. */
                if(validate()){
                    sendQuestion();
                    Toast.makeText(TeacherSetQuiz.this, "Question Saved", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(TeacherSetQuiz.this, "Question Not Saved", Toast.LENGTH_SHORT).show();
                }
            }
        } );

        existingQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeacherSetQuiz.this, TeacherViewExistingQuestions.class);
/* 'startActivity' represents a single screen in an app. I have started a new instance of activity by passing an 'intent' to start the activity. An 'intent' describes
what activity will be carried out next. */
                startActivity(intent);
            }
        } );
    }

    private void setUpUIViews() {
/* All of the variables are assigned to their corresponding ID attribute (widget) from the activity_teacher_set_quiz.xml file. */
        qNumber = (EditText)findViewById(R.id.etQuestionNumber);
        question = (EditText)findViewById(R.id.etQuestion);
        choice1 = (EditText)findViewById(R.id.etChoice1);
        choice2 = (EditText)findViewById(R.id.etChoice2);
        choice3 = (EditText)findViewById(R.id.etChoice3);
        choice4 = (EditText)findViewById(R.id.etChoice4);
        answer = (EditText)findViewById(R.id.etAnswer);
        save = (Button) findViewById(R.id.btnSave);
        existingQuestions = (Button)findViewById(R.id.btnExistingQuestions);
    }

    private Boolean validate() {
/* The method 'validate' method is executed. The initial boolean value of 'result' has been set to 'false'. In this method, encapsulation is used as I have created
string variables which fetches the values entered by the user and converts them to the data type of string. Branching is then used as the 'if else' statement checks
whether the fields on this screen have been left blank, if so a message pops up (using the 'Toast.makeText()' method) stating that the user must complete all fields.
If all fields have been completed, 'result' is then set to 'true'. Also 'exists' is set to 'true' which will be used in the 'sendQuestion' method. */
        Boolean result = false;
        sQNumber = qNumber.getText().toString();
        sQuestion = question.getText().toString();
        sChoice1 = choice1.getText().toString();
        sChoice2 = choice2.getText().toString();
        sChoice3 = choice3.getText().toString();
        sChoice4 = choice4.getText().toString();
        sAnswer = answer.getText().toString();
        exists = "true";
        if(sQNumber.isEmpty() || sQuestion.isEmpty() || sChoice1.isEmpty() || sChoice2.isEmpty() || sChoice3.isEmpty() || sChoice4.isEmpty() || sAnswer.isEmpty()){
            Toast.makeText(this, "Please Complete All Fields", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }
        return result;
    }

    private void sendQuestion() {
/* The object 'rootRef' gets the reference of the database and points to the root of the Firebase database. */
        rootRef = FirebaseDatabase.getInstance().getReference();
/* The object 'nodeRef' is the child of 'rootRef' and is now able to add the question by using '.child("Question Number " + sQNumber)'. */
        nodeRef = rootRef.child("Question Number " + sQNumber);

/* After the two objects have been instantiated, the program then retrieves the reference to the node 'exists' by using 'nodeRef.child' to access this particular node. */
        nodeRef.child("exists").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String existsStatus = dataSnapshot.getValue(String.class);
/* The data read from the 'exists' node is stored as a string variable 'existsStatus'. */

/* When this method is executed, it creates a firebaseDatabase object and then creates a reference which is the question number that the user has entered. */
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference Reference = firebaseDatabase.getReference("Question Number " + sQNumber);
/* I have assigned the values of 'sQNumber', 'sQuestion', 'sChoice1', 'sChoice2', 'sChoice3', 'sChoice4', 'sAnswer' and 'exists' to the variables used in the
QuizQuestionandAnswer.java class. */
                QuizQuestionandAnswer questionsandanswers = new QuizQuestionandAnswer(sQNumber, sQuestion, sChoice1, sChoice2, sChoice3, sChoice4, sAnswer, exists);
/* These details will now be stored in Firebase following the JSON hierarchy data structure. */
                Reference.setValue( questionsandanswers );

/* An 'if' statement is used where if the string variable 'existsStatus' is equal to the variable 'exists' which is set as 'true', then this means that the user is
overwriting an existing question (this is part of the content management system). */
                if(String.valueOf(existsStatus).equals(exists)){
                    Toast.makeText(TeacherSetQuiz.this, "You have overwritten question " + sQNumber, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}