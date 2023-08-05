package nt.jsa.resource

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import jakarta.inject.Inject
import nt.jsa.dtos.CourseDto
import nt.jsa.service.CourseService
import nt.jsa.testlifecyclemanager.DatabaseTestLifeCycleManager
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@QuarkusTest
@QuarkusTestResource(DatabaseTestLifeCycleManager::class)
class CourseResourceTest {

    @Inject
    lateinit var courseService: CourseService

    @Test
    fun whenGivenUrlToCourses_thenStatusOKAndBodySizeCorrect() {
        given()
            .`when`().get("/v1/courses")
            .then()
            .statusCode(200)
            .body("size()", equalTo(2))
    }

    @Test
    fun whenGivenUrlToCourseWithId_thenStatusOkAndCorrectEmail() {
        given()
            .`when`().get("/v1/courses/1000")
            .then()
            .statusCode(200)
            .body("courseDescription", equalTo("desc1000"))
    }

    @Test
    fun whenRequestedPost_thenCreated() {
        val newCourse = CourseDto("someCourse", "someCourse")

        given()
            .contentType("application/json")
            .body(newCourse)
            .`when`()
            .post("/v1/courses")
            .then()
            .statusCode(201)

        val createdCourse = courseService.getCourseByName("someCourse")
        createdCourse?.id?.let { courseService.deleteCourseById(it) }
    }

    @Test
    fun whenRequestedPut_thenUpdatedAndOK() {
        val createdCourse = courseService.createCourse("courseName", "courseDesc")
        val updatedCourseData = CourseDto("hello", "world")

        given()
            .contentType("application/json")
            .body(updatedCourseData)
            .`when`()
            .put("/v1/courses/${createdCourse.id}")
            .then()
            .statusCode(200)

        val updatedCourse = createdCourse.id?.let { courseService.getCourseById(it) }

        if (updatedCourse != null) {
            Assertions.assertEquals("hello", updatedCourse.courseName)
            Assertions.assertEquals("world", updatedCourse.courseDescription)
        }

        if (updatedCourse != null) {
            updatedCourse.id?.let { courseService.deleteCourseById(it) }
        }
    }

    @Test
    fun whenDelete_thenStatus204() {
        val createdCourse = courseService.createCourse("courseName", "courseDesc")

        given()
            .`when`().delete("/v1/courses/${createdCourse.id}")
            .then()
            .statusCode(204)
    }
}
