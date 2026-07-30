/*
 * File        : InputValidator.java
 * Project     : CENTRIA
 *
 * Module      : Validation
 *
 * Description :
 * Central input validation service.
 *
 * Responsibilities:
 * - Validate user inputs
 * - Prevent invalid data
 * - Return ValidationResult with i18n key
 *
 * Package:
 * com.centria.validation
 */


package com.centria.validation;




public class InputValidator {




    /*
     ======================================================
     01 - REQUIRED FIELD VALIDATION
     
     Checks:
     - null
     - empty
     - spaces only
     
     Message:
     validation.required
     ======================================================
     */


    public static ValidationResult validateRequired(
            String value
    ){


        if(value == null
                ||
           value.trim().isEmpty()){


            return ValidationResult.error(
                    "validation.required"
            );


        }



        return ValidationResult.success();


    }









    /*
     ======================================================
     02 - USERNAME VALIDATION
     
     Rules:
     - Required
     - Only letters, numbers and underscore
     
     Message:
     validation.username.invalid
     ======================================================
     */


    public static ValidationResult validateUsername(
            String username
    ){



        ValidationResult result =
                validateRequired(username);



        if(!result.isValid()){


            return result;


        }







        if(!username.matches(
                "^[a-zA-Z0-9_]+$"
        )){


            return ValidationResult.error(
                    "validation.username.invalid"
            );


        }







        return ValidationResult.success();


    }









    /*
     ======================================================
     03 - PASSWORD VALIDATION
     
     Rules:
     - Required
     - Minimum length
     
     Message:
     validation.password.short
     ======================================================
     */


    public static ValidationResult validatePassword(
            String password
    ){



        ValidationResult result =
                validateRequired(password);



        if(!result.isValid()){


            return result;


        }







        if(password.length() < 6){


            return ValidationResult.error(
                    "validation.password.short"
            );


        }







        return ValidationResult.success();


    }









    /*
     ======================================================
     04 - PHONE VALIDATION
     
     Moroccan phone:
     - Starts with 0
     - 10 digits
     
     Examples:
     0612345678  OK
     abc         ERROR
     
     Messages:
     validation.phone.required
     validation.phone.invalid
     ======================================================
     */


    public static ValidationResult validatePhone(
            String phone
    ){



        ValidationResult result =
                validateRequired(phone);



        if(!result.isValid()){


            return ValidationResult.error(
                    "validation.phone.required"
            );


        }







        if(!phone.matches(
                "^0[0-9]{9}$"
        )){


            return ValidationResult.error(
                    "validation.phone.invalid"
            );


        }







        return ValidationResult.success();


    }









    /*
     ======================================================
     05 - CENTRE NAME VALIDATION
     
     Rules:
     - Required
     - No dangerous characters
     - Length control
     
     Messages:
     validation.required
     validation.characters.invalid
     validation.centre.name.short
     validation.centre.name.long
     ======================================================
     */


    public static ValidationResult validateCentreName(
            String name
    ){



        ValidationResult result =
                validateRequired(name);



        if(!result.isValid()){


            return result;


        }







        if(name.matches(
                ".*[<>|*].*"
        )){


            return ValidationResult.error(
                    "validation.characters.invalid"
            );


        }







        if(name.length() < 3){


            return ValidationResult.error(
                    "validation.centre.name.short"
            );


        }







        if(name.length() > 100){


            return ValidationResult.error(
                    "validation.centre.name.long"
            );


        }







        return ValidationResult.success();


    }









    /*
     ======================================================
     06 - GENERAL TEXT VALIDATION
     
     Used for:
     - Owner name
     - Text fields
     
     Message:
     validation.characters.invalid
     ======================================================
     */


    public static ValidationResult validateText(
            String text
    ){



        ValidationResult result =
                validateRequired(text);



        if(!result.isValid()){


            return result;


        }







        if(text.matches(
                ".*[<>|*].*"
        )){


            return ValidationResult.error(
                    "validation.characters.invalid"
            );


        }







        return ValidationResult.success();


    }







}