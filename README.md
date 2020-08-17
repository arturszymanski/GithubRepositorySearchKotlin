# Github Repository Search Kotlin
This repo contains solution for task given bellow

## Task requirements:
 - Create a simple Android phone application in Kotlin that utilises GitHub APIs.
 - Minimum two screens are required List of repository and repository details.
 - Repository list screen should allow to search repository by name and display some basic information about repo.
 - Repository details view should display number of open ssues, forks, watchers and content of repository readme if exists.
 - The application should utilise best practices for the Android 7+ (API 24) and Kotlin.
 - 3rd party liblaries should be limited to minimum.

## Project setup:
Project do not require a lot of special configuration, the only thing that is neede to configure is keystore for release builds:
 - Generate keystore file and put it in eg. <projectfolder>/keystore/test_keystore.jks
 - Edit Your local properties and add there information like bellow:
```
key.alias=<key_alias>
key.password=<key_passwords>
store.file=<keystore_path>
store.password=<keystore_password>
```

## Used liblaries
### Moshi
Common lib that allow to parse json files
 - Moshi Kotlion
 - Moshi Adapters
 - Moshi converter
### Rx
Lib that allow reactive programming and simplify code
 - RxKotlin
 - RxAndroid
 - RxJava 2
### OkHttp
Simplify networking, extends posibilities of retrofit
 - OkHttp 3
 - Logging interceptor
### Retrofit
Simplify networking even more
 - Retrofit 2
 - Retrofit converter moshi
 - Retrofit converter scalars
### Timber
Great small logging lib
### Dagger 2
Dependency injection to make testing easier, right now part of official google libs
### Kotlin
Adds posibility to use kotlin language
### AndroidX
Many different android libs that provide's views, navigation and more

## Project Architecture
Project is created using Clean Architecture guidelines and contains 4 modules:
 - App - provides visual representation of project
 - Presentation - Provides logic that controls the view, in application implemented using MVP (Model View Presenter) pattern
 - Domain - Provides business logic of application
 - Data - Provides access to different data sources