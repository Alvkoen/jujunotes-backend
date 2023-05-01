package com.alvkoen.db

import com.alvkoen.model.Exercise
import java.util.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

fun getExercisesAndSets(parentId: UUID): List<Exercise> {
	return transaction {
		Exercises
			.select { Exercises.parentId eq parentId }
			.mapNotNull { exerciseRow ->
				val exerciseId = exerciseRow[Exercises.id]

				val sets = Sets
					.select { Sets.exerciseId eq exerciseId }
					.mapNotNull { setRow ->
						Sets.toSet(setRow)
					}
				Exercises.toExercise(exerciseRow).copy(sets = sets)
			}
	}
}