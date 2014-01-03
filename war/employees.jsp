<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<%

String searchText = null;
//Read the searchText Request Parameter
if (request.getParameter("searchText") != null) {
	searchText = (String) request.getParameter("searchText");
	System.out.println("Search Text : " + searchText);
}
%>
<html>
	<head>
		<link type="text/css" href="css/redmond/jquery-ui-1.10.3.custom.min.css" rel="stylesheet" />
		<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>

		<link rel="stylesheet" href="css/redmond/jquery-ui-1.7.3.custom.css" />
		<script src="js/jquery-ui-1.7.3.custom.min.js" type="text/javascript"></script>
		<link type="text/css" href="css/jquery.dataTables.css" rel="stylesheet" />
		<script src="js/jquery.dataTables.min.js" type="text/javascript"></script>

		<script type="text/javascript">
		
			$(document).ready(function() {
				
				//Click Handler for Search Button
				$('#searchBtn').click(function() {
                   	var searchText = $('#searchText').val();
                   	if (searchText.length == "") {
                   		alert("Please enter a search term");
                   		return;
                   	}
                   	document.forms['searchForm'].submit();
				});

				//This is the API call for retrieving the Search Records
				//It invokes the Contacts Directory Index Service
				var table_url = "/employeesdirectoryindexservice?sid="+Math.random();
				var oTable = $('#result-table').dataTable( {
						"bJQueryUI": true,
						"bProcessing": true,
	       				"sAjaxSource": table_url+"&searchText=<%=searchText%>",
	       				"sAjaxDataProp": "results",
	       				"iDisplayLength": 50,
	       				"oSearch": {"sSearch":""},
	       				"oLanguage": {"sSearch":"Filter:"},
	       				"aoColumns": [
	       					{ "mData": "userId" },
	       					{ "mData": "firstName" },
	       					{ "mData": "lastName" },
	       					{ "mData": "preferredFullName"},
	       					{ "mData": "employeeCode"},
	       					{ "mData": "jobTitleName"},
	       					{ "mData": "region"},
	       					{ "mData": "emailAddress"},
	       					{ "mData": "phoneNumber"}
	       				],
	       				"aaSorting": [[ 1, "asc" ]]
				});
			});
			
		</script>
		
	</head>
	<body>
	    <h1>Employee Directory</h1>
	    <!--  Search Form. -->
		<div id="basic">
			<form action="employees.jsp" method="get" name="searchForm" id="searchForm">
				<input name="searchText" id="searchText" placeholder="Enter Search Term"></input>
				<input type="button" value="Search" id="searchBtn"></input>&nbsp;|&nbsp;
				<a href="addemployee.jsp">Add More Employees</a>
			</form>
		</div>
	    
	    <!-- Search Results Table -->
		<div id="basic">
			<table id="result-table">
				<thead>
					<tr>
						<th>UserId</th>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Full Name</th>
						<th>Employee Code</th>
						<th>Job Title</th>
						<th>Region</th>
						<th>Email</th>
						<th>Phone</th>
					</tr>
				</thead>
			</table>
		</div>
	</body>
</html>