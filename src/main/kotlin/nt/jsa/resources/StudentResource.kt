package nt.jsa.resources

import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import nt.jsa.dtos.StudentDto
import nt.jsa.model.Student
import nt.jsa.service.StudentService

@Path("/v1/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class StudentResource {

    @Inject
    lateinit var studentService: StudentService

    @GET
    fun getAllStudents() = studentService.getAllStudents()

    @GET
    @Path("{id}")
    fun getStudentById(id: Int) = studentService.getStudentById(id)

    @POST
    fun createStudent(student: StudentDto): Response {
        studentService.createStudent(student.firstName, student.lastName, student.email)
        return Response.status(201).build()
    }

    @PUT
    @Path("{id}")
    fun updateStudent(id: Int, student: StudentDto): Student {
        return studentService.updateStudent(id, student.firstName, student.lastName, student.email)
    }

    @PUT
    @Path("{studentId}/courses/{courseId}/enroll")
    fun enrollInCourse(studentId: Int, courseId: Int) {
        studentService.enrollInCourse(studentId, courseId)
    }

    @PUT
    @Path("{studentId}/courses/{courseId}/disenroll")
    fun disenrollFromCourse(studentId: Int, courseId: Int) {
        studentService.disenrollInCourse(studentId, courseId)
    }

    @DELETE
    @Path("{id}")
    fun deleteStudent(id: Int): Response {
        studentService.deleteStudentById(id)
        return Response.status(204).build()
    }
}
