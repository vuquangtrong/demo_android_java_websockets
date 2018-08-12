package com.humaxdigital.vqtrong.testwebsocket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

public class TestWebSocketMainActivity extends AppCompatActivity {

    private WebSocketClient webSocketClient;
    private TextView txtMessages;
    private String URL = "ws://10.0.2.2:6789";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_web_socket_main);
        txtMessages = findViewById(R.id.txtMessages);

        connectWebSocket(URL);
    }

    private void connectWebSocket(String url) {
        Log.e("connectWebSocket", url);
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.e("Websocket", "Opened");
                //webSocketClient.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
            }

            @Override
            public void onMessage(String s) {
                final String message = s;
                Log.e("Websocket", "_onMessage(String)");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(">>>: ", message);
                        txtMessages.append("\n" + message);
                    }
                });
            }

            @Override
            public void onMessage(ByteBuffer bytes) {
                super.onMessage(bytes);
                Log.e("Websocket", "_onMessage(ByteBuffer)");
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.e("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.e("Websocket", "Error " + e.getMessage());
            }
        };
        webSocketClient.connect();
    }
}
