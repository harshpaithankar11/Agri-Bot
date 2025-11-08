package com.example.virtualfarm;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.text.DecimalFormat;

import io.github.controlwear.virtual.joystick.android.JoystickView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import tech.gusavila92.websocketclient.WebSocketClient;

public class TakeControl extends AppCompatActivity {
    private JoystickView joystick1,joystick2;
    private  camera_setting cs;
    private WebSocketClient webSocketClient;
    private ImageView imageView;
    private ImageButton refresh,capture,Rrt,Lrt,cameraSetting;
    private TextView Temp,humi,soil_moisture,jstate ;
    private String TempR,soil_moistureR, humiR;

    private String mstate;
    private Switch joystik_toggle;
    private float cx=0,cy=0,ax=0,ay=0,az=0;

    private OkHttpClient client,clientC;
    private WebSocket ws ,ws2;
    private SeekBar az2_input;

    private Plant plant;
    JSONObject  capture_json ;
    private Grid_popup popUpClass;
    private Bitmap bitmap;
    private  String base64IMG;
    private Handler handler = new Handler(Looper.getMainLooper());
    private JSONObject json ;




    public void setBase64IMG(String base64IMG) {
        this.base64IMG = base64IMG;
    }

    public String getBase64IMG() {
        return base64IMG;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_take_control);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        capture= findViewById(R.id.capture_btn);
        refresh=findViewById(R.id.Reload);
        Temp=(TextView)findViewById(R.id.Temp);
        humi=(TextView)findViewById(R.id.humi);
        soil_moisture=findViewById(R.id.soil_moisture);
        Rrt=findViewById(R.id.Rrt);
        Lrt=findViewById(R.id.Lrt);

        client = new OkHttpClient();
        clientC = new OkHttpClient();

        Request request = new Request.Builder().url("ws://192.168.22.92:8764/ws").build();
        Request requestC = new Request.Builder().url("ws://192.168.22.92:8765/ws").build();

        ws = client.newWebSocket(request, new EchoWebSocketListener());
        ws2 = clientC.newWebSocket(requestC, new EchoWebSocketListenerC());

        capture_json=new JSONObject();

        popUpClass = new Grid_popup();


        json = new JSONObject();

