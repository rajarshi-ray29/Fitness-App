package com.example.myapplication

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Store(@StringRes val stringResourceId: Int,
                       @DrawableRes val imageResourceId: Int) {
}