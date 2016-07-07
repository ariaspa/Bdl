package it.lispa.bdl.server.utils;

import java.math.BigDecimal;

/**
 * Class BdlServerConstants.
 */
public class BdlServerConstants {
	
	/** La Costante DIR_PDF. */
	public final static String DIR_PDF = "pdf";
	
	/** La Costante DIR_PDFSINGLE. */
	public final static String DIR_PDFSINGLE = "pdfsingle";

	/** La Costante LOGIMPORTAZIONEEXCELSEPARATOR. */
	public final static Character LOGIMPORTAZIONEEXCELSEPARATOR = '\n';
	
	/** La Costante LOGANOMALIARECORDSEPARATOR. */
	public final static Character LOGANOMALIARECORDSEPARATOR = '\n';	
	
	/** La Costante FILE_ACCOLTO. */
	public final static String FILE_ACCOLTO = ".accolto";
	
	/** La Costante FILE_ACCOLTO. */
	public final static String FILE_CREATO = ".creato";
	
	/** La Costante DOT. */
	public final static String DOT = ".";
	
	/** La Costante DOTDOT. */
	public final static String DOTDOT = "..";
	
	/** La Costante FIRST_IMAGE_SEQUENCE. */
	public final static String FIRST_IMAGE_SEQUENCE = "0001";
	
	/** La Costante DIRECTORY_MASTER. */
	public final static String DIRECTORY_MASTER = "master";

	/** La Costante BDL_RUOLO_CD_AMMINISTRATORE. */
	public final static BigDecimal BDL_RUOLO_CD_AMMINISTRATORE = BigDecimal.ONE;
	
	/** La Costante BDL_RUOLO_CD_SUPERVISORE. */
	public final static BigDecimal BDL_RUOLO_CD_SUPERVISORE = new BigDecimal(2);
	
	/** La Costante BDL_RUOLO_CD_CATALOGATORE. */
	public final static BigDecimal BDL_RUOLO_CD_CATALOGATORE = new BigDecimal(3);
	
	/** La Costante BDL_RUOLO_CD_DIGITALIZZATORE. */
	public final static BigDecimal BDL_RUOLO_CD_DIGITALIZZATORE = new BigDecimal(4);

	/** La Costante JSON_LOGICALOP_AND. */
	public final static String JSON_LOGICALOP_AND = "AND";
	
	/** La Costante JSON_LOGICALOP_OR. */
	public final static String JSON_LOGICALOP_OR = "OR";
	
	/** La Costante JSON_LOGICALOP_ANDNOT. */
	public final static String JSON_LOGICALOP_ANDNOT = "ANDNOT";
	
	/** La Costante JSON_LOGICALOPDB_AND. */
	public final static String JSON_LOGICALOPDB_AND = "AND";
	
	/** La Costante JSON_LOGICALOPDB_OR. */
	public final static String JSON_LOGICALOPDB_OR = "OR";
	
	/** La Costante JSON_LOGICALOPDB_ANDNOT. */
	public final static String JSON_LOGICALOPDB_ANDNOT = "AND NOT";
	
	/** La Costante JSON_COMPARISONOP_BETWEEN. */
	public final static String JSON_COMPARISONOP_BETWEEN = "BETWEEN";
	
	/** La Costante JSON_COMPARISONOP_IN. */
	public final static String JSON_COMPARISONOP_IN = "IN";
	
	/** La Costante JSON_COMPARISONOP_INID. */
	public final static String JSON_COMPARISONOP_INID = "IN ID";
	
	/** La Costante JSON_COMPARISONOP_LIKE. */
	public final static String JSON_COMPARISONOP_LIKE = "LIKE";
	
	/** La Costante JSON_COMPARISONOP_NOTEQUAL. */
	public final static String JSON_COMPARISONOP_NOTEQUAL = "NOT EQUAL";
	
	/** La Costante JSON_COMPARISONOP_EQUALID. */
	public final static String JSON_COMPARISONOP_EQUALID = "EQUAL ID";
	
