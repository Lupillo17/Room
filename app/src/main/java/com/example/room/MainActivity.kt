package com.example.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.room.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appDb: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDb = AppDatabase.getDatabase(this)

        binding.btnWriteData.setOnClickListener {
            writeData()
        }
        binding.btnReadData.setOnClickListener {
            readData()
        }

        binding.btnDeleteData.setOnClickListener {
            deleteData()
        }

        binding.btnUpdate.setOnClickListener {
            updateByRollNo()
        }
    }

    private fun updateByRollNo() {
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val rollNo = binding.etRollNo.text.toString()

        if (firstName.isNotEmpty() && lastName.isNotEmpty() && rollNo.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                appDb.studentDao().updateByRollNo(firstName, lastName, rollNo.toInt())
            }

            binding.etFirstName.text?.clear()
            binding.etLastName.text?.clear()
            binding.etRollNo.text?.clear()

            Toast.makeText(this@MainActivity, "Successfully updated", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@MainActivity, "Please add data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteData() {
        CoroutineScope(Dispatchers.IO).launch {
            appDb.studentDao().deleteAll()
        }
    }

    private fun writeData() {
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val rollNo = binding.etRollNo.text.toString()

        if (firstName.isNotEmpty() && lastName.isNotEmpty() && rollNo.isNotEmpty()) {
            val student = Student(
                null, firstName, lastName, rollNo.toInt()
            )

            CoroutineScope(Dispatchers.IO).launch {
                appDb.studentDao().insert(student)
            }

            binding.etFirstName.text?.clear()
            binding.etLastName.text?.clear()
            binding.etRollNo.text?.clear()

            Toast.makeText(this@MainActivity, "Successfully added", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@MainActivity, "Please add data", Toast.LENGTH_SHORT).show()
        }

    }

    private suspend fun displayData(student: Student) {
        withContext(Dispatchers.Main) {
            binding.tvFirstName.text = student.firstName
            binding.tvLastName.text = student.lastName
            binding.tvRollNo.text = student.rollNo.toString()
        }
    }

    private fun readData() {

        val rollNo = binding.etRollNo2.text.toString()
        if (rollNo.isNotEmpty()) {
            var student: Student
            CoroutineScope(Dispatchers.IO).launch {
                student = appDb.studentDao().findByRoll(rollNo.toInt())
                displayData(student)
            }
        }

    }
}