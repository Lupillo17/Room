package com.example.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("student_table")
data class Student(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "firstName") val firstName: String?,
    @ColumnInfo(name = "lastName") val lastName: String?,
    @ColumnInfo(name = "rollNo") val rollNo: Int?
)
