package com.idega.jbpm.flow;

import com.idega.jbpm.view.ViewSubmission;

public interface ProcessW {

	public <P> P getProcessDefinition();

	public String getName();

	/**
	 * Starts process
	 * @param viewSubmission
	 * @return process instance ID
	 */
	public Long startProcess(ViewSubmission viewSubmission);

	public abstract void setProcessDefinitionId(Long processDefinitionId);

	public abstract Long getProcessDefinitionId();
}