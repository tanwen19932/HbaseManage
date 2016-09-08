package tw.utils;

import org.junit.Test;

/**
 * @author TW
 * @date TW on 2016/9/8.
 */
public class DateUtilTest {

    @Test
    public void testTryParse()
            throws Exception {
            for(int i=0 ;i< 1000 ;i++){
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        System.out.println(DateUtil.tryParse("于 2015年12月11日".trim()));
                    }
                });
                thread.start();

            }
    }
}