package nt.jsa.repository

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import nt.jsa.model.Course

@ApplicationScoped
class CourseRepository : PanacheRepository<Course> {
    fun findByCourseName(courseName: String) = find("courseName", courseName).firstResult()

    fun findById(id: Int) = find("id", id).firstResult()

    fun deleteById(id: Int) = delete("id", id)
}
