package edu.buaa.nlp.util;

import net.sf.ezmorph.MorphException;
import net.sf.ezmorph.object.AbstractObjectMorpher;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.JSONUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonUtil {

	/** 
	 * 将Bean中的Timestamp转换为json中的日期字符�?
	 */  
	static class DateJsonValueProcessor implements JsonValueProcessor {
		public static final String Default_DATE_PATTERN = "yyyy-MM-dd";

		private DateFormat dateFormat;

		public DateJsonValueProcessor(String datePattern) {
			try {
				dateFormat = new SimpleDateFormat(datePattern);

			} catch (Exception e) {
				dateFormat = new SimpleDateFormat(Default_DATE_PATTERN);
			}
		}

		private Object process(Object value) {
			return dateFormat.format((Date) value);
		}

		@Override
		public Object processArrayValue(Object arg0, JsonConfig arg1) {
			return process(arg0);
		}

		@Override
		public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
			return process(arg1);
		}
	}

	/** 
	 * 将json串中的日期字符串转换为bean中的Timestamp 
	 */  
	static class TimestampMorpher extends AbstractObjectMorpher {
		private String[] formats;

		public TimestampMorpher(String[] formats) {
			this.formats = formats;
		}

		@Override
		public Object morph(Object value) {
			if (value == null) {
				return null;
			}
			if (Timestamp.class.isAssignableFrom(value.getClass())) {
				return value;
			}
			if (!supports(value.getClass())) {
				throw new MorphException(value.getClass() + " 是不支持的类");
			}
			String strValue = (String) value;
			SimpleDateFormat dateParser = null;
			for (int i = 0; i < formats.length; i++) {
				if (null == dateParser) {
					dateParser = new SimpleDateFormat(formats[i]);
				} else {
					dateParser.applyPattern(formats[i]);
				}
				try {
					return new Timestamp(dateParser.parse(strValue.toLowerCase()).getTime());
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		public Class morphsTo() {
			return Timestamp.class;
		}

		@Override
		public boolean supports(Class clazz) {
			return String.class.isAssignableFrom(clazz);
		}
	}
	
	//将Timestamp转换成Json格式
	public static JsonConfig toJsonConfig(){
		JsonConfig config=new JsonConfig();
		config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		return config;
	}
	
	public static JsonConfig toGeneralConfig(){
		JsonConfig config=new JsonConfig();
		String[] formats={"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"};  
		JSONUtils.getMorpherRegistry().registerMorpher(new TimestampMorpher(formats));
		return config;
	}
}
