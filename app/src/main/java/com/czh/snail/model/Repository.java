package com.czh.snail.model;

import com.czh.snail.model.beans.GankBeautyResult;
import com.czh.snail.model.network.Network;
import com.czh.snail.model.network.PictureDataSource;

import rx.Observable;

/**
 * Created by chenzhihui on 16/7/29.
 * 数据仓库
 */
public class Repository implements PictureDataSource {

    private static final class RepositoryHelper {
        public static final Repository INSTANCE = new Repository();
    }

    private Repository() {
    }

    public static Repository getInstance() {
        return RepositoryHelper.INSTANCE;
    }

    public Observable<GankBeautyResult> getSplashPic() {
        return Network.getGankApi().getBeauties(1, 1);
    }

    public Observable<GankBeautyResult> getWelfareList(int pageSize, int index) {
        return Network.getGankApi().getBeauties(pageSize, index);
    }


}
