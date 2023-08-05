package nt.jsa.model

import javax.persistence.*

@Entity
class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "course_name")
    lateinit var courseName: String

    @Column(name = "course_description")
    lateinit var courseDescription: String

    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    var students: List<Student> = listOf()
}