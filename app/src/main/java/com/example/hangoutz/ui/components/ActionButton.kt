package com.example.hangoutz.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.hangoutz.ui.theme.Chestnut
import com.example.hangoutz.ui.theme.Ivory

@Composable
fun ActionButton(
    buttonText: String,
    onClick: () -> (Unit)
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Ivory),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 50.dp, end = 50.dp)
            .height(50.dp),
    ) {
        Text(text = buttonText, style = MaterialTheme.typography.bodyMedium.copy(color = Chestnut))
    }
}

@Override
@Composable
fun ActionButton(
    painterResource: Int,
    buttonText: String,
    onClick: () -> (Unit)

) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .padding(bottom = 90.dp)

    ) {

        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Ivory, contentColor = Chestnut),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 60.dp)
                .height(50.dp)
                .align(Alignment.BottomCenter)

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
                //contentAlignment = Alignment.
            ) {

                Text(
                    text = buttonText,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(end = 10.dp)
                )

                Icon(
                    painter = painterResource(painterResource),
                    contentDescription = "Login icon",
                    modifier = Modifier.size(20.dp)

                )
            }

        }

Column(modifier = Modifier.align(Alignment.BottomCenter)
    .padding(bottom = 3.dp)) {
    Text(
        text = "OR",
        color = Ivory,
        modifier = Modifier
            .padding(start = 50.dp)


    )
    Text(

        text = "Create Account",
        color = Ivory,

    )
}
}
    }
