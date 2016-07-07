package it.lispa.bdl.server.utils;

import it.lispa.bdl.shared.dto.UnimarcDTO;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.marc4j.MarcReader;
import org.marc4j.MarcStreamReader;
import org.yaz4j.Connection;
import org.yaz4j.Record;
import org.yaz4j.ResultSet;
import org.yaz4j.exception.ZoomException;

/**
 * Class Z3950Utils.
 */
public class Z3950Utils {

	/** log. */
	private static Logger log = Logger.getLogger(Z3950Utils.class);

	/**
	 * Search.
	 *
	 * @param zurl  zurl
	 * @param port  port
	 * @param dbname  dbname
	 * @param syntax  syntax
	 * @param id  id
	 * @param autore  autore
	 * @param titolo  titolo
	 * @param editore  editore
	 * @param anno  anno
	 * @return list
	 * @throws Exception exception
	 */
	public static List<UnimarcDTO> search(String zurl, Integer port, String dbname, String syntax, String id, String autore, String titolo, String editore, String anno) throws Exception{
		log.debug("[search] Entro nel metodo");

		String query = buildQuery(id, autore, titolo, editore, anno);
		log.debug("[search] Ho calcolato la query "+query+" , procedo nel metodo search(query)");
		List<UnimarcDTO> items = search(zurl, port, dbname, syntax, query);
		
		log.debug("[search] Esco dal metodo ritornando "+items.size()+" oggetti");
		return items;
	}
	
	/**
	 * Search.
	 *
	 * @param zurl  zurl
	 * @param port  port
	 * @param dbname  dbname
	 * @param syntax  syntax
	 * @param query  query
	 * @return list
	 * @throws Exception exception
	 */
	public static List<UnimarcDTO> search(String zurl, Integer port, String dbname,String syntax, String query) throws Exception{
		
		List<UnimarcDTO> items = new ArrayList<UnimarcDTO>();
		search(zurl, port,dbname,syntax, query,items);
		return items;
	}
	
	/**
	 * Search.
	 *
	 * @param zurl  zurl
	 * @param port  port
	 * @param dbname  dbname
	 * @param syntax  syntax
	 * @param query  query
	 * @param items  items
	 * @throws Exception exception
	 */
	public static void search(String zurl, Integer port,String dbname,String syntax, String query, List<UnimarcDTO> items) throws Exception{
		
		int maxrecs=10;

		log.debug("[search] Parametri");
		log.debug("[search]   zurl: " + zurl);
		log.debug("[search]   port: " + port);
		log.debug("[search]   dbname: " + dbname);
		log.debug("[search]   query: " + query);
		log.debug("[search]   syntax: " + syntax);
		log.debug("[search]   maxrecs: " + maxrecs);

		Connection con = new Connection(zurl, port);
		con.setDatabaseName(dbname);
		con.setSyntax(syntax);
		try {
			log.debug("[search] Mi connetto");
			con.connect();
			log.debug("[search] Eseguo la query");
			@SuppressWarnings("deprecation")
			ResultSet set = con.search(query, Connection.QueryType.PrefixQuery);

			log.debug("[search] Ho ricevuto "+set.getHitCount()+" risultati");
			//Iterator<Record> itr = set.iterator();
			for(int i=0; i<set.getHitCount() && i<maxrecs; i++) {
				log.debug("[search] Tratto il risultato "+i);
				Record rec = set.getRecord(i);
				if (rec == null) {
					continue;				
				}
				String recordStr = rec.render();				
				InputStream in = new ByteArrayInputStream(rec.getContent());
				MarcReader reader = new MarcStreamReader(in,"UTF-8");
				while (reader.hasNext()) {
					org.marc4j.marc.Record record = reader.next();
					UnimarcDTO dto = UnimarcUtils.buildUnimarcRecord(record, recordStr);

					items.add(dto);

					List<String> titoliSuperiori = dto.getTitoliSuperiori();
					if(titoliSuperiori!=null && !titoliSuperiori.isEmpty()){ 
						for(String titSup:titoliSuperiori){
							if(!idIsContained(titSup,items)){
								search(zurl, port, dbname, syntax, buildQuery(titSup, null, null, null, null),items);
							}
						}						
					}else{
						List<String> titoliInferiori= dto.getTitoliInferiori();
						if(titoliInferiori!=null && !titoliInferiori.isEmpty()){
							for(String titInf:titoliInferiori){
								if(!idIsContained(titInf,items)){
									search(zurl, port, dbname, syntax, buildQuery(titInf, null, null, null, null),items);
								}
							}						
						}
					}
				}
			}
		} catch (ZoomException ze) {
			throw new Exception(ze);
		} finally {
			con.close();
		}
	}
	
