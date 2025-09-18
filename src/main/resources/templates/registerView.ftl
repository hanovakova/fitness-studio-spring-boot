<!DOCTYPE html>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" type="text/css" href="/static/css/style.css">
</head>
<body>
<div class="container">
    <#include "headerView.ftl">
    <form class="styled-form" action="/register" method="POST" enctype="multipart/form-data">
        <h2>${messages["registration.title"]!}</h2>

        <label>${messages["registration.username"]!}
            <input type="text" id="username" name="username"
                   value="${username!}"
                   class="<#if errors.username??>error-input</#if>"
                   required>
            <span class="err">${errors.username!}</span>
        </label>

        <label>${messages["registration.password"]!}
            <input type="password" id="password1" name="password1"
                   class="<#if errors.password1??>error-input</#if>"
                   required>
            <span class="err">${errors.password1!}</span>
        </label>

        <label>${messages["registration.repeat_password"]!}
            <input type="password" id="password2" name="password2"
                   class="<#if errors.password2??>error-input</#if>"
                   required>
            <span class="err">${errors.password2!}</span>
        </label>

        <label>${messages["registration.full_name"]!}
            <input type="text" id="name" name="name"
                   value="${name!}"
                   class="<#if errors.name??>error-input</#if>"
                   required>
            <span class="err">${errors.name!}</span>
        </label>

        <label>${messages["registration.email"]!}
            <input type="text" id="email" name="email"
                   value="${email!}"
                   class="<#if errors.email??>error-input</#if>"
                   required>
            <span class="err">${errors.email!}</span>
        </label>

        <label>${messages["registration.advertisement"]!}
            <input type="checkbox" name="advert" value="yes"
                   <#if advert?? && advert>checked</#if>>
        </label>

        <label>${messages["registration.avatar"]!}
            <input type="file" name="avatar">
        </label>

        <button class="styled-button" type="submit">
            ${messages["registration.submit"]!}
        </button>

        <p style="text-align:center; margin-top: 20px;">
            ${messages["registration.question"]!}
            <a href="/login">${messages["registration.login"]!}</a>
        </p>
    </form>
</div>
</body>
</html>