LocationLibrary
===============

This Library uses LocationClient from google-play-services

You need to add Permission and receiver in menifest file in your AndroidMenfest.xml

Please Check <b>ReadMe</b> file for more info.

<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

<receiver
android:name="com.LocationLibrary.locations.receiver.LocationReceived"
android:exported="true" >
</receiver>

![Alt Text](https://media.giphy.com/media/vFKqnCdLPNOKc/giphy.gif)
