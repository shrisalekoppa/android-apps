package com.example.data.repository

import com.example.data.model.CommodityCategory
import com.example.data.model.MandiRecord
import com.example.data.model.SellAction
import com.example.data.model.SellTimingInsight
import com.example.data.model.WeatherAgriRisk

object MandiMockDataSource {

    val sampleRecords: List<MandiRecord> = listOf(
        // 1. Wheat
        MandiRecord(
            id = "wheat_khanna_pb",
            commodity = "Wheat",
            variety = "Sharbati (High Quality)",
            category = CommodityCategory.CEREALS,
            state = "Punjab",
            district = "Ludhiana",
            market = "Khanna Mandi",
            minPrice = 2480.0,
            modalPrice = 2620.0,
            maxPrice = 2750.0,
            arrivalsQuintals = 4520.0,
            date = "12 Oct 2026",
            priceChangePercent = 2.3,
            mspPrice = 2275.0,
            historical7Days = listOf(2540.0, 2560.0, 2550.0, 2580.0, 2600.0, 2610.0, 2620.0),
            historical30Days = listOf(
                2420.0, 2440.0, 2430.0, 2460.0, 2480.0, 2500.0, 2490.0, 2510.0,
                2530.0, 2520.0, 2540.0, 2560.0, 2550.0, 2580.0, 2600.0, 2620.0
            ),
            isFavorite = true
        ),
        MandiRecord(
            id = "wheat_indore_mp",
            commodity = "Wheat",
            variety = "Lokwan (Mill Quality)",
            category = CommodityCategory.CEREALS,
            state = "Madhya Pradesh",
            district = "Indore",
            market = "Indore Mandi",
            minPrice = 2390.0,
            modalPrice = 2510.0,
            maxPrice = 2640.0,
            arrivalsQuintals = 3840.0,
            date = "12 Oct 2026",
            priceChangePercent = 1.1,
            mspPrice = 2275.0,
            historical7Days = listOf(2470.0, 2480.0, 2490.0, 2485.0, 2495.0, 2500.0, 2510.0),
            historical30Days = listOf(
                2380.0, 2400.0, 2410.0, 2430.0, 2420.0, 2440.0, 2460.0, 2470.0,
                2480.0, 2490.0, 2485.0, 2495.0, 2500.0, 2505.0, 2510.0, 2510.0
            ),
            isFavorite = true
        ),
        MandiRecord(
            id = "wheat_kota_rj",
            commodity = "Wheat",
            variety = "Kalyan Sona",
            category = CommodityCategory.CEREALS,
            state = "Rajasthan",
            district = "Kota",
            market = "Kota Bhamashah Mandi",
            minPrice = 2350.0,
            modalPrice = 2460.0,
            maxPrice = 2580.0,
            arrivalsQuintals = 2950.0,
            date = "12 Oct 2026",
            priceChangePercent = -0.4,
            mspPrice = 2275.0,
            historical7Days = listOf(2490.0, 2480.0, 2475.0, 2470.0, 2465.0, 2460.0, 2460.0),
            historical30Days = listOf(
                2400.0, 2420.0, 2430.0, 2450.0, 2470.0, 2480.0, 2490.0, 2485.0,
                2475.0, 2480.0, 2470.0, 2465.0, 2460.0, 2455.0, 2460.0, 2460.0
            )
        ),

        // 2. Paddy / Basmati Rice
        MandiRecord(
            id = "paddy_karnal_hr",
            commodity = "Paddy (Basmati)",
            variety = "Basmati 1121 Pusa",
            category = CommodityCategory.CEREALS,
            state = "Haryana",
            district = "Karnal",
            market = "Karnal Grain Market",
            minPrice = 3850.0,
            modalPrice = 4250.0,
            maxPrice = 4480.0,
            arrivalsQuintals = 6120.0,
            date = "12 Oct 2026",
            priceChangePercent = 3.8,
            mspPrice = 2320.0,
            historical7Days = listOf(3980.0, 4020.0, 4090.0, 4120.0, 4180.0, 4210.0, 4250.0),
            historical30Days = listOf(
                3700.0, 3750.0, 3820.0, 3890.0, 3950.0, 4000.0, 4050.0, 4020.0,
                4080.0, 4110.0, 4150.0, 4180.0, 4200.0, 4220.0, 4240.0, 4250.0
            ),
            isFavorite = true
        ),
        MandiRecord(
            id = "paddy_davanagere_ka",
            commodity = "Paddy",
            variety = "Sona Masoori",
            category = CommodityCategory.CEREALS,
            state = "Karnataka",
            district = "Davanagere",
            market = "Davanagere APMC",
            minPrice = 2450.0,
            modalPrice = 2650.0,
            maxPrice = 2820.0,
            arrivalsQuintals = 2780.0,
            date = "12 Oct 2026",
            priceChangePercent = 0.8,
            mspPrice = 2300.0,
            historical7Days = listOf(2610.0, 2620.0, 2630.0, 2635.0, 2640.0, 2645.0, 2650.0),
            historical30Days = listOf(
                2500.0, 2520.0, 2540.0, 2560.0, 2580.0, 2600.0, 2610.0, 2620.0,
                2630.0, 2625.0, 2635.0, 2640.0, 2645.0, 2650.0, 2650.0, 2650.0
            )
        ),

        // 3. Soybean
        MandiRecord(
            id = "soybean_indore_mp",
            commodity = "Soybean",
            variety = "Yellow JS-335",
            category = CommodityCategory.OILSEEDS,
            state = "Madhya Pradesh",
            district = "Indore",
            market = "Indore Mandi",
            minPrice = 4450.0,
            modalPrice = 4780.0,
            maxPrice = 4920.0,
            arrivalsQuintals = 5400.0,
            date = "12 Oct 2026",
            priceChangePercent = -1.4,
            mspPrice = 4892.0,
            historical7Days = listOf(4920.0, 4880.0, 4850.0, 4820.0, 4800.0, 4790.0, 4780.0),
            historical30Days = listOf(
                5100.0, 5050.0, 5020.0, 4980.0, 4950.0, 4920.0, 4900.0, 4880.0,
                4860.0, 4840.0, 4820.0, 4810.0, 4800.0, 4790.0, 4785.0, 4780.0
            ),
            isFavorite = true
        ),
        MandiRecord(
            id = "soybean_latur_mh",
            commodity = "Soybean",
            variety = "Yellow Grade-A",
            category = CommodityCategory.OILSEEDS,
            state = "Maharashtra",
            district = "Latur",
            market = "Latur APMC",
            minPrice = 4500.0,
            modalPrice = 4820.0,
            maxPrice = 4990.0,
            arrivalsQuintals = 6200.0,
            date = "12 Oct 2026",
            priceChangePercent = -0.8,
            mspPrice = 4892.0,
            historical7Days = listOf(4910.0, 4890.0, 4870.0, 4850.0, 4840.0, 4830.0, 4820.0),
            historical30Days = listOf(
                5150.0, 5080.0, 5020.0, 4980.0, 4950.0, 4920.0, 4900.0, 4880.0,
                4870.0, 4850.0, 4840.0, 4830.0, 4825.0, 4820.0, 4820.0, 4820.0
            )
        ),

        // 4. Onion
        MandiRecord(
            id = "onion_lasalgaon_mh",
            commodity = "Onion",
            variety = "Red Nasik Quality",
            category = CommodityCategory.VEGETABLES,
            state = "Maharashtra",
            district = "Nashik",
            market = "Lasalgaon Mandi",
            minPrice = 2800.0,
            modalPrice = 3450.0,
            maxPrice = 3850.0,
            arrivalsQuintals = 8900.0,
            date = "12 Oct 2026",
            priceChangePercent = 8.5,
            mspPrice = 0.0,
            historical7Days = listOf(2950.0, 3050.0, 3120.0, 3240.0, 3310.0, 3380.0, 3450.0),
            historical30Days = listOf(
                2200.0, 2300.0, 2450.0, 2600.0, 2750.0, 2850.0, 2950.0, 3050.0,
                3100.0, 3200.0, 3250.0, 3300.0, 3350.0, 3400.0, 3420.0, 3450.0
            ),
            isFavorite = true
        ),
        MandiRecord(
            id = "onion_pune_mh",
            commodity = "Onion",
            variety = "Medium Red Garva",
            category = CommodityCategory.VEGETABLES,
            state = "Maharashtra",
            district = "Pune",
            market = "Pune APMC (Gultekdi)",
            minPrice = 2700.0,
            modalPrice = 3380.0,
            maxPrice = 3750.0,
            arrivalsQuintals = 7100.0,
            date = "12 Oct 2026",
            priceChangePercent = 6.9,
            mspPrice = 0.0,
            historical7Days = listOf(2900.0, 3000.0, 3100.0, 3190.0, 3260.0, 3320.0, 3380.0),
            historical30Days = listOf(
                2150.0, 2280.0, 2400.0, 2550.0, 2700.0, 2800.0, 2900.0, 3000.0,
                3080.0, 3150.0, 3220.0, 3280.0, 3310.0, 3350.0, 3370.0, 3380.0
            )
        ),
        MandiRecord(
            id = "onion_azadpur_dl",
            commodity = "Onion",
            variety = "Nashik Grade A",
            category = CommodityCategory.VEGETABLES,
            state = "Uttar Pradesh",
            district = "Delhi NCR / Border",
            market = "Azadpur Mandi",
            minPrice = 3100.0,
            modalPrice = 3720.0,
            maxPrice = 4100.0,
            arrivalsQuintals = 9500.0,
            date = "12 Oct 2026",
            priceChangePercent = 7.2,
            mspPrice = 0.0,
            historical7Days = listOf(3250.0, 3340.0, 3420.0, 3510.0, 3600.0, 3680.0, 3720.0),
            historical30Days = listOf(
                2500.0, 2620.0, 2750.0, 2900.0, 3050.0, 3180.0, 3280.0, 3380.0,
                3450.0, 3520.0, 3590.0, 3640.0, 3680.0, 3700.0, 3710.0, 3720.0
            )
        ),

        // 5. Tomato
        MandiRecord(
            id = "tomato_bengaluru_ka",
            commodity = "Tomato",
            variety = "Hybrid (Namdhari)",
            category = CommodityCategory.VEGETABLES,
            state = "Karnataka",
            district = "Bengaluru Rural",
            market = "Kolar & Bengaluru APMC",
            minPrice = 1400.0,
            modalPrice = 1750.0,
            maxPrice = 2100.0,
            arrivalsQuintals = 5200.0,
            date = "12 Oct 2026",
            priceChangePercent = -5.4,
            mspPrice = 0.0,
            historical7Days = listOf(2100.0, 2020.0, 1950.0, 1880.0, 1820.0, 1780.0, 1750.0),
            historical30Days = listOf(
                2800.0, 2700.0, 2600.0, 2480.0, 2350.0, 2220.0, 2150.0, 2050.0,
                1980.0, 1920.0, 1860.0, 1820.0, 1790.0, 1760.0, 1750.0, 1750.0
            ),
            isFavorite = true
        ),
        MandiRecord(
            id = "tomato_pune_mh",
            commodity = "Tomato",
            variety = "Abhinav Desi",
            category = CommodityCategory.VEGETABLES,
            state = "Maharashtra",
            district = "Pune",
            market = "Narayangaon & Pune APMC",
            minPrice = 1350.0,
            modalPrice = 1680.0,
            maxPrice = 1980.0,
            arrivalsQuintals = 4800.0,
            date = "12 Oct 2026",
            priceChangePercent = -4.2,
            mspPrice = 0.0,
            historical7Days = listOf(1980.0, 1900.0, 1840.0, 1790.0, 1740.0, 1700.0, 1680.0),
            historical30Days = listOf(
                2700.0, 2580.0, 2450.0, 2320.0, 2200.0, 2100.0, 2000.0, 1920.0,
                1850.0, 1800.0, 1750.0, 1720.0, 1700.0, 1690.0, 1680.0, 1680.0
            )
        ),

        // 6. Cotton
        MandiRecord(
            id = "cotton_rajkot_gj",
            commodity = "Cotton",
            variety = "Shankar-6 (Long Staple)",
            category = CommodityCategory.CASH_CROPS,
            state = "Gujarat",
            district = "Rajkot",
            market = "Rajkot APMC",
            minPrice = 6900.0,
            modalPrice = 7450.0,
            maxPrice = 7780.0,
            arrivalsQuintals = 4100.0,
            date = "12 Oct 2026",
            priceChangePercent = 1.9,
            mspPrice = 7121.0,
            historical7Days = listOf(7220.0, 7280.0, 7310.0, 7360.0, 7400.0, 7420.0, 7450.0),
            historical30Days = listOf(
                6800.0, 6890.0, 6980.0, 7050.0, 7120.0, 7180.0, 7240.0, 7300.0,
                7340.0, 7370.0, 7400.0, 7420.0, 7430.0, 7440.0, 7450.0, 7450.0
            ),
            isFavorite = true
        ),
        MandiRecord(
            id = "cotton_warangal_tg",
            commodity = "Cotton",
            variety = "BT Cotton (Grade A)",
            category = CommodityCategory.CASH_CROPS,
            state = "Telangana",
            district = "Warangal",
            market = "Enumamula APMC Warangal",
            minPrice = 6800.0,
            modalPrice = 7380.0,
            maxPrice = 7650.0,
            arrivalsQuintals = 3800.0,
            date = "12 Oct 2026",
            priceChangePercent = 1.4,
            mspPrice = 7121.0,
            historical7Days = listOf(7180.0, 7230.0, 7270.0, 7310.0, 7340.0, 7360.0, 7380.0),
            historical30Days = listOf(
                6750.0, 6840.0, 6930.0, 7010.0, 7080.0, 7150.0, 7210.0, 7260.0,
                7300.0, 7330.0, 7350.0, 7360.0, 7370.0, 7375.0, 7380.0, 7380.0
            )
        ),

        // 7. Mustard / Sarson
        MandiRecord(
            id = "mustard_sriganganagar_rj",
            commodity = "Mustard",
            variety = "Pusa Bold (Oil Content 42%)",
            category = CommodityCategory.OILSEEDS,
            state = "Rajasthan",
            district = "Sri Ganganagar",
            market = "Sri Ganganagar Mandi",
            minPrice = 5250.0,
            modalPrice = 5620.0,
            maxPrice = 5890.0,
            arrivalsQuintals = 3100.0,
            date = "12 Oct 2026",
            priceChangePercent = 2.1,
            mspPrice = 5650.0,
            historical7Days = listOf(5450.0, 5490.0, 5520.0, 5560.0, 5580.0, 5600.0, 5620.0),
            historical30Days = listOf(
                5100.0, 5180.0, 5260.0, 5320.0, 5390.0, 5440.0, 5490.0, 5530.0,
                5560.0, 5580.0, 5590.0, 5600.0, 5610.0, 5615.0, 5620.0, 5620.0
            )
        ),

        // 8. Chana / Gram
        MandiRecord(
            id = "chana_ujjain_mp",
            commodity = "Chana (Bengal Gram)",
            variety = "Desi Chana",
            category = CommodityCategory.PULSES,
            state = "Madhya Pradesh",
            district = "Ujjain",
            market = "Ujjain Mandi",
            minPrice = 5600.0,
            modalPrice = 5980.0,
            maxPrice = 6250.0,
            arrivalsQuintals = 2900.0,
            date = "12 Oct 2026",
            priceChangePercent = 0.5,
            mspPrice = 5440.0,
            historical7Days = listOf(5910.0, 5930.0, 5950.0, 5960.0, 5970.0, 5975.0, 5980.0),
            historical30Days = listOf(
                5650.0, 5720.0, 5780.0, 5830.0, 5870.0, 5900.0, 5920.0, 5940.0,
                5950.0, 5960.0, 5965.0, 5970.0, 5975.0, 5980.0, 5980.0, 5980.0
            )
        ),

        // 9. Potato
        MandiRecord(
            id = "potato_agra_up",
            commodity = "Potato",
            variety = "Jyoti Cold Storage",
            category = CommodityCategory.VEGETABLES,
            state = "Uttar Pradesh",
            district = "Agra",
            market = "Agra Mandi",
            minPrice = 1450.0,
            modalPrice = 1720.0,
            maxPrice = 1950.0,
            arrivalsQuintals = 7400.0,
            date = "12 Oct 2026",
            priceChangePercent = 3.6,
            mspPrice = 0.0,
            historical7Days = listOf(1580.0, 1610.0, 1640.0, 1670.0, 1690.0, 1710.0, 1720.0),
            historical30Days = listOf(
                1350.0, 1400.0, 1450.0, 1500.0, 1550.0, 1600.0, 1630.0, 1660.0,
                1680.0, 1690.0, 1700.0, 1710.0, 1715.0, 1720.0, 1720.0, 1720.0
            )
        ),

        // 10. Red Chilli & Spices
        MandiRecord(
            id = "chilli_guntur_ap",
            commodity = "Red Chilli",
            variety = "Teja S-17 (Stemless)",
            category = CommodityCategory.SPICES,
            state = "Andhra Pradesh",
            district = "Guntur",
            market = "Guntur Mirchi Yard",
            minPrice = 17500.0,
            modalPrice = 19800.0,
            maxPrice = 21500.0,
            arrivalsQuintals = 8200.0,
            date = "12 Oct 2026",
            priceChangePercent = 4.2,
            mspPrice = 0.0,
            historical7Days = listOf(18600.0, 18900.0, 19200.0, 19400.0, 19600.0, 19700.0, 19800.0),
            historical30Days = listOf(
                16500.0, 17000.0, 17500.0, 18000.0, 18400.0, 18800.0, 19100.0, 19300.0,
                19500.0, 19600.0, 19700.0, 19750.0, 19800.0, 19800.0, 19800.0, 19800.0
            ),
            isFavorite = true
        ),
        MandiRecord(
            id = "turmeric_nizamabad_tg",
            commodity = "Turmeric",
            variety = "Finger Nizamabad Quality",
            category = CommodityCategory.SPICES,
            state = "Telangana",
            district = "Nizamabad",
            market = "Nizamabad APMC",
            minPrice = 11800.0,
            modalPrice = 13400.0,
            maxPrice = 14600.0,
            arrivalsQuintals = 2600.0,
            date = "12 Oct 2026",
            priceChangePercent = 2.7,
            mspPrice = 0.0,
            historical7Days = listOf(12800.0, 12950.0, 13100.0, 13200.0, 13300.0, 13350.0, 13400.0),
            historical30Days = listOf(
                11200.0, 11600.0, 12000.0, 12400.0, 12700.0, 12900.0, 13050.0, 13150.0,
                13250.0, 13300.0, 13350.0, 13380.0, 13400.0, 13400.0, 13400.0, 13400.0
            )
        ),
        MandiRecord(
            id = "cumin_unjha_gj",
            commodity = "Cumin (Jeera)",
            variety = "Machine Cleaned Extra Bold",
            category = CommodityCategory.SPICES,
            state = "Gujarat",
            district = "Mehsana",
            market = "Unjha APMC",
            minPrice = 24500.0,
            modalPrice = 27800.0,
            maxPrice = 29900.0,
            arrivalsQuintals = 3500.0,
            date = "12 Oct 2026",
            priceChangePercent = -1.2,
            mspPrice = 0.0,
            historical7Days = listOf(28400.0, 28200.0, 28050.0, 27950.0, 27900.0, 27850.0, 27800.0),
            historical30Days = listOf(
                29800.0, 29400.0, 29100.0, 28800.0, 28500.0, 28300.0, 28150.0, 28000.0,
                27950.0, 27900.0, 27850.0, 27800.0, 27800.0, 27800.0, 27800.0, 27800.0
            )
        ),

        // 11. Areca Nut (ಅಡಿಕೆ / सुपारी) - Commercial Plantation Crop
        MandiRecord(
            id = "arecanut_shivamogga_ka",
            commodity = "Areca Nut",
            variety = "Rashi Idi (Red Boiled)",
            category = CommodityCategory.COMMERCIAL_PLANTATION,
            state = "Karnataka",
            district = "Shivamogga",
            market = "Shivamogga APMC",
            minPrice = 51500.0,
            modalPrice = 54800.0,
            maxPrice = 56200.0,
            arrivalsQuintals = 3450.0,
            date = "12 Oct 2026",
            priceChangePercent = 2.4,
            mspPrice = 0.0,
            historical7Days = listOf(53200.0, 53600.0, 54000.0, 54200.0, 54500.0, 54600.0, 54800.0),
            historical30Days = listOf(
                50800.0, 51200.0, 51800.0, 52400.0, 52900.0, 53200.0, 53600.0, 54000.0,
                54100.0, 54300.0, 54400.0, 54500.0, 54600.0, 54700.0, 54800.0, 54800.0
            ),
            isFavorite = true
        ),
        MandiRecord(
            id = "arecanut_sirsi_ka",
            commodity = "Areca Nut",
            variety = "Chali (White Peeled)",
            category = CommodityCategory.COMMERCIAL_PLANTATION,
            state = "Karnataka",
            district = "Uttara Kannada",
            market = "Sirsi TSS & APMC",
            minPrice = 46500.0,
            modalPrice = 49200.0,
            maxPrice = 51500.0,
            arrivalsQuintals = 2800.0,
            date = "12 Oct 2026",
            priceChangePercent = 1.8,
            mspPrice = 0.0,
            historical7Days = listOf(48100.0, 48400.0, 48700.0, 48900.0, 49000.0, 49100.0, 49200.0),
            historical30Days = listOf(
                46000.0, 46500.0, 47000.0, 47400.0, 47800.0, 48100.0, 48400.0, 48700.0,
                48800.0, 48900.0, 49000.0, 49100.0, 49150.0, 49200.0, 49200.0, 49200.0
            ),
            isFavorite = true
        ),
        MandiRecord(
            id = "arecanut_channagiri_ka",
            commodity = "Areca Nut",
            variety = "Rashi Supreme Grade",
            category = CommodityCategory.COMMERCIAL_PLANTATION,
            state = "Karnataka",
            district = "Davanagere",
            market = "Channagiri APMC",
            minPrice = 52000.0,
            modalPrice = 55400.0,
            maxPrice = 57100.0,
            arrivalsQuintals = 2950.0,
            date = "12 Oct 2026",
            priceChangePercent = 2.1,
            mspPrice = 0.0,
            historical7Days = listOf(53900.0, 54200.0, 54600.0, 54900.0, 55100.0, 55300.0, 55400.0),
            historical30Days = listOf(
                51200.0, 51800.0, 52400.0, 53000.0, 53500.0, 53900.0, 54300.0, 54600.0,
                54800.0, 55000.0, 55100.0, 55200.0, 55300.0, 55350.0, 55400.0, 55400.0
            )
        ),

        // 12. Coconut & Copra (ತೆಂಗಿನಕಾಯಿ / ಕೊಬ್ಬರಿ)
        MandiRecord(
            id = "coconut_tiptur_ka",
            commodity = "Coconut",
            variety = "Ball Copra (Grade 1 Dry)",
            category = CommodityCategory.COMMERCIAL_PLANTATION,
            state = "Karnataka",
            district = "Tumakuru",
            market = "Tiptur APMC (Copra Hub)",
            minPrice = 12200.0,
            modalPrice = 13800.0,
            maxPrice = 14600.0,
            arrivalsQuintals = 4100.0,
            date = "12 Oct 2026",
            priceChangePercent = 3.2,
            mspPrice = 12000.0,
            historical7Days = listOf(13100.0, 13250.0, 13400.0, 13550.0, 13650.0, 13700.0, 13800.0),
            historical30Days = listOf(
                11800.0, 12000.0, 12250.0, 12500.0, 12800.0, 13000.0, 13200.0, 13350.0,
                13450.0, 13550.0, 13600.0, 13680.0, 13720.0, 13780.0, 13800.0, 13800.0
            ),
            isFavorite = true
        ),
        MandiRecord(
            id = "coconut_arsikere_ka",
            commodity = "Coconut",
            variety = "Raw Mature Coconut (Cleaned)",
            category = CommodityCategory.COMMERCIAL_PLANTATION,
            state = "Karnataka",
            district = "Hassan",
            market = "Arsikere APMC",
            minPrice = 2800.0,
            modalPrice = 3250.0,
            maxPrice = 3600.0,
            arrivalsQuintals = 5200.0,
            date = "12 Oct 2026",
            priceChangePercent = 1.6,
            mspPrice = 0.0,
            historical7Days = listOf(3100.0, 3140.0, 3180.0, 3200.0, 3220.0, 3240.0, 3250.0),
            historical30Days = listOf(
                2750.0, 2800.0, 2880.0, 2950.0, 3020.0, 3080.0, 3120.0, 3160.0,
                3180.0, 3200.0, 3210.0, 3230.0, 3240.0, 3245.0, 3250.0, 3250.0
            ),
            isFavorite = true
        ),
        MandiRecord(
            id = "copra_kozhikode_kl",
            commodity = "Coconut",
            variety = "Milling Copra",
            category = CommodityCategory.COMMERCIAL_PLANTATION,
            state = "Kerala",
            district = "Kozhikode",
            market = "Kozhikode Mandi",
            minPrice = 10400.0,
            modalPrice = 11200.0,
            maxPrice = 11800.0,
            arrivalsQuintals = 3800.0,
            date = "12 Oct 2026",
            priceChangePercent = 0.9,
            mspPrice = 11160.0,
            historical7Days = listOf(11000.0, 11050.0, 11100.0, 11120.0, 11150.0, 11180.0, 11200.0),
            historical30Days = listOf(
                10200.0, 10350.0, 10500.0, 10650.0, 10800.0, 10920.0, 11000.0, 11050.0,
                11100.0, 11120.0, 11140.0, 11160.0, 11180.0, 11190.0, 11200.0, 11200.0
            )
        ),

        // 13. Cocoa (ಕೋಕೋ / कोको)
        MandiRecord(
            id = "cocoa_campco_puttur_ka",
            commodity = "Cocoa",
            variety = "Dry Fermented Beans (Grade A)",
            category = CommodityCategory.COMMERCIAL_PLANTATION,
            state = "Karnataka",
            district = "Dakshina Kannada",
            market = "CAMPCO Puttur & Mangaluru",
            minPrice = 68000.0,
            modalPrice = 74500.0,
            maxPrice = 79000.0,
            arrivalsQuintals = 1200.0,
            date = "12 Oct 2026",
            priceChangePercent = 5.2,
            mspPrice = 0.0,
            historical7Days = listOf(69500.0, 70500.0, 71500.0, 72500.0, 73500.0, 74000.0, 74500.0),
            historical30Days = listOf(
                58000.0, 60000.0, 62000.0, 64500.0, 66500.0, 68000.0, 69500.0, 71000.0,
                72000.0, 72800.0, 73400.0, 73800.0, 74100.0, 74300.0, 74500.0, 74500.0
            ),
            isFavorite = true
        ),
        MandiRecord(
            id = "cocoa_wayanad_kl",
            commodity = "Cocoa",
            variety = "Dry Organic Beans",
            category = CommodityCategory.COMMERCIAL_PLANTATION,
            state = "Kerala",
            district = "Wayanad",
            market = "Sulthan Bathery Mandi",
            minPrice = 66000.0,
            modalPrice = 72800.0,
            maxPrice = 77000.0,
            arrivalsQuintals = 950.0,
            date = "12 Oct 2026",
            priceChangePercent = 4.8,
            mspPrice = 0.0,
            historical7Days = listOf(68000.0, 69000.0, 70000.0, 71000.0, 71800.0, 72300.0, 72800.0),
            historical30Days = listOf(
                57000.0, 59000.0, 61000.0, 63000.0, 65000.0, 67000.0, 68500.0, 70000.0,
                70800.0, 71400.0, 71900.0, 72200.0, 72500.0, 72700.0, 72800.0, 72800.0
            )
        ),

        // 14. Banana (ಬಾಳೆಹಣ್ಣು / केला)
        MandiRecord(
            id = "banana_bengaluru_ka",
            commodity = "Banana",
            variety = "Yelakki Bale (Golden)",
            category = CommodityCategory.FRUITS,
            state = "Karnataka",
            district = "Bengaluru Urban",
            market = "Bengaluru APMC (Yeshwanthpur)",
            minPrice = 3800.0,
            modalPrice = 4650.0,
            maxPrice = 5200.0,
            arrivalsQuintals = 4500.0,
            date = "12 Oct 2026",
            priceChangePercent = 3.1,
            mspPrice = 0.0,
            historical7Days = listOf(4350.0, 4420.0, 4490.0, 4550.0, 4580.0, 4610.0, 4650.0),
            historical30Days = listOf(
                3900.0, 4000.0, 4120.0, 4200.0, 4280.0, 4350.0, 4420.0, 4480.0,
                4520.0, 4560.0, 4590.0, 4620.0, 4635.0, 4645.0, 4650.0, 4650.0
            ),
            isFavorite = true
        ),
        MandiRecord(
            id = "banana_jalgaon_mh",
            commodity = "Banana",
            variety = "Robusta (Export Grade Grand Naine)",
            category = CommodityCategory.FRUITS,
            state = "Maharashtra",
            district = "Jalgaon",
            market = "Raver & Jalgaon Mandi",
            minPrice = 1850.0,
            modalPrice = 2250.0,
            maxPrice = 2500.0,
            arrivalsQuintals = 8500.0,
            date = "12 Oct 2026",
            priceChangePercent = 2.3,
            mspPrice = 0.0,
            historical7Days = listOf(2120.0, 2150.0, 2180.0, 2200.0, 2220.0, 2240.0, 2250.0),
            historical30Days = listOf(
                1750.0, 1820.0, 1900.0, 1980.0, 2050.0, 2100.0, 2140.0, 2180.0,
                2200.0, 2210.0, 2220.0, 2230.0, 2240.0, 2245.0, 2250.0, 2250.0
            )
        ),
        MandiRecord(
            id = "banana_theni_tn",
            commodity = "Banana",
            variety = "Nendran (Chips Grade)",
            category = CommodityCategory.FRUITS,
            state = "Tamil Nadu",
            district = "Theni",
            market = "Theni & Chinnamanur APMC",
            minPrice = 2600.0,
            modalPrice = 3100.0,
            maxPrice = 3450.0,
            arrivalsQuintals = 3900.0,
            date = "12 Oct 2026",
            priceChangePercent = 1.9,
            mspPrice = 0.0,
            historical7Days = listOf(2950.0, 2990.0, 3020.0, 3050.0, 3080.0, 3090.0, 3100.0),
            historical30Days = listOf(
                2500.0, 2600.0, 2700.0, 2780.0, 2850.0, 2920.0, 2970.0, 3020.0,
                3040.0, 3060.0, 3075.0, 3085.0, 3090.0, 3095.0, 3100.0, 3100.0
            )
        ),

        // 15. Pineapple (ಅನಾನಸ್ / अनानास)
        MandiRecord(
            id = "pineapple_vazhakulam_kl",
            commodity = "Pineapple",
            variety = "Mauritius Grade A (GI Tagged)",
            category = CommodityCategory.FRUITS,
            state = "Kerala",
            district = "Ernakulam",
            market = "Vazhakulam Pineapple Market",
            minPrice = 3600.0,
            modalPrice = 4200.0,
            maxPrice = 4650.0,
            arrivalsQuintals = 2700.0,
            date = "12 Oct 2026",
            priceChangePercent = 3.4,
            mspPrice = 0.0,
            historical7Days = listOf(3920.0, 3980.0, 4050.0, 4100.0, 4150.0, 4180.0, 4200.0),
            historical30Days = listOf(
                3400.0, 3500.0, 3600.0, 3720.0, 3810.0, 3900.0, 3960.0, 4020.0,
                4080.0, 4120.0, 4150.0, 4170.0, 4185.0, 4195.0, 4200.0, 4200.0
            ),
            isFavorite = true
        ),
        MandiRecord(
            id = "pineapple_sirsi_ka",
            commodity = "Pineapple",
            variety = "Kew Giant Variety",
            category = CommodityCategory.FRUITS,
            state = "Karnataka",
            district = "Uttara Kannada",
            market = "Sirsi & Yellapur APMC",
            minPrice = 3200.0,
            modalPrice = 3750.0,
            maxPrice = 4100.0,
            arrivalsQuintals = 1600.0,
            date = "12 Oct 2026",
            priceChangePercent = 2.1,
            mspPrice = 0.0,
            historical7Days = listOf(3550.0, 3600.0, 3640.0, 3680.0, 3710.0, 3730.0, 3750.0),
            historical30Days = listOf(
                3100.0, 3180.0, 3260.0, 3350.0, 3420.0, 3500.0, 3560.0, 3620.0,
                3660.0, 3690.0, 3710.0, 3725.0, 3735.0, 3745.0, 3750.0, 3750.0
            )
        ),

        // 16. Coffee & Plantation Spices
        MandiRecord(
            id = "coffee_chikmagalur_ka",
            commodity = "Coffee",
            variety = "Arabica Parchment Extra",
            category = CommodityCategory.COMMERCIAL_PLANTATION,
            state = "Karnataka",
            district = "Chikkamagaluru",
            market = "Chikmagalur Coffee Curing APMC",
            minPrice = 35000.0,
            modalPrice = 38500.0,
            maxPrice = 41000.0,
            arrivalsQuintals = 1800.0,
            date = "12 Oct 2026",
            priceChangePercent = 2.9,
            mspPrice = 0.0,
            historical7Days = listOf(36800.0, 37200.0, 37600.0, 37900.0, 38200.0, 38400.0, 38500.0),
            historical30Days = listOf(
                32500.0, 33500.0, 34500.0, 35200.0, 36000.0, 36800.0, 37200.0, 37600.0,
                37900.0, 38100.0, 38250.0, 38350.0, 38420.0, 38480.0, 38500.0, 38500.0
            ),
            isFavorite = true
        ),
        MandiRecord(
            id = "black_pepper_idukki_kl",
            commodity = "Black Pepper",
            variety = "Malabar Garbled Grade 1",
            category = CommodityCategory.SPICES,
            state = "Kerala",
            district = "Idukki",
            market = "Kumily & Nedumkandam APMC",
            minPrice = 61000.0,
            modalPrice = 65400.0,
            maxPrice = 68000.0,
            arrivalsQuintals = 1100.0,
            date = "12 Oct 2026",
            priceChangePercent = 1.7,
            mspPrice = 0.0,
            historical7Days = listOf(63800.0, 64200.0, 64600.0, 64900.0, 65100.0, 65300.0, 65400.0),
            historical30Days = listOf(
                58000.0, 59200.0, 60400.0, 61500.0, 62600.0, 63400.0, 64100.0, 64600.0,
                64900.0, 65100.0, 65200.0, 65300.0, 65350.0, 65380.0, 65400.0, 65400.0
            )
        ),

        // 17. Additional Commercial Crops & Markets
        MandiRecord(
            id = "arecanut_sagar_ka",
            commodity = "Areca Nut",
            variety = "Bette / Koka Grade",
            category = CommodityCategory.COMMERCIAL_PLANTATION,
            state = "Karnataka",
            district = "Shivamogga",
            market = "Sagar APMC",
            minPrice = 42000.0,
            modalPrice = 45600.0,
            maxPrice = 47800.0,
            arrivalsQuintals = 1950.0,
            date = "12 Oct 2026",
            priceChangePercent = 1.5,
            mspPrice = 0.0,
            historical7Days = listOf(44500.0, 44800.0, 45100.0, 45250.0, 45400.0, 45500.0, 45600.0),
            historical30Days = listOf(
                41000.0, 41800.0, 42500.0, 43200.0, 43900.0, 44400.0, 44800.0, 45100.0,
                45200.0, 45300.0, 45400.0, 45450.0, 45500.0, 45550.0, 45600.0, 45600.0
            ),
            isFavorite = true
        ),
        MandiRecord(
            id = "arecanut_tumakuru_ka",
            commodity = "Areca Nut",
            variety = "Gorabal Grade",
            category = CommodityCategory.COMMERCIAL_PLANTATION,
            state = "Karnataka",
            district = "Tumakuru",
            market = "Tumakuru APMC",
            minPrice = 43500.0,
            modalPrice = 46800.0,
            maxPrice = 48900.0,
            arrivalsQuintals = 2200.0,
            date = "12 Oct 2026",
            priceChangePercent = 2.0,
            mspPrice = 0.0,
            historical7Days = listOf(45600.0, 45900.0, 46100.0, 46350.0, 46500.0, 46650.0, 46800.0),
            historical30Days = listOf(
                42000.0, 42800.0, 43500.0, 44200.0, 44900.0, 45500.0, 45900.0, 46200.0,
                46350.0, 46450.0, 46550.0, 46600.0, 46650.0, 46720.0, 46800.0, 46800.0
            )
        ),
        MandiRecord(
            id = "coconut_maddur_ka",
            commodity = "Coconut",
            variety = "Tender Coconut (Elaneeru Large)",
            category = CommodityCategory.COMMERCIAL_PLANTATION,
            state = "Karnataka",
            district = "Mandya",
            market = "Maddur Tender Coconut Market",
            minPrice = 3100.0,
            modalPrice = 3550.0,
            maxPrice = 3900.0,
            arrivalsQuintals = 6800.0,
            date = "12 Oct 2026",
            priceChangePercent = 2.8,
            mspPrice = 0.0,
            historical7Days = listOf(3350.0, 3390.0, 3430.0, 3470.0, 3500.0, 3530.0, 3550.0),
            historical30Days = listOf(
                3000.0, 3080.0, 3150.0, 3220.0, 3290.0, 3350.0, 3400.0, 3450.0,
                3480.0, 3500.0, 3510.0, 3520.0, 3530.0, 3540.0, 3550.0, 3550.0
            ),
            isFavorite = true
        ),
        MandiRecord(
            id = "coconut_pollachi_tn",
            commodity = "Coconut",
            variety = "Cleaned Mature Nut (Export Grade)",
            category = CommodityCategory.COMMERCIAL_PLANTATION,
            state = "Tamil Nadu",
            district = "Coimbatore",
            market = "Pollachi APMC",
            minPrice = 2900.0,
            modalPrice = 3350.0,
            maxPrice = 3700.0,
            arrivalsQuintals = 8200.0,
            date = "12 Oct 2026",
            priceChangePercent = 1.8,
            mspPrice = 0.0,
            historical7Days = listOf(3220.0, 3250.0, 3280.0, 3300.0, 3320.0, 3340.0, 3350.0),
            historical30Days = listOf(
                2800.0, 2880.0, 2960.0, 3050.0, 3120.0, 3200.0, 3250.0, 3290.0,
                3310.0, 3320.0, 3330.0, 3335.0, 3340.0, 3345.0, 3350.0, 3350.0
            )
        ),
        MandiRecord(
            id = "cocoa_thirthahalli_ka",
            commodity = "Cocoa",
            variety = "Raw Wet Beans (CAMPCO Sourced)",
            category = CommodityCategory.COMMERCIAL_PLANTATION,
            state = "Karnataka",
            district = "Shivamogga",
            market = "Thirthahalli & Shimoga APMC",
            minPrice = 28000.0,
            modalPrice = 32500.0,
            maxPrice = 35000.0,
            arrivalsQuintals = 1600.0,
            date = "12 Oct 2026",
            priceChangePercent = 4.1,
            mspPrice = 0.0,
            historical7Days = listOf(30500.0, 31000.0, 31400.0, 31800.0, 32100.0, 32300.0, 32500.0),
            historical30Days = listOf(
                25000.0, 26000.0, 27200.0, 28400.0, 29500.0, 30400.0, 31000.0, 31500.0,
                31800.0, 32000.0, 32150.0, 32250.0, 32350.0, 32420.0, 32500.0, 32500.0
            )
        ),
        MandiRecord(
            id = "banana_nanjangud_ka",
            commodity = "Banana",
            variety = "Nanjangud Rasabale (GI Tagged)",
            category = CommodityCategory.FRUITS,
            state = "Karnataka",
            district = "Mysuru",
            market = "Mysuru & Nanjangud APMC",
            minPrice = 5200.0,
            modalPrice = 6100.0,
            maxPrice = 6800.0,
            arrivalsQuintals = 1800.0,
            date = "12 Oct 2026",
            priceChangePercent = 3.6,
            mspPrice = 0.0,
            historical7Days = listOf(5750.0, 5820.0, 5900.0, 5970.0, 6020.0, 6060.0, 6100.0),
            historical30Days = listOf(
                5000.0, 5150.0, 5300.0, 5450.0, 5600.0, 5720.0, 5820.0, 5910.0,
                5970.0, 6010.0, 6040.0, 6060.0, 6080.0, 6090.0, 6100.0, 6100.0
            ),
            isFavorite = true
        ),
        MandiRecord(
            id = "pineapple_siliguri_wb",
            commodity = "Pineapple",
            variety = "Queen Pineapple (Sweet Export)",
            category = CommodityCategory.FRUITS,
            state = "West Bengal",
            district = "Darjeeling / Siliguri",
            market = "Siliguri Mandi (Bidhannagar)",
            minPrice = 3400.0,
            modalPrice = 3950.0,
            maxPrice = 4400.0,
            arrivalsQuintals = 2400.0,
            date = "12 Oct 2026",
            priceChangePercent = 1.9,
            mspPrice = 0.0,
            historical7Days = listOf(3800.0, 3830.0, 3870.0, 3900.0, 3920.0, 3940.0, 3950.0),
            historical30Days = listOf(
                3200.0, 3300.0, 3420.0, 3530.0, 3640.0, 3720.0, 3800.0, 3850.0,
                3880.0, 3900.0, 3920.0, 3930.0, 3940.0, 3945.0, 3950.0, 3950.0
            )
        ),
        MandiRecord(
            id = "coffee_kodagu_ka",
            commodity = "Coffee",
            variety = "Robusta Cherry Grade 1",
            category = CommodityCategory.COMMERCIAL_PLANTATION,
            state = "Karnataka",
            district = "Kodagu",
            market = "Madikeri & Kushalnagar APMC",
            minPrice = 22000.0,
            modalPrice = 24800.0,
            maxPrice = 26500.0,
            arrivalsQuintals = 2100.0,
            date = "12 Oct 2026",
            priceChangePercent = 2.4,
            mspPrice = 0.0,
            historical7Days = listOf(23900.0, 24100.0, 24350.0, 24500.0, 24650.0, 24720.0, 24800.0),
            historical30Days = listOf(
                21000.0, 21600.0, 22200.0, 22800.0, 23400.0, 23900.0, 24200.0, 24450.0,
                24550.0, 24620.0, 24680.0, 24720.0, 24750.0, 24780.0, 24800.0, 24800.0
            ),
            isFavorite = true
        ),
        MandiRecord(
            id = "cardamom_bodinayakanur_tn",
            commodity = "Cardamom",
            variety = "8mm Bold Extra Green",
            category = CommodityCategory.SPICES,
            state = "Tamil Nadu",
            district = "Theni",
            market = "Bodinayakanur Cardamom Yard",
            minPrice = 210000.0,
            modalPrice = 235000.0,
            maxPrice = 250000.0,
            arrivalsQuintals = 650.0,
            date = "12 Oct 2026",
            priceChangePercent = 3.5,
            mspPrice = 0.0,
            historical7Days = listOf(222000.0, 225000.0, 228000.0, 231000.0, 233000.0, 234000.0, 235000.0),
            historical30Days = listOf(
                195000.0, 202000.0, 209000.0, 215000.0, 222000.0, 226000.0, 230000.0, 232000.0,
                233000.0, 233800.0, 234200.0, 234500.0, 234700.0, 234900.0, 235000.0, 235000.0
            ),
            isFavorite = true
        ),
        MandiRecord(
            id = "cashew_mangaluru_ka",
            commodity = "Cashew Nut",
            variety = "Raw Cashew Nut (In-Shell)",
            category = CommodityCategory.COMMERCIAL_PLANTATION,
            state = "Karnataka",
            district = "Dakshina Kannada",
            market = "Mangaluru APMC",
            minPrice = 11500.0,
            modalPrice = 12800.0,
            maxPrice = 13900.0,
            arrivalsQuintals = 2800.0,
            date = "12 Oct 2026",
            priceChangePercent = 1.6,
            mspPrice = 0.0,
            historical7Days = listOf(12400.0, 12500.0, 12600.0, 12680.0, 12720.0, 12760.0, 12800.0),
            historical30Days = listOf(
                11200.0, 11500.0, 11800.0, 12100.0, 12350.0, 12550.0, 12650.0, 12700.0,
                12720.0, 12740.0, 12760.0, 12770.0, 12780.0, 12790.0, 12800.0, 12800.0
            )
        ),
        MandiRecord(
            id = "ginger_shivamogga_ka",
            commodity = "Ginger",
            variety = "Green Fresh Ginger (Rio de Janeiro)",
            category = CommodityCategory.SPICES,
            state = "Karnataka",
            district = "Shivamogga",
            market = "Shivamogga & Shikaripura APMC",
            minPrice = 6500.0,
            modalPrice = 7800.0,
            maxPrice = 8600.0,
            arrivalsQuintals = 3200.0,
            date = "12 Oct 2026",
            priceChangePercent = 2.6,
            mspPrice = 0.0,
            historical7Days = listOf(7400.0, 7500.0, 7600.0, 7680.0, 7720.0, 7760.0, 7800.0),
            historical30Days = listOf(
                6200.0, 6500.0, 6800.0, 7100.0, 7350.0, 7550.0, 7650.0, 7700.0,
                7720.0, 7740.0, 7760.0, 7770.0, 7780.0, 7790.0, 7800.0, 7800.0
            )
        ),
        MandiRecord(
            id = "jaggery_mandya_ka",
            commodity = "Sugarcane & Jaggery",
            variety = "Organic Bella (Jaggery Cubes)",
            category = CommodityCategory.COMMERCIAL_PLANTATION,
            state = "Karnataka",
            district = "Mandya",
            market = "Mandya APMC (Sugar City)",
            minPrice = 4100.0,
            modalPrice = 4650.0,
            maxPrice = 5100.0,
            arrivalsQuintals = 5400.0,
            date = "12 Oct 2026",
            priceChangePercent = 2.2,
            mspPrice = 3400.0,
            historical7Days = listOf(4450.0, 4500.0, 4550.0, 4580.0, 4610.0, 4630.0, 4650.0),
            historical30Days = listOf(
                3900.0, 4050.0, 4180.0, 4300.0, 4420.0, 4500.0, 4560.0, 4600.0,
                4610.0, 4620.0, 4630.0, 4635.0, 4640.0, 4645.0, 4650.0, 4650.0
            ),
            isFavorite = true
        ),
        MandiRecord(
            id = "rubber_kottayam_kl",
            commodity = "Rubber",
            variety = "RSS-4 Natural Sheet Grade",
            category = CommodityCategory.COMMERCIAL_PLANTATION,
            state = "Kerala",
            district = "Kottayam",
            market = "Kottayam Rubber Board Market",
            minPrice = 19200.0,
            modalPrice = 21400.0,
            maxPrice = 22800.0,
            arrivalsQuintals = 3600.0,
            date = "12 Oct 2026",
            priceChangePercent = 3.1,
            mspPrice = 0.0,
            historical7Days = listOf(20400.0, 20650.0, 20900.0, 21100.0, 21250.0, 21350.0, 21400.0),
            historical30Days = listOf(
                18500.0, 19000.0, 19500.0, 20000.0, 20500.0, 20900.0, 21100.0, 21250.0,
                21300.0, 21320.0, 21350.0, 21370.0, 21380.0, 21390.0, 21400.0, 21400.0
            )
        )
    )

