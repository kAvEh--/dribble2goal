package ir.eynakgroup.dribble2goal;

/**
 * Created by Eynak_PC2 on 11/16/2016.
 */
public class SignInFinishHandler {

    public static abstract class LoginFinishHandler {
        public abstract void onError(int paramInt);

        public abstract void onFinish(String email, String id, String imageUrl, String nickName);
    }

}
