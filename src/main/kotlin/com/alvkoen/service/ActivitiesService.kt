package com.alvkoen.service

import com.alvkoen.db.TemplateRepository
import com.alvkoen.db.WorkoutRepository
import com.alvkoen.model.ActivityListResult
import com.alvkoen.model.ActivityResult
import com.alvkoen.model.Template
import com.alvkoen.model.Workout
import java.util.*

class ActivitiesService(private val workoutRepository: WorkoutRepository, private val templateRepository: TemplateRepository) {

	fun getWorkouts(): ActivityListResult<Workout> {
		return try {
			ActivityListResult.Success(workoutRepository.getWorkouts())
		} catch (e: Exception) {
			ActivityListResult.Failure("Failed to fetch workouts", e)
		}
	}

	fun getWorkoutById(id: UUID): ActivityResult<Workout?> {
		return try {
			ActivityResult.Success(workoutRepository.getById(id))
		} catch (e: Exception) {
			ActivityResult.Failure("Failed to get workout", e)
		}
	}

	fun addWorkout(workout: Workout): ActivityResult<UUID> {
		return try {
			ActivityResult.Success(workoutRepository.addWorkout(workout))
		} catch (e: Exception) {
			ActivityResult.Failure("Failed to save workout", e)
		}
	}

	fun deleteWorkout(id: UUID): ActivityResult<Boolean> {
		return try {
			ActivityResult.Success(workoutRepository.deleteWorkout(id))
		} catch (e: Exception) {
			ActivityResult.Failure("Failed to delete workout", e)
		}
	}

	fun getTemplates(): ActivityListResult<Template> {
		return try {
			ActivityListResult.Success(templateRepository.getTemplates())
		} catch (e: Exception) {
			ActivityListResult.Failure("Failed to fetch templates", e)
		}
	}

	fun getTemplateById(id: UUID): ActivityResult<Template?> {
		return try {
			ActivityResult.Success(templateRepository.getTemplateById(id))
		} catch (e: Exception) {
			ActivityResult.Failure("Failed to get template", e)
		}
	}

	fun addTemplate(template: Template): ActivityResult<UUID> {
		return try {
			ActivityResult.Success(templateRepository.addTemplate(template))
		} catch (e: Exception) {
			ActivityResult.Failure("Failed to save template", e)
		}
	}

	fun deleteTemplate(id: UUID): ActivityResult<Boolean> {
		return try {
			ActivityResult.Success(templateRepository.deleteTemplate(id))
		} catch (e: Exception) {
			ActivityResult.Failure("Failed to delete template", e)
		}
	}

}