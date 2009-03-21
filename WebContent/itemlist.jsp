<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<html:html>
<head>
<title>S3 on Struts - Item Listing</title>
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

<fieldset><legend>Items</legend> 

<bean:size name="items" id="size" scope="request" />
<h2>Bucket <bean:write name="bucket" /> has <bean:write name="size" /> items</h2>

	<table align="center">
<logic:iterate id="item" indexId="id"
	collection="${items}">
		<tr>
			<td>	${id+1}. <a href="http://s3.amazonaws.com/${bucket}/${item}">${item}</a></td>
			<td>	[<html:link action="/setItemACL.do?bucket=${bucket}&item=${item}&acl=public-read">Public</html:link> | 
					 <html:link action="/setItemACL.do?bucket=${bucket}&item=${item}&acl=private">Private</html:link> | 
					 <html:link action="/deleteItem.do?bucket=${bucket}&item=${item}">DELETE</html:link>]</td>
		</tr>
</logic:iterate>
	</table>

<fieldset><legend>Upload Item</legend>
	<div class="centered">
		<html:form action="/uploadItem.do?bucket=${bucket}" enctype="multipart/form-data">
	         Select File: <html:file property="newItem" />
		    <html:submit value="Drop in Bucket" />
		</html:form>
	</div>
</fieldset>

<p>
<html:link forward="bucketlist">&lt; Back</html:link>
</p>

<logic:present name="flash">
 	<div id="flash"><p><bean:write name="flash" /></p></div>
</logic:present>

</fieldset>

</div>

</div>
 
<jsp:include flush="true" page="footer.jsp"></jsp:include>

</body>
</html:html>
