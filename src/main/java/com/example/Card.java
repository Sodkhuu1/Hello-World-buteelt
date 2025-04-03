package com.example;

public class Card {
    private String question;
    private String answer;
    private int correctCount = 0;
    private int wrongCount = 0;
    private List<Boolean> recentResults = new ArrayList<>();

    public Card(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void markCorrect() {
        correctCount++;
        recentResults.add(true);
    }

    public void markWrong() {
        wrongCount++;
        recentResults.add(false);
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public int getWrongCount() {
        return wrongCount;
    }

    public boolean wasRecentlyIncorrect() {
        return recentResults.size() > 0 && !recentResults.get(recentResults.size() - 1);
    }
}
