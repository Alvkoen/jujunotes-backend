package com.alvkoen.model

import java.time.LocalDateTime
import java.util.UUID

data class Set (
	val id: UUID,
	val name: String,
	val reps: Int,
	val weight: Double,
	val order: Int
)

data class Exercise (
	val id: UUID,
	val name: String,
	val order: Int,
	val sets: List<Set>,
	val isSuperSet: Boolean
)

data class Workout(
	val id: UUID,
	val name: String,
	val date: LocalDateTime,
	val exercises: List<Exercise>,
	val isCompleted: Boolean
)

data class Template (
	val id: UUID,
	val name: String,
	val exercises: List<Exercise>
)