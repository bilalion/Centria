/*
 * File        : ValidationResult.java
 * Project     : CENTRIA
 *
 * Module      : Validation
 *
 * Description :
 * Stores validation operation result.
 *
 * Responsibilities:
 * - Store success or failure state
 * - Store i18n message key
 * - Provide result to controllers
 *
 * Package:
 * com.centria.validation
 */


package com.centria.validation;




public class ValidationResult {




    /*
     ======================================================
     01 - VALIDATION STATUS
     
     true  : valid input
     false : invalid input
     ======================================================
     */


    private final boolean valid;







    /*
     ======================================================
     02 - MESSAGE KEY
     
     Examples:
     
     validation.required
     validation.phone.invalid
     validation.username.invalid
     
     Used by LanguageManager.
     ======================================================
     */


    private final String messageKey;








    /*
     ======================================================
     03 - CONSTRUCTOR
     ======================================================
     */


    public ValidationResult(
            boolean valid,
            String messageKey
    ){


        this.valid = valid;

        this.messageKey = messageKey;


    }









    /*
     ======================================================
     04 - SUCCESS FACTORY
     
     Creates successful validation result.
     ======================================================
     */


    public static ValidationResult success(){


        return new ValidationResult(
                true,
                null
        );


    }









    /*
     ======================================================
     05 - ERROR FACTORY
     
     Creates failed validation result.
     
     Example:
     
     ValidationResult.error(
          "validation.required"
     );
     
     ======================================================
     */


    public static ValidationResult error(
            String messageKey
    ){


        return new ValidationResult(
                false,
                messageKey
        );


    }









    /*
     ======================================================
     06 - CHECK RESULT
     ======================================================
     */


    public boolean isValid(){


        return valid;


    }









    /*
     ======================================================
     07 - GET MESSAGE KEY
     ======================================================
     */


    public String getMessageKey(){


        return messageKey;


    }








}