package com.student.recipes

import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView: ListView = findViewById(R.id.recipeListView)
        val recipeList = ArrayList<HashMap<String, String>>()

        try {
            val inputStream: InputStream = assets.open("recipes.json")
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)
            inputStream.close()
            
            val jsonString = String(buffer, Charsets.UTF_8)
            val jsonArray = JSONArray(jsonString)

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val map = HashMap<String, String>()
                
                map["name"] = jsonObject.getString("name")
                map["calories"] = "🔥 Calories: " + jsonObject.getInt("calories") + " kcal"
                
                val ingredientsArray = jsonObject.getJSONArray("ingredients")
                var ingredientsText = "Ingredients:\n"
                
                for (j in 0 until ingredientsArray.length()) {
                    ingredientsText += "• " + ingredientsArray.getString(j)
                    if (j < ingredientsArray.length() - 1) {
                        ingredientsText += "\n"
                    }
                }
                map["ingredients"] = ingredientsText
                
                recipeList.add(map)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val from = arrayOf("name", "calories", "ingredients")
        val to = intArrayOf(R.id.recipeName, R.id.recipeCalories, R.id.recipeIngredients)

        val adapter = SimpleAdapter(this, recipeList, R.layout.list_item_recipe, from, to)
        listView.adapter = adapter
    }
}
