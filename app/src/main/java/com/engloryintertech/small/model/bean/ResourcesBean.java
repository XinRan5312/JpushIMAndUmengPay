package com.engloryintertech.small.model.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/4 0004.
 */
public class ResourcesBean implements Serializable{//,Comparable

//            "_id": "565f02b935de7e131c53ea1b",
//            "name": "自定义视图属性.",
//            "description": "自定义视图属性.自定义视图属性",
//            "short_description": "自定义视图属性.自定义视图属性.自定义视",
//            "like_no": 0,
//            "view_no": 0,
//            "link": "http:\/\/61.187.93.86:6001\/files\/pItX3XIC_2Fl6RZjM5hZVhXG.mp4",
//            "create_at": "2015-12-02T14:33:04.518Z",
//            "__v": 0,
//            "offline_no": 0,
//            "create_user": [
//            "5657c6c9b63e3e8604c571ad"
//            ],
//            "thumbnails": "http:\/\/61.187.93.86:6001\/files\/8tOJPoaOAohDAXP3kJLrMAFl.jpg",
//            "type": "video"

    private String _id;
    private String name;
    private String description;
    private String short_description;
    private int like_no;
    private int view_no;
    private String link;
    private String create_at;
//    private int __v;
    private int offline_no;
//    private String[] create_user;
    private String thumbnails;
    private String type;

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_id() {
        return _id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setLike_no(int like_no) {
        this.like_no = like_no;
    }

    public int getLike_no() {
        return like_no;
    }

    public void setView_no(int view_no) {
        this.view_no = view_no;
    }

    public int getView_no() {
        return view_no;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getCreate_at() {
        return create_at;
    }

//    public void set__v(int __v) {
//        this.__v = __v;
//    }
//
//    public int get__v() {
//        return __v;
//    }

    public void setOffline_no(int offline_no) {
        this.offline_no = offline_no;
    }

    public int getOffline_no() {
        return offline_no;
    }

//    public void setCreate_user(String[] create_user) {
//        this.create_user = create_user;
//    }
//
//    public String[] getCreate_user() {
//        return create_user;
//    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public ResourcesBean getInfo(){
        //            "_id": "565f02b935de7e131c53ea1b",
//            "name": "自定义视图属性.",
//            "description": "自定义视图属性.自定义视图属性",
//            "short_description": "自定义视图属性.自定义视图属性.自定义视",
//            "like_no": 0,
//            "view_no": 0,
//            "link": "http:\/\/61.187.93.86:6001\/files\/pItX3XIC_2Fl6RZjM5hZVhXG.mp4",
//            "create_at": "2015-12-02T14:33:04.518Z",
//            "__v": 0,
//            "offline_no": 0,
//            "create_user": [
//            "5657c6c9b63e3e8604c571ad"
//            ],
//            "thumbnails": "http:\/\/61.187.93.86:6001\/files\/8tOJPoaOAohDAXP3kJLrMAFl.jpg",
//            "type": "video"
        ResourcesBean resourcesBean = new ResourcesBean();
        resourcesBean.set_id("565f02b935de7e131c53ea1b");
        resourcesBean.setName("测试分享");
        resourcesBean.setShort_description("asdasdasdaneitsdsksndj");
        resourcesBean.setLink("https://www.baidu.com/");
        resourcesBean.setThumbnails("http:\\/\\/61.187.93.86:6001\\/files\\/8tOJPoaOAohDAXP3kJLrMAFl.jpg");
        return resourcesBean;
    }
}
