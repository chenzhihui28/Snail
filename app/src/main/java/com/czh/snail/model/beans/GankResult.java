package com.czh.snail.model.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GankResult {

    private boolean error;
    public Result results;
    public List<String> category;

    public class Result {
        @SerializedName("Android")
        public List<Gank> androidList;
        @SerializedName("休息视频")
        public List<Gank> restVideoList;
        @SerializedName("iOS")
        public List<Gank> iOSList;
        @SerializedName("福利")
        public List<Gank> beautyList;
        @SerializedName("拓展资源")
        public List<Gank> expandResourceList;
        @SerializedName("瞎推荐")
        public List<Gank> recommendList;
        @SerializedName("App")
        public List<Gank> appList;
    }

}
