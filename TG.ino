#include <WiFi.h>
#include <HTTPClient.h>
#include <ArduinoJson.h>

 
const char* ssid = "Fatec WiFi";
const char* password = "";


 
void setup() {
  
  Serial.begin(115200);
  delay(4000);   //Delay needed before calling the WiFi.begin
  pinMode(15, INPUT);
  pinMode(2, OUTPUT);
  WiFi.begin(ssid, password); 
 
  while (WiFi.status() != WL_CONNECTED) { //Check for the connection
    delay(1000);
    Serial.println("Connecting to WiFi..");
  }
 
  Serial.println("Connected to the WiFi network");
 
}
 
void loop() {

  int button_state = digitalRead(15);
  
//  int button_state = HIGH;
  if(button_state == HIGH){
    digitalWrite(2,LOW);
    Serial.println("Energia");
    if(WiFi.status()== WL_CONNECTED){   //Check WiFi connection status
 
   HTTPClient http;   
 
   http.begin("http://jsonplaceholder.typicode.com/posts");  //Specify destination for HTTP request
//   http.addHeader("Content-Type", "application/json");             //Specify content-type header
 
   int httpResponseCode = http.POST("POSTING from ESP32");   //Send the actual POST request
 
   if(httpResponseCode>0){
 
    String response = http.getString();                       //Get the response to the request
    StaticJsonDocument<200> parsed;
//    Serial.println(httpResponseCode);   //Print return code
    Serial.println(response);           //Print request answer
    deserializeJson(parsed, response);//Parse message
    int id = parsed["id"];
//    Serial.println("Id from json : ");
    Serial.println(id);

    
   }else{
 
    Serial.println("Error on sending POST: ");
    Serial.println(httpResponseCode);
 
   }
 
   http.end();  //Free resources

    HTTPClient http2;  
    http2.begin("https://tg-novo.firebaseio.com/coordenates.json");
    http.addHeader("Content-Type", "text/plain");
    unsigned long tempo = millis();
    Serial.println(tempo);
    int number = random(1,100);
    int resp = http2.PUT("{\"data\": {\"latitude\": \"20.00\", \"longitude\":\"10.00\",\"usuario\":\"robson\",\"number\":\""+(String)number+"\"}}");
    tempo = millis();
    Serial.println(tempo);
    Serial.println("Recebi");
    Serial.println(resp);
    http.end();
 
 }else{
 
    Serial.println("Error in WiFi connection");   
 
 }

 delay(5000);
             
  }else{
    digitalWrite(2,HIGH);
    
//  delay(10000);  //Send a request every 10 seconds
 
  }
}
