# 🐾 Pet Palooza

**Pet Palooza** is a Tamagotchi-style virtual pet app developed in **Java 11** using **Android Studio Ladybug**. In this game, players care for a digital pet by feeding, cleaning, and putting it to sleep. If neglected, the pet’s condition worsens — leading to a potential game over!

---

## 📦 Requirements & Environment Setup

To build and run the app, make sure you have the following set up:

* **Android Studio Ladybug** (2024.2.1 or newer)
* **Java 11**
* **Gradle** (bundled with Android Studio)
* Android device or emulator with:

  * **Minimum SDK**: 24
  * **Target SDK**: 34

---

## ⚙️ How to Build and Run Pet Palooza

1. **Clone the Repository**

   ```bash
   git clone https://github.com/strangerthandev115/pet-palooza.git
   cd pet-palooza
   ```

2. **Open in Android Studio**

   * Launch **Android Studio Ladybug**
   * Go to **File > Open**, and select the root folder of the project
   * Wait for **Gradle sync** to complete

3. **Run the App**

   * Start an Android emulator or connect a physical device (USB debugging enabled)
   * Click the green **Run** button or press **Shift + F10**
   * Select your device to launch the app

---

## 🕹️ How to Use and Test the App

Once launched, the user will:

1. **Select a name** for their pet
2. **Choose a pet avatar**
3. **Enter gameplay**, where three main buttons control your pet's care:

| Button    | Functionality                              |
| --------- | ------------------------------------------ |
| **Feed**  | Reduces hunger; shows pet satisfaction     |
| **Clean** | Restores cleanliness; displays clean state |
| **Sleep** | Allows the pet to rest and regain energy   |

* If the pet’s needs are neglected for too long, the game transitions to a **Game Over** screen.
* Timers simulate real-time need decay. For accurate testing, disable battery optimization on physical devices.

---

## 🧪 Model Evaluation Instructions

To evaluate, test, or update **Pet Palooza**, please follow the expected input flow and file structure outlined below. This app simulates a virtual pet experience where the user interacts with their pet via buttons to manage three core needs: **feeding**, **cleaning**, and **sleeping**.

---

### ✅ Required Inputs & Context by Screen

| Activity               | Required Inputs                                                                                                 |
| ---------------------- | --------------------------------------------------------------------------------------------------------------- |
| **MainActivity**       | Tap the **Start Game** button to begin the pet adoption process                                                 |
| **NameSelectActivity** | Enter a **pet name** in the text field and press the **Checkmark** button                                       |
| **PetSelectActivity**  | Tap the **Dog** or **Cat** button, then select a **Color Option (1 or 2)**, then press the **Checkmark** button |
| **GameplayActivity**   | Use the three buttons to interact: **Feed**, **Clean**, and **Sleep**                                           |
| **GameOverActivity**   | Tap the **New Adoption** button to restart the game                                                             |

---

### ⏱️ Time-Based Stat Decay

* Once in `GameplayActivity`, the pet’s stats begin **decreasing over time** (via a handler).
* If all stats reach **0**, the game will transition to the **Game Over** screen.

---

### 🔄 App Flow Summary

1. **Launch App** → `MainActivity`
2. **Start Game** → `NameSelectActivity`
3. **Enter Name + Confirm** → `PetSelectActivity`
4. **Choose Pet Type + Color + Confirm** → `GameplayActivity`
5. **Interact or Wait for Game Over** → `GameOverActivity`
6. **Tap New Adoption** → Return to Start

---

## 📁 Project Structure Highlights

```
src/
├── GameOverActivity.java        # Displays the game over screen
├── GameplayActivity.java        # Main gameplay and pet care logic
├── MainActivity.java            # Entry point to the app
├── NameSelectActivity.java      # User enters pet name
├── Pet.java                     # Manages pet state and stats
├── PetSelectActivity.java       # User selects pet avatar
res/
├── drawable/                    # Pet images and UI assets
└── layout/                      # XML layout files for all activities
```

---

## 📄 License

This project is licensed under the [Unlicense](LICENSE.md).
