package it.lispa.bdl.server.utils;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Class ExcelWriterUtils.
 */
public class ExcelWriterUtils {

	public static final String xlsxMimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	public static final String xlsMimeType = "application/vnd.ms-excel";
	
	/** log. */
	private static Logger log = Logger.getLogger(ExcelMonitoraggioLav.class);
	
	/** wb. */
	protected static Workbook wb;
	
	/** stili. */
	protected static Map<String, CellStyle> stili;
	
	/**
	 * Crea base style.
	 *
	 * @param fontName  font name
	 * @param fontSize  font size
	 * @param fontColor  font color
	 * @param fontBold  font bold
	 * @param fontItalic  font italic
	 * @param fontUnderlined  font underlined
	 * @return cell style
	 * @throws Exception exception
	 */
	public static CellStyle createBaseStyle(String fontName, Integer fontSize, short fontColor, boolean fontBold, boolean fontItalic, boolean fontUnderlined) throws Exception{
		CellStyle style;
		try{
			style = wb.createCellStyle();
			Font font =  createFont(fontName, fontSize, fontColor, fontBold, fontItalic, fontUnderlined);        
			style.setFont(font);
		} catch (Exception e){
			Exception myException = new Exception("[createBaseStyle] "+e.getMessage());
			myException.setStackTrace(e.getStackTrace());
			throw myException;
		}
		return style;
	}
	
	/**
	 * Crea base bordered style.
	 *
	 * @param fontName  font name
	 * @param fontSize  font size
	 * @param fontColor  font color
	 * @param fontBold  font bold
	 * @param fontItalic  font italic
	 * @param fontUnderlined  font underlined
	 * @return cell style
	 * @throws Exception exception
	 */
	public static CellStyle createBaseBorderedStyle(String fontName, Integer fontSize, short fontColor, boolean fontBold, boolean fontItalic, boolean fontUnderlined) throws Exception{
		CellStyle style;
		try{
			style = createBaseStyle(fontName, fontSize, fontColor, fontBold, fontItalic, fontUnderlined);
			style.setBorderBottom(CellStyle.BORDER_THIN);
			style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			style.setBorderLeft(CellStyle.BORDER_THIN);
			style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			style.setBorderRight(CellStyle.BORDER_THIN);
			style.setRightBorderColor(IndexedColors.BLACK.getIndex());
			style.setBorderTop(CellStyle.BORDER_THIN);
			style.setTopBorderColor(IndexedColors.BLACK.getIndex());

		} catch (Exception e){
			Exception myException = new Exception("[createBaseBorderedStyle] "+e.getMessage());
			myException.setStackTrace(e.getStackTrace());
			throw myException;
		}
		return style;
	}

