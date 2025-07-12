# WhatsViral - News App with Taboola Integration

A modern Android news application that displays articles with integrated Taboola recommendations.

## Features

- **News Feed**: Displays articles loaded from a remote JSON source
- **Taboola Widget**: Integrated at position 3 for mid-article recommendations
- **Taboola Feed**: Continuous scrolling recommendations at position 10
- **Modern UI**: Clean card-based design with borders and spacing
- **Image Loading**: Smooth image loading with Glide library



## Technical Implementation

### Architecture
- **Language**: Kotlin
- **UI**: RecyclerView with multiple view types
- **Image Loading**: Glide
- **Network**: HttpURLConnection for JSON fetching
- **SDK**: Taboola Android SDK 4.0.10

### Taboola Configuration
- **Publisher ID**: `sdk-tester-rnd`
- **Widget Mode**: `alternating-widget-without-video`
- **Feed Mode**: `thumbs-feed-01`
- **Page Type**: `article`
- **Target Type**: `mix`
- **Page URL**: `https://blog.taboola.com/`



## Dependencies

```kotlin
// UI & Support
implementation("androidx.appcompat:appcompat:1.6.1")
implementation("androidx.recyclerview:recyclerview:1.3.1")
implementation("androidx.cardview:cardview:1.0.0")
implementation("androidx.browser:browser:1.5.0")

// Image Loading
implementation("com.github.bumptech.glide:glide:4.16.0")
kapt("com.github.bumptech.glide:compiler:4.16.0")

// Taboola SDK
implementation("com.taboola:android-sdk:4.0.10")
```

## Setup Instructions

### Prerequisites
- Android Studio
- Android SDK 24+
- Kotlin support

### Installation
1. **Clone the repository**
   ```bash
   git clone https://github.com/EylonShetritOG/Taboola_PEM.git
   cd Taboola_PEM
   ```

2. **Open in Android Studio**
    - Open Android Studio
    - Select "Open an existing project"
    - Navigate to the cloned directory

3. **Sync the project**
    - Android Studio will automatically prompt to sync
    - Click "Sync Now"

4. **Run the app**
    - Connect an Android device or start an emulator
    - Click the "Run" button or press `Ctrl+R`

### Repository Configuration
The project includes the Taboola repository in `settings.gradle`:
```kotlin
maven { 
    url = uri("https://taboolapublic.jfrog.io/artifactory/mobile-release")
}
```

## Implementation Details

### Taboola Widget (Position 3)
- **Purpose**: Mid-article recommendations
- **Type**: Limited widget showing 1-4 recommendations
- **Configuration**: Uses XML-based TBLClassicUnit

### Taboola Feed (Position 10)
- **Purpose**: Continuous content discovery
- **Type**: Scrollable feed with unlimited content
- **Configuration**: Feed-specific placement type and mode

### RecyclerView Architecture
The adapter handles three view types:
- `VIEW_TYPE_ARTICLE`: Regular news articles
- `VIEW_TYPE_TABOOLA_WIDGET`: Taboola recommendations widget
- `VIEW_TYPE_TABOOLA_FEED`: Taboola continuous feed

### Data Source
Articles are fetched from:
```
https://s3-us-west-2.amazonaws.com/taboola-mobile-sdk/public/home_assignment/data.json
```

## Permissions

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
<uses-permission android:name="com.google.android.gms.permission.AD_ID"/>
```


## Development Notes

### Testing Taboola Integration
- The app uses demo credentials for testing
- Real production apps would require actual Taboola publisher credentials
- Test both widget and feed functionality

### Performance Considerations
- Images are cached using Glide
- Taboola units are lazily loaded
- RecyclerView handles view recycling efficiently





## Contact

**Developer**: Eylon Shetrit  



