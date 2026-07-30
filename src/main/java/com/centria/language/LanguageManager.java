package com.centria.language;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.nio.charset.StandardCharsets;

import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;



/*
 * Centria
 *
 * Language Manager
 *
 * Responsible for loading i18n files
 *
 * Supported languages:
 * ar
 * fr
 * en
 */



public class LanguageManager {




    /*
     =====================================
     Load language file
     =====================================
     */

    private static ResourceBundle loadBundle(
            Locale locale){


        ResourceBundle bundle = null;



        String path =
                "i18n/strings_"
                + locale.getLanguage()
                + ".properties";



        try(
            InputStream input =
            LanguageManager.class
            .getClassLoader()
            .getResourceAsStream(path)
        ){



            if(input == null){

                return null;

            }



            Reader reader =
                    new InputStreamReader(
                            input,
                            StandardCharsets.UTF_8
                    );



            bundle =
                new PropertyResourceBundle(
                        reader
                );



        }
        catch(IOException e){

            e.printStackTrace();

        }



        return bundle;


    }







    /*
     =====================================
     Default language
     Arabic
     =====================================
     */

    public static String get(
            String key){



        ResourceBundle bundle =
                loadBundle(
                    new Locale("ar")
                );



        if(bundle == null){

            return key;

        }



        return bundle.containsKey(key)
                ?
                bundle.getString(key)
                :
                key;


    }









    /*
     =====================================
     Language by User Session
     =====================================
     */

    public static String get(
            String key,
            HttpSession session){



        String lang = "ar";



        if(session != null){


            Object current =
                    session.getAttribute("lang");



            if(current != null){

                lang =
                current.toString();

            }


        }





        /*
         Supported languages
        */


        if(!lang.equals("ar")
                &&
           !lang.equals("fr")
                &&
           !lang.equals("en")){


            lang="ar";


        }






        Locale currentLocale =
                new Locale(lang);





        ResourceBundle bundle =
                loadBundle(currentLocale);






        if(bundle == null){

            return key;

        }





        return bundle.containsKey(key)
                ?
                bundle.getString(key)
                :
                key;



    }




}