<%@page import="com.centria.language.LanguageManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.centria.models.Payment"%>

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

    font-family:Arial,sans-serif;
    background:#eceff3;
    padding:40px;

}

.invoice{

    position:relative;
    overflow:hidden;

    width:850px;
    margin:auto;

    background:#fff;

    border-radius:10px;

    box-shadow:0 4px 20px rgba(0,0,0,.15);

    padding:60px 40px 40px;

}

.invoice-status{

    position:absolute;

    top:18px;
    right:-60px;

    width:210px;

    padding:10px 0;

    background:#2e7d32;

    color:#fff;

    text-align:center;

    font-size:18px;

    font-weight:bold;

    letter-spacing:2px;

    text-transform:uppercase;

    transform:rotate(45deg);

    box-shadow:0 3px 10px rgba(0,0,0,.25);

    z-index:10;

}

/* RTL Ribbon */

html[dir="rtl"] .invoice-status{

    right:auto;
    left:-60px;

    transform:rotate(-45deg);

}

.header{

    display:flex;

    align-items:center;

    justify-content:space-between;

    border-bottom:2px solid #1976d2;

    padding-bottom:20px;

    margin-bottom:35px;

}

.logo{

    width:90px;
    height:90px;

    object-fit:contain;
    flex-shrink:0;

}

.title{

    flex:1;

    text-align:center;

}

.title h1{

    color:#1976d2;

    font-size:34px;

    margin-bottom:5px;

}

.title p{

    color:#777;

    font-size:15px;

}

.section-title{

    font-size:18px;

    color:#1976d2;

    margin-top:20px;

    margin-bottom:15px;

    border-left:4px solid #1976d2;

    padding-left:10px;

    text-align:left;

}

/* RTL Section */

html[dir="rtl"] .section-title{

    border-left:none;
    border-right:4px solid #1976d2;

    padding-left:0;
    padding-right:10px;

    text-align:right;

}

table{

    width:100%;

    border-collapse:collapse;

}

td{

    padding:12px;

    border-bottom:1px solid #e5e5e5;

    text-align:left;

}

td:first-child{

    width:240px;

    font-weight:bold;

    background:#fafafa;

}

/* RTL Table */

html[dir="rtl"] td{

    text-align:right;

}

.footer{

    margin-top:40px;

    text-align:center;

    color:#666;

    font-size:14px;

}

.print-btn{

    display:block;

    margin:35px auto 0;

    padding:12px 35px;

    border:none;

    border-radius:6px;

    background:#1976d2;

    color:#fff;

    font-size:16px;

    cursor:pointer;

}

.print-btn:hover{

    background:#1565c0;

}

@media print{

    body{

        background:#fff;

        padding:0;

    }

    .invoice{

        width:100%;

        box-shadow:none;

        border:none;

        padding:50px 25px 25px;

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

String operationText = operation;

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