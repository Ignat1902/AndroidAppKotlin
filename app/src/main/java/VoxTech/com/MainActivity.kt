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

        val AnimScale:Animation = AnimationUtils.loadAnimation(this,R.anim.scale)
        val btn: Button = findViewById(R.id.convert_btn)
        btn.setOnClickListener(View.OnClickListener { view -> view.startAnimation(AnimScale) })

        btn.setOnClickListener{
            val active = Intent(this,Convertation::class.java)
            startActivity(active)
        }

    }
}