/* This is the package name. */
package com.example.healthylivingapp;

/* These are the Android classes used in the Login.java class. */
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/* These are the Android classes which have been imported in order for the app to connect to the Firebase realtime database. */
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/* Inheritance is used as it allows the Login.java class to inherit the public and protected methods and variables from the superclass and to override methods.
'AppCompatActivity' is the base class for activities which uses the support library action bar feature. */
public class Login extends AppCompatActivity {
/* I have created private variables which define all of the widgets used in the activity_login.xml file. */
    private EditText userName, email, password;
    private TextView displayUserType;
    private Button login, createAccount;
    private String passUserName;

/* To get the instance of Firebase authentication, an object is declared. The variable 'firebaseAuth' is the object of 'FirebaseAuth'. */
    private FirebaseAuth firebaseAuth;
/* I have created two objects of 'DatabaseReference' which will be used to read data from specific locations in the Firebase database. */
    DatabaseReference rootRef, nodeRef;

/* This implements the 'onCreate()' method from the Activity class. This method is called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/* This specifies which layout is to be used, in this case the activity_login.xml file. */
        setContentView(R.layout.activity_login);
/* I have requested for the screen orientation to be portrait. */
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
/* The method 'setUpUIViews' is accessed. */
        setUpUIViews();
/* The instance of Firebase authentication is retrieved. */
        firebaseAuth = FirebaseAuth.getInstance();

/* Event listeners have been used. These methods are called when the 'view' has been triggered by the user's interaction with the 'createAccount' and 'login' button.
The callback method 'View.OnClickListener' has been used so that when the user touches the button, the remaining lines of code are executed. */
/* 'public void' means that the method is visible and can be called from other objects. The use of 'void' means that this method has no return value and is called
when the button is touched. 'Intent' is used which is a messaging object that requests an action from another activity. */
/* 'startActivity' represents a single screen in an app. I have started a new instance of activity by passing an 'intent' to start the activity. An 'intent'
describes what activity will be carried out next. */
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,CreateAnAccount.class);
                startActivity(intent);
            }
        });

/* In this method, encapsulation is used as I have created string variables which fetches the values in the EditText widgets entered by the user and converts them to
the data type of string. Branching is then used as the 'if else' statement checks whether the widgets on the login screen have been left blank, if so a message pops up
(using the 'Toast.makeText()' method) stating that the user must enter all details otherwise they are directed to the 'validate' method where their email and password
are passed as parameters. */
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String check_name = userName.getText().toString();
                String check_email = email.getText().toString();
                String check_password = password.getText().toString();
                if(check_name.isEmpty() || check_email.isEmpty() || check_password.isEmpty()){
                    Toast.makeText(Login.this, "Please Enter Your Login Details", Toast.LENGTH_SHORT).show();
                }else{
                    validate(email.getText().toString(), password.getText().toString());
                }
            }
        });
    }

    private void setUpUIViews(){
/* All of the variables are assigned to their corresponding ID attribute (widget) from the activity_login.xml file. */
        userName = (EditText)findViewById(R.id.etUserName);
        email = (EditText)findViewById(R.id.etEmail);
        password = (EditText)findViewById(R.id.etPassword);
        displayUserType = (TextView)findViewById(R.id.tvUserType);
        login = (Button)findViewById(R.id.btnLogin);
        createAccount = (Button)findViewById(R.id.btnCreateAccount);
    }

/* The method 'validate' is executed once the user has entered all details and activated the 'login' button. The Firebase database is accessed and the user's email
and password is passed to the 'signInWithEmailAndPassword' method. */
    private void validate(final String email, final String password){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    DatabaseReference Reference = FirebaseDatabase.getInstance().getReference();
                    Reference.child(userName.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
/* An 'if else' statement is used so if the username exists, the user's email address entered is compared to the email address they signed up with. If they match,
then the method 'checkUserType()' is executed to determine which homepage the user is directed to. */
                            if (dataSnapshot.exists()){
                                rootRef = FirebaseDatabase.getInstance().getReference();
/* The object 'nodeRef' is the child of 'rootRef' and is now able to access the user's information by using '.child(userName.getText().toString())'. */
                                nodeRef = rootRef.child(userName.getText().toString());
                                nodeRef.child(String.valueOf("userEmail")).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String userEmail = dataSnapshot.getValue(String.class);
                                        if(userEmail.equals(email.toString())){
/* The method 'checkUserType' is then called to determine which homepage is displayed. */
                                            checkUserType();
/* After the above method has been executed, a message is displayed to inform the user that their username, email and password have been found in the Firebase database. */
                                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        }else{
/* If the email addresses do not match, then an error message is displayed to the user. */
                                            Toast.makeText(Login.this, "This account does not exist", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                            }else{
/* If the username does not exist in the Firebase database, an error message is displayed to the user. */
                                Toast.makeText(Login.this, "This account does not exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }else{
/* If the email address and password does not exist in the Firebase database, an error message is displayed to the user. */
                    Toast.makeText(Login.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkUserType(){
/* This method is used to check the user type access level to determine which homepage the user is directed to. The username entered in the EditText widget is
stored as the string variable 'passUserName'. */
        passUserName = userName.getText().toString();
/* The object 'rootRef' gets the reference of the database and points to the root of the Firebase database. */
        rootRef = FirebaseDatabase.getInstance().getReference();
/* The object 'nodeRef' is the child of 'rootRef' and is now able to access the user's information by using '.child(passUserName)'. */
        nodeRef = rootRef.child(passUserName);
/* After the two objects have been instantiated, the program then retrieves the reference to the node 'userType' by using 'nodeRef.child' to access this particular
node. */
        nodeRef.child("userType").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userType = dataSnapshot.getValue(String.class);
/* The data read from the 'userType' node is stored as a string variable 'userType'. This is then stored in the 'displayUserType' TextView widget. */
                displayUserType.setText("You are a: " + userType);
/* An 'if else' statement is used so that if the value fetched from the database is equal to 'Teacher', then the teacher's homepage is displayed using an 'Intent'.
Otherwise the user is directed to the student's homepage using an 'Intent'. However the user's username is also passed to the student's homepage as this will later be
used for the student's quiz and progress feature. This has been implemented using 'intent.putExtra' to send a copy of the object between the two classes, Login.java
and StudentHomepage.java. Note, "username" has been used to provide a reference so that from the student's homepage, the object is retrieved by referencing "username". */
                if(userType.equals("Teacher")){
                    startActivity(new Intent(Login.this, TeacherHomepage.class) );
                }else{
                    Intent intent = new Intent(getApplicationContext(), StudentHomepage.class);
                    intent.putExtra("username", passUserName);
                    startActivity(intent);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}