package com.example;

import java.util.*;

public class RecentMistakesFirstSorter implements CardOrganizer {
    public List<Card> organize(List<Card> cards) {
        List<Card> mistakesFirst = new ArrayList<>();
        List<Card> rest = new ArrayList<>();

        for (Card card : cards) {
            if (card.wasRecentlyIncorrect()) {
                mistakesFirst.add(card);
            } else {
                rest.add(card);
            }
        }

        List<Card> result = new ArrayList<>();
        result.addAll(mistakesFirst);
        result.addAll(rest);
        return result;
    }
}