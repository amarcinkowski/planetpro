package io.github.amarcinkowski;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelReader {

	final static Logger logger = LoggerFactory.getLogger(ExcelReader.class);

	public static DynamicTable read(String excelFilePath) throws IOException {

		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		Workbook workbook = new HSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();
		DynamicTable dynamicTable = new DynamicTable();

		boolean firstRow = true;
		int rowIndex = 0;
		int columnIndex = 0;

		while (iterator.hasNext()) {
			columnIndex = 0;
			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();

			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				Object value = getValue(cell);

				if (firstRow) {
					logger.trace("header: " + value.toString());
					dynamicTable.addHeader(stripUnits(value.toString()));
				} else {
					logger.trace("row/column: " + rowIndex + "/" + columnIndex + " " + value);
					dynamicTable.addElement(rowIndex, columnIndex++, value);
				}
			}
			if (!firstRow) {
				rowIndex++;
			}
			firstRow = false;
		}
		workbook.close();
		inputStream.close();

		return dynamicTable;
	}

	private static String stripUnits(String cellValue) {
		return cellValue.contains("[") ? cellValue.substring(0, cellValue.indexOf("[")) : cellValue;
	}

	private static Object getValue(Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();
		case Cell.CELL_TYPE_NUMERIC:
			return cell.getNumericCellValue();
		}
		return null;
	}

}