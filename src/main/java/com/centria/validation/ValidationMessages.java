/*
==========================================================
File        : ValidationMessages.java
Project     : CENTRIA
Package     : com.centria.validation

Description :
Central validation message keys.

Contains:
- i18n validation keys constants

Do not add:
- Real messages
- Language logic
- Validation rules
==========================================================
*/

package com.centria.validation;


/*
==========================================================
CLASS : ValidationMessages
==========================================================
*/

public final class ValidationMessages {



    /*
    ======================================================
    CONSTRUCTOR
    Prevent object creation
    ======================================================
    */

    private ValidationMessages(){

    }





    /*
    ======================================================
    GENERAL VALIDATION KEYS
    ======================================================
    */

    public static final String REQUIRED =
            "validation.required";



    public static final String CHARACTERS_INVALID =
            "validation.characters.invalid";



    public static final String NUMBER_INVALID =
            "validation.number.invalid";







    /*
    ======================================================
    PHONE VALIDATION KEYS
    ======================================================
    */

    public static final String PHONE_REQUIRED =
            "validation.phone.required";



    public static final String PHONE_INVALID =
            "validation.phone.invalid";







    /*
    ======================================================
    CENTRE VALIDATION KEYS
    ======================================================
    */

    public static final String CENTRE_NAME_SHORT =
            "validation.centre.name.short";



    public static final String CENTRE_NAME_LONG =
            "validation.centre.name.long";







    /*
    ======================================================
    USERNAME VALIDATION KEYS
    ======================================================
    */

    public static final String USERNAME_INVALID =
            "validation.username.invalid";







    /*
    ======================================================
    PASSWORD VALIDATION KEYS
    ======================================================
    */

    public static final String PASSWORD_SHORT =
            "validation.password.short";


}