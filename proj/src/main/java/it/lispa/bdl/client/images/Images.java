package it.lispa.bdl.client.images;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * Interface Images.
 */
public interface Images extends ClientBundle {

	@Source("menu_freccia_off.gif")
	ImageResource getFrecciaOff();
	
	@Source("menu_freccia_on.gif")
	ImageResource getFrecciaOn();
	
	@Source("clear.gif")
	ImageResource getClear();

	@Source("ico_add_16.png")
	ImageResource add();

	@Source("ico_annulla_18.gif")
	ImageResource annulla();

	@Source("ico_approva_18.gif")
	ImageResource approva();

	@Source("ico_approvaDoc_18.gif")
	ImageResource approvaDoc();

	@Source("ico_avanti_18.gif")
	ImageResource avanti();

	@Source("ico_avantiDoc_18.gif")
	ImageResource avantiDoc();
	
	@Source("ico_cartella_16.png")
	ImageResource cartella();

	@Source("ico_classifica_18.gif")
	ImageResource classifica();
	
	@Source("ico_indietro_18.gif")
	ImageResource indietro();
	
	@Source("ico_info_18.gif")
	ImageResource infoI();
	
	@Source("ico_info_18.png")
	ImageResource info();
	
	@Source("ico_modifica_18.gif")
	ImageResource modifica();
	
	@Source("ico_nuovo_18.gif")
	ImageResource nuovo();
	
	@Source("ico_pagina_16.png")
	ImageResource pagina();

	@Source("ico_remove_16.png")
	ImageResource remove();
	
	@Source("ico_ricerca_18.gif")
	ImageResource ricerca();
	
	@Source("ico_salva_18.gif")
	ImageResource salva();
	
	@Source("ico_salvaExcel_18.gif")
	ImageResource salvaExcel();
	
	@Source("ico_salvaPdf_18.gif")
	ImageResource salvaPdf();

	@Source("ico_visualizzaDoc_18.gif")
	ImageResource visualizzaDoc();

	@Source("ico_riscontra_18.gif")
	ImageResource visualizzaUtente();
	
	@Source("ico_undo_16.png")
	ImageResource undo();


}
