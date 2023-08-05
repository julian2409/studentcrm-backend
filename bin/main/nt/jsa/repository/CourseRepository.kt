package nt.jsa.repository

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import nt.jsa.model.Course
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class CourseRepository: PanacheRepository<Course> {
    fun findByCourseName(courseName: String) = find("courseName", courseName).firstResult()
}