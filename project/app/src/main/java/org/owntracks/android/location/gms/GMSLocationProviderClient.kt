package org.owntracks.android.location.gms

import android.annotation.SuppressLint
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import org.owntracks.android.location.LocationCallback
import org.owntracks.android.location.LocationProviderClient
import org.owntracks.android.location.LocationRequest

class GMSLocationProviderClient(private val fusedLocationProviderClient: FusedLocationProviderClient) : LocationProviderClient {
    private val callbackMap = mutableMapOf<LocationCallback, com.google.android.gms.location.LocationCallback>()


    override fun requestLocationUpdates(locationRequest: LocationRequest, clientCallBack: LocationCallback) {
        requestLocationUpdates(locationRequest, clientCallBack, null)
    }

    @SuppressLint("MissingPermission")
    override fun requestLocationUpdates(locationRequest: LocationRequest, clientCallBack: LocationCallback, looper: Looper?) {
        val gmsCallBack = com.google.android.gms.location.LocationCallback()
        callbackMap[clientCallBack] = gmsCallBack
        fusedLocationProviderClient.requestLocationUpdates(locationRequest.toGMSLocationRequest(), gmsCallBack, looper)
    }

    override fun removeLocationUpdates(clientCallBack: LocationCallback) {
        fusedLocationProviderClient.removeLocationUpdates(callbackMap[clientCallBack])
    }

    override fun flushLocations() {
        fusedLocationProviderClient.flushLocations();
    }

}