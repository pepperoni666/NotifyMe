package com.pepperoni.android.notifyme.mainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.pepperoni.android.notifyme.NotifyMeApplication
import com.pepperoni.android.notifyme.R
import com.pepperoni.android.notifyme.mainActivity.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val model: MainActivityViewModel by viewModels()

    private lateinit var app: NotifyMeApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        app = application as NotifyMeApplication
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = app.auth.currentUser
        //updateUI(currentUser)
    }

    override fun onResume() {
        super.onResume()
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        if(googleApiAvailability.isGooglePlayServicesAvailable(applicationContext) != ConnectionResult.SUCCESS){
            GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this)
        }

        app.auth.signInAnonymously()
            .addOnCompleteListener(this) { authTask ->
                if (authTask.isSuccessful) {
                    FirebaseInstanceId.getInstance().instanceId
                        .addOnCompleteListener { idTask ->
                            if (idTask.isSuccessful) {
                                idTask.result?.token?.let {
                                    FirebaseFirestore.getInstance().collection("receivers").document(it).set(
                                        hashMapOf(
                                            "receiverId" to it
                                        )).addOnSuccessListener {
                                        Toast.makeText(baseContext, "Authentication success.",
                                            Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }

                            val token = idTask.result?.token

                            main_text.text = SpannableStringBuilder(token)
                        }
                    val user = app.auth.currentUser
                } else {

                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}
