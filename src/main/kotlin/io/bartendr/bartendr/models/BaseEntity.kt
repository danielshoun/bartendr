package io.bartendr.bartendr.models

import java.io.Serializable
import java.time.Instant
import java.util.*
import javax.persistence.*

@MappedSuperclass
open class BaseEntity : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    val id: Long? = null

    @Column(name = "created_at")
    val createdAt: Date = Date()

    @Column(name = "updated_at")
    var updatedAt: Date = Date()

    val isNew: Boolean
        get() = this.id == null
}