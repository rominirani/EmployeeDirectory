/**
 * This utility is used to load sample contacts in the Google App Engine Search Index. It will load the records that are present
 * in the file defined in the Constants.SAMPLE_CONTACTS_DATA_FILE property. The loadData() method is invoked via the 
 * '/importindex' endpoint.
 */

package com.mindstorm.employeesdirectory.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.mindstorm.employeesdirectory.global.Constants;
import com.mindstorm.employeesdirectory.search.SearchIndexManager;

public class ImportEmployeesIntoIndex {
	public static Logger myLogger = Logger.getLogger(ImportEmployeesIntoIndex.class.getName());
	
	/**
	 * This method is invoked via the '/importindex' endpoint. It reads the JSON data file and invokes the processEmployees method.
	 */

	public static void loadData() {
		try {
			BufferedReader rdr = new BufferedReader(new InputStreamReader(new FileInputStream(Constants.SAMPLE_EMPLOYEES_DATA_FILE)));
			StringBuffer SB = new StringBuffer();
			String nextLine;
			while ((nextLine = rdr.readLine()) != null) {
				SB.append(nextLine);
			}
			processEmployees(SB.toString());
			rdr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * It will parse the JSON data file and for each of the Employee Records, it will do the following:
	 * 1. Create a Document Object with each attribute of the Document object populated accordingly.
	 * 2. By defining these attributes, the Search can function on any of these fields via a simple Text Term
	 * 3. Each Document is then added to the index via the SearchIndexManager method
	 * @param employeesJSONData String value of the JSON Employees data
	 * @throws Exception
	 */
	public static void processEmployees(String employeesJSONData) throws Exception {
		JSONObject feedData = new JSONObject(employeesJSONData);
		JSONArray employeeFeeds = feedData.getJSONArray("Employees");
		for (int i = 0; i < employeeFeeds.length(); i++) {
			JSONObject employeeFeed = (JSONObject) employeeFeeds.get(i);
			String userId = employeeFeed.getString("userId");
			String jobTitleName = employeeFeed.getString("jobTitleName");
			String firstName = employeeFeed.getString("firstName");
			String lastName = employeeFeed.getString("lastName");
			String preferredFullName = employeeFeed.getString("preferredFullName");
			String employeeCode = employeeFeed.getString("employeeCode");
			String region = employeeFeed.getString("region");
			String phoneNumber = employeeFeed.getString("phoneNumber");
			String emailAddress = employeeFeed.getString("emailAddress");
			try {
				if (!userId.isEmpty()) {

					//Build a Document Object
					//Add all the attributes on which search can be done
					Document newDoc = Document.newBuilder().setId(userId)
							.addField(Field.newBuilder().setName("userId").setText(userId))
							.addField(Field.newBuilder().setName("jobTitleName").setText(jobTitleName))
							.addField(Field.newBuilder().setName("firstName").setText(firstName))
							.addField(Field.newBuilder().setName("lastName").setText(lastName))
							.addField(Field.newBuilder().setName("preferredFullName").setText(preferredFullName))
							.addField(Field.newBuilder().setName("employeeCode").setText(employeeCode))
							.addField(Field.newBuilder().setName("region").setText(region))
							.addField(Field.newBuilder().setName("phoneNumber").setText(phoneNumber))
							.addField(Field.newBuilder().setName("emailAddress").setText(emailAddress)).build();
					
					//Add the Document instance to the Search Index
					SearchIndexManager.INSTANCE.indexDocument(Constants.EMPLOYEES_INDEX_NAME, newDoc);
                } 
				else {
                    	throw new Exception("userId field is empty");
                }
			} catch (Exception ex) {
				myLogger.warning("Could not process Record for User : " + preferredFullName + ".Reason : " + ex.getMessage());
			}
		}
	}
}
