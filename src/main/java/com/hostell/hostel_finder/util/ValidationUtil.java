package com.hostell.hostel_finder.util;

public class ValidationUtil {
    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isPositive(double value) {
        return value > 0;
    }
}
