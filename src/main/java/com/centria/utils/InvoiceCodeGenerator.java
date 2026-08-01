/*
 * File        : InvoiceCodeGenerator.java
 * Project     : CENTRIA
 *
 * Description :
 * Generate unique invoice codes.
 */

package com.centria.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InvoiceCodeGenerator {

    /*
    ======================================================
    GENERATE INVOICE CODE

    Example:

    FAC-20260801-143512-000002

    ======================================================
    */

    public static String generateCode(int sequence) {

        String dateTime =
                LocalDateTime.now()
                        .format(
                                DateTimeFormatter.ofPattern(
                                        "yyyyMMdd-HHmmss"
                                )
                        );

        String number =
                String.format("%06d", sequence);

        return "FAC-"
                + dateTime
                + "-"
                + number;

    }

}