package com.alvkoen.db

import com.alvkoen.model.Exercise
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow
import com.alvkoen.model.Set
import com.alvkoen.model.Template
import com.alvkoen.model.Workout
import org.jetbrains.exposed.sql.javatime.datetime


object Sets : UUIDTable("set") {
	val name = varchar("name", 255)
	val reps = integer("reps")
	val weight = double("weight")
	val order = integer("order")
	val exerciseId = reference("exercise_id", Exercises.id)

	fun toSet(row: ResultRow): Set =
		Set(
			id = row[id].value,
			name = row[name],
			reps = row[reps],
			weight = row[weight],
			order = row[order]
		)
}

object Exercises : UUIDTable("exercise") {
	val name = varchar("name", 255)
	val order = integer("exercise_order")
	val parentId = uuid("parent_id")
	val isSuperSet = bool("is_superset")

	fun toExercise(row: ResultRow): Exercise =
		Exercise(
			id = row[id].value,
			name = row[name],
			order = row[order],
			sets = emptyList(), // This will be filled later
			isSuperSet = row[isSuperSet]
		)
}

object Templates : UUIDTable("template") {
	val name = varchar("name", 255)

	fun toTemplate(row: ResultRow): Template =
		Template(
			id = row[id].value,
			name = row[name],
			exercises = emptyList() // This will be filled later
		)
}

object Workouts : UUIDTable("workout") {
	val name = varchar("name", 255)
	val date = datetime("date")
	val isCompleted = bool("is_completed")

	fun toWorkout(row: ResultRow): Workout =
		Workout(
			id = row[id].value,
			name = row[name],
			exercises = emptyList(), // This will be filled later
			date = row[date],
			isCompleted = row[isCompleted]
		)
}