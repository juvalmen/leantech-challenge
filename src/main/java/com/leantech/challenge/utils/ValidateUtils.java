package com.leantech.challenge.utils;

import com.google.common.base.Preconditions;

import java.util.Collection;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Util functions for validations of objects.
 */
public class ValidateUtils {


    private ValidateUtils() {
    }

    /**
     * Evaluates if object has content.
     */
    public static boolean hasContent(final Object object) {
        if (isNull(object)) {
            return FALSE;
        } else if ((object instanceof String)) {
            return isNotBlank((String) object);
        } else if (object instanceof Collection<?>) {
            return !((Collection<?>) object).isEmpty();
        } else {
            return TRUE;
        }
    }

    /**
     * Check if a param has value. Throws IllegalArgumentException if not.
     */
    public static void checkArgument(final Object param, final String paramName) {
        Preconditions.checkArgument(hasContent(param), String.format("Param '%s' is mandatory", paramName));
    }


    /**
     * Check if a param has value. Throws IllegalArgumentException if not.
     */
    public static void checkState(final Object field, final String fieldName) {
        Preconditions.checkState(hasContent(field), String.format("Field '%s' is mandatory", fieldName));
    }
}
