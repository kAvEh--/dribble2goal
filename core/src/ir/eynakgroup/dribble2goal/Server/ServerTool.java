package ir.eynakgroup.dribble2goal.Server;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import ir.eynakgroup.dribble2goal.GamePrefs;
import ir.eynakgroup.dribble2goal.MyGame;

/**
 * Created by kAvEh on 2/20/2016.
 *
 */
public class ServerTool {

    public static Socket socket;
    private static ServerTool instance;

    public static ServerTool getInstance() {
        if (instance == null) {
            instance = new ServerTool();
            connectSocket2();
        }
        return instance;
    }

    private static void connectSocket2() {
        try {
            socket = IO.socket("http://127.0.0.1:8080");
            socket.connect();
        } catch (Exception e) {
//            System.err.println(e);
        }
    }

    public void getCoin() {
        if (!socket.connected()) {
            socket.connect();
            MyGame.mainInstance.setEntranceScreen();
            return;
        }
        JSONObject data = new JSONObject();
        try {
            data.put("playerId", GamePrefs.getInstance().playerId);

            socket.emit("coin", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getProfile() {
        if (!socket.connected()) {
            socket.connect();
            MyGame.mainInstance.setEntranceScreen();
            return;
        }
        JSONObject data = new JSONObject();
        try {
            data.put("playerId", GamePrefs.getInstance().playerId);

            socket.emit("profile", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setNickName(String newName) {
        if (!socket.connected()) {
            socket.connect();
            MyGame.mainInstance.setEntranceScreen();
            return;
        }
        JSONObject data = new JSONObject();
        try {
            data.put("playerId", GamePrefs.getInstance().playerId);
            data.put("nickname", newName);

            socket.emit("new-nickname", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void freeCoin() {
        if (!socket.connected()) {
            socket.connect();
            MyGame.mainInstance.setEntranceScreen();
            return;
        }
        JSONObject data = new JSONObject();
        try {
            data.put("playerId", GamePrefs.getInstance().playerId);

            socket.emit("freeCoin", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendBackFind(int stadium) {
        if (!socket.connected()) {
            socket.connect();
            MyGame.mainInstance.setEntranceScreen();
            return;
        }
        JSONObject data = new JSONObject();
        try {
            data.put("playerId", GamePrefs.getInstance().playerId);
            data.put("roomNum", stadium);

            socket.emit("back", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void signOut() {
        if (!socket.connected()) {
            socket.connect();
            MyGame.mainInstance.setEntranceScreen();
            return;
        }
        JSONObject data = new JSONObject();
        try {
            data.put("playerId", GamePrefs.getInstance().playerId);

            socket.emit("sign-out", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void login(String username, String pass) {
        if (!socket.connected()) {
            socket.connect();
        }
        JSONObject data = new JSONObject();
        try {
            data.put("username", username);
            data.put("password", pass);

            System.out.println("------->>>> login emitted" + data);
            socket.emit("sign-in", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void loginGoogle(String email, String id, String nickName) {
        if (!socket.connected()) {
            socket.connect();
            return;
        }
        JSONObject data = new JSONObject();
        try {
            data.put("username", email);
            data.put("password", id);
            data.put("nickname", nickName);

            socket.emit("sign-in-google", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void register(String username, String pass, String nickName) {
        if (!socket.connected()) {
            socket.connect();
            return;
        }
        JSONObject data = new JSONObject();
        try {
            data.put("username", username);
            data.put("password", pass);
            data.put("nickname", nickName);

            socket.emit("new-player", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void findMatch(int level, boolean ispenalty) {
        if (!socket.connected()) {
            socket.connect();
            MyGame.mainInstance.setEntranceScreen();
            return;
        }
        JSONObject data = new JSONObject();
        int tmp = 0;
        if (ispenalty)
            tmp = 6;
        try {
            data.put("playerId", GamePrefs.getInstance().playerId);
            data.put("roomNum", level + tmp);

            socket.emit("find-match", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void matchDone() {
        if (!socket.connected()) {
            socket.connect();
            MyGame.mainInstance.setEntranceScreen();
            return;
        }
        JSONObject data = new JSONObject();
        try {
            data.put("playerId", GamePrefs.getInstance().playerId);

            socket.emit("matchDone", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void reConnect() {
        if (!socket.connected()) {
            socket.connect();
            MyGame.mainInstance.setEntranceScreen();
            return;
        }
        JSONObject data = new JSONObject();
        try {
            data.put("playerId", GamePrefs.getInstance().playerId);

            socket.emit("rc", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void resignMatch() {
        if (!socket.connected()) {
            socket.connect();
            MyGame.mainInstance.setEntranceScreen();
            return;
        }
        JSONObject data = new JSONObject();
        try {
            data.put("playerId", GamePrefs.getInstance().playerId);

            socket.emit("resign", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void editAvatar(int avatar) {
        if (!socket.connected()) {
            socket.connect();
            MyGame.mainInstance.setEntranceScreen();
            return;
        }
        System.out.println("avatar ------>>>>" + avatar);
        JSONObject data = new JSONObject();
        try {
            data.put("playerId", GamePrefs.getInstance().playerId);
            data.put("avatarId", avatar);

            socket.emit("edit-avatar", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendUpgrade(int num, String attr, int value) {
        if (!socket.connected()) {
            socket.connect();
            MyGame.mainInstance.setEntranceScreen();
            return;
        }
        JSONObject data = new JSONObject();
        try {
            data.put("playerId", GamePrefs.getInstance().playerId);
            data.put("playerNum", num);
            data.put("attrName", attr);
            data.put("attrVal", value);

            socket.emit("upgrade", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendLineUp(int position, int[] lineup) {
        if (!socket.connected()) {
            socket.connect();
            MyGame.mainInstance.setEntranceScreen();
            return;
        }
        JSONObject data = new JSONObject();
        try {
            data.put("playerId", GamePrefs.getInstance().playerId);
            data.put("formation", position);
            JSONObject tmp = new JSONObject();
            for (int i = 0; i < lineup.length; i++) {
                if (i > 2) {
                    tmp.put((lineup[i] + 1) + "", -1);
                } else {
                    tmp.put((lineup[i] + 1) + "", i + 1);
                }
            }
            data.put("lineup", tmp);

            socket.emit("defaultPosition", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendShop(int coinPackage, int shirtId, String token, String payload) {
//        if (!socket.connected()) {
//            socket.connect();
//            MyGame.mainInstance.setEntranceScreen();
//            return;
//        }
        JSONObject data = new JSONObject();
        try {
            data.put("playerId", GamePrefs.getInstance().playerId);
            data.put("coinPackage", coinPackage);
            data.put("shirtId", shirtId);
            data.put("token", token);
            data.put("payload", payload);

            socket.emit("shop", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
