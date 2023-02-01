package com.example.room

import androidx.room.*

@Dao
interface StudentDao {
    @Query("SELECT * FROM student_table")
    fun getAll(): List<Student>

    @Query("SELECT * FROM student_table WHERE rollNo LIKE :roll LIMIT 1")
    suspend fun findByRoll(roll: Int): Student

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(student: Student)

    @Delete
    suspend fun delete(student: Student)

    @Query("DELETE FROM student_table")
    suspend fun deleteAll()

    @Query("UPDATE student_table SET firstName=:firstName, lastName=:lastName, WHERE rollNo LIKE :rollNo")
    suspend fun updateByRollNo(firstName: String, lastName: String, rollNo: Int)
}