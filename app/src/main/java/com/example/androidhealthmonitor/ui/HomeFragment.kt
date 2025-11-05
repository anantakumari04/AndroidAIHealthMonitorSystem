package com.example.androidhealthmonitor.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.androidhealthmonitor.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val cvCalorieTracker = view.findViewById<CardView>(R.id.cvCalorieTracker)
        val cvWeightBmi = view.findViewById<CardView>(R.id.cvWeightBmi)
        val cvWaterReminder = view.findViewById<CardView>(R.id.cvWaterReminder)
        val cvCheckSymptoms = view.findViewById<CardView>(R.id.cvCheckSymptoms)
        val cvHealthDiet = view.findViewById<CardView>(R.id.cvHealthDiet)
        val cvSleepTime = view.findViewById<CardView>(R.id.cvSleepTime)

        cvCalorieTracker.setOnClickListener { 
            val intent = Intent(activity, CalorieTrackerActivity::class.java)
            startActivity(intent)
        }
        cvCheckSymptoms.setOnClickListener { 
            val intent = Intent(activity, AddPatientActivity::class.java)
            startActivity(intent)
        }
        cvWeightBmi.setOnClickListener { 
            val intent = Intent(activity, BmiCalculatorActivity::class.java)
            startActivity(intent)
        }
        cvWaterReminder.setOnClickListener { 
            val intent = Intent(activity, WaterReminderActivity::class.java)
            startActivity(intent)
        }
        cvHealthDiet.setOnClickListener { 
            val intent = Intent(activity, HealthDietActivity::class.java)
            startActivity(intent)
        }
        cvSleepTime.setOnClickListener { 
            val intent = Intent(activity, SleepTimeActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun openAnalysis(analysisType: String) {
        val intent = Intent(activity, AnalysisActivity::class.java)
        intent.putExtra("ANALYSIS_TYPE", analysisType)
        startActivity(intent)
    }
}
