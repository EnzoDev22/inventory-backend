package com.company.inventory.junit.repaso;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class CalculadoraTest {

    Calculadora cal;

    @BeforeAll
    public static void primero(){
        System.out.println("Primero");
    }

    @AfterAll
    public static void ultimo(){
        System.out.println("Ultimo");
    }

    @BeforeEach
    public void antesDeCadaTest(){
        System.out.println("Antes de cada test");
    }

    @AfterEach
    public void despuesDeCadaTest(){
        System.out.println("Despues de cada test");
    }



}
