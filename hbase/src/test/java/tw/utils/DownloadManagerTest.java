package tw.utils;

import org.junit.Test;

import java.io.IOException;

/**
 * @author TW
 * @date TW on 2016/9/2.
 */
public class DownloadManagerTest {

    @Test
    public void testDoDownload() {
        String filePath = "E:/";
        String taskName = "2016_08_22_1149.zip";
        String url = "http://195.23.58.154/diogo/QuaForce/08/";
        DownloadManager downloadManager = new DownloadManager();
        try {
            downloadManager.downloadBy1Thread(
                    "http://www.pc6.com/softview/SoftView_25196.html#download",
                    //url+taskName,
                    filePath+taskName);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}