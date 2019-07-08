package com.witt.utils;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class DESTestTest {

    @Test
    public void fun() {
        String str = "osc";
        byte[] bytes = str.getBytes();
        System.out.println(Arrays.toString(bytes));
        System.out.println(Arrays.toString(new String(str).getBytes()));
    }
}