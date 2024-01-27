package com.example.expirytracker

object SampleItemList {

    val itemList = listOf(
        ItemInfo("Paneer", sortedSetOf(ExpiryDate(26, 1, 2024))),
        ItemInfo("Milk", sortedSetOf(ExpiryDate(30, 1, 2024), ExpiryDate(15, 2, 2024))),
        ItemInfo("Guacamole", sortedSetOf(ExpiryDate(24, 1, 2024)), "https://www.amazon.com/PRODUCE-Hot-Guacamole-Dip-Small/dp/B09WZ9XCZQ"),
        ItemInfo("Strawberry", sortedSetOf(ExpiryDate(20, 1, 2024)), "https://www.amazon.com/produce-aisle-mburring-Organic-Strawberries/dp/B002B8Z98W"),
        ItemInfo("Eggs", sortedSetOf(ExpiryDate(25, 1, 2024)), "https://www.amazon.com/Vital-Farms-Restorative-Pasture-Raised/dp/B09RRDKJ58"),
        ItemInfo("Bread", sortedSetOf(ExpiryDate(4, 2, 2024))),
        ItemInfo("Honey", sortedSetOf(ExpiryDate(3, 2, 2025))),
        ItemInfo("Semolina", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ItemInfo("Rice", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ItemInfo("Sorghum", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ItemInfo("Broken Wheat", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ItemInfo("Quinoa", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ItemInfo("Couscous", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ItemInfo("Wheat", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ItemInfo("Lotus seeds", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ItemInfo("Soybean", sortedSetOf(ExpiryDate(4, 2, 2025)), "https://www.amazon.com/gp/aw/d/B086D52W5F"),
        ItemInfo("Kidney beans", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ItemInfo("Peanuts", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ItemInfo("Cashew", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ItemInfo("Walnut", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ItemInfo("Pistachio", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ItemInfo("Almond", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ItemInfo("Raisins", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ItemInfo("Mixed seeds", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ItemInfo("Flattened Rice", sortedSetOf(ExpiryDate(4, 2, 2025))),
        ItemInfo("Basmati Rice", sortedSetOf(ExpiryDate(4, 3, 2024))),
        ItemInfo("Garam Masala", sortedSetOf(ExpiryDate(10, 2, 2024)), "https://www.amazon.com/Everest-Garam-Masala-50g/dp/B017BK7MD4"),
        ItemInfo("Orange Juice", sortedSetOf(ExpiryDate(13, 2, 2024))),
    )
}