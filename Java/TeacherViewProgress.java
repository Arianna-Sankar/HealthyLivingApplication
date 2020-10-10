/* This is the package name. */
package com.example.healthylivingapp;

/* These are the Android classes used in the TeacherViewProgress.java class. */
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/* These are the Android classes which have been imported in order for the app to connect to the Firebase realtime database. */
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/* Inheritance is used as it allows the TeacherViewProgress.java class to inherit the public and protected methods and variables from the superclass and to override
methods. 'AppCompatActivity' is the base class for activities which uses the support library action bar feature. */
public class TeacherViewProgress extends AppCompatActivity {
/* I have created private variables which define all of the widgets used in the activity_teacher_view_progress.xml file. */
    private EditText userName, comment;
    private TextView displayName, displayEmail, scoreNumber, viewScore;
    private Button search, view, send;
    private String findStudent;
    private int i;

/* I have created two objects of 'DatabaseReference' which will be used to read data from specific locations in the Firebase database. */
    DatabaseReference rootRef, nodeRef;

/* This implements the 'onCreate()' method from the Activity class. This method is called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/* This specifies which layout is to be used, in this case the activity_teacher_view_progress.xml file. */
        setContentView(R.layout.activity_teacher_view_progress);
/* I have requested for the screen orientation to be portrait. */
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
/* The method 'setUpUIViews' is accessed. */
        setUpUIViews();
/* The 'view' and 'send' button have now been disenabled. */
        view.setEnabled(false);
        send.setEnabled(false);

/* Event listeners have been used for the following buttons. These methods are called when the 'view' has been triggered by the user's interaction with the 'search',
'view' and 'send' button. The callback method 'View.OnClickListener' has been used so that when the user touches the button, the remaining lines of code are executed. */
/* 'public void' means that the method is visible and can be called from other objects. The use of 'void' means that this method has no return value and is called
when the button is touched. */
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/* The method 'checkUserExists' is accessed. */
                checkUserExists();
            }
        } );

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/* The method 'displayScores' is accessed. */
                displayScores();
            }
        } );

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/* The method 'sendComments' is accessed. */
                sendComments();
            }
        } );
    }

    private void setUpUIViews() {
/* All of the variables are assigned to their corresponding ID attribute (widget) from the activity_teacher_view_progress.xml file. */
        userName = (EditText)findViewById(R.id.etSearchStudent);
        displayName = (TextView)findViewById(R.id.tvDisplayName);
        displayEmail = (TextView)findViewById(R.id.tvDisplayEmail);
        scoreNumber = (TextView)findViewById(R.id.tvDisplayNumberOfScores);
        viewScore= (TextView)findViewById(R.id.tvViewScore);
        comment = (EditText)findViewById(R.id.etSendComments);
        search = (Button)findViewById(R.id.btnSearch);
        view = (Button)findViewById(R.id.btnViewScores);
        send = (Button)findViewById(R.id.btnSendComments);
    }

    private void checkUserExists() {
/* This method is used to check that the username entered in EditText 'userName' exists. The value is retrieved from the widget and stored as the string variable
'findStudent'. An 'if else' statement is used because if 'findStudent' is equal to an empty string then this means that the widget has not been completed. */
        findStudent = userName.getText().toString();
        if(findStudent.equals("")){
            Toast.makeText(TeacherViewProgress.this, "Please enter a student's username", Toast.LENGTH_SHORT).show();
/* However if the widget is not equal to an empty string then the object 'Reference' gets the reference of the database and points to the root of the Firebase database.
The object 'Reference' checks whether the username exists in the Firebase database. */
        }else{
            DatabaseReference Reference = FirebaseDatabase.getInstance().getReference();
            Reference.child(findStudent).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
/* An 'if else' statement is used where if the username exists, then the method 'validateUserType' is accessed, otherwise an error message is displayed. */
                    if(dataSnapshot.exists()){
                        Toast.makeText(TeacherViewProgress.this,findStudent + " exists", Toast.LENGTH_SHORT).show();
                        validateUserType();
                    }else{
                        Toast.makeText(TeacherViewProgress.this,findStudent + " does not exist", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            } );
        }
    }

    private void validateUserType(){
/* This method is used to check that a student and not a teacher is being searched for as if a teacher is searched for, the app would crash because inside the
'getUserInfo' method, the program would not be able to retrieve the data from the requested nodes as these nodes are not included in the teacher's Firebase account,
only the students. The value is retrieved from the EditText 'userName' widget and stored as the string variable 'findStudent'. */
        findStudent = userName.getText().toString();
/* The object 'rootRef' gets the reference of the database and points to the root of the Firebase database. */
        rootRef = FirebaseDatabase.getInstance().getReference();
/* The object 'nodeRef' is the child of 'rootRef' and is now able to access the user's information by using '.child(findStudent)'. */
        nodeRef = rootRef.child(findStudent);

/* After the two objects have been instantiated, the program then retrieves the reference to the node 'userType' by using 'nodeRef.child' to access this particular
node. The value is then stored as a string variable. */
        nodeRef.child("userType").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userType = dataSnapshot.getValue(String.class);
/* An 'if else' statement is used so that if the user type is equal to teacher, then an error message is displayed preventing the 'getUserInfo' from being accessed.
However if the user type is equal to student then the 'send' button is enabled allowing access to the 'getUserInfo' method. */
                if(userType.equals("Teacher")){
                    Toast.makeText(TeacherViewProgress.this,findStudent + " is a teacher, not a student", Toast.LENGTH_SHORT).show();
                }else if(userType.equals("Student")){
/* The 'view' and 'send' button have now been enabled. */
                    view.setEnabled(true);
                    send.setEnabled(true);
                    getUserInfo();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getUserInfo(){
/* This method is used to retrieve the user's details. The value is retrieved from the EditText 'userName' widget and stored as the string variable 'findStudent'. */
        findStudent = userName.getText().toString();
/* The object 'rootRef' gets the reference of the database and points to the root of the Firebase database. */
        rootRef = FirebaseDatabase.getInstance().getReference();
/* The object 'nodeRef' is the child of 'rootRef' and is now able to access the user's information by using '.child(findStudent)'. */
        nodeRef = rootRef.child(findStudent);

/* After the two objects have been instantiated, the program then retrieves the reference to the following nodes 'userName', 'userEmail' and 'counter' by using
'nodeRef.child' to access these particular nodes. The values are then stored as string variables and appended to the corresponding TextView widgets. */
         nodeRef.child("userName").addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 String dbusername = dataSnapshot.getValue(String.class);
                 displayName.append(dbusername);
             }
             @Override
             public void onCancelled(DatabaseError databaseError) {
             }
         });

         nodeRef.child("userEmail").addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 String dbemail = dataSnapshot.getValue(String.class);
                 displayEmail.append(dbemail);
                 displayEmail.setTextIsSelectable(true);
             }
             @Override
             public void onCancelled(DatabaseError databaseError) {
             }
         });

         nodeRef.child("counter").addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 String dbquiznumber = dataSnapshot.getValue(String.class);
                 scoreNumber.append(dbquiznumber);
             }
             @Override
             public void onCancelled(DatabaseError databaseError) {
             }
         });
    }

    private void displayScores(){
/* This method is used to display the user's score. The value is retrieved from the EditText 'userName' widget and stored as the string variable 'findStudent'. */
        findStudent = userName.getText().toString();
/* The integer variable 'i' is incremented by 1. */
        i = i + 1;
/* The object 'rootRef' gets the reference of the database and points to the root of the Firebase database. */
        rootRef = FirebaseDatabase.getInstance().getReference();
/* The object 'nodeRef' is the child of 'rootRef' and is now able to access the user's information by using '.child(findStudent)'. */
        nodeRef = rootRef.child(findStudent);
/* After the two objects have been instantiated, the program then retrieves the reference to the node 'findStudent + "score"' and then to '"score" + i' to access this
particular node. This follows the JSON hierarchy data structure. This value is then stored as the string variable 'result' which is outputted to the user. I have used
the integer variable 'i' which is appended to the string 'Quiz' alerting the user what score they achieved for a particular quiz. */
        nodeRef.child(findStudent + "score").child("score" + i).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String result = dataSnapshot.getValue( String.class );
                viewScore.append("Quiz " + i + " = " + result + ", ");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        } );
    }

    private void sendComments(){
/* This method is used to allow teachers to send a comment to the student's account. The value is retrieved from the EditText 'userName' widget and stored as the
string variable 'findStudent'. */
        findStudent = userName.getText().toString();
/* The object 'rootRef' gets the reference of the database and points to the root of the Firebase database. */
        rootRef = FirebaseDatabase.getInstance().getReference();
/* The object 'nodeRef' is the child of 'rootRef' and is now able to access the user's information by using '.child(findStudent)'. */
        nodeRef = rootRef.child(findStudent);
/* The node 'teacherComment' is overwritten (as this was created in the CreateAnAccount.java class) and stores the value retrieved from the EditText 'comment'. */
        nodeRef.child("teacherComment").setValue(String.valueOf(comment.getText().toString()));
        Toast.makeText(this,"Comment Sent To " + findStudent + "'s account", Toast.LENGTH_SHORT).show();
    }
}