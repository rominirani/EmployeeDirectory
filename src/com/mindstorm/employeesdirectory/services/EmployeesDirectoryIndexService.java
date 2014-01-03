/**
 * This Java Servlet is the main interaction gateway for the UI. It is primarily invoked from the contacts.jsp page to 
 * search for Contacts. This Servlet interacts primarily with the App Engine Search API to handle the documents within the Index.
 * 
 * It also functions as a REST API
 * - /contactsdirectoryindexservice -> This will retrieve the documents in the index
 * - /contactsdirectoryindexservice?searchText=SOME_VALUE -> This will retrieve the documents in the index that match the search term
 * 
 * The API will provide JSON formatted responses
 */
package com.mindstorm.employeesdirectory.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.gson.Gson;
import com.mindstorm.employeesdirectory.entities.Employee;
import com.mindstorm.employeesdirectory.search.SearchIndexManager;

public class EmployeesDirectoryIndexService extends HttpServlet {
	
	/**
	 * The GET Method is where the Search Magic happens. It extracts out the searchText request parameter and invokes the 
	 * SearchIndexManager class to retrieve the documents that match the searchText
	 */

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		//Extract out the search Text entered by the user on the contacts.jsp page
		String searchText = req.getParameter("searchText");
		try {
			//Check if the search Text is not empty
			if (searchText != null && !searchText.isEmpty()) {
				List<Employee> Employees = new ArrayList<Employee>();
				
				//Retrieve the list of Documents that match the SearchText entered.
				Results<ScoredDocument> results = SearchIndexManager.INSTANCE.retrieveDocuments(searchText);
				
				//For each of the documents, extract out the attributes from the document and populate the Employee entity class
				//Add the Employee object to the Employees collection
				for (ScoredDocument scoredDocument : results) {
					String firstName = scoredDocument.getOnlyField("firstName").getText();
					String lastName = scoredDocument.getOnlyField("lastName").getText();
					String preferredFullName = scoredDocument.getOnlyField("preferredFullName").getText();
					String phoneNumber = scoredDocument.getOnlyField("phoneNumber").getText();
					String emailAddress = scoredDocument.getOnlyField("emailAddress").getText();
					String region = scoredDocument.getOnlyField("region").getText();
					String userId = scoredDocument.getOnlyField("userId").getText();
					String employeeCode = scoredDocument.getOnlyField("employeeCode").getText();
					String jobTitleName = scoredDocument.getOnlyField("jobTitleName").getText();
					Employee _Contact = new Employee();
					_Contact.setEmployeeCode(employeeCode);
					_Contact.setJobTitleName(jobTitleName);
					_Contact.setEmailAddress(emailAddress);
					_Contact.setFirstName(firstName);
					_Contact.setLastName(lastName);
					_Contact.setRegion(region);
					_Contact.setUserId(userId);
					_Contact.setPhoneNumber(phoneNumber);
					_Contact.setPreferredFullName(preferredFullName);
					Employees.add(_Contact);
				}
					res.getOutputStream().print("{\"results\":" +new Gson().toJson(Employees)+"}");
						
			} else {
				//This should ideally not be allowed but is just present over here for demo purpose. The UI does restrict it from
				//being called if the Search Text field is empty
				List<Employee> Employees = new ArrayList<Employee>();

				Results<ScoredDocument> results = SearchIndexManager.INSTANCE.retrieveDocuments("");
				//For each of the documents, extract out the attributes from the document and populate the Employee entity class
				//Add the Employee object to the Employees collection
				for (ScoredDocument scoredDocument : results) {
					String firstName = scoredDocument.getOnlyField("firstName").getText();
					String lastName = scoredDocument.getOnlyField("lastName").getText();
					String preferredFullName = scoredDocument.getOnlyField("preferredFullName").getText();
					String phoneNumber = scoredDocument.getOnlyField("phoneNumber").getText();
					String emailAddress = scoredDocument.getOnlyField("emailAddress").getText();
					String region = scoredDocument.getOnlyField("region").getText();
					String userId = scoredDocument.getOnlyField("userId").getText();
					String employeeCode = scoredDocument.getOnlyField("employeeCode").getText();
					String jobTitleName = scoredDocument.getOnlyField("jobTitleName").getText();
					Employee _Contact = new Employee();
					_Contact.setEmployeeCode(employeeCode);
					_Contact.setJobTitleName(jobTitleName);
					_Contact.setEmailAddress(emailAddress);
					_Contact.setFirstName(firstName);
					_Contact.setLastName(lastName);
					_Contact.setRegion(region);
					_Contact.setUserId(userId);
					_Contact.setPhoneNumber(phoneNumber);
					_Contact.setPreferredFullName(preferredFullName);
					Employees.add(_Contact);
				}
				//Marshall the Collection into a JSON representation using the GSON Java library
				res.getOutputStream().print("{\"results\":" +new Gson().toJson(Employees)+"}");
			}
		} catch (Exception e) {
			res.getOutputStream().println(getFailureMessage());
		}
	}

	/**
	 * The POST method is currently not supported
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String status = "Not supported";
		res.getOutputStream().print(status);
	}

	/**
	 * The PUT method currently routes to the POST method
	 */
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String status = "Not supported";
		res.getOutputStream().print(status);
	}
	
	/**
	 * The DELETE method can be used to remove a document from the Search Index
	 */
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		//Check if documentId is provided
		String documentId = req.getParameter("userId");
		
		if (documentId == null || documentId.equals("")) {
			throw new IllegalStateException("documentId has to be provided to remove the record from the Search Index.");
		}
		
		String status;
		try {
			SearchIndexManager.INSTANCE.deleteDocumentFromIndex(documentId);
			status = getSuccessMessage();
		} catch (Exception e) {
			status = getFailureMessage();
		}
		res.getOutputStream().print(status);
	}
	
	private String getSuccessMessage() {
		return ("[{status:\"success\"}]");
	}
	private String getFailureMessage() {
		return ("[{status:\"failed\"}]");
	}
}
