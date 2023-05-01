package com.alvkoen

import com.alvkoen.model.Exercise
import com.alvkoen.model.Workout
import com.alvkoen.model.Set
import java.time.LocalDateTime
import java.util.*

fun testWorkout() = Workout(
	id = UUID.randomUUID(),
	name = "Test Workout",
	exercises = listOf(
		Exercise(
			id = UUID.randomUUID(),
			name = "Test Exercise",
			order = 1,
			sets = listOf(
				Set(
					id = UUID.randomUUID(),
					name = "Test Set",
					reps = 10,
					weight = 100.0,
					order = 1
				)
			),
			isSuperSet = false
		)
	),
	date = LocalDateTime.now(),
	isCompleted = false
)