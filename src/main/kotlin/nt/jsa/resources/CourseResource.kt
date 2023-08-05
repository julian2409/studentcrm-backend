package nt.jsa.resources

import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import nt.jsa.dtos.CourseDto
import nt.jsa.model.Course
import nt.jsa.service.CourseService

@Path("/v1/courses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class CourseResource {

    @Inject
    lateinit var courseService: CourseService

    @GET
    fun getAllCourses() = courseService.getAllCourses()

    @GET
    @Path("{id}")
    fun getCourseById(id: Int) = courseService.getCourseById(id)

    @POST
    fun createCourse(course: CourseDto): Response {
        courseService.createCourse(course.courseName, course.courseDescription)
        return Response.status(201).build()
    }

    @PUT
    @Path("{id}")
    fun updateCourse(id: Int, course: CourseDto): Course {
        return courseService
            .updateCourseData(courseService.getCourseById(id).courseName, course.courseName, course.courseDescription)
    }

    @DELETE
    @Path("{id}")
    fun deleteCourse(id: Int): Response {
        courseService.deleteCourseById(id)
        return Response.status(204).build()
    }
}
