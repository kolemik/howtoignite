package ru.ya.kolemik.payment;

public interface PaymentManager {
    void pay(Account from, Account to, Amount a);
}
