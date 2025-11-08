



#define m11 14
#define m12 27
#define m1e 12

#define m21  18
#define m22  5
#define m2e  19

#define s1  13
#define s2 15

#define s3  26
#define s4 32
#define s5 25


unsigned long startTime = 0; 

int pos=0;

int sec;
int mins;
int hrs;

int tmx;

int botsp; 

Servo servo1,servo2,servo3,servo4,servo5;



void  Mforward()  {
  digitalWrite(m11, HIGH);
  digitalWrite(m12, LOW);
  analogWrite(m1e,255);

  digitalWrite(m22, LOW);
  digitalWrite(m21, HIGH);
   analogWrite(m2e,255);

 

}
void Mback(){
  digitalWrite(m11, LOW);
  digitalWrite(m12, HIGH);
  analogWrite(m1e,255);

  digitalWrite(m22, HIGH);
  digitalWrite(m21, LOW);
  analogWrite(m2e,255);
}
void Mright(){
 
  digitalWrite(m22, LOW);
  digitalWrite(m21, HIGH);
  analogWrite(m2e,255);

   digitalWrite(m11, LOW);
  digitalWrite(m12, LOW);
  analogWrite(m1e,0);
}
void Mleft(){
    digitalWrite(m11, HIGH);
  digitalWrite(m12, LOW);
  analogWrite(m1e,255);

  digitalWrite(m22, LOW);
  digitalWrite(m21, LOW);
  analogWrite(m2e,0);
}
void MStop(){
      digitalWrite(m11, LOW);
  digitalWrite(m12, LOW);
  analogWrite(m1e,255);

  digitalWrite(m22, LOW);
  digitalWrite(m21, LOW);
  analogWrite(m2e,0);
}
void motorAction(int state) {
  switch (state) {
    case 1 :
    Serial.println("Moving  forward");
    Mforward();
    break;
    case 4:
        Serial.println("Moving  back");
    Mback();
    break;

    case 43:
        Serial.println("Moving  backright");
         digitalWrite(m22, HIGH);
  digitalWrite(m21,LOW);
  analogWrite(m2e,255);

   digitalWrite(m11, LOW);
  digitalWrite(m12, LOW);
  analogWrite(m1e,0);


    
    break;

    case 42:
        Serial.println("Moving  backleft");
          digitalWrite(m11,LOW);
  digitalWrite(m12,HIGH);
  analogWrite(m1e,255);

  digitalWrite(m22,LOW);
  digitalWrite(m21, LOW);
  analogWrite(m2e,0);
        
    
    break;
    case 12:
        Serial.println("Moving  frontleft");
           digitalWrite(m11, HIGH);
  digitalWrite(m12, LOW);
  analogWrite(m1e,255);

  digitalWrite(m22, LOW);
  digitalWrite(m21, LOW);
  analogWrite(m2e,0);
        

    
    break;
    case 13:
        Serial.println("Moving  frontright");
         digitalWrite(m22, LOW);
  digitalWrite(m21, HIGH);
  analogWrite(m2e,255);

   digitalWrite(m11,LOW);
  digitalWrite(m12, LOW);
  analogWrite(m1e,0);
    
    break;
     case 33:
        Serial.println(" rotating right");
         digitalWrite(m22, LOW);
  digitalWrite(m21, HIGH);
  analogWrite(m2e,255);

   digitalWrite(m11, LOW);
  digitalWrite(m12,HIGH);
  analogWrite(m1e,255);
    
    break;
     case 22:
        Serial.println("  rotating left");
           digitalWrite(m11, HIGH);
  digitalWrite(m12, LOW);
  analogWrite(m1e,255);

  digitalWrite(m22, HIGH);
  digitalWrite(m21, LOW);
  analogWrite(m2e,255);
    
    break;


    default:
     MStop();
     Serial.println("stoped");

    break;
     
  }

}
void camera_action(int x,int y){
  servo1.write(x);
  servo2.write(y);  
}
void arm_action(int x,int y){
  servo3.write(x);
  servo4.write(y);

                 
  }
  void armf_action(int st){
   if (st == 11) {
   

    servo5.write(0);
    
  }

  else if (st== 22) {
    
     servo5.write(180);
  
  }
  }

void atmvx(int tmx){
  Mforward(); 
  delay(tmx*1000);
  MStop(); 
}
void attn(int tmy,int direc){
  if (direc==0){
     // rotating right
   digitalWrite(m22, LOW);
  digitalWrite(m21, HIGH);
  analogWrite(m2e,255);
   digitalWrite(m11, LOW);
  digitalWrite(m12,HIGH);
  analogWrite(m1e,255);

  delay(tmy*1000);

  }
  else{
   // rotating left
  digitalWrite(m11, HIGH);
  digitalWrite(m12, LOW);
  analogWrite(m1e,255);

  digitalWrite(m22, HIGH);
  digitalWrite(m21, LOW);
  analogWrite(m2e,255);

    delay(tmy*1000);

  }


}
void capture(){
  servo1.write(30);
  servo2.write(90); 

  servo3.write(60);
  servo4.write(100); 
}
void atmv(){
  atmvx(1);
  delay(500);
   attn(0.5,1);
     delay(500);
  atmvx(1);
    delay(500);
  capture(); 
}

