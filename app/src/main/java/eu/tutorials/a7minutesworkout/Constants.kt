package eu.tutorials.a7minutesworkout

object Constants {
    fun defaultExerciseList():ArrayList<ExerciseModel>{
      val exerciseList=ArrayList<ExerciseModel>()
        val jumpingjacks=ExerciseModel(
            1,"Jumping Jack",
            R.drawable.ic_jumping_jacks,
            false,
        false


        )
        exerciseList.add(jumpingjacks)

        val wallsit=ExerciseModel(
            2,"Wall Sit",
            R.drawable.ic_wall_sit,
            false,
            false


        )
        exerciseList.add(wallsit)

        val plank=ExerciseModel(
            3,"Plank",
            R.drawable.ic_plank,
            false,
            false


        )
        exerciseList.add(plank)


        val lunge=ExerciseModel(
            4,"Lunge",
            R.drawable.ic_lunge,
            false,
            false


        )
        exerciseList.add(lunge)


        val pushup=ExerciseModel(
            5,"Push up",
            R.drawable.ic_push_up,
            false,
            false


        )
        exerciseList.add(pushup)


        val pushupandrotation=ExerciseModel(
            6,"Push up and Rotation",
            R.drawable.ic_push_up_and_rotation,
            false,
            false


        )
        exerciseList.add(pushupandrotation)


        val sideplank=ExerciseModel(
            7,"Side Plank",
            R.drawable.ic_side_plank,
            false,
            false


        )
        exerciseList.add(sideplank)


        val squat=ExerciseModel(
            8,"Squat",
            R.drawable.ic_squat,
            false,
            false


        )
        exerciseList.add(squat)


        val stepupchair=ExerciseModel(
            9,"Step up onto chair",
            R.drawable.ic_step_up_onto_chair,
            false,
            false


        )
        exerciseList.add(stepupchair)

        val tricepsdip=ExerciseModel(
            10,"Triceps dip on chair",
            R.drawable.ic_triceps_dip_on_chair,
            false,
            false


        )
        exerciseList.add(tricepsdip)


        val abdominal=ExerciseModel(
            11,"Abdominal crunch",
            R.drawable.ic_abdominal_crunch,
            false,
            false


        )
        exerciseList.add(abdominal)


        val highknees=ExerciseModel(
            12,"High Knees",
            R.drawable.ic_high_knees_running_in_place,
            false,
            false


        )
        exerciseList.add(highknees)




return exerciseList

    }


}