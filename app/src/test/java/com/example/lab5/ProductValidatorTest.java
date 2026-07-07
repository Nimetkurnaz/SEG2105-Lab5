package com.example.lab5;

import org.junit.Assert;
import org.junit.Test;

public class ProductValidatorTest {

    @Test
    public void skuMustBePositive() {
        // Since isValidSku is a static method, you don't even need to instantiate the validator,
        // but keeping it or removing it is fine.
        Assert.assertFalse(ProductValidator.isValidSku(0));
        Assert.assertFalse(ProductValidator.isValidSku(-42));
        Assert.assertTrue(ProductValidator.isValidSku(1001));
    }
}