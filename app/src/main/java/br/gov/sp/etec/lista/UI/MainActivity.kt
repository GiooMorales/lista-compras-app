package br.gov.sp.etec.lista.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.gov.sp.etec.lista.R
import br.gov.sp.etec.lista.adapters.TaskAdapter
import br.gov.sp.etec.lista.models.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class MainActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore //Criando a variável de conexão com o database
    private lateinit var recyclerView: RecyclerView //Declarando o componente de tela
    private lateinit var taskAdapter: TaskAdapter
    private val taskList = mutableListOf<Task>() //Lista mutável para armazenar as tarefas
    //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = FirebaseFirestore.getInstance() //Ligando com o db

        recyclerView = findViewById(R.id.recyclerView) //Cria uma referência a um elemento de UI

        recyclerView.layoutManager = LinearLayoutManager(this) //Define o gerenciador de layout

        taskAdapter = TaskAdapter(taskList) //Inicia o adapter com a lista de contatos

        recyclerView.adapter = taskAdapter

        taskLoader()

        //Botão
        findViewById<Button>(R.id.btnMain).setOnClickListener {
            val intent = Intent(this, itemForm::class.java);
            startActivity(intent)
        }
    }//Fim do Oncreate

    private fun taskLoader(){
        db.collection("tasks").get()
            .addOnSuccessListener { tasks->
                taskList.clear()
                
                for (document in tasks){
                    val item = document.toObject<Task>()
                    item.id = document.id

                    taskList.add(item)
                }

                taskAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { erro->
                Toast.makeText(this, "Erro:" + "${erro.message}", Toast.LENGTH_SHORT).show()
            }
    }

}//Fim da classe