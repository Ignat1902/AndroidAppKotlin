package VoxTech.com

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.net.URL

class currency_exchange_rate : AppCompatActivity() {

    var tv1_in: TextView? = null
    var tv2_in: TextView? = null
    var tv3_in: TextView? = null
    var tv4_in: TextView? = null
    var tv6_in: TextView? = null

    var tv1_out: TextView? = null
    var tv2_out: TextView? = null
    var tv3_out: TextView? = null
    var tv4_out: TextView? = null
    var tv6_out: TextView? = null

    var time: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_exchange_rate)

        tv1_in = findViewById(R.id.tv_BTC)
        tv2_in = findViewById(R.id.tv_ETH)
        tv3_in = findViewById(R.id.tv_XRP)
        tv4_in = findViewById(R.id.tv_BCH)
        tv6_in = findViewById(R.id.tv_LTC)


        tv1_out = findViewById(R.id.tv_rate_BTC)
        tv2_out = findViewById(R.id.tv_rate_ETH)
        tv3_out = findViewById(R.id.tv_rate_XRP)
        tv4_out = findViewById(R.id.tv_rate_BCC)
        tv6_out = findViewById(R.id.tv_rate_LTC)

        time = findViewById(R.id.time_crip)

        fun async( textView_in: TextView?, textView_out: TextView?) {
            doAsync { applicationContext
                val api_key = "VYTUVQDCRZWUO86H"
                var name_rate = textView_in?.text.toString()
                var api = "https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=$name_rate&to_currency=RUB&apikey=$api_key"

                val apiResponse = URL(api).readText()
                val valuta = JSONObject(apiResponse).getJSONObject("Realtime Currency Exchange Rate")
                var value = valuta.getString("5. Exchange Rate").toDouble()
                var ask_price = valuta.getString("9. Ask Price").toDouble()
                var times = valuta.getString("6. Last Refreshed")

                uiThread {
                    time?.setText("Время последнего обновления: "+times)
                    if (value>ask_price){
                        textView_out?.setText("%.2f".format(value)+" ₽ ↑")
                        textView_out?.setTextColor(Color.GREEN)}
                    else{
                        textView_out?.setText("%.2f".format(value)+" ₽ ↓")
                        textView_out?.setTextColor(Color.RED)
                }
            }
        }
    }
        async(tv1_in,tv1_out)
        async(tv2_in,tv2_out)
        async(tv3_in,tv3_out)
        async(tv4_in,tv4_out)
        async(tv6_in,tv6_out)
}
}
