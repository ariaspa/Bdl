package it.lispa.bdl.server.utils;

import it.lispa.bdl.shared.dto.VOggettoCountDTO;

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
 * Class ExcelMonitoraggioLav.
 */
public class ExcelMonitoraggioLav extends ExcelWriterUtils{

	/** log. */
	private static Logger log = Logger.getLogger(ExcelMonitoraggioLav.class);

	/**
	 * Istanzia un nuovo excel monitoraggio lav.
	 *
	 * @param righeConteggi  righe conteggi
	 * @throws Exception exception
	 */
	public ExcelMonitoraggioLav(List<VOggettoCountDTO> righeConteggi) throws Exception {
		log.debug("[ExcelMonitoraggioLav] Entro nel metodo");
		setWb(new XSSFWorkbook());
		initStyles();
		createSheet(righeConteggi);
	}

	/**
	 * Crea sheet.
	 *
	 * @param righeConteggi  righe conteggi
	 * @throws Exception exception
	 */
	private void createSheet(List<VOggettoCountDTO> righeConteggi) throws Exception{
		log.debug("[createSheet] Entro nel metodo");

		String sheetName = "Esportazione";
		String sheetTitle = "Report Monitoraggio Lavorazione";

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
		CellRangeAddress region = CellRangeAddress.valueOf("A1:E1");
		sh.addMergedRegion(region);

		row = sh.createRow(++currentRow);

		log.debug("[createSheet] Procedo con la prima tabella");

		row = sh.createRow(++currentRow);
		currentCell = 0;
		row.setHeightInPoints((float) 36);


		String[] headersTotalsTable = {
				"Stato", 
				"Flag", 
				"Conteggio Oggetti", 
				"Immagini Previste", 
				"Immagini Digitalizzate"
		}; 
		CellStyle[] hStylesTotalsTable = {
				stili.get("tableBigTitle"),
				stili.get("tableBigTitle"),
				stili.get("tableBigTitle"),
				stili.get("tableBigTitle"),
				stili.get("tableBigTitle")
		};
		createTableHeader(sh, headersTotalsTable, hStylesTotalsTable, currentRow, currentCell);

		Float sumOggetti = new Float(0);
		Float sumImgPrev = new Float(0);
		Float sumImgDigit = new Float(0);

		Iterator<VOggettoCountDTO> righeItr = righeConteggi.iterator();
		while(righeItr.hasNext()){
			VOggettoCountDTO riga = (VOggettoCountDTO) righeItr.next();

			if(riga.getOggetti()!=null){
				sumOggetti += riga.getOggetti().floatValue();
			}
			if(riga.getImmaginiPreviste()!=null){
				sumImgPrev += riga.getImmaginiPreviste().floatValue();
			}
			if(riga.getImmaginiDigitalizzate()!=null){
				sumImgDigit += riga.getImmaginiDigitalizzate().floatValue();
			}
			
			row = sh.createRow(++currentRow);
			currentCell = 0;
			Object[] valuesDetailTable = {
					riga.getStatoHuman(),
					riga.getFlagHuman(),
					riga.getOggetti(),
					riga.getImmaginiPreviste(),
					riga.getImmaginiDigitalizzate()
			}; 
			CellStyle[] vStylesDetailTable = {
					stili.get("tableLittleCell"), 
					stili.get("tableLittleCell"), 
					stili.get("tableLittleCellInteger"),  
					stili.get("tableLittleCellInteger"),
					stili.get("tableLittleCellInteger")
			};
			createTableRow(sh, valuesDetailTable, vStylesDetailTable, currentRow, currentCell);			
		}


		log.debug("[createSheet] Creo la riga dei totali");
		row = sh.createRow(++currentRow);
		currentCell = 0;
		Object[] valuesTotalsTable = {
				"Totale", 
				"",
				sumOggetti,
				sumImgPrev, 
				sumImgDigit
		}; 
		CellStyle[] vStylesTotalsTable = {
				stili.get("tableBigCell"), 
				stili.get("tableBigCell"), 
				stili.get("tableBigCellInteger"),  
				stili.get("tableBigCellInteger"), 
				stili.get("tableBigCellInteger")
		};
		createTableRow(sh, valuesTotalsTable, vStylesTotalsTable, currentRow, currentCell);



		log.debug("[createSheet] Imposto la larghezza delle colonne");
		sh.setColumnWidth(0, (int) (26.29*255));
		sh.setColumnWidth(1, (int) (26.29*255));
		sh.setColumnWidth(2, (int) (16.57*255));
		sh.setColumnWidth(3, (int) (16.57*255));
		sh.setColumnWidth(4, (int) (16.57*255));
	}

}

