package com.example.data.model

import com.example.data.localization.AppLanguage

data class CropVariety(
    val id: String,
    val cropName: String,
    val nameEn: String,
    val nameKn: String,
    val nameHi: String,
    val typicalPricePerQtl: Double = 0.0,
    val descriptionEn: String = "",
    val descriptionKn: String = "",
    val descriptionHi: String = ""
) {
    fun localizedName(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> nameEn
        AppLanguage.KANNADA -> "$nameKn ($nameEn)"
        AppLanguage.HINDI -> "$nameHi ($nameEn)"
    }

    fun shortLocalizedName(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> nameEn
        AppLanguage.KANNADA -> nameKn
        AppLanguage.HINDI -> nameHi
    }
}

object CropVarietyCatalog {

    val varietiesMap: Map<String, List<CropVariety>> = mapOf(
        // Commercial & Plantation Crops
        "Areca Nut" to listOf(
            CropVariety("areca_rashi", "Areca Nut", "Rashi Idilu", "ರಾಶಿ ಇಡಿಲು", "राशी इडिलू", 55200.0),
            CropVariety("areca_bette", "Areca Nut", "Bette", "ಬೆಟ್ಟೆ", "बेट्टे", 48500.0),
            CropVariety("areca_chali", "Areca Nut", "Chali / Supari", "ಚಾಲಿ ಸುಪಾರಿ", "चाली सुपारी", 42300.0),
            CropVariety("areca_gorabal", "Areca Nut", "Gorabal", "ಗೋರಬಲು", "गोरबल", 38000.0),
            CropVariety("areca_koka", "Areca Nut", "Koka", "ಕೋಕಾ", "कोका", 29500.0),
            CropVariety("areca_tatte", "Areca Nut", "Tatte Bette", "ತಟ್ಟೆ ಬೆಟ್ಟೆ", "तट्टे बेट्टे", 45000.0),
            CropVariety("areca_phaton", "Areca Nut", "Phaton (Boiled)", "ಫಟೋನ್", "फटोन", 51000.0)
        ),

        "Coconut" to listOf(
            CropVariety("coco_tiptur", "Coconut", "Tiptur Copra", "ತಿಪಟೂರು ಕೊಬ್ಬರಿ", "तिप्तूर खोपरा", 14200.0),
            CropVariety("coco_ball", "Coconut", "Ball Copra (Gundu)", "ಗುಂಡು ಕೊಬ್ಬರಿ", "गोला खोपरा", 15800.0),
            CropVariety("coco_tender", "Coconut", "Tender Coconut (Elaneeru)", "ಎಳನೀರು", "डाब / कच्चा नारियल", 3500.0),
            CropVariety("coco_milling", "Coconut", "Milling Copra", "ಮಿಲ್ಲಿಂಗ್ ಕೊಬ್ಬರಿ", "मिलिंग खोपरा", 11400.0),
            CropVariety("coco_dry", "Coconut", "Grade-1 Dry Coconut", "ಗ್ರೇಡ್-1 ಒಣ ತೆಂಗಿನಕಾಯಿ", "सूखा नारियल", 12800.0)
        ),

        "Cocoa" to listOf(
            CropVariety("cocoa_wet", "Cocoa", "Wet Beans (Hasi Bija)", "ಹಸಿ ಕೋಕೋ ಬೀಜ", "गीली फलियां", 11500.0),
            CropVariety("cocoa_dry", "Cocoa", "Dry Beans (CAMPCO)", "ಒಣ ಕೋಕೋ ಬೀಜ (ಕ್ಯಾಂಪ್ಕೋ)", "सूखी फलियां", 31200.0),
            CropVariety("cocoa_grade_a", "Cocoa", "Grade-A Fermented", "ಗ್ರೇಡ್-ಎ ಫರ್ಮೆಂಟೆಡ್", "ग्रेड-ए कोको", 33500.0),
            CropVariety("cocoa_organic", "Cocoa", "Organic Estate Cocoa", "ಸಾವಯವ ಎಸ್ಟೇಟ್ ಕೋಕೋ", "ऑर्गेनिक कोको", 34800.0)
        ),

        "Banana" to listOf(
            CropVariety("banana_yelakki", "Banana", "Yelakki (Elakki Bale)", "ಏಲಕ್ಕಿ ಬಾಳೆ", "इलायची केला", 3400.0),
            CropVariety("banana_nendran", "Banana", "Nendran (Chips Variety)", "ನೇಂದ್ರನ್ ಬಾಳೆ", "नेन्द्रन केला", 4100.0),
            CropVariety("banana_robusta", "Banana", "Robusta / Grand Naine", "ರೋಬಸ್ಟಾ ಬಾಳೆ", "रोबस्टा केला", 1850.0),
            CropVariety("banana_pachabale", "Banana", "Pachabale (Cavendish)", "ಪಚ್ಚಬಾಳೆ", "हरी केला", 1600.0),
            CropVariety("banana_red", "Banana", "Red Banana (Chandra Bale)", "ಕೆಂಪು ಬಾಳೆ / ಚಂದ್ರಬಾಳೆ", "लाल केला", 4800.0)
        ),

        "Pineapple" to listOf(
            CropVariety("pine_kew", "Pineapple", "Kew Variety (Vazhakulam)", "ಕೇವ್ ತಳಿ (ವಾಳಕ್ಕುಳಂ)", "क्यू किस्म", 3800.0),
            CropVariety("pine_queen", "Pineapple", "Queen Victoria", "ಕ್ವೀನ್ ವಿಕ್ಟೋರಿಯಾ", "क्वीन किस्म", 4200.0),
            CropVariety("pine_mauritius", "Pineapple", "Mauritius Smooth", "ಮಾರಿಷಸ್ ಸ್ಮೂತ್", "मॉरीशस किस्म", 3950.0)
        ),

        "Coffee" to listOf(
            CropVariety("coffee_arabica_pl", "Coffee", "Arabica Plantation A", "ಅರೇಬಿಕಾ ಪ್ಲಾಂಟೇಶನ್-ಎ", "अरेबिका प्लांटेशन-ए", 31500.0),
            CropVariety("coffee_robusta_ch", "Coffee", "Robusta Cherry", "ರೋಬಸ್ಟಾ ಚೆರ್ರಿ", "रोबस्टा चेरी", 18900.0),
            CropVariety("coffee_robusta_pa", "Coffee", "Robusta Parchment", "ರೋಬಸ್ಟಾ ಪಾರ್ಚ್‌ಮೆಂಟ್", "रोबस्टा पार्चमेंट", 22400.0),
            CropVariety("coffee_arabica_pa", "Coffee", "Arabica Parchment", "ಅರೇಬಿಕಾ ಪಾರ್ಚ್‌ಮೆಂಟ್", "अरेबिका पार्चमेंट", 33000.0)
        ),

        "Black Pepper" to listOf(
            CropVariety("pepper_panniyur", "Black Pepper", "Panniyur-1 Bold", "ಪನ್ನಿಯೂರು-1 ಬೋಲ್ಡ್", "पन्नियूर-1 बोल्ड", 68500.0),
            CropVariety("pepper_karimunda", "Black Pepper", "Karimunda Traditional", "ಕರಿಮುಂಡ ತಳಿ", "करीमुंडा", 69800.0),
            CropVariety("pepper_garbled", "Black Pepper", "Malabar Garbled Grade", "ಮಲಬಾರ್ ಗಾರ್ಬಲ್ಡ್", "मालाबार गारबल्ड", 71200.0)
        ),

        "Cardamom" to listOf(
            CropVariety("card_8mm", "Cardamom", "Small Green 8mm+ Bold", "ದಪ್ಪ ಹಸಿರು ಏಲಕ್ಕಿ 8ಮಿಮೀ", "बोल्ड हरी इलायची 8मिमी", 248000.0),
            CropVariety("card_7mm", "Cardamom", "Medium Green 7mm", "ಮಧ್ಯಮ ಹಸಿರು ಏಲಕ್ಕಿ 7ಮಿಮೀ", "मध्यम हरी इलायची", 215000.0),
            CropVariety("card_alleppey", "Cardamom", "Alleppey Green Extra Bold", "ಅಲೆಪ್ಪಿ ಗ್ರೀನ್ ಎಕ್ಸ್‌ಟ್ರಾ", "अलेप्पी ग्रीन बोल्ड", 262000.0)
        ),

        "Cashew Nut" to listOf(
            CropVariety("cashew_raw", "Cashew Nut", "Raw Nut with Shell", "ಹಸಿ ಗೋಡಂಬಿ ಬೀಜ", "कच्चा काजू बीज", 13200.0),
            CropVariety("cashew_w180", "Cashew Nut", "W-180 King Jumbo White", "ಡಬ್ಲ್ಯೂ-180 ಜಂಬೋ ಗೋಡಂಬಿ", "डब्ल्यू-180 जंबो काजू", 98000.0),
            CropVariety("cashew_w240", "Cashew Nut", "W-240 Standard Large", "ಡಬ್ಲ್ಯೂ-240 ಗೋಡಂಬಿ", "डब्ल्यू-240 काजू", 84000.0),
            CropVariety("cashew_w320", "Cashew Nut", "W-320 Popular Kernel", "ಡಬ್ಲ್ಯೂ-320 ಜನಪ್ರಿಯ ಗ್ರೇಡ್", "डब्ल्यू-320 काजू", 76000.0)
        ),

        "Ginger" to listOf(
            CropVariety("ginger_fresh", "Ginger", "Fresh Green Ginger", "ಹಸಿ ಶುಂಠಿ", "ताजा हरा अदरक", 8400.0),
            CropVariety("ginger_dry", "Ginger", "Dry Ginger / Sonth", "ಒಣ ಶುಂಠಿ (ಸೊಂಠಿ)", "सोंठ (सूखा अदरक)", 28500.0),
            CropVariety("ginger_rio", "Ginger", "Rio de Janeiro High Yield", "ರಿಯೋ ತಳಿ ಶುಂಠಿ", "रियो डी जनेरियो अदरक", 9100.0)
        ),

        "Sugarcane & Jaggery" to listOf(
            CropVariety("sugar_jaggery_block", "Sugarcane & Jaggery", "Organic Jaggery Blocks (Bella)", "ಸಾವಯವ ಬೆಲ್ಲದ ಅಚ್ಚು", "जैविक गुड़ भेली", 4800.0),
            CropVariety("sugar_jaggery_powder", "Sugarcane & Jaggery", "Jaggery Powder / Shakkar", "ಬೆಲ್ಲದ ಪುಡಿ (ಸಕ್ಕರೆ)", "गुड़ पाउडर / शक्कर", 5600.0),
            CropVariety("sugar_cane_raw", "Sugarcane & Jaggery", "Co-86032 Sweet Cane", "ಕೋ-86032 ಸಿಹಿ ಕಬ್ಬು", "को-86032 गन्ना", 315.0)
        ),

        "Rubber" to listOf(
            CropVariety("rubber_rss4", "Rubber", "RSS-4 Sheet Rubber", "ಆರ್‌ಎಸ್‌ಎಸ್-4 ಶೀಟ್ ರಬ್ಬರ್", "आरएसएस-4 शीट रबर", 19200.0),
            CropVariety("rubber_rss5", "Rubber", "RSS-5 Commercial Grade", "ಆರ್‌ಎಸ್‌ಎಸ್-5 ಗ್ರೇಡ್", "आरएसएस-5 रबर", 18400.0),
            CropVariety("rubber_isnr20", "Rubber", "ISNR-20 Block Rubber", "ಐಎಸ್‌ಎನ್‌ಆರ್-20 ಬ್ಲಾಕ್", "आईएसएनआर-20 रबर", 17900.0)
        ),

        // Cereals, Pulses, Oilseeds & Vegetables
        "Wheat" to listOf(
            CropVariety("wheat_sharbati", "Wheat", "Sharbati (High Quality)", "ಶರ್ಬತಿ ಗೋಧಿ", "शरबती गेहूं", 2620.0),
            CropVariety("wheat_lokwan", "Wheat", "Lokwan (Mill Quality)", "ಲೋಕವನ್ ಗೋಧಿ", "लोकवन गेहूं", 2510.0),
            CropVariety("wheat_kalyan", "Wheat", "Kalyan Sona", "ಕಲ್ಯಾಣ ಸೋನಾ", "कल्याण सोना", 2460.0),
            CropVariety("wheat_durum", "Wheat", "Durum MP Quality", "ಡ್ಯೂರಂ ಗೋಧಿ", "डूरम गेहूं", 2740.0)
        ),

        "Paddy (Basmati)" to listOf(
            CropVariety("paddy_1121", "Paddy (Basmati)", "Basmati 1121 Pusa", "ಬಾಸ್ಮತಿ 1121 ಪೂಸಾ", "बासमती 1121 पूसा", 4250.0),
            CropVariety("paddy_sona", "Paddy (Basmati)", "Sona Masoori", "ಸೋನಾ ಮಸೂರಿ", "सोना मसूरी", 2650.0),
            CropVariety("paddy_trad", "Paddy (Basmati)", "Basmati Traditional", "ಸಾಂಪ್ರದಾಯಿಕ ಬಾಸ್ಮತಿ", "पारंपरिक बासमती", 4600.0),
            CropVariety("paddy_kolam", "Paddy (Basmati)", "Wada Kolam", "ವಾಡಾ ಕೊಲಂ", "वाडा कोलम", 3100.0)
        ),

        "Paddy" to listOf(
            CropVariety("paddy_sona_gen", "Paddy", "Sona Masoori", "ಸೋನಾ ಮಸೂರಿ", "सोना मसूरी", 2650.0),
            CropVariety("paddy_ir64", "Paddy", "IR-64 Raw Rice", "ಐಆರ್-64 ಕಚ್ಚಾ ಅಕ್ಕಿ", "आईआर-64 चावल", 2320.0),
            CropVariety("paddy_jaya", "Paddy", "Jaya Variety", "ಜಯಾ ತಳಿ", "जया धान", 2280.0)
        ),

        "Soybean" to listOf(
            CropVariety("soy_js335", "Soybean", "Yellow JS-335", "ಹಳದಿ ಜೆಎಸ್-335", "पीला जेएस-335", 4780.0),
            CropVariety("soy_js9560", "Soybean", "Yellow JS-9560 Early", "ಜೆಎಸ್-9560 ಮುಂಗಾರು", "जेएस-9560 सोयाबीन", 4850.0),
            CropVariety("soy_grade_a", "Soybean", "Yellow Grade-A", "ಹಳದಿ ಗ್ರೇಡ್-ಎ", "पीला ग्रेड-ए", 4820.0)
        ),

        "Onion" to listOf(
            CropVariety("onion_red_med", "Onion", "Red Medium", "ಕೆಂಪು ಮಧ್ಯಮ", "लाल मध्यम प्याज", 3450.0),
            CropVariety("onion_nashik", "Onion", "Nashik Special Garwa", "ನಾಸಿಕ್ ಗಾರ್ವಾ ಸ್ಪೆಷಲ್", "नासिक गारवा प्याज", 3620.0),
            CropVariety("onion_white", "Onion", "White Onion (Processing)", "ಬಿಳಿ ಈರುಳ್ಳಿ", "सफेद प्याज", 2980.0),
            CropVariety("onion_rose", "Onion", "Bangalore Rose Export", "ಬೆಂಗಳೂರು ರೋಸ್ ಈರುಳ್ಳಿ", "बैंगलोर रोज़ प्याज", 4400.0)
        ),

        "Tomato" to listOf(
            CropVariety("tomato_hybrid", "Tomato", "Hybrid Vaishali", "ಹೈಬ್ರಿಡ್ ವೈಶಾಲಿ", "हाइब्रिड वैशाली टमाटर", 2400.0),
            CropVariety("tomato_desi", "Tomato", "Desi Local (Nati)", "ನಾಟಿ ಟೊಮೇಟೊ", "देसी लोकल टमाटर", 2250.0),
            CropVariety("tomato_abhinav", "Tomato", "Abhinav Seminis", "ಅಭಿನವ್ ಸೆಮಿನಿಸ್", "अभिनव टमाटर", 2550.0)
        ),

        "Cotton" to listOf(
            CropVariety("cotton_bt", "Cotton", "Bt Cotton Medium Staple", "ಬಿಟಿ ಹತ್ತಿ ಮಧ್ಯಮ", "बीटी कपास मध्यम स्टेपल", 7200.0),
            CropVariety("cotton_shankar6", "Cotton", "Shankar-6 Long Staple", "ಶಂಕರ್-6 ಲಾಂಗ್ ಸ್ಟೇಪಲ್", "शंकर-6 लंबा स्टेपल", 7550.0),
            CropVariety("cotton_dch32", "Cotton", "DCH-32 Extra Long", "ಡಿಸಿಎಚ್-32 ಎಕ್ಸ್‌ಟ್ರಾ ಲಾಂಗ್", "डीसीएच-32 एक्स्ट्रा लॉन्ग", 8100.0)
        ),

        "Mustard" to listOf(
            CropVariety("mustard_pusa", "Mustard", "Pusa Bold", "ಪೂಸಾ ಬೋಲ್ಡ್", "पूसा बोल्ड सरसों", 5450.0),
            CropVariety("mustard_varuna", "Mustard", "Varuna (T-59)", "ವರುಣಾ (ಟಿ-59)", "वरुणा सरसों", 5380.0),
            CropVariety("mustard_black", "Mustard", "Black Rai (Rai Mustard)", "ಕಪ್ಪು ಸಾಸಿವೆ / ರೈ", "काली राई", 5520.0)
        ),

        "Chana" to listOf(
            CropVariety("chana_desi", "Chana", "Desi Brown Gram", "ಕಂದು ಕಡಲೆ (ದೇಸಿ)", "देसी भूरा चना", 5950.0),
            CropVariety("chana_kabuli", "Chana", "Kabuli Dollar Chana", "ಕಾಬೂಲಿ / ಡಾಲರ್ ಕಡಲೆ", "काबुली डॉलर चना", 11200.0),
            CropVariety("chana_annigeri", "Chana", "Annigeri-1 (Karnataka)", "ಅಣ್ಣಿಗೇರಿ-1 ಕಡಲೆ", "अन्निगेरी-1 चना", 6100.0)
        ),

        "Potato" to listOf(
            CropVariety("potato_jyoti", "Potato", "Kufri Jyoti", "ಕುಫ್ರಿ ಜ್ಯೋತಿ", "कुफरी ज्योति आलू", 1750.0),
            CropVariety("potato_chipsona", "Potato", "Chipsona Processing", "ಚಿಪ್ಸೋನಾ ಆಲೂಗಡ್ಡೆ", "चिप्सोना आलू", 2100.0),
            CropVariety("potato_pukhraj", "Potato", "Kufri Pukhraj", "ಕುಫ್ರಿ ಪುಖ್‌ರಾಜ್", "कुफरी पुखराज", 1680.0)
        ),

        "Red Chilli" to listOf(
            CropVariety("chilli_byadagi_kaddi", "Red Chilli", "Byadagi Kaddi (High Color)", "ಬ್ಯಾಡಗಿ ಕಡ್ಡಿ", "ब्याडगी कड्डी मिर्च", 44000.0),
            CropVariety("chilli_byadagi_dabbi", "Red Chilli", "Byadagi Dabbi", "ಬ್ಯಾಡಗಿ ಡಬ್ಬಿ", "ब्याडगी डब्बी मिर्च", 48000.0),
            CropVariety("chilli_guntur", "Red Chilli", "Guntur Sannam 334", "ಗುಂಟೂರು ಸನ್ನಂ 334", "गुंटूर सन्नम मिर्च", 23500.0),
            CropVariety("chilli_teja", "Red Chilli", "Teja Red Hot", "ತೇಜಾ ಕೆಂಪು ಖಾರ ಮೆಣಸು", "तेजा लाल मिर्च", 26800.0)
        ),

        "Turmeric" to listOf(
            CropVariety("turmeric_salem", "Turmeric", "Salem Finger (Curcumin 4%+)", "ಸೇಲಂ ಕೊಂಬು ಅರಿಶಿನ", "सेलम फिंगर हल्दी", 16800.0),
            CropVariety("turmeric_rajapore", "Turmeric", "Rajapore Quality", "ರಾಜಾಪುರ ಅರಿಶಿನ", "राजापुर हल्दी", 18200.0),
            CropVariety("turmeric_nizam", "Turmeric", "Nizamabad Double Polished", "ನಿಜಾಮಾಬಾದ್ ಪಾಲಿಶ್ಡ್", "निजामाबाद पॉलिश हल्दी", 15900.0)
        ),

        "Cumin (Jeera)" to listOf(
            CropVariety("jeera_unjha", "Cumin (Jeera)", "Unjha Machine Cleaned", "ಉಂಝಾ ಮಷೀನ್ ಕ್ಲೀನ್ಡ್", "ऊंझा मशीन क्लीन जीरा", 29500.0),
            CropVariety("jeera_gc4", "Cumin (Jeera)", "Gujarat Cumin-4", "ಗುಜರಾತ್ ಜೀರಿಗೆ-4", "गुजरात जीरा-4", 28200.0)
        )
    )

