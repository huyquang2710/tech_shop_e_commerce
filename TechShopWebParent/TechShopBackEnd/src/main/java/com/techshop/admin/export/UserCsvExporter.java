package com.techshop.admin.export;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.techshop.common.entity.User;

public class UserCsvExporter extends AbstractExporter {
	public void export(List<User> userList, HttpServletResponse response) throws IOException {
		String extension = ".csv";
		String contentType = "text/csv";
		super.setResponseHeader(response, contentType, extension);
		
		ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		
		String[] csvHeader = {"User ID", "Email", "First Name", "Last Name", "Roles", "Enabled"};
		String[] fieldMapping = {"id", "email", "firstName", "lastName", "roles", "enabled"};
		
		csvBeanWriter.writeHeader(csvHeader);
		
		for(User user: userList) {
			csvBeanWriter.write(user, fieldMapping);
		}
		csvBeanWriter.close();
	}
}
