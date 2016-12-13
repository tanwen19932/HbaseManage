package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Watched extends Observable implements Runnable{

	@Override
	public void run() {
		i=0;
		while(true){
			while(list.isEmpty()){
				notifyObservers();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(list.remove(i++));;
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private List<String> list=new ArrayList<String>();
	private int i;
	
	public List<String> getList(){
		return list;
	}
	
}
