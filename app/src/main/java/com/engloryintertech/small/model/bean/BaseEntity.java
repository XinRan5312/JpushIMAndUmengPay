package com.engloryintertech.small.model.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/5 0005.
 */

public abstract class BaseEntity <T>{

    protected abstract void save();
    protected abstract void update();
    protected abstract T findFirst();
    protected abstract ArrayList<? extends BaseEntity> findList(int queryType);

}
