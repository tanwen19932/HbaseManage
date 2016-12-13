package handler;

public interface ErrorHandler<T> {
	 void errorHandle(String errorMessage, T t) ;
	 void successHandle(T t);
}
