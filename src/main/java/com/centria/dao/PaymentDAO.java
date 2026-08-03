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
    String centreCode,
    Date dateStart,
    Date dateEnd,
    int durationMonths
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
+ "(code_facture, centre_code, date_paiement, date_start, date_end, duration_months, operation_type) "
+ "VALUES (?, ?, ?, ?, ?, ?, ?)";





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


            history.setDate(3,new Date(System.currentTimeMillis()));
            
            history.setDate(4, dateStart);

            history.setDate(5, dateEnd);

            history.setInt(6, durationMonths);

            history.setString(7, "INITIAL");

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
+ "(code_facture, centre_code, date_paiement, date_start, date_end, duration_months) "
+ "VALUES (?, ?, ?, ?, ?, ?)";


        PreparedStatement psHistory =

                con.prepareStatement(
                        insertHistory
                );



        

psHistory.setString(1,newInvoiceCode);
        psHistory.setString(2,centreCode);
        psHistory.setDate(3,new Date(System.currentTimeMillis()));
        psHistory.setDate(4,newStartDate);
        psHistory.setDate(5,subscriptionEnd);
        psHistory.setInt(6,durationMonths);


     




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
        Date dateFrom,
        Date dateTo,
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
        + "c.phone, "
        + "c.subscription_start, "
        + "c.subscription_end, "
        + "p.code_facture, "
        + "p.status_payment, "
        + "c.status "
        + "FROM payments p "
        + "INNER JOIN centres c "
        + "ON p.centre_code = c.centre_code "
        + "INNER JOIN history_payment h "
        + "ON p.code_facture = h.code_facture "
        + "WHERE p.status_payment='UNPAID' "
        + "AND c.status IN ('PENDING','SUSPENDED') ";



        /*
        ======================================
        DATE FILTER
        date_paiement
        ======================================
        */


/*
======================================
DATE FILTER TAB1
subscription_end
======================================
*/

if(dateFrom != null){

    sql +=
    "AND c.subscription_end >= ? ";

}


if(dateTo != null){

    sql +=
    "AND c.subscription_end <= ? ";

}





        /*
        ======================================
        TEXT SEARCH
        ======================================
        */


        if(search != null
                && !search.trim().isEmpty()){


            sql +=

            "AND ("
            + "p.centre_code LIKE ? "
            + "OR c.name LIKE ? "
            + "OR c.phone LIKE ? "
            + "OR p.code_facture LIKE ? "
            + ") ";


        }





        /*
        ======================================
        ORDER
        ======================================
        */


        if("OLD".equalsIgnoreCase(order)){


            sql +=

            "ORDER BY c.subscription_end DESC ";


        }
        else{


            sql +=

            "ORDER BY c.subscription_end ASC ";


        }




        sql +=

        "LIMIT ? OFFSET ?";





        PreparedStatement ps =

                con.prepareStatement(sql);




        int index = 1;




        if(dateFrom != null){


            ps.setDate(
                    index++,
                    dateFrom
            );


        }



        if(dateTo != null){


            ps.setDate(
                    index++,
                    dateTo
            );


        }






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






        ResultSet rs =

                ps.executeQuery();






        while(rs.next()){


            Payment payment =

                    new Payment();



            payment.setCentreCode(
                    rs.getString("centre_code")
            );


            payment.setCentreName(
                    rs.getString("name")
            );


            payment.setSubscriptionStart(
                    rs.getDate("subscription_start")
            );


            payment.setSubscriptionEnd(
                    rs.getDate("subscription_end")
            );


            payment.setCodeFacture(
                    rs.getString("code_facture")
            );


            payment.setStatusPayment(
                    rs.getString("status_payment")
            );


            payment.setAccountStatus(
                    rs.getString("status")
            );



            payments.add(payment);


        }



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
        catch(Exception e){


            e.printStackTrace();


        }


    }



    return payments;

}


/*
======================================================
GET PAID PAYMENTS

Used by TAB2 (affichage des donnees)

Display centres with active subscriptions

======================================================
*/

