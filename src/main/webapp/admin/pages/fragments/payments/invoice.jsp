<%@page import="java.lang.String"%>
<%@page import="com.centria.language.LanguageManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.centria.models.Payment"%>
<%
java.util.Calendar calendar = java.util.Calendar.getInstance();

int currentYear = calendar.get(java.util.Calendar.YEAR);
%>

<%
Payment payment = (Payment) request.getAttribute("payment");

String lang = (String)session.getAttribute("lang");

if(lang == null){
    lang = "en";
}

String dir = lang.equals("ar") ? "rtl" : "ltr";
%>

<!DOCTYPE html>

<html lang="<%=lang%>" dir="<%=dir%>">

<head>

<meta charset="UTF-8">

<title>CENTRIA Invoice</title>

<style>

*{
    margin:0;
    padding:0;
    box-sizing:border-box;
}


body{

    font-family:Arial,"Segoe UI",sans-serif;

    background:#eef2f7;

    padding:40px;

}


/* ==========================
   INVOICE CONTAINER
========================== */


.invoice{

    position:relative;

    overflow:hidden;

    width:850px;

    margin:auto;

    background:#ffffff;

    border-radius:16px;

    box-shadow:
    0 10px 30px rgba(15,35,70,.15);

    padding:60px 45px 40px;

}


/* ==========================
   STATUS RIBBON
========================== */


.invoice-status{

    position:absolute;

    top:20px;

    right:-60px;

    width:220px;

    padding:11px 0;


    background:#16a34a;

    color:white;


    text-align:center;

    font-size:18px;

    font-weight:700;

    letter-spacing:2px;

    text-transform:uppercase;


    transform:rotate(45deg);


    box-shadow:
    0 5px 15px rgba(0,0,0,.25);


    z-index:10;

}


/* RTL Ribbon */

html[dir="rtl"] .invoice-status{

    right:auto;

    left:-60px;

    transform:rotate(-45deg);

}



/* ==========================
   HEADER
========================== */


.header{


    display:flex;

    align-items:center;

    justify-content:space-between;


    padding-bottom:25px;

    margin-bottom:35px;


    border-bottom:

    3px solid #2563eb;


}



.logo{


    width:105px;

    height:105px;


    object-fit:contain;

    flex-shrink:0;


}



.title{


    flex:1;

    text-align:center;


}



.title h1{


    color:#1e40af;


    font-size:40px;

    font-weight:800;


    letter-spacing:1px;


    margin-bottom:8px;


}



.title p{


    color:#64748b;


    font-size:16px;

    font-weight:500;


}



/* ==========================
   SECTIONS
========================== */


.section-title{


    font-size:19px;


    color:#1e40af;


    margin-top:25px;

    margin-bottom:15px;


    border-left:

    5px solid #2563eb;


    padding-left:12px;


    font-weight:700;


    text-align:left;


}



/* RTL */

html[dir="rtl"] .section-title{


    border-left:none;

    border-right:

    5px solid #2563eb;


    padding-left:0;

    padding-right:12px;


    text-align:right;


}



/* ==========================
   TABLE
========================== */


table{


    width:100%;


    border-collapse:collapse;


    overflow:hidden;


    border-radius:8px;


}



td{


    padding:13px;


    border-bottom:

    1px solid #e2e8f0;


    text-align:left;


    color:#334155;


}



td:first-child{


    width:240px;


    font-weight:700;


    color:#1e293b;


    background:#f8fafc;


}



/* RTL TABLE */

html[dir="rtl"] td{


    text-align:right;


}




/* ==========================
   FOOTER
========================== */


.footer{


    margin-top:45px;


    padding-top:20px;


    border-top:

    2px solid #e2e8f0;


    text-align:center;


    color:#64748b;


    font-size:14px;


    line-height:1.8;


}



.footer strong{


    display:block;


    color:#2563eb;


    font-size:17px;


    margin-bottom:8px;


}



.footer hr{


    width:60%;


    margin:15px auto;


    border:none;


    border-top:

    1px solid #cbd5e1;


}



/* ==========================
   PRINT BUTTON
========================== */


.print-btn{


    display:block;


    margin:35px auto 0;


    padding:13px 40px;


    border:none;


    border-radius:8px;


    background:#2563eb;


    color:white;


    font-size:16px;


    font-weight:600;


    cursor:pointer;


    transition:.3s;


}



