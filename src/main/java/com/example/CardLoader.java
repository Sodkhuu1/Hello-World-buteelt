package com.example;

import java.io.*;
import java.util.*;

public class CardLoader {
    public static Map<String, String> loadCards(String filename) throws IOException {
        Map<String, String> cards = new LinkedHashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("::", 2);
            if (parts.length == 2) {
                cards.put(parts[0].trim(), parts[1].trim());
            }
        }
        reader.close();
        return cards;
    }
}
