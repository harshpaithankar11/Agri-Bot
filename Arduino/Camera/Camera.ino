#include <WiFi.h>
#include <WebSocketsServer.h>
#include <esp_camera.h>
extern "C" {
    #include "esp_heap_caps.h"
}





const char* ssid= "Your Wifi ssid";
const char* password = "Wifi password";



IPAddress local_IP(192, 168, 1, 201);  
IPAddress gateway(192, 168, 1, 1);
IPAddress subnet(255, 255, 255, 0);
IPAddress primaryDNS(8, 8, 8, 8);
IPAddress secondaryDNS(8, 8, 4, 4);



WebSocketsServer webSocket = WebSocketsServer(1);
uint8_t n;


#define PWDN_GPIO_NUM     32
#define RESET_GPIO_NUM    -1
#define XCLK_GPIO_NUM      0
#define SIOD_GPIO_NUM     26
#define SIOC_GPIO_NUM     27
#define Y9_GPIO_NUM       35
#define Y8_GPIO_NUM       34
#define Y7_GPIO_NUM       39
#define Y6_GPIO_NUM       36
#define Y5_GPIO_NUM       21
#define Y4_GPIO_NUM       19
#define Y3_GPIO_NUM       18
#define Y2_GPIO_NUM        5
#define VSYNC_GPIO_NUM    25
#define HREF_GPIO_NUM     23
#define PCLK_GPIO_NUM     22




void wifiInt(){ 

WiFi.config(local_IP, gateway, subnet, primaryDNS, secondaryDNS);
    
   WiFi.begin(ssid,password);

  Serial.print("Connecting to WiFi ");

   int timeout = 7;
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
      
            Serial.println("\nFailed to connect  with wifi . Restarting.... ");
            ESP.restart();

          }

    
    }



void setup() {

  Serial.begin(115200);
  wifiInt();



  camera_config_t config;
  config.ledc_channel = LEDC_CHANNEL_0;
  config.ledc_timer = LEDC_TIMER_0;
  config.pin_d0 = Y2_GPIO_NUM;
  config.pin_d1 = Y3_GPIO_NUM;
  config.pin_d2 = Y4_GPIO_NUM;
  config.pin_d3 = Y5_GPIO_NUM;
  config.pin_d4 = Y6_GPIO_NUM;
  config.pin_d5 = Y7_GPIO_NUM;
  config.pin_d6 = Y8_GPIO_NUM;
  config.pin_d7 = Y9_GPIO_NUM;
  config.pin_xclk = XCLK_GPIO_NUM;
  config.pin_pclk = PCLK_GPIO_NUM;
  config.pin_vsync = VSYNC_GPIO_NUM;
  config.pin_href = HREF_GPIO_NUM;
  config.pin_sscb_sda = SIOD_GPIO_NUM;
  config.pin_sscb_scl = SIOC_GPIO_NUM;
  config.pin_pwdn = PWDN_GPIO_NUM;
  config.pin_reset = RESET_GPIO_NUM;
  config.xclk_freq_hz = 20000000;
  config.pixel_format = PIXFORMAT_JPEG;


      config.frame_size = FRAMESIZE_VGA;
      config.jpeg_quality = 11;           
      config.fb_count = 2;  
   

  esp_err_t err = esp_camera_init(&config);
  if (err != ESP_OK) {
    Serial.printf("Camera init failed with error 0x%x", err);
    return;
  }


 webSocket.begin();
  webSocket.onEvent(webSocketEvent);

}

void loop() {
  camera_fb_t * fb = esp_camera_fb_get();
    if (fb) {
         webSocket.sendBIN(n,fb->buf, fb->len);
        esp_camera_fb_return(fb);
    }

  webSocket.loop();
  delay(25);
      
}

void webSocketEvent(uint8_t num, WStype_t type, uint8_t * payload, size_t length) {
  switch (type) {
    case WStype_DISCONNECTED:
      Serial.printf("[%u] Disconnected!\n", num);
      break;
    case WStype_CONNECTED: {
      IPAddress ip = webSocket.remoteIP(num);
      Serial.printf("[%u] Connection from %s\n", num, ip.toString().c_str());
      break;
    }
    case WStype_TEXT:
      n=num;
      break;
}
}



   



