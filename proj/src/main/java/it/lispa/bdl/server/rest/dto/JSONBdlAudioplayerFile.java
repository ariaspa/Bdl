package it.lispa.bdl.server.rest.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class JSONBdlAudioplayerFile.
 */
@XmlRootElement
public class JSONBdlAudioplayerFile {
	private int id;
	private String title;
	
	/**
	 * Istanzia un nuovo JSON bdl audioplayer file.
	 *
	 * @param id  id
	 * @param title  title
	 */
	public JSONBdlAudioplayerFile(int id, String title) {
		this.id = id;
		this.title = title;
	}
	
	/**
	 * Legge id.
	 *
	 * @return id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Imposta id.
	 *
	 * @param id nuovo id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Legge title.
	 *
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Imposta title.
	 *
	 * @param title nuovo title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}