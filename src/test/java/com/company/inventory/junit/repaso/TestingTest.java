package com.company.inventory.junit.repaso;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestingTest {

    @Test
    public void assertEqualsTest(){
        assertEquals(1,1);
    }

    @Test
    public void assertNotEqualsTest(){
        assertNotEquals(2,3);
    }

    @Test
    public void assertTrueTest(){
        assertTrue(1==1);
    }

    @Test
    public void assertFalseTest(){
        assertFalse(1==1);
    }

    @Test
    public void assertArrayTest(){
        String [] arr1 = {"aa","bb"};
        String [] arr2 = {"aa","bb"};
        String [] arr3 = {"aa","bb","cc"};
        assertArrayEquals(arr1,arr2);
    }

}