public List<Payment> getPaidPayments(
        String search,
        String order,
        Date dateFrom,
        Date dateTo,
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
        + "c.phone, "
        + "c.subscription_start, "
        + "c.subscription_end, "
        + "p.code_facture, "
        + "p.status_payment, "
        + "c.status, "
        + "h.date_paiement "
        + "FROM payments p "

        + "INNER JOIN centres c "
        + "ON p.centre_code=c.centre_code "

        + "INNER JOIN history_payment h "
        + "ON p.code_facture=h.code_facture "

        + "WHERE p.status_payment='PAID' "
        + "AND c.status='ACTIVE' ";





        /*
        ======================================
        DATE FILTER
        TAB2
        date_paiement
        ======================================
        */


        if(dateFrom != null){

            sql +=

            "AND h.date_paiement >= ? ";

        }



        if(dateTo != null){

            sql +=

            "AND h.date_paiement <= ? ";

        }






        /*
        ======================================
        SEARCH
        ======================================
        */


        if(search != null
                && !search.trim().isEmpty()){


            sql +=


            "AND ("
            + "p.centre_code LIKE ? "
            + "OR c.name LIKE ? "
            + "OR c.phone LIKE ? "
            + "OR p.code_facture LIKE ? "
            + ") ";


        }







        /*
        ======================================
        ORDER
        ======================================
        */


        if("OLD".equalsIgnoreCase(order)){


            sql +=

            "ORDER BY h.date_paiement ASC ";


        }
        else{


            sql +=

            "ORDER BY h.date_paiement DESC ";


        }






        sql +=

        "LIMIT ? OFFSET ?";






        PreparedStatement ps =

                con.prepareStatement(sql);




        int index = 1;





        if(dateFrom != null){

            ps.setDate(
                    index++,
                    dateFrom
            );

        }




        if(dateTo != null){

            ps.setDate(
                    index++,
                    dateTo
            );

        }







        if(search != null
                && !search.trim().isEmpty()){


            String keyword =

                    "%"
                    +
                    search.trim()
                    +
                    "%";



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
                (page-1)*pageSize
        );






        ResultSet rs =

                ps.executeQuery();







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



            payment.setPhone(
                    rs.getString(
                            "phone"
                    )
            );



            payment.setSubscriptionStart(
                    rs.getDate(
                            "subscription_start"
                    )
            );



            payment.setSubscriptionEnd(
                    rs.getDate(
                            "subscription_end"
                    )
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
                    rs.getString(
                            "status"
                    )
            );



            payment.setDatePaiement(
                    rs.getDate(
                            "date_paiement"
                    )
            );



            payments.add(
                    payment
            );


        }



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
        catch(Exception e){

            e.printStackTrace();

        }


    }



    return payments;

}



/*
==========================================================
 TAB2
 SUBSCRIPTION UPDATE
==========================================================
*/


