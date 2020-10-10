/* This is the package name. */
package com.example.healthylivingapp;
/* This class is used to allow the question to be stored in the Firebase database. */
public class QuizQuestionandAnswer {
    public String questionNumber;
    public String question;
    public String choice1;
    public String choice2;
    public String choice3;
    public String choice4;
    public String answer;
    public String exists;

/* A constructor is created. This constructor is a method which has the same name as the above public class. When an object of the QuizQuestionandAnswer is created,
this method will be executed. */
    public QuizQuestionandAnswer(String questionNumber, String question, String choice1, String choice2, String choice3, String choice4, String answer, String exists) {
        this.questionNumber = questionNumber;
        this.question = question;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
        this.answer = answer;
        this.exists = exists;
    }
}