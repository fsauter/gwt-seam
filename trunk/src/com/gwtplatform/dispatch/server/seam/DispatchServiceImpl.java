package com.gwtplatform.dispatch.server.seam;

import java.util.logging.Logger;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.remoting.WebRemote;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwtplatform.dispatch.client.DispatchService;
import com.gwtplatform.dispatch.server.AbstractDispatchServiceImpl;
import com.gwtplatform.dispatch.server.Dispatch;
import com.gwtplatform.dispatch.server.RequestProvider;

import com.gwtplatform.dispatch.server.seam.request.DefaultRequestProvider;
import com.gwtplatform.dispatch.shared.Action;
import com.gwtplatform.dispatch.shared.ActionException;
import com.gwtplatform.dispatch.shared.Result;
import com.gwtplatform.dispatch.shared.ServiceException;

@Scope(ScopeType.APPLICATION)
@Name("com.gwtplatform.dispatch.client.DispatchService")
public class DispatchServiceImpl extends RemoteServiceServlet implements DispatchService {

	private static final long serialVersionUID = -708585059678689064L;
	protected static final Logger logger = Logger.getLogger(DispatchServiceImpl.class.getName());
	protected DispatchService dispatchService;
	
	@In protected DispatchImpl gwtDispatchImpl;
	@In(create = true) private DispatchConfiguration gwtpDispatchConfiguration;
	@In protected HandlerRegistry gwtpActionHandlerValidatorRegistry;
	
	@Create
	public void create() {
		gwtpDispatchConfiguration.configureHandlers(gwtpActionHandlerValidatorRegistry);
		dispatchService = new NonAbstractDispatchServiceImpl(logger, gwtDispatchImpl, new DefaultRequestProvider());
	}

	@Override
	@WebRemote
	public Result execute(String cookieSentByRPC, Action<?> action) throws ActionException, ServiceException {
		return dispatchService.execute(cookieSentByRPC, action);
	}

	@Override
	@WebRemote
	public void undo(String cookieSentByRPC, Action<Result> action, Result result) throws ActionException, ServiceException {
		dispatchService.undo(cookieSentByRPC, action, result);
	}
	
	
	private class NonAbstractDispatchServiceImpl extends AbstractDispatchServiceImpl {

		private static final long serialVersionUID = 3471758240308402503L;

		protected NonAbstractDispatchServiceImpl(Logger logger, Dispatch dispatch, RequestProvider requestProvider) {
			super(logger, dispatch, requestProvider);
		}
	}
}
