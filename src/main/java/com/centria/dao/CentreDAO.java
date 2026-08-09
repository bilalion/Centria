package com.centria.dao;


import com.centria.config.DatabaseConfig;
import com.centria.models.Centre;
import com.centria.utils.CentreCodeGenerator;
import com.centria.utils.PasswordUtil;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;



public class CentreDAO {



    /*
    ======================================================
    GET ALL CENTRES
    ======================================================
    */


    public List<Centre> getAllCentres(){

        return searchCentres(
                "",
                "ALL",
                "NEW"
        );

    }






    /*
    ======================================================
    SEARCH CENTRES (OLD VERSION)
    ======================================================
    */


    public List<Centre> searchCentres(
            String search,
            String status,
            String order
    ){

        return searchCentres(
                search,
                status,
                order,
                1,
                10
        );

    }







    /*
    ======================================================
    SEARCH CENTRES WITH PAGINATION
    ======================================================
    */


    public List<Centre> searchCentres(
            String search,
            String status,
            String order,
            int page,
            int pageSize
    ){


        List<Centre> centres =
                new ArrayList<>();



        StringBuilder sql =
                new StringBuilder();



        sql.append(
                "SELECT * FROM centres WHERE 1=1 "
        );





        if(search != null &&
           !search.trim().isEmpty()){


          sql.append(
            " AND (centre_code LIKE ? "
            + "OR name LIKE ? "
            + "OR owner_name LIKE ? "
            + "OR username LIKE ? "
            + "OR phone LIKE ?) "
    );

        }






        if(status != null &&
           !status.equals("ALL")){


            sql.append(
                    " AND status=? "
            );


        }





        if(order == null){

            order="NEW";

        }





        switch(order){


            case "OLD":

                sql.append(
                        " ORDER BY id ASC "
                );

                break;



            case "NAME":

                sql.append(
                        " ORDER BY name ASC "
                );

                break;



            default:

                sql.append(
                        " ORDER BY id DESC "
                );

                break;


        }







        int offset =
                (page - 1) * pageSize;





        sql.append(
                " LIMIT ? OFFSET ? "
        );






        try(

            Connection con =
                    DatabaseConfig.getConnection();


            PreparedStatement ps =
                    con.prepareStatement(
                            sql.toString()
                    )

        ){



            int index = 1;





            if(search != null &&
               !search.trim().isEmpty()){


                String value =
                        "%" + search + "%";


ps.setString(
        index++,
        value
); // centre_code


ps.setString(
        index++,
        value
); // name


ps.setString(
        index++,
        value
); // owner_name


ps.setString(
        index++,
        value
); // username


ps.setString(
        index++,
        value
); // phone


            }






            if(status != null &&
               !status.equals("ALL")){


                ps.setString(
                        index++,
                        status
                );


            }






            ps.setInt(
                    index++,
                    pageSize
            );



            ps.setInt(
                    index++,
                    offset
            );







            ResultSet rs =
                    ps.executeQuery();






            while(rs.next()){



                Centre centre =
                        new Centre();




                centre.setId(
                        rs.getInt("id")
                );



                // NEW : Centre unique code

                centre.setCentreCode(
                        rs.getString("centre_code")
                );



                centre.setName(
                        rs.getString("name")
                );



                centre.setOwnerName(
                        rs.getString("owner_name")
                );



                centre.setUsername(
                        rs.getString("username")
                );



                centre.setPasswordHash(
                        rs.getString("password_hash")
                );



                centre.setPhone(
                        rs.getString("phone")
                );



                centre.setSubscriptionStart(
                        rs.getDate("subscription_start")
                );



                centre.setSubscriptionEnd(
                        rs.getDate("subscription_end")
                );



                centre.setStatus(
                        rs.getString("status")
                );



                centre.setCreatedAt(
                        rs.getTimestamp("created_at")
                );



                centre.setMustChangePassword(
                        rs.getBoolean("must_change_password")
                );



                centre.setLastLogin(
                        rs.getTimestamp("last_login")
                );



                centres.add(centre);


            }



        }


        catch(Exception e){


            e.printStackTrace();


        }




        return centres;


    }
    
    
    /*
======================================================
GET CENTRE BY ID
======================================================
*/

public Centre getCentreById(int id){


    Centre centre = null;



    String sql =
            "SELECT * FROM centres WHERE id=?";



    try(

        Connection con =
                DatabaseConfig.getConnection();


        PreparedStatement ps =
                con.prepareStatement(sql)

    ){


        ps.setInt(
                1,
                id
        );



        ResultSet rs =
                ps.executeQuery();




        if(rs.next()){


            centre = new Centre();



            centre.setId(
                    rs.getInt("id")
            );


            centre.setCentreCode(
                    rs.getString("centre_code")
            );


            centre.setName(
                    rs.getString("name")
            );


            centre.setOwnerName(
                    rs.getString("owner_name")
            );


            centre.setUsername(
                    rs.getString("username")
            );


            /*
              لا نعرض كلمة المرور في الواجهة
              لكنها تبقى داخل Model إذا احتجناها
            */
            centre.setPasswordHash(
                    rs.getString("password_hash")
            );



            centre.setPhone(
                    rs.getString("phone")
            );


            centre.setSubscriptionStart(
                    rs.getDate("subscription_start")
            );


            centre.setSubscriptionEnd(
                    rs.getDate("subscription_end")
            );


            centre.setStatus(
                    rs.getString("status")
            );


            centre.setCreatedAt(
                    rs.getTimestamp("created_at")
            );


            centre.setMustChangePassword(
                    rs.getBoolean("must_change_password")
            );


            centre.setLastLogin(
                    rs.getTimestamp("last_login")
            );



        }



    }


    catch(Exception e){


        e.printStackTrace();


    }




    return centre;


}
    
    
    
    
    
