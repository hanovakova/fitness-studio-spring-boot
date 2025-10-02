<!DOCTYPE html>
<html lang="en">
<head>
    <title>Purchased Classes</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<div class="container">
    <#include "headerView.ftl">
    <h1>Classes You Purchased</h1>
    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Start Time</th>
            <th>End Time</th>
            <th>Instructor</th>
            <th>Price</th>
        </tr>
        </thead>
        <tbody>
        <#list purchasedClasses as enrolledClass>
            <tr>
                <td>${enrolledClass.name}</td>
                <td>${enrolledClass.description}</td>
                <td>${enrolledClass.startTime}</td>
                <td>${enrolledClass.endTime}</td>
                <td>${enrolledClass.instructorName}</td>
                <td>${enrolledClass.price}</td>
            </tr>
        </#list>
        </tbody>
    </table>
    <p class="center"><a href="/fitnessClasses">View other classes</a></p>
</div>
</body>
</html>