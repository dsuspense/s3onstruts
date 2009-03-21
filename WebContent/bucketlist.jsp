<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<html:html>
<head>
<title>S3 on Struts - Bucket Listing</title>
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

<fieldset><legend>Buckets</legend> 

<bean:size name="buckets" id="size" />
<h2>Your S3 account has <bean:write name="size" /> buckets</h2>

	<table align="center">
<logic:iterate id="bucket" indexId="id"
	collection="${buckets}">
		<tr>
			<td>	${id+1}. <html:link action="/listBucket.do?bucket=${bucket}">${bucket}</html:link></td>
			<td>	[<html:link action="/setBucketACL.do?bucket=${bucket}&acl=public-read">Public</html:link> | 
					 <html:link action="/setBucketACL.do?bucket=${bucket}&acl=private">Private</html:link> | 
					 <html:link action="/deleteBucket.do?bucket=${bucket}">DELETE</html:link>]</td>
		</tr>
</logic:iterate>
	</table>

<fieldset><legend>Create Bucket</legend>
	<div class="centered">
		<html:form action="/createBucket.do">
	         Bucket: <html:text property="bucketName"/>
		    <html:submit value="Create Bucket" />
		</html:form>
	</div>
</fieldset>

<logic:present name="flash">
 	<div id="flash"><p><bean:write name="flash" /></p></div>
</logic:present>

</fieldset>

</div>

</div>
 
<jsp:include flush="true" page="footer.jsp"></jsp:include>

</body>
</html:html>