    val sampleSellAdvisorInsights: List<SellTimingInsight> = listOf(
        SellTimingInsight(
            cropName = "Onion",
            marketName = "Lasalgaon & Pune Mandi",
            action = SellAction.HOLD_STRONG,
            title = "Hold stock for 10-14 days. Diwali & festive festival demand surge ahead",
            summary = "Heavy buffer procurement by NAFED underway. Kharif arrivals in northern Karnataka are delayed by 2 weeks due to delayed monsoon exit. Spot mandi price is up +8.5% this week.",
            arrivalForecast = "Arrivals currently 8,900 qtl (low supply). Projected to stay tight till late October.",
            weatherWarning = "Low rainfall in Nashik belt; suitable for well-ventilated kanda chawl storage.",
            projectedPriceChange = "+₹350 to +₹500 / Quintal projected gain",
            confidencePercent = 89
        ),
        SellTimingInsight(
            cropName = "Tomato",
            marketName = "Kolar & Bengaluru APMC",
            action = SellAction.SELL_URGENT,
            title = "Sell immediately within 24-48 hours. Flood of arrivals incoming",
            summary = "Fresh peak pickings from Madanapalle and Chittoor belts hitting southern mandis simultaneously. Perishability risk is acute with rain showers forecasted.",
            arrivalForecast = "Arrivals surging +40% next week (est. 7,500 qtl daily).",
            weatherWarning = "High humidity (82%) with thunderstorm risk: fruit cracking and rot risk elevated.",
            projectedPriceChange = "-₹200 to -₹350 / Quintal price drop expected",
            confidencePercent = 94
        ),
        SellTimingInsight(
            cropName = "Soybean",
            marketName = "Indore & Latur Mandi",
            action = SellAction.HOLD_STRONG,
            title = "Hold in dry godown. International edible oil import duty hike supportive",
            summary = "Domestic crushing plants are ramping up post-harvest procurement. Moisture discount is currently penalizing newly harvested damp pods by ₹250/qtl. Drying stock increases grade to Premium A.",
            arrivalForecast = "Current peak arrivals leveling off over the next 15 days.",
            weatherWarning = "Ensure moisture below 10% before godown bagging to prevent yellow bean fungal discoloration.",
            projectedPriceChange = "+₹180 to +₹240 / Quintal expected post-drying",
            confidencePercent = 82
        ),
        SellTimingInsight(
            cropName = "Wheat",
            marketName = "Khanna & Indore Mandi",
            action = SellAction.FAVORABLE_SELL,
            title = "Good time for phased selling (30-40% of lot). Prices near 6-month high",
            summary = "Mandi price (₹2,620) is trading comfortably +15.1% above Govt MSP (₹2,275). Excellent realization for farmers with immediate liquidity or rabi sowing input expenses.",
            arrivalForecast = "Arrivals steady at 4,500 qtl/day with high miller demand.",
            weatherWarning = "Dry weather; safe transport and loading conditions.",
            projectedPriceChange = "Prices expected to stay range-bound around ₹2,600-₹2,650/qtl.",
            confidencePercent = 78
        ),
        SellTimingInsight(
            cropName = "Areca Nut",
            marketName = "Shivamogga & Sirsi APMC",
            action = SellAction.HOLD_STRONG,
            title = "Hold Rashi & Chali lots. Export and pan masala industry restocking surge",
            summary = "Karnataka arecanut production has experienced lower fruit set in coastal belts due to heavy July monsoon. Upcountry pan masala and gutkha manufacturers in Kanpur and Gujarat are placing bulk purchase orders for Deepavali.",
            arrivalForecast = "Arrivals currently 3,450 qtl (down 18% vs last year). Steady pipeline.",
            weatherWarning = "Keep stored nuts in dry moisture-proof gunny bags with adequate aeration to prevent Kole roga fungal blemishes.",
            projectedPriceChange = "+₹2,200 to +₹3,500 / Quintal projected rally",
            confidencePercent = 91
        ),
        SellTimingInsight(
            cropName = "Coconut",
            marketName = "Tiptur & Arsikere APMC",
            action = SellAction.HOLD_STRONG,
            title = "Hold Ball Copra stock for 3 weeks. North Indian festive demand peak",
            summary = "Tiptur Ball Copra is in massive demand across Delhi, Rajasthan, and UP markets for winter pooja and culinary preparations. NAFED minimum support price operations are acting as a solid floor.",
            arrivalForecast = "Arrivals steady at 4,100 qtl with aggressive competitive bidding among copra merchants.",
            weatherWarning = "Maintain godown humidity below 65% to preserve ivory-white kernel grade.",
            projectedPriceChange = "+₹800 to +₹1,200 / Quintal expected increase",
            confidencePercent = 88
        ),
        SellTimingInsight(
            cropName = "Cocoa",
            marketName = "CAMPCO Puttur & Mangaluru",
            action = SellAction.HOLD_STRONG,
            title = "Hold dry fermented beans. Global chocolate supply crunch historic highs",
            summary = "Global cocoa bean deficit in West Africa (Ivory Coast & Ghana) has pushed domestic chocolate processors like CAMPCO, Mondelez, and Amul to source aggressively from South Indian growers.",
            arrivalForecast = "Tight arrivals across Dakshina Kannada and Wayanad belts.",
            weatherWarning = "Ensure bean fermentation protocol is complete and moisture level strictly below 7.5%.",
            projectedPriceChange = "+₹3,000 to +₹5,500 / Quintal historic premium",
            confidencePercent = 95
        ),
        SellTimingInsight(
            cropName = "Banana",
            marketName = "Bengaluru & Jalgaon APMC",
            action = SellAction.FAVORABLE_SELL,
            title = "Sell ripe lots within 48-72 hours. Excellent realization for Yelakki Bale",
            summary = "Yelakki banana spot prices in Bengaluru are trading at ₹4,650/qtl due to intense wedding and Navaratri/Deepavali retail consumption across Karnataka.",
            arrivalForecast = "Daily arrivals at 4,500 qtl meeting active retail absorption.",
            weatherWarning = "Transport under ventilated crates to avoid transit bruising.",
            projectedPriceChange = "Prices expected to remain firm around ₹4,600-₹4,800/qtl.",
            confidencePercent = 84
        ),
        SellTimingInsight(
            cropName = "Pineapple",
            marketName = "Vazhakulam & Sirsi APMC",
            action = SellAction.FAVORABLE_SELL,
            title = "Harvest ripe fruits now. High festival demand across North Indian markets",
            summary = "GI-tagged Vazhakulam Mauritius pineapple is seeing strong truck dispatches to Delhi, Mumbai, and Ahmedabad. Spot prices are holding high at ₹4,200/qtl.",
            arrivalForecast = "Arrivals 2,700 qtl steady with brisk fruit juice & wholesale demand.",
            weatherWarning = "Avoid keeping mature fruits in wet fields; dispatch immediately to prevent bottom fungal rot.",
            projectedPriceChange = "+₹200 to +₹350 / Quintal short-term premium",
            confidencePercent = 87
        ),
        SellTimingInsight(
            cropName = "Coffee",
            marketName = "Chikmagalur & Kodagu APMC",
            action = SellAction.HOLD_STRONG,
            title = "Hold Arabica parchment and Robusta cherry lots. London & ICE futures bull run",
            summary = "Global coffee bean deficit in Brazil and Vietnam has pushed international Robusta and Arabica prices to multi-year highs. Local curing works are competing aggressively for dry parchment.",
            arrivalForecast = "Pre-blossom arrivals are low at 1,800 qtl. Curers offering spot premiums.",
            weatherWarning = "Ensure warehouse moisture below 10.5% with double polythene bottom lining.",
            projectedPriceChange = "+₹1,500 to +₹2,800 / Quintal expected increase",
            confidencePercent = 92
        )
    )

