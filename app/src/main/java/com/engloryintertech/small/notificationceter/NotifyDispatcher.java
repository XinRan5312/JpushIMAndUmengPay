package com.engloryintertech.small.notificationceter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class NotifyDispatcher<T, K> {

    private static final String TAG = "NotifyDispatcher";

    private Map<T, List<IDataSourceListener<T, K>>> mDataSourceListenerMap = new ConcurrentHashMap<T, List<IDataSourceListener<T, K>>>();

    public void registerDataListener(T t, IDataSourceListener<T, K> listener) {
        if (listener == null) {
            return;
        }
        List<IDataSourceListener<T, K>> listetenerList = mDataSourceListenerMap
                .get(t);
        if (listetenerList == null) {
            listetenerList = new ArrayList<IDataSourceListener<T, K>>();
//			listetenerList.add(listener);
            listetenerList.add(0, listener);
            mDataSourceListenerMap.put(t, listetenerList);
        } else {
//		    listetenerList.add(listener);
            listetenerList.add(0, listener);
        }
    }

    public void unRegisterDataListener(IDataSourceListener<T, K> listener) {
        Set<Entry<T, List<IDataSourceListener<T, K>>>> sets = mDataSourceListenerMap
                .entrySet();
        for (Entry<T, List<IDataSourceListener<T, K>>> item : sets) {
            List<IDataSourceListener<T, K>> listenerList = item.getValue();
            listenerList.remove(listener);
        }
    }

    /**
     * Notify a data source changed for each registered listener by speical
     * type.
     *
     * @param type
     */
    public void notifyDataChanged(T type, K k) {//
        List<IDataSourceListener<T, K>> lists = mDataSourceListenerMap.get(type);
        if (lists != null) {
//		    Collections.reverse(lists); // 因为通知界面要先通知最上面的界面（后注册的，也即后添加进来的），所以先将数组倒序排序
            for (IDataSourceListener<T, K> listener : lists) {
                if (listener != null) {
                    listener.onChange(type, k);
                }
            }
        }
    }

    public interface IDataSourceListener<T, K> {
        void onChange(T type, K data);
    }
}