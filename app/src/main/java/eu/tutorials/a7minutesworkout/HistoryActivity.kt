package eu.tutorials.a7minutesworkout

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import eu.tutorials.a7minutesworkout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
@InternalCoroutinesApi
class HistoryActivity : AppCompatActivity() {
  private var binding:ActivityHistoryBinding?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarHistoryActivity)


        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "HISTORY"

        }


        binding?.toolbarHistoryActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
        val historyDao=(application as WorkOutApp).db.historyDao()
        getallCompletedDates(historyDao)


    }

    private fun getallCompletedDates(historyDao: HistoryDao){
        lifecycleScope.launch {
            historyDao.fetchAllDates().collect(){ allCompletedDatesList ->
                if(allCompletedDatesList.isNotEmpty()) {
                    binding?.tvHistory?.visibility = View.VISIBLE
                    binding?.rvHistory?.visibility = View.VISIBLE
                    binding?.tvNoDataAvailable?.visibility = View.INVISIBLE

                    binding?.rvHistory?.layoutManager=LinearLayoutManager(this@HistoryActivity)

                    val dates=ArrayList<String>()

                    for(date in allCompletedDatesList){
                        dates.add(date.date)
                    }

                    val  historyAdapter=HistoryAdapter(dates)
                    binding?.rvHistory?.adapter=historyAdapter
                }
                else{
                    binding?.tvHistory?.visibility = View.GONE
                    binding?.rvHistory?.visibility = View.GONE
                    binding?.tvNoDataAvailable?.visibility = View.VISIBLE
                }


            }


        }
    }


    override fun onDestroy() {
        super.onDestroy()

        binding=null
    }
}