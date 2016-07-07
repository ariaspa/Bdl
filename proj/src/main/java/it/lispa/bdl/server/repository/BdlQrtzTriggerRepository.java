package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlQrtzTrigger;
import it.lispa.bdl.commons.domain.BdlQrtzTriggerPK;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlQrtzTriggerRepository.
 */
public interface BdlQrtzTriggerRepository extends PagingAndSortingRepository<BdlQrtzTrigger, BdlQrtzTriggerPK> {
	@Query(value = "SELECT myTable.* FROM BDL_QRTZ_TRIGGERS myTable WHERE myTable.TRIGGER_NAME LIKE(?1)", nativeQuery = true)
	public List<BdlQrtzTrigger> findByTriggerNameLike(String triggerNameForLike);

}