        /*
    ======================================================
    COUNT CENTRES
    ======================================================
    */


    public int countCentres(
            String search,
            String status
    ){


        int count = 0;



        StringBuilder sql =
                new StringBuilder();



        sql.append(
                "SELECT COUNT(*) FROM centres WHERE 1=1 "
        );





        if(search != null &&
           !search.trim().isEmpty()){


         sql.append(
        " AND (centre_code LIKE ? "
        + "OR name LIKE ? "
        + "OR owner_name LIKE ? "
        + "OR username LIKE ? "
        + "OR phone LIKE ?) "
);


        }






        if(status != null &&
           !status.equals("ALL")){


            sql.append(
                    " AND status=? "
            );


        }






        try(

            Connection con =
                    DatabaseConfig.getConnection();


            PreparedStatement ps =
                    con.prepareStatement(
                            sql.toString()
                    )

        ){



            int index = 1;





            if(search != null &&
               !search.trim().isEmpty()){


                String value =
                        "%" + search + "%";


               ps.setString(
                        index++,
                        value
                );
                ps.setString(
                        index++,
                        value
                );


                ps.setString(
                        index++,
                        value
                );


                ps.setString(
                        index++,
                        value
                );


                ps.setString(
                        index++,
                        value
                );


            }






            if(status != null &&
               !status.equals("ALL")){


                ps.setString(
                        index++,
                        status
                );


            }






            ResultSet rs =
                    ps.executeQuery();






            if(rs.next()){


                count =
                        rs.getInt(1);


            }




        }


        catch(Exception e){


            e.printStackTrace();


        }





        return count;


    }









    /*
    ======================================================
    ADD CENTRE
    ======================================================
    */


   public boolean addCentre(Centre centre){


    String insertSql =

    "INSERT INTO centres "
    + "(name,owner_name,username,password_hash,"
    + "phone,subscription_start,subscription_end,"
    + "status,must_change_password) "
    + "VALUES (?,?,?,?,?,?,?,?,1)";



    String updateSql =

    "UPDATE centres SET "
    + "centre_code=?, username=? "
    + "WHERE id=?";



    try(

        Connection con =
                DatabaseConfig.getConnection();


        PreparedStatement ps =
                con.prepareStatement(
                        insertSql,
                        java.sql.Statement.RETURN_GENERATED_KEYS
                )

    ){


        ps.setString(
                1,
                centre.getName()
        );


        ps.setString(
                2,
                centre.getOwnerName()
        );


        /*
          username مؤقت
          سيتم تغييره بعد توليد id
        */
        ps.setString(
                3,
                "TEMP"
        );


        ps.setString(
                4,
                centre.getPasswordHash()
        );


        ps.setString(
                5,
                centre.getPhone()
        );


        ps.setDate(
                6,
                centre.getSubscriptionStart()
        );


        ps.setDate(
                7,
                centre.getSubscriptionEnd()
        );


        ps.setString(
                8,
                "ACTIVE"
        );



        int result =
                ps.executeUpdate();



        if(result == 0){
            return false;
        }




        ResultSet rs =
                ps.getGeneratedKeys();



        if(rs.next()){


            int id =
                    rs.getInt(1);



            String code =
                    CentreCodeGenerator.generateCode(id);



            String username =
                    CentreCodeGenerator.generateUsername(code);



            PreparedStatement update =
                    con.prepareStatement(
                            updateSql
                    );



            update.setString(
                    1,
                    code
            );


            update.setString(
                    2,
                    username
            );


            update.setInt(
                    3,
                    id
            );


            update.executeUpdate();



            centre.setId(id);
            centre.setCentreCode(code);
            centre.setUsername(username);



            return true;


        }



    }

    catch(Exception e){

        e.printStackTrace();

    }



    return false;


}








    /*
    ======================================================
    RESET PASSWORD
    ======================================================
    */


