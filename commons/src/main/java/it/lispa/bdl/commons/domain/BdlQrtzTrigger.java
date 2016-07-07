package it.lispa.bdl.commons.domain;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Class BdlQrtzTrigger.
 */
@Entity
@Table(name = "BDL_QRTZ_TRIGGERS")
public class BdlQrtzTrigger implements Serializable {

	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = -1570311195644689252L;

	/** comp_id. */
	private BdlQrtzTriggerPK comp_id;
	
	/** job name. */
	private String jobName;

	/** job group. */
	private String jobGroup;

	/** description. */
	private String description;

	/** next fire time. */
	private Long nextFireTime;

	/** prev fire time. */
	private Long prevFireTime;
	
	/** priority. */
	private Long priority;

	/** trigger state. */
	private String triggerState;

	/** trigger type. */
	private String triggerType;

	/** start time. */
	private Long startTime;

	/** end time. */
	private Long endTime;

	/** calendar name. */
	private String calendarName;

	/** misfire instr. */
	private Integer misfireInstr;

	/** job data. */
	private Blob jobData;

	/**
	 * Istanzia un nuovo bdl qrtz trigger.
	 */
	public BdlQrtzTrigger() {
	}

	/**
	 * Istanzia un nuovo bdl qrtz trigger.
	 *
	 * @param comp_id  comp_id
	 * @param triggerState  trigger state
	 * @param triggerType  trigger type
	 * @param startTime  start time
	 */
	public BdlQrtzTrigger(BdlQrtzTriggerPK comp_id, String triggerState, String triggerType, long startTime) {
		this.comp_id = comp_id;
		this.triggerState = triggerState;
		this.triggerType = triggerType;
		this.startTime = startTime;
	}
	
	/**
	 * Legge comp_id.
	 *
	 * @return the comp_id
	 */
	@EmbeddedId
	@AttributeOverrides( {
		@AttributeOverride(name = "schedName", column = @Column(name = "SCHED_NAME", nullable = false, length = 120)),
		@AttributeOverride(name = "triggerName", column = @Column(name = "TRIGGER_NAME", nullable = false, length = 200)),
		@AttributeOverride(name = "triggerGroup", column = @Column(name = "TRIGGER_GROUP", nullable = false, length = 200)) 
	})
	public BdlQrtzTriggerPK getComp_id() {
		return this.comp_id;
	}

	/**
	 * Imposta comp_id.
	 *
	 * @param comp_id the new comp_id
	 */
	public void setComp_id(BdlQrtzTriggerPK comp_id) {
		this.comp_id = comp_id;
	}

	/**
	 * Legge job name.
	 *
	 * @return the job name
	 */
	@Column(name = "JOB_NAME", length = 200)
	public String getJobName() {
		return jobName;
	}

	/**
	 * Imposta job name.
	 *
	 * @param jobName the new job name
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * Legge job group.
	 *
	 * @return the job group
	 */
	@Column(name = "JOB_GROUP", length = 200)
	public String getJobGroup() {
		return jobGroup;
	}

	/**
	 * Imposta job group.
	 *
	 * @param jobGroup the new job group
	 */
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	/**
	 * Legge description.
	 *
	 * @return the description
	 */
	@Column(name = "DESCRIPTION", length = 250)
	public String getDescription() {
		return description;
	}

	/**
	 * Imposta description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Legge next fire time.
	 *
	 * @return the next fire time
	 */
	@Column(name = "NEXT_FIRE_TIME", length = 13)
	public Long getNextFireTime() {
		return nextFireTime;
	}

	/**
	 * Imposta next fire time.
	 *
	 * @param nextFireTime the new next fire time
	 */
	public void setNextFireTime(Long nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	/**
	 * Legge prev fire time.
	 *
	 * @return the prev fire time
	 */
	@Column(name = "PREV_FIRE_TIME", length = 13)
	public Long getPrevFireTime() {
		return prevFireTime;
	}

	/**
	 * Imposta prev fire time.
	 *
	 * @param prevFireTime the new prev fire time
	 */
	public void setPrevFireTime(Long prevFireTime) {
		this.prevFireTime = prevFireTime;
	}

	/**
	 * Legge priority.
	 *
	 * @return the priority
	 */
	@Column(name = "PRIORITY", length = 13)
	public Long getPriority() {
		return priority;
	}

	/**
	 * Imposta priority.
	 *
	 * @param priority the new priority
	 */
	public void setPriority(Long priority) {
		this.priority = priority;
	}

	/**
	 * Legge trigger state.
	 *
	 * @return the trigger state
	 */
	@Column(name = "TRIGGER_STATE", length = 16)
	public String getTriggerState() {
		return triggerState;
	}

	/**
	 * Imposta trigger state.
	 *
	 * @param triggerState the new trigger state
	 */
	public void setTriggerState(String triggerState) {
		this.triggerState = triggerState;
	}

	/**
	 * Legge trigger type.
	 *
	 * @return the trigger type
	 */
	@Column(name = "TRIGGER_TYPE", length = 8)
	public String getTriggerType() {
		return triggerType;
	}

	/**
	 * Imposta trigger type.
	 *
	 * @param triggerType the new trigger type
	 */
	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

	/**
	 * Legge start time.
	 *
	 * @return the start time
	 */
	@Column(name = "START_TIME", length = 13)
	public Long getStartTime() {
		return startTime;
	}

	/**
	 * Imposta start time.
	 *
	 * @param startTime the new start time
	 */
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	/**
	 * Legge end time.
	 *
	 * @return the end time
	 */
	@Column(name = "END_TIME", length = 13)
	public Long getEndTime() {
		return endTime;
	}

	/**
	 * Imposta end time.
	 *
	 * @param endTime the new end time
	 */
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	/**
	 * Legge calendar name.
	 *
	 * @return the calendar name
	 */
	@Column(name = "CALENDAR_NAME", length = 200)
	public String getCalendarName() {
		return calendarName;
	}

	/**
	 * Imposta calendar name.
	 *
	 * @param calendarName the new calendar name
	 */
	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}

	/**
	 * Legge misfire instr.
	 *
	 * @return the misfire instr
	 */
	@Column(name = "MISFIRE_INSTR")
	public Integer getMisfireInstr() {
		return misfireInstr;
	}

	/**
	 * Imposta misfire instr.
	 *
	 * @param misfireInstr the new misfire instr
	 */
	public void setMisfireInstr(Integer misfireInstr) {
		this.misfireInstr = misfireInstr;
	}

	/**
	 * Legge job data.
	 *
	 * @return the job data
	 */
	@Column(name = "JOB_DATA", length = 4000)
	public Blob getJobData() {
		return jobData;
	}

	/**
	 * Imposta job data.
	 *
	 * @param jobData the new job data
	 */
	public void setJobData(Blob jobData) {
		this.jobData = jobData;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("comp_id", getComp_id()).toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof BdlQrtzTrigger))
			return false;
		BdlQrtzTrigger castOther = (BdlQrtzTrigger) other;
		return new EqualsBuilder().append(this.getComp_id(), castOther.getComp_id()).isEquals();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder().append(getComp_id()).toHashCode();
	}

}