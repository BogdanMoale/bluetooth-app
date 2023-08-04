#include <SoftwareSerial.h>

SoftwareSerial bluetooth(10, 11); // RX, TX

void setup() {
  Serial.begin(9600);
  bluetooth.begin(9600);
  pinMode(2, OUTPUT); // Motor control pin
  pinMode(3, OUTPUT); // Motor control pin
  pinMode(4, OUTPUT); // Motor control pin
  pinMode(5, OUTPUT); // Motor control pin
}

void loop() {
  if (bluetooth.available()) {
    char command = bluetooth.read();
    executeCommand(command);
  }
}

void executeCommand(char command) {
  switch (command) {
    case 'a': // forward
      moveForward();
      break;
    case 'b': // backward
      moveBackward();
      break;
    case 'left': // left
      turnLeft();
      break;
    case 'right': // right
      turnRight();
      break;
    case 'stop': // Stop 
      stopRobot();
      break;
    case 'c': //line follow alghoritm
      followLine();
      break;
    case 'obstacle': // obstacle avoidance algorithm
      ObstacleAvoidance();
      break;
    default:
      break;
  }
}

// Define your motor control functions here
void moveForward() {
  digitalWrite(2, HIGH);
  digitalWrite(3, LOW);
  digitalWrite(4, HIGH);
  digitalWrite(5, LOW);
}

void moveBackward() {
  digitalWrite(2, LOW);
  digitalWrite(3, HIGH);
  digitalWrite(4, LOW);
  digitalWrite(5, HIGH);
}

void turnLeft() {
  digitalWrite(2, LOW);
  digitalWrite(3, LOW);
  digitalWrite(4, HIGH);
  digitalWrite(5, LOW);
}

void turnRight() {
  digitalWrite(2, HIGH);
  digitalWrite(3, LOW);
  digitalWrite(4, LOW);
  digitalWrite(5, LOW);
}

void stopRobot() {
  digitalWrite(2, LOW);
  digitalWrite(3, LOW);
  digitalWrite(4, LOW);
  digitalWrite(5, LOW);
}

void followLine() {
  // Implement your line tracking algorithm here
  // For example, control motor actions to follow a line
}

void ObstacleAvoidance() {
  // Implement your obstacle avoidance algorithm here
  // For example, control motor actions to avoid obstacles
}
