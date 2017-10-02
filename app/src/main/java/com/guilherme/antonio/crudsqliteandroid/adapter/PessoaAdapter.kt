package com.guilherme.antonio.crudsqliteandroid.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.guilherme.antonio.crudsqliteandroid.R
import com.guilherme.antonio.crudsqliteandroid.model.Pessoa

/**
 * Created by Guilherme on 29/09/2017.
 */
class PessoaAdapter(private val pessoasList: List<Pessoa>, private val context: Context, private val listener: OnItemClickListener)
    : RecyclerView.Adapter<PessoaAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.bind(pessoasList[position], listener)


    }

    override fun getItemCount(): Int {
        return pessoasList.size
    }


    interface OnItemClickListener {

        fun onItemClickUpdate(item: Pessoa)
        fun onItemClickDelete(item: Pessoa)


    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var item_nome = itemView.findViewById(R.id.item_nome) as TextView
        private var item_email = itemView.findViewById(R.id.item_email) as TextView
        private var item_telefone = itemView.findViewById(R.id.item_telefone) as TextView
        private var item_endereco = itemView.findViewById(R.id.item_endereco) as TextView
        private var item_update = itemView.findViewById(R.id.item_update) as ImageView
        private var item_delete = itemView.findViewById(R.id.item_delete) as ImageView

        fun bind(item: Pessoa, listener: OnItemClickListener?) {

            item_nome.text = item.nome
            item_email.text = item.email
            item_telefone.text = item.telefone
            item_endereco.text = item.endereco

            item_update.setOnClickListener { listener?.onItemClickUpdate(item) }
            item_delete.setOnClickListener { listener?.onItemClickDelete(item) }


        }
    }
}
