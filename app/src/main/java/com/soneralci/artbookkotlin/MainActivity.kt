package com.soneralci.artbookkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.soneralci.artbookkotlin.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var artList : ArrayList<Art>
    private lateinit var artAdapter : ArtAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        artList = ArrayList<Art>()

        artAdapter = ArtAdapter(artList)
        binding.recylerView.layoutManager = LinearLayoutManager(this)
        binding.recylerView.adapter = artAdapter


        try {
            val database = this.openOrCreateDatabase("Arts", MODE_PRIVATE,null)
            val cursor = database.rawQuery("SELECT * FROM arts", null)
            val artNameIx = cursor.getColumnIndex("artname")
            val idIx = cursor.getColumnIndex("id")

            while (cursor.moveToNext()){
                val name = cursor.getString(artNameIx)
                val id = cursor.getInt(idIx)
                val art = Art(name, id)
                artList.add(art)
            }
            artAdapter.notifyDataSetChanged()
            cursor.close()

        }catch (e : Exception){
            e.printStackTrace()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //Inflater
       val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.art_menu,menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.add_art_item) {
            val intent = Intent(this,ArtActivity::class.java)
            intent.putExtra("info","new")
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }
}