package com.example.room

import androidx.room.*
import com.example.room.Student

@Dao
interface StudentDao {
    @Query("SELECT * FROM student_table")
    fun getAll():List<Student>

    @Query("SELECT * FROM student_table WHERE rollNo LIKE :roll LIMIT 1")
    suspend fun findByRoll(roll:Int): Student

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(student: Student)

    @Delete
    suspend fun delete(student: Student)

    @Query("DELETE FROM student_table")
    suspend fun deleteAll()
}