package com.app.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    private String userName;
    private String correctAnswers;

    private TextView username;
    private TextView result;
    private AppCompatButton backBtn;
    private AppCompatButton againBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();

        userName = intent.getStringExtra("user_name");
        correctAnswers = intent.getStringExtra("correct_answers") + " ";

        username = findViewById(R.id.username);
        username.setText(userName);
        result = findViewById(R.id.score);
        result.setText(correctAnswers);
        backBtn = findViewById(R.id.back);
        againBtn = findViewById(R.id.again);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        againBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, QuestionActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}