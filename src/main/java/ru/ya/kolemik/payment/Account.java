package ru.ya.kolemik.payment;

import java.util.Currency;

public interface Account {

    String getId();
    String getOwnerId();
    String getTypeId();
    Currency getCurrency();
}
