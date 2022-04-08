<img src="https://github.com/exyte/Grid/raw/media/Assets/header.png">
<img align="right" height="700" src="https://user-images.githubusercontent.com/57913130/162217415-aaacfaa8-9ae0-427f-a88a-ad98a8a92a06.png"/>

<p><h1 align="left">ShapedBackground</h1></p>

<p><h4>Repo for the Instagram-like shaped background on Android</h4></p>

___

<p> We are a development agency building
  <a href="https://clutch.co/profile/exyte#review-731233?utm_medium=referral&utm_source=github.com&utm_campaign=phenomenal_to_clutch">phenomenal</a> apps.</p>

</br>

<a href="https://exyte.com/contacts"><img src="https://i.imgur.com/vGjsQPt.png" width="134" height="34"></a> <a href="https://twitter.com/exyteHQ"><img src="https://i.imgur.com/DngwSn1.png" width="165" height="34"></a>

</br>

[![Version](https://img.shields.io/jitpack/v/github/exyte/ShapedBackgroundAndroid?label=version)](https://github.com/exyte/ShapedBackgroundAndroid)
[![License](https://img.shields.io/github/license/exyte/ShapedBackgroundAndroid)](https://github.com/exyte/ShapedBackgroundAndroid)
[![API](https://img.shields.io/badge/API-17%2B-green)](https://github.com/exyte/ShapedBackgroundAndroid)

## Overview

ShapedBackground is an easy way to colour your backdrop as in Instagram stories

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
In addition to ```TextView```, you can also use the ShapedBackground for ```EditText```

<img height="400" src="https://user-images.githubusercontent.com/57913130/162227748-abc47483-9ed3-46a0-8a48-d722dcf42a0d.gif">

## Download

...

## Requirements
Min SDK 17+


