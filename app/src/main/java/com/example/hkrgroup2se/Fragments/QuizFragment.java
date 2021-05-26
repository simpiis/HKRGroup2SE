package com.example.hkrgroup2se.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hkrgroup2se.R;
import com.example.hkrgroup2se.Skeleton.Question;

import java.util.ArrayList;
import java.util.Random;


public class QuizFragment extends Fragment {

    ArrayList<Question> questions = new ArrayList<>();
    Button optionOne,optionTwo,optionThree,Next;
    TextView questionText, answerText;
    Question chosenQuestion,oldQuestion;
    int points = 0;
    int questionsAnswered = 0;

    Question question1 = new Question("How much is thrown away?","1/3","1/5","1/9","1/3");
    Question question2 = new Question("How much does food waste cost the global economy in billion dollars? ","505","940","700","940");
    Question question3 = new Question("How many people are starving?","1/9","1/4","1/3","1/9");
    Question question4 = new Question("What percentage of greenhouse gases is because of foodwaste","4%","3%","8%","8%");
    Question question5 = new Question("How much fruit and vegetables are thrown ?","1/5","1/2","1/10","1/2");
    Question question6 = new Question("Percentage wise, how much of the fresh water supply is wasted thanks to food waste.","5%","10%","25%","25%");
    Question question7 = new Question("Food waste is the x solution to climate crisis","1","5","6","1");
    Question question8 = new Question("Nearly x% of the worlds agriculture land is occupied to produce food that is thrown away","10%","21%","30%","30%");
    Question question9 = new Question("Throwing away 1 burger equates to a x minute shower", "30","90","60","90");
    Question question10 = new Question("What grocery has the highest wastage percentage?","bread","fruits","meat","fruits");


    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        optionOne = view.findViewById(R.id.optionOne);
        optionTwo = view.findViewById(R.id.optionTwo);
        optionThree = view.findViewById(R.id.optionThree);
        Next = view.findViewById(R.id.nextButton);
        questionText = view.findViewById(R.id.questionText);
        answerText = view.findViewById(R.id.answerText);

        questions.add(question1);
        questions.add(question2);
        questions.add(question3);
        questions.add(question4);
        questions.add(question5);
        questions.add(question6);
        questions.add(question7);
        questions.add(question8);
        questions.add(question9);
        questions.add(question10);

        generateQuestion();

        optionOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unclickableButton();
                if (chosenQuestion.getOption1().equals(chosenQuestion.getAnswer())){
                    answerText.setText("Correct! Answer is : " + chosenQuestion.getAnswer());
                    optionOne.setBackgroundColor(Color.GREEN);
                    points++;
                    questionsAnswered ++;
                    if(questionsAnswered == 5){
                        Next.setText("Menu");
                        finish();

                    }

                }else{
                    answerText.setText("Wrong! Answer is : " + chosenQuestion.getAnswer());
                    optionOne.setBackgroundColor(Color.RED);
                    questionsAnswered ++;
                    if(questionsAnswered == 5){
                        Next.setText("Menu");
                        finish();
                    }

                }
            }
        });

        optionTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unclickableButton();
                if (chosenQuestion.getOption2().equals(chosenQuestion.getAnswer())){
                    answerText.setText("Correct! Answer is : " + chosenQuestion.getAnswer());
                    optionTwo.setBackgroundColor(Color.GREEN);
                    points++;
                    questionsAnswered ++;
                    if(questionsAnswered == 5){
                        Next.setText("Menu");
                        finish();
                    }
                }else{
                    answerText.setText("Wrong! Answer is : " + chosenQuestion.getAnswer());
                    optionTwo.setBackgroundColor(Color.RED);
                    questionsAnswered ++;
                    if(questionsAnswered == 5){
                        Next.setText("Menu");
                        finish();
                    }
                }
            }
        });

        optionThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unclickableButton();
                if (chosenQuestion.getOption3().equals(chosenQuestion.getAnswer())){
                    answerText.setText("Correct! Answer is : " + chosenQuestion.getAnswer());
                    optionThree.setBackgroundColor(Color.GREEN);
                    points++;
                    questionsAnswered ++;
                    if(questionsAnswered == 5){
                        Next.setText("Menu");
                        finish();
                    }
                }else{
                    answerText.setText("Wrong! Answer is : " + chosenQuestion.getAnswer());
                    optionThree.setBackgroundColor(Color.RED);
                    questionsAnswered ++;
                    if(questionsAnswered == 5){
                        Next.setText("Menu");
                        finish();

                    }
                }
            }
        });

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questionsAnswered == 5){
                    Navigation.findNavController(view).navigate(R.id.action_quizFragment_pop);
                }
                generateQuestion();

            }
        });

        return view;
    }

    public void generateQuestion(){
        answerText.setText("");
        Random randomNumber = new Random();
        int number = randomNumber.nextInt(questions.size());

        chosenQuestion = questions.get(number);
        questions.remove(number);


        questionText.setText(chosenQuestion.getQuestion());
        optionOne.setText(chosenQuestion.getOption1());
        optionTwo.setText(chosenQuestion.getOption2());
        optionThree.setText(chosenQuestion.getOption3());

        optionOne.setClickable(true);
        optionTwo.setClickable(true);
        optionThree.setClickable(true);

        optionOne.setBackgroundColor(Color.BLACK);
        optionTwo.setBackgroundColor(Color.BLACK);
        optionThree.setBackgroundColor(Color.BLACK);

    }

    public void unclickableButton(){
        optionOne.setClickable(false);
        optionTwo.setClickable(false);
        optionThree.setClickable(false);
    }

    public void finish(){
        optionOne.setBackgroundColor(Color.BLACK);
        optionTwo.setBackgroundColor(Color.BLACK);
        optionThree.setBackgroundColor(Color.BLACK);
        questionText.setText("Good job you got the score off :" +points + "/" + questionsAnswered);
        optionOne.setText("Well");
        optionTwo.setText("Done");
        optionThree.setText("<3");
        answerText.setText("");
        unclickableButton();
    }
}