        joystick1();
        joystick2();
        openS();
    }
    void openS(){
        cs = new camera_setting();
        cameraSetting=findViewById(R.id.cameraSetting);
        cameraSetting.setOnClickListener(v -> cs.showPopupWindow(v));

    }
    void capture_btnB(Bitmap bitmap){
        capture.setOnClickListener(v -> {
            String ss= popUpClass.showPopupWindow(v);
            if(ss!=null){
                plant=new Plant();
                plant.setPlant_img(bitmap);
                plant.setHumi(humiR);
                plant.setTemp(TempR);
                plant.setSoil_cond(soil_moistureR);
                plant.Write_Database(ss);}
        });
    }
    void joystick1(){
        joystick1 = findViewById(R.id.joystick1);
        joystick1.setOnMoveListener(new JoystickView.OnMoveListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onMove(int angle, int strength) {

                if (angle > 160 && angle < 190 && strength > 20) {
                    mstate = "l";
                }
                else if (angle > 340 || angle < 20 && strength > 20) {
                    mstate = "r";
                }
                else if (angle > 70 && angle < 110 && strength > 20) {
                    mstate = "f";
                } else if (angle > 250 && angle < 290 && strength > 20) {
                    mstate = "b";}
                else if (strength < 20) {
                    mstate = "0";
                }
                Rrt.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                try {
                                    json.put("state","33");
                                    ws.send(json.toString());

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return true;
                            case MotionEvent.ACTION_UP:
                            case MotionEvent.ACTION_CANCEL:
                                try {
                                    json.put("state","0");
                                    ws.send(json.toString());

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                return true;
                        }
                        return false;
                    }
                });
                Lrt.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                try {
                                    json.put("state","22");
                                    ws.send(json.toString());

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return true;
                            case MotionEvent.ACTION_UP:
                            case MotionEvent.ACTION_CANCEL:
                                try {
                                    json.put("state","0");
                                    ws.send(json.toString());

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                return true;
                        }
                        return false;
                    }
                });
                try {
                    json.put("state",mstate);
                    ws.send(json.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

    }
    void joystick2(){
        jstate = findViewById(R.id.jstate);
        joystick2 = findViewById(R.id.joystick2);
        joystick2.setAutoReCenterButton(false);

        joystik_toggle= findViewById(R.id.joystick_toggle);
        joystik_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    int p;

                    jstate.setText("    Arm");



                    joystick2.setOnMoveListener(new JoystickView.OnMoveListener() {

                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onMove(int angle, int strength) {
                            DecimalFormat decfor = new DecimalFormat("#");


                            float ay2 = Float.parseFloat(decfor.format((90-(float) (Math.sin(Math.toRadians(angle))*strength*0.9))));

                            float ax2= Float.parseFloat(decfor.format((90-(float) (Math.cos(Math.toRadians(angle))*strength*0.9))));

                            try {
                                json.put("valuex2",ay2);
                                json.put("valuey2",ax2);
                                json.put("valuez2",ay2);

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            ws.send(json.toString());
                        }
                    });
                }
                else{
                    Temp=(TextView)findViewById(R.id.Temp);
                    jstate.setText("Camera");

                    joystick2.setOnMoveListener(new JoystickView.OnMoveListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onMove(int angle, int strength) {
                            DecimalFormat decfor = new DecimalFormat("#");

                            float cy1 = Float.parseFloat(decfor.format((90-(float) (Math.sin(Math.toRadians(angle))*strength*0.85))));
                            float cx1= Float.parseFloat(decfor.format((90-(float) (Math.cos(Math.toRadians(angle))*strength*0.85))));
                            try {
                                json.put("valuex1",cy1);
                                json.put("valuey1",cx1);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            ws.send(json.toString());
                            refresh.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        json.put("valuex1",90);
                                        json.put("valuey1",90);
                                        ws.send(json.toString());
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            });
                        }
                    });


                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    private final class EchoWebSocketListener extends WebSocketListener {
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            System.out.println("WebSocket Connected");

        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            System.out.println("Receiving: " + text);
            runOnUiThread(() -> {
                try {
                    JSONObject jsonObject = new JSONObject( text);
                    int temperature = jsonObject.getInt("temperature");
                    int humidity = jsonObject.getInt("humidity");
                    int soilMoisture = jsonObject.getInt("soil_moisture");
                    String displayTemp = String.format("Temperature: %d", temperature);
                    TempR = displayTemp;
                    String displaysoil = String.format("Soil Moisture: %d", soilMoisture);
                    soil_moistureR=displaysoil;
                    String displayhumi = String.format("Humidity: %d", humidity);
                    humiR=displayhumi;
                    soil_moisture.setText(soil_moistureR);
                    Temp.setText(TempR);
                    humi.setText(humiR);

                } catch (Exception e) {
                }

            });
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            System.out.println("Receiving bytes: " + bytes.hex());

        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(1000, null);
            System.out.println("Closing: " + code + " / " + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            t.printStackTrace();
        }
    }
    private final class EchoWebSocketListenerC extends WebSocketListener {
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            System.out.println("WebSocket Connected");

        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            imageView = findViewById(R.id.capture);

            runOnUiThread(() -> {
                try {
                    byte[] decodedBytes = Base64.decode(text, Base64.DEFAULT);
                    bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                    imageView.setImageBitmap(bitmap);
                    capture_btnB(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            System.out.println("Receiving bytes: " + bytes.hex());

        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(1000, null);
            System.out.println("Closing: " + code + " / " + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            t.printStackTrace();
        }
    }


}
