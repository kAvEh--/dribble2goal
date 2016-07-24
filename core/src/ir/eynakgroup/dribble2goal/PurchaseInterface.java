package ir.eynakgroup.dribble2goal;

/**
 * Created by Eynak_PC2 on 7/21/2016.
 */
public class PurchaseInterface {
    public static abstract class InAppPurchaseFinishHandler {
        public abstract void onError(int paramInt);

        public abstract void onFinish(Purchase paramPurchase);
    }

    public static abstract class InAppPurchaseConsumeHandler {
        public abstract void onError(int paramInt);

        public abstract void onFinish(Purchase paramPurchase);
    }

    public static abstract interface InAppPurchaseInterface {
        void StartPurchaseFlow(String sku, PurchaseInterface.InAppPurchaseFinishHandler finishHandler);

        void ConsumePurchase(String sku, PurchaseInterface.InAppPurchaseConsumeHandler consumeHandler);
    }
}
