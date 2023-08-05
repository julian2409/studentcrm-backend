package nt.jsa.model

import javax.persistence.*

@Entity
class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "first_name")
    lateinit var firstName: String

    @Column(name = "last_name")
    lateinit var lastName: String
    lateinit var email: String

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "student_course",
        joinColumns = [JoinColumn(name = "fk_student_id")],
        inverseJoinColumns = [JoinColumn(name = "fk_course_id")]
    )
    var courses: List<Course> = listOf()
}