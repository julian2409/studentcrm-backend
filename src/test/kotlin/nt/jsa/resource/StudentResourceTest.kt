package nt.jsa.resource

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import jakarta.inject.Inject
import nt.jsa.dtos.StudentDto
import nt.jsa.service.StudentService
import nt.jsa.testlifecyclemanager.DatabaseTestLifeCycleManager
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@QuarkusTest
@QuarkusTestResource(DatabaseTestLifeCycleManager::class)
class StudentResourceTest {

    @Inject
    lateinit var studentService: StudentService

    @Test
    fun whenGivenUrlToStudents_thenStatusOkAndBodySizeCorrect() {
        given()
            .`when`().get("/v1/students")
            .then()
            .statusCode(200)
            .body("size()", equalTo(5))
    }

    @Test
    fun whenGivenUrlToStudentWithId_thenStatusOkAndCorrectEmail() {
        given()
            .`when`().get("/v1/students/1000")
            .then()
            .statusCode(200)
            .body("email", equalTo("firstlast0@mail.com"))
    }

    @Test
    fun whenRequestedPost_thenCreated() {
        val newStudent = StudentDto("hello", "world", "hello.world@email.com")

        given()
            .contentType("application/json")
            .body(newStudent)
            .`when`()
            .post("/v1/students")
            .then()
            .statusCode(201)

        val createdStudent = studentService.getStudentByEmail("hello.world@email.com")
        createdStudent.id?.let { studentService.deleteStudentById(it) }
    }

    @Test
    fun whenRequestedPut_thenUpdatedAndOK() {
        val createdStudent = studentService.createStudent("test", "test", "test.test@test.test")
        val updatedStudentData = StudentDto("hello", "world", "hello.world@email.com")

        given()
            .contentType("application/json")
            .body(updatedStudentData)
            .`when`()
            .put("/v1/students/${createdStudent.id}")
            .then()
            .statusCode(200)

        val updatedStudent = createdStudent.id?.let { studentService.getStudentById(it) }

        if (updatedStudent != null) {
            Assertions.assertEquals("hello", updatedStudent.firstName)
            Assertions.assertEquals("world", updatedStudent.lastName)
            Assertions.assertEquals("hello.world@email.com", updatedStudent.email)
        }

        if (updatedStudent != null) {
            updatedStudent.id?.let { studentService.deleteStudentById(it) }
        }
    }

    @Test
    fun whenDelete_thenStatus204() {
        val createdStudent = studentService.createStudent("test", "test", "test.test@test.test")

        given()
            .`when`().delete("/v1/students/${createdStudent.id}")
            .then()
            .statusCode(204)
    }
}
