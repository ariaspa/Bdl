package it.lispa.bdl.server.utils;

import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Class ExcelParser.
 */
public class ExcelParser {
	

	/** La Costante FIRST_ROW_IS_HEADER. */
	public final static Boolean FIRST_ROW_IS_HEADER = true;
	
	/** La Costante FIRST_ROW_IS_NOT_HEADER. */
	public final static Boolean FIRST_ROW_IS_NOT_HEADER = false;
	/**
	 * Istanzia un nuovo excel parser.
	 */
	public ExcelParser(){
		// do nothing...
	}
		
	/**
	 * Parsa xlsx file.
	 *
	 * @param fis  fis
	 * @param maxColumnNumber  max column number
	 * @param isFirstRowAnHeader  is first row an header
	 * @return list
	 * @throws AsyncServiceException async service exception
	 */
	public List<Map<Integer,String>> parseXlsxFileWithMandatoryColumns(InputStream fis, Integer maxColumnNumber, Boolean isFirstRowAnHeader) throws AsyncServiceException {
		return parseXlsxFile(fis, maxColumnNumber,isFirstRowAnHeader, true);
	}
	
	/**
	 * Parsa xlsx file with optional columns.
	 *
	 * @param fis  fis
	 * @param maxColumnNumber  max column number
	 * @param isFirstRowAnHeader  is first row an header
	 * @return list
	 * @throws AsyncServiceException async service exception
	 */
	public List<Map<Integer,String>> parseXlsxFileWithOptionalColumns(InputStream fis, Integer maxColumnNumber, Boolean isFirstRowAnHeader) throws AsyncServiceException {
		return parseXlsxFile(fis, maxColumnNumber, isFirstRowAnHeader , false);
	}
	
	/**
	 * Parsa xlsx file.
	 *
	 * @param fis  fis
	 * @param maxColumnNumber  max column number
	 * @param isFirstRowAnHeader  is first row an header
	 * @param controlRowColumns  control row columns
	 * @return list
	 * @throws AsyncServiceException async service exception
	 */
	private List<Map<Integer,String>> parseXlsxFile(InputStream fis, Integer maxColumnNumber, Boolean isFirstRowAnHeader, Boolean controlRowColumns) throws AsyncServiceException {
		
		if (fis == null ){
			throw new AsyncServiceException("Errore durante la parsificazione del file, null");
		}
		
		XSSFWorkbook workbook = null;
		List<Map<Integer,String>> rows = new ArrayList<Map<Integer,String>>();
		
		try {
			workbook = new XSSFWorkbook(fis);
	        XSSFSheet sheet = workbook.getSheetAt(0);
			if (sheet == null){
				throw new AsyncServiceException("Lo sheet risulta nullo");
			}
			Integer firstRowToParse = 0;
			if(isFirstRowAnHeader){
				XSSFRow row = sheet.getRow(0);
				int lastCellNum = row.getLastCellNum();
				if (lastCellNum < maxColumnNumber) {
					throw new AsyncServiceException("Il foglio non ha il numero di colonne richieste ");
				}
				firstRowToParse = 1;
			}

			int lastRowNum = sheet.getLastRowNum(); 
			if (lastRowNum < firstRowToParse){
				throw new AsyncServiceException("Errore Il file non contiene nessuna riga di record valida");
			}
			for (int rowIndex = firstRowToParse; rowIndex <= lastRowNum; rowIndex++) {
				XSSFRow row = sheet.getRow(rowIndex);
				if(controlRowColumns){
    				int lastCellNum = row.getLastCellNum();
    				if (lastCellNum < maxColumnNumber) {
    					throw new AsyncServiceException("La riga " + rowIndex + " non ha il numero di colonne richieste ");
    				}
				}
				Map<Integer,String> cellValues = null;
				if(row!=null) {
					cellValues  = parseRow(row, maxColumnNumber);
				}
				if(cellValues!=null){
					rows.add(cellValues);
				}
			}
		} catch (FileNotFoundException e) {
			AsyncServiceException myAsyncServiceException = new AsyncServiceException("FileNotFoundException : "+e.getMessage());
			myAsyncServiceException.setStackTrace(e.getStackTrace());
			throw myAsyncServiceException;
		} catch (IOException e) {
			AsyncServiceException myAsyncServiceException = new AsyncServiceException("IOException : "+e.getMessage());
			myAsyncServiceException.setStackTrace(e.getStackTrace());
			throw myAsyncServiceException;
		}
		return rows;
	}
	
	/**
	 * Parsa row.
	 *
	 * @param row  row
	 * @param maxColumnNumber  max column number
	 * @return map
	 */
	private Map<Integer,String> parseRow(XSSFRow row, Integer maxColumnNumber){
		Map<Integer,String> cellValues  = new HashMap<Integer, String>();
		
		boolean hasAColumn = false;
		for (int cellIndex = 0; cellIndex < maxColumnNumber; cellIndex++) {
			XSSFCell cell = row.getCell(cellIndex);
			String value = this.parseCellContent(cell); 
			if(!value.equals("")){
				hasAColumn = true;
			}
			cellValues.put(cellIndex, value);
		}
		if(!hasAColumn){
			cellValues = null;
		}
		return cellValues;
	}
	
	/**
	 * Parsa cell content.
	 *
	 * @param cell  cell
	 * @return string
	 */
	private String parseCellContent(XSSFCell cell){
		String value = "";
		if (cell != null ) {
				
			switch (cell.getCellType()) {
				case XSSFCell.CELL_TYPE_FORMULA:
					value = cell.getStringCellValue();
					break;
				case XSSFCell.CELL_TYPE_NUMERIC:
					Double tmpValue = new Double(cell.getNumericCellValue());
					value = ""+tmpValue.intValue();
					break;
				case XSSFCell.CELL_TYPE_STRING:
					value = cell.getStringCellValue();
					break;
				case XSSFCell.CELL_TYPE_BLANK:
					value = "";
					break;
				case XSSFCell.CELL_TYPE_ERROR:
					value = "error";
					break;
				default:
					break;
			}
		}
		return value;
	}

}
