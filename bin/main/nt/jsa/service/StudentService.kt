package nt.jsa.service

import nt.jsa.model.Student
import nt.jsa.repository.StudentRepository
import java.lang.Exception
import java.util.stream.Collectors
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class StudentService {
    @Inject
    lateinit var studentRepository: StudentRepository

    fun createStudent(firstName: String, lastName: String, email: String): Student {
        val student = Student()
        student.firstName = firstName
        student.lastName = lastName
        student.email = email

        studentRepository.persist(student)
        return studentRepository.findByEmail(email) ?: throw Exception()
    }

    fun getStudentById(id: Int) = studentRepository.findById(id.toLong()) ?: throw Exception()

    fun getAllStudents(): List<Student> = studentRepository.findAll().stream().toList()

//    fun updateStudentData()
//
//    fun enrollInCourse()
//
//    fun disenrollInCourse()

    fun deleteStudentById(id: Int) = studentRepository.deleteById(id.toLong())
}