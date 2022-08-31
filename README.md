<img src="https://github.com/exyte/Grid/raw/media/Assets/header.png">
<img align="right" height="700" src="https://user-images.githubusercontent.com/57913130/187610131-0edd19e6-56de-4316-a3c0-57eac9ca14bf.gif"/>


<p><h1 align="left">ShapedBackground</h1></p>

<p><h4>Repo for the Instagram-like shaped background on Android</h4></p>

___

<p> We are a development agency building
  <a href="https://clutch.co/profile/exyte#review-731233?utm_medium=referral&utm_source=github.com&utm_campaign=phenomenal_to_clutch">phenomenal</a> apps.</p>

</br>

<a href="https://exyte.com/contacts"><img src="https://i.imgur.com/vGjsQPt.png" width="134" height="34"></a> <a href="https://twitter.com/exyteHQ"><img src="https://i.imgur.com/DngwSn1.png" width="165" height="34"></a>

</br>

[![Version](https://img.shields.io/jitpack/v/github/exyte/ShapedBackgroundAndroid?label=version)](https://github.com/exyte/ShapedBackgroundAndroid)
[![License](https://img.shields.io/github/license/exyte/shapedBackgroundAndroid)](https://github.com/exyte/ShapedBackgroundAndroid)
[![API](https://img.shields.io/badge/API-17%2B-green)](https://github.com/exyte/ShapedBackgroundAndroid)
[![API](https://img.shields.io/badge/Compose%20API%20-21%2B-brightgreen)](https://github.com/exyte/ShapedBackgroundAndroid)


## Overview

ShapedBackground is an easy way to colour your backdrop as in Instagram stories
<p><h4>Android View</h4></p>

```kotlin
text.roundedBackground {
    backgroundColor = getColor(R.color.serenade)
    shadow {
        dx = 5f
        dy = 5f
        radius = 10f
    }
}
```
You can also add a gradient and set the background parameters

```kotlin
binding.text.roundedBackground {
    gradient = arrayListOf(Color.MAGENTA,Color.CYAN)
    cornerRadius = 40f
    paddingVertical = 5f
    paddingHorizontal = 5f
}
```
The ShapedBackground drawable uses View paddings to draw. You need to specify paddings to make the background drawable rendered without cropping:

```android:padding="@dimen/your_padding"```

<br/>

In addition to ```TextView```, you can also use the ShapedBackground for ```EditText```

<p><h4>Jetpack Compose</h4></p>

```kotlin
RoundedBackgroundText(
    value = text,
    backgroundParams = BackgroundParams(
        paddingHorizontal = 15.dp,
        paddingVertical = 15.dp,
        cornerRadius = 15.dp,
        backgroundColor = Zeus,
        shadow = ShadowParams(
            dx = 2.dp,
            dy = 2.dp,
            radius = 1.dp
        )
    )
)
```

A gradient can be set instead of a solid background colour:

```kotlin
backgroundParams = BackgroundParams(
    gradient = arrayListOf(Color.Black, Color.Blue)
)
```


## Download

1. Add the repository

```
repositories {
	...
	maven { url 'https://jitpack.io' }
}
```
2. Add the dependency:

```implementation 'com.github.exyte:ShapedBackgroundAndroid:1.0.0'```

## Requirements
<p>Android View: Min SDK 17+</p>
<p>Jetpack Compose: Min SDK 21+</p>