public boolean updateSubscription(
        String centreCode,
        String codeFacture,
        String operation,
        int durationMonths
){

    Connection con = null;

    try{

        con = DatabaseConfig.getConnection();

        con.setAutoCommit(false);

        /*
        ==========================================
        PART 1
        UPGRADE
        ==========================================
        */

        if("UPGRADE".equalsIgnoreCase(operation)){

            /*
            ==========================================
            1 - GET SUBSCRIPTION START
            ==========================================
            */

            String sql =

            "SELECT subscription_start "
            + "FROM centres "
            + "WHERE centre_code=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, centreCode);

            ResultSet rs = ps.executeQuery();

            if(!rs.next()){

                con.rollback();

                return false;

            }

            Date subscriptionStart =
                    rs.getDate("subscription_start");

            /*
            ==========================================
            2 - CALCULATE NEW END DATE
            ==========================================
            */

            java.util.Calendar calendar =
                    java.util.Calendar.getInstance();

            calendar.setTime(subscriptionStart);

            calendar.add(
                    java.util.Calendar.MONTH,
                    durationMonths
            );

            Date subscriptionEnd =
                    new Date(
                            calendar.getTimeInMillis()
                    );

            /*
            ==========================================
            3 - UPDATE CENTRES
            ==========================================
            */

            sql =

            "UPDATE centres "
            + "SET subscription_end=? "
            + "WHERE centre_code=?";

            ps = con.prepareStatement(sql);

            ps.setDate(
                    1,
                    subscriptionEnd
            );

            ps.setString(
                    2,
                    centreCode
            );

            if(ps.executeUpdate()==0){

                con.rollback();

                return false;

            }

            /*
            ==========================================
            4 - UPDATE HISTORY
            ==========================================
            */

        sql =

"UPDATE history_payment SET "
+ "date_start=?, "
+ "date_end=?, "
+ "duration_months=?, "
+ "operation_type=? "
+ "WHERE code_facture=?";


ps = con.prepareStatement(sql);

        ps.setDate(1,subscriptionStart);
        ps.setDate(2,subscriptionEnd);
        ps.setInt(3,durationMonths);
        ps.setString(4,"UPGRADE");
        ps.setString(5,codeFacture);







        






if(ps.executeUpdate()==0){

    con.rollback();

    return false;

}

        }

        /*
        ==========================================
        PART 2
        EXTENDED
        ==========================================
        */

    else if("EXTENDED".equalsIgnoreCase(operation)){


    /*
    ==========================================
    1 - GET PAYMENT ID
    ==========================================
    */

    String sql =

    "SELECT p.id "
    + "FROM payments p "
    + "WHERE p.centre_code=?";


    PreparedStatement ps =
            con.prepareStatement(sql);


    ps.setString(
            1,
            centreCode
    );


    ResultSet rs =
            ps.executeQuery();


    if(!rs.next()){

        con.rollback();

        return false;

    }


    int paymentId =
            rs.getInt("id");





    /*
    ==========================================
    2 - CALCULATE NEW END DATE

    Start date comes from Servlet
    End date calculated here

    ==========================================
    */
/*
==========================================
2 - GET CURRENT END DATE

EXTENDED:
newStartDate = current subscription_end

==========================================
*/


sql =

"SELECT subscription_end "
+ "FROM centres "
+ "WHERE centre_code=?";


ps = con.prepareStatement(sql);


ps.setString(1,centreCode);
rs = ps.executeQuery();
if(!rs.next()){

    con.rollback();

    return false;

}


      Date newStartDate = rs.getDate("subscription_end");
      java.util.Calendar calendar = java.util.Calendar.getInstance();
      calendar.setTime(newStartDate);
      calendar.add(java.util.Calendar.MONTH,durationMonths);
      Date newEndDate =new Date(calendar.getTimeInMillis());





    /*
    ==========================================
    3 - UPDATE CENTRES

    New subscription period

    ==========================================
    */


    sql =

    "UPDATE centres "
    + "SET subscription_start=?, "
    + "subscription_end=? "
    + "WHERE centre_code=?";


    ps = con.prepareStatement(sql);


    ps.setDate(
            1,
            newStartDate
    );


    ps.setDate(
            2,
            newEndDate
    );


    ps.setString(
            3,
            centreCode
    );


    if(ps.executeUpdate()==0){

        con.rollback();

        return false;

    }







    /*
    ==========================================
    4 - GENERATE NEW INVOICE

    EXTENDED = new payment record

    ==========================================
    */


    String newInvoiceCode =

            InvoiceCodeGenerator.generateCode(
                    paymentId
            );







    /*
    ==========================================
    5 - UPDATE PAYMENTS

    Replace current invoice

    ==========================================
    */


    sql =

    "UPDATE payments "
    + "SET code_facture=? "
    + "WHERE centre_code=?";


    ps = con.prepareStatement(sql);


    ps.setString(
            1,
            newInvoiceCode
    );


    ps.setString(
            2,
            centreCode
    );


    if(ps.executeUpdate()==0){

        con.rollback();

        return false;

    }








    /*
    ==========================================
    6 - INSERT HISTORY PAYMENT

    New history row

    ==========================================
    */


    sql =

    "INSERT INTO history_payment "
    + "(code_facture, centre_code, date_paiement, "
    + "date_start, date_end, duration_months, operation_type) "
    + "VALUES (?, ?, ?, ?, ?, ?, ?)";


    ps = con.prepareStatement(sql);



    ps.setString(
            1,
            newInvoiceCode
    );


    ps.setString(
            2,
            centreCode
    );


    ps.setDate(
            3,
            new Date(System.currentTimeMillis())
    );


    ps.setDate(
            4,
            newStartDate
    );


    ps.setDate(
            5,
            newEndDate
    );


    ps.setInt(
            6,
            durationMonths
    );


    ps.setString(
            7,
            "EXTENDED"
    );



    if(ps.executeUpdate()==0){

        con.rollback();

        return false;

    }

}

        else{

            con.rollback();

            return false;

        }

        con.commit();

        return true;

    }
    catch(Exception ex){

        ex.printStackTrace();

        try{

            if(con != null){

                con.rollback();

            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

        return false;

    }
    finally{

        try{

            if(con != null){

                con.setAutoCommit(true);

                con.close();

            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

    }

}



/*
======================================================
COUNT UNPAID PAYMENTS

TAB1 BADGE

======================================================
*/


/*
======================================================
COUNT UNPAID PAYMENTS

TAB1 BADGE

======================================================
*/

public int countUnpaidPayments(
        String search,
        Date dateFrom,
        Date dateTo
){

    int count = 0;

    Connection con = null;


    try{


        con = DatabaseConfig.getConnection();



        String sql =

        "SELECT COUNT(*) "
        + "FROM payments p "
        + "INNER JOIN centres c "
        + "ON p.centre_code=c.centre_code "
        + "WHERE p.status_payment='UNPAID' "
        + "AND c.status IN ('PENDING','SUSPENDED') ";



        /*
        ======================================
        DATE FILTER
        TAB1
        subscription_end
        ======================================
        */

        if(dateFrom != null){

            sql +=
            "AND c.subscription_end >= ? ";

        }


        if(dateTo != null){

            sql +=
            "AND c.subscription_end <= ? ";

        }





        /*
        ======================================
        SEARCH
        ======================================
        */

        if(search != null
                && !search.trim().isEmpty()){


            sql +=

            "AND ("
            + "p.centre_code LIKE ? "
            + "OR c.name LIKE ? "
            + "OR c.phone LIKE ? "
            + "OR p.code_facture LIKE ? "
            + ") ";

        }




        PreparedStatement ps =
                con.prepareStatement(sql);



        int index = 1;




        /*
        ======================================
        SET DATE PARAMETERS
        ======================================
        */

        if(dateFrom != null){

            ps.setDate(
                    index++,
                    dateFrom
            );

        }



        if(dateTo != null){

            ps.setDate(
                    index++,
                    dateTo
            );

        }






        /*
        ======================================
        SET SEARCH PARAMETERS
        ======================================
        */

        if(search != null
                && !search.trim().isEmpty()){


            String keyword =
                    "%" + search.trim() + "%";


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




        ResultSet rs =
                ps.executeQuery();



        if(rs.next()){

            count = rs.getInt(1);

        }



    }
    catch(Exception e){

        e.printStackTrace();

    }
    finally{

        try{

            if(con != null)
                con.close();

        }
        catch(Exception e){

            e.printStackTrace();

        }

    }


    return count;

}

/*
======================================================
COUNT PAID PAYMENTS

TAB2 BADGE

======================================================
*/

public int countPaidPayments(
        String search,
        Date dateFrom,
        Date dateTo
){

    int count = 0;

    Connection con = null;


    try{


        con = DatabaseConfig.getConnection();



        String sql =

        "SELECT COUNT(*) "
        + "FROM payments p "
        + "INNER JOIN centres c "
        + "ON p.centre_code=c.centre_code "
        + "INNER JOIN history_payment h "
        + "ON p.code_facture=h.code_facture "
        + "WHERE p.status_payment='PAID' "
        + "AND c.status='ACTIVE' ";




        /*
        ======================================
        DATE FILTER
        TAB2

        date_paiement
        ======================================
        */


        if(dateFrom != null){

            sql +=

            "AND h.date_paiement >= ? ";

        }



        if(dateTo != null){

            sql +=

            "AND h.date_paiement <= ? ";

        }






        /*
        ======================================
        SEARCH
        ======================================
        */


        if(search != null
                && !search.trim().isEmpty()){


            sql +=

            "AND ("
            + "p.centre_code LIKE ? "
            + "OR c.name LIKE ? "
            + "OR c.phone LIKE ? "
            + "OR p.code_facture LIKE ? "
            + ") ";

        }






        PreparedStatement ps =
                con.prepareStatement(sql);



        int index = 1;





        /*
        ======================================
        SET DATE PARAMETERS
        ======================================
        */


        if(dateFrom != null){

            ps.setDate(
                    index++,
                    dateFrom
            );

        }




        if(dateTo != null){

            ps.setDate(
                    index++,
                    dateTo
            );

        }







        /*
        ======================================
        SET SEARCH PARAMETERS
        ======================================
        */


        if(search != null
                && !search.trim().isEmpty()){


            String keyword =
                    "%" + search.trim() + "%";



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





        ResultSet rs =
                ps.executeQuery();



        if(rs.next()){

            count = rs.getInt(1);

        }



    }
    catch(Exception e){

        e.printStackTrace();

    }
    finally{


        try{


            if(con != null)
                con.close();


        }
        catch(Exception e){

            e.printStackTrace();

        }

    }



    return count;

}
/*
======================================================
COUNT HISTORY PAYMENTS

TAB3 BADGE

======================================================
*/

public int countHistoryPayments(
        String search,
        Date dateFrom,
        Date dateTo
){

    int count = 0;

    Connection con = null;


    try{


        con = DatabaseConfig.getConnection();



        String sql =

        "SELECT COUNT(*) "
        + "FROM history_payment h "
        + "INNER JOIN centres c "
        + "ON h.centre_code=c.centre_code "
        + "WHERE 1=1 ";




        /*
        ======================================
        DATE FILTER

        TAB3
        date_paiement
        ======================================
        */


        if(dateFrom != null){

            sql +=

            "AND h.date_paiement >= ? ";

        }



        if(dateTo != null){

            sql +=

            "AND h.date_paiement <= ? ";

        }






        /*
        ======================================
        SEARCH
        ======================================
        */


        if(search != null
                && !search.trim().isEmpty()){


            sql +=

            "AND ("
            + "h.centre_code LIKE ? "
            + "OR c.name LIKE ? "
            + "OR h.code_facture LIKE ? "
            + ") ";

        }





        PreparedStatement ps =
                con.prepareStatement(sql);



        int index = 1;






        /*
        ======================================
        SET DATE PARAMETERS
        ======================================
        */


        if(dateFrom != null){

            ps.setDate(
                    index++,
                    dateFrom
            );

        }




        if(dateTo != null){

            ps.setDate(
                    index++,
                    dateTo
            );

        }






        /*
        ======================================
        SET SEARCH PARAMETERS
        ======================================
        */


        if(search != null
                && !search.trim().isEmpty()){


            String keyword =
                    "%" + search.trim() + "%";



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





        ResultSet rs =
                ps.executeQuery();



        if(rs.next()){

            count = rs.getInt(1);

        }



    }
    catch(Exception e){

        e.printStackTrace();

    }
    finally{


        try{


            if(con != null)
                con.close();


        }
        catch(Exception e){

            e.printStackTrace();

        }

    }



    return count;

}

/*
======================================================
GET HISTORY PAYMENTS

Used by TAB3

Display all payment history records

Example:
Centre A paid 3 times = 3 rows

======================================================
*/

public List<Payment> getHistoryPayments(
        String search,
        String order,
        Date dateFrom,
        Date dateTo,
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
        + "h.centre_code, "
        + "c.name, "
        + "h.code_facture, "
        + "h.date_paiement, "
        + "h.operation_type "
        + "FROM history_payment h "
        + "INNER JOIN centres c "
        + "ON h.centre_code=c.centre_code "
        + "WHERE 1=1 ";




        /*
        ======================================
        DATE FILTER

        history date_paiement

        ======================================
        */


        if(dateFrom != null){

            sql +=

            "AND h.date_paiement >= ? ";

        }


        if(dateTo != null){

            sql +=

            "AND h.date_paiement <= ? ";

        }




        /*
        ======================================
        SEARCH

        centre
        name
        invoice

        ======================================
        */


        if(search != null
                && !search.trim().isEmpty()){


            sql +=

            "AND ("
            + "h.centre_code LIKE ? "
            + "OR c.name LIKE ? "
            + "OR h.code_facture LIKE ? "
            + ") ";

        }





        /*
        ======================================
        ORDER

        NEW = latest payment first

        ======================================
        */


        if("OLD".equalsIgnoreCase(order)){


            sql +=

            "ORDER BY h.date_paiement ASC ";


        }
        else{


            sql +=

            "ORDER BY h.date_paiement DESC ";

        }






        /*
        ======================================
        PAGINATION
        ======================================
        */


        sql +=

        "LIMIT ? OFFSET ?";





        PreparedStatement ps =

                con.prepareStatement(sql);



        int index = 1;





        if(dateFrom != null){

            ps.setDate(
                    index++,
                    dateFrom
            );

        }



        if(dateTo != null){

            ps.setDate(
                    index++,
                    dateTo
            );

        }





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


        }






        ps.setInt(
                index++,
                pageSize
        );



        ps.setInt(
                index,
                (page - 1) * pageSize
        );







        ResultSet rs =

                ps.executeQuery();






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



            payment.setCodeFacture(
                    rs.getString(
                            "code_facture"
                    )
            );



            payment.setDatePaiement(
                    rs.getDate(
                            "date_paiement"
                    )
            );



            payment.setOperationType(
                    rs.getString(
                            "operation_type"
                    )
            );



            payments.add(
                    payment
            );


        }



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
        catch(Exception e){

            e.printStackTrace();

        }

    }



    return payments;

}



/*
======================================================
GET INVOICE BY CODE

Used for printing invoice

Search one payment history record

======================================================
*/

/*
======================================================
GET INVOICE BY CODE

TEMPORARY DISABLED

Printing flow disabled for now.
Method kept because PaymentServlet depends on it.

======================================================
*/

public Payment getInvoiceByCode(
        String codeFacture
){

    return null;

}

}

