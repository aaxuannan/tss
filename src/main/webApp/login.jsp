<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <body>
        <form action="/tss/tss/sen?page=1&size=5" method="post">
            <div style="text-align: center">
                <h2>请输入您的查询调教：</h2>
                <input  type="text" name="context"/>
                <strong>输入查询的诗人名称（选填）</strong>
                <input type="text" name="aut">
                <br>
                <input  type="submit"  value="点击查询"/>
            </div>
        </form>
    </body>
</html>
