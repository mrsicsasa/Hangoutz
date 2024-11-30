package com.example.hangoutz.ui.screens.eventDetails

import android.graphics.RenderEffect
import android.graphics.Shader
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.hangoutz.R
import com.example.hangoutz.ui.components.InputField
import com.example.hangoutz.ui.screens.login.LoginViewModel
import com.example.hangoutz.ui.theme.Ivory
import com.example.hangoutz.ui.theme.TopBarBackgroundColor
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Constants.EMAIL
import com.example.hangoutz.utils.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsScreen(navController: NavController, viewmodel: EventDetailsViewModel = hiltViewModel()) {
    val data = viewmodel.uiState.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            modifier = Modifier
                .height(Dimensions.TOP_BAR_HEIGHT)
                .wrapContentHeight(align = Alignment.CenterVertically)
                .semantics {
                    contentDescription = Constants.TOP_BAR
                },
            title = {
                Text(
                    text = Constants.TOP_BAR_TITLE,
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.semantics {
                        contentDescription = Constants.TOP_BAR_TITLE
                    }
                )

            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = TopBarBackgroundColor
            )
        )
    }){innerPadding ->


    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(R.drawable.blurred_background),
                contentScale = ContentScale.FillBounds

            )
            .padding(
                top = innerPadding.calculateTopPadding() + Dimensions.EVENTDETAILS_TOP_PADDING,
                start = Dimensions.ACTION_BUTTON_MEDIUM2,
                end = Dimensions.ACTION_BUTTON_MEDIUM2
            ).verticalScroll(rememberScrollState())

    ){
        InputField(
            "title",
            "title",
            {  },
          false,
            modifier = Modifier.semantics {
                contentDescription = Constants.LOGIN_EMAIL_INPUT_FIELD
            }
        )
        InputField(
            "description",
            "description",
            {  },
            false,
            modifier = Modifier.semantics {
                contentDescription = Constants.LOGIN_EMAIL_INPUT_FIELD
            }
        )
        InputField(
            "city",
            "city",
            {  },
            false,
            modifier = Modifier.semantics {
                contentDescription = Constants.LOGIN_EMAIL_INPUT_FIELD
            }
        )
        InputField(
            "street",
            "street",
            {  },
            false,
            modifier = Modifier.semantics {
                contentDescription = Constants.LOGIN_EMAIL_INPUT_FIELD
            }
        )
        InputField(
            "place",
            "place",
            {  },
            false,
            modifier = Modifier.semantics {
                contentDescription = Constants.LOGIN_EMAIL_INPUT_FIELD
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            InputField(
                "date",
                "date",
                { },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .semantics {
                        contentDescription = Constants.LOGIN_EMAIL_INPUT_FIELD
                    },
                R.drawable.calendar_ic,

            )
            InputField(
                "time",
                "time",
                { },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .semantics {
                        contentDescription = Constants.LOGIN_EMAIL_INPUT_FIELD
                    },
                R.drawable.timeicon
            )

}

        Text(
            "participants",
            color = Ivory,
            modifier = Modifier.padding(top = 5.dp)
        )

        HorizontalDivider(thickness = 2.dp)






    }



    }}

