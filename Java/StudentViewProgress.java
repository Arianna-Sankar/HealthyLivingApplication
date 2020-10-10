/* This is the package name. */
package com.example.healthylivingapp;

/* These are the Android classes used in the StudentViewProgress.java class. */
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/* These are the Android classes which have been imported in order for the app to connect to the Firebase realtime database and to access the bar chart library. */
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

/* Inheritance is used as it allows the StudentViewProgress.java class to inherit the public and protected methods and variables from the superclass and to override
methods. 'AppCompatActivity' is the base class for activities which uses the support library action bar feature. */
public class StudentViewProgress extends AppCompatActivity {
/* I have created private variables which define all of the widgets used in the activity_student_view_progress.xml file. */
    private TextView displayname, scoreNumber, viewscore, comment, graphMessage;
    private Button view;
    private String getUserName;
    private int i, counter;

/* I have created two objects of 'DatabaseReference' which will be used to read data from specific locations in the Firebase database. */
    DatabaseReference rootRef, nodeRef;
/* I have created an object of 'BarChart' which will be used to create the bar chart. */
    BarChart barChart;

/* This implements the 'onCreate()' method from the Activity class. This method is called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/* This specifies which layout is to be used, in this case the activity_student_view_progress.xml file. */
        setContentView(R.layout.activity_student_view_progress);
/* I have requested for the screen orientation to be portrait. */
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

/* I have retrieved the user's username from the StudentHomepage.java class. This has been achieved by using 'getIntent().getStringExtra("username")'. The use of
"username", is a reference which I created in the StudentHomepage.java class, therefore by calling this reference I am able to retrieve the username object. The object
is stored in the string variable 'userName'. I then added this string to the TextView 'UserName'. */
        Intent intent = getIntent();
        String userName = getIntent().getStringExtra("username");
        getUserName = userName;

/* The methods 'setUpUIViews' and 'getUserInfo' is accessed. */
        setUpUIViews();
        getUserInfo();
/* The 'view' button has now been enabled. */
        view.setEnabled(true);

/* An event listener has been used. This method is called when the 'view' has been triggered by the user's interaction with the 'view' button. The callback
method 'View.OnClickListener' has been used so that when the user touches this button, the remaining lines of code are executed. */
        view.setOnClickListener(new View.OnClickListener() {
            @Override
/* 'public void' means that the method is visible and can be called from other objects. The use of 'void' means that this method has no return value and is called
when the button is touched.*/
            public void onClick(View view) {
/* The method 'displayScores' is accessed. */
                displayScores();
            }
        } );
    }

    private void setUpUIViews() {
/* All of the variables are assigned to their corresponding ID attribute (widget) from the activity_student_view_progress.xml file. */
        displayname = (TextView)findViewById(R.id.tvDisplayName);
        scoreNumber = (TextView)findViewById(R.id.tvDisplayNumberOfScores);
        viewscore= (TextView)findViewById(R.id.tvViewScore);
        view = (Button) findViewById(R.id.btnViewScores);
        comment = (TextView)findViewById(R.id.tvViewcomments);
        barChart = (BarChart)findViewById(R.id.bargraph);
        graphMessage = (TextView)findViewById(R.id.tvGraphMessage);
    }

    private void getUserInfo(){
/* The object 'rootRef' gets the reference of the database and points to the root of the Firebase database. */
        rootRef = FirebaseDatabase.getInstance().getReference();
/* The object 'nodeRef' is the child of 'rootRef' and is now able to access the user's information by using '.child(UserName)'. */
        nodeRef = rootRef.child(getUserName);

/* After the two objects have been instantiated, the program then retrieves the reference to the following nodes 'userName', 'counter' and 'Teacher's Comment' by using
'nodeRef.child' to access these particular nodes. The value is then stored as a string variable. */
        nodeRef.child("userName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dbusername = dataSnapshot.getValue(String.class);
                displayname.append(dbusername);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        nodeRef.child("counter").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dbquiznumber = dataSnapshot.getValue(String.class);
                counter = Integer.valueOf(dbquiznumber);
                scoreNumber.append(dbquiznumber);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
/* Referring back to the CreateAnAccount.java class, I created the node 'teacherComment' so that in this class if the teacher has not left a comment, then the
initial value stored for this node is displayed in the TextView 'comment' widget. This was to solve a problem where if the teacher did not leave a comment and the node
was not created in the CreateAnAccount.java class, then this class would crash because there would be no data appended to this TextView. */
        nodeRef.child("teacherComment").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dbcomment = dataSnapshot.getValue(String.class);
                comment.append(dbcomment);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void displayScores(){
/* The integer variable 'i' is incremented by 1. */
        i = i + 1;
/* The object 'rootRef' gets the reference of the database and points to the root of the Firebase database. */
        rootRef = FirebaseDatabase.getInstance().getReference();
/* The object 'nodeRef' is the child of 'rootRef' and is now able to access the user's information by using '.child(UserName)'. */
        nodeRef = rootRef.child(getUserName);

/* After the two objects have been instantiated, the program then retrieves the reference to the node 'UserName + "score"' and then to '"score" + i' to access this
particular node. This follows the JSON hierarchy data structure. */
        nodeRef.child(getUserName + "score").child("score" + i).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String result = dataSnapshot.getValue(String.class);
/* The program then retrieves the reference to the node 'UserName + "score"' and then to '"score1"' to access this particular node. This follows the JSON hierarchy
data structure. The value retrieved is stored as the string variable 'firstQuizScore' which will be the first bar in the bar chart. */
                nodeRef.child(getUserName + "score").child("score1").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String firstQuizScore = dataSnapshot.getValue(String.class);
/* The program then retrieves the reference to the node 'UserName + "score"' and then to '"score" + counter' to access this particular node. This follows the JSON
hierarchy data structure. The value retrieved is stored as the string variable 'lastQuizScore' which will be the last bar in the bar chart. 'counter' has been used
because this stores the total number of quizzes completed thus the most recent score. */
                        nodeRef.child(getUserName + "score").child("score" + counter).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                final String lastQuizScore = dataSnapshot.getValue(String.class);
/* If the variable 'result' is empty (in other words null), then the graph is ready to be produced. */
                                if(TextUtils.isEmpty(result)){

/* The 'view' button is now disenabled because if this was not included it would cause the app to crash as null data would be sent to the graph. */
                                    view.setEnabled(false);
/* The bar chart is now generated using the two variables retrieved from the Firebase database. An ArrayList is used which stores this data. These two bars (variables)
are positioned at index 0 and 1. The label 'Score Achieved' is added to the graph to clearly inform the user what the graph is showing. */
                                    graphMessage.setVisibility(View.VISIBLE);
                                    ArrayList<BarEntry> barEntries = new ArrayList<>();

                                    barEntries.add( new BarEntry( Float.parseFloat(firstQuizScore),0  ));
                                    barEntries.add( new BarEntry(( Float.parseFloat(lastQuizScore)) ,1  ));
                                    BarDataSet barDataSet = new BarDataSet(barEntries, "Score Achieved");
/* At the top of each bar the scores are added to inform the user what value the bar represents. */
                                    ArrayList<String> scoreNumber = new ArrayList<>();
                                    scoreNumber.add("Quiz 1");
                                    scoreNumber.add("Quiz " + (counter));
                                    BarData theData = new BarData(scoreNumber,barDataSet);
/* Below I have specified the attributes for the graph such as the width and colours of the bar. I have also not allowed zooming in or out on the graph as this makes
the graph become difficult to view. */
                                    barDataSet.setBarSpacePercent(5f);
                                    barDataSet.setColor(Color.RED);
                                    barChart.setPinchZoom(false);
                                    barChart.setData(theData);
/* However, if the variable 'result' is not empty (in other words there are still integers being produced), then the graph is not ready to be displayed but the 'result'
is shown on the screen instead. */
                                }else{
                                    viewscore.append("Quiz " + i + " = " + result + ", ");
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        } );
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                } );
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        } );
    }
}