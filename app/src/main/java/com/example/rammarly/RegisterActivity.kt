package com.example.rammarly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private val TAG = "CreateAccountActivity"
    //global variables
    private var email: String? = null
    private var password: String? = null
    private var password2: String? = null
    //UI elements
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var etPassword2: EditText? = null
    private var btnCreateAccount: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initialise()
    }

    private fun initialise() {
        etEmail = findViewById<View>(R.id.et_email_re) as EditText
        etPassword = findViewById<View>(R.id.et_password_re) as EditText
        etPassword2 = findViewById<View>(R.id.et_password_re2) as EditText
        btnCreateAccount = findViewById<View>(R.id.btn_register) as Button
        btnCreateAccount!!.setOnClickListener { createNewAccount() }

    }

    private fun createNewAccount() {
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()
        password2 = etPassword2?.text.toString()
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(password2))
            if (password != password2){
                Toast.makeText(this, "Passwordid ei ole samad!", Toast.LENGTH_SHORT).show()}
            else {
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        //Verify Email
                        verifyEmail()
                        updateUserInfoAndUI()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            this@RegisterActivity, "Registreerimine ebaõnnestunud.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

        } else {
            Toast.makeText(this, "Sisesta kõik andmed!", Toast.LENGTH_SHORT).show()


        }
    }

    private fun updateUserInfoAndUI() {
        //start next activity
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun verifyEmail() {
        val mUser = FirebaseAuth.getInstance().currentUser
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Kinnitus Email saadetud " + mUser.getEmail(),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    Toast.makeText(
                        this@RegisterActivity,
                        "Kinnitus emaili saatmine ebaõnnestus.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}