package com.example.hkrgroup2se.Fragments;

import android.content.SharedPreferences;
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
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Random;


public class QuizFragment extends Fragment {

    ArrayList<Question> questions = new ArrayList<>();
    Button optionOne,optionTwo,optionThree,Next;
    TextView questionText, answerText;
    Question chosenQuestion ;

    Question question1 = new Question("How much is thrown away?","1/3","1/5","1/9","1/3");
    Question question2 = new Question("How much does food waste cost the global economy in billion dollars? ","505","940","700","940");
    Question question3 = new Question("How many people are starving?","1/9","1/4","1/3","1/9");
    Question question4 = new Question("What percentage of grennhouse gases is because of foodwaste","4%","3%","8%","8%");
    Question question5 = new Question("How much fruit and vegetables are thrown ?","1/5","1/2","1/10","1/2");


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

        generateQuestion();


        optionOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unclickableButton();
                if (chosenQuestion.getOption1().equals(chosenQuestion.getAnswer())){
                    answerText.setText("Correct! Answer is : " + chosenQuestion.getAnswer());
                }else{
                    answerText.setText("Wrong! Answer is : " + chosenQuestion.getAnswer());
                }
            }
        });

        optionTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unclickableButton();
                if (chosenQuestion.getOption2().equals(chosenQuestion.getAnswer())){
                    answerText.setText("Correct! Answer is : " + chosenQuestion.getAnswer());
                }else{
                    answerText.setText("Wrong! Answer is : " + chosenQuestion.getAnswer());
                }
            }
        });

        optionThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unclickableButton();
                if (chosenQuestion.getOption3().equals(chosenQuestion.getAnswer())){
                    answerText.setText("Correct! Answer is : " + chosenQuestion.getAnswer());
                }else{
                    answerText.setText("Wrong! Answer is : " + chosenQuestion.getAnswer());
                }
            }
        });

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateQuestion();

            }
        });

        return view;
    }

    public void generateQuestion(){
        answerText.setText("");
        Random randomNumber = new Random();
        chosenQuestion = questions.get(randomNumber.nextInt(questions.size()));
        questionText.setText(chosenQuestion.getQuestion());
        optionOne.setText(chosenQuestion.getOption1());
        optionTwo.setText(chosenQuestion.getOption2());
        optionThree.setText(chosenQuestion.getOption3());
        optionOne.setClickable(true);
        optionTwo.setClickable(true);
        optionThree.setClickable(true);

    }

    public void unclickableButton(){
        optionOne.setClickable(false);
        optionTwo.setClickable(false);
        optionThree.setClickable(false);
    }
}