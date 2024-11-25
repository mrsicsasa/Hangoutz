package com.example.hangoutz.ui.screens.settingsscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.hangoutz.R
import com.example.hangoutz.ui.components.nameField
import com.example.hangoutz.ui.theme.Dimensions.Medium3
import com.example.hangoutz.ui.theme.Dimensions.Medium4
import com.example.hangoutz.ui.theme.Dimensions.Small1
import com.example.hangoutz.ui.theme.Ivory

@Composable
fun NameInput(
    name: String, isReadOnly: Boolean, onNameChanged: (String) -> Unit, onPencilClick: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Medium3, Medium3),
    ) {
        val (image, text) = createRefs()
        Box(modifier = Modifier.constrainAs(image) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }) {
            nameField(
                name,
                { onNameChanged(it) },
                isReadOnly,
                focusRequester,
                modifier = Modifier
                    .align(Alignment.Center)
                    .testTag("nameField")
            )
        }
        Box(modifier = Modifier
            .constrainAs(text) {
                start.linkTo(image.end)
            }
            .padding(Small1)
            .fillMaxHeight()) {

            Image(
                painterResource(R.drawable.pencil),
                "Name change icon",
                modifier = Modifier
                    .size(Medium4)
                    .align(Alignment.Center)
                    .testTag("nameEditIcon")
                    .clickable {
                        onPencilClick()
                        focusRequester.requestFocus()
                    },
                colorFilter = ColorFilter.tint(Ivory)
            )
        }
    }
}