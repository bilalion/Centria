/*
 * File        : PaymentDAO.java
 * Project     : CENTRIA
 *
 * Description :
 * Payment database operations.
 */

package com.centria.dao;


import com.centria.config.DatabaseConfig;
import com.centria.models.Payment;
import com.centria.utils.InvoiceCodeGenerator;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.List;



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


    /*
======================================================
CONFIRM PAYMENT

Called from Payment Management.

Actions:

1- Generate new invoice
2- Update payments
3- Insert history_payment
4- Update centre subscription

======================================================
*/

/*
======================================================
CONFIRM PAYMENT

TAB1 : UNPAID

Flow:

1- Get current UNPAID payment
2- Generate new code_facture
3- Update centres
4- Update payments
5- Insert history_payment

======================================================
*/

public boolean confirmPayment(
        String centreCode,
        Date newStartDate,
        int durationMonths
){

    Connection con = null;


    try{


        con = DatabaseConfig.getConnection();

        con.setAutoCommit(false);



        /*
        ==============================================
        1 - GET CURRENT PAYMENT
        ==============================================
        */

        String selectPayment =

        "SELECT id "
        + "FROM payments "
        + "WHERE centre_code=? "
        + "AND status_payment='UNPAID' "
        + "LIMIT 1";


        PreparedStatement psSelect =

                con.prepareStatement(
                        selectPayment
                );


        psSelect.setString(
                1,
                centreCode
        );


        ResultSet rs =

                psSelect.executeQuery();



        if(!rs.next()){


            System.out.println(
                    "NO UNPAID PAYMENT FOUND : "
                    + centreCode
            );


            con.rollback();

            return false;

        }



        int paymentId =

                rs.getInt("id");





        /*
        ==============================================
        2 - CALCULATE END DATE
        ==============================================
        */


        java.util.Calendar calendar =

                java.util.Calendar.getInstance();



        calendar.setTime(
                newStartDate
        );


        calendar.add(
                java.util.Calendar.MONTH,
                durationMonths
        );


        Date subscriptionEnd =

                new Date(
                    calendar.getTimeInMillis()
                );







        /*
        ==============================================
        3 - UPDATE CENTRES
        ==============================================
        */


        String updateCentre =

        "UPDATE centres SET "
        + "subscription_start=?, "
        + "subscription_end=?, "
        + "status=? "
        + "WHERE centre_code=?";


        PreparedStatement psCentre =

                con.prepareStatement(
                        updateCentre
                );



        psCentre.setDate(
                1,
                newStartDate
        );


        psCentre.setDate(
                2,
                subscriptionEnd
        );


        psCentre.setString(
                3,
                "ACTIVE"
        );


        psCentre.setString(
                4,
                centreCode
        );



        if(psCentre.executeUpdate()==0){


            System.out.println(
                    "CENTRE UPDATE FAILED"
            );


            con.rollback();

            return false;

        }








        /*
        ==============================================
        4 - GENERATE NEW INVOICE CODE
        ==============================================
        */


        String newInvoiceCode =

                InvoiceCodeGenerator.generateCode(
                        paymentId
                );








        /*
        ==============================================
        5 - UPDATE PAYMENTS
        ==============================================
        */


        String updatePayment =

        "UPDATE payments SET "
        + "code_facture=?, "
        + "status_payment=? "
        + "WHERE id=?";



        PreparedStatement psPayment =

                con.prepareStatement(
                        updatePayment
                );



        psPayment.setString(
                1,
                newInvoiceCode
        );


        psPayment.setString(
                2,
                "PAID"
        );


        psPayment.setInt(
                3,
                paymentId
        );



        if(psPayment.executeUpdate()==0){


            System.out.println(
                    "PAYMENT UPDATE FAILED"
            );


            con.rollback();

            return false;

        }







        /*
        ==============================================
        6 - INSERT HISTORY PAYMENT
        ==============================================
        */


        String insertHistory =

        "INSERT INTO history_payment "
        + "(code_facture, centre_code, date_paiement) "
        + "VALUES (?, ?, ?)";



        PreparedStatement psHistory =

                con.prepareStatement(
                        insertHistory
                );



        psHistory.setString(
                1,
                newInvoiceCode
        );


        psHistory.setString(
                2,
                centreCode
        );


        psHistory.setDate(
                3,
                new Date(
                    System.currentTimeMillis()
                )
        );



        if(psHistory.executeUpdate()==0){


            System.out.println(
                    "HISTORY INSERT FAILED"
            );


            con.rollback();

            return false;

        }







        /*
        ==============================================
        COMMIT
        ==============================================
        */


        con.commit();


        System.out.println(
                "PAYMENT CONFIRMED SUCCESSFULLY : "
                + centreCode
        );


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


/* ======================================================
GET UNPAID PAYMENTS

Used by TAB1

======================================================
*/

public List<Payment> getUnpaidPayments(
        String search,
        String order,
        int page,
        int pageSize
){

    List<Payment> payments =

            new java.util.ArrayList<>();


    Connection con = null;


    try{


        con = DatabaseConfig.getConnection();



  String sql =

"SELECT "
+ "p.centre_code, "
+ "c.name, "
+ "c.subscription_end, "
+ "p.code_facture, "
+ "p.status_payment, "
+ "c.status "
+ "FROM payments p "
+ "INNER JOIN centres c "
+ "ON p.centre_code = c.centre_code "
+ "WHERE p.status_payment='UNPAID' "
+ "AND c.status IN ('SUSPENDED','PENDING') ";


        if(search != null
                && !search.trim().isEmpty()){

           sql +=

"AND ("

+ "p.centre_code LIKE ? "

+ "OR c.name LIKE ? "

+ "OR c.phone LIKE ? "

+ "OR p.code_facture LIKE ?"

+ ") ";

        }



if("OLD".equals(order)){

    sql +=

    "ORDER BY "
    + "CASE "
    + "WHEN c.status='SUSPENDED' THEN 1 "
    + "WHEN c.status='PENDING' THEN 2 "
    + "END, "
    + "p.id ASC ";

}
else{

    sql +=

    "ORDER BY "
    + "CASE "
    + "WHEN c.status='SUSPENDED' THEN 1 "
    + "WHEN c.status='PENDING' THEN 2 "
    + "END, "
    + "p.id DESC ";

}


        sql +=

        "LIMIT ? OFFSET ?";



        PreparedStatement ps =

                con.prepareStatement(
                        sql
                );
        
     /* ==============================================
        SET PARAMETERS
        ==============================================
        */

        int index = 1;


        if(search != null
                && !search.trim().isEmpty()){

            String keyword =

                    "%"
                    + search.trim()
                    + "%";


            ps.setString(
                    index++,
                    keyword
            );


            ps.setString(
                    index++,
                    keyword
            );


            ps.setString(
                    index++,
                    keyword
            );
            
            ps.setString(
        index++,
        keyword
);

        }



        ps.setInt(
                index++,
                pageSize
        );


        ps.setInt(
                index,
                (page - 1) * pageSize
        );




        /*
        ==============================================
        EXECUTE QUERY
        ==============================================
        */

        ResultSet rs =

                ps.executeQuery();




        /*
        ==============================================
        BUILD PAYMENT LIST
        ==============================================
        */

        while(rs.next()){


            Payment payment =

                    new Payment();



            payment.setCentreCode(
                    rs.getString(
                            "centre_code"
                    )
            );



            payment.setCentreName(
                    rs.getString(
                            "name"
                    )
            );



         payment.setSubscriptionEnd(
                   rs.getDate("subscription_end")
         );



            payment.setCodeFacture(
                    rs.getString(
                            "code_facture"
                    )
            );



            payment.setStatusPayment(
                    rs.getString(
                            "status_payment"
                    )
            );

            payment.setAccountStatus(
        rs.getString("status")
);


            payments.add(
                    payment
            );

        }
                /*
        ==============================================
        RETURN RESULT
        ==============================================
        */

        return payments;

    }
    catch(Exception e){

        e.printStackTrace();

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

    return payments;

}

}

