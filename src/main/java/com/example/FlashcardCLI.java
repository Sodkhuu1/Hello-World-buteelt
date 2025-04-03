package com.example;

import java.io.*;
import java.util.*;

public class FlashcardCLI {
    public static void main(String[] args) {
        if (args.length == 0 || Arrays.asList(args).contains("--help")) {
            printHelp();
            return;
        }

        String cardFile = args[0];
        Map<String, String> cardMap = new LinkedHashMap<>();

        // Default values
        String order = "random";
        int repetitions = 1;
        boolean invert = false;

        // Parse options
        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "--order":
                    if (i + 1 < args.length) order = args[++i];
                    break;
                case "--repetitions":
                    if (i + 1 < args.length) repetitions = Integer.parseInt(args[++i]);
                    break;
                case "--invertCards":
                    invert = true;
                    break;
            }
        }

        try {
            cardMap = CardLoader.loadCards(cardFile);
        } catch (IOException e) {
            System.out.println("file unshihad aldaa garlaa: " + e.getMessage());
            return;
        }

        CardOrganizer organizer = CardOrganizerFactory.create(order);
        List<Card> cards = new ArrayList<>();
        for (Map.Entry<String, String> entry : cardMap.entrySet()) {
            cards.add(new Card(entry.getKey(), entry.getValue()));
        }

        List<Card> organizedCards = organizer.organize(cards);
        FlashcardSession session = new FlashcardSession(organizedCards, repetitions, invert);
        session.start();
    }

    private static void printHelp() {
        System.out.println("Usage: flashcard <cards-file> [options]\n" +
            "Options:\n" +
            "  --help                     Тусламжийн мэдээлэл\n" +
            "  --order <order>           Картын дараалал [random|worst-first|recent-mistakes-first]\n" +
            "  --repetitions <num>       Карт бүрийг хичнээн удаа зөв хариулахыг шаардах вэ\n" +
            "  --invertCards             Асуулт/хариултыг солих\n");
    }
}
