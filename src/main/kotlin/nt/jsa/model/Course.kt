package nt.jsa.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany

@Entity
open class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Int? = null

    @Column(name = "course_name")
    open lateinit var courseName: String

    @Column(name = "course_description")
    open lateinit var courseDescription: String

    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    open var students: MutableList<Student> = mutableListOf()
}
