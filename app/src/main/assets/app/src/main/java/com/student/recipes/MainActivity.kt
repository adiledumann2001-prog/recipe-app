package com.student.recipes

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
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
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val jsonStr = String(buffer, Charsets.UTF_8)

            val jsonArray = JSONArray(jsonStr)
            for (i in 0 until jsonArray.length()) {
                val recipe = jsonArray.getJSONObject(i)
                val name = recipe.getString("name")
                val calories = recipe.getInt("calories")
                val isVegan = recipe.getBoolean("vegan")
                
                var displayText = "$name \n🔥 Calories: $calories"
                if (isVegan) displayText += " (🌱 Vegan)"
                
                recipeList.add(displayText)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            recipeList.add("Error loading recipes.")
        }

        val adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recipeList) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val textView = view.findViewById<TextView>(android.R.id.text1)
                textView.setTextColor(Color.parseColor("#333333"))
                textView.textSize = 18f
                textView.setPadding(40, 40, 40, 40)
                return view
            }
        }
        listView.adapter = adapter
    }
}
