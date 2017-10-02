package com.guilherme.antonio.crudsqliteandroid.activity

import android.app.Dialog
import android.content.DialogInterface
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import com.guilherme.antonio.crudsqliteandroid.R
import com.guilherme.antonio.crudsqliteandroid.adapter.PessoaAdapter
import com.guilherme.antonio.crudsqliteandroid.database.Database
import com.guilherme.antonio.crudsqliteandroid.model.Pessoa
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    private val db: Database = Database(this)
    private var mAdapter: PessoaAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        readPessoas()
    }

    private fun readPessoas() {
        mAdapter = PessoaAdapter(db.readPessoa(), this, object : PessoaAdapter.OnItemClickListener {
            override fun onItemClickUpdate(item: Pessoa) {
                updatePessoa(item)


            }

            override fun onItemClickDelete(item: Pessoa) {
                deletePessoa(item)
            }

        })

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.stackFromEnd = true
        recycler_view_pessoas.layoutManager = linearLayoutManager
        recycler_view_pessoas.adapter = mAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val id = item?.itemId

        when (id) {
            R.id.add_pessoa -> createPessoa()

        }
        return super.onOptionsItemSelected(item)
    }

    private fun createPessoa(): AlertDialog.Builder {

        val builder = AlertDialog.Builder(this)
        builder.setView(layoutInflater.inflate(R.layout.dialog_add_item, null))
        builder.setPositiveButton("ADICIONAR") { dialog, which ->

            val view = dialog as Dialog
            val nome = view.findViewById(R.id.nome) as EditText
            val email = view.findViewById(R.id.email) as EditText
            val telefone = view.findViewById(R.id.telefone) as EditText
            val endereco = view.findViewById(R.id.endereco) as EditText

            if (nome.text.isEmpty() || email.text.isEmpty() || telefone.text.isEmpty() || endereco.text.isEmpty()) {
                toast("Preencha os dados")
            } else {

                val pessoa = Pessoa(
                        null,
                        nome.text.toString(),
                        email.text.toString(),
                        telefone.text.toString(),
                        endereco.text.toString()
                )
                db.createPessoa(pessoa)
                readPessoas()
                toast("Pessoa cadastrada!")
            }

        }
        builder.create()
        builder.show()
        return builder
    }

    private fun updatePessoa(item: Pessoa): Unit {

        val builder = AlertDialog.Builder(this)
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_add_item, null)
        builder.setView(view)

        val dialog: Dialog

        val nome = view.findViewById(R.id.nome) as EditText
        val email = view.findViewById(R.id.email) as EditText
        val telefone = view.findViewById(R.id.telefone) as EditText
        val endereco = view.findViewById(R.id.endereco) as EditText

        nome.setText(item.nome)
        email.setText(item.email)
        telefone.setText(item.telefone)
        endereco.setText(item.endereco)

        builder.setPositiveButton("ATUALIZAR") { dialog, which ->

            val pessoa = Pessoa(
                    item.id,
                    nome.text.toString(),
                    email.text.toString(),
                    telefone.text.toString(),
                    endereco.text.toString()
            )
            db.updatePessoa(pessoa)
            readPessoas()
            toast("Pessoa atualizada!")

        }

        dialog = builder.create()

        return dialog.show()

    }

    private fun deletePessoa(item: Pessoa): AlertDialog.Builder {

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Deseja remover o item ?")
        builder.setPositiveButton("REMOVER") { dialog, which ->


            val pessoa = Pessoa(
                    item.id,
                    item.nome,
                    item.email,
                    item.telefone,
                    item.endereco
            )
            db.deletePessoa(pessoa)
            readPessoas()
            toast("Pessoa deletada!")

        }
        builder.create()
        builder.show()
        return builder


    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
    }

}
