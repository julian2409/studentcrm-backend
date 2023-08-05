package nt.jsa.service

import io.quarkus.test.TestTransaction
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import nt.jsa.model.Course
import nt.jsa.testlifecyclemanager.DatabaseTestLifeCycleManager
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import jakarta.inject.Inject

@QuarkusTest
@QuarkusTestResource(DatabaseTestLifeCycleManager::class)
class CourseServiceTest {
    @Inject
    lateinit var courseService: CourseService

    @Test
    @TestTransaction
    fun testCreateCourse() {
        val course = Course()
        course.courseName = "course0"
        course.courseDescription = "course desc 0"

        val res = courseService.createCourse(course)
        Assertions.assertEquals("course0", res.courseName)
        Assertions.assertEquals("course desc 0", res.courseDescription)
    }

    @Test
    @TestTransaction
    fun testGetAllCourses() {
        val res = courseService.getAllCourses()
        Assertions.assertEquals(2, res.size)
    }

    @Test
    @TestTransaction
    fun testGetStudentById() {
        val res = courseService.getCourseById(1001)

        Assertions.assertEquals("desc1001", res.courseDescription)
        Assertions.assertEquals("course1001", res.courseName)
    }

    @Test
    @TestTransaction
    fun testUpdateCourse() {
        courseService.updateCourseData("course1000", "newName1000", "newDesc1000")

        val res = courseService.getCourseById(1000)
        Assertions.assertEquals("newName1000", res.courseName)
        Assertions.assertEquals("newDesc1000", res.courseDescription)
        Assertions.assertEquals(4, res.students.size)
    }

    @Test
    @TestTransaction
    fun testGetEnrolledStudents() {

        val res = courseService.getCourseById(1000)

        Assertions.assertEquals(4, res.students.size)
    }

    @Test
    @TestTransaction
    fun testDeleteCourseById() {
        courseService.deleteCourseById(1001)

        Assertions.assertEquals(1, courseService.getAllCourses().size)
    }
}
