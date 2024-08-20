package ru.agapov.springintegrationproject.utils;

public class UtilsForArrays {
    public static boolean arrayNotNull(String[] array) {
        return (array != null) ? true : false;
    }
    public static boolean arrayNotEmpty(String[] array) {
        return (array.length > 0) ? true : false;
    }
}
