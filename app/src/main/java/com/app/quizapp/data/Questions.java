package com.app.quizapp.data;

import java.util.ArrayList;
import java.util.List;

public class Questions {
    private final List<Question> questions = new ArrayList<>();
    private int index = 0;

    public Questions(){
        this.addQuestions();
    }

    public Questions(String category){
        this.addQuestions();
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int getNrOfQuestions(){
        return index;
    }

    private void addQuestions(){
        questions.add(new Question(++index,
                "Java is a",
                "High-level programming language",
                "Object-oriented (class-based) programming language",
                "Functional, imperative and reflective programming language",
                "All the above",
                4));

        questions.add(new Question(++index,
                "What is a correct syntax to output \"Hello World\" in Java?",
                "System.out.println(\"Hello World\");",
                "echo(\"Hello World\");",
                "Console.WriteLine(\"Hello World\");",
                "print(\"Hello World\");",
                1));

        questions.add(new Question(++index,
                "How do you insert COMMENTS in Java code?",
                "/* This is a comment",
                "# This is a comment",
                "// This is a comment",
                "<-- This is a comment -->",
                3));

        questions.add(new Question(++index,
                "Which data type is used to create a variable that should store text?",
                "myString",
                "string",
                "Txt",
                "String",
                4));

        questions.add(new Question(++index,
                "How do you create a variable with the numeric value 5?",
                "float x = 5;",
                "num x = 5;",
                "int x = 5;",
                "x = 5;",
                3));

        questions.add(new Question(++index,
                "How do you create a variable with the floating number 2.8?",
                "float x = 2.8f;",
                "byte x = 2.8f;",
                "x = 2.8f;",
                "int x = 2.8f;",
                1));

        questions.add(new Question(++index,
                "Which method can be used to find the length of a string?",
                "len()",
                "length()",
                "getLength()",
                "getSize()",
                2));

        questions.add(new Question(++index,
                "Which method can be used to return a string in upper case letters?",
                "toUpperCase()",
                "upperCase()",
                "toUpper()",
                "toUpperCaseString()",
                1));

        questions.add(new Question(++index,
                "Which method can be used to return a string in lower case letters?",
                "toLowerCase()",
                "lowerCase()",
                "toLower()",
                "toLowerCaseString()",
                1));

        questions.add(new Question(++index,
                "Which operator can be used to compare two values?",
                "=",
                "==",
                "===",
                "None of the above",
                2));

        questions.add(new Question(++index,
                "Which keyword is used to create a class in Java?",
                "class",
                "className",
                "myClass",
                "newClass",
                1));

        questions.add(new Question(++index,
                "What is the correct way to create an object called myObj of MyClass?",
                "class myObj = new MyClass();",
                "new myObj = MyClass();",
                "MyClass myObj = new MyClass();",
                "class MyClass = new MyObj();",
                3));

        questions.add(new Question(++index,
                "Which keyword is used to import a package from the Java API library?",
                "import",
                "extract",
                "package",
                "add",
                1));

        questions.add(new Question(++index,
                "Which statement is used to stop a loop?",
                "break",
                "exit",
                "stop",
                "return",
                1));

        questions.add(new Question(++index,
                "What is the default value of data type boolean in Java?",
                "0",
                "1",
                "true",
                "false",
                4));

        questions.add(new Question(++index,
                "What is the value of the expression 79 / 10?",
                "9",
                "7",
                "0",
                "None of the above",
                1));

        questions.add(new Question(++index,
                "What is the value of the expression 79 % 10?",
                "9",
                "7",
                "0",
                "None of the above",
                2));

        questions.add(new Question(++index,
                "Which word is not a Java keyword?",
                "protect",
                "public",
                "private",
                "secret",
                4));

        questions.add(new Question(++index,
                "Which data type is not a Java primitive data type?",
                "int",
                "char",
                "String",
                "boolean",
                3));
    }
}
