package com.example;

import java.util.*;

public class FlashcardSession {
    private final List<Card> cards;
    private final int repetitions;
    private final boolean invert;
    private final Scanner scanner = new Scanner(System.in);
    private final Map<Card, Integer> correctAnswers = new HashMap<>();
    private final Map<Card, Integer> totalAttempts = new HashMap<>();

    public FlashcardSession(List<Card> cards, int repetitions, boolean invert) {
        this.cards = cards;
        this.repetitions = repetitions;
        this.invert = invert;
    }

    public void start() {
        long startTime = System.currentTimeMillis();
        boolean allCorrect = true;

        for (Card card : cards) {
            int correctCount = 0;
            while (correctCount < repetitions) {
                String question = invert ? card.getAnswer() : card.getQuestion();
                String expected = invert ? card.getQuestion() : card.getAnswer();

                System.out.println("Asuult: " + question);
                System.out.print("Hariult: ");
                String answer = scanner.nextLine().trim();

                totalAttempts.put(card, totalAttempts.getOrDefault(card, 0) + 1);

                if (answer.equalsIgnoreCase(expected)) {
                    card.markCorrect();
                    correctAnswers.put(card, correctAnswers.getOrDefault(card, 0) + 1);
                    correctCount++;
                    System.out.println("Zuv!");
                } else {
                    card.markWrong();
                    allCorrect = false;
                    System.out.println("Buruu, zuv hariult: " + expected);
                }
            }
        }

        long duration = System.currentTimeMillis() - startTime;
        double avgSeconds = duration / 1000.0 / cards.size();

        System.out.println("\n--- Hicheel Duuslaa ---");
        System.out.println("Dundaj he=ugatsaa: " + String.format("%.2f", avgSeconds) + " second");

        if (avgSeconds < 5.0) {
            System.out.println("Amjilt: FAST LEARNER!");
        }
        if (allCorrect) {
            System.out.println("🎉 Amjilt: CORRECT - бүгдийг зөв хариулсан!");
        }
        for (Card card : cards) {
            int total = totalAttempts.getOrDefault(card, 0);
            int correct = correctAnswers.getOrDefault(card, 0);

            if (total > 5) {
                System.out.println("🔁 Амжилт: REPEAT - " + card.getQuestion());
            }
            if (correct >= 3) {
                System.out.println("💪 Amjilt: CONFIDENT - " + card.getQuestion());
            }
        }
    }
}
