<!DOCTYPE html>
<html lang="en">
<head>
    <title>Available Fitness Classes</title>
    <link rel="stylesheet" type="text/css" href="/static/css/style.css">
    <script src="/static/js/signup.js"></script>
</head>
<body>
<#include "headerView.ftl">

<h1>Fitness Classes</h1>

<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>Image</th>
        <th>Description</th>
        <th>Start Time</th>
        <th>End Time</th>
        <th>Instructor</th>
        <th>Price</th>
        <th>Yoga Level</th>
        <th>Bike Type</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <#list fitnessClasses as fitnessClass>
        <tr>
            <td>${fitnessClass.name}</td>
            <td>
                <img src="/${fitnessClass.imagePath}" class="class-image" alt="Class Image"/>
            </td>
            <td>${fitnessClass.description}</td>
            <td>${fitnessClass.startTime}</td>
            <td>${fitnessClass.endTime}</td>
            <td>${fitnessClass.instructorName}</td>
            <td>${fitnessClass.price}</td>
            <td>
                <#if fitnessClass.classType == "YogaClass">
                    ${fitnessClass.yogaLevel}
                <#else>-</#if>
            </td>
            <td>
                <#if fitnessClass.classType == "SpinningClass">
                    ${fitnessClass.bikeType}
                <#else>-</#if>
            </td>
            <td>
                <#-- Handle actions -->
                <#if classIdsPaid[fitnessClass.id]?default(false)>
                    <span style="color: gray; font-weight: bold;">Purchased</span>
                <#elseif classIdsCapacityExceeded[fitnessClass.id]?default(false)>
                    <span style="color: gray; font-weight: bold;">Class is Full</span>
                <#elseif (loggedIn?default(false) == false)>
                    <span style="color: gray; font-weight: bold;">Log In First</span>
                <#else>
                    <form class="table-form">
                        <input type="hidden" name="classId" value="${fitnessClass.id}"/>
                        <button class="table-button"
                                onclick="signup(${fitnessClass.id}, this, '', event)">Sign Up</button>
                    </form>
                </#if>
            </td>
        </tr>
    </#list>
    </tbody>
</table>

<p><a href="/user-classes/selected">View your classes</a></p>

</body>
</html>