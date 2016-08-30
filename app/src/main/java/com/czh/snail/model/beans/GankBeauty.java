package com.czh.snail.model.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class GankBeauty implements Parcelable{
    public String _id;
    public String createdAt;
    public String desc;
    public String publishedAt;
    public String source;
    public String type;
    public boolean used;
    public String who;
    public String url;

    protected GankBeauty(Parcel in) {
        _id = in.readString();
        createdAt = in.readString();
        desc = in.readString();
        publishedAt = in.readString();
        source = in.readString();
        type = in.readString();
        used = in.readByte() != 0;
        who = in.readString();
        url = in.readString();
    }

    public static final Creator<GankBeauty> CREATOR = new Creator<GankBeauty>() {
        @Override
        public GankBeauty createFromParcel(Parcel in) {
            return new GankBeauty(in);
        }

        @Override
        public GankBeauty[] newArray(int size) {
            return new GankBeauty[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(createdAt);
        dest.writeString(desc);
        dest.writeString(publishedAt);
        dest.writeString(source);
        dest.writeString(type);
        dest.writeByte((byte) (used ? 1 : 0));
        dest.writeString(who);
        dest.writeString(url);
    }
}
