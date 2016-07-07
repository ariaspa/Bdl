package it.lispa.bdl.server.mail;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * Class EmailSender.
 */
public class EmailSender {
	
	/** mail sender. */
	private MailSender mailSender;
	
	/** simple mail message. */
	private SimpleMailMessage simpleMailMessage;
	
	/**
	 * Legge mail sender.
	 *
	 * @return the mail sender
	 */
	public MailSender getMailSender() {
		return mailSender;
	}
	
	/**
	 * Imposta mail sender.
	 *
	 * @param mailSender the new mail sender
	 */
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	/**
	 * Legge simple mail message.
	 *
	 * @return the simple mail message
	 */
	public SimpleMailMessage getSimpleMailMessage() {
		return simpleMailMessage;
	}
	
	/**
	 * Imposta simple mail message.
	 *
	 * @param simpleMailMessage the new simple mail message
	 */
	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}	
}