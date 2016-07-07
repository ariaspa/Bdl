package it.lispa.bdl.server.utils;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFName;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Class ExcelCatalogazioneTpl.
 */
public class ExcelCatalogazioneTpl extends ExcelWriterUtils {

	/** log. */
	private static Logger log = Logger.getLogger(ExcelCatalogazioneTpl.class);

	/** start row. */
	public static final Integer startRow = Integer.valueOf(1);
	
	/** start col descrizionecontenutistica. */
	public static final Integer startColDescrizionecontenutistica = Integer.valueOf(1);
	
	/** start col descrizionefisica. */
	public static final Integer startColDescrizionefisica = Integer.valueOf(2);
	
	/** start col livellobibliografico. */
	public static final Integer startColLivellobibliografico = Integer.valueOf(3);
	
	/** start col soggetto. */
	public static final Integer startColSoggetto = Integer.valueOf(4);
	
	/** start col qualificatoreautore. */
	public static final Integer startColQualificatoreautore = Integer.valueOf(5);
	
	/** start col autore. */
	public static final Integer startColAutore = Integer.valueOf(6);
	
	/** start col qualificatoreautore due. */
	public static final Integer startColQualificatoreautoreDue = Integer.valueOf(7);
	
	/** start col autore2. */
	public static final Integer startColAutore2 = Integer.valueOf(8);
	
	/** start col editore. */
	public static final Integer startColEditore = Integer.valueOf(9);
	
	/** start col lingua. */
	public static final Integer startColLingua = Integer.valueOf(10);
	
	/** start col tipoidentificativo. */
	public static final Integer startColTipoidentificativo = Integer.valueOf(11);
	
	/** start col identificativo. */
	public static final Integer startColIdentificativo = Integer.valueOf(12);
	
	/** start col luogopubblicazione. */
	public static final Integer startColLuogopubblicazione = Integer.valueOf(13);
	
	/** start col qualificatoredata. */
	public static final Integer startColQualificatoredata = Integer.valueOf(14);
	
	/** start col data. */
	public static final Integer startColData = Integer.valueOf(15);
	
	/** start col note. */
	public static final Integer startColNote = Integer.valueOf(16);
	
	/** start col scala. */
	public static final Integer startColScala = Integer.valueOf(17);
	
	/** start col proiezione. */
	public static final Integer startColProiezione = Integer.valueOf(18);
	
	/** start col coordinate. */
	public static final Integer startColCoordinate = Integer.valueOf(19);
	
	/** start col contestoarchivistico. */
	public static final Integer startColContestoarchivistico = Integer.valueOf(20);
	
	/** start col soggettiproduttori. */
	public static final Integer startColSoggettiproduttori = Integer.valueOf(21);
	
	/** start col linkcatalogo. */
	public static final Integer startColLinkcatalogo = Integer.valueOf(22);
	
	/** start col tipografica. */
	public static final Integer startColTipografica = Integer.valueOf(23);
	
	/** start col supportoprimario. */
	public static final Integer startColSupportoprimario = Integer.valueOf(24);
	
	/** start col tecnicagrafica. */
	public static final Integer startColTecnicagrafica = Integer.valueOf(25);
	
	/** start col tipoarchivio. */
	public static final Integer startColTipoarchivio = Integer.valueOf(26);

	/** start col titoloFe. */
	public static final Integer startColTitoloFe = Integer.valueOf(27);
	
	/** start col titoloFe. */
	public static final Integer startColSegnatura = Integer.valueOf(28);

	/** codice tipo oggetto. */
	private BigDecimal cdTipoOggetto;
	
	/**
	 * Istanzia un nuovo excel catalogazione tpl.
	 *
	 * @param cdTipoOggetto  codice tipo oggetto
	 * @param inputStream  input stream
	 * @throws Exception exception
	 */
	public ExcelCatalogazioneTpl(BigDecimal cdTipoOggetto, InputStream inputStream) throws Exception {
		log.debug("[ExcelCatalogazioneTpl] Entro nel metodo");
		if(inputStream!=null) {
			log.debug("[ExcelCatalogazioneTpl] Apro il file Excel...");
			setWb(new XSSFWorkbook(inputStream));
//	        Sheet sheet = ExcelWriterUtils.wb.getSheetAt(0);
		} else {
			throw new Exception("inputStream non puo' essere nullo...");
		}
		this.cdTipoOggetto = cdTipoOggetto;
		if(this.cdTipoOggetto==null) {
			throw new Exception("cdTipoOggetto non puo' essere nullo...");
		}
	}

	/**
	 * Aggiunge cmb content.
	 *
	 * @param rangeName  range name
	 * @param cmbContent  cmb content
	 * @param rowIdx  row idx
	 * @param colIdx  col idx
	 * @throws Exception exception
	 */
	public void addCmbContent(String rangeName, List<String> cmbContent, Integer rowIdx, Integer colIdx) throws Exception {
		
		if(!cmbContent.isEmpty()){
			// definisco i Nomi da usare nella fase di ValidazioneDati...
    		Sheet sheetValori = ExcelWriterUtils.wb.getSheet("ValoriFissi");
    		
    		List<CellStyle> vStyles = new ArrayList<CellStyle>();
    		for(int i=0; i<cmbContent.size(); i++) {
    			vStyles.add(null);
    		}
    		String[] arrayCmb = cmbContent.toArray(new String[cmbContent.size()]);
    		CellStyle[] arraySty = vStyles.toArray(new CellStyle[vStyles.size()]);
    		createTableCol(sheetValori, arrayCmb, arraySty, rowIdx, colIdx);	
       		
    		
    		// http://poi.apache.org/spreadsheet/quick-guide.html#Validation
    		XSSFName name = (XSSFName) ExcelWriterUtils.wb.getName(rangeName);
    		if(name==null) {
    			//name non definito...
    			name = (XSSFName) ExcelWriterUtils.wb.createName();
    			name.setNameName(rangeName);
    		}
    		Integer startRefNumForNome  = Integer.valueOf(2);
    		Integer endRefNumForNome 	= Integer.valueOf(cmbContent.size()+1);
    		String startRefColForNome 		= "$"+CellReference.convertNumToColString(colIdx)+"$"+startRefNumForNome;
    		String endRefColForForNome 		= "$"+CellReference.convertNumToColString(colIdx)+"$"+endRefNumForNome;
    		name.setRefersToFormula("'ValoriFissi'!"+startRefColForNome+":"+endRefColForForNome);
    		
    		// applico alle celle indicate la ValidazioneDati...
    		DataValidation dataValidation = null;
    		DataValidationConstraint constraint = null;
    		DataValidationHelper validationHelper = null;
    
    		Sheet sheetDati = ExcelWriterUtils.wb.getSheet("Dati");
    		
    		validationHelper = new XSSFDataValidationHelper((XSSFSheet) sheetDati);
    		CellRangeAddressList addressList = new CellRangeAddressList(1,2500,colIdx,colIdx);
    
    		constraint = validationHelper.createFormulaListConstraint(rangeName);
    		dataValidation = validationHelper.createValidation(constraint, addressList);
    		
    		dataValidation.setSuppressDropDownArrow(true);    
    		dataValidation.setShowErrorBox(true);
    		
    		dataValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
    		dataValidation.createErrorBox("Errore", "Input non valido!");
    		  
    		sheetDati.addValidationData(dataValidation);
		}
	}
}

