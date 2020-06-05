package com.heer.games.myApplication.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.heer.games.R
import com.heer.games.myApplication.adapter.ComicDashboardRecyclerAdapter
import com.heer.games.myApplication.model.Comic

class ComicDashboardFragment: Fragment() {

    private val comicInfoList = arrayListOf(
        Comic(
            1,
            "Zombie Breakers",
            "Thriller / Action",
            "Author: Daijiachong",
            R.drawable.zombie_breakers
        ),
        Comic(
            2,
            "Across The Dangerous Ocean",
            "Mystery / Heartwarming",
            "Author: Extreme Culture",
            R.drawable.across_the_dangerous_ocean
        ),
        Comic(
            3,
            "Pharoah's Concubine",
            "Ancient / Fictional",
            "Author: Erciyuan",
            R.drawable.pharoahs_concubine
        ),
        Comic(4, "Glacious", "Fantasy / Dragons", "Author: Yaruno and Nawa", R.drawable.glacious),
        Comic(
            5,
            "Bride of The Black Sea",
            "Supernatural / Mystery",
            "Author: Migu Comic",
            R.drawable.bride_of_the_black_sea
        ),
        Comic(6, "Frozen Heart", "Fantasy / Drama", "Author: Fireytika", R.drawable.frozen_heart)
    )

    private lateinit var recyclerComic: RecyclerView

    lateinit var layoutManager: RecyclerView.LayoutManager

    lateinit var recyclerAdapter: ComicDashboardRecyclerAdapter

    lateinit var progressLayout: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_comic_dashboard, container, false)

        setHasOptionsMenu(true)

        recyclerComic = view.findViewById(R.id.recyclerComic)

        layoutManager = LinearLayoutManager(activity)

        progressLayout = view.findViewById(R.id.progressLayout)

        // Send data to adapter
        recyclerAdapter =
            ComicDashboardRecyclerAdapter(activity as Context, comicInfoList)
        recyclerComic.adapter = recyclerAdapter
        recyclerComic.layoutManager = layoutManager

        Handler().postDelayed({
            progressLayout.visibility = View.GONE
        },2000)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        inflater.inflate(R.menu.menu_read, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favList -> {
                    openFav()
                }
            R.id.action_games -> {
                activity?.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openFav() {
        activity?.title = "My Favourites List"
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.frameLayout, ComicFavFragment())
            ?.addToBackStack(null)?.commit()
    }
}