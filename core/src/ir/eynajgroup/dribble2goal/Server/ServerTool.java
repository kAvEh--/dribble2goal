package ir.eynajgroup.dribble2goal.Server;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * Created by kAvEh on 2/20/2016.
 */
public class ServerTool {

    Socket socket;
    public void connectSocket(){
        JSONObject data = new JSONObject();

        try {
            socket = IO.socket("http://130.185.76.91:8080");
            System.out.println("------------");
            socket.connect();
        } catch(Exception e){
            System.out.println(e);
        }

        try {
            data.put("username", "kiiiiiif");
            data.put("password", "kjahfgjhagldf");

            socket.emit("sign-in", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
