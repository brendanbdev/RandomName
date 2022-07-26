package com.example.randomname;

import java.util.ArrayList;
import java.util.Random;
import java.lang.StringBuilder;

public class RandomNameGenerator {
    private static Random random = new Random();
    private static String[] possibleFirstLetters = {"A", "E", "I", "O", "U", "B", "C", "D", "F", "G", "H", "J", "K", "L", "M", "N", "P", "R", "S", "T", "V", "W", "X", "Z", "Ch", "Ph", "Th", "Sh"};
    private static String[] vowels = {"a", "e", "i", "o", "u"};
    private static String[] consonants = {"b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "r", "s", "t", "v", "w", "x", "z", "ch", "ph", "th", "sh"};
    private static String[] lettersAfterConsonants = {"a", "e", "i", "o", "u", "l", "r"};
    private static String[] lettersAfterLAndR = {"b", "c", "d", "f", "g", "h", "j", "k", "m", "n", "p", "t", "v", "w", "x", "z", "ch", "ph", "th", "sh", "a", "e", "i", "o", "u"};
    private static String[] lettersAfterS = {"c", "m", "n", "p", "t", "w", "a", "e", "i", "o", "u"};
    private static String[] possibleEndOfName = {"ael", "iel", "aul", "uel"};

    private static int NAME_LENGTH_VARIABLE = 5;
    private static int MIN_MAX_BOUND = 4;
    //There is a 1 in 2 chance that a special ending will be added if the name's length is 4.
    private static int PROBABILITY_OF_SPECIAL_ENDING = 2;

    private ArrayList<String> randomNameArrayList;
    private StringBuilder randomNameStringBuilder;

    public RandomNameGenerator() {
        randomNameArrayList = new ArrayList<>();
        randomNameStringBuilder = new StringBuilder();
    }

    public String getRandomName() {

        //Reset the Array List and String Builder
        randomNameArrayList.clear();
        randomNameStringBuilder.delete(0,randomNameStringBuilder.length());

        int nameLength = random.nextInt(NAME_LENGTH_VARIABLE) + MIN_MAX_BOUND;

        addLetterFrom(possibleFirstLetters);

        if (firstLetterIsAVowel()) {
            addLetterFrom(consonants);
        }

        if (lastLetterIs("s")) {
            addLetterFrom(lettersAfterS);
        } else {
            addLetterFrom(vowels);
        }

        while(randomNameArrayList.size() < nameLength) {
            if (lastLetterIsAVowel()) {
                addLetterFrom(consonants);
            } else if(secondToLastLetterIsAConsonant()) {
                addLetterFrom(vowels);
            } else if (lastLetterIs("l") || lastLetterIs("r")) {
                addLetterFrom(lettersAfterLAndR);
            } else if (lastLetterIs("s")) {
                addLetterFrom(lettersAfterS);
            } else {
                addLetterFrom(lettersAfterConsonants);
            }
        }

        int last_index = randomNameArrayList.size() - 1;
        if (nameLength == MIN_MAX_BOUND && letterAtIndexIsAConsonant(last_index) && shouldAddSpecialEnding()) {
            addLetterFrom(possibleEndOfName);
        }

        for (String i : randomNameArrayList) {randomNameStringBuilder.append(i);}

        return randomNameStringBuilder.toString();
    }

    private boolean letterAtIndexEquals(int index, String letter) {
        return randomNameArrayList.get(index).equalsIgnoreCase(letter);
    }

    private boolean lastLetterIs(String letter) {
        return randomNameArrayList.get(randomNameArrayList.size() - 1).equalsIgnoreCase(letter);
    }

    private boolean secondToLastLetterIsAConsonant() {
        return letterAtIndexIsAConsonant(randomNameArrayList.size() - 2);
    }

    private boolean firstLetterIsAVowel() {
        return letterAtIndexIsAVowel(0);
    }

    private boolean lastLetterIsAVowel() {
        return letterAtIndexIsAVowel(randomNameArrayList.size() - 1);
    }

    private boolean letterAtIndexIsAVowel(int index) {
        for (String vowel : vowels) {
            if (letterAtIndexEquals(index, vowel)) {
                return true;
            }
        }
        return false;
    }

    private boolean letterAtIndexIsAConsonant(int index) {
        for (String consonant : consonants) {
            if (letterAtIndexEquals(index, consonant)) {
                return true;
            }
        }
        return false;
    }

    private void addLetterFrom(String[] options) {
        randomNameArrayList.add(options[random.nextInt(options.length)]);
    }

    private static boolean shouldAddSpecialEnding() {
        return random.nextInt(PROBABILITY_OF_SPECIAL_ENDING) == 0;
    }
}