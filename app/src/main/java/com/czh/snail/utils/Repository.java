package com.czh.snail.utils;

/**
 * Created by chenzhihui on 16/7/29.
 * 数据仓库
 */
public class Repository {

    private static final class RepositoryHelper {
        public static final Repository INSTANCE = new Repository();
    }

    private Repository() {
    }

    public static Repository getInstance() {
        return RepositoryHelper.INSTANCE;
    }
}