.print-btn:hover{


    background:#1d4ed8;


    transform:translateY(-2px);


}



/* ==========================
   PRINT MODE
========================== */


@media print{


    body{


        background:white;

        padding:0;


    }



    .invoice{


        width:100%;


        box-shadow:none;


        border:none;


        padding:45px 25px 25px;


    }



    .print-btn{


        display:none;


    }


}



</style>

</head>

<body>

<div class="invoice">

<div class="invoice-status">

<%=LanguageManager.get("invoice.paid", session)%>

</div>

<div class="header">

<div>

<img
src="<%=request.getContextPath()%>/assets/images/centria-logo.png"
class="logo"
alt="CENTRIA">

</div>

<div class="title">

<h1><%=LanguageManager.get("invoice.title", session)%></h1>

<p><%=LanguageManager.get("invoice.subtitle", session)%></p>

</div>

</div>

<%
if(payment!=null){
%>

<div class="section-title">

<%=LanguageManager.get("invoice.section.invoice", session)%>

</div>

<table>

<tr>

<td><%=LanguageManager.get("invoice.code", session)%></td>

<td><%=payment.getCodeFacture()%></td>

</tr>

<tr>

<td><%=LanguageManager.get("invoice.paymentDate", session)%></td>

<td><%=payment.getDatePaiement()%></td>

</tr>

<tr>

<td>
<%=LanguageManager.get("invoice.operation", session)%>
</td>

<td>

<%
String operation = payment.getOperationType();

String operationText = "-";


if(operation != null){

    if("EXTENDED".equalsIgnoreCase(operation)){

        operationText =
        LanguageManager.get(
            "payments.extended",
            session
        );

    }
    else if("UPGRADE".equalsIgnoreCase(operation)){

        operationText =
        LanguageManager.get(
            "payments.upgrade",
            session
        );

    }
    else if("NEW".equalsIgnoreCase(operation)){

        operationText =
        LanguageManager.get(
            "payments.operation.new",
            session
        );

    }
    else if("INITIAL".equalsIgnoreCase(operation)){

        operationText =
        LanguageManager.get(
            "payments.operation.initial",
            session
        );

    }

}


%>

<%=operationText%>

</td>

</tr>

</table>

<div class="section-title">

<%=LanguageManager.get("invoice.section.centre", session)%>

</div>

<table>

<tr>

<td><%=LanguageManager.get("invoice.centreCode", session)%></td>

<td><%=payment.getCentreCode()%></td>

</tr>

<tr>

<td><%=LanguageManager.get("invoice.centreName", session)%></td>

<td><%=payment.getCentreName()%></td>

</tr>

<tr>

<td><%=LanguageManager.get("invoice.phone", session)%></td>

<td><%=payment.getPhone()%></td>

</tr>

</table>

<div class="section-title">

<%=LanguageManager.get("invoice.section.subscription", session)%>

</div>

<table>

<tr>

<td><%=LanguageManager.get("invoice.startDate", session)%></td>

<td><%=payment.getSubscriptionStart()%></td>

</tr>

<tr>

<td><%=LanguageManager.get("invoice.endDate", session)%></td>

<td><%=payment.getSubscriptionEnd()%></td>

</tr>

<tr>

<td><%=LanguageManager.get("invoice.duration", session)%></td>

<td>
<%=payment.getDurationMonths()%>
<%=LanguageManager.get("invoice.months", session)%>
</td>

</tr>

</table>

<div class="footer">

<p>

<strong>

<%=LanguageManager.get("invoice.thankYou", session)%>

</strong>

</p>


<p>

<%=LanguageManager.get("invoice.footer", session)%>

</p>


<hr>


<p>

CENTRIA © <%=currentYear%> |

+212 600 000 000 |

contact@centria.com

</p>


</div>

<%
}else{
%>

<h2 style="text-align:center;color:#d32f2f;">

<%=LanguageManager.get("invoice.notFound", session)%>

</h2>

<%
}
%>

<button
class="print-btn"
onclick="window.print()">

🖨 <%=LanguageManager.get("invoice.print", session)%>

</button>

</div>

</body>

</html>