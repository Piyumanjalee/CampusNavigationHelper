# Campus Navigation Helper

## Project Overview
**Campus Navigation Helper** is an Android application designed to assist university students, staff, and visitors in easily navigating the campus premises. The app provides a comprehensive directory of important locations, interactive mapping, and a personalized experience for users to save their favorite spots.

## Key Features
* **User Authentication:** Secure Registration and Login system using local SQLite database.
* **Session Management:** Keeps users logged in using `SharedPreferences` until they choose to log out.
* **Location Directory:** A clean, scrollable list (using `RecyclerView`) of key campus locations (e.g., Library, IT Faculty, Canteens).
* **Favorite Locations:** Logged-in users can bookmark specific locations to their personalized 'Favorites' list.
* **Interactive Map Integration:** View campus locations on a real-world map (Powered by Google Maps API).

##  Technologies & Tools Used
* **Platform:** Android
* **Programming Language:** Java
* **UI/UX:** XML (LinearLayout, ConstraintLayout, RecyclerView, CardView)
* **Local Database:** SQLite (Relational DB for Users, Locations, and Favorites)
* **Session Tracking:** SharedPreferences
* **APIs:** Google Maps SDK for Android
* **IDE:** Android Studio

##  How to Run the Project (Installation)
1. Clone the repository to your local machine:
   ```bash
   git clone [https://github.com/your-username/CampusNavigationHelper.git](https://github.com/your-username/CampusNavigationHelper.git)
