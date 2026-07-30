/*
 * File        : SupportedLanguage.java
 * Project     : CENTRIA
 *
 * Description :
 * Supported system languages.
 *
 * Responsibilities:
 * - Check available languages
 * - Validate language code
 * - Provide default language
 *
 * Package:
 * com.centria.language
 */


package com.centria.language;



import java.util.Arrays;
import java.util.List;



public class SupportedLanguage {




    /*
     ======================================================
     01 - SUPPORTED LANGUAGES
     
     Available:
     ar : Arabic
     fr : French
     en : English
     ======================================================
     */


    private static final List<String> LANGUAGES =

            Arrays.asList(
                    "ar",
                    "fr",
                    "en"
            );







    /*
     ======================================================
     02 - DEFAULT LANGUAGE
     ======================================================
     */


    private static final String DEFAULT_LANGUAGE = "ar";








    /*
     ======================================================
     03 - CHECK IF LANGUAGE IS SUPPORTED
     
     Return:
     true  -> supported language
     false -> invalid language
     ======================================================
     */


    public static boolean isSupported(
            String lang
    ){


        return lang != null
                &&
                LANGUAGES.contains(lang);


    }








    /*
     ======================================================
     04 - NORMALIZE LANGUAGE
     
     Invalid value returns default language
     ======================================================
     */


    public static String normalize(
            String lang
    ){


        if(!isSupported(lang)){


            return DEFAULT_LANGUAGE;


        }



        return lang;


    }








    /*
     ======================================================
     05 - GET DEFAULT LANGUAGE
     ======================================================
     */


    public static String getDefault(){


        return DEFAULT_LANGUAGE;


    }






}