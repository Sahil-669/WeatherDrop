🌦️ WeatherDrop

WeatherDrop is a modern weather application built with Android Studio and Jetpack Compose.
It provides accurate and real-time weather information with a clean and smooth UI, including animated search, city suggestions, and pull-to-refresh functionality.

🚀 Features

🔍 Search Locations – Search any city and get instant weather details.

📍 City Suggestions – Type in the search bar to see suggestions before selecting.

🎨 Modern UI with Jetpack Compose – Animated search field, gradients, and smooth transitions.

🛠️ Tech Stack

Kotlin – Primary language

Jetpack Compose – Declarative UI toolkit

Retrofit2 – For API calls


	
	
🔧 Setup & Installation

Clone the repository:

git clone https://github.com/<your-username>/WeatherDrop.git

cd WeatherDrop


Open the project in Android Studio.

Add your API key in local.properties or Constants.kt:

const val WEATHER_API_KEY = "your_api_key_here"


Build & run the app on your emulator/device.

📡 API Used

This project uses WeatherAPI
 (or whichever you’re using).

Current Weather

Forecasts

City Autocomplete

📂 Project Structure
WeatherDrop/
 ├── data/             # Data sources (API, models, repositories)
 ├── ui/               # Composables for UI screens
 ├── viewmodel/        # ViewModel for state handling
 ├── di/               # Dependency Injection (if using Hilt)
 └── utils/            # Helper classes

🖼️ Future Improvements

🌍 Multi-language support

📊 Hourly/Daily forecast view

🧭 GPS location auto-detection

📱 Widgets for quick access

🤝 Contributing

Contributions are welcome!

Fork the project

Create a feature branch

Submit a Pull Request
