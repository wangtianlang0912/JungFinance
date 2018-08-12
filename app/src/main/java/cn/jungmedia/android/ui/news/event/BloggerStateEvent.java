package cn.jungmedia.android.ui.news.event;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/8/12. 下午5:03
 *
 *
 */
public class BloggerStateEvent {

    int objectId;
    int favoriteObjId;
    int favoriteUid;
    boolean isSubcribed;

    public BloggerStateEvent(int objectId, boolean isSubcribed, int favoriteObjId,int favoriteUid) {
        this.objectId = objectId;
        this.isSubcribed = isSubcribed;
        this.favoriteObjId = favoriteObjId;
        this.favoriteUid = favoriteUid;
    }

    public int getObjectId() {
        return objectId;
    }

    public int getFavoriteObjId() {
        return favoriteObjId;
    }

    public int getFavoriteUid() {
        return favoriteUid;
    }

    public boolean isSubcribed() {

        return isSubcribed;
    }
}
