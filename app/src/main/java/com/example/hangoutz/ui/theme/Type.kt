package com.example.hangoutz.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.hangoutz.R

// Set of Material typography styles to start with

val inter = FontFamily(
    Font(R.font.inter_variable)
)

val Typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = inter,
        fontWeight = FontWeight.W500,
        fontSize = 18.sp,
        lineHeight = 23.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = inter,
        fontWeight = FontWeight.W700,
        fontSize = 22.sp,
        lineHeight = 27.sp,
        letterSpacing = 0.1.sp
    ),
    labelSmall = TextStyle(
        fontFamily = inter,
        fontWeight = FontWeight.W400,
        fontSize = 13.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    bodySmall = TextStyle(
        fontFamily = inter,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.1.sp
    ),
    titleMedium = TextStyle(
        fontFamily = inter,
        fontWeight = FontWeight.W600,
        fontSize = 24.sp,
        lineHeight = 29.sp
    )
)