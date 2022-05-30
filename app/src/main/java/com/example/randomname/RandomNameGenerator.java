package com.example.randomname;

import java.util.Random;
import java.lang.StringBuilder;

public class RandomNameGenerator {
    static StringBuilder randomName = new StringBuilder();
    static Random rand = new Random();
    static boolean randomBooleanValue;
    static String[] vowel = {"a", "e", "i", "o", "u"};
    static String[] consonant = {"b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "r", "s", "t", "v", "w", "x", "z"};

    public static String getRandomName() {
        randomBooleanValue = rand.nextInt(2) == 0;
        randomName.delete(0,8);
        setFirstLetter();
        setRemainingLetters(randomBooleanValue);
        return randomName.toString();
    }

    public static void setFirstLetter() {
        if (randomBooleanValue) {
            randomName.append(getRandomVowel().toUpperCase());
        } else {
            randomName.append(getRandomConsonant().toUpperCase());
        }
    }

    public static void setRemainingLetters(boolean isVowel) {
        int lengthOfName = rand.nextInt(6) + 3;
        for (int l = 1; l < lengthOfName; l++) {
            if (isVowel) {
                randomName.append(getRandomConsonant());
            } else {
                randomName.append(getRandomVowel());
            }
            isVowel = !isVowel;
        }
    }

    public static String getRandomVowel() {
        return vowel[rand.nextInt(5)];
    }

    public static String getRandomConsonant() {
        return consonant[rand.nextInt(19)];
    }
}