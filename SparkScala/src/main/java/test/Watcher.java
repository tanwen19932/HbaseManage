package test;

import java.util.Observable;
import java.util.Observer;

public class Watcher implements Observer{

	private Observable observable;
	
	public Watcher(Observable observable) {
		this.observable=observable;
		observable.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println(arg);
		for(int i=0; i<10; i++){
			((Watched)o).getList().add(""+Math.random());
		}
	}

	
	public static void main(String[] args) {
		Watched watched=new Watched();
		Watcher watcher=new Watcher(watched);
		new Thread(watched).start();
	}
}
