package other.hbaseUniversalDao;


public interface I_Inserter {
	public abstract <T extends Object> void insert(T obj);
}
