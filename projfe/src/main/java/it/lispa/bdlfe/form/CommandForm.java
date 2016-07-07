package it.lispa.bdlfe.form;

import it.lispa.bdlfe.utils.BdlfeSearchItemsPerPage;
import it.lispa.bdlfe.utils.BdlfeSearchOrderOptions;

public class CommandForm {
	private String commandStr;
	private Integer commandInt;
	private BdlfeSearchOrderOptions commandOrdOpt;
	private BdlfeSearchItemsPerPage commandResPPOpt;
	
	public CommandForm(){
		
	}
	public CommandForm(String commandStr, Integer commandInt) {
		this.commandStr = commandStr;
		this.commandInt = commandInt;
	}
	public CommandForm(String commandStr) {
		this.commandStr = commandStr;
	}
	public CommandForm(Integer commandInt) {
		this.commandInt = commandInt;
	}
	public CommandForm(BdlfeSearchOrderOptions commandOrdOpt) {
		this.commandOrdOpt = commandOrdOpt;
	}
	public CommandForm(BdlfeSearchItemsPerPage commandResPPOpt) {
		this.commandResPPOpt = commandResPPOpt;
	}
	public String getCommandStr() {
		return commandStr;
	}
	public void setCommandStr(String commandStr) {
		this.commandStr = commandStr;
	}
	public Integer getCommandInt() {
		return commandInt;
	}
	public void setCommandInt(Integer commandInt) {
		this.commandInt = commandInt;
	}
	public BdlfeSearchOrderOptions getCommandOrdOpt() {
		return commandOrdOpt;
	}
	public void setCommandOrdOpt(BdlfeSearchOrderOptions commandOrdOpt) {
		this.commandOrdOpt = commandOrdOpt;
	}
	public BdlfeSearchItemsPerPage getCommandResPPOpt() {
		return commandResPPOpt;
	}
	public void setCommandResPPOpt(BdlfeSearchItemsPerPage commandResPPOpt) {
		this.commandResPPOpt = commandResPPOpt;
	}
}
