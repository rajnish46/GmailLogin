package rajnish.gmaillogin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import rajnish.gmaillogin.databinding.ProfileActivityBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ProfileActivityBinding
    var account: GoogleSignInAccount? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ProfileActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        account = intent.getParcelableExtra<GoogleSignInAccount>("gmailAcc")

        binding.name.text = account?.displayName
        binding.mail.text = account?.email

        println("photoUrl:"+account?.photoUrl)


        Glide.with(baseContext)
            .load(account?.photoUrl)
            .placeholder(resources.getDrawable(R.drawable.ic_launcher_background))
            .into(binding.image);
    }

}