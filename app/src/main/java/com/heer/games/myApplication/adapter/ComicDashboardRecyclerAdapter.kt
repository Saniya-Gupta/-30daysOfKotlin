package com.heer.games.myApplication.adapter

import android.content.Context
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.heer.games.R
import com.heer.games.myApplication.database.ComicDB
import com.heer.games.myApplication.database.ComicEntity
import com.heer.games.myApplication.model.Comic

class ComicDashboardRecyclerAdapter(val context: Context, private val itemList: ArrayList<Comic>) :
    RecyclerView.Adapter<ComicDashboardRecyclerAdapter.ComicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comic_recycler, parent, false)
        return ComicViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        val comic = itemList[position]
        holder.txtName.text = comic.name
        holder.txtGenre.text = comic.genre
        holder.txtAuthor.text = comic.author
        holder.imgName.setImageResource(comic.imgId)

        val comicEntity = ComicEntity(
            comic.id,
            comic.name,
            comic.genre,
            comic.author,
            comic.imgId
        )

        if (!DbAsyncTask(context.applicationContext, comicEntity, 3).execute().get()) {
            holder.imgFav.setImageResource(R.drawable.ic_fav)
        } else {
            holder.imgFav.setImageResource(R.drawable.ic_favourite)
        }

        holder.imgFav.setOnClickListener {
            if (!DbAsyncTask(context.applicationContext, comicEntity, 3).execute().get()) {
                val addToFav = DbAsyncTask(context, comicEntity, 1).execute().get()
                if (addToFav) {
                    Toast.makeText(context, "Added to fav", Toast.LENGTH_SHORT).show()
                    holder.imgFav.setImageResource(R.drawable.ic_favourite)
                }
            } else {
                val removeFromFav = DbAsyncTask(context, comicEntity, 2).execute().get()
                if (removeFromFav) {
                    Toast.makeText(context, "Removed From fav", Toast.LENGTH_SHORT).show()
                    holder.imgFav.setImageResource(R.drawable.ic_fav)
                }
            }
        }
    }

    class ComicViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtGenre: TextView = view.findViewById(R.id.txtGenre)
        val txtAuthor: TextView = view.findViewById(R.id.txtAuthor)
        val imgName: ImageView = view.findViewById(R.id.imgName)
        val imgFav: ImageView = view.findViewById(R.id.imgFav)

    }

    class DbAsyncTask(val context: Context, val comicEntity: ComicEntity, val mode: Int) :
        AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            val db = Room.databaseBuilder(context, ComicDB::class.java, "comic-db")
                .build()
            when (mode) {
                1 -> {
                    db.comicDao().insertComic(comicEntity)
                    db.close()
                    return true
                }
                2 -> {
                    db.comicDao().removeComic(comicEntity)
                    db.close()
                    return true
                }
                3 -> {
                    val favComicId: ComicEntity? = db.comicDao()
                        .getComicById(comicEntity.comic_id.toString())
                    db.close()
                    return favComicId != null
                }
            }
            return false
        }
    }

}