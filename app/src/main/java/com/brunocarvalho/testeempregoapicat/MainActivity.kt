package com.brunocarvalho.testeempregoapicat

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.brunocarvalho.testeempregoapicat.adapter.GalleryAdapter
import com.brunocarvalho.testeempregoapicat.api.RetrofitService
import com.brunocarvalho.testeempregoapicat.databinding.ActivityMainBinding
import com.brunocarvalho.testeempregoapicat.model.GallerySearchResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var paginaAtual = 1

    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val retrofit by lazy {
        RetrofitService.retrofit
    }

    private lateinit var galleryAdapter: GalleryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        inicializarView()
    }

    override fun onStart() {
        super.onStart()
        pesquisarImagens()
    }

    private fun inicializarView() {

        galleryAdapter = GalleryAdapter()
        binding.rvGallery.adapter = galleryAdapter
        binding.rvGallery.layoutManager = GridLayoutManager(this, 3)
        binding.rvGallery.addOnScrollListener( object : OnScrollListener(){

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val podeDescerVerticalmente = recyclerView.canScrollVertically(1)
                if(!podeDescerVerticalmente){
                    pesquisarImagensProximaPagina()
                }

            }
        })
    }

    private fun pesquisarImagensProximaPagina(){
        paginaAtual++
        pesquisarImagens(paginaAtual)
    }


    private fun pesquisarImagens(pagina: Int = 1) {

        CoroutineScope(Dispatchers.IO).launch {

            var resposta: Response<GallerySearchResponse>? = null

            try {
                resposta = retrofit.pesquisarImagens(page = pagina)
            }catch (e: Exception){
                exibirMensagem("Erro ao recuperar dados")
            }

            if(resposta != null){

                if(resposta.isSuccessful){

                    val galeria = resposta.body()
                    val listaDados = galeria?.data

                    val listaUrlImagens = mutableListOf<String>()
                    listaDados?.forEach { dado ->
                        if(dado.images != null){
                            dado.images.forEach{ imagem ->
                                val tipo = imagem.type
                                if( tipo == "image/jpeg" ){
                                    listaUrlImagens.add( imagem.link )
                                }
                            }
                        }
                    }

                    withContext(Dispatchers.Main){
                        galleryAdapter.adicionarLista(listaUrlImagens)
                    }
                }

            }else{
                exibirMensagem("Erro ao recuperar dados")
            }
        }

    }

    private fun exibirMensagem(mensagem: String) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
    }

}