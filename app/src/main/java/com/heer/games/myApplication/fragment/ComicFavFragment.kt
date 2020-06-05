package com.heer.games.myApplication.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.heer.games.R
import com.heer.games.myApplication.database.ComicDB
import com.heer.games.myApplication.database.ComicEntity
import com.saniya.module3assignment.adapter.ComicFavRecyclerAdapter

class ComicFavFragment : Fragment() {

    private lateinit var recyclerFav: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var recyclerAdapter : ComicFavRecyclerAdapter
    lateinit var progressLayout: RelativeLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_comic_fav,container,false)
        setHasOptionsMenu(false)
        recyclerFav = view.findViewById(R.id.recyclerComic)
        layoutManager = LinearLayoutManager(activity)
        progressLayout = view.findViewById(R.id.progressLayout)

        Handler().postDelayed({
            progressLayout.visibility = View.GONE
        },2000)

        val favComicList = DbAsyncTask(activity as Context).execute().get()

        if(favComicList.isNotEmpty()) {
            recyclerAdapter = ComicFavRecyclerAdapter(activity as Context, favComicList)
            recyclerFav.adapter = recyclerAdapter
            recyclerFav.layoutManager = layoutManager
        }
        else {
            Toast.makeText(activity as Context, "No comics added to favourites", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    class DbAsyncTask(val context: Context) : AsyncTask<Void, Void, List<ComicEntity>>() {
        override fun doInBackground(vararg params: Void?): List<ComicEntity> {
            val db = Room.databaseBuilder(context, ComicDB::class.java,"comic-db").build()
            return db.comicDao().getAllComics()
        }
    }

}