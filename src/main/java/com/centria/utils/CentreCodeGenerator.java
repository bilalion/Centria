package com.centria.utils;

import java.time.Year;


public class CentreCodeGenerator {


    public static String generateCode(int id){


        int year = Year.now().getValue();


        return String.format(
                "CTR-%d-%04d",
                year,
                id
        );


    }



    public static String generateUsername(String code){


        return code
                .replace("-", "")
                .toUpperCase();


    }



}