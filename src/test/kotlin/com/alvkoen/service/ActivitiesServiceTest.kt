package com.alvkoen.service

import com.alvkoen.BaseTest
import com.alvkoen.db.TemplateRepository
import com.alvkoen.db.WorkoutRepository
import com.alvkoen.model.ActivityListResult
import com.alvkoen.model.ActivityResult
import com.alvkoen.testWorkout
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


class ActivitiesServiceTest: BaseTest() {
	private val workoutRepository = WorkoutRepository()
	private val templateRepository = TemplateRepository()
	private val activitiesService = ActivitiesService(workoutRepository, templateRepository)

	@Test
	fun `test insert and fetch workout`() {
		val testWorkout = testWorkout()
		val insertResult = activitiesService.addWorkout(testWorkout)
		assertNotNull(insertResult)
		assertEquals(testWorkout.id, (insertResult as? ActivityResult.Success)?.value)

		val fetchResult = activitiesService.getWorkoutById(testWorkout.id)
		assertNotNull(fetchResult)

		val fetchedWorkout = (fetchResult as? ActivityResult.Success)?.value

		assertEquals(testWorkout.id, fetchedWorkout?.id)
		assertEquals(testWorkout.name, fetchedWorkout?.name)
		assertEquals(testWorkout.date, fetchedWorkout?.date)
		assertEquals(testWorkout.isCompleted, fetchedWorkout?.isCompleted)
		assertEquals(testWorkout.exercises, fetchedWorkout?.exercises)
	}

	@Test
	fun `test insert multiple workouts`() {
		IntRange(0, 2).forEach{ _ ->
			activitiesService.addWorkout(testWorkout())
		}

		val fetchResult = activitiesService.getWorkouts()
		assertNotNull(fetchResult)

		val fetchedWorkout = (fetchResult as? ActivityListResult.Success)?.values
		assertEquals(3, fetchedWorkout?.size)
	}
}