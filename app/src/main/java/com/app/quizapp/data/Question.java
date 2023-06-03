package com.app.quizapp.data;

public class Question {
    private final int id;
    private final String question;
    private String options1;
    private String options2;
    private String options3;
    private String options4;
    private int correctAnswer;

    public Question(int id, String question, String options1, String options2, String options3, String options4, int correctAnswer){
        this.id = id;
        this.question = question;
        this.options1 = options1;
        this.options2 = options2;
        this.options3 = options3;
        this.options4 = options4;
        this.correctAnswer = correctAnswer;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getOptions1() {
        return options1;
    }

    public void setOptions1(String option){
        this.options1 = option;
    }

    public String getOptions2() {
        return options2;
    }

    public void setOptions2(String option){
        this.options2 = option;
    }

    public String getOptions3() {
        return options3;
    }

    public void setOptions3(String option){
        this.options3 = option;
    }

    public String getOptions4() {
        return options4;
    }

    public void setOptions4(String option){
        this.options4 = option;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
