<!DOCTYPE html>
<html lang="en">
<head>
    <title>Available Fitness Classes</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <script src="/static/js/signup.js"></script>
</head>
<body>
<#include "headerView.ftl">

<h1>Fitness Classes</h1>
<div class="class-container">
    <div class="filters">
        <h3>Filters</h3>
        <form id="filterForm" action="/fitnessClasses" method="get">
            <label for="timeSelect">Time</label>
            <select id="timeSelect" name="classTime">
                <option value="" <#if (classTime!"") == "">selected</#if>>All</option>
                <#list timeOptions as opt>
                    <option value="${opt}" <#if (classTime!"") == opt>selected</#if>>${opt}</option>
                </#list>
            </select>

            <label for="classTypeSelect">Class Type</label>
            <select id="classTypeSelect" name="classType">
                <option value="" <#if (classType!"") == "">selected</#if>>All</option>
                <#list typeOptions?keys as key>
                    <option value="${key}" <#if (classType!"") == key>selected</#if>>${typeOptions[key]}</option>
                </#list>
            </select>

            <div class="filter-buttons">
                <button type="submit">Apply</button>
                <button type="button" id="resetFilters">Reset</button>

                <script>
                    document.getElementById("resetFilters").addEventListener("click", function() {
                        // reset form fields
                        document.getElementById("filterForm").reset();
                        // submit form to reload with no params
                        window.location.href = "/fitnessClasses";
                    });
                </script>
            </div>
        </form>
    </div>
    <div class="table-container">
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
                        <#if classIdsPaid[fitnessClass.id?string]!false>
                            <span style="color: gray; font-weight: bold;">Purchased</span>
                        <#elseif classIdsCapacityExceeded[fitnessClass.id?string]!false>
                            <span style="color: gray; font-weight: bold;">Class is Full</span>
                        <#elseif (loggedIn!false == false)>
                            <span style="color: gray; font-weight: bold;">Log In First</span>
                        <#else>
                            <form class="table-form">
                                <input type="hidden" name="classId" value="${fitnessClass.id}"/>
                                <button class="table-button"
                                        onclick="signup(${fitnessClass.id}, this, '', event)">Sign Up
                                </button>
                            </form>
                        </#if>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</div>

<p><a href="/user-classes/selected">View your classes</a></p>

</body>
</html>