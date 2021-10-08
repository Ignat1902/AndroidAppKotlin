package VoxTech.com

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.net.URL

class metals_exchange_rate : AppCompatActivity() {
    var tv1_in: TextView? = null
    var tv2_in: TextView? = null
    var tv3_in: TextView? = null
    var tv4_in: TextView? = null
    var tv5_in: TextView? = null
    var tv8_in: TextView? = null
    var tv9_in: TextView? = null
    var tv10_in: TextView? = null
    var tv11_in: TextView? = null

    var tv1_out: TextView? = null
    var tv2_out: TextView? = null
    var tv3_out: TextView? = null
    var tv4_out: TextView? = null
    var tv5_out: TextView? = null
    var tv8_out: TextView? = null
    var tv9_out: TextView? = null
    var tv10_out: TextView? = null
    var tv11_out: TextView? = null

    var tv_time_one: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_metals_exchange_rate)

        tv1_in = findViewById(R.id.tv_AU)
        tv2_in = findViewById(R.id.tv_AG)
        tv3_in = findViewById(R.id.tv_PT)
        tv4_in = findViewById(R.id.tv_PD)
        tv5_in = findViewById(R.id.tv_CU)
        tv8_in = findViewById(R.id.tv_ALU)
        tv9_in = findViewById(R.id.tv_NI)
        tv10_in = findViewById(R.id.tv_ZNC)
        tv11_in = findViewById(R.id.tv_TIN)


        tv1_out = findViewById(R.id.tv_rate_AU)
        tv2_out = findViewById(R.id.tv_rate_AG)
        tv3_out = findViewById(R.id.tv_rate_PT)
        tv4_out = findViewById(R.id.tv_rate_PD)
        tv5_out = findViewById(R.id.tv_rate_CU)
        tv8_out = findViewById(R.id.tv_rate_ALU)
        tv9_out = findViewById(R.id.tv_rate_NI)
        tv10_out = findViewById(R.id.tv_rate_ZNC)
        tv11_out = findViewById(R.id.tv_rate_TIN)

        tv_time_one = findViewById(R.id.time_metal)

        val url_adress = "https://metals-api.com/api/latest?access_key=1a6c6tj8u52509ghoobgamq51atk1e7714x520fj8ff92o99b4zitd4obcxr&base=RUB&symbols=XAU%2CXAG%2CXPT%2CXPD%2CXCU%2CXRH%2CRUTH%2CALU%2CNI%2CZNC%2CTIN"

        doAsync { applicationContext
            val apiResponse = URL(url_adress).readText()
            val date = JSONObject(apiResponse).getString("date")
            uiThread {
                tv_time_one?.setText("Время: " + date)
            }
        }

        fun async( textView_in: TextView?, textView_out: TextView?) {
            doAsync { applicationContext
                val apiResponse = URL(url_adress).readText()
                val valuta = JSONObject(apiResponse).getJSONObject("rates")
                val value = valuta.getString(textView_in?.text.toString()).toDouble()

                uiThread {
                    var result = value
                    textView_out?.setText("%.2f".format(result)+" ₽")
                }
            }
        }

        async(tv1_in,tv1_out)
        async(tv2_in,tv2_out)
        async(tv3_in,tv3_out)
        async(tv4_in,tv4_out)
        async(tv5_in,tv5_out)
        async(tv8_in,tv8_out)
        async(tv9_in,tv9_out)
        async(tv10_in,tv10_out)
        async(tv11_in,tv11_out)

    }
}