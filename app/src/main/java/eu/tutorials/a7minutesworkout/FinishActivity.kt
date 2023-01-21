package eu.tutorials.a7minutesworkout

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import eu.tutorials.a7minutesworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
@InternalCoroutinesApi
class FinishActivity : AppCompatActivity() {

    private var binding:ActivityFinishBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarFinishActivity)

        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

        }

        binding?.toolbarFinishActivity?.setNavigationOnClickListener {
            onBackPressed()
        }



        binding?.btnFinish?.setOnClickListener{
          finish()
        }

        val historyDao=(application as WorkOutApp).db.historyDao()
        addDateToDatabase(historyDao)
    }

private fun addDateToDatabase(historyDao: HistoryDao){
    val c= Calendar.getInstance()
    val dateTime=c.time
    Log.e("Date: ", "" +dateTime)
    val sdf=SimpleDateFormat("dd MMM yyyy HH:mm:ss",Locale.getDefault())

   val date=sdf.format(dateTime)
    Log.e("formatted Date :", "" + date)



    lifecycleScope.launch{
        historyDao.insert(HistoryEntity(date))

        Log.e(
            "Date : ",
            "Added..."
        )
    }
}
}