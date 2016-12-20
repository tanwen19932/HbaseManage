package handler;

public interface Handler<T>  {
	/**
	 * @param param
	 * @return is handler successful?
	 */
	boolean handle(T param)
			throws Exception;
}
