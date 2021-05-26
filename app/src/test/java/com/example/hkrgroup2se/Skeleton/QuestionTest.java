package com.example.hkrgroup2se.Skeleton;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class QuestionTest {

    Question question = new Question("question","option1","option2","option3","answer");

    @Test
    public void getQuestion() {
        String expectedValue = "question";
        String actualValue = question.getQuestion();
        Assert.assertEquals(expectedValue,actualValue);
    }

    @Test
    public void getOption1() {
        String expectedValue = "option1";
        String actualValue = question.getOption1();
        Assert.assertEquals(expectedValue,actualValue);
    }

    @Test
    public void getOption2() {
        String expectedValue = "option2";
        String actualValue = question.getOption2();
        Assert.assertEquals(expectedValue,actualValue);
    }

    @Test
    public void getOption3() {
        String expectedValue = "option3";
        String actualValue = question.getOption3();
        Assert.assertEquals(expectedValue,actualValue);
    }

    @Test
    public void getAnswer() {
        String expectedValue = "answer";
        String actualValue = question.getAnswer();
        Assert.assertEquals(expectedValue,actualValue);
    }
}