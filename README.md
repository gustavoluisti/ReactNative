<div align="center">
  
  [<img width="400px" src="/resources/combateafraude_logo.png?raw=true">](https://combateafraude.com)

  # Combate à Fraude - React Native
</div>

<hr>

Welcome to the Combate a Fraude's React Native repository! In this page you'll learn how to integrate your project with the [Android SDKs](https://github.com/combateafraude/Android) in React Native.

Special thanks to @felansu for this tutorial :green_heart:

1. Create the files `CombateAFraudeModule.java` and `CombateAFraudePackage.java` in path `<project_folder>/android/app/src/main/java/com/combateafraudereact/combateAFraude`.
- The `getName` function will be used for call in react side.
- The methods with `@ReactMethod` annotation will be used for call in react side.

2. Add the created package in main application: 

``` java
import com.combateafraudereact.combateAFraude.CombateAFraudePackage;

private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
    ...
    packages.add(new CombateAFraudePackage());
    ...
}
```

3. Add dependencies in `app/build.gradle`:

``` gradle
dependencies {
    ...
    implementation 'com.combateafraude.sdk:<sdk_name>:<sdk_version>'
    ...
}
```

4. Add maven dependencies `app/build.gradle` and minSdk config to 21

``` gradle
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
         // Combate à Fraude
         url "https://raw.githubusercontent.com/combateafraude/Android/releases"
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

5. Change `allowBackup` to true in application tag in `AndroidManifest.xml`:

``` xml

<application
     android:allowBackup="true">
  
```


6. Refresh the dependencies running:

``` bash
cd android
./gradlew clean bundleRelease
./gradlew build --refresh-dependencies
```

Note: be safe you are running java 1.8. If not, run the command:

``` bash
export JAVA_HOME=`/usr/libexec/java_home -v 1.8`
```

7. Run the project:

``` bash
npm install
npm run android
```
