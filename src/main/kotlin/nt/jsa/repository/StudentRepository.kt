package nt.jsa.repository

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import nt.jsa.model.Student

@ApplicationScoped
class StudentRepository : PanacheRepository<Student> {
    fun findByEmail(email: String) = find("email", email).firstResult()
    fun findById(id: Int) = find("id", id).firstResult()

    fun deleteById(id: Int) = delete("id", id)
}
