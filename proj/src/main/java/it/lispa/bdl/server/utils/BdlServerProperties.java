package it.lispa.bdl.server.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class BdlServerProperties.
 */
@Component
public class BdlServerProperties {
	
	/** dir netapp tmp. */
	@Value("${dir.netapp.tmp}")
	private String dirNetappTmp;

	/** dir netapp wrk. */
	@Value("${dir.netapp.wrk}")
	private String dirNetappWrk;
	

	/** imagemagick bin. */
	@Value("${imagemagick.bin}")
	private String imagemagickBin;
	
	/** debug mainmenu. */
	@Value("${debug.mainmenu}")
	private String debugMainmenu;
	

	/** opac sbn addr. */
	@Value("${opac.sbn.addr}")
	private String opacSbnAddr;
	
	/** opac sbn port. */
	@Value("${opac.sbn.port}")
	private Integer opacSbnPort;
	
	/** opac sbn dbname. */
	@Value("${opac.sbn.dbname}")
	private String opacSbnDbname;
	
	/** opac sbn syntax. */
	@Value("${opac.sbn.syntax}")
	private String opacSbnSyntax;

	/** mail sender host. */
	@Value("${mailsender.host}")
	private String mailSenderHost;

	/** mail sender port. */
	@Value("${mailsender.port}")
	private Integer mailSenderPort;

	/** mail sender username. */
	@Value("${mailsender.username}")
	private String mailSenderUsername;

	/** mail sender password. */
	@Value("${mailsender.password}")
	private String mailSenderPassword;

	/** mail sender auth. */
	@Value("${mailsender.auth}")
	private String mailSenderAuth;

	/** mail sender start ttls enable. */
	@Value("${mailsender.starttls.enable}")
	private String mailSenderStartTtlsEnable;

	/** mail sender default from. */
	@Value("${mailsender.default.from}")
	private String mailSenderDefaultFrom;

	/** mail sender default cc. */
	@Value("${mailsender.default.cc}")
	private String mailSenderDefaultCc;

	/** mail sender default bcc. */
	@Value("${mailsender.default.bcc}")
	private String mailSenderDefaultBcc;

	/** mail sender debug recipient. */
	@Value("${mailsender.debug.recipient}")
	private String mailSenderDebugRecipient;

	/** idpc url login. */
	@Value("${idpc.url.login}")
	private String idpcUrlLogin;

	/** idpc url logout. */
	@Value("${idpc.url.logout}")
	private String idpcUrlLogout;

	/** bdlquartz scheduler. */
	@Value("${bdlquartz.scheduler}")
	private String bdlquartzScheduler;
	
	/**
	 * Legge dir netapp tmp.
	 *
	 * @return the dir netapp tmp
	 */
	public String getDirNetappTmp() {
		return dirNetappTmp;
	}

	/**
	 * Imposta dir netapp tmp.
	 *
	 * @param dirNetappTmp the new dir netapp tmp
	 */
	public void setDirNetappTmp(String dirNetappTmp) {
		this.dirNetappTmp = dirNetappTmp;
	}

	/**
	 * Legge dir netapp wrk.
	 *
	 * @return the dir netapp wrk
	 */
	public String getDirNetappWrk() {
		return dirNetappWrk;
	}

	/**
	 * Imposta dir netapp wrk.
	 *
	 * @param dirNetappWrk the new dir netapp wrk
	 */
	public void setDirNetappWrk(String dirNetappWrk) {
		this.dirNetappWrk = dirNetappWrk;
	}

	/**
	 * Legge imagemagick bin.
	 *
	 * @return the imagemagick bin
	 */
	public String getImagemagickBin() {
		return imagemagickBin;
	}

	/**
	 * Imposta imagemagick bin.
	 *
	 * @param imagemagickBin the new imagemagick bin
	 */
	public void setImagemagickBin(String imagemagickBin) {
		this.imagemagickBin = imagemagickBin;
	}

