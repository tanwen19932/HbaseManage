package test;

public class T2STest {

	public static void main(String[] args) {
		Thread thread1=new Thread(() -> {
            int i=1;
            while(true){
                System.out.println("thread1");
                try{
                    int a=i/0;
                    System.out.println(a);
                }catch(Exception e){
                    e.printStackTrace();
                }
                i++;
            }
        });
		thread1.start();
	}
}
