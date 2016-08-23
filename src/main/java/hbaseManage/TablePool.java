package hbaseManage;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author TW
 * @date TW on 2016/8/23.
 */
public class TablePool {
   static LoadingCache<String, TableManager> tableCache =
            CacheBuilder.newBuilder().concurrencyLevel(1)
            .maximumSize(20)
            .expireAfterAccess(30, TimeUnit.MINUTES)
            .build(new CacheLoader<String, TableManager>() {
                @Override
                public TableManager load(String tableName)
                        throws Exception {
                    return new TableManager(tableName);
                }
            });

    public static TableManager get(String tableName){
        try {
            return tableCache.get(tableName);
        } catch (ExecutionException e) {
            return new TableManager(tableName);
        }
    }

    private TablePool() {
    }
}
