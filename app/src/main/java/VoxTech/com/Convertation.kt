package VoxTech.com

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Convertation : AppCompatActivity() {
    var swap: FloatingActionButton? = null
    var sp1: Spinner? = null
    var sp2: Spinner? = null
    var adapters: CustomAdapter? = null
    var names = listOf("RUB","USD","EUR","GBP","CHF","JPY","UAH","CAD","PLN")
    var images = intArrayOf(R.drawable.rub,R.drawable.usa,R.drawable.eur,R.drawable.gbp,R.drawable.chf,R.drawable.jp,R.drawable.uah,R.drawable.cad,R.drawable.pln)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_convertation)

        swap = findViewById(R.id.swapButton)
        sp1  = findViewById(R.id.spinner_fist_currency)
        sp2 = findViewById(R.id.spinner_last_currency)

        adapters = CustomAdapter(this, names.toTypedArray(), images)
        sp1?.adapter = adapters
        sp2?.adapter = adapters

        var item_one: String = names[1]
        var item_two: String = names[0]

        sp1?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, l: Long) {
                if (adapterView != null) {
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

        sp2?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, l: Long) {
                if (adapterView != null) {
                    if (adapterView.getItemAtPosition(position) == item_one ) {
                        sp1?.setSelection(names.indexOf(item_two))
                        item_one=item_two
                    }
                    item_two = names[position]
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        swap?.setOnClickListener{
            sp1?.setSelection(names.indexOf(item_two))
            sp2?.setSelection(names.indexOf(item_one))
        }

    }
}
