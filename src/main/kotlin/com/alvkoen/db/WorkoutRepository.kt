package com.alvkoen.db

import com.alvkoen.model.Workout
import java.util.*
import org.jooq.DSLContext


class WorkoutRepository(private val dslContext: DSLContext) {
	fun getWorkouts(): List<Workout> {
		TODO("Not yet implemented")
	}

	fun getById(id: UUID): Workout {
		TODO("Not yet implemented")
	}

	fun addWorkout(workout: Workout): Workout {
		TODO("Not yet implemented")
	}

	fun deleteWorkout(id: UUID): Workout {
		TODO("Not yet implemented")
	}

}