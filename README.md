[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

### **Firebase Kit**
  An Android application showcasing the implementation of Firebase features using **Clean Architecture and MVVM** design pattern.

| Dark Mode | Light Mode | Google SignIn/SignUp | Profile Screen | Anonymous SignIn (Skip) |
| --- | --- | --- | --- | --- |
| <img src="https://github.com/Brindha-m/FirebaseKit/assets/72887609/864b3c82-c1ab-4674-bae5-b0915bd8bea2" width="225" height="400"/> | <img src="https://github.com/Brindha-m/FirebaseKit/assets/72887609/e4983f15-c5ee-44bf-98e7-1d7081154515" width="225" height="400"/> | <img src="https://github.com/Brindha-m/FirebaseKit/assets/72887609/00207ebe-ae58-4f81-a494-f7cca3de6aff" width="225" height="400"/> | <img src="https://github.com/Brindha-m/FirebaseKit/assets/72887609/5a0eb4ec-1aa7-40c3-af39-b7673b338fc3" width="225" height="400"/> | <img src="https://github.com/Brindha-m/FirebaseKit/assets/72887609/59549af6-2716-4b23-8086-e8bb156ec405" width="225" height="400"/> |


## Features

- **Firebase Authentication:** Implementing user authentication using Firebase Authentication [Google Sign and Anonymous].
- **Remote Config:** Dynamic app configuration using Firebase Remote Config.

## Architecture

The project follows the Clean Architecture principles and MVVM design pattern. It is structured into multiple layers:

- **Presentation:** Contains UI (view) components and ViewModels.
- **Domain:** Holds business logic and use case implementations.
- **Data:** Manages data sources, repositories, and interacts with external APIs (Firebase in this case).

## Firebase Setup

Before building and running the project, you need to set up your Firebase project:

1. Create a new project on the [Firebase Console](https://console.firebase.google.com/).
2. Add your Android app to the project and download the `google-services.json` file.
3. Place the `google-services.json` file in the `app` module of your project.

### 1. Obtain the SHA-1 Release Fingerprint:

#### Using Android Studio:

1. Open Android Studio.
2. Click on the "Gradle" tab on the right side.
3. Navigate to `Your App -> Tasks -> android -> signingReport`.
4. Run `signingReport`. The SHA-1 fingerprint will be displayed in the "Run" tab at the bottom.
<img alt="image" src="https://github.com/Brindha-m/FirebaseKit/assets/72887609/325c9979-32e8-4618-abd7-71405fc7454b">
<img alt="image" src="https://github.com/Brindha-m/FirebaseKit/assets/72887609/1be38811-d39a-400c-bbdc-83345408c1bd">



### 2. Add the SHA-1 Fingerprint to Firebase:

1. Go to the [Firebase Console](https://console.firebase.google.com/).
2. Open your Firebase project.
3. Navigate to "Authentication" in the left-hand menu.
4. Go to the "Sign-in method" tab.
5. Enable "Google" as a sign-in provider.

![image](https://github.com/Brindha-m/FirebaseKit/assets/72887609/d4ec9da0-8163-4a6b-9f30-c1aae3b80676)

6. In the "Web client ID" section, click on "Download" to download the configuration file.
7. Save the downloaded `google-services.json` file into your app module.
8. In the "Project settings" page, navigate to the "General" tab.
9. Scroll down to the "Your apps" section.
10. In the "SHA certificate fingerprints" section, click on "Add fingerprint."
11. Enter the SHA-1 fingerprint obtained in step 1.
12. Save the changes.






## Contributing

Feel free to contribute to this project by opening issues, providing feedback, or submitting pull requests. Your contributions are highly appreciated.

