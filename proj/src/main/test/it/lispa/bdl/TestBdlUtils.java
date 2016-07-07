package it.lispa.bdl;

import it.lispa.bdl.utils.core.BdlUtilsApp;

public class TestBdlUtils {
	
	public static void main(String[] args) {
		final Integer numArgs = Integer.valueOf(2);
		if(args.length!=numArgs) {
			System.out.println("[main] Errore nel passaggio dei parametri ");
			return;
		}
		creaImmagini(args);
		return;
    }
	
	private static void creaImmagini(String[] args) {
		BdlUtilsApp.main(args);
	}
}
