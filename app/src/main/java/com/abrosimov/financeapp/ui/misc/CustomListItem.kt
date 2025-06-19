package com.abrosimov.financeapp.ui.misc

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abrosimov.financeapp.R
import com.abrosimov.financeapp.ui.theme.FinanceAppTheme

@Composable
fun CustomListItem(
    leftTitle: String,
    leftSubtitle: String? = null,
    rightTitle: String? = null,
    rightSubtitle: String? = null,
    leftIcon: String? = null,
    rightIcon: Int? = null,
    listHeight: Int = 70,
    listBackground: Color = MaterialTheme.colorScheme.background,
    leftIconBackground: Color = MaterialTheme.colorScheme.secondary,
    clickable: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(listHeight.dp)
            .background(listBackground)
            .clickable(clickable, onClick = onClick)
    ) {
        Spacer(modifier = Modifier.width(0.dp))
        leftIcon?.let{
            Text(
                leftIcon,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                modifier = Modifier
                    .size(24.dp)
                    .background(leftIconBackground, CircleShape)

            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            ListLeftInfo(leftTitle, leftSubtitle)
            ListRightInfo(rightTitle, rightSubtitle)
        }
        ListRightIcon(rightIcon)
        Spacer(modifier = Modifier.width(0.dp))
    }
}

@Composable
fun ListRightIcon(rightIcon: Int?){
    rightIcon?.let {
        Icon(
            painter = painterResource(id = rightIcon),
            contentDescription = "Перейти",
            modifier = Modifier.width(24.dp)
        )
    }
}

@Composable
fun ListLeftInfo(leftTitle: String?, leftSubtitle: String?){
    Column(horizontalAlignment = Alignment.Start) {
        leftTitle?.let {
            Text(
                leftTitle,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
            )

            leftSubtitle?.let {
                Text(
                    leftSubtitle,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                )
            }
        }
    }
}

@Composable
fun ListRightInfo(
    rightTitle: String?,
    rightSubtitle: String?,
) {
    Column(horizontalAlignment = Alignment.End) {
        rightTitle?.let {
            Text(
                rightTitle,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
            )
            rightSubtitle?.let {
                Text(
                    rightSubtitle,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                )
            }
        }
    }
}


@Preview
@Composable
fun ListItem2Preview() {
    FinanceAppTheme {
        CustomListItem(
            leftTitle = "Ремонт квартиры",
            leftSubtitle = "Ремонт - фурнитура для дверей",
            rightTitle = "100 000 Р",
            rightSubtitle = "22:01",
            leftIcon = "\uD83D\uDEE0\uFE0F",
            rightIcon = R.drawable.ic_drill_in,
            listHeight = 70,
            listBackground = MaterialTheme.colorScheme.background,
            leftIconBackground = MaterialTheme.colorScheme.secondary,
            clickable = true,
            onClick = {}
        )
    }
}