package com.example.rest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.rest.ApiConfig.apiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var edtNrp: EditText? = null
    private var edtNama: EditText? = null
    private var edtEmail: EditText? = null
    private var edtJurusan: EditText? = null
    private var progressBar: ProgressBar? = null
    private lateinit var btnAdd: Button
    private lateinit var btnListData: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edtNrp = findViewById(R.id.edtNrp)
        edtNama = findViewById(R.id.edtNama)
        edtEmail = findViewById(R.id.edtEmail)
        edtJurusan = findViewById(R.id.edtJurusan)
        progressBar = findViewById(R.id.progressBar)
        btnAdd = findViewById(R.id.btnAdd)
        btnListData = findViewById(R.id.btnList)
        btnAdd.setOnClickListener {
            addDataMahasiswa()
        }
        btnListData.setOnClickListener {
            val intent = Intent(this@MainActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addDataMahasiswa() {
        showLoading(true)
        val nrp = edtNrp!!.text.toString()
        val nama = edtNama!!.text.toString()
        val email = edtEmail!!.text.toString()
        val jurusan = edtJurusan!!.text.toString()
        if (nrp.isEmpty() || nama.isEmpty() || email.isEmpty() || jurusan.isEmpty()) {
            Toast.makeText(this@MainActivity, "Silahkan lengkapi form terlebih dahulu", Toast.LENGTH_SHORT).show()
            showLoading(false)
        } else {
            val client = apiService.addMahasiswa(nrp, nama, email, jurusan)
            client.enqueue(object : Callback<AddMahasiswaResponse> {
                override fun onResponse(call: Call<AddMahasiswaResponse>, response: Response<AddMahasiswaResponse>) {
                    showLoading(false)
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            Toast.makeText(this@MainActivity, "Berhasil menambahakan silahkan cek data pada halaman list!", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.e("Error", "onResponse: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<AddMahasiswaResponse>, t: Throwable) {
                    showLoading(false)
                    Log.e("Error Retrofit", "onFailure: ${t.message}")
                }
            })
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            progressBar!!.visibility = View.VISIBLE
        } else {
            progressBar!!.visibility = View.GONE
        }
    }
}
