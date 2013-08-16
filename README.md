LocationLibrary
===============

This Library uses LocationClient from google-play-services

You need to add this in your AndroidMenfest.xml

<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

<receiver
android:name="com.LocationLibrary.locations.receiver.LocationReceived"
android:exported="true" >
</receiver>
