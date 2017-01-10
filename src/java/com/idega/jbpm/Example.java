/**
 * @(#)Example.java    1.0.0 10:09:38
 *
 * Idega Software hf. Source Code Licence Agreement x
 *
 * This agreement, made this 10th of February 2006 by and between 
 * Idega Software hf., a business formed and operating under laws 
 * of Iceland, having its principal place of business in Reykjavik, 
 * Iceland, hereinafter after referred to as "Manufacturer" and Agura 
 * IT hereinafter referred to as "Licensee".
 * 1.  License Grant: Upon completion of this agreement, the source 
 *     code that may be made available according to the documentation for 
 *     a particular software product (Software) from Manufacturer 
 *     (Source Code) shall be provided to Licensee, provided that 
 *     (1) funds have been received for payment of the License for Software and 
 *     (2) the appropriate License has been purchased as stated in the 
 *     documentation for Software. As used in this License Agreement, 
 *     Licensee shall also mean the individual using or installing 
 *     the source code together with any individual or entity, including 
 *     but not limited to your employer, on whose behalf you are acting 
 *     in using or installing the Source Code. By completing this agreement, 
 *     Licensee agrees to be bound by the terms and conditions of this Source 
 *     Code License Agreement. This Source Code License Agreement shall 
 *     be an extension of the Software License Agreement for the associated 
 *     product. No additional amendment or modification shall be made 
 *     to this Agreement except in writing signed by Licensee and 
 *     Manufacturer. This Agreement is effective indefinitely and once
 *     completed, cannot be terminated. Manufacturer hereby grants to 
 *     Licensee a non-transferable, worldwide license during the term of 
 *     this Agreement to use the Source Code for the associated product 
 *     purchased. In the event the Software License Agreement to the 
 *     associated product is terminated; (1) Licensee's rights to use 
 *     the Source Code are revoked and (2) Licensee shall destroy all 
 *     copies of the Source Code including any Source Code used in 
 *     Licensee's applications.
 * 2.  License Limitations
 *     2.1 Licensee may not resell, rent, lease or distribute the 
 *         Source Code alone, it shall only be distributed as a 
 *         compiled component of an application.
 *     2.2 Licensee shall protect and keep secure all Source Code 
 *         provided by this this Source Code License Agreement. 
 *         All Source Code provided by this Agreement that is used 
 *         with an application that is distributed or accessible outside
 *         Licensee's organization (including use from the Internet), 
 *         must be protected to the extent that it cannot be easily 
 *         extracted or decompiled.
 *     2.3 The Licensee shall not resell, rent, lease or distribute 
 *         the products created from the Source Code in any way that 
 *         would compete with Idega Software.
 *     2.4 Manufacturer's copyright notices may not be removed from 
 *         the Source Code.
 *     2.5 All modifications on the source code by Licencee must 
 *         be submitted to or provided to Manufacturer.
 * 3.  Copyright: Manufacturer's source code is copyrighted and contains 
 *     proprietary information. Licensee shall not distribute or 
 *     reveal the Source Code to anyone other than the software 
 *     developers of Licensee's organization. Licensee may be held 
 *     legally responsible for any infringement of intellectual property 
 *     rights that is caused or encouraged by Licensee's failure to abide 
 *     by the terms of this Agreement. Licensee may make copies of the 
 *     Source Code provided the copyright and trademark notices are 
 *     reproduced in their entirety on the copy. Manufacturer reserves 
 *     all rights not specifically granted to Licensee.
 *
 * 4.  Warranty & Risks: Although efforts have been made to assure that the 
 *     Source Code is correct, reliable, date compliant, and technically 
 *     accurate, the Source Code is licensed to Licensee as is and without 
 *     warranties as to performance of merchantability, fitness for a 
 *     particular purpose or use, or any other warranties whether 
 *     expressed or implied. Licensee's organization and all users 
 *     of the source code assume all risks when using it. The manufacturers, 
 *     distributors and resellers of the Source Code shall not be liable 
 *     for any consequential, incidental, punitive or special damages 
 *     arising out of the use of or inability to use the source code or 
 *     the provision of or failure to provide support services, even if we 
 *     have been advised of the possibility of such damages. In any case, 
 *     the entire liability under any provision of this agreement shall be 
 *     limited to the greater of the amount actually paid by Licensee for the 
 *     Software or 5.00 USD. No returns will be provided for the associated 
 *     License that was purchased to become eligible to receive the Source 
 *     Code after Licensee receives the source code. 
 */
