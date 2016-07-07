package it.lispa.bdl;

import it.lispa.bdl.server.utils.Z3950Utils;
import it.lispa.bdl.shared.dto.UnimarcDTO;

import java.util.List;

public class TestZ3950Utils {

	public static void main(String[] args) throws Exception {
		System.out.println("Entro nel MAIN");
		test();
	}

	private static void test() throws Exception{
		String zurl = "opac.sbn.it";
		Integer port = new Integer(3950);
		String dbname = "nopac";
		String syntax = "UNIMARC"; 
		List<UnimarcDTO> res1 = Z3950Utils.search(zurl, port, dbname, syntax, "IT\\ICCU\\LO1\\1433168", null, null, null, null);
		//List<UnimarcDTO> res1 = Z3950Utils.search("IT\\ICCU\\MUS\\0319984", null, null, null, null);
		//List<UnimarcDTO> res1 = Z3950Utils.search("@attrset bib-1 @attr 1=42 @attr 4=1 \"IT\\ICCU\\MIL\\0713515\"");
		//List<UnimarcDTO> res1 = Z3950Utils.search("IT\\ICCU\\CSA\\0113115", null, null, null, null);
		printSearchResult(res1);
		
	}
	private static void printSearchResult(List<UnimarcDTO> records){
		System.out.println("*********************************************");
		if(!records.isEmpty()){
			System.out.println("TROVATI "+records.size()+" risultati !");
			System.out.println("*********************************************");
			for(UnimarcDTO rec:records){
				System.out.println("ID:                  "+rec.getId());
				System.out.println("Titolo:              "+rec.getTitolo());
				System.out.println("TipoOggetto:         "+rec.getTipoOggetto());
				System.out.println("Descrizione Fisica:  "+rec.getDescrizioniFisiche().toString());
				System.out.println("Autore:              "+rec.getAutore());
				System.out.println("Autore Secondario1:  "+rec.getAutoriSecondari1().toString());
				System.out.println("Autore Secondario2:  "+rec.getAutoriSecondari2().toString());
				System.out.println("Editore:             "+rec.getEditori().toString());
				System.out.println("Lingua:              "+rec.getLingua());
				System.out.println("ISBN:                "+rec.getISBN());
				System.out.println("ISSN:                "+rec.getISSN());
				System.out.println("Luogo Pubblicazione: "+rec.getLuoghiPubblicazione().toString());
				System.out.println("Data Pubblicazione:  "+rec.getDatePubblicazione().toString());	
				System.out.println("Titolo Superiore:    "+rec.getTitoliSuperiori().toString());	
				System.out.println("Titolo Inferiore:    "+rec.getTitoliInferiori().toString());	
				System.out.println("*********************************************");
				System.out.println(rec.getRecord());
				System.out.println("*********************************************");
			}
		}else{

			System.out.println("NESSUN RISULTATO TROVATO!");
			System.out.println("*********************************************");
		}
	}
	
}