	/**
	 * Legge debug mainmenu.
	 *
	 * @return the debug mainmenu
	 */
	public String getDebugMainmenu() {
		return debugMainmenu;
	}

	/**
	 * Imposta debug mainmenu.
	 *
	 * @param debugMainmenu the new debug mainmenu
	 */
	public void setDebugMainmenu(String debugMainmenu) {
		this.debugMainmenu = debugMainmenu;
	}

	/**
	 * Legge opac sbn addr.
	 *
	 * @return the opac sbn addr
	 */
	public String getOpacSbnAddr() {
		return opacSbnAddr;
	}

	/**
	 * Imposta opac sbn addr.
	 *
	 * @param opacSbnAddr the new opac sbn addr
	 */
	public void setOpacSbnAddr(String opacSbnAddr) {
		this.opacSbnAddr = opacSbnAddr;
	}

	/**
	 * Legge opac sbn port.
	 *
	 * @return the opac sbn port
	 */
	public Integer getOpacSbnPort() {
		return opacSbnPort;
	}

	/**
	 * Imposta opac sbn port.
	 *
	 * @param opacSbnPort the new opac sbn port
	 */
	public void setOpacSbnPort(Integer opacSbnPort) {
		this.opacSbnPort = opacSbnPort;
	}

	/**
	 * Legge opac sbn dbname.
	 *
	 * @return the opac sbn dbname
	 */
	public String getOpacSbnDbname() {
		return opacSbnDbname;
	}

	/**
	 * Imposta opac sbn dbname.
	 *
	 * @param opacSbnDbname the new opac sbn dbname
	 */
	public void setOpacSbnDbname(String opacSbnDbname) {
		this.opacSbnDbname = opacSbnDbname;
	}

	/**
	 * Legge opac sbn syntax.
	 *
	 * @return the opac sbn syntax
	 */
	public String getOpacSbnSyntax() {
		return opacSbnSyntax;
	}

	/**
	 * Imposta opac sbn syntax.
	 *
	 * @param opacSbnSyntax the new opac sbn syntax
	 */
	public void setOpacSbnSyntax(String opacSbnSyntax) {
		this.opacSbnSyntax = opacSbnSyntax;
	}

	/**
	 * Legge mail sender host.
	 *
	 * @return the mail sender host
	 */
	public String getMailSenderHost() {
		return mailSenderHost;
	}

	/**
	 * Imposta mail sender host.
	 *
	 * @param mailSenderHost the new mail sender host
	 */
	public void setMailSenderHost(String mailSenderHost) {
		this.mailSenderHost = mailSenderHost;
	}

	/**
	 * Legge mail sender port.
	 *
	 * @return the mail sender port
	 */
	public Integer getMailSenderPort() {
		return mailSenderPort;
	}

	/**
	 * Imposta mail sender port.
	 *
	 * @param mailSenderPort the new mail sender port
	 */
	public void setMailSenderPort(Integer mailSenderPort) {
		this.mailSenderPort = mailSenderPort;
	}

	/**
	 * Legge mail sender username.
	 *
	 * @return the mail sender username
	 */
	public String getMailSenderUsername() {
		return mailSenderUsername;
	}

	/**
	 * Imposta mail sender username.
	 *
	 * @param mailSenderUsername the new mail sender username
	 */
	public void setMailSenderUsername(String mailSenderUsername) {
		this.mailSenderUsername = mailSenderUsername;
	}

	/**
	 * Legge mail sender password.
	 *
	 * @return the mail sender password
	 */
	public String getMailSenderPassword() {
		return mailSenderPassword;
	}

	/**
	 * Imposta mail sender password.
	 *
	 * @param mailSenderPassword the new mail sender password
	 */
	public void setMailSenderPassword(String mailSenderPassword) {
		this.mailSenderPassword = mailSenderPassword;
	}

