package nt.jsa.service

import nt.jsa.repository.CourseRepository
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class CourseService {
    @Inject
    lateinit var courseRepository: CourseRepository

//    fun createCourse()
//
//    fun getCourseById()
//
//    fun getAllCourses()
//
//    fun updateCourseData()
//
//    fun enrollStudent()
//
//    fun disenrollStudent()
//
//    fun deleteByIdCourse()
}