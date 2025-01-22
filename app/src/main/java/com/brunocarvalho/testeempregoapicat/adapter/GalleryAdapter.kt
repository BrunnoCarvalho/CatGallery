package com.brunocarvalho.testeempregoapicat.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brunocarvalho.testeempregoapicat.R
import com.brunocarvalho.testeempregoapicat.databinding.ItemImagemBinding
import com.brunocarvalho.testeempregoapicat.model.Image
import com.squareup.picasso.Picasso

class GalleryAdapter: RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    private val listaImagens = mutableListOf<String>()

    @SuppressLint("NotifyDataSetChanged")
    fun adicionarLista(lista: List<String> ){
        listaImagens.addAll(lista)
        notifyDataSetChanged()
    }

    inner class GalleryViewHolder(val binding: ItemImagemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(imagem: String){
            Picasso.get().cancelRequest(binding.imageCat)
            Picasso.get()
                .load(imagem)
                .resize(500, 500)
                .into(binding.imageCat)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemImagemBinding.inflate(layoutInflater, parent, false)

        return GalleryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
         val imagemGato = listaImagens[position]
         holder.bind(imagemGato)
    }

    override fun getItemCount(): Int {
        return listaImagens.size
    }
}