/*
 * File        : PaymentDAO.java
 * Project     : CENTRIA
 *
 * Description :
 * Payment database operations.
 */

package com.centria.dao;


import com.centria.config.DatabaseConfig;
import com.centria.utils.InvoiceCodeGenerator;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;



public class PaymentDAO {



    /*
    ======================================================
    CREATE INITIAL PAYMENT

    Called when a new centre is created.

    First subscription is already paid.

    Actions:

    1- Insert last payment in payments
    2- Insert same payment in history_payment

    ======================================================
    */


    public boolean createInitialPayment(
            String centreCode
    ){


        Connection con = null;


        try{


            con = DatabaseConfig.getConnection();


            con.setAutoCommit(false);





            /*
            ==============================================
            STEP 1
            INSERT TEMP PAYMENT
            ==============================================
            */


         String insertPayment =

"INSERT INTO payments "
+ "(code_facture, centre_code, status_payment) "
+ "VALUES (?, ?, ?)";



            PreparedStatement ps =

                    con.prepareStatement(
                            insertPayment,
                            java.sql.Statement.RETURN_GENERATED_KEYS
                    );



            ps.setString(
                    1,
                    "TEMP"
            );


            ps.setString(
                    2,
                    centreCode
            );

            ps.setString(
        3,
        "PAID"
);


            int result =
                    ps.executeUpdate();



            if(result == 0){

                con.rollback();

                return false;

            }





            /*
            ==============================================
            STEP 2
            GENERATE INVOICE CODE
            ==============================================
            */


            ResultSet rs =

                    ps.getGeneratedKeys();



            if(!rs.next()){


                con.rollback();

                return false;

            }



            int paymentId =

                    rs.getInt(1);




            String invoiceCode =

                    InvoiceCodeGenerator.generateCode(
                            paymentId
                    );







            /*
            ==============================================
            STEP 3
            UPDATE PAYMENT CODE
            ==============================================
            */


            String updatePayment =

            "UPDATE payments SET "
            + "code_facture=? "
            + "WHERE id=?";




            PreparedStatement update =

                    con.prepareStatement(
                            updatePayment
                    );



            update.setString(
                    1,
                    invoiceCode
            );


            update.setInt(
                    2,
                    paymentId
            );



            update.executeUpdate();







            /*
            ==============================================
            STEP 4
            INSERT PAYMENT HISTORY

            Same invoice

            Date = today

            ==============================================
            */


            String insertHistory =

            "INSERT INTO history_payment"
            + "(code_facture, centre_code, date_paiement) "
            + "VALUES (?, ?, ?)";





            PreparedStatement history =

                    con.prepareStatement(
                            insertHistory
                    );



            history.setString(
                    1,
                    invoiceCode
            );


            history.setString(
                    2,
                    centreCode
            );


            history.setDate(
                    3,
                    new Date(
                        System.currentTimeMillis()
                    )
            );



            history.executeUpdate();







            /*
            ==============================================
            COMMIT
            ==============================================
            */


            con.commit();


            return true;



        }
        catch(Exception e){


            e.printStackTrace();


            try{


                if(con != null){

                    con.rollback();

                }


            }
            catch(Exception rollback){


                rollback.printStackTrace();

            }



        }
        finally{


            try{


                if(con != null){

                    con.close();

                }


            }
            catch(Exception close){


                close.printStackTrace();

            }


        }




        return false;


    }








    /*
    ======================================================
    UPDATE LAST PAYMENT

    Used later for renew subscription.

    - Replace current invoice in payments
    - Add new record in history

    ======================================================
    */


    public boolean renewPayment(
            String centreCode
    ){


        Connection con = null;



        try{


            con = DatabaseConfig.getConnection();


            con.setAutoCommit(false);






            /*
            Generate new invoice
            */


            String tempInvoice = "TEMP";




            String updatePayment =

            "UPDATE payments SET "
            + "code_facture=? "
            + "WHERE centre_code=?";




            PreparedStatement ps =

                    con.prepareStatement(
                            updatePayment
                    );



            ps.setString(
                    1,
                    tempInvoice
            );


            ps.setString(
                    2,
                    centreCode
            );



            ps.executeUpdate();








            /*
            Get new payment id
            */


            String select =

            "SELECT id FROM payments "
            + "WHERE centre_code=?";




            PreparedStatement getId =

                    con.prepareStatement(
                            select
                    );



            getId.setString(
                    1,
                    centreCode
            );



            ResultSet rs =

                    getId.executeQuery();



            if(!rs.next()){


                con.rollback();

                return false;

            }




            int id =

                    rs.getInt("id");




            String invoiceCode =

                    InvoiceCodeGenerator.generateCode(
                            id
                    );







            /*
            Update final invoice code
            */


            PreparedStatement update =

                    con.prepareStatement(
                    "UPDATE payments SET code_facture=? WHERE id=?"
                    );



            update.setString(
                    1,
                    invoiceCode
            );


            update.setInt(
                    2,
                    id
            );



            update.executeUpdate();








            /*
            Add history
            */


            PreparedStatement history =

                    con.prepareStatement(
                    "INSERT INTO history_payment"
                    + "(code_facture,centre_code,date_paiement) "
                    + "VALUES(?,?,?)"
                    );



            history.setString(
                    1,
                    invoiceCode
            );


            history.setString(
                    2,
                    centreCode
            );


            history.setDate(
                    3,
                    new Date(
                        System.currentTimeMillis()
                    )
            );



            history.executeUpdate();






            con.commit();



            return true;



        }
        catch(Exception e){


            e.printStackTrace();


            try{

                if(con != null){

                    con.rollback();

                }

            }
            catch(Exception rollback){

                rollback.printStackTrace();

            }


        }
        finally{


            try{


                if(con != null){

                    con.close();

                }


            }
            catch(Exception close){

                close.printStackTrace();

            }


        }



        return false;


    }



}