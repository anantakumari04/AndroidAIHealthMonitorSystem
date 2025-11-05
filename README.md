# 🩺 AI Health Monitor – Android Application

<p align="center">
  <img src="Screenshot 2025-11-05 133303.png" alt="Home Screen" width="300">
</p>

A smart **AI-powered Android Health Monitoring System** where users can **Login/Signup** and access a **Home Dashboard** with 6 health monitoring features such as Blood Pressure, Calorie & BMI, Water Reminder, Check Symptoms (AI-based), Healthy Diets & Sleep Timer Alarm.  
Water reminder sends **notifications**, and the Sleep Timer triggers an **alarm ring**. In the Symptom Checker, users can add **patient details** and the condition is **analyzed by AI**.

---

## ✅ Features

| Feature | Description |
|---------|-------------|
| 🔐 **User Authentication** | Login & Signup using Firebase Authentication. |
| 🏠 **Home Fragment with 6 Cards** | Blood Pressure, Calorie Tracker, BMI/Weight, Water Reminder, Check Symptoms (AI), Healthy Diets, Sleep Timer. |
| 🤖 **AI Symptom Checker** | User enters symptoms and patient details → AI analyzes the possible health condition. |
| 💧 **Water Reminder** | Sends notifications at regular intervals to drink water. |
| ⏰ **Sleep Timer Alarm** | Set alarm time → mobile rings at the selected wake-up time. |
| ❤️ **Blood Pressure Tracker** | Allows users to store and view BP readings. |
| 📊 **BMI & Calorie Management** | Calculates BMI and tracks calories. |
| 🥗 **Healthy Diet Suggestions** | Suggests diets based on user’s health metrics. |

---

## 🖼 UI Preview (Home Screen)


<p align="center">
  <img src="images/home_screen.png" alt="Home Screen UI" width="350">
</p>

---

## 📂 Project Structure

```bash
AI-Health-Monitor/
│
├── app/src/main/java/com/healthmonitor/
│   ├── activities/
│   │   ├── LoginActivity.java
│   │   ├── SignupActivity.java
│   │   ├── MainActivity.java
│   ├── fragments/
│   │   ├── HomeFragment.java
│   │   ├── WaterReminderFragment.java
│   │   ├── SleepTimerFragment.java
│   │   ├── SymptomCheckFragment.java
│   ├── utils/
│   ├── adapters/
│   ├── models/
│
├── app/src/main/res/
│   ├── layout/
│   ├── drawable/
│   ├── values/
│
├── README.md
└── build.gradle


🧠 AI Symptom Analysis
Input Field	Details
👤 Patient Info	Name, Age, Gender, Medical History
📝 Symptoms	User enters symptoms (e.g. headache, fever)
⚙️ AI Processing	Model predicts possible condition
📍 Output	Suggested diagnosis + precautions


🔔 Water Reminder Notification Logic
Step	Task
1️⃣	User selects reminder time interval
2️⃣	WorkManager / AlarmManager schedules task
3️⃣	Notification shows: “💧 Time to drink water!”
4️⃣	Click → opens Water Reminder Fragment



⏰ Sleep Timer Alarm Flow
Step	Description
✔ User sets sleep time & wake-up time	
✔ Alarm scheduled via AlarmManager	
✔ At time → ringtone + vibration alert	
✔ User can dismiss or snooze

🚀 How to Run the Project

# 1. Clone this repository
git clone https://github.com/yourusername/AI-Health-Monitor.git

# 2. Open in Android Studio

# 3. Add your Firebase google-services.json file under /app directory

# 4. Sync Gradle and Run on Emulator / Real Device


🤝 Contribution

Want to contribute?

1. Fork the project
2. Create your branch (feature-new)
3. Commit changes (git commit -m "Added new feature")
4. Push the branch (git push origin feature-new)
5. Open Pull Request