	/**
	 * Inizializza styles.
	 *
	 * @throws Exception exception
	 */
	public static void initStyles() throws Exception{

		stili = new HashMap<String,CellStyle>();

		String euroFormat = "_-� * #,##0.00_-;-� * #,##0.00_-;_-� * \"-\"??_-;_-@_-";
		String integerFormat = "#,##0";
		String floatFormat = "#,##0.00";
		String percFormat = "0.00%";

		String fontName = "Arial";

		CellStyle titleStyle = createBaseStyle(fontName, 18, IndexedColors.BLACK.getIndex(), true, false, false);
		titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
		stili.put("sheetTitle", titleStyle);

		CellStyle tableBigTitle = createBaseBorderedStyle(fontName, 10, IndexedColors.WHITE.getIndex(), true, false, false);
		tableBigTitle.setAlignment(CellStyle.ALIGN_CENTER);
		tableBigTitle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		tableBigTitle.setWrapText(true);
		tableBigTitle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		tableBigTitle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		stili.put("tableBigTitle", tableBigTitle);

		CellStyle tableBigCell = createBaseBorderedStyle(fontName, 10, IndexedColors.BLACK.getIndex(), true, false, false);
		tableBigCell.setAlignment(CellStyle.ALIGN_CENTER);
		tableBigCell.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		tableBigCell.setWrapText(true);
		stili.put("tableBigCell", tableBigCell);

		CellStyle tableBigCellPerc = createBaseBorderedStyle(fontName, 10, IndexedColors.BLACK.getIndex(), true, false, false);
		tableBigCellPerc.setAlignment(CellStyle.ALIGN_CENTER);
		tableBigCellPerc.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		tableBigCellPerc.setWrapText(true);
		tableBigCellPerc.setDataFormat(wb.createDataFormat().getFormat(percFormat));
		stili.put("tableBigCellPerc", tableBigCellPerc);

		CellStyle tableBigCellInteger = createBaseBorderedStyle(fontName, 10, IndexedColors.BLACK.getIndex(), true, false, false);
		tableBigCellInteger.setAlignment(CellStyle.ALIGN_RIGHT);
		tableBigCellInteger.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		tableBigCellInteger.setWrapText(true);
		tableBigCellInteger.setDataFormat(wb.createDataFormat().getFormat(integerFormat));
		stili.put("tableBigCellInteger", tableBigCellInteger);

		CellStyle tableBigCellFloat = createBaseBorderedStyle(fontName, 10, IndexedColors.BLACK.getIndex(), true, false, false);
		tableBigCellFloat.setAlignment(CellStyle.ALIGN_RIGHT);
		tableBigCellFloat.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		tableBigCellFloat.setWrapText(true);
		tableBigCellFloat.setDataFormat(wb.createDataFormat().getFormat(floatFormat));
		stili.put("tableBigCellFloat", tableBigCellFloat);

		CellStyle tableBigCellEuro = createBaseBorderedStyle(fontName, 10, IndexedColors.BLACK.getIndex(), true, false, false);
		tableBigCellEuro.setAlignment(CellStyle.ALIGN_CENTER);
		tableBigCellEuro.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		tableBigCellEuro.setWrapText(true);
		tableBigCellEuro.setDataFormat(wb.createDataFormat().getFormat(euroFormat));
		stili.put("tableBigCellEuro", tableBigCellEuro);

		CellStyle tableLittleTitle = createBaseBorderedStyle(fontName, 9, IndexedColors.WHITE.getIndex(), false, false, false);
		tableLittleTitle.setAlignment(CellStyle.ALIGN_CENTER);
		tableLittleTitle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		tableLittleTitle.setWrapText(true);
		tableLittleTitle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		tableLittleTitle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		stili.put("tableLittleTitle", tableLittleTitle);

		CellStyle tableLittleCell = createBaseBorderedStyle(fontName, 9, IndexedColors.BLACK.getIndex(), false, false, false);
		tableLittleCell.setAlignment(CellStyle.ALIGN_CENTER);
		tableLittleCell.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		tableLittleCell.setWrapText(true);
		stili.put("tableLittleCell", tableLittleCell);

		CellStyle tableLittleCellLeft = createBaseBorderedStyle(fontName, 9, IndexedColors.BLACK.getIndex(), false, false, false);
		tableLittleCellLeft.setAlignment(CellStyle.ALIGN_LEFT);
		tableLittleCellLeft.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		tableLittleCellLeft.setWrapText(true);
		stili.put("tableLittleCellLeft", tableLittleCellLeft);

		CellStyle tableLittleCellLeftNoWrap = createBaseBorderedStyle(fontName, 9, IndexedColors.BLACK.getIndex(), false, false, false);
		tableLittleCellLeftNoWrap.setAlignment(CellStyle.ALIGN_LEFT);
		tableLittleCellLeftNoWrap.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		tableLittleCellLeftNoWrap.setWrapText(false);
		stili.put("tableLittleCellLeftNoWrap", tableLittleCellLeftNoWrap);

		CellStyle tableLittleCellPerc = createBaseBorderedStyle(fontName, 9, IndexedColors.BLACK.getIndex(), false, false, false);
		tableLittleCellPerc.setAlignment(CellStyle.ALIGN_CENTER);
		tableLittleCellPerc.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		tableLittleCellPerc.setWrapText(true);
		tableLittleCellPerc.setDataFormat(wb.createDataFormat().getFormat(percFormat));
		stili.put("tableLittleCellPerc", tableLittleCellPerc);

		CellStyle tableLittleCellInteger = createBaseBorderedStyle(fontName, 9, IndexedColors.BLACK.getIndex(), false, false, false);
		tableLittleCellInteger.setAlignment(CellStyle.ALIGN_RIGHT);
		tableLittleCellInteger.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		tableLittleCellInteger.setWrapText(true);
		tableLittleCellInteger.setDataFormat(wb.createDataFormat().getFormat(integerFormat));
		stili.put("tableLittleCellInteger", tableLittleCellInteger);

		CellStyle tableLittleCellFloat = createBaseBorderedStyle(fontName, 9, IndexedColors.BLACK.getIndex(), false, false, false);
		tableLittleCellFloat.setAlignment(CellStyle.ALIGN_RIGHT);
		tableLittleCellFloat.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		tableLittleCellFloat.setWrapText(true);
		tableLittleCellFloat.setDataFormat(wb.createDataFormat().getFormat(floatFormat));
		stili.put("tableLittleCellFloat", tableLittleCellFloat);

		CellStyle tableLittleCellEuro = createBaseBorderedStyle(fontName, 9, IndexedColors.BLACK.getIndex(), false, false, false);
		tableLittleCellEuro.setAlignment(CellStyle.ALIGN_CENTER);
		tableLittleCellEuro.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		tableLittleCellEuro.setWrapText(true);
		tableLittleCellEuro.setDataFormat(wb.createDataFormat().getFormat(euroFormat));
		stili.put("tableLittleCellEuro", tableLittleCellEuro);

		CellStyle tableLittleCellGrey = createBaseBorderedStyle(fontName, 9, IndexedColors.WHITE.getIndex(), true, false, false);
		tableLittleCellGrey.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
		tableLittleCellGrey.setFillPattern(CellStyle.SOLID_FOREGROUND);
		tableLittleCellGrey.setAlignment(CellStyle.ALIGN_CENTER);
		tableLittleCellGrey.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		tableLittleCellGrey.setWrapText(true);
		stili.put("tableLittleCellGrey", tableLittleCellGrey);

		CellStyle tableLittleCellGreyLeft = createBaseBorderedStyle(fontName, 9, IndexedColors.WHITE.getIndex(), true, false, false);
		tableLittleCellGreyLeft.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
		tableLittleCellGreyLeft.setFillPattern(CellStyle.SOLID_FOREGROUND);
		tableLittleCellGreyLeft.setAlignment(CellStyle.ALIGN_LEFT);
		tableLittleCellGreyLeft.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		tableLittleCellGreyLeft.setWrapText(true);
		stili.put("tableLittleCellGreyLeft", tableLittleCellGreyLeft);

		CellStyle tableLittleCellGreylt = createBaseBorderedStyle(fontName, 9, IndexedColors.BLACK.getIndex(), true, false, false);
		tableLittleCellGreylt.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		tableLittleCellGreylt.setFillPattern(CellStyle.SOLID_FOREGROUND);
		tableLittleCellGreylt.setAlignment(CellStyle.ALIGN_CENTER);
		tableLittleCellGreylt.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		tableLittleCellGreylt.setWrapText(true);
		stili.put("tableLittleCellGreylt", tableLittleCellGreylt);

		CellStyle tableLittleCellGreyltRight = createBaseBorderedStyle(fontName, 9, IndexedColors.BLACK.getIndex(), true, false, false);
		tableLittleCellGreyltRight.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		tableLittleCellGreyltRight.setFillPattern(CellStyle.SOLID_FOREGROUND);
		tableLittleCellGreyltRight.setAlignment(CellStyle.ALIGN_RIGHT);
		tableLittleCellGreyltRight.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		tableLittleCellGreyltRight.setWrapText(true);
		stili.put("tableLittleCellGreyltRight", tableLittleCellGreyltRight);

		CellStyle tableLittleCellGreyltPerc = createBaseBorderedStyle(fontName, 9, IndexedColors.BLACK.getIndex(), true, false, false);
		tableLittleCellGreyltPerc.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		tableLittleCellGreyltPerc.setFillPattern(CellStyle.SOLID_FOREGROUND);
		tableLittleCellGreyltPerc.setAlignment(CellStyle.ALIGN_CENTER);
		tableLittleCellGreyltPerc.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		tableLittleCellGreyltPerc.setWrapText(true);
		tableLittleCellGreyltPerc.setDataFormat(wb.createDataFormat().getFormat(percFormat));
		stili.put("tableLittleCellGreyltPerc", tableLittleCellGreyltPerc);

		CellStyle tableLittleCellGreyltInteger = createBaseBorderedStyle(fontName, 9, IndexedColors.BLACK.getIndex(), true, false, false);
		tableLittleCellGreyltInteger.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		tableLittleCellGreyltInteger.setFillPattern(CellStyle.SOLID_FOREGROUND);
		tableLittleCellGreyltInteger.setAlignment(CellStyle.ALIGN_RIGHT);
		tableLittleCellGreyltInteger.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		tableLittleCellGreyltInteger.setWrapText(true);
		tableLittleCellGreyltInteger.setDataFormat(wb.createDataFormat().getFormat(integerFormat));
		stili.put("tableLittleCellGreyltInteger", tableLittleCellGreyltInteger);

		CellStyle tableLittleCellGreyltFloat = createBaseBorderedStyle(fontName, 9, IndexedColors.BLACK.getIndex(), true, false, false);
		tableLittleCellGreyltFloat.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		tableLittleCellGreyltFloat.setFillPattern(CellStyle.SOLID_FOREGROUND);
		tableLittleCellGreyltFloat.setAlignment(CellStyle.ALIGN_RIGHT);
		tableLittleCellGreyltFloat.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		tableLittleCellGreyltFloat.setWrapText(true);
		tableLittleCellGreyltFloat.setDataFormat(wb.createDataFormat().getFormat(floatFormat));
		stili.put("tableLittleCellGreyltFloat", tableLittleCellGreyltFloat);

		CellStyle tableLittleCellGreyltEuro = createBaseBorderedStyle(fontName, 9, IndexedColors.BLACK.getIndex(), true, false, false);
		tableLittleCellGreyltEuro.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		tableLittleCellGreyltEuro.setFillPattern(CellStyle.SOLID_FOREGROUND);
		tableLittleCellGreyltEuro.setAlignment(CellStyle.ALIGN_CENTER);
		tableLittleCellGreyltEuro.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		tableLittleCellGreyltEuro.setWrapText(true);
		tableLittleCellGreyltEuro.setDataFormat(wb.createDataFormat().getFormat(euroFormat));
		stili.put("tableLittleCellGreyltEuro", tableLittleCellGreyltEuro);

	}
	
