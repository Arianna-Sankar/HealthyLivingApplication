/* This is the package name. */
package com.example.healthylivingapp;

/* These are the Android classes used in the CreateAnAccount.java class. */
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Toast;

/* These are the Android classes which have been imported in order for the app to connect to the Firebase realtime database. */
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/* Inheritance is used as it allows the CreateAnAccount.java class to inherit the public and protected methods and variables from the superclass and to override methods.
'AppCompatActivity' is the base class for activities which uses the support library action bar feature. */
public class CreateAnAccount extends AppCompatActivity {
/* I have created private variables which define all of the widgets used in the activity_create_an_account.xml file. */
   private EditText userName, email, setPassword, teacherCode;
   private RadioGroup radioGroup;
   private RadioButton radioButton;
   private Button createAccount;
   private String sUserName, sEmail, sSetPassword, sUserType, sTeacherCode;

/* To get the instance of Firebase authentication, an object is declared. The variable 'firebaseAuth' is the object of 'FirebaseAuth'. */
    private FirebaseAuth firebaseAuth;
/* I have created two objects of 'DatabaseReference' which will be used to read data from specific locations in the Firebase database. */
    DatabaseReference rootRef, nodeRef;

/* This implements the 'onCreate()' method from the Activity class. This method is called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/* This specifies which layout is to be used, in this case the activity_create_an_account.xml file. */
        setContentView(R.layout.activity_create_an_account);
/* I have requested for the screen orientation to be portrait. */
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
/* The methods 'setUpUIViews' and 'validate' is accessed. */
        setUpUIViews();
        validate();
/* The instance of Firebase authentication is retrieved. */
        firebaseAuth = FirebaseAuth.getInstance();

/* An event listener has been used. This method is called when the 'view' has been triggered by the user's interaction with the 'createAccount' button. The callback
method 'View.OnClickListener' has been used so that when the user touches this button, the remaining lines of code are executed. */
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
/* 'public void' means that the method is visible and can be called from other objects. The use of 'void' means that this method has no return value and is called
when the button is touched. 'Intent' is used which is a messaging object that requests an action from another activity. */
            public void onClick(View view) {
/* In the 'if' statements below, the method 'validate' is accessed. If the result of this method is 'true', either the method 'verifyTeacher' or 'verifyStudent'
is called to check whether the user has selected teacher or student. If the result of this method is 'true', the 'verification' method is accessed. */
                if(validate()){
                    if(verifyTeacher()){
                        verification();
                    }
                }
                if(validate()){
                    if(verifyStudent()){
                        verification();
                    }
                }
            }
        });
    }

    private void setUpUIViews(){
/* All of the variables are assigned to their corresponding ID attribute (widget) from the activity_create_an_account.xml file. */
        userName = (EditText)findViewById(R.id.etUserName);
        email = (EditText)findViewById(R.id.etEmail);
        setPassword = (EditText)findViewById(R.id.etSetPassword);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        teacherCode = (EditText)findViewById(R.id.etTeacherCode);
        createAccount = (Button)findViewById(R.id.btnCreateAccount);
    }

    private Boolean validate(){
/* This method is used to ensure that all fields have been completed. The initial boolean value of 'result' has been set to 'false'. In this method, encapsulation is
used as I have created string variables which fetches the values entered by the user and converts them to the data type of string. To check that a radio button was
selected I created an integer variable 'selectedID' and if this contains '-1' this means that a radio button has not been selected. */
        Boolean result = false;
        sUserName = userName.getText().toString();
        sEmail = email.getText().toString();
        sSetPassword = setPassword.getText().toString();
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);
/* Branching is then used as the 'if else' statement checks whether the fields on this screen have been left blank, if so a message pops up (using the 'Toast.makeText()'
method) stating that the user must complete all fields. If all fields have been completed, 'result' is then set to 'true'. */
        if(sUserName.isEmpty() || sEmail.isEmpty() || sSetPassword.isEmpty() || selectedId == -1){
            Toast.makeText(this, "Please Complete All Fields", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }
        return result;
    }

    private Boolean verifyTeacher(){
/* This method is used to determine that when the user has selected the 'teacher' radio button, further security checks will be carried out by entering a teacher code
which would be distributed to teachers. This will deter students from selecting the 'teacher' radio button as they will not have access to this code. The
boolean value of 'teacher' is initially set as 'false'. The user's type and teacher code is then retrieved and stored as string variables. */
        Boolean teacher = false;
        sUserType = radioButton.getText().toString();
        sTeacherCode = teacherCode.getText().toString();
/* Branching is used as the 'if else' statement checks that if the user is a teacher and has entered the correct teacher code, in this case 'abc', then access
is allowed and the boolean value 'teacher' is set to 'true'. If the user is a teacher and they have not entered a teacher code, an error message is displayed. Another
error message is displayed if the user is a teacher but has entered an invalid teacher code. */
        if(sUserType.equals("Teacher") && sTeacherCode.equals("abc")){
            Toast.makeText(CreateAnAccount.this, "Valid teacher code", Toast.LENGTH_SHORT).show();
            teacher = true;
        }else if(sUserType.equals("Teacher") && (sTeacherCode.isEmpty())){
            Toast.makeText(CreateAnAccount.this, "Please enter teacher code", Toast.LENGTH_SHORT).show();
        }else if(sUserType.equals("Teacher") && (!sTeacherCode.equals("abc"))){
            Toast.makeText(CreateAnAccount.this, "Invalid teacher code", Toast.LENGTH_SHORT).show();
        }
/* The value of 'teacher' is returned. */
        return teacher;
    }

    private Boolean verifyStudent(){
/* This method is used to check that if a user has selected the 'student' radio button, they will not be required to enter a teacher code. The boolean value of
'student' is initially set as 'false'. The user's type is then retrieved and stored as a string variable. */
        Boolean student = false;
        sUserType = radioButton.getText().toString();
/* Branching is used as if the user is a student and has not entered a teacher code, then the boolean value 'student' is set to 'true'. */
        if(sUserType.equals("Student") && sTeacherCode.isEmpty()){
            student = true;
        }
/* The value of 'student' is returned. */
        return student;
    }

    private void verification(){
/* This method is used to verify the email and password the user has entered. Therefore, the Firebase database is accessed and the user's email and password is fetched
and converted to the data type of string. These string variables are then passed to the 'createUserWithEmailAndPassword' method. */
        String sSetEmail = email.getText().toString();
        String sSetPassword = setPassword.getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(sSetEmail, sSetPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
/* An 'if else' statement is carried out where if the task is successful a pop up message is displayed and the 'sendUserDetails' method is called. If the task is
unsuccessful, then an error message is displayed. */
                if(task.isSuccessful()){
                    Toast.makeText(CreateAnAccount.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    sendUserDetails();
                }else{
                    Toast.makeText(CreateAnAccount.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendUserDetails(){
/* When this method is executed, it creates a 'firebaseDatabase' object and then creates a reference which is the user's name. Rather than using the UID, the user's
name will be referenced which makes it easier to search for data especially in the progress feature where the teacher will need to search for their students progress. */
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference Reference = firebaseDatabase.getReference(sUserName);
/* I have assigned the values of 'sUserName', 'sEmail' and 'sUserType' to the variables used in the UserDetails.java class. */
        UserDetails userDetails = new UserDetails(sUserName, sEmail, sUserType);
/* These details will now be stored in Firebase following the JSON hierarchy data structure. */
        Reference.setValue(userDetails);

/* The object 'rootRef' gets the reference of the database and points to the root of the Firebase database. */
        rootRef = FirebaseDatabase.getInstance().getReference();
/* The object 'nodeRef' is the child of 'rootRef' and is now able to access the user's information by using '.child(sUserName)'. */
        nodeRef = rootRef.child(sUserName);
/* Three nodes are then created called 'exists' which is set to 'false', 'teacherComment' and 'counter'. These are essential for the student's homepage, quiz and
progress feature which will be explained in further detail in the StudentCompleteQuiz.java, StudentViewProgress.java/TeacherViewProgress.java and StudentHomepage.java
classes respectively. */
        nodeRef.child("exists").setValue("false");
        nodeRef.child("teacherComment").setValue("Your teacher has not yet commented.");
        nodeRef.child("counter").setValue("");
    }
}