package nt.jsa.service

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import nt.jsa.exceptions.StudentCreationFailedException
import nt.jsa.exceptions.StudentNotFoundException
import nt.jsa.exceptions.StudentUpdateFailedException
import nt.jsa.model.Student
import nt.jsa.repository.CourseRepository
import nt.jsa.repository.StudentRepository

@ApplicationScoped
class StudentService {
    @Inject
    private lateinit var studentRepository: StudentRepository

    @Inject
    private lateinit var courseRepository: CourseRepository

    @Transactional
    fun createStudent(firstName: String, lastName: String, email: String): Student {
        val student = Student()
        student.firstName = firstName
        student.lastName = lastName
        student.email = email

        studentRepository.persist(student)
        return studentRepository.findByEmail(email)
            ?: throw StudentCreationFailedException("Student $student could not be created")
    }

    @Transactional
    fun createStudent(studentData: Student): Student {
        val student = Student()
        student.firstName = studentData.firstName
        student.lastName = studentData.lastName
        student.email = studentData.email

        studentRepository.persist(student)
        return studentRepository.findByEmail(studentData.email)
            ?: throw StudentCreationFailedException("Student $student could not be created")
    }

    @Transactional
    fun getStudentById(id: Int) = studentRepository.findById(id)
        ?: throw StudentNotFoundException("Student with Id $id not found")

    @Transactional
    fun getStudentByEmail(email: String) = studentRepository.findByEmail(email)
        ?: throw StudentNotFoundException("Student with email $email not found")

    @Transactional
    fun getAllStudents(): List<Student> = studentRepository.findAll().stream().toList()

    @Transactional
    fun updateStudentData(oldMail: String, firstName: String, lastName: String, email: String): Student {
        val studentToUpdate = studentRepository.findByEmail(oldMail)
            ?: throw StudentNotFoundException("Student with email $oldMail not found")

        studentToUpdate.firstName = firstName
        studentToUpdate.lastName = lastName
        studentToUpdate.email = email

        studentRepository.persist(studentToUpdate)

        return studentRepository.findById(studentToUpdate.id!!)
            ?: throw StudentUpdateFailedException("Student with email $oldMail could not be updated")
    }

    @Transactional
    fun updateStudent(id: Int, firstName: String, lastName: String, email: String): Student {
        val studentToUpdate = studentRepository.findById(id)
            ?: throw StudentNotFoundException("Student with id $id not found")

        studentToUpdate.firstName = firstName
        studentToUpdate.lastName = lastName
        studentToUpdate.email = email

        studentRepository.persist(studentToUpdate)

        return studentRepository.findById(studentToUpdate.id!!)
            ?: throw StudentUpdateFailedException("Student with id $id could not be updated")
    }

    @Transactional
    fun enrollInCourse(studentId: Int, courseId: Int): Student {
        val student = studentRepository.findById(studentId)
            ?: throw StudentNotFoundException("Student with Id $studentId not found")

        val studentCourses = student.courses.toMutableSet()
        courseRepository.findById(courseId)?.let { studentCourses.add(it) }
        student.courses = studentCourses
        studentRepository.persist(student)

        return studentRepository.findById(studentId)
            ?: throw StudentNotFoundException("Student with Id $studentId not found")
    }

    @Transactional
    fun disenrollInCourse(studentId: Int, courseId: Int): Student {
        val student = studentRepository.findById(studentId)
            ?: throw StudentNotFoundException("Student with Id $studentId not found")

        val studentCourses = student.courses.toMutableSet()
        studentCourses.removeIf { it.id == courseId }
        student.courses = studentCourses
        studentRepository.persist(student)

        return studentRepository.findById(studentId)
            ?: throw StudentNotFoundException("Student with Id $studentId not found")
    }

    @Transactional
    fun getAllCoursesOfStudent(email: String) =
        studentRepository.findByEmail(email)?.courses
            ?: throw StudentNotFoundException("Student with Email $email not found")

    @Transactional
    fun deleteStudentById(id: Int) = studentRepository.deleteById(id)
}