	/**
	 * Crea query.
	 *
	 * @param id  id
	 * @param autore  autore
	 * @param titolo  titolo
	 * @param editore  editore
	 * @param anno  anno
	 * @return string
	 * @throws AsyncServiceException async service exception
	 */
	public static String buildQuery(String id, String autore, String titolo, String editore, String anno) throws AsyncServiceException{
		
		/*
			
			@attrset bib-1 @and @attr 1=4 @attr 4=2 "Cesare Egitto" @attr 1=1003 @attr 4=2 "Pacini Giovanni"
			
		    'author'                   => {'attribute' => '1003', 'structure' => '1' },
		    'title'                    => {'attribute' => '4'   , 'structure' => '1' },
		    'date_publication'         => {'attribute' => '31'  , 'structure' => '1' },
		    'publisher'                => {'attribute' => '1018', 'structure' => '1' },
		    'institution_directory_id' => {'attribute' => '1044', 'structure' => '1' },
		    'isbn'                     => {'attribute' => '7'   , 'structure' => '1' },
		    'issn'                     => {'attribute' => '8'   , 'structure' => '1' },
		    'bid'                      => {'attribute' => '1032', 'structure' => '1' }
			
		    Use attribute (1). Common use attributes are 1 Personal-name, 4 Title, 7 ISBN, 8 ISSN, 30 Date, 62 Subject, 1003 Author), 1016 Any. Specify value as an integer.
			Relation attribute (2). Common values are 1 <, 2 <=, 3 =, 4 >=, 5 >, 6 <>, 100 phonetic, 101 stem, 102 relevance, 103 always matches.
			Position attribute (3). Values: 1 first in field, 2 first in any subfield, 3 any position in field.
			Structure attribute (4). Values: 1 phrase, 2 word, 3 key, 4 year, 5 date, 6 word list, 100 date (un), 101 name (norm), 102 name (un), 103 structure, 104 urx, 105 free-form-text, 106 document-text, 107 local-number, 108 string, 109 numeric string.
			Truncation attribute (5). Values: 1 right, 2 left, 3 left& right, 100 none, 101 process #, 102 regular-1, 103 regular-2, 104 CCL.
			Completeness attribute (6). Values: 1 incomplete subfield, 2 complete subfield, 3 complete field.
			
		*/
		
		String q = "@attrset bib-1 ";
		List<String> qParts = new ArrayList<String>();

		if(id!=null && !"".equals(id)){
			
			id = id.replace("\\", "\\\\");
			
//			if( (titolo!=null && !"".equals(titolo)) || (autore!=null && !"".equals(autore)) || (editore!=null && !"".equals(editore)) || (anno!=null && !"".equals(anno)) ){
//				throw new AsyncServiceException("Ricercando per Identificativo SBN non e' possibile specificare ulteriori parametri di ricerca!"); 
//			}	
			if( (titolo!=null && !"".equals(titolo)) || (autore!=null && !"".equals(autore)) ){
				throw new AsyncServiceException("Ricercando per Identificativo SBN non e' possibile specificare ulteriori parametri di ricerca!"); 
			}	
			if( (editore!=null && !"".equals(editore)) || (anno!=null && !"".equals(anno)) ){
				throw new AsyncServiceException("Ricercando per Identificativo SBN non e' possibile specificare ulteriori parametri di ricerca!"); 
			}
			
			qParts.add("@attr 1=1032 @attr 4=6 \""+id+"\""); 
		}else{
			if(titolo!=null && !"".equals(titolo)){
				qParts.add("@attr 1=4 @attr 4=2 \""+titolo+"\""); 
			}
			if(autore!=null && !"".equals(autore)){
				qParts.add("@attr 1=1003 @attr 4=2 \""+autore+"\""); 
			}
			if(editore!=null && !"".equals(editore)){
				qParts.add("@attr 1=1018 @attr 4=2 \""+editore+"\""); 
			}
			if(anno!=null && !"".equals(anno)){
				qParts.add("@attr 1=31 @attr 4=2 \""+anno+"\""); 
			}	
		}
		if(qParts.size()>1){
			q += " @and ";
		}

	    StringBuilder builder = new StringBuilder();
	    builder.append( qParts.remove(0));
	    for( String s : qParts) {
	        builder.append(" ");
	        builder.append(s);
	    }
	    return q+builder.toString();
	}
	
	/**
	 * Id is contained.
	 *
	 * @param id  id
	 * @param lista  lista
	 * @return true, se vero
	 */
	private static boolean idIsContained(String id, List<UnimarcDTO> lista){
		for(UnimarcDTO item : lista){
			log.debug("Verifico item ID="+item.getId());
			if(item.getId().equals(id)){
				log.debug("La lista non contiene ID="+id);
				return true;
			}
		}
		log.debug("La lista non contiene ID="+id);
		return false;
		
	}
}
