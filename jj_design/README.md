# JJUPBAK Design System
## Getting Started
- [Button](#button)
    - [JjButton](#jjbutton)
    - [JjIconButton](#jjiconbutton)
    - [JjChipButton](#jjchipbutton)
    - [JjPlusButton](#jjplusbutton)
    - [JjTrendButton](#jjtrendbutton)
    - [JjMapButton](#jjmapbutton)

## Button
! Button Design Component example

![alt text](images/button_test.gif)
### JjButton
<img width = 200px height = 300px src="images/jjbutton.png">

- XML Code
```kotlin
<com.android.jj_design.button.JjButton
    android:id="@+id/jj_button"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:jjText="1234"
    app:jjTextColor="@color/color_white"/>
```
- Programmatically Code
    - Enabled Button   
    ![alt text](images/jjbutton_enabled.png)
    ```kotlin
    binding.jjButton.isEnabled = true
    ```
    - Disabled Button   
    ![alt text](images/jjbutton_disabled.png)
    ```kotlin
    binding.jjButton.isEnabled = true
    ```
    - Error Button   
    ![alt text](images/jjbutton_error.png)
    ```kotlin
    binding.jjButton.setErrorBackground()
    ```

### JjIconButton
![alt text](images/jjiconbutton.png)

- XML Code
```kotlin
<com.android.jj_design.button.JjIconButton
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:jjIconText="리뷰하기"
    app:jjIconTextColor="@color/color_666666"
    app:jjIconDrawableStart="@drawable/icon_pencil"/>
```

### JjChipButton
![alt text](images/jjchipbutton.png)

- XML Code
```kotlin
<com.android.jj_design.button.JjChipButton
    android:id="@+id/jj_chip_button"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:jjChipType="Confirm"/>
    // Delete, Confirm, Requested, Follow, Following
```

- Programmatically Code (use jjChipType)
    ```kotlin
    binding.jjChipButton.setChipItemType(Confirm)
    ```

### JjPlusButton
![alt text](images/jjplusbutton.png)

- XML Code
```kotlin
<com.android.jj_design.button.JjPlusButton
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"/>
```

### JjTrendButton

- XML Code
```kotlin
<com.android.jj_design.button.JjTrendButton
    android:id="@+id/jj_trend_button"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"/>
```

- Programmatically Code
    - Enabled Trend Button   
    ![alt text](images/jjtrendbutton_enabled.png)
        ```kotlin
        binding.jjTrendButton.isEnabled = true
        ```

    - Disabled Trend Button   
    ![alt text](images/jjtrendbutton_disabled.png)
        ```kotlin
        binding.jjTrendButton.isEnabled = false
        ```


### JjMapButton
![alt text](images/jjmapbutton.png)

- XML Code
```kotlin
<com.android.jj_design.button.JjMapButton
    android:id="@+id/jj_map_button"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginTop="10dp"
    app:jjMapType="Add"/>
    // Add, Minus, Position
```

- Programmatically Code
```kotlin
binding.jjMapButton.setJjMapType(Add) // Minus, Position
```