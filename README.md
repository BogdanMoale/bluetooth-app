# bluetooth-app
Android app developed in Android Studio to control a mobile robot using Bluetooth communication


* Bluetooth Connection Setup: The ConectareBluetooth class extends AsyncTask and handles the setup and connection of a Bluetooth socket to the robot. It connects to the specified Bluetooth device address and establishes a communication channel.

* Button Click Listeners: The code sets up click listeners for various buttons such as forward, backward, left, right, stop, disconnect, voice recognition, line tracking, and obstacle avoidance. When these buttons are clicked, corresponding commands are sent over the Bluetooth socket to control the robot's movements.

* Voice Recognition: The SpeachRecognition function initiates voice recognition using Android's built-in speech recognition capabilities. When speech is recognized, the detected text is sent over the Bluetooth socket to control the robot.

* Menu Options: The activity includes a menu with options to turn Bluetooth on/off, access Bluetooth settings, and view information about the app.

* Bluetooth Initialization: The PornesteBluetooth, OpresteBluetooth, and SetariBluetooth functions handle Bluetooth initialization, turning it on/off, and opening the Bluetooth settings.

Please note that this code provides a foundation for controlling a mobile robot using Bluetooth communication. To use this code effectively, you will need to integrate it with the rest of your application, ensure proper error handling, and implement the corresponding Bluetooth communication protocol on the robot side


