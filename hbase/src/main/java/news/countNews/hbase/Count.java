package news.countNews.hbase;

import java.util.Date;

public class Count {
		String RL_SOURCES_ID;
		String RL_MEDIA_ID;
		String RL_LANGUAGE_ID;
		int RL_IS_INCRE = 0;
		String RL_ERROR ;
		Date RL_NODEDATE;
		int RL_COUNT = 0;
		
		public Count() {
			// TODO Auto-generated constructor stub
		}

		public String getRL_ERROR() {
			return RL_ERROR;
		}

		public void setRL_ERROR(String rL_ERROR) {
			RL_ERROR = rL_ERROR;
		}
	
		public String getRL_SOURCES_ID() {
			return RL_SOURCES_ID;
		}

		public void setRL_SOURCES_ID(String rL_SOURCES_ID) {
			RL_SOURCES_ID = rL_SOURCES_ID;
		}

		public String getRL_MEDIA_ID() {
			return RL_MEDIA_ID;
		}

		public void setRL_MEDIA_ID(String rL_MEDIA_ID) {
			RL_MEDIA_ID = rL_MEDIA_ID;
		}

		public String getRL_LANGUAGE_ID() {
			return RL_LANGUAGE_ID;
		}

		public void setRL_LANGUAGE_ID(String rL_LANGUAGE_ID) {
			RL_LANGUAGE_ID = rL_LANGUAGE_ID;
		}

		public Date getRL_NODEDATE() {
			return RL_NODEDATE;
		}

		public void setRL_NODEDATE(Date rL_NODEDATE) {
			RL_NODEDATE = rL_NODEDATE;
		}

		public int getRL_COUNT() {
			return RL_COUNT;
		}

		public void setRL_COUNT(int rL_COUNT) {
			RL_COUNT = rL_COUNT;
		}

		public int getRL_IS_INCRE() {
			return RL_IS_INCRE;
		}

		public void setRL_IS_INCRE(int rL_IS_INCRE) {
			RL_IS_INCRE = rL_IS_INCRE;
		}

	}