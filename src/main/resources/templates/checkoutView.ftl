<!DOCTYPE html>
<html>
<head>
    <title>Checkout</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<div class="container">
    <#include "headerView.ftl">
    <form class="styled-form" action="/checkout" method="post">
        <h2>Checkout</h2>
        <table>
            <thead>
            <tr>
                <th>Name</th>
                <th>Price</th>
            </tr>
            </thead>
            <tbody>
            <#assign totalPrice=0>
            <#list enrolledClasses as enrolledClass>
                <tr>
                    <td>${enrolledClass.name}</td>
                    <td>${enrolledClass.price}</td>
                </tr>
                <#assign totalPrice = totalPrice + enrolledClass.price>
            </#list>
            <tr>
                <td><strong>Total</strong></td>
                <td><strong>${totalPrice}</strong></td>
            </tr>
            </tbody>
        </table>

        <label for="fullName">Name On The Card:</label>
        <input type="text" id="fullName" name="fullName" required/>

        <label for="creditCard">Credit Card Number:</label>
        <input type="text"
               id="creditCard"
               name="creditCard"
               required
               maxlength="16"
               pattern="\\d{16}"
               inputmode="numeric"
               title="Please enter a 16-digit credit card number"/>

        <button class="styled-button" type="submit">Confirm Payment</button>
    </form>
</div>
</body>
</html>