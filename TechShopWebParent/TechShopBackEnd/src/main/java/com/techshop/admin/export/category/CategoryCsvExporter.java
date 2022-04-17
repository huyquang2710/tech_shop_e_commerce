package com.techshop.admin.export.category;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.techshop.admin.export.AbstractExporter;
import com.techshop.common.entity.Category;

public class CategoryCsvExporter extends AbstractExporter{
	public void export(List<Category> categories, HttpServletResponse response) throws IOException {
		
		String extension = ".csv";
		String contentType = "text/csv";
		String prefix = "category_";
		
		super.setResponseHeader(response, contentType, extension, prefix);
		
		ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		
		String[] header = {"ID", "Name"};
		String[] fieldMapping = {"id", "name"};
		
		csvBeanWriter.writeHeader(header);
		
		for(Category category : categories) {
			category.setName(category.getName().replace("--", "  "));
			csvBeanWriter.write(category, fieldMapping);
		}
		csvBeanWriter.close();
	}
}
