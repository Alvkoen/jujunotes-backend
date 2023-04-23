package com.alvkoen.db

import com.alvkoen.model.Template
import java.util.*
import org.jooq.DSLContext

class TemplateRepository(private val dslContext: DSLContext) {

	fun getTemplates(): List<Template> {
		TODO("Not yet implemented")
	}

	fun getById(id: UUID): Template {
		TODO("Not yet implemented")
	}

	fun addTemplate(template: Template): Template {
		TODO("Not yet implemented")
	}

	fun deleteTemplate(id: UUID): Template {
		TODO("Not yet implemented")
	}
}