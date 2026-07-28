package com.centria.dao;


import com.centria.config.DatabaseConfig;
import com.centria.models.Centre;
import com.centria.utils.CentreCodeGenerator;
import com.centria.utils.PasswordUtil;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;

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
                    " AND (name LIKE ? "
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
                    " AND (name LIKE ? "
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
                "PENDING"
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
    ======================================================
    UPDATE SUBSCRIPTION
    ======================================================
    */


    public boolean updateSubscription(
            int centreId,
            Date endDate
    ){



        String sql =

        "UPDATE centres SET "
        + "subscription_end=?, "
        + "status='ACTIVE' "
        + "WHERE id=?";





        try(

            Connection con =
                    DatabaseConfig.getConnection();


            PreparedStatement ps =
                    con.prepareStatement(sql)

        ){



            ps.setDate(
                    1,
                    endDate
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




}