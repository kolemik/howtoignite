package ru.ya.kolemik.ya06;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public final class Curr {
    public static Curr EUR, USD, RUB; 

    static {
        int order = 0;
        for (Field f : Curr.class.getDeclaredFields()) {
            if (Modifier.isStatic(f.getModifiers())) {
                try {
                    f.set(null, new Curr(f.getName(), order++));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                f.setAccessible(false);
            }
        }
    }

    private final String name;
    private final int order;
    
    private Curr(String name, int order) {
        this.name = name;
        this.order = order;
    }

    public String name() {
        return name;
    }

    public int ordinal() {
        return order;
    }

    @Override
    public String toString() {
        return name;
    }

    public static void main(String[] args) {
        System.out.println(Curr.EUR);
    }
}
