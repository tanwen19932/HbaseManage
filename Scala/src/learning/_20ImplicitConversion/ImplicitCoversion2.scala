package learning._20ImplicitConversion

/**
	* @author TW
	* @date TW on 2016/12/9.
	*/
//主要为 柯里化 作为隐式参数
class ImplicitCoversion2 {
	def smaller[T](a:T,b:T)(implicit order:T=> Ordered[T]): T ={
		if ( order(a) >  b) b else a
	}
}
//上下文界定： 柯里化函数
class pair[T:Ordering](val a:T ,val b:T){
	def smaller(implicit ord:Ordering[T])={
		if(ord.compare(a,b)>0) b else a
	}
}