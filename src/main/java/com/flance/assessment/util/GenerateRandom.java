package com.flance.assessment.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateRandom {
    public static String generateRandomAlphaNumeric(int num) {
        StringBuilder res = new StringBuilder();
        Random random = new Random();
        List<String> alphanumericList = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            alphanumericList.add(String.valueOf(i));
        }

        for (char c = 'A'; c <= 'Z'; c++) {
            alphanumericList.add(String.valueOf(c));
        }

        for (int i = 0; i < num; i++) {
            int randomNumber = random.nextInt(alphanumericList.size());
            res.append(alphanumericList.get(randomNumber));
        }
        return res.toString();
    }
}
