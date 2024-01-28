package com.example.expirytracker

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expirytracker.ui.theme.ExpiryTrackerTheme
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.ChronoUnit

const val WARNING_DAYS = 15
const val SAFE_DAYS = 30

//val currentTime = LocalDate.now()
val CURRENT_TIME: LocalDate = LocalDate.of(2024, 1, 25)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true
)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ItemInfoCardPreview() {
    App()
}

@Composable
fun App() {
    ExpiryTrackerTheme {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(
                topBar = { AppTopBar() },
                floatingActionButton = { AddItemFloatingActionButton() }
            ) {
                innerPadding ->
                    ItemList(SampleItemList.itemList, WARNING_DAYS, SAFE_DAYS, innerPadding)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar() {
    TopAppBar(
        title = {
            Text(text = "Expiry Tracker")
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer, titleContentColor = MaterialTheme.colorScheme.primary),
        navigationIcon = {
            Icon(
                Icons.Filled.AccountCircle,
                contentDescription = "App Icon",
                modifier = Modifier
                    .size(40.dp)
            )
        },
        actions = {
            IconButton(
                onClick = {

                },
                modifier = Modifier.offset(12.dp)
            ) {
                Icon(
                    Icons.Filled.Search,
                    "Search",
                    Modifier.size(20.dp)
                )
            }

            IconButton(
                onClick = {

                }
            ) {
                Icon(
                    Icons.Filled.Settings,
                    "Settings",
                    Modifier.size(20.dp)
                )
            }
        }
    )
}

@Composable
fun AddItemFloatingActionButton() {
    FloatingActionButton(
        onClick = {

        },
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Icon(Icons.Default.Add, contentDescription = "Add an item")
    }
}

@Composable
fun ItemList(itemInfoList: List<ItemInfo>, warningDays: Int, safeDays: Int, innerPadding: PaddingValues) {
    itemInfoList.forEach { itemInfo ->
        val daysRemaining = ChronoUnit.DAYS.between(CURRENT_TIME, itemInfo.expiryDate.localDate)
        itemInfo.daysRemaining = daysRemaining
    }

    val expiredItemList = getItemsBetweenDays(itemInfoList, max = -1L)
    val expiringSoonItemList = getItemsBetweenDays(itemInfoList, 0, safeDays - 1L)
    val safeItemList = getItemsBetweenDays(itemInfoList, safeDays.toLong())

    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(innerPadding),
    ) {
        if (expiredItemList.isNotEmpty()) {
            item {
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = "Expired",
                    modifier = Modifier.padding(all = 8.dp)
                )
            }

            items(expiredItemList) { ItemInfoCard(it) }
        }

        if (expiringSoonItemList.isNotEmpty()) {
            item {
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = "Expiring Soon",
                    modifier = Modifier.padding(all = 8.dp)
                )
            }

            items(expiringSoonItemList) { ItemInfoCard(it) }
        }

        if (safeItemList.isNotEmpty()) {
            item {
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = "Safe",
                    modifier = Modifier.padding(all = 8.dp)
                )
            }

            items(safeItemList) { ItemInfoCard(it) }
        }
    }
}

@Composable
fun ItemInfoCard(itemInfo: ItemInfo) {
    val itemColor = getItemColor(itemInfo.daysRemaining, WARNING_DAYS, SAFE_DAYS)
    var isExpended by remember { mutableStateOf(false) }

    Surface(
        shape = MaterialTheme.shapes.small,
        color = getColorWithAlpha(itemColor, 0.25F),
        modifier = Modifier
            .fillMaxWidth()
            .border(0.25.dp, getColorWithAlpha(itemColor, 0.5F), MaterialTheme.shapes.small)
            .border(0.125.dp, Color.DarkGray, MaterialTheme.shapes.small)
            .clickable { isExpended = !isExpended }
            .animateContentSize()
    ) {
        Row {
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .weight(1F)
            ) {
                ItemTitleAndExpiryIndicator(itemInfo, itemColor, isExpended)
                Spacer(modifier = Modifier.height(1.dp))
                ItemDetailAndAdditionalInformation(itemInfo, isExpended)
            }

            ItemProductLink(itemInfo)
        }
    }
}

