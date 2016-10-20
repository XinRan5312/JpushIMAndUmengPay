package com.engloryintertech.small.datas;

import com.engloryintertech.small.model.bean.UserBean;
import com.engloryintertech.small.model.dao.UserBeanDao;
import com.engloryintertech.small.tools.Common;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

public class UserDaoHelper implements DaoHelperInterface {

    private static UserDaoHelper instance;
    private UserBeanDao mUserBeanDao;

    private UserDaoHelper() {
        try {
            mUserBeanDao = DatabaseLoader.getDaoSession().getUserBeanDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static UserDaoHelper getInstance() {
        if (instance == null) {
            instance = new UserDaoHelper();
        }
        return instance;
    }

    @Override
    public <T> void addData(T bean) {
        if (mUserBeanDao != null && bean != null) {
            mUserBeanDao.insertOrReplace((UserBean) bean);
        }
    }

    @Override
    public void deleteData(long id) {
        if (mUserBeanDao != null) {
            mUserBeanDao.deleteByKey(id);
        }
    }

    @Override
    public UserBean getDataById(long id) {
        if (mUserBeanDao != null) {
            return mUserBeanDao.load(id);
        }
        return null;
    }

    @Override
    public List getAllData() {
        if (mUserBeanDao != null) {
            return mUserBeanDao.loadAll();
        }
        return null;
    }

    @Override
    public boolean hasKey(long id) {
        if (mUserBeanDao == null) {
            return false;
        }

        QueryBuilder<UserBean> qb = mUserBeanDao.queryBuilder();
        qb.where(UserBeanDao.Properties.Id.eq(id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        if (mUserBeanDao == null) {
            return 0;
        }

        QueryBuilder<UserBean> qb = mUserBeanDao.queryBuilder();
        return qb.buildCount().count();
    }

    @Override
    public void deleteAll() {
        if (mUserBeanDao != null) {
            mUserBeanDao.deleteAll();
        }
    }

    /**
     * 获取用户信息
     */
    public UserBean getUserInfo() {
        List<UserBean> userBeanList = new ArrayList<>();
        if (isExistUser()) {
            userBeanList.addAll(getAllData());
            return userBeanList.get(0);
        }
        return null;
    }

    /**
     * 判断用户是否存在  用于判断是否登录等
     */
    public boolean isExistUser() {
        if (getAllData().size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 获取用户Id  用于请求头等
     */
    public int getUserId() {
        if (!isExistUser()) {
            return 0;
        } else {
            if (Common.isStringNull(getUserInfo().getUserId() + "")) {
                return 0;
            }
        }
        return getUserInfo().getUserId();
    }

    /**
     * 获取UserToken  用于请求头等
     */
    public String getUserToken() {
        if (!isExistUser()) {
            return "";
        } else {
            if (Common.isStringNull(getUserInfo().getUserToken())) {
                return "";
            }
        }
        return getUserInfo().getUserToken();
    }

    public void saveUserBean(UserBean item) {
        UserBean userBean = getUserInfo();
        if (userBean != null) {
            item.setId(userBean.getId());
            mUserBeanDao.update(item);
            return;
        }
        item.setId(null);
        mUserBeanDao.insert(item);
    }

}
