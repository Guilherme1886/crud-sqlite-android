package com.guilherme.antonio.crudsqliteandroid.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.guilherme.antonio.crudsqliteandroid.model.Pessoa
import java.util.ArrayList

/**
 * Created by Guilherme on 29/09/2017.
 */

class Database(context: Context?) : SQLiteOpenHelper(context, "crud_sqlite", null, 1) {

    private val TABLE_NAME = "usuarios"
    private val SQL_SELECT_USUARIOS = "SELECT * FROM $TABLE_NAME"
    private val SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nome TEXT," +
            "email TEXT," +
            "telefone TEXT," +
            "endereco TEXT)"

    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL(SQL_CREATE_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)

    }


    fun createPessoa(pessoa: Pessoa) {

        val db = this.writableDatabase
        val values = ContentValues()
        values.put("nome", pessoa.nome)
        values.put("email", pessoa.email)
        values.put("telefone", pessoa.telefone)
        values.put("endereco", pessoa.endereco)
        db.insert(TABLE_NAME, null, values)
        db.close()

    }

    fun readPessoa(): List<Pessoa> {

        val pessoaList = ArrayList<Pessoa>()
        val db = this.readableDatabase
        val cursor = db.rawQuery(SQL_SELECT_USUARIOS, null)

        if (cursor.moveToFirst()) {
            do {
                val pessoa = Pessoa(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                )
                pessoaList.add(pessoa)

            } while (cursor.moveToNext())
        }

        return pessoaList

    }

    fun updatePessoa(pessoa: Pessoa): Int {

        val db = this.readableDatabase
        val values = ContentValues()
        values.put("nome", pessoa.nome)
        values.put("email", pessoa.email)
        values.put("telefone", pessoa.telefone)
        values.put("endereco", pessoa.endereco)

        return db.update(TABLE_NAME, values, "id = ?", arrayOf(pessoa.id.toString()))


    }

    fun deletePessoa(pessoa: Pessoa): Boolean {

        val db = this.readableDatabase
        db.delete(TABLE_NAME, "id = ?", arrayOf(pessoa.id.toString()))
        db.close()

        return true
    }


}