    fun getVarietiesForCrop(cropName: String): List<CropVariety> {
        val trimmed = cropName.trim()
        val directMatch = varietiesMap[trimmed]
        if (directMatch != null) return directMatch

        // Fuzzy match case insensitive
        varietiesMap.entries.firstOrNull { it.key.equals(trimmed, ignoreCase = true) }?.let {
            return it.value
        }

        // Substring / alias check
        val lower = trimmed.lowercase()
        return when {
            lower.contains("areca") || lower.contains("adike") || lower.contains("supari") ->
                varietiesMap["Areca Nut"] ?: emptyList()
            lower.contains("coco nut") || lower.contains("coconut") || lower.contains("copra") || lower.contains("tengu") ->
                varietiesMap["Coconut"] ?: emptyList()
            lower.contains("cocoa") || lower.contains("cacao") || lower.contains("campco") ->
                varietiesMap["Cocoa"] ?: emptyList()
            lower.contains("banana") || lower.contains("bale") || lower.contains("kela") ->
                varietiesMap["Banana"] ?: emptyList()
            lower.contains("pine") || lower.contains("ananas") ->
                varietiesMap["Pineapple"] ?: emptyList()
            lower.contains("coffee") || lower.contains("kafi") ->
                varietiesMap["Coffee"] ?: emptyList()
            lower.contains("pepper") || lower.contains("menasu") ->
                varietiesMap["Black Pepper"] ?: emptyList()
            lower.contains("cardamom") || lower.contains("elakki") || lower.contains("elaichi") ->
                varietiesMap["Cardamom"] ?: emptyList()
            lower.contains("cashew") || lower.contains("kaju") ->
                varietiesMap["Cashew Nut"] ?: emptyList()
            lower.contains("ginger") || lower.contains("shunti") || lower.contains("adrak") ->
                varietiesMap["Ginger"] ?: emptyList()
            lower.contains("sugar") || lower.contains("jaggery") || lower.contains("bella") || lower.contains("kabbu") || lower.contains("gur") ->
                varietiesMap["Sugarcane & Jaggery"] ?: emptyList()
            lower.contains("rubber") ->
                varietiesMap["Rubber"] ?: emptyList()
            lower.contains("wheat") || lower.contains("godhi") || lower.contains("gehun") ->
                varietiesMap["Wheat"] ?: emptyList()
            lower.contains("paddy") || lower.contains("rice") || lower.contains("bhatta") || lower.contains("dhan") ->
                varietiesMap["Paddy (Basmati)"] ?: emptyList()
            lower.contains("soy") ->
                varietiesMap["Soybean"] ?: emptyList()
            lower.contains("onion") || lower.contains("eerulli") || lower.contains("pyaz") ->
                varietiesMap["Onion"] ?: emptyList()
            lower.contains("tomato") ->
                varietiesMap["Tomato"] ?: emptyList()
            lower.contains("cotton") || lower.contains("kapas") || lower.contains("hathi") ->
                varietiesMap["Cotton"] ?: emptyList()
            lower.contains("chilli") || lower.contains("mirchi") || lower.contains("menasina") ->
                varietiesMap["Red Chilli"] ?: emptyList()
            lower.contains("turmeric") || lower.contains("haldi") || lower.contains("arishina") ->
                varietiesMap["Turmeric"] ?: emptyList()
            lower.contains("cumin") || lower.contains("jeera") || lower.contains("jirige") ->
                varietiesMap["Cumin (Jeera)"] ?: emptyList()
            else -> emptyList()
        }
    }

    fun getAllCrops(): List<String> = varietiesMap.keys.toList()
}