    public boolean resetPassword(
            int centreId,
            String newPassword
    ){


        String sql =

        "UPDATE centres SET "
        + "password_hash=?, "
        + "must_change_password=1 "
        + "WHERE id=?";





        try(

            Connection con =
                    DatabaseConfig.getConnection();


            PreparedStatement ps =
                    con.prepareStatement(sql)

        ){



            ps.setString(
                    1,
                    PasswordUtil.hashPassword(
                            newPassword
                    )
            );



            ps.setInt(
                    2,
                    centreId
            );



            return ps.executeUpdate() > 0;



        }


        catch(Exception e){


            e.printStackTrace();


        }





        return false;


    }









    /*
    ======================================================
    UPDATE STATUS
    ======================================================
    */


    public boolean updateStatus(
            int centreId,
            String status
    ){


        String sql =

        "UPDATE centres SET status=? WHERE id=?";





        try(

            Connection con =
                    DatabaseConfig.getConnection();


            PreparedStatement ps =
                    con.prepareStatement(sql)

        ){



            ps.setString(
                    1,
                    status
            );



            ps.setInt(
                    2,
                    centreId
            );



            return ps.executeUpdate() > 0;



        }


        catch(Exception e){


            e.printStackTrace();


        }





        return false;


    }









   

   

    
    /*
 * ======================================================
 * UPDATE CENTRE PROFILE
 * Used by Edit Dialog
 * ======================================================
 */
public boolean updateCentreProfile(Centre centre){


    if(centre == null){

        return false;

    }



    String sql =

    "UPDATE centres SET "
    + "name=?, "
    + "owner_name=?, "
    + "phone=? "
    + "WHERE id=?";




    try(
        Connection con =
                DatabaseConfig.getConnection();


        PreparedStatement ps =
                con.prepareStatement(sql)

    ){



        ps.setString(
                1,
                centre.getName()
        );


        ps.setString(
                2,
                centre.getOwnerName()
        );


        ps.setString(
                3,
                centre.getPhone()
        );


        ps.setInt(
                4,
                centre.getId()
        );



        int rows =
                ps.executeUpdate();



        return rows > 0;



    }
    catch(Exception e){


        System.err.println(
            "UPDATE CENTRE PROFILE ERROR"
        );


        e.printStackTrace();


    }



    return false;

}


    /*
======================================================
ACTIVATE CENTRE AFTER PAYMENT

Called after payment confirmation.

Updates centres table:

- subscription_start = new payment date
- subscription_end = start + duration
- status = ACTIVE

======================================================
*/

public boolean activateCentreAfterPayment(
        String centreCode,
        int durationMonths
){


    String sql =

    "UPDATE centres SET "
    + "subscription_start=?, "
    + "subscription_end=?, "
    + "status=? "
    + "WHERE centre_code=?";




    try(

        Connection con =
                DatabaseConfig.getConnection();


        PreparedStatement ps =
                con.prepareStatement(sql)

    ){



        /*
        New subscription start date
        = Today
        */

        Date startDate =

                new Date(
                    System.currentTimeMillis()
                );



        /*
        Calculate new end date
        */

        LocalDate end =

                startDate.toLocalDate()
                .plusMonths(durationMonths);



        Date endDate =

                Date.valueOf(end);





        ps.setDate(
                1,
                startDate
        );



        ps.setDate(
                2,
                endDate
        );



        ps.setString(
                3,
                "ACTIVE"
        );



        ps.setString(
                4,
                centreCode
        );




        return ps.executeUpdate() > 0;



    }
    catch(Exception e){


        e.printStackTrace();


    }



    return false;


}



/*
======================================================
ACCOUNT STATUS MONITOR
======================================================
*/

public int monitorExpiredActiveCentres(){

    String sql =
        "UPDATE centres SET status='PENDING' " +
        "WHERE status='ACTIVE' " +
        "AND subscription_end < CURRENT_DATE";

    try(
        Connection con =
                DatabaseConfig.getConnection();

        PreparedStatement ps =
                con.prepareStatement(sql)
    ){

        return ps.executeUpdate();

    }
    catch(Exception e){

        e.printStackTrace();

    }

    return 0;
}

public int monitorPendingCentres(int graceDays) {

    String sql =
            "UPDATE centres " +
            "SET status = 'SUSPENDED' " +
            "WHERE status = 'PENDING' " +
            "AND subscription_end < DATE_SUB(CURRENT_DATE, INTERVAL ? DAY)";

    try (
            Connection con = DatabaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
    ) {

        ps.setInt(1, graceDays);

        return ps.executeUpdate();

    }
    catch (Exception e) {

        e.printStackTrace();

    }

    return 0;
}

public int monitorSuspendedCentres(int archiveDays) {

    String sql =
            "UPDATE centres " +
            "SET status = 'ARCHIVED' " +
            "WHERE status = 'SUSPENDED' " +
            "AND subscription_end < DATE_SUB(CURRENT_DATE, INTERVAL ? DAY)";

    try (
            Connection con = DatabaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
    ) {

        ps.setInt(1, archiveDays);

        return ps.executeUpdate();

    }
    catch (Exception e) {

        e.printStackTrace();

    }

    return 0;
}
}