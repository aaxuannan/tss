<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

    <body>
        <table border="1px">

                <tr>
                    <td>题目</td>
                    <td>内容</td>
                    <td>作者</td>
                </tr>
                <c:forEach items="${requestScope.scoreDocs}" var="score">
                    <tr>
                        <td>${score.title}</td>
                        <td>${score.content}</td>
                        <td>${score.author}</td>
                    </tr>
                </c:forEach>
        </table>
    <c:if test="${param.page==1}">
        <a href="/tss/tss/sen?page=${param.page-1}&size=5&context=${param.context}&aut=null">上一页</a>
    </c:if>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="/tss/tss/sen?page=${param.page+1}&size=5&context=${param.context}&aut=null">下一页</a>
    </body>
</html>
