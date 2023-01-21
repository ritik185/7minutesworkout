package eu.tutorials.a7minutesworkout

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import eu.tutorials.a7minutesworkout.databinding.ActivityMainBinding
import kotlinx.coroutines.InternalCoroutinesApi
@InternalCoroutinesApi
class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        //val flStartButton:FrameLayout =findViewById(R.id.flStart)

        binding?.flStart?.setOnClickListener {
           val intent= Intent(this,ExerciseActivity::class.java)
            startActivity(intent)
        }

        binding?.flBMI?.setOnClickListener {
            val intent= Intent(this,BMIActivity::class.java)
            startActivity(intent)
        }

        binding?.flHistory?.setOnClickListener {
            val intent= Intent(this,HistoryActivity::class.java)
            startActivity(intent)
        }


    }
    override fun onDestroy(){
        super.onDestroy()
        binding=null
    }
}