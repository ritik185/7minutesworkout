package eu.tutorials.a7minutesworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import eu.tutorials.a7minutesworkout.databinding.ActivityExerciseBinding
import eu.tutorials.a7minutesworkout.databinding.DialogCustomBackConfirmationBinding
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.*
@InternalCoroutinesApi
class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding:ActivityExerciseBinding?=null
    private var restTimer:CountDownTimer?=null
    private var restProgress=0
    private var exercisetimer:CountDownTimer?=null
    private var exerciseprogress=0

    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var currentExercisePosition=-1

    private var player:MediaPlayer?=null

    private var tts:TextToSpeech?=null
    private var exerciseAdapter:ExerciseStatusAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

        }

        exerciseList=Constants.defaultExerciseList()
        tts= TextToSpeech(this,this)

        binding?.toolbarExercise?.setNavigationOnClickListener {
            customDialogForBackButton()

        }
        setupRestView()
        setupExerciseStatusRecyclerView()
    }

  private fun  customDialogForBackButton(){
               val customDialog= Dialog(this)
                val dialogBinding=DialogCustomBackConfirmationBinding.inflate(layoutInflater)
                customDialog.setContentView(dialogBinding.root)
                 customDialog.setCanceledOnTouchOutside(false)
           dialogBinding.btnYes.setOnClickListener {
              this@ExerciseActivity.finish()
               customDialog.dismiss()
           }

      dialogBinding.btnNo.setOnClickListener {
          customDialog.dismiss()
      }

      customDialog.show()


    }

    private fun setupExerciseStatusRecyclerView(){

           binding?.rvExerciseStatus?.layoutManager=

               LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exerciseAdapter= ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter=exerciseAdapter

    }

    @InternalCoroutinesApi
    private fun setupRestView(){

        try {
            val soundURI= Uri.parse(
                "android.resource://eu.tutorials.a7minutesworkout/"+ R.raw.press_start)
            player=MediaPlayer.create(applicationContext,soundURI)
            player?.isLooping=false
            player?.start()
        }catch (e:Exception){
            e.printStackTrace()
        }

        binding?.flRestView?.visibility=View.VISIBLE
        binding?.tvTitle?.visibility= View.VISIBLE
        binding?.tvExercise?.visibility=View.INVISIBLE
        binding?.flExerciseView?.visibility=View.INVISIBLE
        binding?.ivImage?.visibility=View.INVISIBLE
        binding?.tvUpcomingLevel?.visibility=View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility=View.VISIBLE
        if(restTimer!=null){
            restTimer?.cancel()
            restProgress=0
        }

        speakOut("Take a rest")

        binding?.tvUpcomingExerciseName?.text=exerciseList!![currentExercisePosition+1].getname()

        setRestProgressBar()
    }

    @InternalCoroutinesApi
    private fun setupExerciseView(){
        binding?.flRestView?.visibility=View.INVISIBLE
        binding?.tvTitle?.visibility= View.INVISIBLE
        binding?.tvExercise?.visibility=View.VISIBLE
        binding?.flExerciseView?.visibility=View.VISIBLE
        binding?.ivImage?.visibility=View.VISIBLE
        binding?.tvUpcomingLevel?.visibility=View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility=View.INVISIBLE
        if(exercisetimer!=null){
            exercisetimer?.cancel()
            exerciseprogress=0

        }

            speakOut(exerciseList!![currentExercisePosition].getname())

        binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.tvExercise?.text=(exerciseList!![currentExercisePosition].getname())

        setExerciseProgressBar()
    }

    @InternalCoroutinesApi
    private fun setRestProgressBar(){
        binding?.ProgressBar?.progress=restProgress
        restTimer = object :CountDownTimer(10000,1000) {
            override fun onTick(p0: Long) {
                restProgress++
                binding?.ProgressBar?.progress=10-restProgress
                binding?.tvTimer?.text=(10-restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsselected(true)
                exerciseAdapter!!.notifyDataSetChanged()
               setupExerciseView()
            }


        }.start()
    }

    @InternalCoroutinesApi
    private fun setExerciseProgressBar(){
        binding?.ProgressBarExercise?.progress=exerciseprogress
        exercisetimer = object :CountDownTimer(30000,1000) {
            override fun onTick(p0: Long) {
                exerciseprogress++
                binding?.ProgressBarExercise?.progress=30-exerciseprogress
                binding?.tvTimerExercise?.text=(30-exerciseprogress).toString()
            }

            override fun onFinish() {
                exerciseList!![currentExercisePosition].setIsselected(false)
                exerciseList!![currentExercisePosition].setIsCompleted(true)
                exerciseAdapter!!.notifyDataSetChanged()

                if (currentExercisePosition < exerciseList?.size!! - 1) {
                    setupRestView()
                } else {
                    finish()
                   val intent=Intent(this@ExerciseActivity,FinishActivity::class.java)
                    startActivity(intent)
                }
            }

        }.start()
    }


    override fun onDestroy() {
        super.onDestroy()

        if(restTimer!=null){
            restTimer?.cancel()
            restProgress=0
        }

        if(tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }

        if(player!=null){
            player!!.stop()
        }

        binding=null
    }

    override fun onInit(status: Int) {
           if(status==TextToSpeech.SUCCESS){
               val result=tts?.setLanguage(Locale.ENGLISH)
               if(result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED){
                   Log.e("TTS","THE LANGUAGE SPECIFIED IS NOT SUPPORTED")
               }


           }else{
               Log.e("TTS","Initialization Failed!")
           }
    }

    private fun speakOut(text:String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

}