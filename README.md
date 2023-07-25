<a href="https://exyte.com/"><picture><source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/exyte/media/master/common/header-dark.png"><img src="https://raw.githubusercontent.com/exyte/media/master/common/header-light.png"></picture></a>

<a href="https://exyte.com/"><picture><source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/exyte/media/master/common/our-site-dark.png" width="80" height="16"><img src="https://raw.githubusercontent.com/exyte/media/master/common/our-site-light.png" width="80" height="16"></picture></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="https://twitter.com/exyteHQ"><picture><source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/exyte/media/master/common/twitter-dark.png" width="74" height="16"><img src="https://raw.githubusercontent.com/exyte/media/master/common/twitter-light.png" width="74" height="16">
</picture></a> <a href="https://exyte.com/contacts"><picture><source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/exyte/media/master/common/get-in-touch-dark.png" width="128" height="24" align="right"><img src="https://raw.githubusercontent.com/exyte/media/master/common/get-in-touch-light.png" width="128" height="24" align="right"></picture></a>

<img align="right" width="480" src="https://user-images.githubusercontent.com/57913130/189569490-71862ab5-5004-4374-9b5d-abf131264431.gif"/>


<p><h1 align="left">ShapedBackground</h1></p>

<p><h4>Repo for the Instagram-like shaped background on Android</h4></p>

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

Android View

```implementation 'com.github.exyte.ShapedBackgroundAndroid:shapedbackground:1.1.1'```

Jetpack Compose

```implementation 'com.github.exyte.ShapedBackgroundAndroid:shapedbackgroundcompose:1.1.1'```


## Requirements
<p>Android View: Min SDK 17+</p>
<p>Jetpack Compose: Min SDK 21+</p>
