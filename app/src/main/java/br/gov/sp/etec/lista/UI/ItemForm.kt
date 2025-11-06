package br.gov.sp.etec.lista.UI

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.gov.sp.etec.lista.R
import br.gov.sp.etec.lista.models.Task
import com.google.firebase.firestore.FirebaseFirestore

class itemForm : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_item_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val title = intent.getStringExtra("title") ?: ""
        val id = intent.getStringExtra("id") ?: ""
        val description = intent.getStringExtra("description") ?: ""
        val status = intent.getStringExtra("status") ?: ""

        db = FirebaseFirestore.getInstance()
        //Cria uma referencia para os elementos da UI
        editTextTitle = findViewById(R.id.editTextTextTask)
        editTextDescription = findViewById(R.id.editTextTextMultiLineDescription)

        val btnDelete: Button = findViewById(R.id.btnExcluir)
        if(id == ""){
            btnDelete.visibility = View.GONE
        }

        //Referenciando um botão e adicionando um ouvinte para o evento de click
        findViewById<Button>(R.id.btnSalvar).setOnClickListener { save() }
        findViewById<Button>(R.id.btnExcluir).setOnClickListener {  }

    }//Fim do onCreate

    private fun save(){
        val task = Task(
            title = editTextTitle.text.toString(),
            description = editTextDescription.text.toString(),
            status = "PENDING"
        )

        db.collection("tasks").add(task)
            .addOnSuccessListener {
                Toast.makeText(this, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show()
                Log.e("ERRO FIREBASE",error.toString())
            }
    }

}//Fim da classe