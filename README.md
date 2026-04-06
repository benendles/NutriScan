# NutriScan — Android Frontend

A complete Kotlin Android frontend for the NutriScan nutrition tracking app.

## Color Palette
| Name        | Hex       | Usage                     |
|-------------|-----------|---------------------------|
| Charcoal    | #1E1E1A   | Hero sections, nav bar    |
| Cream       | #F5F0E8   | App background            |
| Olive       | #3D4A2E   | Primary buttons, accents  |
| Olive Mid   | #8FA672   | Ring progress, highlights |
| Olive Pale  | #C8D4B0   | Subtle labels on dark bg  |
| Rust        | #C4522A   | Voice card, warnings      |
| Amber       | #D4A03A   | Suggestion cards          |

## Fonts (add to /res/font/)
Download and place in `app/src/main/res/font/`:
- **Syne** (400, 700) — https://fonts.google.com/specimen/Syne
- **DM Mono** (400) — https://fonts.google.com/specimen/DM+Mono

## Project Structure
```
app/src/main/
├── java/com/nutriscan/app/
│   ├── MainActivity.kt
│   ├── data/model/Models.kt
│   └── ui/
│       ├── home/   HomeFragment, FoodEntryAdapter, CalorieRingView
│       ├── scan/   ScanFragment, ScanOverlayView, DetectedFoodAdapter
│       ├── voice/  VoiceFragment, WaveformView
│       ├── log/    LogFragment, LogFoodAdapter
│       ├── progress/ ProgressFragment
│       └── ai/     AiFragment
├── res/
│   ├── layout/     All XML screens
│   ├── drawable/   All shape/vector drawables
│   ├── navigation/ nav_graph.xml
│   ├── values/     colors, strings, themes, dimens
│   ├── menu/       bottom_nav_menu.xml
│   ├── anim/       slide transitions
│   └── font/       syne.ttf, syne_bold.ttf, dm_mono.ttf
└── AndroidManifest.xml
```

## Setup
1. Open in Android Studio (Hedgehog or later)
2. Add font files to `res/font/`
3. Sync Gradle
4. Run on device or emulator (API 26+)

## Dependencies (app/build.gradle)
- Navigation Component 2.7.7
- CameraX 1.3.1
- MPAndroidChart v3.1.0
- Retrofit 2.9.0
- Material Components 1.11.0

## Screens
| Screen   | Fragment          | Nav ID            |
|----------|-------------------|-------------------|
| Home     | HomeFragment      | homeFragment      |
| Scan     | ScanFragment      | scanFragment      |
| Voice    | VoiceFragment     | voiceFragment     |
| Log      | LogFragment       | logFragment       |
| Progress | ProgressFragment  | progressFragment  |
| AI       | AiFragment        | aiFragment        |

## Backend Integration Points
- `ScanFragment.showResults()` — replace with real API call uploading image
- `VoiceFragment.onResult()` — send spoken text to NLP endpoint
- `LogFragment` / `ProgressFragment` — replace static data with Room DB or API
