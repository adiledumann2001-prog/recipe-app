package com.student.recipes

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.recipeListView)
        val recipeList = mutableListOf<String>()

        try {
            val inputStream = assets.open("recipes.json")
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)
            inputStream.close()
            
            val jsonArray = JSONArray(String(buffer, Charsets.UTF_8))
            for (i in 0 until jsonArray.length()) {
                val recipe = jsonArray.getJSONObject(i)
                val name = recipe.getString("name")
                val calories = recipe.getInt("calories")
                recipeList.add("$name \n🔥 Calories: $calories")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, recipeList)
    }
}
