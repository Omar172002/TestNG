package com.example;
import com.example.BankAccount;
import org.testng.Assert;
import org.testng.annotations.*;

public class BankAccountTest {

    private BankAccount account;

    @BeforeClass
    public void setup() {
        account = new BankAccount(1000.00);  // Crear cuenta con saldo inicial
        System.out.println("Cuenta creada con saldo inicial de 1000.");
    }

    @AfterClass
    public void teardown() {
        account = null;  // Limpiar la cuenta después de las pruebas
        System.out.println("Cuenta eliminada.");
    }

    // ---- PRUEBAS (Depósito y Retiro) ----


    // Prueba  para depósito
    @Test(groups = "positive-tests")
    public void testDeposit() {
        account.deposit(500.00);
        Assert.assertEquals(account.getBalance(), 1500.00, "El saldo después del depósito debería ser 1500.");
    }

    // Prueba  para retiro
    @Test(groups = "positive-tests")
    public void testWithdraw() {
        account.withdraw(200.00);
        Assert.assertEquals(account.getBalance(), 800.00, "El saldo después del retiro debería ser 800.");
    }

    // ---- PRUEBAS DE EXCEPCIONES (Depósito y Retiro Inválidos) ----


    // Prueba para depósito negativo 
    @Test(groups = "negative-tests", expectedExceptions = IllegalArgumentException.class)
    public void testDepositNegativeAmount() {
        account.deposit(-100.00);
    }

    // Prueba para retiro con fondos insuficientes 
    @Test(groups = "negative-tests", expectedExceptions = IllegalArgumentException.class)
    public void testWithdrawInsufficientFunds() {
        account.withdraw(1200.00);
    }

    // Prueba para retiro negativo 
    @Test(groups = "negative-tests", expectedExceptions = IllegalArgumentException.class)
    public void testWithdrawNegativeAmount() {
        account.withdraw(-50.00);
    }

    // ---- PRUEBAS DE DATOS (Usando @DataProvider) ----


    // Prueba de datos utilizando @DataProvider para depósitos y retiros
    @DataProvider(name = "transactionData")
    public Object[][] createTransactionData() {
        return new Object[][] {
            { 500.00, 1500.00 }, // Depósito válido
            { -100.00, 1000.00 }, // Depósito negativo (debe fallar)
            { 200.00, 800.00 }   // Retiro válido
        };
    }


    // ---- PRUEBAS DE DEPENDENCIAS (Depósito seguido de Retiro) ----
    // Prueba que depende de un depósito exitoso
    @Test(groups = "positive-tests")
    public void testDepositForWithdraw() {
        account.deposit(500.00);
        Assert.assertEquals(account.getBalance(), 1500.00, "El saldo después del depósito debería ser 1500.");
    }

  
}
