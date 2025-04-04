package com.example;

import java.util.*;

public class FlashcardSession {
    private List<Card> cards;
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
        boolean allCorrect;
        List<Card> fullCardList = new ArrayList<>(cards);

        while (true) {
            allCorrect = true;
            List<Card> remainingCards = new ArrayList<>(cards);

            while (!remainingCards.isEmpty()) {
                List<Card> wrongAnswers = new ArrayList<>();

                for (Card card : remainingCards) {
                    int wrongCount = card.getWrongCount();
                    if (wrongCount > 0) {
                        System.out.println("Ankhaaruulga: Ene asuultad umnu ni " + wrongCount + " udaa buruu hariulsan.");
                    }

                    for (int i = 0; i < repetitions; i++) {
                        String question = invert ? card.getAnswer() : card.getQuestion();
                        String expected = invert ? card.getQuestion() : card.getAnswer();

                        System.out.println("Asuult: " + question);
                        System.out.print("Hariult (esvel 'exit'): ");
                        String answer = scanner.nextLine().trim();

                        if (answer.equalsIgnoreCase("exit")) {
                            System.out.println("Program haagdaj baina...");
                            return;
                        }

                        totalAttempts.put(card, totalAttempts.getOrDefault(card, 0) + 1);

                        if (answer.equalsIgnoreCase(expected)) {
                            card.markCorrect();
                            correctAnswers.put(card, correctAnswers.getOrDefault(card, 0) + 1);
                            System.out.println("Zuv!");
                        } else {
                            card.markWrong();
                            wrongAnswers.add(card);
                            allCorrect = false;
                            System.out.println("Buruu, zuv hariult: " + expected);
                            break;
                        }
                    }
                }

                if (!wrongAnswers.isEmpty()) {
                    System.out.println("\nBuruu hariulsan asuultuud:");
                    for (Card c : wrongAnswers) {
                        System.out.println("- " + (invert ? c.getAnswer() : c.getQuestion()));
                    }
                }

                remainingCards = wrongAnswers;
            }

            long duration = System.currentTimeMillis() - startTime;
            double avgSeconds = duration / 1000.0 / fullCardList.size();

            System.out.println("\n--- Hicheel duuslaa ---");
            System.out.println("Dundaj hugatsaa: " + String.format("%.2f", avgSeconds) + " second");

            if (avgSeconds < 5.0) {
                System.out.println("Amjilt: FAST LEARNER");
            }
            if (allCorrect) {
                System.out.println("Amjilt: CORRECT - buh asuultand zuv hariullaa");
            }
            for (Card card : fullCardList) {
                int total = totalAttempts.getOrDefault(card, 0);
                int correct = correctAnswers.getOrDefault(card, 0);

                if (total > 5) {
                    System.out.println("Amjilt: REPEAT - " + card.getQuestion());
                }
                if (correct >= 3) {
                    System.out.println("Amjilt: CONFIDENT - " + card.getQuestion());
                }
            }

            // Sonolt avah heseg
            System.out.println("\nDahin ehluuleh uu?");
            System.out.println("1. Ehnees ni ehleh");
            System.out.println("2. Suuld buruu hariulsan asuultaar ehleh (recent-mistakes-first)");
            System.out.println("3. Hamgiin olon buruu hariulsan asuultaar ehleh (most-mistaked-first)");
            System.out.println("exit. Programiig haah");
            System.out.print("Tanii songolt: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("exit")) {
                System.out.println("Program haagdaj baina...");
                return;
            } else if (choice.equals("1")) {
                cards = new ArrayList<>(fullCardList);
            } else if (choice.equals("2")) {
                cards = new RecentMistakesFirstSorter().organize(fullCardList);
            } else if (choice.equals("3")) {
                cards = new ArrayList<>(fullCardList);
                cards.sort((c1, c2) -> Integer.compare(c2.getWrongCount(), c1.getWrongCount()));
            } else {
                System.out.println("Buruu songolt. Program haagdaj baina...");
                return;
            }
        }
    }
}