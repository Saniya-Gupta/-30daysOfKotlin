package com.saniya.module3assignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.heer.games.R
import com.heer.games.myApplication.adapter.ComicDashboardRecyclerAdapter
import com.heer.games.myApplication.database.ComicEntity

class ComicFavRecyclerAdapter(val context: Context, private val comicList: List<ComicEntity>) :
    RecyclerView.Adapter<ComicFavRecyclerAdapter.FavouritesViewHolder>() {

    class FavouritesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtGenre: TextView = view.findViewById(R.id.txtGenre)
        val txtAuthor: TextView = view.findViewById(R.id.txtAuthor)
        val imgName: ImageView = view.findViewById(R.id.imgName)
        val imgFav: ImageView = view.findViewById(R.id.imgFav)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.comic_recycler, parent, false)
        return FavouritesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return comicList.size
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        val comic = comicList[position]
        holder.txtName.text = comic.name
        holder.txtGenre.text = comic.genre
        holder.txtAuthor.text = comic.author
        holder.imgName.setImageResource(comic.imgId)

        val comicEntity = ComicEntity(
            comic.comic_id,
            comic.name,
            comic.genre,
            comic.author,
            comic.imgId
        )

        if (!ComicDashboardRecyclerAdapter.DbAsyncTask(context.applicationContext, comicEntity, 3)
                .execute().get()
        ) {
            holder.imgFav.setImageResource(R.drawable.ic_fav)
        } else {
            holder.imgFav.setImageResource(R.drawable.ic_favourite)
        }

        holder.imgFav.setOnClickListener {
            if (!ComicDashboardRecyclerAdapter.DbAsyncTask(
                    context.applicationContext,
                    comicEntity,
                    3
                ).execute().get()
            ) {
                val addToFav =
                    ComicDashboardRecyclerAdapter.DbAsyncTask(context, comicEntity, 1).execute()
                        .get()
                if (addToFav) {
                    Toast.makeText(context, "Added to fav", Toast.LENGTH_SHORT).show()
                    holder.imgFav.setImageResource(R.drawable.ic_favourite)
                }
            } else {
                val removeFromFav = ComicDashboardRecyclerAdapter.DbAsyncTask(
                    context,
                    comicEntity,
                    2
                ).execute().get()
                if (removeFromFav) {
                    Toast.makeText(context, "Removed From fav", Toast.LENGTH_SHORT).show()
                    holder.imgFav.setImageResource(R.drawable.ic_fav)
                }
            }
        }
    }
}