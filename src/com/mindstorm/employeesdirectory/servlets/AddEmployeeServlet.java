/**
 * A Java Servlet, mapped to '/AddEmployee' endpoint and used by the addemployee.jsp page to add sample employees to the 
 * Index. 
 */
package com.mindstorm.employeesdirectory.servlets;

import java.io.IOException;
import javax.servlet.http.*;

import com.mindstorm.employeesdirectory.utils.ImportEmployeesIntoIndex;

@SuppressWarnings("serial")
public class AddEmployeeServlet extends HttpServlet {
	/**
	 * The GET method extracts out the JSON formatted Contact data provided. It will call the utility method in the 
	 * ImportContactsIntoIndex class to process the Records and add them to the Index.
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");
		try {
			String employeeJSONData = req.getParameter("employeeJSONData");
			if (employeeJSONData != null) {
				ImportEmployeesIntoIndex.processEmployees(employeeJSONData);
				resp.getWriter().println("Employees have been added to the Index");
				resp.getWriter().println("<a href='employees.jsp'>Back to Search Page</a> | <a href='addemployee.jsp'>Add More Employees</a>");
			}
		}
		catch (Exception ex) {
			resp.getWriter().println("Error importing employees. Reason : " + ex.getMessage());
			resp.getWriter().println("<a href='employees.jsp'>Back to Search Page</a> | <a href='addemployee.jsp'>Add More Employees</a>");
		}
	}
}
