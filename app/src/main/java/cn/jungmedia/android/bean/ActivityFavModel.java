package cn.jungmedia.android.bean;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/7. 下午5:23
 *
 *
 */
public class ActivityFavModel {

    private Favorite favorite;

    public void setFavorite(Favorite favorite) {
        this.favorite = favorite;
    }

    public Favorite getFavorite() {
        return this.favorite;
    }

    public class Favorite {
        private int entityId;

        private ActivityModel.Activity activity;

        private int objectId;

        public void setEntityId(int entityId) {
            this.entityId = entityId;
        }

        public int getEntityId() {
            return this.entityId;
        }

        public void setActivity(ActivityModel.Activity activity) {
            this.activity = activity;
        }

        public ActivityModel.Activity getActivity() {
            return this.activity;
        }

        public void setObjectId(int objectId) {
            this.objectId = objectId;
        }

        public int getObjectId() {
            return this.objectId;
        }

    }
}
