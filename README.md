# 🐤 FlappyFly: The Java Jumper

[![Java](https://img.shields.io/badge/Language-Java-orange.svg)](https://www.oracle.com/java/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Swing](https://img.shields.io/badge/UI-Swing-blue.svg)](https://docs.oracle.com/javase/tutorial/uiswing/)

Welcome to **FlappyFly**, a classic rendition of the endlessly addictive *Flappy Bird* game, reimagined and built with the power of **Java Swing**! Navigate a cheerful pixelated bird through an endless series of pipes, testing your reflexes and aiming for the ultimate record.

---

## 🚀 Key Features

- 🎮 **Classic Mechanics**: Simple, challenging, and addictive gameplay.
- 💾 **Persistent High Score**: Your best record is automatically saved to `highscore.txt` and loaded every time you play.
- 🎨 **Retro Pixel Art**: Hand-crafted assets for that nostalgic arcade feel.
- ⚡ **High-Performance Loop**: Smooth rendering and responsive collision detection.
- 🛠️ **Swing Implementation**: Pure Java implementation, no heavy game engines required.

---

## 🕹️ Controls

| Action | Control |
| :--- | :--- |
| **Flap / Jump** | `SPACEBAR` |
| **Start / Restart** | `SPACEBAR` |

---

## 🏃 How to Run

### Prerequisites
- **Java JDK 8 or higher** installed on your system.

### Steps
1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/FlappyFly.git
   cd FlappyFly
   ```
2. **Compile the source files**:
   ```bash
   javac App.java FlappyBird.java
   ```
3. **Execute the application**:
   ```bash
   java App
   ```

---

## 📂 Project Structure

```text
FlappyFly/
├── App.java                # Application entry point & window setup
├── FlappyBird.java         # Main game logic, rendering, and physics
├── highscore.txt           # Persistent high score data (Auto-generated)
├── flappybirdbg.png        # Background asset
├── flappybird.png          # Bird sprite
├── toppipe.png             # Pipe asset (top)
└── bottompipe.png          # Pipe asset (bottom)
```

---

## 🤝 Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📜 License

Distributed under the MIT License. See `LICENSE` for more information.

---

*Enjoy the flight!* ✈️🐤
