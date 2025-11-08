#include <ESP32Servo.h>
#include <ESPAsyncWebServer.h>
#include <AsyncTCP.h>
#include <ArduinoJson.h>
#include <WiFi.h>
#include "action.h"
#include <DHT.h>



const char* ssid= "Your wifi ssid";
const char* password = "Wifi pass";


IPAddress local_IP(192, 168, 163, 192);  
IPAddress gateway(192, 168, 1, 1);     
IPAddress subnet(255, 255, 255, 0);
   





AsyncWebServer server(80);
AsyncWebSocket ws("/ws");






#define DHT_SENSOR_PIN  21

#define DHT_SENSOR_TYPE DHT22
#define Soil_Sensor 34


DHT dht_sensor(DHT_SENSOR_PIN, DHT_SENSOR_TYPE);


 int interval = 1000;                              
unsigned long previousMillis = 0; 


void wifiInt(){ 


  WiFi.config(local_IP, gateway, subnet);
    

   WiFi.begin(ssid,password);

  Serial.print("Connecting to WiFi ");

   int timeout = 30;
    while (WiFi.status() != WL_CONNECTED && timeout > 0) {
        delay(1000);
        Serial.print(".");
        timeout--;
    }

    if (WiFi.status() == WL_CONNECTED) {
        Serial.println("\nConnected!");
        Serial.print("IP Address: ");
        Serial.println(WiFi.localIP());
    } else {
        Serial.println("\nFailed to connect. Restarting...");
        ESP.restart();
    }

    }
  



void sendJsonData() {
   if (ws.availableForWriteAll()){
    
    int tempC =27;
 
   int humi  =85;
   
    int sm  = 2300;
 

  StaticJsonDocument<200> doc;
  doc["temperature"] = tempC;
  doc["humidity"] = humi;
  doc["soil_moisture"] = sm;

 String jsonStr;
  serializeJson(doc, jsonStr);
 ws.textAll(jsonStr);}
}





void setup() {



   Serial.begin(115200);
       wifiInt();


    ws.onEvent(onWebSocketEvent);
    server.addHandler(&ws);
    server.begin();

 

     dht_sensor.begin();
     pinMode(Soil_Sensor,INPUT);

   servo1.attach(s1);
   servo2.attach(s2);

   servo3.attach(s3);
   servo4.attach(s4);
   servo5.attach(s5);

  pinMode(m11, OUTPUT);
  pinMode(m12, OUTPUT);
  pinMode(m1e, OUTPUT);

  pinMode(m21, OUTPUT);
  pinMode(m22, OUTPUT);
  pinMode(m2e, OUTPUT);
    
   

}

void loop() {
 
    ws.cleanupClients();
   

 unsigned long now = millis();                       
  if ((unsigned long)(now - previousMillis) >= interval) {
    sendJsonData();
    previousMillis = now;
    } 

  
}
void onWebSocketEvent(AsyncWebSocket *server, AsyncWebSocketClient *client, 
                      AwsEventType type, void *arg, uint8_t *data, size_t len) {
                         
if (type == WS_EVT_CONNECT) {
    Serial.printf("WebSocket client #%u connected from %s\n", client->id(), client->remoteIP().toString().c_str());}

else if (type == WS_EVT_DISCONNECT) {
    Serial.printf("WebSocket client #%u disconnected\n", client->id());}


else if (type == WS_EVT_DATA) {

        AwsFrameInfo *info = (AwsFrameInfo*)arg; 
        if (info->opcode == WS_TEXT) {
          
            data[len] = 0;
            StaticJsonDocument<200> jsonDoc;
            DeserializationError error = deserializeJson(jsonDoc, (char*)data);
            if (!error) {
                int receivedIntx1 = jsonDoc["valuex1"];
                int receivedInty1 = jsonDoc["valuey1"];
             
                int receivedIntx2= jsonDoc["valuex2"];
                int receivedInty2= jsonDoc["valuey2"];
                int receivedIntz2= jsonDoc["valuez2"];

            
                int receivedstate= jsonDoc["state"];

                int recStimeHr=jsonDoc["valueSetHr"];
                int recStimeMin=jsonDoc["valueSetMin"];

       

                arm_action(receivedIntx2,receivedInty2);
                camera_action(receivedIntx1,receivedInty1);
                motorAction(receivedstate);
                armf_action( receivedIntz2);
          
                


                 
                

                Serial.printf("Received integer  x1: %d\n", receivedIntx1);
                Serial.printf("Received integer y1: %d\n\n\n", receivedInty1);

                

                Serial.printf("Received Shr  x1: %d\n", recStimeHr);
                Serial.printf("Received Smin y1: %d\n\n\n",recStimeMin);

                 Serial.printf("Received integer x2: %d\n", receivedIntx2);
                Serial.printf("Received integer y2: %d\n", receivedInty2);
                 Serial.printf("Received integer z2: %d\n\n\n", receivedIntz2);
              
                Serial.printf("Received state: %d\n", receivedstate);
                
            } else {
                Serial.printf("Failed to parse JSON: %s\n", error.c_str());
            }
        }
    }
}