package com.idega.jbpm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;

import org.drools.core.audit.WorkingMemoryInMemoryLogger;
import org.jbpm.persistence.settings.JpaSettings;
import org.jbpm.process.instance.event.DefaultSignalManagerFactory;
import org.jbpm.process.instance.impl.DefaultProcessInstanceManagerFactory;
import org.jbpm.runtime.manager.impl.DefaultRegisterableItemsFactory;
import org.jbpm.runtime.manager.impl.SimpleRegisterableItemsFactory;
import org.jbpm.services.task.identity.JBossUserGroupCallbackImpl;
import org.kie.api.event.process.ProcessEventListener;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.Context;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.api.runtime.manager.audit.AuditService;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.task.TaskLifeCycleEventListener;
import org.kie.api.task.TaskService;
import org.kie.api.task.UserGroupCallback;
import org.kie.api.task.model.TaskSummary;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;

/**
 * <p>Temporary class to test jBPM</p>
 *
 * @version 1.0.0 2017-01-09
 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
 */
public class Example {

	private boolean setupDataSource = false;
    private boolean sessionPersistence = false;

    private EntityManagerFactory emf;

    private RuntimeManagerFactory managerFactory = RuntimeManagerFactory.Factory.get();
    private RuntimeManager manager;

    private AuditService logService;
    private WorkingMemoryInMemoryLogger inMemoryLogger;

    private UserGroupCallback userGroupCallback = new JBossUserGroupCallbackImpl("usergroups.properties");

    private Set<RuntimeEngine> activeEngines = new HashSet<RuntimeEngine>();

    private Map<String, WorkItemHandler> customHandlers = new HashMap<String, WorkItemHandler>();
    private List<ProcessEventListener> customProcessListeners = new ArrayList<ProcessEventListener>();
    private List<AgendaEventListener> customAgendaListeners = new ArrayList<AgendaEventListener>();
    private List<TaskLifeCycleEventListener> customTaskListeners = new ArrayList<TaskLifeCycleEventListener>();
    private Map<String, Object> customEnvironmentEntries = new HashMap<String, Object>();

    /**
     * Creates default configuration of <code>RuntimeManager</code> with given <code>strategy</code> and all
     * <code>processes</code> being added to knowledge base.
     * <br/>
     * There should be only one <code>RuntimeManager</code> created during single test.
     * @param strategy - selected strategy of those that are supported
     * @param identifier - identifies the runtime manager
     * @param process - processes that shall be added to knowledge base
     * @return new instance of RuntimeManager
     */
    private RuntimeManager createRuntime(String identifier, String... process) {
        Map<String, ResourceType> resources = new HashMap<String, ResourceType>();
        for (String p : process) {
            resources.put(p, ResourceType.BPMN2);
        }
        return createRuntimeManager(resources, identifier);
    }

