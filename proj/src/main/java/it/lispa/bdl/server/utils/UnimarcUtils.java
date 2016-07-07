package it.lispa.bdl.server.utils;

import it.lispa.bdl.shared.dto.UnimarcDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.marc4j.marc.ControlField;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Leader;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

/**
 * Class UnimarcUtils.
 */
public class UnimarcUtils {

	/**
	 * Crea unimarc record.
	 *
	 * @param record  record
	 * @param recordStr  record str
	 * @return unimarc dto
	 */
	public static UnimarcDTO buildUnimarcRecord(Record record, String recordStr){

		UnimarcDTO rec = new UnimarcDTO(
			getId(record),
			getTitolo(record),
			getTipoOggetto(record),
			getDescrizioniFisiche(record),
			getAutore(record),
			getAutoriSecondari1(record),
			getAutoriSecondari2(record),
			getEditori(record),
			getLingua(record),
			getISBN(record),
			getISSN(record),
			getLuoghiPubblicazione(record),
			getDatePubblicazione(record),
			getTitoliSuperiori(record),
			getTitoliInferiori(record),
			getPath(record),
			recordStr
		);
		return rec;

	}

	/**
	 * Legge id.
	 *
	 * @param record  record
	 * @return id
	 */
	private static String getId(Record record){
		// 001
		ControlField  campo = (ControlField) record.getVariableField("001");
		return campo.getData();
	}

	/**
	 * Legge titolo.
	 *
	 * @param record  record
	 * @return titolo
	 */
	private static String getTitolo(Record record){
		// 200	$a $b $c $d $e $f $g $h $i $v Titolo
		DataField  campo = (DataField) record.getVariableField("200");
		List<Character> sottoCampi = new ArrayList<Character>();
		sottoCampi.add('a');
		sottoCampi.add('b');
		sottoCampi.add('c');
		sottoCampi.add('d');
		sottoCampi.add('e');
		sottoCampi.add('f');
		sottoCampi.add('g');
		sottoCampi.add('h');
		sottoCampi.add('i');
		sottoCampi.add('v');
		return getFieldValue(campo, sottoCampi);
	}
	
	/**
	 * Legge tipo oggetto.
	 *
	 * @param record  record
	 * @return tipo oggetto
	 */
	private static String getTipoOggetto(Record record){
		// 02227ndm0
		Leader lead = record.getLeader();
		char tipo = lead.getTypeOfRecord();
		return Character.toString(tipo);
	}

	/**
	 * Legge descrizioni fisiche.
	 *
	 * @param record  record
	 * @return descrizioni fisiche
	 */
	private static List<String> getDescrizioniFisiche(Record record){
		//215	$a $c $d $e	Descrizione fisica
		List<VariableField>  campi = (List<VariableField>) record.getVariableFields("215");
		List<Character> sottoCampi = new ArrayList<Character>();
		sottoCampi.add('a');
		sottoCampi.add('c');
		sottoCampi.add('d');
		sottoCampi.add('e');
		return getFieldValues(campi, sottoCampi);
	}

	/**
	 * Legge autore.
	 *
	 * @param record  record
	 * @return autore
	 */
	private static String getAutore(Record record){
		//700	$a $b $c $d $f $3
		DataField  campo = (DataField) record.getVariableField("700");
		List<Character> sottoCampi = new ArrayList<Character>();
		sottoCampi.add('a');
		sottoCampi.add('b');
		sottoCampi.add('c');
		sottoCampi.add('d');
		sottoCampi.add('f');
		return getFieldValue(campo, sottoCampi);
	}

	/**
	 * Legge autori secondari1.
	 *
	 * @param record  record
	 * @return autori secondari1
	 */
	private static List<String> getAutoriSecondari1(Record record){
		//701	$a $b $c $d $f $3	
		List<VariableField>  campi = (List<VariableField>) record.getVariableFields("701");
		List<Character> sottoCampi = new ArrayList<Character>();
		sottoCampi.add('a');
		sottoCampi.add('b');
		sottoCampi.add('c');
		sottoCampi.add('d');
		sottoCampi.add('f');
		return getFieldValues(campi, sottoCampi);
	}

	/**
	 * Legge autori secondari2.
	 *
	 * @param record  record
	 * @return autori secondari2
	 */
	private static List<String> getAutoriSecondari2(Record record){
		//702	$a $b $c $d $f $3	
		List<VariableField>  campi = (List<VariableField>) record.getVariableFields("702");
		List<Character> sottoCampi = new ArrayList<Character>();
		sottoCampi.add('a');
		sottoCampi.add('b');
		sottoCampi.add('c');
		sottoCampi.add('d');
		sottoCampi.add('f');
		return getFieldValues(campi, sottoCampi);
	}

	/**
	 * Legge editori.
	 *
	 * @param record  record
	 * @return editori
	 */
	private static List<String> getEditori(Record record){
		//210	$c	Editore
		List<VariableField>  campi = (List<VariableField>) record.getVariableFields("210");
		List<Character> sottoCampi = new ArrayList<Character>();
		sottoCampi.add('c');
		return getFieldValues(campi, sottoCampi);
	}
	
	/**
	 * Legge lingua.
	 *
	 * @param record  record
	 * @return lingua
	 */
	private static String getLingua(Record record){
		//101	$a	Lingua
		DataField  campo = (DataField) record.getVariableField("101");
		List<Character> sottoCampi = new ArrayList<Character>();
		sottoCampi.add('a');
		return getFieldValue(campo, sottoCampi);
	}

