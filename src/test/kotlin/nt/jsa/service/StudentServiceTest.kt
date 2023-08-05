package nt.jsa.service

import io.quarkus.test.TestTransaction
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import nt.jsa.model.Student
import nt.jsa.testlifecyclemanager.DatabaseTestLifeCycleManager
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@QuarkusTest
@QuarkusTestResource(DatabaseTestLifeCycleManager::class)
class StudentServiceTest {
    @Inject
    lateinit var studentService: StudentService

    @Test
    @TestTransaction
    fun testCreateStudent() {
        val student = Student()
        student.firstName = "test"
        student.lastName = "student01"
        student.email = "test.student01@mail.com"

        val res = studentService.createStudent(student)
        val res2 = studentService.createStudent("test2", "test2", "test2@mail.com")
        Assertions.assertEquals(student.firstName, res.firstName)
        Assertions.assertEquals(student.lastName, res.lastName)
        Assertions.assertEquals(student.email, res.email)
        Assertions.assertEquals("test2", res2.firstName)
        Assertions.assertEquals("test2", res2.lastName)
        Assertions.assertEquals("test2@mail.com", res2.email)
    }

    @Test
    @TestTransaction
    fun testGetAllStudents() {
        val res = studentService.getAllStudents()
        Assertions.assertEquals(5, res.size)
    }

    @Test
    @TestTransaction
    fun testGetStudentById() {
        val res = studentService.getStudentByEmail("firstlast0@mail.com")

        Assertions.assertEquals("first", res.firstName)
        Assertions.assertEquals("last", res.lastName)
        Assertions.assertEquals("firstlast0@mail.com", res.email)
    }

    @Test
    @TestTransaction
    fun testUpdateStudentData() {
        studentService.updateStudentData(
            "firstlast0@mail.com",
            "updated",
            "updated",
            "updatedupdated@mail.com",
        )
        val res = studentService.getStudentByEmail("updatedupdated@mail.com")

        Assertions.assertEquals("updated", res.firstName)
        Assertions.assertEquals("updated", res.lastName)
        Assertions.assertEquals("updatedupdated@mail.com", res.email)
    }

    @Test
    @TestTransaction
    fun testUpdateStudent() {
        studentService.updateStudent(
            1000,
            "updated",
            "updated",
            "updatedupdated@mail.com",
        )
        val res = studentService.getStudentById(1000)

        Assertions.assertEquals("updated", res.firstName)
        Assertions.assertEquals("updated", res.lastName)
        Assertions.assertEquals("updatedupdated@mail.com", res.email)
    }

    @Test
    @TestTransaction
    fun testEnrollInCourse() {
        studentService.enrollInCourse(1004, 1000)

        val res = studentService.getStudentById(1004)

        Assertions.assertEquals(2, res.courses.size)
    }

    @Test
    @TestTransaction
    fun testDisEnrollInCourse() {
        studentService.disenrollInCourse(1004, 1001)

        val res = studentService.getStudentById(1004)

        Assertions.assertEquals(0, res.courses.size)
    }

    @Test
    @TestTransaction
    fun testDeleteStudentById() {
        studentService.deleteStudentById(1004)

        Assertions.assertEquals(4, studentService.getAllStudents().size)
    }

    @Test
    @TestTransaction
    fun testGetAllCoursesOfStudent() {
        val courses = studentService.getAllCoursesOfStudent("firstlast0@mail.com")
        Assertions.assertEquals(2, courses.size)
    }
}
