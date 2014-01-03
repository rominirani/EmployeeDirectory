<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<html>
	<head>
		<link type="text/css" href="css/redmond/jquery-ui-1.10.3.custom.min.css" rel="stylesheet" />
		<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>

		<link rel="stylesheet" href="css/redmond/jquery-ui-1.7.3.custom.css" />
		<script src="js/jquery-ui-1.7.3.custom.min.js" type="text/javascript"></script>

		<script type="text/javascript">
		
			$(document).ready(function() {
				
				//Click handler for Add Employee
				$('#addEmployeeBtn').click(function() {
                   	var employeeJSONData = $('#employeeJSONData').val();
                   	if (employeeJSONData.length == "") {
                   		alert("Please enter the Employee Records in JSON Format");
                   		return;
                   	}
                   	document.forms['addEmployeeForm'].submit();
				});
			});
			
		</script>
		
	</head>
	<body>
	    <h1>Employees Directory - Add Employee</h1>
	    <!--  Add Employee Form -->
	    <!--  It will invoke the /AddEmployee Servlet -->
		<div id="basic">
			<form action="/AddEmployee" method="get" name="addEmployeeForm" id="addEmployeeForm">
				<textarea rows="30" cols="50" name="employeeJSONData" id="employeeJSONData"></textarea>
				<br/>
				<input type="button" value="Add Employee" id="addEmployeeBtn"></input>
			</form>
			<!-- Sample Record -->
			<div id="sampleJSONData">
			<p>
			<b>Copy-Paste the Text for sample records below. Remember to change the user id (key) and the other fields, otherwise it will simply overwrite the same document in the index.</b>
<pre>			
{
"Employees" : [
{
"userId":"rirani",
"jobTitleName":"Developer",
"firstName":"Romin",
"lastName":"Irani",
"preferredFullName":"Romin Irani",
"employeeCode":"E1",
"region":"CA",
"phoneNumber":"408-1234567",
"emailAddress":"romin.k.irani@gmail.com"
},
{
"userId":"nirani",
"jobTitleName":"Developer",
"firstName":"Neil",
"lastName":"Irani",
"preferredFullName":"Neil Irani",
"employeeCode":"E2",
"region":"CA",
"phoneNumber":"408-1111111",
"emailAddress":"neilrirani@gmail.com"
},
{
"userId":"thanks",
"jobTitleName":"Program Directory",
"firstName":"Tom",
"lastName":"Hanks",
"preferredFullName":"Tom Hanks",
"employeeCode":"E3",
"region":"CA",
"phoneNumber":"408-2222222",
"emailAddress":"tomhanks@gmail.com"
}
]
}</pre>
			</p>
			</div>
		</div>
	</body>
</html>