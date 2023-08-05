package nt.jsa.service

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import nt.jsa.exceptions.CourseCreationFailedException
import nt.jsa.exceptions.CourseNotFoundException
import nt.jsa.exceptions.CourseUpdateFailedException
import nt.jsa.exceptions.StudentNotFoundException
import nt.jsa.exceptions.StudentUpdateFailedException
import nt.jsa.model.Course
import nt.jsa.repository.CourseRepository

@ApplicationScoped
class CourseService {
    @Inject
    private lateinit var courseRepository: CourseRepository

    @Transactional
    fun createCourse(courseName: String, courseDescription: String): Course {
        val course = Course()
        course.courseName = courseName
        course.courseDescription = courseDescription

        courseRepository.persist(course)
        return courseRepository.findByCourseName(courseName)
            ?: throw CourseCreationFailedException("Course ${course.courseName} could not be created")
    }

    @Transactional
    fun createCourse(courseData: Course): Course {
        val course = Course()
        course.courseName = courseData.courseName
        course.courseDescription = courseData.courseDescription

        courseRepository.persist(course)
        return courseRepository.findByCourseName(courseData.courseName)
            ?: throw CourseCreationFailedException("Course ${courseData.courseName} could not be created")
    }

     @Transactional
     fun getCourseById(id: Int): Course {
         val course = courseRepository.findById(id) ?: throw CourseNotFoundException("Course with Id $id not found")
         course.students.forEach { print(it) }
         return course
     }

    @Transactional
    fun getCourseByName(name: String) = courseRepository.findByCourseName(name)

    @Transactional
    fun getAllCourses(): List<Course> {
        val courses = courseRepository.findAll().stream().toList()
        courses.forEach { print(it.students) }
        return courses
    }

    @Transactional
    fun updateCourseData(oldName: String, courseName: String, courseDescription: String): Course {
        val courseToUpdate = courseRepository.findByCourseName(oldName)
            ?: throw CourseNotFoundException("Course $oldName not found")

        courseToUpdate.courseName = courseName
        courseToUpdate.courseDescription = courseDescription
        courseToUpdate.students.forEach { print(it) }

        courseRepository.persist(courseToUpdate)

        return courseRepository.findById(courseToUpdate.id!!)
            ?: throw StudentNotFoundException("Course $oldName could not be updated")
    }

    @Transactional
    fun getEnrolledStudents(courseName: String) =
        courseRepository.findByCourseName(courseName)?.students
            ?: throw CourseNotFoundException("Course $courseName not found")

    @Transactional
    fun deleteCourseById(id: Int) = courseRepository.deleteById(id)
}
