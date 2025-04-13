package org.nazar.common.validators;

public class SidValidator {
    public static boolean isValidSid(String sid) {
        return sid != null && !sid.isEmpty() && sid.matches("[a-zA-Z0-9]{8,}");
    }
}
