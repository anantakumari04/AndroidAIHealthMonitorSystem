package com.example.androidhealthmonitor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.androidhealthmonitor.R

class HealthDietActivity : AppCompatActivity() {

    data class Diet(val title: String, val description: String, val imageRes: Int)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_diet)

        val balanced = findViewById<LinearLayout>(R.id.containerBalanced)
        val plantBased = findViewById<LinearLayout>(R.id.containerPlantBased)
        val lowCarb = findViewById<LinearLayout>(R.id.containerLowCarb)
        val timing = findViewById<LinearLayout>(R.id.containerTiming)

        val inflater = LayoutInflater.from(this)

        addSectionHeader(balanced, "Balanced and Whole-Food Diets")
        addDiets(inflater, balanced, listOf(
            Diet("DASH Diet", "Rich in fruits, vegetables, and low-fat dairy to help lower blood pressure.", R.drawable.dashdiet),
            Diet("Mediterranean Diet", "Emphasizes whole foods, healthy fats, and limits red meat for heart health.", R.drawable.mediterranean),
            Diet("Balanced Diet", "Includes a variety of foods providing all essential nutrients.", R.drawable.balancediet),
            Diet("Whole30 Diet", "A 30-day plan eliminating certain food groups to reduce inflammation.", R.drawable.whole30)
        ))

        addSectionHeader(plantBased, "Plant-Based Diets")
        addDiets(inflater, plantBased, listOf(
            Diet("Vegetarian Diet", "Excludes meat, poultry, and fish; variations include lacto-ovo and pescatarian.", R.drawable.vegetarian),
            Diet("Vegan Diet", "Excludes all animal products including dairy and eggs.", R.drawable.vegan),
            Diet("Flexitarian Diet", "Primarily vegetarian but includes occasional meat or fish.", R.drawable.flexitarian)
        ))

        addSectionHeader(lowCarb, "Low-Carbohydrate and Ketogenic Diets")
        addDiets(inflater, lowCarb, listOf(
            Diet("Ketogenic (Keto) Diet", "A very low-carb, high-fat diet for weight loss or epilepsy management.", R.drawable.keto),
            Diet("Low-Carb Diet", "Reduces carbohydrate intake for weight management (e.g. Atkins).", R.drawable.lowcarb),
            Diet("Carnivore Diet", "Focuses exclusively on animal products.", R.drawable.carnivore)
        ))

        addSectionHeader(timing, "Timing and Other Diets")
        addDiets(inflater, timing, listOf(
            Diet("Intermittent Fasting", "Cycles between eating and fasting periods.", R.drawable.fasting),
            Diet("Paleo Diet", "Focuses on foods available to early humans like meats and nuts.", R.drawable.paleo),
            Diet("Gluten-Free Diet", "Eliminates gluten for those with celiac disease.", R.drawable.glutenfree),
            Diet("Low-FODMAP Diet", "Restricts certain carbs to manage IBS symptoms.", R.drawable.lowfodmap),
            Diet("Low-Fat Diet", "Reduces fat intake for heart and weight health.", R.drawable.lowfat)
        ))
    }

    private fun addSectionHeader(container: LinearLayout, title: String) {
        val header = TextView(this)
        header.text = title
        header.textSize = 18f
        header.setPadding(0, 16, 0, 8)
        header.setTypeface(null, android.graphics.Typeface.BOLD)
        container.addView(header)
    }

    private fun addDiets(inflater: LayoutInflater, container: LinearLayout, diets: List<Diet>) {
        for (diet in diets) {
            val item = inflater.inflate(R.layout.diet_item, container, false)
            item.findViewById<TextView>(R.id.tvDietTitle).text = diet.title
            item.findViewById<TextView>(R.id.tvDietDescription).text = diet.description
            item.findViewById<ImageView>(R.id.ivDiet).setImageResource(diet.imageRes)
            container.addView(item)
        }
    }
}
