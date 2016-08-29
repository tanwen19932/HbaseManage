package news.cisionNews.hbase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import news.NewsDaoRT;

public class DelCisionNews {
	static final ExecutorService executorService = Executors.newFixedThreadPool(20);


	public static void main(String[] args) throws Exception {
		NewsDaoRT newsDao = new NewsDaoRT(false) {
			
			
			class Scanner implements Callable<List>{
				Scan scan;
				public Scanner( Scan scan) {
					this.scan = scan;
				}
				@Override
				public List call() throws Exception {
					// TODO Auto-generated method stub
					List<Delete> rowkeyList = new ArrayList<>(100);
					Table table = wTableLog[random.nextInt(tableN)];
					ResultScanner rs = table.getScanner(scan);
					int i =0;
					for (Result r : rs) {
						Delete delete = new Delete(r.getRow());
						rowkeyList.add( delete );
						i++;
						if(i==100){
							table.delete(rowkeyList);
							System.out.println("删除成功！100条"  );
							i =0;
							rowkeyList.clear();
						}

					}
					rs.close();
					
					table.delete(rowkeyList);
					System.out.println("删除 ——————" + rowkeyList.size());
					return null;
				}
				
			}
			@Override
			public void delBetween(Date beginDate, Date endDate) {
				// TODO Auto-generated method stub
				Map<String, Integer> sourceCountTemp = new ConcurrentHashMap<>();
				
				List<Future> futures = new ArrayList<>();
				try {
					long beginMills = System.currentTimeMillis();
					for (int i = 0; i < 100; i++) {
						String startF = null;
						if (beginDate == null) {
							startF = String.format("%02d", i) + (endDate.getTime() - 24 * 60 * 60 * 1000);
						} else {
							startF = String.format("%02d", i) + beginDate.getTime();
						}
						String endF = String.format("%02d", i) + endDate.getTime();
						
						Filter filter = new SingleColumnValueFilter(
								Bytes.toBytes("I"), 
								Bytes.toBytes("comeFrom"), CompareOp.EQUAL, Bytes.toBytes("Cision"));

						byte[] startRow = Bytes.padTail(Bytes.toBytes(startF), 20);
						byte[] endRow = Bytes.padTail(Bytes.toBytes(endF), 20);
						Scan scan = new Scan(startRow, endRow);
						scan.setFilter(filter);
						futures.add( executorService.submit(new Scanner( scan )) );
						
					}
					
					for (int i = 0; i < futures.size(); i++) {
						futures.get(i).get();
					}
					long hbaseTime=(System.currentTimeMillis() - beginMills) / 1000;
					System.out.println("Hbase time spend : " + hbaseTime);

					System.out.println("Count time spend : " + (System.currentTimeMillis() - beginMills) / 1000);
//					saveToDatabase(sourceCount, beginDate, 1);
//					saveToDatabase(totalCount, beginDate, 0);
					System.out.println("Save time spend : " + (System.currentTimeMillis() - beginMills) / 1000);

				} catch (Exception e) {
					LOG.error(e);
					e.printStackTrace();
				}
			}
		};

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdfD = new SimpleDateFormat("dd");
		SimpleDateFormat sdfSqlDate = new SimpleDateFormat("yyyy-MM-dd");
		// 初始化task；
		// BufferedReader bf = new BufferedReader(new FileReader(new
		// File("E:/insert/"+"count"+sdf.format(lastTaskDate)+".txt")));
		Date beginDate = dateFormat.parse("2016-07-26 15:00:00");
		Date endDate = dateFormat.parse("2016-07-26 21:00:00");
		
				
		newsDao.delBetween(beginDate, endDate );

	}
}
