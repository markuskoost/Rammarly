package com.example.rammarly

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore


class AddMarkerActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val user = FirebaseAuth.getInstance().currentUser
    val uid = user!!.uid
    var markerCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_marker)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val location = LatLng(58.417, 22.500)
        val locations = CameraUpdateFactory.newLatLngZoom(location, 8F)
        mMap.animateCamera(locations)
        mMap.setOnMapClickListener { latLng ->
            if (markerCount < 1) {
                markerCount += 1
                val markerOptions = MarkerOptions()
                markerOptions.position(latLng)
                // markerOptions.title(latLng.latitude.toString() + " : " + latLng.longitude)
                googleMap.clear()
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                googleMap.addMarker(markerOptions)

                FirebaseFirestore.getInstance().collection("Markers").document().set(latLng)
            } else {
                Toast.makeText(
                    applicationContext, "Ainult ühe markeri võib lisada",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_add_marker, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.getItemId()

        if (id == R.id.check_icon) {
            val intent = Intent(this@AddMarkerActivity, MapsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)

    }
}
