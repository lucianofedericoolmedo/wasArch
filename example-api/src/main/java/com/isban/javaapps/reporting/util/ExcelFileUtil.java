package com.isban.javaapps.reporting.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileUtil {

	private static final String NO_SUCH_METHOD_EXCEPTION_BASE_MESSAGE = "El nombre del campo %s no existe o no posee un método getter asociado.";

	public static File generateGeneric(Collection<String> header, Collection<Object[]> elements, Collection<String> fields) throws Exception {
		File tempFile = File.createTempFile(new Date().toString(), ".xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Hoja 1");
        ExcelFileUtil.addRow(sheet, 0, header);
        Integer rowNum = 1;
        for (Object[] element : elements) {
            ExcelFileUtil.addRow(sheet, rowNum++, element, fields);
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(tempFile);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	public static File generate(Collection<String> header, Collection<Object> elements, Collection<String> fields) throws Exception {
		File tempFile = File.createTempFile(new Date().toString(), ".xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Hoja 1");
        ExcelFileUtil.addRow(sheet, 0, header);
        Integer rowNum = 1;
        for (Object element : elements) {
            ExcelFileUtil.addRow(sheet, rowNum++, element, fields);
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(tempFile);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	public static void addRow(XSSFSheet sheet, Integer rowNumber, Object[] element, Collection<String> fieldsNames) throws Exception {
		List<String> fieldsValues = new LinkedList<String>();
		for (String fieldName : fieldsNames) {
			String result = null;
				Object[] objectArray = (Object[]) element;
				result = String.valueOf(objectArray[Integer.parseInt(fieldName)]);
			try {
				fieldsValues.add(result);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		ExcelFileUtil.addRow(sheet, rowNumber, fieldsValues);
	}

	public static void addRow(XSSFSheet sheet, Integer rowNumber, Object element, Collection<String> fieldsNames) throws Exception {
		List<String> fieldsValues = new LinkedList<String>();
		for (String fieldName : fieldsNames) {
			String fieldNameAsGetter = null;
			String result = null;
			fieldNameAsGetter = String.format("get%s", StringUtils.capitalize(fieldName));
			Method method = element.getClass().getMethod(fieldNameAsGetter);
			result = (String) method.invoke(element);
			try {
				fieldsValues.add(result);
//			} catch (NoSuchMethodException e) {
//				throw new ExcelFileUtilException(String.format(NO_SUCH_METHOD_EXCEPTION_BASE_MESSAGE, fieldName), e);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		ExcelFileUtil.addRow(sheet, rowNumber, fieldsValues);
	}

	public static void addRow(XSSFSheet sheet, Integer rowNumber, Collection<String> rowValues) {
		Row row = sheet.createRow(rowNumber);
		ExcelFileUtil.addCell(row, rowValues);
	}

	public static void addCell(Row row, Collection<String> rowValues) {
		Integer colNum = 0;
		for (String value : rowValues) {
			Cell cell = row.createCell(colNum++);
			cell.setCellValue(value);
		}
	}
	
}
