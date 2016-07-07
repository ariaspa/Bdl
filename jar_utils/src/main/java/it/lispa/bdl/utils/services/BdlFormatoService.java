package it.lispa.bdl.utils.services;

import it.lispa.bdl.commons.domain.BdlFormato;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BdlFormatoService {
	
	@SuppressWarnings("unused")
	private static final Logger mylog = LoggerFactory.getLogger(BdlFormatoService.class);

	protected EntityManager em;

	public BdlFormatoService(EntityManager em) {
		this.em = em;
	}

	public List<BdlFormato> findAll() throws SQLException {
		String jpaQueryStr = "SELECT c FROM BdlFormato c ";
		TypedQuery<BdlFormato> jpaQuery = em.createQuery(jpaQueryStr, BdlFormato.class);
		
		return jpaQuery.getResultList();
	}

	public List<BdlFormato> findByDsNaturaAndCdTipoOggetto(String dsNatura, BigDecimal cdTipoOggetto) throws SQLException {
		String jpaQueryStr = "SELECT c FROM BdlFormato c WHERE dsNatura=?1 and cdTipoOggetto=?2 ";
		TypedQuery<BdlFormato> jpaQuery = em.createQuery(jpaQueryStr, BdlFormato.class);
		jpaQuery.setParameter(1, dsNatura);
		jpaQuery.setParameter(2, cdTipoOggetto);
		
		return jpaQuery.getResultList();
	}
	public List<BdlFormato> findByDsNaturaOrDsNaturaAndCdTipoOggetto(String dsNatura1,String dsNatura2, BigDecimal cdTipoOggetto) throws SQLException {
		String jpaQueryStr = "SELECT c FROM BdlFormato c WHERE (dsNatura=?1 or dsNatura=?2) and cdTipoOggetto=?3 ";
		TypedQuery<BdlFormato> jpaQuery = em.createQuery(jpaQueryStr, BdlFormato.class);
		jpaQuery.setParameter(1, dsNatura1);
		jpaQuery.setParameter(2, dsNatura2);
		jpaQuery.setParameter(3, cdTipoOggetto);
		
		return jpaQuery.getResultList();
	}

	public List<BdlFormato> findByFlRegolaNamingAndCdTipoOggetto(Boolean flRegolaNaming, BigDecimal cdTipoOggetto) {
		String jpaQueryStr = "SELECT c FROM BdlFormato c WHERE flRegolaNaming=?1 and cdTipoOggetto=?2 ";
		TypedQuery<BdlFormato> jpaQuery = em.createQuery(jpaQueryStr, BdlFormato.class);
		jpaQuery.setParameter(1, flRegolaNaming);
		jpaQuery.setParameter(2, cdTipoOggetto);
		
		return jpaQuery.getResultList();
	}
}
