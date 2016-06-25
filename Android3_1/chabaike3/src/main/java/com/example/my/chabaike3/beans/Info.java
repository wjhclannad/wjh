package com.example.my.chabaike3.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by my on 2016/6/22.
 */
public class Info implements Parcelable{
    private String title;
    private int infoclass;
    private String img;
    private String description;

    @Override
    public String toString() {
        return "Info{" +
                "title='" + title + '\'' +
                ", infoclass=" + infoclass +
                ", img='" + img + '\'' +
                ", description='" + description + '\'' +
                ", keywords='" + keywords + '\'' +
                ", count=" + count +
                ", fcount=" + fcount +
                ", rcount=" + rcount +
                ", time='" + time + '\'' +
                ", id=" + id +
                '}';
    }

    private String keywords;
    private int count ;
    private int fcount;
    private int rcount;
    private String time;
    private long id;

    public Info(Parcel in){
        img = in.readString();
        description = in.readString();
        rcount = in.readInt();
        time = in.readString();
        id = in.readLong();
    }
    public Info(){}
    public static final Creator<Info> CREATOR = new Creator<Info>() {

        @Override
        public Info createFromParcel(Parcel source) {
            return new Info(source);
        }

        @Override
        public Info[] newArray(int size) {
            return new Info[size];
        }
    };


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getInfoclass() {
        return infoclass;
    }

    public void setInfoclass(int infoclass) {
        this.infoclass = infoclass;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getFcount() {
        return fcount;
    }

    public void setFcount(int fcount) {
        this.fcount = fcount;
    }

    public int getRcount() {
        return rcount;
    }

    public void setRcount(int rcount) {
        this.rcount = rcount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(img);
        dest.writeString(description);
        dest.writeInt(rcount);
        dest.writeString(time);
        dest.writeLong(id);
    }
}
