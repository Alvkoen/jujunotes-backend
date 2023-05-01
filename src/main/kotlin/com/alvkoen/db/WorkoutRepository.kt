package com.alvkoen.db

import com.alvkoen.model.Workout
import java.util.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction


class WorkoutRepository {
	fun getWorkouts(): List<Workout> {
		return transaction {
			Workouts.selectAll().mapNotNull { workoutRow ->
				val workoutId = workoutRow[Workouts.id]
				val exercises = getExercisesAndSets(workoutId.value)
				Workouts.toWorkout(workoutRow).copy(exercises = exercises)
			}
		}
	}

	fun getById(id: UUID): Workout? {
		return transaction {
			val workoutRow = Workouts.select { Workouts.id eq id }.singleOrNull() ?: return@transaction null
			val exercises = getExercisesAndSets(id)
			Workouts.toWorkout(workoutRow).copy(exercises = exercises)
		}
	}

	fun addWorkout(workout: Workout): UUID {
		return transaction {
			val workoutId = Workouts.insertAndGetId { row ->
				row[id] = workout.id
				row[name] = workout.name
				row[date] = workout.date
				row[isCompleted] = workout.isCompleted
			}

			workout.exercises.forEach { exercise ->
				val exId = Exercises.insertAndGetId { row ->
					row[id] = exercise.id
					row[name] = exercise.name
					row[order] = exercise.order
					row[isSuperSet] = exercise.isSuperSet
					row[parentId] = workoutId.value
				}

				exercise.sets.forEach { set ->
					Sets.insert {
						it[id] = set.id
						it[name] = set.name
						it[reps] = set.reps
						it[weight] = set.weight
						it[order] = set.order
						it[exerciseId] = exId
					}
				}
			}
			workoutId.value
		}
	}

	fun deleteWorkout(id: UUID): Boolean {
		return transaction {
			Exercises.select { Exercises.parentId eq id }
				.forEach { exerciseRow ->
					val exerciseId = exerciseRow[Exercises.id]
					Sets.deleteWhere { Sets.exerciseId eq exerciseId }
				}

			Exercises.deleteWhere { parentId eq id }

			Workouts.deleteWhere { Workouts.id eq id } > 0
		}
	}

}