	/**
	 * Legge internal font.
	 *
	 * @param fontName  font name
	 * @param fontHeightInPoints  font height in points
	 * @param fontColor  font color
	 * @param fontBold  font bold
	 * @param fontItalic  font italic
	 * @param fontUnderlined  font underlined
	 * @return internal font
	 */
	public static Font getInternalFont(String fontName, Integer fontHeightInPoints, short fontColor, boolean fontBold, boolean fontItalic, boolean fontUnderlined){
		short fontBoldSh = Font.BOLDWEIGHT_NORMAL;
		if(fontBold){
			fontBoldSh = Font.BOLDWEIGHT_BOLD;
		}
		byte fontUnderlinedSh = 0;
		if(fontUnderlined){
			fontUnderlinedSh = 1;
		}
		Boolean italic = fontItalic;

		return getInternalFont(fontName,fontHeightInPoints.shortValue(), fontColor, fontBoldSh, italic, fontUnderlinedSh);
	}
	
	/**
	 * Legge internal font.
	 *
	 * @param fontName  font name
	 * @param fontHeightInPoints  font height in points
	 * @param fontColor  font color
	 * @param boldweight  boldweight
	 * @param italic  italic
	 * @param underline  underline
	 * @return internal font
	 */
	private static Font getInternalFont(String fontName, short fontHeightInPoints, short fontColor, short boldweight, boolean italic, byte underline) {
		Boolean strikeout = false;
		short typeOffset = Font.SS_NONE;

		Integer dimensioniOrig = Integer.valueOf(fontHeightInPoints) * 20;
		Font fToReturn =  wb.findFont(boldweight, fontColor, dimensioniOrig.shortValue(), fontName, italic, strikeout,typeOffset, underline);
		return fToReturn;
	}

