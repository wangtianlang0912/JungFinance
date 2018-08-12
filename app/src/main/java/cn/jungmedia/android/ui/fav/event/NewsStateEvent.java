package cn.jungmedia.android.ui.fav.event;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/8/12. 下午10:26
 *
 *
 */
public class NewsStateEvent {

    int objectId;
    boolean isFav;

    public NewsStateEvent(int objectId, boolean isFav) {
        this.objectId = objectId;
        this.isFav = isFav;
    }

    public int getObjectId() {
        return objectId;
    }

    public boolean isFav() {
        return isFav;
    }
}
