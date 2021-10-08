package VoxTech.com

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import android.widget.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.net.URL
import android.widget.EditText
import android.text.Editable
import android.widget.TextView



class Convertation : AppCompatActivity() {
    var edt1: EditText? = null
    var tv1: TextView? = null
    var edt2: EditText? = null
    var tv2: TextView? = null
    var sp1: Spinner? = null
    var sp2: Spinner? = null
    var sp3: Spinner? = null
    var sp4: Spinner? = null
    var adapters: CustomAdapter? = null
    var adapters_two: CustomAdapter? = null
    var names = listOf("USD","EUR","GBP","CHF","JPY","UAH","CAD","PLN")
    var names_two = listOf("RUB")
    var images = intArrayOf(R.drawable.usa,R.drawable.eur,R.drawable.gbp,R.drawable.chf,R.drawable.jp,R.drawable.uah,R.drawable.cad,R.drawable.pln)
    var images_two = intArrayOf(R.drawable.rub)

    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_convertation)

        edt1 = findViewById(R.id.edit_fist_value)
        tv1 = findViewById(R.id.edit_last_value)

        sp1  = findViewById(R.id.spinner_fist_currency) //верхнее выпадающее меню
        sp2 = findViewById(R.id.spinner_last_currency) //нижнее выпадаюшее меню

        adapters = CustomAdapter(this, names.toTypedArray(), images) //создание объекта класса кастомного адаптера для выпадающих меню
        adapters_two = CustomAdapter(this, names_two.toTypedArray(), images_two)
        sp1?.adapter = adapters //адаптер для первого вып.меню
        sp2?.adapter = adapters_two //адаптер для второго вып.меню

        edt2 = findViewById(R.id.edit_fist_value2)
        tv2 = findViewById(R.id.edit_last_value2)

        sp3 = findViewById(R.id.spinner_fist_currency2)
        sp4 = findViewById(R.id.spinner_last_currency2)
        sp3?.adapter = adapters_two
        sp4?.adapter = adapters

        var item_one: String = names[0] //переменная, хранящяя значение 1 вып. меню
        var item_two: String = names_two[0] //переменная, хранящяя значение 2 выпю меню

        var item_four: String = names[0]
        // выше инициализированные переменные нужны для того, чтобы запоминать значение вып.меню при их смене
        sp2?.setEnabled(false)
        sp3?.setEnabled(false)

        val url_adress = "https://www.cbr-xml-daily.ru/daily_json.js"

        fun ValRub (meaning: String, name: String, nominal: String):Double{
            return (name.toDouble()*meaning.toDouble())/nominal.toInt()

        }

        fun RubVal (meaning: String, name: String, nominal: String):Double{
            return (nominal.toInt()*meaning.toDouble())/name.toDouble()

        }

        fun async(position:Int, spinner: Spinner?,textView: TextView,editText: EditText) {
            doAsync { applicationContext
                val apiResponse = URL(url_adress).readText()
                val valuta = JSONObject(apiResponse).getJSONObject("Valute")
                val nominal = valuta.getJSONObject(spinner?.getItemAtPosition(position) as String).getString("Nominal")
                val name = valuta.getJSONObject(spinner?.getItemAtPosition(position) as String).getString("Value")

                uiThread {
                    if(spinner == sp1){textView?.setText(String.format("%.3f", ValRub(editText?.getText().toString(),name,nominal)))}
                    else {textView?.setText(String.format("%.3f", RubVal(editText?.getText().toString(),name,nominal)))}
                }
            }
        }

       // событие, при нажатии на вып меню и выбора валюты
        sp1?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, l: Long) {

                if (adapterView != null) {
                            //Perform Cod
                                async(position,sp1,tv1!!,edt1!!)
                    if (adapterView.getItemAtPosition(position) == item_two ) {
                        sp2?.setSelection(names.indexOf(item_one))
                        item_two=item_one
                    }
                    item_one = names[position]
                }

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        edt1?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (!s.isEmpty()) {
                    async(names.indexOf(item_one),sp1,tv1!!,edt1!!)
                }
                else{
                    edt1?.setHint("0")
                    tv1?.setText("0.00")
                    edt1?.setSelection(0)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }
        })


        //обработка 3 спинера , в котором будет RUB
        sp4?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, l: Long) {

                if (adapterView != null) {
                    async(position,sp4,tv2!!,edt2!!)
                    item_four = names[position]
                }

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        //обработка 2 edit text
        edt2?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (!s.isEmpty()) {
                    async(names.indexOf(item_four),sp4,tv2!!,edt2!!)
                }
                else{
                    edt2?.setHint("0")
                    tv2?.setText("0.00")
                    edt2?.setSelection(0)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }
        })

    }
}