	/**
	 * Legge isbn.
	 *
	 * @param record  record
	 * @return isbn
	 */
	private static List<String> getISBN(Record record){
		//010	$a	Identificativo ISBN
		List<VariableField>  campi = (List<VariableField>) record.getVariableFields("010");
		List<Character> sottoCampi = new ArrayList<Character>();
		sottoCampi.add('a');		
		return getFieldValues(campi, sottoCampi);
	}
	
	/**
	 * Legge issn.
	 *
	 * @param record  record
	 * @return issn
	 */
	private static List<String> getISSN(Record record){
		//011	$a	Identificativo ISSN
		List<VariableField>  campi = (List<VariableField>) record.getVariableFields("011");
		List<Character> sottoCampi = new ArrayList<Character>();
		sottoCampi.add('a');		
		return getFieldValues(campi, sottoCampi);
	}
	
	/**
	 * Legge luoghi pubblicazione.
	 *
	 * @param record  record
	 * @return luoghi pubblicazione
	 */
	private static List<String> getLuoghiPubblicazione(Record record){
		//210	$a	Luogo di pubblicazione
		List<VariableField>  campi = (List<VariableField>) record.getVariableFields("210");
		List<Character> sottoCampi = new ArrayList<Character>();
		sottoCampi.add('a');		
		return getFieldValues(campi, sottoCampi);
	}
	
	/**
	 * Legge date pubblicazione.
	 *
	 * @param record  record
	 * @return date pubblicazione
	 */
	private static List<String> getDatePubblicazione(Record record){
		//210	$d	Data
		List<VariableField>  campi = (List<VariableField>) record.getVariableFields("210");
		List<Character> sottoCampi = new ArrayList<Character>();
		sottoCampi.add('d');		
		return getFieldValues(campi, sottoCampi);
	}
	
	/**
	 * Legge titoli superiori.
	 *
	 * @param record  record
	 * @return titoli superiori
	 */
	private static List<String> getTitoliSuperiori(Record record){
		//461	$1	Titolo incluso
		List<VariableField>  campi = (List<VariableField>) record.getVariableFields("461");

		List<Character> sottoCampi = new ArrayList<Character>();
		sottoCampi.add('1');

		List<String> fieldValues = new ArrayList<String>();
		if(campi!=null){
			for(VariableField field:campi){
				DataField fieldData = (DataField) field;
				String val = getFieldValue(fieldData, sottoCampi);
				val = val.replaceFirst("^001", "");
				if(!"".equals(val)){
					fieldValues.add(val);
				}
			}
		}
		return fieldValues;
	}
	
	/**
	 * Legge titoli inferiori.
	 *
	 * @param record  record
	 * @return titoli inferiori
	 */
	private static List<String> getTitoliInferiori(Record record){
		//462	$1	Titolo incluso
		//463	$1	Titolo incluso
		//464	$1	Titolo incluso

		List<String> fieldValues = new ArrayList<String>();
		List<Character> sottoCampi = new ArrayList<Character>();
		sottoCampi.add('1');

		List<VariableField>  campi = (List<VariableField>) record.getVariableFields("462");
		if(campi!=null){
			for(VariableField field:campi){
				DataField fieldData = (DataField) field;
				String val = getFieldValue(fieldData, sottoCampi);
				val = val.replaceFirst("^001", "");
				if(!"".equals(val)){
					fieldValues.add(val);
				}
			}
		}
		campi = (List<VariableField>) record.getVariableFields("463");
		if(campi!=null){
			for(VariableField field:campi){
				DataField fieldData = (DataField) field;
				String val = getFieldValue(fieldData, sottoCampi);
				val = val.replaceFirst("^001", "");
				if(!"".equals(val)){
					fieldValues.add(val);
				}
			}
		}
		campi = (List<VariableField>) record.getVariableFields("464");
		if(campi!=null){
			for(VariableField field:campi){
				DataField fieldData = (DataField) field;
				String val = getFieldValue(fieldData, sottoCampi);
				val = val.replaceFirst("^001", "");
				if(!"".equals(val)){
					fieldValues.add(val);
				}
			}
		}
		return fieldValues;
	}
	
	/**
	 * Legge path.
	 *
	 * @param record  record
	 * @return path
	 */
	private static String getPath(Record record){
		
		String path = null;
		
		List<String> fieldValues = getTitoliSuperiori(record);
		if( fieldValues!=null && !fieldValues.isEmpty() ){
			path = fieldValues.get(0);
		}
		
		return path;
	}
	
	/**
	 * Legge field value.
	 *
	 * @param field  field
	 * @param subfieldCodes  subfield codes
	 * @return field value
	 */
	private static String getFieldValue(DataField field, List<Character> subfieldCodes){
		List<String> subfieldValues = new ArrayList<String>();
		if(field!=null){
			for (Character subfieldCode : subfieldCodes) {
				Subfield sb = field.getSubfield(subfieldCode);
				if(sb!=null){
					if(!"".equals(sb.getData())) {
						subfieldValues.add(sb.getData());
					}
				}
			}
		}
		subfieldValues.removeAll(Arrays.asList("", null));
		if( subfieldValues.isEmpty()) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		builder.append( subfieldValues.remove(0));
		for( String s : subfieldValues) {
			builder.append( " ");
			builder.append( s);
		}
		return builder.toString();
	}

	/**
	 * Legge field values.
	 *
	 * @param fields  fields
	 * @param subfieldCodes  subfield codes
	 * @return field values
	 */
	private static List<String> getFieldValues(List<VariableField> fields, List<Character> subfieldCodes){
		List<String> fieldValues = new ArrayList<String>();
		if(fields!=null){
			for(VariableField field:fields){
				DataField fieldData = (DataField) field;
				String val = getFieldValue(fieldData, subfieldCodes);
				if(!"".equals(val)){
					fieldValues.add(val);
				}
			}
		}
		return fieldValues;
	}
}
