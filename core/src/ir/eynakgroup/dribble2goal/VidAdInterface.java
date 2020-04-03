package ir.eynakgroup.dribble2goal;

import ir.eynakgroup.dribble2goal.Util.ShopScrollPane;

/**
 * Created by Eynak_PC2 on 8/18/2016.
 */
public class VidAdInterface {

    public static abstract class VideoAdFinishHandler {
        public abstract void onError(int paramInt);

        public abstract void onFinish(VideoAds mVideoAds);
    }

    public static abstract interface VideoAdInterface {
        void LoadVideoAd(VidAdInterface.VideoAdFinishHandler finishHandler);
    }
}
