package tw.utils;

import news.mediaSrc.util.MediaSrcUtil;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MediaSrcUtilTest {
	
	@Test
	public void getMediaSrc(){
		try{
			Map aMap = MediaSrcUtil.getInstance();
			aMap.get("");
			assertEquals(true,true);
		}catch (Exception e){
			assertEquals(true,false);
		}
		
	}

}
