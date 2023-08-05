package nt.jsa.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany

@Entity
open class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Int? = null

    @Column(name = "first_name")
    open lateinit var firstName: String

    @Column(name = "last_name")
    open lateinit var lastName: String
    open lateinit var email: String

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "student_course",
        joinColumns = [JoinColumn(name = "fk_student_id")],
        inverseJoinColumns = [JoinColumn(name = "fk_course_id")],
    )
    @JsonIgnore
    open var courses: MutableSet<Course> = mutableSetOf()
}