	/**
	 * Crea font.
	 *
	 * @param fontName  font name
	 * @param fontHeightInPoints  font height in points
	 * @param color  color
	 * @param boldweight  boldweight
	 * @param italic  italic
	 * @param underline  underline
	 * @return font
	 */
	protected static Font createFont(String fontName, short fontHeightInPoints, short color, short boldweight, boolean italic, byte underline) {
		Font font = getInternalFont(fontName, fontHeightInPoints, color, boldweight, italic, underline);
		if(font==null) {    
			// restituisco il font personalizzato... altrimenti quello trovato nel workbook!
			Boolean strikeout = false;
			short typeOffset = Font.SS_NONE;

			font =  wb.createFont();
			font.setFontHeightInPoints(fontHeightInPoints);
			font.setFontName(fontName);
			font.setColor(color);
			font.setBoldweight(boldweight);
			font.setUnderline(underline);
			font.setStrikeout(strikeout);
			font.setItalic(italic);
			font.setTypeOffset(typeOffset);
		}	
		
		return font;
	}
	
	/**
	 * Crea font.
	 *
	 * @param fontName  font name
	 * @param fontSizeInPoints  font size in points
	 * @param fontColor  font color
	 * @param fontBold  font bold
	 * @param fontItalic  font italic
	 * @param fontUnderlined  font underlined
	 * @return font
	 * @throws Exception exception
	 */
	public static Font createFont(String fontName, Integer fontSizeInPoints, short fontColor, boolean fontBold, boolean fontItalic, boolean fontUnderlined) throws Exception{

		short fontBoldSh = Font.BOLDWEIGHT_NORMAL;
		if(fontBold){
			fontBoldSh = Font.BOLDWEIGHT_BOLD;
		}
		byte fontUnderlinedSh = 0;
		if(fontUnderlined){
			fontUnderlinedSh = 1;
		}

		short fontHeightInPoints = (short) fontSizeInPoints.shortValue();
		byte underline = fontUnderlinedSh;
		Boolean italic = fontItalic;

		return createFont(fontName, fontHeightInPoints, fontColor, fontBoldSh, italic, underline);

	}

