package com.example.hangoutz.ui.components

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.TextFieldValue
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.hangoutz.ui.theme.Ivory
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Dimensions


@Composable
fun NameInput(
    icon: Int,
    name: TextFieldValue,
    isReadOnly: Boolean,
    onNameChanged: (TextFieldValue) -> Unit,
    onPencilClick: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                end = Dimensions.SETTINGS_SCREEN_MEDIUM3,
                start = Dimensions.SETTINGS_SCREEN_MEDIUM3
            ),
    ) {
        val (image, text) = createRefs()
        Box(modifier = Modifier.constrainAs(image) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }) {
            NameField(
                name,
                { onNameChanged(it) },
                isReadOnly,
                focusRequester,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Box(modifier = Modifier
            .constrainAs(text) {
                start.linkTo(image.end)
            }
            .padding(Dimensions.SETTINGS_SCREEN_SMALL1)
            .fillMaxHeight()) {
            Image(
                painterResource(icon),
                "",
                modifier = Modifier
                    .size(Dimensions.SETTINGS_SCREEN_MEDIUM4)
                    .align(Alignment.Center)
                    .semantics {
                        contentDescription = Constants.SETTINGS_NAME_VALIDATOR_ICON_TAG
                    }
                    .clickable {
                        onPencilClick()
                        focusRequester.requestFocus()
                    },
                colorFilter = ColorFilter.tint(Ivory)
            )
        }
    }
}