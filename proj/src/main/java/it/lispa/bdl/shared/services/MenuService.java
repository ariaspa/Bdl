package it.lispa.bdl.shared.services;

import it.lispa.bdl.shared.dto.MenuItemDTO;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Interface MenuService.
 */
@RemoteServiceRelativePath("../"+BdlSharedConstants.BDL_SERVICE_PATH_PRIVATE+"/services/MenuService")
public interface MenuService extends RemoteService {
	
	/**
	 * Find funzioni accessibili.
	 *
	 * @return list
	 */
	public List<MenuItemDTO> findFunzioniAccessibili();
}