	/**
	 * Crea table header.
	 *
	 * @param sh  sh
	 * @param headers  headers
	 * @param myStyles  my styles
	 * @param row  row
	 * @param column  column
	 */
	public void createTableHeader(Sheet sh, String[] headers, CellStyle[] myStyles, Integer row, Integer column){
		for(int i=0; i<headers.length; i++){
			Integer cellIndex = column + i;
			String myHeader = headers[i];
			Row myRow = sh.getRow(row);
			Cell myCell = myRow.createCell(cellIndex);
			myCell.setCellValue(myHeader);
			myCell.setCellStyle(myStyles[i]);
		}
	}

	/**
	 * Crea table row.
	 *
	 * @param sh  sh
	 * @param values  values
	 * @param myStyles  my styles
	 * @param row  row
	 * @param column  column
	 * @throws Exception exception
	 */
	public void createTableRow(Sheet sh, Object[] values, CellStyle[] myStyles, Integer row, Integer column) throws Exception{
		for(int i=0; i<values.length;i++){
			Integer cellIndex = column + i;
			Object myValue = values[i];
			Row myRow = sh.getRow(row);
			Cell myCell = myRow.createCell(cellIndex);
			String myClass;
			if(myValue!=null){
				myClass = myValue.getClass().getSimpleName();
				if(myClass.equals("Integer")){
					myCell.setCellValue((Integer)myValue);
				}else if(myClass.equals("Double")){
					myCell.setCellValue((Double)myValue);
				}else if(myClass.equals("Float")){
					myCell.setCellValue((Float)myValue);
				}else if(myClass.equals("BigDecimal")){
					myCell.setCellValue(((BigDecimal)myValue).floatValue());
				}else if(myClass.equals("String")){
					myCell.setCellValue((String)myValue);
				}else{
					throw new Exception("[createTableRow] la classe "+myClass+" non risulta mappata all'interno della funzione");
				}
			}else{
				myCell.setCellValue("");
			}
			if(myStyles.length-1 < i){
				throw new Exception("[createTableRow] lo stile "+i+" non risulta definito");
			}
			myCell.setCellStyle(myStyles[i]);
		}
	}

