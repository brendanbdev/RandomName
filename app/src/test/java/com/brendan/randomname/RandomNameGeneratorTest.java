package com.brendan.randomname;

import org.junit.Assert;
import org.junit.Test;

public class RandomNameGeneratorTest {
    @Test
    public void test(){
        RandomNameGenerator rngObject = new RandomNameGenerator();
        Assert.assertNotNull(rngObject.getRandomName());
    }
}