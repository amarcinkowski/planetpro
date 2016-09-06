package io.github.amarcinkowski;

import java.util.TreeMap;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DynamicTable {

	final static Logger logger = LoggerFactory.getLogger(ExcelReader.class);

	private Vector<String> headers = new Vector<>();
	private TreeMap<String, Object> table = new TreeMap<String, Object>();

	public void addHeader(String header) {
		headers.addElement(header);
	}

	public void addElement(int x, int y, Object element) {
		String index = String.format("%d.%d", x, y);
		table.put(index, element);
		try {
			logger.trace(String.format("Adding %s: %s", index, element));
		} catch (Exception e) {
			logger.error("Err at: " + index);
		}
	}

	@Override
	public String toString() {
		return String.format("%s\n%s", headers, table.toString());
	}

	public TreeMap<String, Object> getTable() {
		return table;
	}

	public Vector<String> getHeaders() {
		return headers;
	}

}