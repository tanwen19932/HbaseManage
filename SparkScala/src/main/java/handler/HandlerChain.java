package handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public  abstract class HandlerChain<T> implements Handler<T> {
	protected static final Logger LOG = LoggerFactory.getLogger( HandlerChain.class );
	protected List<Handler<T>> handlers = null;
	protected ErrorHandler<T>  errorHandler = null;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HandlerChain() {
		init();
		if(handlers == null) {
			LOG.info("未初始化handlers  自动初始化");
			handlers= new LinkedList<>();
		}
		if(errorHandler == null) {
			LOG.info("未初始化errorHandler  自动初始化");
			errorHandler= new ErrorHandler() {
				Logger LOG = LoggerFactory.getLogger(ErrorHandler.class);
				@Override
				public void errorHandle(String errorMessage, Object t) {
					LOG.error("处理错误：{}", errorMessage);
				}
				@Override
				public void successHandle(Object t) {
				}
			};
		}
	}
	
	List<Handler<T>> getHandlers() {
		return handlers;
	}

	protected void setHandlers(List<Handler<T>> handlers) {
		this.handlers = handlers;
	}


	protected void add(Handler<T> handler) {
		handlers.add(handler);
	}

	protected void remove(Handler<T> handler) {
		if(handlers.contains(handler)){
			handlers.remove(handler);
		}
	}
	
	@Override
	public boolean handle(T t) throws Exception {
		return handleByChain(t);
	}
	
	protected boolean handleByChain(T t) {
		for(Handler<T> handler : handlers){
			long s = System.currentTimeMillis();
			try {
				if (!handler.handle( t )) {
					errorHandler.errorHandle( getErrorMessage( handler ), t );
					return false;
				}
			} catch (Exception e) {
				errorHandler.errorHandle( getErrorMessage( handler ), t );
				LOG.error( "", e  );
				return false;
			};
			LOG.debug(handler.getClass().getSimpleName() + " \t\t +++++ 耗时： " +  (System.currentTimeMillis()-s));
		}
		errorHandler.successHandle( t );
		return true;
	}

	protected abstract String getErrorMessage(Handler<T> handler);
	/**
	 * 初始化 ErrorHandler和
	 */
	protected abstract void init(); 
}