    val sampleWeatherRisks: List<WeatherAgriRisk> = listOf(
        WeatherAgriRisk(
            district = "Pune",
            state = "Maharashtra",
            temperatureCelsius = 28,
            humidityPercent = 68,
            precipitationRiskPercent = 25,
            condition = "Partly Cloudy",
            rainAlertText = "Low rain probability (25%). Field drying conditions are good.",
            harvestDryingIndex = "Optimal (Safe for open drying yards)",
            godownStorageAdvisory = "Moisture levels stable. Standard aeration recommended."
        ),
        WeatherAgriRisk(
            district = "Nashik",
            state = "Maharashtra",
            temperatureCelsius = 26,
            humidityPercent = 74,
            precipitationRiskPercent = 35,
            condition = "Overcast with Light Breeze",
            rainAlertText = "Isolated light drizzles expected evening. Cover open onion stacks with tarpaulins.",
            harvestDryingIndex = "Moderate Risk (Keep covers on standby)",
            godownStorageAdvisory = "Run exhaust fans to maintain humidity below 70%."
        ),
        WeatherAgriRisk(
            district = "Ludhiana",
            state = "Punjab",
            temperatureCelsius = 31,
            humidityPercent = 48,
            precipitationRiskPercent = 5,
            condition = "Clear Sunny",
            rainAlertText = "Zero rain risk for next 7 days. Excellent paddy harvesting & transit window.",
            harvestDryingIndex = "Superior (Ideal harvest sun exposure)",
            godownStorageAdvisory = "Optimal moisture retention. Low pest hazard."
        ),
        WeatherAgriRisk(
            district = "Guntur",
            state = "Andhra Pradesh",
            temperatureCelsius = 33,
            humidityPercent = 62,
            precipitationRiskPercent = 15,
            condition = "Warm & Dry",
            rainAlertText = "High sunshine hours. Ideal for open red chilli drying platforms.",
            harvestDryingIndex = "Optimal (Sun drying efficiency >90%)",
            godownStorageAdvisory = "Cold storage recommended for color retention (ASTA level)."
        )
    )
}
