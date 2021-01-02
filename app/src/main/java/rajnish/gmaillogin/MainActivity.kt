package rajnish.gmaillogin

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import rajnish.gmaillogin.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    val RC_SIGN_IN: Int = 1
    private lateinit var binding: ActivityMainBinding
    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signInButton.setSize(SignInButton.SIZE_STANDARD)
        binding.signInButton.setOnClickListener { signIn() }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

    }

    fun signIn() {
//        if (isUserNameValid(binding.username.text.toString())) {
            val signInIntent: Intent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
//        } else {
//            Toast.makeText(
//                baseContext,
//                resources.getString(R.string.invalid_mail_id),
//                Toast.LENGTH_LONG
//            ).show()
//        }
    }

    override fun onStart() {
        super.onStart()
//        val account = GoogleSignIn.getLastSignedInAccount(this)
//          updateUI(account)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            openSecActivity(account)
        } catch (e: ApiException) {
            println("signInResult:failed code=" + e.statusCode)
        }
    }

    private fun openSecActivity(account: GoogleSignInAccount?) {
        if (account != null) {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("gmailAcc", account)
            startActivity(intent)
        }
    }


    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }
}