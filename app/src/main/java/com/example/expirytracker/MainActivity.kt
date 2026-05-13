package com.example.expirytracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expirytracker.pojo.CategoryInfo
import com.example.expirytracker.pojo.ExpiryDate
import com.example.expirytracker.pojo.ProductInfo
import com.example.expirytracker.pojo.getProductsBetweenDays
import com.example.expirytracker.ui.theme.ExpiryTrackerTheme
import java.time.LocalDate
import java.time.temporal.ChronoUnit

// TODO make configurable
const val WARNING_DAYS = 15
const val SAFE_DAYS = 30

// TODO change to current time
//val currentTime = LocalDate.now()
val CURRENT_TIME: LocalDate = LocalDate.of(2024, 1, 25)
val CATEGORIES: List<CategoryInfo> = listOf(
    CategoryInfo("Expired", maxDays = -1L),
    CategoryInfo("Expiring Soon", 0L, SAFE_DAYS - 1L),
    CategoryInfo("Safe", SAFE_DAYS.toLong())
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

//@Preview(
//    name = "Light Mode",
//    showBackground = true
//)
//@Preview(
//    name = "Dark Mode",
//    showBackground = true,
//    uiMode = Configuration.UI_MODE_NIGHT_YES
//)
//@Composable
//fun CollapsedComponentPreviewTest() {
//    val productInfo = ProductInfo(
//        "Milk",
//        sortedSetOf(ExpiryDate(30, 1, 2024), ExpiryDate(15, 2, 2024)),
//        "https://www.amazon.com/mery-Grass-Organic-Whole-Supportibackl-248/dp/B06ZYNZ7NF"
//    )
//    ProductInfoCard(productInfo, false)
////    App()
//}
//
//@Preview(
//    name = "Light Mode",
//    showBackground = true
//)
//@Preview(
//    name = "Dark Mode",
//    showBackground = true,
//    uiMode = Configuration.UI_MODE_NIGHT_YES
//)
//@Composable
//fun ExpendedComponentPreview() {
//    val productInfo = ProductInfo(
//        "Milk",
//        sortedSetOf(ExpiryDate(30, 1, 2024), ExpiryDate(15, 2, 2024)),
//        "https://www.amazon.com/mery-Grass-Organic-Whole-Supportibackl-248/dp/B06ZYNZ7NF"
//    )
//    ProductInfoCard(productInfo, true)
//}

@Composable
fun App() {
    val productList = loadData()

    ExpiryTrackerTheme {
        Scaffold(
            topBar = { AppTopBar() },
            floatingActionButton = { ItemAdditionFloatingActionButton() }
        ) { innerPadding ->
            CategorizedProductList(productList, innerPadding)
        }
    }
}

fun loadData() : List<ProductInfo> {
    return SampleProductList.productList
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar() {
    TopAppBar(
        title = {
            Text(text = "Expiry Tracker")
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
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
fun ItemAdditionFloatingActionButton() {
    FloatingActionButton(
        onClick = {

        },
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Icon(Icons.Default.Add, contentDescription = "Add an item")
    }
}

@Composable
fun CategorizedProductList(
    productInfoList: List<ProductInfo>,
    innerPadding: PaddingValues
) {
    productInfoList.forEach { productInfo ->
        productInfo.daysRemaining = ChronoUnit.DAYS
            .between(CURRENT_TIME, productInfo.expiryDate.localDate)
    }

    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(innerPadding)
    ) {
        CATEGORIES.forEach { categoryInfo ->
            val products = productInfoList.getProductsBetweenDays(categoryInfo.minDays, categoryInfo.maxDays)
                .sortedBy { it }
            if (products.isNotEmpty()) {
                item { CategoryText(categoryInfo.name) }
                items(products) { ProductInfoCard(it) }
            }
        }
    }
}

@Composable
fun CategoryText(text: String) {
    Text(
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface,
        text = text,
        modifier = Modifier.padding(all = 8.dp)
    )
}

@Composable
fun ProductInfoCard(productInfo: ProductInfo, shouldExpend: Boolean = false) {
    val productColor = getProductColor(productInfo.daysRemaining, WARNING_DAYS, SAFE_DAYS)

    var isExpended by remember { mutableStateOf(shouldExpend) }
    val surfaceColor by animateColorAsState(
        if (isExpended) MaterialTheme.colorScheme.surface else getProductInfoSurfaceColor(productColor),
        label = "ProductInfo Card surface color"
    )

    ExpirySurface(
        color = productColor,
        surfaceColor = surfaceColor,
        modifier = Modifier
            .clickable { isExpended = !isExpended }
            .animateContentSize()
    ) {
        Column {
            Row {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .weight(1F)
                ) {
                    ProductTitleAndExpiryIndicator(productInfo, productColor, isExpended)
                    Spacer(modifier = Modifier.height(1.dp))
                    ProductDetailAndAdditionalInformation(productInfo, isExpended)
                }

                // TODO touch left ear with left hand i.e. simplify code bruh
                if (!isExpended)
                    ProductLink(productInfo, Modifier.padding(all = 8.dp))
            }

            if (isExpended) {
                IconButton(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(25.dp),
                    onClick = {

                    }
                ) {
                    Icon(
                        Icons.Filled.Delete,
                        "Delete Item",
                        Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))
            }
        }
    }
}

@Composable
fun ExpirySurface(
    color: Color,
    modifier: Modifier = Modifier,
    surfaceColor: Color = getProductInfoSurfaceColor(color),
    content: @Composable () -> Unit
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = surfaceColor,
        modifier = modifier
            .fillMaxWidth()
            .border(0.25.dp, getColorWithAlpha(color, 0.5F), MaterialTheme.shapes.small)
            .border(0.125.dp, Color.DarkGray, MaterialTheme.shapes.small),
        content = content
    )
}

@Composable
fun ProductTitleAndExpiryIndicator(productInfo: ProductInfo, productColor: Color, isExpended: Boolean) {
    Row {
        val fontSize = if (isExpended) 23.sp else TextUnit.Unspecified

        Text(
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.secondary,
            text = productInfo.name,
            fontSize = fontSize
        )

        Spacer(modifier = Modifier.width(3.dp))

        val size = if (isExpended) 15.dp else 10.dp

        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .align(Alignment.CenterVertically)
                .background(productColor, CircleShape)
                .border(0.5.dp, MaterialTheme.colorScheme.secondary, CircleShape)
        )

        Spacer(modifier = Modifier.weight(1F))

        if (isExpended)
            ProductLink(productInfo, Modifier.padding(top = 4.dp))
    }
}

@Composable
fun ProductDetailAndAdditionalInformation(productInfo: ProductInfo, isExpended: Boolean) {
    if (!isExpended) {
        Text(
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
            text = "${productInfo.expiryDate.date} ${productInfo.expiryDate.displayableMonth}, ${productInfo.expiryDate.year}"
        )
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(top = 3.dp)
        ) {
            productInfo.expiryDates.forEach {
                ItemExpiryInfo(it)
            }
        }
    }
}

