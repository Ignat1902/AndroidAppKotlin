package VoxTech.com

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

 class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getSupportActionBar()?.setElevation(0F);

        val AnimScale:Animation = AnimationUtils.loadAnimation(this,R.anim.scale)
        val btn: Button = findViewById(R.id.convert_btn)
        btn.setOnClickListener(View.OnClickListener { view -> view.startAnimation(AnimScale) })

        val btn_kurs: Button = findViewById(R.id.currency_rate_btn)
        val btn_metal: Button = findViewById(R.id.metal_rate_btn)
        var btn_crip: Button = findViewById(R.id.crip_rate_btn)

        btn.setOnClickListener{
            val active = Intent(this,Convertation::class.java)
            startActivity(active)
        }

        btn_kurs.setOnClickListener{
            val activ = Intent(this,Exchange_rates::class.java)
            startActivity(activ)
        }

        btn_metal.setOnClickListener{
            val activ = Intent(this,metals_exchange_rate::class.java)
            startActivity(activ)
        }

        btn_crip.setOnClickListener{
            val activ = Intent(this,currency_exchange_rate::class.java)
            startActivity(activ)
        }

    }
}