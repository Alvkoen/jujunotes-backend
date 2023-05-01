package com.alvkoen.model

import java.time.LocalDateTime
import java.util.UUID

sealed class ActivityResult<out T> {
	data class Success<T>(val value: T) : ActivityResult<T>()
	data class Failure(val reason: String, val exception: Exception? = null) : ActivityResult<Nothing>()
}

sealed class ActivityListResult<out T> {
	data class Success<T>(val values: List<T>) : ActivityListResult<T>()
	data class Failure(val reason: String, val exception: Exception? = null) : ActivityListResult<Nothing>()
}

data class Set(
	val id: UUID,
	val name: String,
	val reps: Int,
	val weight: Double,
	val order: Int
)

data class Exercise(
	val id: UUID,
	val name: String,
	val order: Int,
	val sets: List<Set>,
	val isSuperSet: Boolean
)

open class Template(
	open val id: UUID,
	open val name: String,
	open val exercises: List<Exercise>,
)

class Workout(
	override val id: UUID,
	override val name: String,
	override val exercises: List<Exercise>,
	val date: LocalDateTime,
	val isCompleted: Boolean
) : Template(id, name, exercises) {
	fun copy(
		id: UUID = this.id,
		name: String = this.name,
		exercises: List<Exercise> = this.exercises,
		date: LocalDateTime = this.date,
		isCompleted: Boolean = this.isCompleted
	): Workout {
		return Workout(id, name, exercises, date, isCompleted)
	}
}
