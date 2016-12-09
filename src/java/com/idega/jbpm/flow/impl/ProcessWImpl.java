package com.idega.jbpm.flow.impl;

import java.util.logging.Level;

import org.drools.core.common.InternalKnowledgeRuntime;
import org.kie.api.definition.process.Process;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.core.business.DefaultSpringBean;
import com.idega.jbpm.flow.ProcessW;
import com.idega.jbpm.view.ViewSubmission;

@Service("defaultPD2W")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ProcessWImpl extends DefaultSpringBean implements ProcessW {

	private Long processDefinitionId;

	private transient InternalKnowledgeRuntime kruntime;

	private transient Process process;

	@Override
	public <P> P getProcessDefinition() {
		if (process == null) {
			Long id = getProcessDefinitionId();
			if (id == null) {
				getLogger().warning("ID is unknown");
				return null;
			}

			if (kruntime == null) {
				getLogger().warning("Process definition " + id + " is disconnected.");
				return null;
			}

			try {
				process = kruntime.getKieBase().getProcess(String.valueOf(id));
			} catch (Exception e) {
				getLogger().log(Level.WARNING, "Error getting process by ID: " + id, e);
			}
		}

		if (process == null) {
			return null;
		}

		@SuppressWarnings("unchecked")
		P process = (P) this.process;
		return process;
	}

	@Override
	public String getName() {
		Process process = getProcessDefinition();
		return process == null ? null : process.getName();
	}

	@Override
	public Long startProcess(ViewSubmission viewSubmission) {
		Long id = null;
		try {
			id = viewSubmission.getProcessDefinitionId();
			//	TODO: implement

		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Error starting instance for proc. def. with ID: " + id, e);
		}
		return null;
	}

	@Override
	public void setProcessDefinitionId(Long processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	@Override
	public Long getProcessDefinitionId() {
		return processDefinitionId;
	}

}