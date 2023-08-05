package nt.jsa.repository

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import nt.jsa.model.Student
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class StudentRepository: PanacheRepository<Student> {
    fun findByEmail(email: String) = find("email", email).firstResult()
}