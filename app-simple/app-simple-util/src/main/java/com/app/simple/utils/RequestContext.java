/*
 * Created on Oct 26, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.app.simple.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;





/**
 * @author chris
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RequestContext  {
	private static final Log log = LogFactory.getLog(RequestContext.class);
	private static ThreadLocal requestContext  = null;
	private ApplicationContext springContext  = null;
	private Map requestBeans  = null;
	
	/**
	 * 
	 */
	private RequestContext() {
		super();
		// instantiate new objects in request context
		init();
	}
	public static synchronized RequestContext getCurrentContext() {
		if (getRequestContext().get() == null) {
			RequestContext aContext = new RequestContext();
			requestContext.set(aContext);
		}
		return (RequestContext) getRequestContext().get();
	}

	/**
	 * @return Returns the requestContext.
	 */
	private static ThreadLocal getRequestContext() {
		if (requestContext == null) {
			requestContext = new ThreadLocal();
		}
		return requestContext;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	protected void finalize() throws Throwable {
	    try {
	        release();
	    } finally {
	        super.finalize();
	    }
	}
	/**
	 * 
	 */
	private void init() {
		
		if (log.isDebugEnabled()) {
            log.debug("initializing Request context...");
        }
		requestBeans = new HashMap();
		
		if (log.isDebugEnabled()) {
           log.debug("Request context successfully initialized !!..");
       }
        
	}
	/**
	 * 
	 */
	public void release() {
		
		springContext  = null;
		requestBeans   = null;
		requestContext = null;
	}
	/**
	 * @return Returns the requestBeans.
	 */
	public Map getRequestBeans() {
		return requestBeans;
	}
	/**
	 * @return Returns the springContext.
	 */
	public ApplicationContext getSpringContext() {
		return springContext;
	}
	public static ApplicationContext getCtx() {
		return getCurrentContext().getSpringContext();
	}
	public static Map getBeans() {
		return getCurrentContext().getRequestBeans();
	}
	/**
	 * @param springContext The springContext to set.
	 */
	public void setSpringContext(ApplicationContext springContext) {
		this.springContext = springContext;
	}
}
