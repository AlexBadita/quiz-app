package com.app.quizapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.quizapp.data.Questions;
import com.app.quizapp.data.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class QuestionActivity extends AppCompatActivity {
    private final int numberOfQuestions = 10;

    private Drawable unselectedAnswerBg;
    private Drawable selectedAnswerBg;
    private Drawable wrongAnswerBg;
    private Drawable rightAnswerBg;

    private Drawable unselectedAnswerCircle;
    private Drawable selectedAnswerCircle;
    private Drawable wrongAnswerCircle;
    private Drawable rightAnswerCircle;

    private int unselectedAnswerColor;
    private int selectedAnswerColor;
    private int wrongAnswerColor;
    private int rightAnswerColor;

    private Drawable statusDefault;
    private Drawable statusCurrent;
    private Drawable statusWrong;
    private Drawable statusRight;

    private Questions questions = new Questions();
    private List<Question> selectedQuestions = new ArrayList<>();

    private ImageView backBtn;
    private TextView timer;
    private TextView currentQuestion;
    private TextView question;

    private List<ConstraintLayout> answersLayout = new ArrayList<>();
    private List<TextView> answersText = new ArrayList<>();
    private List<ImageView> answersCircle = new ArrayList<>();

    private AppCompatButton skipBtn;
    private AppCompatButton confirmBtn;

    private List<ImageView> statuses = new ArrayList<>();

    private int currentIndex = 0;
    private int[] questionsStatus = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    private int remainingQuestions = 10;
    private int selectedAnswer = -1;
    private int correctAnswers = 0;

    private String userName;
    private String category;

    private long timeLeftMillis = 300000; // 5 min
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        userName = getIntent().getStringExtra("user_name");
        category = getIntent().getStringExtra("category");

        setIdReferences();
        setConstants();
        selectQuestions();

        for(int i = 0; i < 4; i++){
            int index = i;
            answersLayout.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectAnswer(index);
                }
            });
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skip();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
            }
        });

        startTimer(timeLeftMillis);
    }

    private void setIdReferences(){
        backBtn = findViewById(R.id.back_btn);
        timer = findViewById(R.id.timer);
        currentQuestion = findViewById(R.id.question_nr);
        question = findViewById(R.id.question);

        answersLayout.add(findViewById(R.id.answer_1));
        answersLayout.add(findViewById(R.id.answer_2));
        answersLayout.add(findViewById(R.id.answer_3));
        answersLayout.add(findViewById(R.id.answer_4));

        answersText.add(findViewById(R.id.answer_1_text));
        answersText.add(findViewById(R.id.answer_2_text));
        answersText.add(findViewById(R.id.answer_3_text));
        answersText.add(findViewById(R.id.answer_4_text));

        answersCircle.add(findViewById(R.id.answer_1_check));
        answersCircle.add(findViewById(R.id.answer_2_check));
        answersCircle.add(findViewById(R.id.answer_3_check));
        answersCircle.add(findViewById(R.id.answer_4_check));

        skipBtn = findViewById(R.id.skip_btn);
        confirmBtn = findViewById(R.id.confirm_btn);

        statuses.add(findViewById(R.id.status_1));
        statuses.add(findViewById(R.id.status_2));
        statuses.add(findViewById(R.id.status_3));
        statuses.add(findViewById(R.id.status_4));
        statuses.add(findViewById(R.id.status_5));
        statuses.add(findViewById(R.id.status_6));
        statuses.add(findViewById(R.id.status_7));
        statuses.add(findViewById(R.id.status_8));
        statuses.add(findViewById(R.id.status_9));
        statuses.add(findViewById(R.id.status_10));
    }

    private void setConstants(){
        unselectedAnswerBg = ContextCompat.getDrawable(this, R.drawable.rounded_answer_bg);
        selectedAnswerBg = ContextCompat.getDrawable(this, R.drawable.selected_rounded_bg);
        wrongAnswerBg = ContextCompat.getDrawable(this, R.drawable.wrong_rounded_bg);
        rightAnswerBg = ContextCompat.getDrawable(this, R.drawable.right_rounded_bg);

        unselectedAnswerCircle = ContextCompat.getDrawable(this, R.drawable.circle_empty);
        selectedAnswerCircle = ContextCompat.getDrawable(this, R.drawable.circle_full);
        wrongAnswerCircle = ContextCompat.getDrawable(this, R.drawable.circle_wrong);
        rightAnswerCircle = ContextCompat.getDrawable(this, R.drawable.circle_right);

        unselectedAnswerColor = getResources().getColor(R.color.medium_grey);
        selectedAnswerColor = getResources().getColor(R.color.selected_answer);
        wrongAnswerColor = getResources().getColor(R.color.wrong_answer);
        rightAnswerColor = getResources().getColor(R.color.right_answer);

        statusDefault = ContextCompat.getDrawable(this, R.drawable.default_status);
        statusCurrent = ContextCompat.getDrawable(this, R.drawable.status_current);
        statusWrong = ContextCompat.getDrawable(this, R.drawable.status_wrong);
        statusRight = ContextCompat.getDrawable(this, R.drawable.status_right);
    }

    private void selectQuestions(){
        Random random = new Random();
        List<Question> allQuestions = questions.getQuestions();
        for(int i = 0; i < numberOfQuestions; i++){
            int randomIndex = random.nextInt(allQuestions.size());
            Question randomQuestion = allQuestions.get(randomIndex);
            allQuestions.remove(randomQuestion);
            shuffleAnswers(randomQuestion);
            selectedQuestions.add(randomQuestion);
        }
        setCurrentQuestion();
    }

    private void shuffleAnswers(Question question){
        List<String> answers = new ArrayList<>();
        answers.add(question.getOptions1());
        answers.add(question.getOptions2());
        answers.add(question.getOptions3());
        answers.add(question.getOptions4());

        int rightAnswerIndex = question.getCorrectAnswer() - 1;
        String rightAnswer = answers.get(rightAnswerIndex);

        Collections.shuffle(answers);
        question.setOptions1(answers.get(0));
        question.setOptions2(answers.get(1));
        question.setOptions3(answers.get(2));
        question.setOptions4(answers.get(3));

        for(int j = 0; j < 4; j++){
            if(rightAnswer.equals(answers.get(j))){
                question.setCorrectAnswer(j);
                break;
            }
        }
    }

    private Question getCurrentQuestion(){
        return selectedQuestions.get(currentIndex);
    }

    private void setCurrentQuestion(){
        Question q = getCurrentQuestion();

        String currQuestion = "Question" + (currentIndex + 1);
        currentQuestion.setText(currQuestion);
        question.setText(q.getQuestion());
        answersText.get(0).setText(q.getOptions1());
        answersText.get(1).setText(q.getOptions2());
        answersText.get(2).setText(q.getOptions3());
        answersText.get(3).setText(q.getOptions4());

        statuses.get(currentIndex).setImageDrawable(statusCurrent);
    }

    private void startTimer(long millisLeft){
        countDownTimer = new CountDownTimer(millisLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftMillis = millisUntilFinished;
                updateCountDownTimer();
            }

            @Override
            public void onFinish() {
                timeLeftMillis = 0;
                updateCountDownTimer();
                disableButtons();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        goToResults();
                    }
                }, 1000);
            }
        }.start();
    }

    public void pauseTimer(){
        countDownTimer.cancel();
    }

    public void resumeTimer(){
        startTimer(timeLeftMillis);
    }

    public void updateCountDownTimer(){
        int minutes = (int)(timeLeftMillis / 1000) / 60;
        int seconds = (int)(timeLeftMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        timer.setText(timeFormatted);
    }

    public void disableButtons(){
        backBtn.setClickable(false);
        skipBtn.setClickable(false);
        confirmBtn.setClickable(false);
    }

    public void enableButtons(){
        backBtn.setClickable(true);
        skipBtn.setClickable(true);
        confirmBtn.setClickable(true);
    }

    private void selectAnswer(int index){
        for(int i = 0; i < 4; i++){
            if(i == index){
                setAnswer(index, selectedAnswerBg, selectedAnswerColor, selectedAnswerCircle);
            } else{
                setAnswer(i, unselectedAnswerBg, unselectedAnswerColor, unselectedAnswerCircle);
            }
        }
        selectedAnswer = index;
    }

    private void setAnswer(int index, Drawable layoutBg, int color, Drawable circle){
        answersLayout.get(index).setBackground(layoutBg);
        answersText.get(index).setTextColor(color);
        answersCircle.get(index).setBackground(circle);
    }

    private void back(){
        pauseTimer();
        AlertDialog.Builder alert = new AlertDialog.Builder(QuestionActivity.this);
        alert.setMessage("Are you sure you want to go back? Your progress will be lost");
        alert.setCancelable(false);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(QuestionActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resumeTimer();
            }
        });
        alert.create();
        alert.show();
    }

    private void skip(){
        if(remainingQuestions > 1){
            statuses.get(currentIndex).setImageDrawable(statusDefault);
            if(selectedAnswer > -1){
                setAnswer(selectedAnswer, unselectedAnswerBg, unselectedAnswerColor, unselectedAnswerCircle);
            }
            calculateNextQuestionNr();
            setCurrentQuestion();
            selectedAnswer = -1;
        }
    }

    private void confirm(){
        if(selectedAnswer == -1){
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
        } else{
            int correctAnswer = getCurrentQuestion().getCorrectAnswer();
            final Handler handler = new Handler();
            if(remainingQuestions == 1){
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        goToResults();
                    }
                }, 1000);
            } else{
                if(selectedAnswer == correctAnswer){
                    setAnswer(selectedAnswer, rightAnswerBg, rightAnswerColor, rightAnswerCircle);
                    statuses.get(currentIndex).setImageDrawable(statusRight);
                    correctAnswers++;
                } else {
                    setAnswer(selectedAnswer, wrongAnswerBg, wrongAnswerColor, wrongAnswerCircle);
                    setAnswer(correctAnswer, rightAnswerBg, rightAnswerColor, rightAnswerCircle);
                    statuses.get(currentIndex).setImageDrawable(statusWrong);
                }
                remainingQuestions--;
                questionsStatus[currentIndex] = 0;
                calculateNextQuestionNr();
                disableButtons();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        enableButtons();
                        setAnswer(selectedAnswer, unselectedAnswerBg, unselectedAnswerColor, unselectedAnswerCircle);
                        setAnswer(correctAnswer, unselectedAnswerBg, unselectedAnswerColor, unselectedAnswerCircle);
                        setCurrentQuestion();
                        selectedAnswer = -1;
                    }
                }, 1000);
            }
        }
    }

    private void calculateNextQuestionNr(){
        if(remainingQuestions > 0){
            do{
                currentIndex++;
                if(currentIndex == numberOfQuestions){
                    currentIndex = 0;
                }
            } while (questionsStatus[currentIndex] == 0);
        }
    }

    private void goToResults(){
        Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
        intent.putExtra("correct_answers", correctAnswers + "");
        intent.putExtra("user_name", userName);
        startActivity(intent);
        finish();
    }
}