@Composable
fun ItemTitleAndExpiryIndicator(itemInfo: ItemInfo, itemColor: Color, isExpended: Boolean) {
    Row {
        val fontSize = if (isExpended) 23.sp else TextUnit.Unspecified

        Text(
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.secondary,
            text = itemInfo.name,
            fontSize = fontSize
        )

        Spacer(modifier = Modifier.width(3.dp))

        val size = if (isExpended) 15.dp else 10.dp

        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .align(Alignment.CenterVertically)
                .background(itemColor, CircleShape)
                .border(0.5.dp, MaterialTheme.colorScheme.secondary, CircleShape)
        )
    }
}

@Composable
fun ItemDetailAndAdditionalInformation(itemInfo: ItemInfo, isExpended: Boolean) {
    if (!isExpended) {
        Text(
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
            text = "${itemInfo.expiryDate.date} ${itemInfo.expiryDate.displayableMonth}, ${itemInfo.expiryDate.year}"
        )
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            itemInfo.expiryDates.forEach {
                val daysRemaining = ChronoUnit.DAYS.between(CURRENT_TIME, it.localDate)
                val itemColor = getItemColor(daysRemaining, WARNING_DAYS, SAFE_DAYS)

                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = getColorWithAlpha(itemColor, 0.25F),
                    modifier = Modifier
                        .border(0.25.dp, getColorWithAlpha(itemColor, 0.5F), MaterialTheme.shapes.small)
                        .border(0.125.dp, Color.DarkGray, MaterialTheme.shapes.small)
                ) {
                    Text(
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        text = "${it.date} ${it.displayableMonth}, ${it.year}",
                        modifier = Modifier.padding(all = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ItemProductLink(itemInfo: ItemInfo) {
    if (itemInfo.productLink != null) {
        Column {
            val uriHandler = LocalUriHandler.current
            ElevatedButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(all = 8.dp)
                    .size(65.dp, 25.dp),
                shape = MaterialTheme.shapes.medium,
                onClick = {
                    uriHandler.openUri(itemInfo.productLink)
                }
            ) {
                Text(
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontSize = 7.sp,
                    fontWeight = FontWeight.Normal,
                    text = "Buy",
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

fun isLastDay(date: ExpiryDate): Boolean {
    return YearMonth.of(date.year, date.month).atEndOfMonth().dayOfMonth == date.date
}

fun getItemColor(daysRemaining: Long, warningDays: Int, safeDays:Int, alpha: Float = 1F): Color {
    return if (daysRemaining < 0) {
        Color(Color.Red.red, Color.Red.green, Color.Red.blue, alpha)
    } else if (daysRemaining <= warningDays) {
        val percent = (warningDays - daysRemaining) / (warningDays + 1).toFloat()
        getGradientColor(percent, Color.Yellow, Color.Red, alpha)
    } else if (daysRemaining <= safeDays) {
        val percent = (safeDays - daysRemaining) / (safeDays - warningDays + 1).toFloat()
        getGradientColor(percent, Color.Green, Color.Yellow, alpha)
    } else {
        Color(Color.Green.red, Color.Green.green, Color.Green.blue, alpha)
    }
}

fun getGradientColor(percentFinalColor: Float, startColor: Color, endColor: Color, alpha: Float = 1F): Color {
    val red = (1 - percentFinalColor) * startColor.red + percentFinalColor * endColor.red
    val green = (1 - percentFinalColor) * startColor.green + percentFinalColor * endColor.green
    val blue = (1 - percentFinalColor) * startColor.blue + percentFinalColor * endColor.blue
    return Color(red, green, blue, alpha)
}

fun getColorWithAlpha(color: Color, alpha: Float): Color {
    return Color(color.red, color.green, color.blue, alpha)
}

fun getItemsBetweenDays(itemInfoList: List<ItemInfo>, min: Long = Long.MIN_VALUE, max: Long = Long.MAX_VALUE): List<ItemInfo> {
    return itemInfoList
        .filter { it.daysRemaining in min..max }
        .sortedBy { it }
}