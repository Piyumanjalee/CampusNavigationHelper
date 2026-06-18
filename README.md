# Campus Navigation Helper 🏛️📍

An Android application designed to help university students easily navigate the campus of **Rajarata University of Sri Lanka**, explore nearby attractions in the Mihintale area, and organize academic and daily notes offline.

Developed to offer utility for students, combining location services with a personal productivity space.

---

## 🌟 Features

### 1. Interactive Dashboard (`MainActivity`)
- Clean, card-based Material Design dashboard.
- Full support for **Dark Mode** to reduce eye strain and optimize battery usage.
- Simple, quick-access options for Maps, Notes, Favourites, and account settings.

### 2. Campus Information & Navigation (`CampusInfoActivity`)
- **Category-Based Locations**:
  - **Campus Locations**: Quick access to key university buildings like the Main Library, IT Faculty Building, Student Canteen, and Faculty of Applied Sciences.
  - **Mihintale Places**: Explore historical landmarks and attractions within a 5km radius (e.g., Mihintale Temple Complex, Kaludiya Pokuna, Ancient Hospital Ruins, and Mihintale Railway Station).
- **Real-World Route Estimation**:
  - Automatically calculates distance (in meters) and compass directions (e.g. North, North-East) between start and destination locations.
  - Calculates estimated walking time based on standard walking speed.
- **Indoor Floor Plans (`IndoorMapActivity`)**:
  - View multi-level directories and floor layouts for campus buildings (e.g., Main Library levels, IT Faculty labs, Applied Sciences offices) complete with graphical blueprint maps.

### 3. Interactive Google Maps (`GoogleMapActivity`)
- High-fidelity map integration with full zoom and compass control options.
- **5km Radius boundary**: Visualized as a semi-transparent purple circle centered at Mihintale.
- **Color-Coded Custom Pins**:
  - 🟢 **Green Pin**: Current Start Point.
  - 🌹 **Rose Pin**: Destination.
  - 🔵 **Azure Pins**: Campus Locations.
  - 🟡 **Yellow Pins**: Nearby Tourist Attractions.
  - 🟠 **Orange Pin**: Mihintale Center point.
- **Interactive Routing**: Clicking any pin recalculates and updates the route polyline instantly.
- **Map Mode Switcher**: Toggle easily between standard, satellite, hybrid, and terrain views.
- **Navigation Deep-Linking**: Launch Google Maps app (or web fallback) directly for real-time walking directions.

### 4. Location Bookmarking (`FavouritesActivity`)
- Save important or frequent locations directly from the Campus Info dashboard.
- Persistent bookmarks stored locally in the database.
- Dedicated Favourites explorer with an adapter pattern list (`FavouritesAdapter`) to view location details or delete bookmarks.

### 5. Local Offline Notes (`NotesActivity`, `NotesView`)
- Full **CRUD (Create, Read, Update, Delete)** note organizer.
- SQLite database backend allows you to add, read, update, or remove notes completely offline.
- Features a dynamic list (`NotesAdapter`) where the newest notes are displayed first for quick access.

### 6. Enhanced User Authentication (`LoginActivity`, `RegistrationActivity`, `ForgotPasswordActivity`)
- Secure local authentication (Register, Login, and Logout).
- **Password Recovery**: Secure password reset system using registered security details (Email + Date of Birth verification) to reset forgotten user passwords.

---

## 🛠️ Technology Stack

| Component | Technology | Description |
| :--- | :--- | :--- |
| **Language** | Java | Back-end app logic (JDK 11) |
| **UI Styling** | XML | Android Material Design Components with Dark Theme |
| **Database** | SQLite | Local offline relational database (`DatabaseHelper`) |
| **Maps Service** | Google Play Services Maps | Map rendering, markers, and custom overlays |
| **API Target** | Android SDK 36 | Compiled & target SDK level 36, min SDK level 24 |

---

## 🚀 Getting Started & Setup

### Prerequisites
Make sure you have the following installed on your machine:
- **Android Studio (Ladybug version or newer)**
- **Android SDK Level 36** (Target/Compile) / Min SDK Level 24
- **JDK 11** or higher

### Google Maps API Configuration
The project uses a root `.env` configuration file to keep API keys secure and inject them dynamically during the Gradle build phase:

1. In the project root directory, create a file named `.env`.
2. Add your Google Maps API Key to the file:
   ```env
   MAPS_API_KEY=your_google_maps_api_key_here
   ```
3. During build time, the key will be parsed and injected into the placeholder in the `AndroidManifest.xml`.

### Installation Steps
1. Clone this repository:
   ```bash
   git clone https://github.com/Piyumanjalee/campus-navigation-helper.git
   ```
2. Open the project folder in **Android Studio**.
3. Allow Android Studio to sync Gradle and download dependencies.
4. Run the project on an **Android Emulator** or a connected **Physical Device**.

---

## 💾 Local Database Schema

The app utilizes a local SQLite Database (`CampusNav.db`) with three tables:

### 1. `users` Table
- `id` (INTEGER PRIMARY KEY)
- `name` (TEXT)
- `email` (TEXT UNIQUE)
- `password` (TEXT)
- `dob` (TEXT) - Used for security verification during password recovery.

### 2. `notes` Table
- `note_id` (INTEGER PRIMARY KEY)
- `title` (TEXT)
- `content` (TEXT)

### 3. `favourites` Table
- `fav_id` (INTEGER PRIMARY KEY)
- `fav_name` (TEXT)
- `fav_description` (TEXT)

---

## 🎓 Student Details

| Student ID | Name | Index No |
| :--- | :--- | :--- |
| ICT/2022/123 | S.H.M.P.K. Senadheera | 5725 |
| ICT/2022/122 | A.W.I Ahmed | 5724 |
| ICT/2022/121 | R.M.F. Zulfa | 5723 |

---

## 📌 Project Purpose

The main goal of the **Campus Navigation Helper** is to resolve navigation hurdles for university freshmen, visitors, and students at Rajarata University of Sri Lanka while integrating local attraction details and offline tools to streamline academic routines.
