/*
 * File        : DatabaseConfig.java
 * Project     : CENTRIA
 *
 * Description :
 * تحميل إعدادات Database وإنشاء Connection.
 */

package com.centria.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

public class DatabaseConfig {

    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    /*
     * LOAD DATABASE CONFIG
     *
     * قراءة database.xml وتحميل معلومات الاتصال
     */
    static {

        try {

            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();

            DocumentBuilder builder =
                    factory.newDocumentBuilder();

            InputStream input =
                    DatabaseConfig.class
                    .getClassLoader()
                    .getResourceAsStream("config/database.xml");

            if (input == null) {
                throw new RuntimeException(
                        "ERROR : database.xml introuvable"
                );
            }

            Document document = builder.parse(input);

            driver = document
                    .getElementsByTagName("driver")
                    .item(0)
                    .getTextContent()
                    .trim();

            url = document
                    .getElementsByTagName("url")
                    .item(0)
                    .getTextContent()
                    .trim();

            username = document
                    .getElementsByTagName("username")
                    .item(0)
                    .getTextContent()
                    .trim();

            password = document
                    .getElementsByTagName("password")
                    .item(0)
                    .getTextContent()
                    .trim();

            Class.forName(driver);

            System.out.println("======================");
            System.out.println("DATABASE CONFIG OK");
            System.out.println("Driver : " + driver);
            System.out.println("URL : " + url);
            System.out.println("User : " + username);
            System.out.println("======================");

        } catch (Exception e) {

            System.out.println("DATABASE CONFIG ERROR");

            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    /*
     * GETTERS
     */

    public static String getDriver() {
        return driver;
    }

    public static String getUrl() {
        return url;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    /*
     * DATABASE CONNECTION
     */

    public static Connection getConnection() throws Exception {

        return DriverManager.getConnection(
                url,
                username,
                password
        );
    }
}