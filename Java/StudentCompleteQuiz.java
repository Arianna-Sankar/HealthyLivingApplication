/* This is the package name. */
package com.example.healthylivingapp;

/* These are the Android classes used in the StudentCompleteQuiz.java class. */
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

/* Inheritance is used as it allows the StudentCompleteQuiz.java class to inherit the public and protected methods and variables from the superclass and to
override methods. 'AppCompatActivity' is the base class for activities which uses the support library action bar feature. */
public class StudentCompleteQuiz extends AppCompatActivity {
/* I have created private variables which define all of the widgets used in the activity_student_complete_quiz.xml file. */
    private TextView displayName, qNumber, question, answer, viewScore;
    private Button choice1, choice2, choice3, choice4, click;
    private String sChoice, sChoice1, sChoice2, sChoice3, sChoice4, userName;
    private int questionNumber = 0, userScore = 0, counter;

/* I have created two objects of 'DatabaseReference' which will be used to read data from specific locations in the Firebase database. */
    DatabaseReference rootRef, nodeRef;

/* This implements the 'onCreate()' method from the Activity class. This method is called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/* This specifies which layout is to be used, in this case the activity_student_complete_quiz.xml file. */
        setContentView(R.layout.activity_student_complete_quiz);
/* I have requested for the screen orientation to be portrait. */
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

/* I have retrieved the user's username from the StudentHomepage.java class. This has been achieved by using 'getIntent().getStringExtra("username")'. The use of
"username", is a reference which I created in the StudentHomepage.java class, therefore by calling this reference I am able to retrieve the username object. The object
is stored in the string variable 'username'. I then set this as the text for the TextView 'displayName' which is stored in the string 'UserName' variable. */
        displayName = (TextView)findViewById(R.id.tvDisplayUserName);
        Intent intent = getIntent();
        String username = getIntent().getStringExtra("username");
        displayName.setText(username);
        userName = displayName.getText().toString();

/* The methods 'setUpUIViews' and 'retrieveQuestions' is accessed. */
        setUpUIViews();
        retrieveQuestions();

/* An event listener has been used. This method is called when the 'view' has been triggered by the user's interaction with the 'click' button. The callback
method 'View.OnClickListener' has been used so that when the user touches this button, the remaining lines of code are executed. */
        click.setOnClickListener(new View.OnClickListener() {
            @Override
/* 'public void' means that the method is visible and can be called from other objects. The use of 'void' means that this method has no return value and is called
when the button is touched. When this button is activated, the method 'retrieveQuestions' is accessed. */
            public void onClick(View view) {
/* An 'if else' statement has been used to check that if the four buttons on the screens (choices) are equal to an empty string, then this means that the quiz has
finished and the user's score can be displayed to the user and stored in the Firebase database using the 'sendScore' method. Otherwise if the user selects the 'click'
button during the quiz, a message is displayed alerting them to continue as the quiz has not yet finished. The purpose of this is because at the end of the quiz, null
data would be displayed in the choice buttons and if clicked on the score would increment. Therefore I have solved this by using this 'if else' statement. */
                if (sChoice1.equals("") && (sChoice2.equals("") && (sChoice3.equals("") && (sChoice4.equals(""))))) {
                    Toast.makeText(StudentCompleteQuiz.this, userName + " " + "you have finished the quiz!", Toast.LENGTH_SHORT).show();
                    click.setText("MY SCORE: " + userScore);
                    sendScore();
                    click.setEnabled(false);
                }else{
                    Toast.makeText(StudentCompleteQuiz.this, "Keep going, almost there!", Toast.LENGTH_SHORT).show();
                }
            }
        } );

/* Event listeners have been used. These methods are called when the 'view' has been triggered by the user's interaction with the 'choice1', 'choice2', 'choice3' and
'choice4' button. The callback method 'View.OnClickListener' has been used so that when the user touches the button, the remaining lines of code are executed. */
/* When each button is clicked, an 'if else' statement is carried out where if the button is equal to an empty string, this means that the quiz has finished, as the
button contains null data. However if the button is not empty, the text inside the button is retrieved and compared to the current 'answer' variable. If the answer
is correct, the score is incremented and the 'updateScore' method is accessed which passes the 'mscore' variable. The user is then redirected to the 'retrieveQuestions'
method. If the user did not get the answer right, their score is not incremented or updated, instead they are redirected back to 'retrieveQuestions'. */
        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sChoice1.equals("")){
                    Toast.makeText(StudentCompleteQuiz.this, "Press 'Click Here'", Toast.LENGTH_SHORT).show();
                }else if(choice1.getText().equals(answer.getText())){
                    userScore = userScore + 1;
                    updateScore(userScore);
                    Toast.makeText(StudentCompleteQuiz.this, "Correct!", Toast.LENGTH_SHORT).show();
                    retrieveQuestions();
                }else{
                    Toast.makeText(StudentCompleteQuiz.this, "Incorrect.", Toast.LENGTH_SHORT).show();
                    retrieveQuestions();
                }
            }
        } );

        choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sChoice2.equals("")){
                    Toast.makeText(StudentCompleteQuiz.this, "Press 'Click Here'", Toast.LENGTH_SHORT).show();
                }else if(choice2.getText().equals(answer.getText())){
                    userScore = userScore + 1;
                    updateScore(userScore);
                    Toast.makeText(StudentCompleteQuiz.this, "Correct!", Toast.LENGTH_SHORT).show();
                    retrieveQuestions();
                }else{
                    Toast.makeText(StudentCompleteQuiz.this, "Incorrect.", Toast.LENGTH_SHORT).show();
                    retrieveQuestions();
                }
            }
        } );

        choice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sChoice3.equals("")){
                    Toast.makeText(StudentCompleteQuiz.this, "Press 'Click Here'", Toast.LENGTH_SHORT).show();
                }else if(choice3.getText().equals( answer.getText() )) {
                    userScore = userScore + 1;
                    updateScore(userScore);
                    Toast.makeText(StudentCompleteQuiz.this, "Correct!", Toast.LENGTH_SHORT).show();
                    retrieveQuestions();
                }else{
                    Toast.makeText(StudentCompleteQuiz.this, "Incorrect.", Toast.LENGTH_SHORT).show();
                    retrieveQuestions();
                }
            }
        } );

        choice4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sChoice4.equals("")){
                    Toast.makeText(StudentCompleteQuiz.this, "Press 'Click Here'", Toast.LENGTH_SHORT).show();
                }else if(choice4.getText().equals( answer.getText() )){
                    userScore = userScore + 1;
                    updateScore(userScore);
                    Toast.makeText(StudentCompleteQuiz.this, "Correct!", Toast.LENGTH_SHORT).show();
                    retrieveQuestions();
                }else{
                    Toast.makeText(StudentCompleteQuiz.this, "Incorrect.", Toast.LENGTH_SHORT).show();
                    retrieveQuestions();
                }
            }
        } );
    }

    private void setUpUIViews() {
/* All of the variables are assigned to their corresponding ID attribute (widget) from the activity_student_complete_quiz.xml file. */
        qNumber = (TextView)findViewById(R.id.tvQuestionNumber);
        question = (TextView)findViewById(R.id.tvQuestion);
        choice1 = (Button)findViewById(R.id.etChoice1);
        choice2 = (Button)findViewById(R.id.etChoice2);
        choice3 = (Button)findViewById(R.id.etChoice3);
        choice4 = (Button)findViewById(R.id.etChoice4);
        answer = (TextView)findViewById(R.id.tvAnswer);
        viewScore = (TextView)findViewById(R.id.tvScore);
        click = (Button)findViewById(R.id.btnClickHere);
    }

    private void retrieveQuestions() {
/* This method is used to retrieve the questions from the Firebase database. */
/* The object 'rootRef' gets the reference of the database and points to the root of the Firebase database. */
        rootRef = FirebaseDatabase.getInstance().getReference();
/* The object 'nodeRef' is the child of 'rootRef' and is now able to access the question by using '.child("Question Number " + questionNumber)'. */
        nodeRef = rootRef.child( String.valueOf("Question Number " + questionNumber ));

/* After the two objects have been instantiated, the program then retrieves the reference to the following nodes 'questionNumber', 'question', 'choice1', 'choice2',
'choice3', 'choice4' and 'answer' by using 'nodeRef.child' to access the particular node. */
/* The data read from each node is stored as a string variable and stored in the corresponding TextView widget. */
        nodeRef.child(String.valueOf("questionNumber" )).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dbqnumber = dataSnapshot.getValue(String.class);
                qNumber.setText(dbqnumber);
                sChoice = qNumber.getText().toString();
                if(sChoice.length() == 0){
                    retrieveQuestions();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        } );

        nodeRef.child("question").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dbquestion = dataSnapshot.getValue(String.class);
                question.setText("Question: " + dbquestion);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        } );

        nodeRef.child("choice1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dbchoice1 = dataSnapshot.getValue(String.class);
                choice1.setText(dbchoice1);
                sChoice1 = choice1.getText().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        } );

        nodeRef.child("choice2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dbchoice2 = dataSnapshot.getValue(String.class);
                choice2.setText(dbchoice2);
                sChoice2 = choice2.getText().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        } );

        nodeRef.child("choice3").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dbchoice3 = dataSnapshot.getValue(String.class);
                choice3.setText(dbchoice3);
                sChoice3 = choice3.getText().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        } );

        nodeRef.child("choice4").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dbchoice4 = dataSnapshot.getValue(String.class);
                choice4.setText(dbchoice4);
                sChoice4 = choice4.getText().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        } );

        nodeRef.child("answer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dbanswer = dataSnapshot.getValue(String.class);
                answer.setText(dbanswer);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        } );

        questionNumber = questionNumber + 1;
    }

    private void updateScore(int scoreUpdate) {
/* This method is used to update the user's score. The TextView 'viewScore' is updated. */
        viewScore.setText("Score: " + userScore);
    }

    private void sendScore() {
/* This method is used to retrieve the questions from the Firebase database. */
/* The object 'rootRef' gets the reference of the database and points to the root of the Firebase database. */
        rootRef = FirebaseDatabase.getInstance().getReference();
/* The object 'nodeRef' is the child of 'rootRef' and is now able to access the user's information by using '.child(UserName)'. */
        nodeRef = rootRef.child(userName);
/* The 'counter' refers to the number of quizzes completed. */
/* After the two objects have been instantiated, the program then retrieves the reference to the 'exists' node which was created in the CreateAnAccount.java class.
To access this particular node I have used 'nodeRef.child'. The purpose of this is to determine whether the user has yet completed their first quiz. If not, then
a 'counter' is created and stored in the Firebase database to monitor how many times the student has completed the quiz and each time the student enters the app and
completes the quiz, this counter value is retrieved and added onto. For example if the current value in Firebase is 5 and the user completes the quiz again, this counter
will increment by 1 and be stored as 6. This has been implemented to solve a problem where the number of quizzes the student completed was not saved. */
/* The data read from the node is stored as a string variable. */
        nodeRef.child("exists").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String existsStatus = dataSnapshot.getValue(String.class);

/* An 'if else' statement has been used because if the string value retrieved from the node 'exists' is equal to 'false', this means that the user has not yet completed
their first quiz. Therefore, a node called 'counter' in the Firebase database is overwritten because at the moment it is an empty string. The variable 'counter'
overwrites this node which is '0'. Afterwards, this node is retrieved and stored as the variable 'counter' which is incremented by 1. In addition, the 'score', 'counter'
and 'exists' node is updated. Now that the user has completed their first quiz, the counter is increased by 1 as they have completed one quiz. The 'exists' node is
updated to 'true' because they have now completed a quiz. */
                if(String.valueOf(existsStatus).equals("false")){
                    rootRef = FirebaseDatabase.getInstance().getReference();
                    nodeRef = rootRef.child(userName);
                    nodeRef.child("counter").setValue(String.valueOf(counter));
                    nodeRef.child(String.valueOf("counter")).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String dbcounter = dataSnapshot.getValue(String.class);
                            counter = Integer.valueOf(dbcounter);
                            rootRef = FirebaseDatabase.getInstance().getReference();
                            nodeRef = rootRef.child(userName);
                            counter = counter + 1;
                            nodeRef.child(userName + "score").child("score" + counter).setValue(String.valueOf(userScore));
                            nodeRef.child("counter").setValue(String.valueOf(counter));
                            nodeRef.child("exists").setValue("true");
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    } );
/* However if the 'exists' node is equal to 'true' this means that the user has already completed a quiz and the above steps are repeated. */
                }else if(String.valueOf(existsStatus).equals("true")){
                    rootRef = FirebaseDatabase.getInstance().getReference();
                    nodeRef = rootRef.child(userName);
                    nodeRef.child( String.valueOf("counter")).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String dbcounter = dataSnapshot.getValue(String.class);
                            counter = Integer.valueOf(dbcounter);
                            rootRef = FirebaseDatabase.getInstance().getReference();
                            nodeRef = rootRef.child(userName);
                            counter = counter + 1;
                            nodeRef.child(userName + "score").child("score" + counter).setValue(String.valueOf(userScore));
                            nodeRef.child("counter").setValue(String.valueOf(counter));
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    } );
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}