    /**
     * Creates default configuration of <code>RuntimeManager</code> with given <code>strategy</code> and all
     * <code>resources</code> being added to knowledge base.
     * <br/>
     * There should be only one <code>RuntimeManager</code> created during single test.
     * @param strategy - selected strategy of those that are supported
     * @param resources - resources that shall be added to knowledge base
     * @param identifier - identifies the runtime manager
     * @return new instance of RuntimeManager
     */
    private RuntimeManager createRuntimeManager(Map<String, ResourceType> resources, String identifier) {
        if (manager != null) {
            throw new IllegalStateException("There is already one RuntimeManager active");
        }

        RuntimeEnvironmentBuilder builder = null;
        if (!setupDataSource){
            builder = RuntimeEnvironmentBuilder.Factory.get()
        			.newEmptyBuilder()
            .addConfiguration("drools.processSignalManagerFactory", DefaultSignalManagerFactory.class.getName())
            .addConfiguration("drools.processInstanceManagerFactory", DefaultProcessInstanceManagerFactory.class.getName())            
            .registerableItemsFactory(new SimpleRegisterableItemsFactory() {

				@Override
				public Map<String, WorkItemHandler> getWorkItemHandlers(RuntimeEngine runtime) {
					Map<String, WorkItemHandler> handlers = new HashMap<String, WorkItemHandler>();
					handlers.putAll(super.getWorkItemHandlers(runtime));
					handlers.putAll(customHandlers);
					return handlers;
				}

				@Override
				public List<ProcessEventListener> getProcessEventListeners(RuntimeEngine runtime) {
					List<ProcessEventListener> listeners = super.getProcessEventListeners(runtime);
					listeners.addAll(customProcessListeners);
					return listeners;
				}

				@Override
				public List<AgendaEventListener> getAgendaEventListeners( RuntimeEngine runtime) {
					List<AgendaEventListener> listeners = super.getAgendaEventListeners(runtime);
					listeners.addAll(customAgendaListeners);
					return listeners;
				}

				@Override
				public List<TaskLifeCycleEventListener> getTaskListeners() {
					List<TaskLifeCycleEventListener> listeners = super.getTaskListeners();
					listeners.addAll(customTaskListeners);
					return listeners;
				}

	        });

        } else if (sessionPersistence) {
            builder = RuntimeEnvironmentBuilder.Factory.get()
        			.newDefaultBuilder()
            .entityManagerFactory(emf)
            .registerableItemsFactory(new DefaultRegisterableItemsFactory() {

				@Override
				public Map<String, WorkItemHandler> getWorkItemHandlers(RuntimeEngine runtime) {
					Map<String, WorkItemHandler> handlers = new HashMap<String, WorkItemHandler>();
					handlers.putAll(super.getWorkItemHandlers(runtime));
					handlers.putAll(customHandlers);
					return handlers;
				}

				@Override
				public List<ProcessEventListener> getProcessEventListeners(RuntimeEngine runtime) {
					List<ProcessEventListener> listeners = super.getProcessEventListeners(runtime);
					listeners.addAll(customProcessListeners);
					return listeners;
				}

				@Override
				public List<AgendaEventListener> getAgendaEventListeners( RuntimeEngine runtime) {
					List<AgendaEventListener> listeners = super.getAgendaEventListeners(runtime);
					listeners.addAll(customAgendaListeners);
					return listeners;
				}

				@Override
				public List<TaskLifeCycleEventListener> getTaskListeners() {
					List<TaskLifeCycleEventListener> listeners = super.getTaskListeners();
					listeners.addAll(customTaskListeners);
					return listeners;
				}

	        });
        } else {
            builder = RuntimeEnvironmentBuilder.Factory.get()
        			.newDefaultInMemoryBuilder()
        			.entityManagerFactory(emf)
        			.registerableItemsFactory(new DefaultRegisterableItemsFactory() {

				@Override
				public Map<String, WorkItemHandler> getWorkItemHandlers(RuntimeEngine runtime) {
					Map<String, WorkItemHandler> handlers = new HashMap<String, WorkItemHandler>();
					handlers.putAll(super.getWorkItemHandlers(runtime));
					handlers.putAll(customHandlers);
					return handlers;
				}

				@Override
				public List<ProcessEventListener> getProcessEventListeners(RuntimeEngine runtime) {
					List<ProcessEventListener> listeners = super.getProcessEventListeners(runtime);
					listeners.addAll(customProcessListeners);
					return listeners;
				}

				@Override
				public List<AgendaEventListener> getAgendaEventListeners( RuntimeEngine runtime) {
					List<AgendaEventListener> listeners = super.getAgendaEventListeners(runtime);
					listeners.addAll(customAgendaListeners);
					return listeners;
				}

				@Override
				public List<TaskLifeCycleEventListener> getTaskListeners() {
					List<TaskLifeCycleEventListener> listeners = super.getTaskListeners();
					listeners.addAll(customTaskListeners);
					return listeners;
				}

	        });
        }
        builder.userGroupCallback(userGroupCallback);
        
        for (Entry<String, Object> envEntry : customEnvironmentEntries.entrySet()) {
        	builder.addEnvironmentEntry(envEntry.getKey(), envEntry.getValue());
        }

        for (Map.Entry<String, ResourceType> entry : resources.entrySet()) {
            builder.addAsset(ResourceFactory.newClassPathResource(entry.getKey()), entry.getValue());
        }

        return createRuntimeManager(resources, builder.get(), identifier);
    }