@Composable
fun ProductLink(productInfo: ProductInfo, modifier: Modifier = Modifier) {
    if (productInfo.productLink != null) {
        val uriHandler = LocalUriHandler.current
        ElevatedButton(
            modifier = modifier
                .size(65.dp, 25.dp),
            shape = MaterialTheme.shapes.medium,
            onClick = {
                uriHandler.openUri(productInfo.productLink)
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

@Composable
fun ItemExpiryInfo(expiryDate: ExpiryDate) {
    val daysRemaining = ChronoUnit.DAYS.between(CURRENT_TIME, expiryDate.localDate)
    // TODO Calculate once?
    val itemColor = getProductColor(daysRemaining, WARNING_DAYS, SAFE_DAYS)

    ExpirySurface(
        color = itemColor
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(2.dp))

            Text(
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                text = "${expiryDate.date} ${expiryDate.displayableMonth}, ${expiryDate.year}",
                modifier = Modifier
                    .padding(all = 4.dp)
                    .weight(1F)
            )

            ItemExpiryInfoIcon(Icons.Filled.Edit, "Edit Item") {

            }

            Spacer(modifier = Modifier.width(2.dp))

            ItemExpiryInfoIcon(Icons.Filled.Delete, "Delete Item") {

            }

            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}

@Composable
fun ItemExpiryInfoIcon(
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    IconButton(
        modifier = Modifier
            .size(20.dp),
        onClick = onClick
    ) {
        Icon(
            imageVector,
            contentDescription,
            Modifier.size(15.dp)
        )
    }
}

fun getProductColor(daysRemaining: Long, warningDays: Int, safeDays: Int, alpha: Float = 1F): Color {
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

fun getGradientColor(
    percentFinalColor: Float,
    startColor: Color,
    endColor: Color,
    alpha: Float = 1F
): Color {
    val red = (1 - percentFinalColor) * startColor.red + percentFinalColor * endColor.red
    val green = (1 - percentFinalColor) * startColor.green + percentFinalColor * endColor.green
    val blue = (1 - percentFinalColor) * startColor.blue + percentFinalColor * endColor.blue
    return Color(red, green, blue, alpha)
}

fun getColorWithAlpha(color: Color, alpha: Float): Color {
    return Color(color.red, color.green, color.blue, alpha)
}

fun getProductInfoSurfaceColor(productColor: Color): Color {
    return getColorWithAlpha(productColor, 0.25F)
}