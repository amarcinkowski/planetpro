package io.github.amarcinkowski;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelReader {

	final static Logger logger = LoggerFactory.getLogger(ExcelReader.class);

	static Vector<String> rowHeader = new Vector<>();
	static Vector<CelestialObject> celestialObjects = new Vector<>();

	@SuppressWarnings("unchecked")
	public static Vector<CelestialObject> read(String excelFilePath) throws IOException {

		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

		Workbook workbook = new HSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();

		boolean firstLine = true;

		while (iterator.hasNext()) {
			CelestialObject co = new CelestialObject();

			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();

			int fieldIndex = 0;

			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();

				if (firstLine) {
					String cellValue = cell.getStringCellValue();
					cellValue = stripUnits(cellValue);
					rowHeader.add(cellValue);
				} else {
					try {
						String fieldName = rowHeader.elementAt(fieldIndex).toLowerCase();

						String subFieldName = (fieldName.contains(".") ? fieldName.substring(fieldName.indexOf(".") + 1)
								: "");
						fieldName = (fieldName.contains(".") ? fieldName.substring(0, fieldName.indexOf("."))
								: fieldName);

						Field field = co.getClass().getDeclaredField(fieldName);
						field.setAccessible(true);

						String fieldType = field.getType().getSimpleName();

						Object value = getValue(cell);

						switch (fieldType) {
						case "String":
							logger.trace("/T:" + field.getName() + "/V:" + value + "/");
							field.set(co, value);
							break;
						case "Double":
						case "Integer":
						case "BigDecimal":
							logger.trace("/T:" + field.getName() + "/V:" + value + "/");
							value = fieldType.equals("BigDecimal") ? new BigDecimal((double) value) : value;
							field.set(co, value);
							break;
						case "Enum":
							logger.trace("/T:" + field.getName() + "/V:" + value + "/");
							String enumName = field.getGenericType().getTypeName();
							enumName = enumName.substring(enumName.indexOf("<") + 1, enumName.length() - 1);
							Object enumvalue = Enum.valueOf((Class<Enum>) Class.forName(enumName), (String) value);
							field.set(co, enumvalue);
							break;
						case "HashMap":
							HashMap<String, String> map = getOrCreate(co, fieldName);
							logger.trace("/T:" + subFieldName + "/V:" + value);
							map.put(subFieldName, (String) value);
							field.set(co, map);
							break;
						default:
							logger.error("MISSING TYPE:" + fieldType + " unknown");
						}
					} catch (NoSuchFieldException | SecurityException | IllegalArgumentException
							| IllegalAccessException | ClassNotFoundException e) {
						e.printStackTrace();
					}

				}

				fieldIndex++;

			}

			if (firstLine) {
				firstLine = false;
				continue;
			} else {
				System.out.println(co);
			}

		}
		workbook.close();
		inputStream.close();
		logger.trace(rowHeader.toString());

		return null;
	}

	private static String stripUnits(String cellValue) {
		return cellValue.contains("[") ? cellValue.substring(0, cellValue.indexOf("[")) : cellValue;
	}

	@SuppressWarnings("unchecked")
	private static HashMap<String, String> getOrCreate(CelestialObject co, String fieldName) {
		try {
			HashMap<String, String> map = (HashMap<String, String>) co.getClass().getDeclaredField(fieldName).get(co);
			return map;
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		return new HashMap<String, String>();
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