package com.example.expirytracker

import com.example.expirytracker.pojo.ExpiryDate
import com.example.expirytracker.pojo.ProductInfo

object SampleProductList {

    val productList = listOf(
        ProductInfo("Paneer", sortedSetOf(ExpiryDate(26, 1, 2024))),
        ProductInfo("Milk", sortedSetOf(ExpiryDate(30, 1, 2024), ExpiryDate(15, 2, 2024)), "https://www.amazon.com/mery-Grass-Organic-Whole-Supportibackl-248/dp/B06ZYNZ7NF"),
        ProductInfo("Guacamole", sortedSetOf(ExpiryDate(24, 1, 2024)), "https://www.amazon.com/PRODUCE-Hot-Guacamole-Dip-Small/dp/B09WZ9XCZQ"),
        ProductInfo("Strawberry", sortedSetOf(ExpiryDate(20, 1, 2024)), "https://www.amazon.com/produce-aisle-mburring-Organic-Strawberries/dp/B002B8Z98W"),
        ProductInfo("Eggs", sortedSetOf(ExpiryDate(25, 1, 2024)), "https://www.amazon.com/Vital-Farms-Restorative-Pasture-Raised/dp/B09RRDKJ58"),
        ProductInfo("Bread", sortedSetOf(ExpiryDate(4, 2, 2024))),
        ProductInfo("Honey", sortedSetOf(ExpiryDate(3, 2, 2025))),
        ProductInfo("Semolina", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ProductInfo("Rice", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ProductInfo("Sorghum", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ProductInfo("Broken Wheat", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ProductInfo("Quinoa", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ProductInfo("Couscous", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ProductInfo("Wheat", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ProductInfo("Lotus seeds", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ProductInfo("Soybean", sortedSetOf(ExpiryDate(4, 2, 2025)), "https://www.amazon.com/gp/aw/d/B086D52W5F"),
        ProductInfo("Kidney beans", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ProductInfo("Peanuts", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ProductInfo("Cashew", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ProductInfo("Walnut", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ProductInfo("Pistachio", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ProductInfo("Almond", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ProductInfo("Raisins", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ProductInfo("Mixed seeds", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ProductInfo("Flattened Rice", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ProductInfo("Basmati Rice", sortedSetOf(ExpiryDate(4, 3, 2024))),
        ProductInfo("Garam Masala", sortedSetOf(ExpiryDate(10, 2, 2024)), "https://www.amazon.com/Everest-Garam-Masala-50g/dp/B017BK7MD4"),
        ProductInfo("Orange Juice", sortedSetOf(ExpiryDate(13, 2, 2024))),
    )
}