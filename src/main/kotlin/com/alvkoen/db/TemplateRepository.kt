package com.alvkoen.db

import com.alvkoen.model.Template
import java.util.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class TemplateRepository {

	fun getTemplates(): List<Template> {
		return transaction {
			Templates.selectAll().mapNotNull { templateRow ->
				val templateId = templateRow[Templates.id]
				val exercises = getExercisesAndSets(templateId.value)
				Template(id = templateId.value, name = templateRow[Templates.name], exercises = exercises)
			}
		}
	}

	fun getTemplateById(id: UUID): Template? {
		return transaction {
			val templateRow = Templates.select { Templates.id eq id }.singleOrNull() ?: return@transaction null
			val exercises = getExercisesAndSets(id)
			Template(id = templateRow[Templates.id].value, name = templateRow[Templates.name], exercises = exercises)
		}
	}

	fun addTemplate(template: Template): UUID {
		return transaction {
			val templateId = Templates.insertAndGetId {
				it[name] = template.name
			}

			template.exercises.forEach { exercise ->
				val exId = Exercises.insertAndGetId {
					it[name] = exercise.name
					it[order] = exercise.order
					it[isSuperSet] = exercise.isSuperSet
					it[parentId] = templateId.value
				}

				exercise.sets.forEach { set ->
					Sets.insert {
						it[name] = set.name
						it[reps] = set.reps
						it[weight] = set.weight
						it[order] = set.order
						it[exerciseId] = exId.value
					}
				}
			}

			templateId.value
		}
	}

	fun deleteTemplate(id: UUID): Boolean {
		return transaction {
			val exerciseRows = Exercises.select { Exercises.parentId eq id }
			exerciseRows.forEach { exerciseRow ->
				val exerciseId = exerciseRow[Exercises.id]
				Sets.deleteWhere { Sets.exerciseId eq exerciseId }
			}

			Exercises.deleteWhere { parentId eq id }

			Templates.deleteWhere { Templates.id eq id } > 0
		}
	}
}