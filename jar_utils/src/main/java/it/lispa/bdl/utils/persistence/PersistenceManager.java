package it.lispa.bdl.utils.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceManager {

	private EntityManagerFactory emFactory;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PersistenceManager(String url, String username, String password) {
		
	    Map properties = new HashMap();
	    properties.put("javax.persistence.jdbc.url", url);
	    properties.put("javax.persistence.jdbc.user", username);
	    properties.put("javax.persistence.jdbc.password", password);
	    
		emFactory = Persistence.createEntityManagerFactory("bdlUtilsEm", properties);
	}

	public EntityManager getEntityManager() {
	    return (EntityManager) emFactory.createEntityManager();
	}
	
	public void close() {
		emFactory.close();
	}
}