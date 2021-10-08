package VoxTech.com

import android.graphics.Color
import android.os.Bundle
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.net.URL


class Exchange_rates : AppCompatActivity() {
    var tv1_in: TextView? = null
    var tv2_in: TextView? = null
    var tv3_in: TextView? = null
    var tv4_in: TextView? = null
    var tv5_in: TextView? = null
    var tv6_in: TextView? = null
    var tv7_in: TextView? = null
    var tv8_in: TextView? = null

    var tv1_out: TextView? = null
    var tv2_out: TextView? = null
    var tv3_out: TextView? = null
    var tv4_out: TextView? = null
    var tv5_out: TextView? = null
    var tv6_out: TextView? = null
    var tv7_out: TextView? = null
    var tv8_out: TextView? = null

    var tv_time_one: TextView? = null
    var tv_time_two: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_rates)

        tv1_in = findViewById(R.id.tv_USD)
        tv2_in = findViewById(R.id.tv_EUR)
        tv3_in = findViewById(R.id.tv_GBP)
        tv4_in = findViewById(R.id.tv_CHF)
        tv5_in = findViewById(R.id.tv_JPY)
        tv6_in = findViewById(R.id.tv_UAH)
        tv7_in = findViewById(R.id.tv_CAD)
        tv8_in = findViewById(R.id.tv_PLN)

        tv1_out = findViewById(R.id.tv_rate_USD)
        tv2_out = findViewById(R.id.tv_rate_EUR)
        tv3_out = findViewById(R.id.tv_rate_GBP)
        tv4_out = findViewById(R.id.tv_rate_CHF)
        tv5_out = findViewById(R.id.tv_rate_JPY)
        tv6_out = findViewById(R.id.tv_rate_UAH)
        tv7_out = findViewById(R.id.tv_rate_CAD)
        tv8_out = findViewById(R.id.tv_rate_PLN)

        tv_time_one = findViewById(R.id.time_1)
        tv_time_two = findViewById(R.id.time_2)

        val url_adress = "https://www.cbr-xml-daily.ru/daily_json.js"

        doAsync { applicationContext
            val apiResponse = URL(url_adress).readText()
            val date = JSONObject(apiResponse).getString("Timestamp")
            var PreviousDate = JSONObject(apiResponse).getString("PreviousDate")
            uiThread {
                tv_time_one?.setText("Время: " + date)
                tv_time_two?.setText("Последнее время: "+ PreviousDate)
            }
            }

        fun async( textView_in: TextView?, textView_out: TextView?) {
            doAsync { applicationContext
                val apiResponse = URL(url_adress).readText()
                val valuta = JSONObject(apiResponse).getJSONObject("Valute")
                val nominal = valuta.getJSONObject(textView_in?.text.toString()).getString("Nominal").toInt()
                val value = valuta.getJSONObject(textView_in?.text.toString()).getString("Value").toDouble()
                val value_previous = valuta.getJSONObject(textView_in?.text.toString()).getString("Previous").toDouble()


                uiThread {
                    var result = (value/nominal)
                    //textView_out?.setText("%.3f".format(result)+" ₽↑")
                    if (value>value_previous){
                        textView_out?.setTextColor(Color.GREEN)
                        textView_out?.setText("%.3f".format(result)+" ₽↑")
                    }
                   else {
                       textView_out?.setTextColor(Color.RED)
                        textView_out?.setText("%.3f".format(result)+" ₽↓")
                   }


                }
            }
        }

        async(tv1_in,tv1_out)
        async(tv2_in,tv2_out)
        async(tv3_in,tv3_out)
        async(tv4_in,tv4_out)
        async(tv5_in,tv5_out)
        async(tv6_in,tv6_out)
        async(tv7_in,tv7_out)
        async(tv8_in,tv8_out)


    }
}