	/**
	 * Crea table column.
	 *
	 * @param sh  sh
	 * @param values  values
	 * @param myStyles  my styles
	 * @param row  row
	 * @param column  column
	 * @throws Exception exception
	 */
	public void createTableCol(Sheet sh, Object[] values, CellStyle[] myStyles, Integer row, Integer column) throws Exception{
		for(int i=0; i<values.length;i++){
			Object myValue = values[i];
			
			Integer rowIndex = row + i;
			Row myRow = sh.getRow(rowIndex);
			if(myRow == null){
				myRow = sh.createRow(rowIndex);
			}
			Cell myCell = myRow.createCell(column);
			String myClass;
			if(myValue!=null){
				myClass = myValue.getClass().getSimpleName();
				if(myClass.equals("Integer")){
					myCell.setCellValue((Integer)myValue);
				}else if(myClass.equals("Double")){
					myCell.setCellValue((Double)myValue);
				}else if(myClass.equals("Float")){
					myCell.setCellValue((Float)myValue);
				}else if(myClass.equals("BigDecimal")){
					myCell.setCellValue(((BigDecimal)myValue).floatValue());
				}else if(myClass.equals("String")){
					myCell.setCellValue((String)myValue);
				}else{
					throw new Exception("[createTableCol] la classe "+myClass+" non risulta mappata all'interno della funzione");
				}
			}else{
				myCell.setCellValue("");
			}
			if(myStyles.length-1 < i){
				throw new Exception("[createTableCol] lo stile "+i+" non risulta definito");
			}
			if(myStyles[i]!=null) {
				myCell.setCellStyle(myStyles[i]);
			}
		}
	}
	/**
	 * Save work book.
	 *
	 * @param filename  filename
	 * @throws Exception exception
	 */
	public void saveWorkBook(String filename) throws Exception{

		FileOutputStream out = null;
		try {
			String myFilename = null;
			if(wb instanceof XSSFWorkbook) {
				myFilename = filename + ".xlsx";
			}else{
				myFilename = filename + ".xls";
			}
			out = new FileOutputStream(myFilename);
			wb.write(out);
		} catch (Exception e) {
			Exception myException = new Exception("[saveWorkBook] "+e.getMessage());
			myException.setStackTrace(e.getStackTrace());
			throw myException;
		} finally{
			if(out!=null){
				out.close();
			}
		}
	}
	
	public Workbook getWb() {
		return wb;
	}
	public void setWb(Workbook wb) {
		ExcelWriterUtils.wb = wb;
	}
	
	protected Sheet initSheet(String sheetName, String sheetTitle) {
		Sheet sh = ExcelWriterUtils.wb.createSheet(sheetName);		

		log.debug("[initSheet] Imposto le proprieta' di stampa");
		sh.setDisplayGridlines(false);
		sh.setPrintGridlines(false);
		sh.setFitToPage(true);
		sh.setHorizontallyCenter(true);
		PrintSetup printSetup = sh.getPrintSetup();
		printSetup.setLandscape(true);
		sh.setAutobreaks(true);
		printSetup.setFitHeight((short)1);
		printSetup.setFitWidth((short)1);
		
		return sh;
	}
}