	/** La Costante JSON_COMPARISONOP_EQUAL. */
	public final static String JSON_COMPARISONOP_EQUAL = "EQUAL";
	
	/** La Costante JSON_FILTERTYPE_SUBJECT. */
	public final static String JSON_FILTERTYPE_SUBJECT = "SUBJECT";
	
	/** La Costante JSON_FILTERTYPE_AUTHOR. */
	public final static String JSON_FILTERTYPE_AUTHOR = "AUTHOR";
	
	/** La Costante JSON_FILTERTYPE_TECNIQUE. */
	public final static String JSON_FILTERTYPE_TECNIQUE = "TECNIQUE";
	
	/** La Costante JSON_FILTERTYPE_SUPPORT. */
	public final static String JSON_FILTERTYPE_SUPPORT = "SUPPORT";
	
	/** La Costante JSON_FILTERTYPE_GRAPHICMATERIAL. */
	public final static String JSON_FILTERTYPE_GRAPHICMATERIAL = "GRAPHICMATERIAL";
	
	/** La Costante JSON_FILTERTYPE_INSTITUTE. */
	public final static String JSON_FILTERTYPE_INSTITUTE = "INSTITUTE";
	
	/** La Costante JSON_FILTERTYPE_KIND. */
	public final static String JSON_FILTERTYPE_KIND = "KIND";
	
	/** La Costante JSON_FILTERTYPE_COLLECTION. */
	public final static String JSON_FILTERTYPE_COLLECTION = "COLLECTION";
	
	/** La Costante JSON_FILTERTYPE_LANG. */
	public final static String JSON_FILTERTYPE_LANG = "LANG";
	
	/** La Costante JSON_FILTERTYPE_PUBLISHER. */
	public final static String JSON_FILTERTYPE_PUBLISHER = "PUBLISHER";
	
	/** La Costante JSON_FILTERTYPE_DATE. */
	public final static String JSON_FILTERTYPE_DATE = "DATE";
	
	/** La Costante JSON_FILTERTYPE_TITLE. */
	public final static String JSON_FILTERTYPE_TITLE = "TITLE";
	
	/** La Costante JSON_FILTERTYPE_TYPE. */
	public final static String JSON_FILTERTYPE_TYPE = "TYPE";
	
	/** La Costante JSON_FILTERTYPE_TYPE. */
	public final static String JSON_FILTERTYPE_SEGNATURA = "SEGNATURA";

	/** La Costante JSON_LOGICALOPS. */
	public final static String[] JSON_LOGICALOPS = new String[] {
			JSON_LOGICALOP_AND,
			JSON_LOGICALOP_OR,
			JSON_LOGICALOP_ANDNOT
	};
	
	/** La Costante JSON_COMPARISONOPS. */
	public final static String[] JSON_COMPARISONOPS = new String[] {
		JSON_COMPARISONOP_EQUAL,
		JSON_COMPARISONOP_EQUALID,
		JSON_COMPARISONOP_NOTEQUAL,
		JSON_COMPARISONOP_LIKE,
		JSON_COMPARISONOP_IN,
		JSON_COMPARISONOP_INID,
		JSON_COMPARISONOP_BETWEEN
	};

	/** La Costante JSON_FILTERTYPES. */
	public final static String[] JSON_FILTERTYPES = new String[] {
		JSON_FILTERTYPE_TYPE,
		JSON_FILTERTYPE_TITLE,
		JSON_FILTERTYPE_DATE,
		JSON_FILTERTYPE_PUBLISHER,
		JSON_FILTERTYPE_LANG,
		JSON_FILTERTYPE_COLLECTION,
		JSON_FILTERTYPE_KIND,
		JSON_FILTERTYPE_INSTITUTE,
		JSON_FILTERTYPE_GRAPHICMATERIAL,
		JSON_FILTERTYPE_SUPPORT,
		JSON_FILTERTYPE_TECNIQUE,
		JSON_FILTERTYPE_AUTHOR,
		JSON_FILTERTYPE_SUBJECT,
		JSON_FILTERTYPE_SEGNATURA
	};
	
}
