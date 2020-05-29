That example show how integrate SDK with react native.

Steps for make integration:

1. Create module and package files in path **android/app/src/main/java/com/combateafraudereact/combateAFraude**:
```
CombateAFraudeModule.java
CombateAFraudePackage.java
```

- The **getName** function will be used for call in react side.
- The methods with **@ReactMethod** annotation will be used for call in react side.


2. Added package in main application **MainApplication.java**: 
```
import com.combateafraudereact.combateAFraude.CombateAFraudePackage;

private final ReactNativeHost mReactNativeHost =
      new ReactNativeHost(this) {
    ...
    packages.add(new CombateAFraudePackage());
    ...
}
```

3. Add dependencies in **app/build.gradle**
```
dependencies {
    ...
    implementation 'com.combateafraude.sdk:passive-face-liveness:1.0.0-beta11'
    ...
}
```

4. Add maven dependencies **app/build.gradle** and minSdk to 21
```
buildscript {
    ...
    ext {
        ...
        minSdkVersion = 21
        ...
    }
    ...
}

allprojects {
    repositories {
     ...
     maven {
         // Combate a fraude
         url "https://raw.githubusercontent.com/combateafraude/Mobile/android-releases"
     }
     ...
    }
}

aaptOptions {
    noCompress "tflite"
}

dataBinding {
    enabled = true
}

```

5. Change allowBackup to true in **AndroidManifest.xml** <application android:allowBackup="true">


6. Refresh dependencies running:
```
cd android
./gradlew clean bundleRelease
./gradlew build --refresh-dependencies
```
> Note: be safe you are running java 1.8, that resolve my problem:
>
> ```export JAVA_HOME=`/usr/libexec/java_home -v 1.8```


Run:

```
npm install
npm run android
```