	/**
	 * Legge mail sender auth.
	 *
	 * @return the mail sender auth
	 */
	public String getMailSenderAuth() {
		return mailSenderAuth;
	}

	/**
	 * Imposta mail sender auth.
	 *
	 * @param mailSenderAuth the new mail sender auth
	 */
	public void setMailSenderAuth(String mailSenderAuth) {
		this.mailSenderAuth = mailSenderAuth;
	}

	/**
	 * Legge mail sender start ttls enable.
	 *
	 * @return the mail sender start ttls enable
	 */
	public String getMailSenderStartTtlsEnable() {
		return mailSenderStartTtlsEnable;
	}

	/**
	 * Imposta mail sender start ttls enable.
	 *
	 * @param mailSenderStartTtlsEnable the new mail sender start ttls enable
	 */
	public void setMailSenderStartTtlsEnable(String mailSenderStartTtlsEnable) {
		this.mailSenderStartTtlsEnable = mailSenderStartTtlsEnable;
	}

	/**
	 * Legge mail sender default from.
	 *
	 * @return the mail sender default from
	 */
	public String getMailSenderDefaultFrom() {
		return mailSenderDefaultFrom;
	}

	/**
	 * Imposta mail sender default from.
	 *
	 * @param mailSenderDefaultFrom the new mail sender default from
	 */
	public void setMailSenderDefaultFrom(String mailSenderDefaultFrom) {
		this.mailSenderDefaultFrom = mailSenderDefaultFrom;
	}

	/**
	 * Legge mail sender default cc.
	 *
	 * @return the mail sender default cc
	 */
	public String getMailSenderDefaultCc() {
		return mailSenderDefaultCc;
	}

	/**
	 * Imposta mail sender default cc.
	 *
	 * @param mailSenderDefaultCc the new mail sender default cc
	 */
	public void setMailSenderDefaultCc(String mailSenderDefaultCc) {
		this.mailSenderDefaultCc = mailSenderDefaultCc;
	}

	/**
	 * Legge mail sender default bcc.
	 *
	 * @return the mail sender default bcc
	 */
	public String getMailSenderDefaultBcc() {
		return mailSenderDefaultBcc;
	}

	/**
	 * Imposta mail sender default bcc.
	 *
	 * @param mailSenderDefaultBcc the new mail sender default bcc
	 */
	public void setMailSenderDefaultBcc(String mailSenderDefaultBcc) {
		this.mailSenderDefaultBcc = mailSenderDefaultBcc;
	}

	/**
	 * Legge mail sender debug recipient.
	 *
	 * @return the mail sender debug recipient
	 */
	public String getMailSenderDebugRecipient() {
		return mailSenderDebugRecipient;
	}

	/**
	 * Imposta mail sender debug recipient.
	 *
	 * @param mailSenderDebugRecipient the new mail sender debug recipient
	 */
	public void setMailSenderDebugRecipient(String mailSenderDebugRecipient) {
		this.mailSenderDebugRecipient = mailSenderDebugRecipient;
	}

	/**
	 * Legge idpc url login.
	 *
	 * @return the idpc url login
	 */
	public String getIdpcUrlLogin() {
		return idpcUrlLogin;
	}

	/**
	 * Imposta idpc url login.
	 *
	 * @param idpcUrlLogin the new idpc url login
	 */
	public void setIdpcUrlLogin(String idpcUrlLogin) {
		this.idpcUrlLogin = idpcUrlLogin;
	}

	/**
	 * Legge idpc url logout.
	 *
	 * @return the idpc url logout
	 */
	public String getIdpcUrlLogout() {
		return idpcUrlLogout;
	}

	/**
	 * Imposta idpc url logout.
	 *
	 * @param idpcUrlLogout the new idpc url logout
	 */
	public void setIdpcUrlLogout(String idpcUrlLogout) {
		this.idpcUrlLogout = idpcUrlLogout;
	}
}
