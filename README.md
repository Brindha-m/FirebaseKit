##  <img src ="https://github.com/Brindha-m/FirebaseKit/assets/72887609/f48f3af0-ce7c-45fa-81fd-00caaf5d80bc" width = "40"/>  **Firebase Kit**
  
  An Android application showcasing the implementation of Firebase features using **Clean Architecture and MVVM** design pattern.

| Dark Mode (Emulator) | Light Mode (Real Device) |
| --- | --- |
| <img src="https://github.com/Brindha-m/FirebaseKit/assets/72887609/864b3c82-c1ab-4674-bae5-b0915bd8bea2" width="240" height="550"/> | <img src="https://github.com/Brindha-m/FirebaseKit/assets/72887609/e4983f15-c5ee-44bf-98e7-1d7081154515" width="240" height="550"/> | 



## Features

- **Firebase Authentication:** Implementing user authentication using Firebase Authentication [Google Sign and Anonymous].

| Google SignIn/SignUp | Profile Screen | Anonymous SignIn (Skip) |
| --- | --- | --- |
| <img src="https://github.com/Brindha-m/FirebaseKit/assets/72887609/00207ebe-ae58-4f81-a494-f7cca3de6aff" width="240" height="550"/> | <img src="https://github.com/Brindha-m/FirebaseKit/assets/72887609/b823d97f-c373-4c03-b758-70d66a9176c6" width="240" height="550"/> | <img src="https://github.com/Brindha-m/FirebaseKit/assets/72887609/59549af6-2716-4b23-8086-e8bb156ec405" width="240" height="550"/> |


- **Remote Config:** Dynamic app configuration using Firebase Remote Config.

| Remote Config (Dark) | Remote Config (Light) |
| --------| --------|
| <img src="https://github.com/Brindha-m/FirebaseKit/assets/72887609/f85011b4-071b-4573-aa46-c0f3eaec767d" width="250" height="600"/> |  <img src="https://github.com/Brindha-m/FirebaseKit/assets/72887609/844660a5-127f-47a9-a7e9-68f0599c3356"  width="250" height="600"> | 

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

1. Go to the [Firebase Console](https://console.firebase.google.com/). Open your Firebase project.
2. Navigate to "Authentication" in the left-hand menu.
3. Go to the "Sign-in method" tab.
4. Enable "Google" and "Anonymous" as a sign-in provider.

![image](https://github.com/Brindha-m/FirebaseKit/assets/72887609/d4ec9da0-8163-4a6b-9f30-c1aae3b80676)

5. In the "Web client ID" section, copy the client id.
6. In the "Project settings" page, navigate to the "General" tab.
7. In the "SHA certificate fingerprints" section, click on "Add fingerprint." and save the changes.


## Storing Secret Keys in Android

1. Create a file called `apiKeys.properties` in  root directory.
   
  ```kotlin
    WEB_CLIENT_ID = "9...435.apps.googleusercontent.com"
  
  ```

2. In `build.gradle.kts` (module)

   ```kotlin
   android {
   
      val apikeyPropertiesFile = rootProject.file("apiKeys.properties")
      val apikeyProperties = Properties()
  
      /** Just use it here or separately in release and debug buildtypes. **/
     
      defaultConfig {
           buildConfigField("String", "WEB_CLIENT_ID",
                  apikeyProperties["WEB_CLIENT_ID"].toString()
           )
      }
  
      buildTypes {
          degug {
             // Build Config field
          }
          release {
               // Build Config field
          }
      }
      
      buildFeatures {
          compose = true
          buildConfig = true
      }
   
   }
  
    ```


## Remote Config Essentials

1. In Firebase console navigate to **Remote Config dashboard**. Click on "**Add Parameter**" to define a new parameter.
2. Configure the parameter with a key, default value, and optional description.
3. After setting up parameters, click on "**Publish Changes**" to make them live.
4. You can provide default values for Firebase Remote Config in Android Studio either using an XML file (R.xml) or directly in Kotlin code.
5. For Sample JSON data [Click here](https://gist.githubusercontent.com/Brindha-m/fd3bf58ad0aa65b4919e4ce720de2251/raw/aeaa89221baec0107891888fdec8c5add5470f34/remoteConfig.json)

<img src="https://github.com/Brindha-m/FirebaseKit/assets/72887609/579eba0d-f055-4162-b47d-e46cfa702a12">
<img alt="image" src="https://github.com/Brindha-m/FirebaseKit/assets/72887609/fc6d294a-fcdd-43de-9f40-468c405e5724">


### Using XML (R.xml file):

1. Create an XML file in the `res/xml` directory, e.g., `remote_config_defaults.xml`:

   ```xml
   <!-- res/xml/remote_config_defaults.xml -->
   <defaultsMap>
       <entry>
           <key>your_parameter_key</key>
           <value>default_value</value>
       </entry>
   </defaultsMap>
   ```

2. In your Kotlin code, set the default values using the resource ID:

   ```kotlin
   val defaultResId = R.xml.remote_config_defaults
   firebaseRemoteConfig.setDefaultsAsync(defaultResId)
   ```

### Directly in Kotlin:

```kotlin
val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
val configSettings = FirebaseRemoteConfigSettings.Builder()
    .setMinimumFetchIntervalInSeconds(3600) // 1 hour
    .build()
firebaseRemoteConfig.setConfigSettingsAsync(configSettings)

// Set default values directly in Kotlin
firebaseRemoteConfig.setDefaultsAsync(
    mapOf(
        "your_parameter_key" to "default_value",
        // Add more entries as needed
    )
)
```


## Contributing

Feel free to contribute to this project by opening issues, providing feedback, or submitting pull requests. Your contributions are highly appreciated.

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)
