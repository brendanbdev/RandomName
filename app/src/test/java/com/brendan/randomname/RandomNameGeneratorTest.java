package com.brendan.randomname;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RandomNameGeneratorTest {
    RandomNameGenerator rngObject;
    MockRandomGen randomGen;

    @Before
    public void setUp() {
        randomGen = new MockRandomGen();
        rngObject = new RandomNameGenerator(randomGen);
    }

    @Test
    public void testNameIsNotEmpty() {
        randomGen.setNextValues(new int[] {2, 4, 3, 3, 1, 2, 3, 1, 1, 4});
        String randomName = rngObject.getRandomName();

        Assert.assertNotNull(randomName);
        Assert.assertNotEquals("", randomName);
    }

    @Test
    public void testNameIsAppropriateLength() {
        randomGen.setNextValues(new int[] {2, 4, 3, 3, 1, 2, 3, 1, 1, 4});
        String randomName = rngObject.getRandomName();
        int nameLength = randomName.length();

        int minLength = 4;
        int maxLength = 9;

        Assert.assertTrue(nameLength >= minLength);
        Assert.assertTrue(nameLength <= maxLength);
    }

    @Test
    public void testSpecificName() {
        randomGen.setNextValues(new int[] {2, 4, 3, 3, 1, 2, 3, 1, 1, 4});
        String randomName = rngObject.getRandomName();

        Assert.assertEquals("Ufocif", randomName);
    }

    // TODO: Write tests to check if each logic rule works as expected (special endings, vowels/consonants alternate, etc.)
}