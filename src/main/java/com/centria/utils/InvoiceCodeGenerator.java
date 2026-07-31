/*
 * File        : InvoiceCodeGenerator.java
 * Project     : CENTRIA
 *
 * Description :
 * Generate invoice codes.
 */

package com.centria.utils;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



public class InvoiceCodeGenerator {



    /*
    ======================================================
    GENERATE INVOICE CODE

    Format:

    FAC-YYYYMMDD-000001

    Example:

    FAC-20260731-000001

    ======================================================
    */


    public static String generateCode(
            int sequence
    ){


        String date =

                LocalDate.now()
                .format(
                    DateTimeFormatter.ofPattern(
                        "yyyyMMdd"
                    )
                );



        String number =

                String.format(
                        "%06d",
                        sequence
                );



        return

                "FAC-"
                + date
                + "-"
                + number;


    }


}