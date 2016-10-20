package com.engloryintertech.small.datas;

import java.util.List;

public interface DaoHelperInterface {
	public <T> void addData(T t);
	public void deleteData(long id);
	public <T> T getDataById(long id);
	public List getAllData();
	public boolean hasKey(long id);
	public long getTotalCount();
	public void deleteAll();
}
