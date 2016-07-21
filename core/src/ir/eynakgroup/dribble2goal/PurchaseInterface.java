package ir.eynakgroup.dribble2goal;

/**
 * Created by Eynak_PC2 on 7/21/2016.
 */
public class PurchaseInterface {
    public static abstract class InAppPurchaseFinishHandler {
        public abstract void onError(int paramInt);

        public abstract void onFinish(Purchase paramPurchase);
    }

    public static abstract interface InAppPurchaseInterface {
        public abstract void StartPurchaseFlow(String paramString, PurchaseInterface.InAppPurchaseFinishHandler paramInAppPurchaseFinishHandler);
    }
}
