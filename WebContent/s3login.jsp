<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<html:html>
<head>
<title>S3 on Struts - S3 Login</title>
<html:base />
<link rel="shortcut icon" href="images/100bucketsicon.jpg">
<link rel="stylesheet" href="css/screen.css" type="text/css" media="all" />
</head>
<body>

<div align="center" class="banner">
<h1>S3 on Struts</h1>
</div>

<div align="center">

<div class="centered">

<fieldset><legend>S3 Login</legend> 

<div align="center">
<html:form action="/s3login-submit">
	<table>
		<tr>
			<td><b>Access Key ID:</b> </td><td><html:text property="s3AccessKeyId" size="30"></html:text></td>
		</tr>
		<tr>
			<td><b>Secret Access Key ID:</b> </td><td><html:text property="s3SecretAccessKey" size="50"></html:text></td>
		</tr>
	</table>
	<html:submit value="Submit">Submit</html:submit>
	<html:submit value="Cancel">Cancel</html:submit>
</html:form>
</div>

<logic:present name="flash">
 	<div id="flash"><p><bean:write name="flash" /></p></div>
</logic:present>

</fieldset>

</div>

</div>

<jsp:include flush="true" page="footer.jsp"></jsp:include>

</body>
</html:html>