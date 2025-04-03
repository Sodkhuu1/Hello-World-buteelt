package com.example;

public class CardOrganizerFactory {
    public static CardOrganizer create(String order) {
        switch (order) {
            case "recent-mistakes-first":
                return new RecentMistakesFirstSorter();
            case "worst-first":
                return (cards) -> {
                    cards.sort((c1, c2) -> Integer.compare(c2.getWrongCount(), c1.getWrongCount()));
                    return cards;
                };
            default:
                Collections.shuffle(cards);
                return (cards) -> cards;
        }
    }
}
