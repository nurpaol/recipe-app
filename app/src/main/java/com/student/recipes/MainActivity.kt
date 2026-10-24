package com.student.recipes

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView: ListView = findViewById(R.id.recipeListView)
        val recipeList = mutableListOf<String>()

        try {
            val inputStream: InputStream = assets.open("recipes.json")
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)
            inputStream.close()
            
            val jsonString = String(buffer, Charsets.UTF_8)
            val jsonArray = JSONArray(jsonString)

            // JSON dosyasındaki tarifleri döngüyle okuyup listeye ekliyoruz
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val name = jsonObject.getString("name")
                val calories = jsonObject.getInt("calories")
                
                // İstenen özellik: İngilizce ve kalori gösterimi
                recipeList.add("$name\nCalories: $calories kcal")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            recipeList.add("Error loading recipes.")
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, recipeList)
        listView.adapter = adapter
    }
}