    /**
     * The lowest level of creation of <code>RuntimeManager</code> that expects to get <code>RuntimeEnvironment</code>
     * to be given as argument. It does not assume any particular configuration as it's considered manual creation
     * that allows to configure every single piece of <code>RuntimeManager</code>. <br/>
     * Use this only when you know what you do!
     * @param strategy - selected strategy of those that are supported
     * @param resources - resources that shall be added to knowledge base
     * @param environment - runtime environment used for <code>RuntimeManager</code> creation
     * @param identifier - identifies the runtime manager
     * @return new instance of RuntimeManager
     */
    private RuntimeManager createRuntimeManager(Map<String, ResourceType> resources, RuntimeEnvironment environment, String identifier) {
        if (manager != null) {
            throw new IllegalStateException("There is already one RuntimeManager active");
        }
        try {
            if (identifier == null) {
                manager = managerFactory.newSingletonRuntimeManager(environment);
            } else {
                manager = managerFactory.newSingletonRuntimeManager(environment, identifier);
            }
    
            return manager;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Disposes currently active (in scope of a test) <code>RuntimeManager</code> together with all
     * active <code>RuntimeEngine</code>'s that were created (in scope of a test). Usual use case is
     * to simulate system shutdown.
     */
    private void disposeRuntimeManager() {
        if (!activeEngines.isEmpty()) {
            for (RuntimeEngine engine : activeEngines) {
            	try {
            		manager.disposeRuntimeEngine(engine);
            	} catch (Exception e) {
            		Logger.getLogger(getClass().getName()).log(Level.WARNING, "Exception during dipose of runtime engine, might be already disposed - {}", e);
            	}
            }
            activeEngines.clear();
        }
        if (manager != null) {
            manager.close();
            manager = null;
        }
    }

    /**
     * Returns new <code>RuntimeEngine</code> built from the manager of this test case. Common use case is to maintain
     * same session for process instance and thus <code>ProcessInstanceIdContext</code> shall be used.
     * @param context - instance of the context that shall be used to create <code>RuntimeManager</code>
     * @return new RuntimeEngine instance
     */
    private RuntimeEngine getRuntimeEngine(Context<?> context) {
        if (manager == null) {
            throw new IllegalStateException("RuntimeManager is not initialized, did you forgot to create it?");
        }

        RuntimeEngine runtimeEngine = manager.getRuntimeEngine(context);
        activeEngines.add(runtimeEngine);
        if (sessionPersistence) {
            logService = runtimeEngine.getAuditService();

        } else {
            inMemoryLogger = new WorkingMemoryInMemoryLogger((StatefulKnowledgeSession) runtimeEngine.getKieSession());
        }

        return runtimeEngine;
    }
	
	public void test() {
		JpaSettings settings = JpaSettings.get();
		settings.setDataSourceJndiName("java:comp/env/jdbc/DefaultDS");
		
		RuntimeManager manager = createRuntime(null, "sample.bpmn");
		RuntimeEngine engine = getRuntimeEngine(null);
		KieSession ksession = engine.getKieSession();
		TaskService taskService = engine.getTaskService();
		
		ProcessInstance processInstance = ksession.startProcess("com.sample.bpmn.hello");
			
		// let john execute Task 1
		List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
		TaskSummary task = list.get(0);
		System.out.println("John is executing task " + task.getName());
		taskService.start(task.getId(), "john");
		taskService.complete(task.getId(), "john", null);
			
		// let mary execute Task 2
		list = taskService.getTasksAssignedAsPotentialOwner("mary", "en-UK");
		task = list.get(0);
		System.out.println("Mary is executing task " + task.getName());
		taskService.start(task.getId(), "mary");
		taskService.complete(task.getId(), "mary", null);
			
		manager.disposeRuntimeEngine(engine);
		manager.close();
	}
}
