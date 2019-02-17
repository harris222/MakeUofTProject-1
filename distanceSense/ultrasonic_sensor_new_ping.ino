#include <NewTone.h>
#include <NewPing.h>

int trigPin = 11;
int echoPin = 12;
int maxDistance = 200; 
NewPing sonar(trigPin, echoPin, maxDistance);
int buzzerPin = 4; 
int lightPin = 6; 
int kBuzzer = 30000; //buzzer
int kLight = 510; 
double duration, cm;
unsigned int x, n;

void setup() {
  Serial.begin(9600);
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  pinMode(buzzerPin, OUTPUT); 
  pinMode(lightPin, OUTPUT);
}

void loop() {
  double distance = sonar.ping_cm();
  Serial.print (distance);

  x = kBuzzer * (1/distance); 
  n = kLight * (1/distance);
  Serial.println();
  Serial.print(n);
  Serial.println();
  
  if (distance < 200 && distance != 0) {
    NewTone(buzzerPin, x);
    analogWrite(lightPin, n);
  } else {
    x = 0;
    NewTone(buzzerPin, x);
    analogWrite(lightPin, x);  
  }

  delay(100);
  
}
