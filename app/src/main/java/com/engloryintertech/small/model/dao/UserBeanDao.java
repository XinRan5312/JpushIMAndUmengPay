package com.engloryintertech.small.model.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.engloryintertech.small.model.bean.UserBean;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table USER_BEAN.
*/
public class UserBeanDao extends AbstractDao<UserBean, Long> {

    public static final String TABLENAME = "USER_BEAN";

    /**
     * Properties of entity UserBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property UserId = new Property(1, int.class, "UserId", false, "USER_ID");
        public final static Property UserName = new Property(2, String.class, "UserName", false, "USER_NAME");
        public final static Property UserNickName = new Property(3, String.class, "UserNickName", false, "USER_NICK_NAME");
        public final static Property Sex = new Property(4, Integer.class, "Sex", false, "SEX");
        public final static Property Age = new Property(5, Integer.class, "Age", false, "AGE");
        public final static Property Profession = new Property(6, String.class, "Profession", false, "PROFESSION");
        public final static Property Company = new Property(7, String.class, "Company", false, "COMPANY");
        public final static Property Email = new Property(8, String.class, "Email", false, "EMAIL");
        public final static Property Signature = new Property(9, String.class, "Signature", false, "SIGNATURE");
        public final static Property Birthday = new Property(10, String.class, "Birthday", false, "BIRTHDAY");
        public final static Property AvatarUrl = new Property(11, String.class, "AvatarUrl", false, "AVATAR_URL");
        public final static Property Address = new Property(12, String.class, "Address", false, "ADDRESS");
        public final static Property TellPhone = new Property(13, String.class, "TellPhone", false, "TELL_PHONE");
        public final static Property UserToken = new Property(14, String.class, "UserToken", false, "USER_TOKEN");
        public final static Property School = new Property(15, String.class, "School", false, "SCHOOL");
        public final static Property AuthenticationState = new Property(16, Integer.class, "AuthenticationState", false, "AUTHENTICATION_STATE");
        public final static Property TellPhoneState = new Property(17, Integer.class, "TellPhoneState", false, "TELL_PHONE_STATE");
        public final static Property CertificationState = new Property(18, Integer.class, "CertificationState", false, "CERTIFICATION_STATE");
        public final static Property RecommendCode = new Property(19, String.class, "RecommendCode", false, "RECOMMEND_CODE");
        public final static Property CreditValue = new Property(20, Float.class, "CreditValue", false, "CREDIT_VALUE");
        public final static Property MonetaryAssets = new Property(21, Float.class, "MonetaryAssets", false, "MONETARY_ASSETS");
        public final static Property RedAssets = new Property(22, Float.class, "RedAssets", false, "RED_ASSETS");
        public final static Property LoginType = new Property(23, int.class, "LoginType", false, "LOGIN_TYPE");
    };


    public UserBeanDao(DaoConfig config) {
        super(config);
    }
    
    public UserBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'USER_BEAN' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'USER_ID' INTEGER NOT NULL ," + // 1: UserId
                "'USER_NAME' TEXT NOT NULL ," + // 2: UserName
                "'USER_NICK_NAME' TEXT," + // 3: UserNickName
                "'SEX' INTEGER," + // 4: Sex
                "'AGE' INTEGER," + // 5: Age
                "'PROFESSION' TEXT," + // 6: Profession
                "'COMPANY' TEXT," + // 7: Company
                "'EMAIL' TEXT," + // 8: Email
                "'SIGNATURE' TEXT," + // 9: Signature
                "'BIRTHDAY' TEXT," + // 10: Birthday
                "'AVATAR_URL' TEXT," + // 11: AvatarUrl
                "'ADDRESS' TEXT," + // 12: Address
                "'TELL_PHONE' TEXT," + // 13: TellPhone
                "'USER_TOKEN' TEXT," + // 14: UserToken
                "'SCHOOL' TEXT," + // 15: School
                "'AUTHENTICATION_STATE' INTEGER," + // 16: AuthenticationState
                "'TELL_PHONE_STATE' INTEGER," + // 17: TellPhoneState
                "'CERTIFICATION_STATE' INTEGER," + // 18: CertificationState
                "'RECOMMEND_CODE' TEXT," + // 19: RecommendCode
                "'CREDIT_VALUE' REAL," + // 20: CreditValue
                "'MONETARY_ASSETS' REAL," + // 21: MonetaryAssets
                "'RED_ASSETS' REAL," + // 22: RedAssets
                "'LOGIN_TYPE' INTEGER NOT NULL );"); // 23: LoginType
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'USER_BEAN'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, UserBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getUserId());
        stmt.bindString(3, entity.getUserName());
 
        String UserNickName = entity.getUserNickName();
        if (UserNickName != null) {
            stmt.bindString(4, UserNickName);
        }
 
        Integer Sex = entity.getSex();
        if (Sex != null) {
            stmt.bindLong(5, Sex);
        }
 
        Integer Age = entity.getAge();
        if (Age != null) {
            stmt.bindLong(6, Age);
        }
 
        String Profession = entity.getProfession();
        if (Profession != null) {
            stmt.bindString(7, Profession);
        }
 
        String Company = entity.getCompany();
        if (Company != null) {
            stmt.bindString(8, Company);
        }
 
        String Email = entity.getEmail();
        if (Email != null) {
            stmt.bindString(9, Email);
        }
 
        String Signature = entity.getSignature();
        if (Signature != null) {
            stmt.bindString(10, Signature);
        }
 
        String Birthday = entity.getBirthday();
        if (Birthday != null) {
            stmt.bindString(11, Birthday);
        }
 
        String AvatarUrl = entity.getAvatarUrl();
        if (AvatarUrl != null) {
            stmt.bindString(12, AvatarUrl);
        }
 
        String Address = entity.getAddress();
        if (Address != null) {
            stmt.bindString(13, Address);
        }
 
        String TellPhone = entity.getTellPhone();
        if (TellPhone != null) {
            stmt.bindString(14, TellPhone);
        }
 
        String UserToken = entity.getUserToken();
        if (UserToken != null) {
            stmt.bindString(15, UserToken);
        }
 
        String School = entity.getSchool();
        if (School != null) {
            stmt.bindString(16, School);
        }
 
        Integer AuthenticationState = entity.getAuthenticationState();
        if (AuthenticationState != null) {
            stmt.bindLong(17, AuthenticationState);
        }
 
        Integer TellPhoneState = entity.getTellPhoneState();
        if (TellPhoneState != null) {
            stmt.bindLong(18, TellPhoneState);
        }
 
        Integer CertificationState = entity.getCertificationState();
        if (CertificationState != null) {
            stmt.bindLong(19, CertificationState);
        }
 
        String RecommendCode = entity.getRecommendCode();
        if (RecommendCode != null) {
            stmt.bindString(20, RecommendCode);
        }
 
        Float CreditValue = entity.getCreditValue();
        if (CreditValue != null) {
            stmt.bindDouble(21, CreditValue);
        }
 
        Float MonetaryAssets = entity.getMonetaryAssets();
        if (MonetaryAssets != null) {
            stmt.bindDouble(22, MonetaryAssets);
        }
 
        Float RedAssets = entity.getRedAssets();
        if (RedAssets != null) {
            stmt.bindDouble(23, RedAssets);
        }
        stmt.bindLong(24, entity.getLoginType());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public UserBean readEntity(Cursor cursor, int offset) {
        UserBean entity = new UserBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // UserId
            cursor.getString(offset + 2), // UserName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // UserNickName
            cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4), // Sex
            cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5), // Age
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // Profession
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // Company
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // Email
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // Signature
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // Birthday
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // AvatarUrl
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // Address
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // TellPhone
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // UserToken
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // School
            cursor.isNull(offset + 16) ? null : cursor.getInt(offset + 16), // AuthenticationState
            cursor.isNull(offset + 17) ? null : cursor.getInt(offset + 17), // TellPhoneState
            cursor.isNull(offset + 18) ? null : cursor.getInt(offset + 18), // CertificationState
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // RecommendCode
            cursor.isNull(offset + 20) ? null : cursor.getFloat(offset + 20), // CreditValue
            cursor.isNull(offset + 21) ? null : cursor.getFloat(offset + 21), // MonetaryAssets
            cursor.isNull(offset + 22) ? null : cursor.getFloat(offset + 22), // RedAssets
            cursor.getInt(offset + 23) // LoginType
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, UserBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserId(cursor.getInt(offset + 1));
        entity.setUserName(cursor.getString(offset + 2));
        entity.setUserNickName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSex(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
        entity.setAge(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
        entity.setProfession(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setCompany(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setEmail(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setSignature(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setBirthday(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setAvatarUrl(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setAddress(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setTellPhone(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setUserToken(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setSchool(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setAuthenticationState(cursor.isNull(offset + 16) ? null : cursor.getInt(offset + 16));
        entity.setTellPhoneState(cursor.isNull(offset + 17) ? null : cursor.getInt(offset + 17));
        entity.setCertificationState(cursor.isNull(offset + 18) ? null : cursor.getInt(offset + 18));
        entity.setRecommendCode(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setCreditValue(cursor.isNull(offset + 20) ? null : cursor.getFloat(offset + 20));
        entity.setMonetaryAssets(cursor.isNull(offset + 21) ? null : cursor.getFloat(offset + 21));
        entity.setRedAssets(cursor.isNull(offset + 22) ? null : cursor.getFloat(offset + 22));
        entity.setLoginType(cursor.getInt(offset + 23));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(UserBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(UserBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
