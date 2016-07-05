package ir.eynajgroup.dribble2goal.Server;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import ir.eynajgroup.dribble2goal.GamePrefs;

/**
 * Created by kAvEh on 2/20/2016.
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

    public static void connectSocket2(){
        try {
            socket = IO.socket("http://130.185.76.91:6060");
            socket.connect();
        } catch(Exception e){
            System.err.println(e);
        }
    }

    public void login(String username, String pass) {
        JSONObject data = new JSONObject();
        try {
            data.put("username", username);
            data.put("password", pass);

            socket.emit("sign-in", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void register(String username, String pass, String nickName) {
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

    public void findMatch(int level) {
        JSONObject data = new JSONObject();
        try {
            data.put("playerId", GamePrefs.getInstance().playerId);

            socket.emit("find-match" + level, data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendUpgrade(int num, String attr, int value) {
        JSONObject data = new JSONObject();
        System.out.println(GamePrefs.getInstance().playerId + " = " + num + " = " + attr + " = " + value);
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
        JSONObject data = new JSONObject();
        try {
            data.put("playerId", GamePrefs.getInstance().playerId);
            data.put("formation", position);
            JSONObject tmp = new JSONObject();
            for (int i = 0; i < lineup.length; i++) {
                if (i > 2) {
                    tmp.put((lineup[i] +1) + "", -1);
                } else {
                    tmp.put((lineup[i] +1) + "", i + 1);
                }
            }
            data.put("lineup", tmp);

            socket.emit("defaultPosition", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendShop(int coinPackage, int shirtId, String token, String payload) {
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

    public void sendAfter(JSONObject data) {
        socket.emit("after", data);
    }
}
