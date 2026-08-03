<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.centria.models.Payment"%>

<%
Payment payment = (Payment) request.getAttribute("payment");
%>

<!DOCTYPE html>

<html>

<head>

    <meta charset="UTF-8">

    <title>Invoice</title>

    <style>

        body{

            font-family:Arial,sans-serif;

            background:#f5f5f5;

            margin:40px;

        }

        .invoice{

            max-width:800px;

            margin:auto;

            background:#fff;

            padding:30px;

            border-radius:8px;

            box-shadow:0 2px 8px rgba(0,0,0,.15);

        }

        h1{

            margin-bottom:30px;

        }

        table{

            width:100%;

            border-collapse:collapse;

        }

        td{

            padding:10px;

            border-bottom:1px solid #ddd;

        }

        td:first-child{

            width:220px;

            font-weight:bold;

        }

        .print-btn{

            margin-top:30px;

            padding:12px 25px;

            cursor:pointer;

        }

    </style>

</head>

<body>

<div class="invoice">

    <h1>Invoice</h1>

<%
if(payment!=null){
%>

<table>

<tr>

<td>Invoice Code</td>

<td><%=payment.getCodeFacture()%></td>

</tr>

<tr>

<td>Centre Code</td>

<td><%=payment.getCentreCode()%></td>

</tr>

<tr>

<td>Centre Name</td>

<td><%=payment.getCentreName()%></td>

</tr>

<tr>

<td>Phone</td>

<td><%=payment.getPhone()%></td>

</tr>

<tr>

<td>Payment Date</td>

<td><%=payment.getDatePaiement()%></td>

</tr>

<tr>

<td>Start Date</td>

<td><%=payment.getSubscriptionStart()%></td>

</tr>

<tr>

<td>End Date</td>

<td><%=payment.getSubscriptionEnd()%></td>

</tr>

<tr>

<td>Duration</td>

<td><%=payment.getDurationMonths()%> Month(s)</td>

</tr>

<tr>

<td>Operation</td>

<td><%=payment.getOperationType()%></td>

</tr>

</table>

<%
}else{
%>

<h3>Invoice not found.</h3>

<%
}
%>

<button
class="print-btn"
onclick="window.print()">

🖨 Print

</button>

</div>

</body>

</html>