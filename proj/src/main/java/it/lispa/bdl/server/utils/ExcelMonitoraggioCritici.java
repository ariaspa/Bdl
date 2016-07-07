package it.lispa.bdl.server.utils;

import it.lispa.bdl.shared.dto.VOggettoDTO;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * Class ExcelMonitoraggioCritici.
 */
public class ExcelMonitoraggioCritici extends ExcelWriterUtils{

	/** log. */
	private static Logger log = Logger.getLogger(ExcelMonitoraggioCritici.class);

	/**
	 * Istanzia un nuovo excel monitoraggio critici.
	 *
	 * @param righeOggetti  righe oggetti
	 * @throws Exception exception
	 */
	public ExcelMonitoraggioCritici(List<VOggettoDTO> righeOggetti) throws Exception {
		log.debug("[ExcelMonitoraggioCritici] Entro nel metodo");
		setWb(new XSSFWorkbook());
		initStyles();
		createSheet(righeOggetti);
	}


	/**
	 * Crea sheet.
	 *
	 * @param righeOggetti  righe oggetti
	 * @throws Exception exception
	 */
	private void createSheet(List<VOggettoDTO> righeOggetti) throws Exception{
		log.debug("[createSheet] Entro nel metodo");

		String sheetName = "Esportazione";
		String sheetTitle = "Report Monitoraggio Oggetti Critici";

		Sheet sh = initSheet(sheetName, sheetTitle);  

		Integer currentRow = -1;
		Integer currentCell = -1;
		Row row = null;
		Cell cell = null;

		// Create a row and put some cells in it. Rows are 0 based.
		row = sh.createRow(++currentRow);

		log.debug("[createSheet] Scrivo il titolo");
		cell = row.createCell(0);
		cell.setCellValue(sheetTitle);
		cell.setCellStyle(stili.get("sheetTitle"));
		row.setHeightInPoints((float) 23.25);
		
		log.debug("[createSheet] Creo il merge per il titolo");
		CellRangeAddress region = CellRangeAddress.valueOf("A1:I1");
		sh.addMergedRegion(region);

		row = sh.createRow(++currentRow);

		log.debug("[createSheet] Procedo con la prima tabella");

		row = sh.createRow(++currentRow);
		currentCell = 0;
		row.setHeightInPoints((float) 36);


		
		String[] headersTotalsTable = {
				"Istituto", 
				"Progetto", 
				"Collezione", 
				"Titolo", 
				"Titolo Superiore", 
				"Immagini Previste", 
				"Tipologia Oggetto", 
				"Critico", 
				"Nota Criticita'"
		}; 
		CellStyle[] hStylesTotalsTable = {
				stili.get("tableBigTitle"), 
				stili.get("tableBigTitle"),  
				stili.get("tableBigTitle"), 
				stili.get("tableBigTitle"),  
				stili.get("tableBigTitle"),  
				stili.get("tableBigTitle"), 
				stili.get("tableBigTitle"),  
				stili.get("tableBigTitle"),  
				stili.get("tableBigTitle")
		};
		createTableHeader(sh, headersTotalsTable, hStylesTotalsTable, currentRow, currentCell);

		Iterator<VOggettoDTO> righeItr = righeOggetti.iterator();
		while(righeItr.hasNext()){
			VOggettoDTO item = (VOggettoDTO) righeItr.next();
			
			row = sh.createRow(++currentRow);
			currentCell = 0;
			Object[] valuesDetailTable = {
					item.getI_DnNome(),
					item.getP_DnTitolo(),
					item.getC_DnTitolo(),
					item.getO_DnTitolo(),
					item.getO_DnTitoloSup(),
					item.getO_NrImmaginiPreviste(),
					item.getT_DnNome(),
					item.getO_FlIsCritico_Human(),
					item.getO_DsNotaCritico()
			}; 
			CellStyle[] vStylesDetailTable = {
					stili.get("tableLittleCell"), 
					stili.get("tableLittleCell"), 
					stili.get("tableLittleCell"), 
					stili.get("tableLittleCell"), 
					stili.get("tableLittleCell"), 
					stili.get("tableLittleCellInteger"), 
					stili.get("tableLittleCell"), 
					stili.get("tableLittleCell"), 
					stili.get("tableLittleCell")
			};
			createTableRow(sh, valuesDetailTable, vStylesDetailTable, currentRow, currentCell);			
		}

		log.debug("[createSheet] Imposto la larghezza delle colonne");
		sh.setColumnWidth(0, (int) (23.86*255));
		sh.setColumnWidth(1, (int) (23.86*255));
		sh.setColumnWidth(2, (int) (23.86*255));
		sh.setColumnWidth(3, (int) (23.86*255));
		sh.setColumnWidth(4, (int) (23.86*255));
		sh.setColumnWidth(5, (int) (10*255));
		sh.setColumnWidth(6, (int) (21.29*255));
		sh.setColumnWidth(7, (int) (11.43*255));
		sh.setColumnWidth(8, (int) (40.14*255));
	}

}

