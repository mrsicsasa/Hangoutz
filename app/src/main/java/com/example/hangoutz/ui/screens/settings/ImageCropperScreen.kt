package com.example.hangoutz.ui.screens.settings

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.example.hangoutz.ui.components.ActionButton
import com.example.hangoutz.ui.theme.SilverCloud
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Dimensions
import com.image.cropview.CropType
import com.image.cropview.EdgeType
import com.image.cropview.ImageCrop

@Composable
fun ImageCropperScreen(
    bitmap: Bitmap?,
    modifier: Modifier = Modifier,
    onBitmapCropped: (Bitmap?) -> Unit
) {
    val imageCrop = ImageCrop(bitmap!!)
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .aspectRatio(bitmap.width.toFloat() / bitmap.height.toFloat())
        ) {
            imageCrop.ImageCropView(
                guideLineColor = SilverCloud,
                guideLineWidth = Dimensions.PROFILE_IMAGE_BORDER_WIDTH,
                cropType = CropType.PROFILE_CIRCLE,
                edgeType = EdgeType.CIRCULAR,
                modifier = modifier
                    .semantics {
                        contentDescription = Constants.IMAGE_CROPPER
                    }
            )
        }

        ActionButton(
            buttonText = "Select this area",
            modifier = Modifier
                .padding(bottom = Dimensions.PHOTO_PICKER_BUTTON_PADDING)
                .semantics {
                    contentDescription = Constants.SELECT_THIS_AREA
                }
        ) {
            onBitmapCropped(imageCrop.onCrop())
        }
    }
}