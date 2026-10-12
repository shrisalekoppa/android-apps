package com.example.data.localization

enum class AppLanguage(
    val code: String,
    val displayName: String,
    val nativeName: String,
    val shortLabel: String
) {
    ENGLISH("en", "English", "English", "EN"),
    KANNADA("kn", "Kannada", "ಕನ್ನಡ", "ಕನ್"),
    HINDI("hi", "Hindi", "हिन्दी", "हि")
}

object LocalizationStrings {

    fun getAppName(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "KrishiMandi"
        AppLanguage.KANNADA -> "ಕೃಷಿ ಮಂಡಿ"
        AppLanguage.HINDI -> "कृषि मंडी"
    }

    // Navigation Labels
    fun navHome(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Home"
        AppLanguage.KANNADA -> "ಮುಖಪುಟ"
        AppLanguage.HINDI -> "होम"
    }

    fun navPrices(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Prices"
        AppLanguage.KANNADA -> "ದರಗಳು"
        AppLanguage.HINDI -> "मंडी भाव"
    }

    fun navSearch(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Google Search"
        AppLanguage.KANNADA -> "ಗೂಗಲ್ ಹುಡುಕಾಟ"
        AppLanguage.HINDI -> "गूगल खोज"
    }

    fun navTrends(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Trends"
        AppLanguage.KANNADA -> "ಪ್ರವೃತ್ತಿ"
        AppLanguage.HINDI -> "रुझान"
    }

    fun navAdvisor(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Advisor"
        AppLanguage.KANNADA -> "ಸಲಹೆಗಾರ"
        AppLanguage.HINDI -> "सलाहकार"
    }

    fun navAlerts(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Alerts"
        AppLanguage.KANNADA -> "ಎಚ್ಚರಿಕೆಗಳು"
        AppLanguage.HINDI -> "अलर्ट"
    }

    fun navReports(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Reports"
        AppLanguage.KANNADA -> "ವರದಿಗಳು"
        AppLanguage.HINDI -> "रिपोर्ट्स"
    }

    fun navProfile(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Profile"
        AppLanguage.KANNADA -> "ಪ್ರೊಫೈಲ್"
        AppLanguage.HINDI -> "प्रोफ़ाइल"
    }

    // Dashboard Strings
    fun greeting(lang: AppLanguage, name: String): String = when (lang) {
        AppLanguage.ENGLISH -> "Namaste, $name 🌾"
        AppLanguage.KANNADA -> "ನಮಸ್ಕಾರ, $name 🌾"
        AppLanguage.HINDI -> "नमस्ते, $name 🌾"
    }

    fun watchlist(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Watchlist"
        AppLanguage.KANNADA -> "ವೀಕ್ಷಣಾ ಪಟ್ಟಿ"
        AppLanguage.HINDI -> "वॉचलिस्ट"
    }

    fun activeAlerts(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Active Alerts"
        AppLanguage.KANNADA -> "ಸಕ್ರಿಯ ಎಚ್ಚರಿಕೆಗಳು"
        AppLanguage.HINDI -> "सक्रिय अलर्ट"
    }

    fun liveMandis(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Live Mandis"
        AppLanguage.KANNADA -> "ಲೈವ್ ಮಂಡಿಗಳು"
        AppLanguage.HINDI -> "लाइव मंडियां"
    }

    fun offlineMode(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Offline Mode (Local Cache Active)"
        AppLanguage.KANNADA -> "ಆಫ್‌ಲೈನ್ ಮೋಡ್ (ಸ್ಥಳೀಯ ಡೇಟಾ ಸಕ್ರಿಯ)"
        AppLanguage.HINDI -> "ऑफलाइन मोड (स्थानीय डेटा सक्रिय)"
    }

    fun connectedMode(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Connected (All-India Mandi Sync)"
        AppLanguage.KANNADA -> "ಆನ್‌ಲೈನ್ (ಅಖಿಲ ಭಾರತ ಮಂಡಿ ಸಿಂಕ್)"
        AppLanguage.HINDI -> "कनेक्टेड (अखिल भारतीय मंडी सिंक)"
    }

    fun lastSynced(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Last synced:"
        AppLanguage.KANNADA -> "ಕೊನೆಯ ಸಿಂಕ್:"
        AppLanguage.HINDI -> "अंतिम सिंक:"
    }

    fun keyToolsForFarmers(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Key Tools for Farmers"
        AppLanguage.KANNADA -> "ರೈತರಿಗೆ ಪ್ರಮುಖ ಸಾಧನಗಳು"
        AppLanguage.HINDI -> "किसानों के प्रमुख उपकरण"
    }

    fun marketTrends(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Market Trends"
        AppLanguage.KANNADA -> "ಮಾರುಕಟ್ಟೆ ಪ್ರವೃತ್ತಿ"
        AppLanguage.HINDI -> "बाजार रुझान"
    }

    fun sellAdvisor(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Sell Advisor"
        AppLanguage.KANNADA -> "ಮಾರಾಟ ಸಲಹೆಗಾರ"
        AppLanguage.HINDI -> "बिक्री सलाहकार"
    }

    fun priceAlerts(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Price Alerts"
        AppLanguage.KANNADA -> "ದರ ಎಚ್ಚರಿಕೆಗಳು"
        AppLanguage.HINDI -> "मूल्य अलर्ट"
    }

    fun exportReports(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Export Reports"
        AppLanguage.KANNADA -> "ವರದಿ ರಫ್ತು"
        AppLanguage.HINDI -> "रिपोर्ट निर्यात"
    }

    fun myMandiWatchlist(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "My Mandi Watchlist"
        AppLanguage.KANNADA -> "ನನ್ನ ಮಂಡಿ ವೀಕ್ಷಣಾ ಪಟ್ಟಿ"
        AppLanguage.HINDI -> "मेरी पसंदीदा मंडियां"
    }

    fun customDashboardSubtitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Custom dashboard for your region & crops"
        AppLanguage.KANNADA -> "ನಿಮ್ಮ ಪ್ರದೇಶ ಮತ್ತು ಬೆಳೆಗಳ ಕಸ್ಟಮ್ ಡ್ಯಾಶ್‌ಬೋರ್ಡ್"
        AppLanguage.HINDI -> "आपके क्षेत्र और फसलों के लिए डैशबोर्ड"
    }

    fun viewAll(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "View All"
        AppLanguage.KANNADA -> "ಎಲ್ಲವನ್ನೂ ವೀಕ್ಷಿಸಿ"
        AppLanguage.HINDI -> "सभी देखें"
    }

    // Price Cards & Filtering
    fun modalPrice(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Modal Price"
        AppLanguage.KANNADA -> "ಮಾದರಿ ದರ"
        AppLanguage.HINDI -> "मॉडल भाव"
    }

    fun perQuintal(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> " / Qtl"
        AppLanguage.KANNADA -> " / ಕ್ವಿಂಟಾಲ್"
        AppLanguage.HINDI -> " / क्विंटल"
    }

    fun searchPlaceholder(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Search crop, mandi, district..."
        AppLanguage.KANNADA -> "ಬೆಳೆ, ಮಂಡಿ, ಜಿಲ್ಲೆ ಹುಡುಕಿ..."
        AppLanguage.HINDI -> "फसल, मंडी, जिला खोजें..."
    }

    fun allStates(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "All States (India)"
        AppLanguage.KANNADA -> "ಎಲ್ಲಾ ರಾಜ್ಯಗಳು (ಭಾರತ)"
        AppLanguage.HINDI -> "सभी राज्य (भारत)"
    }

    fun arrivals(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Arrivals"
        AppLanguage.KANNADA -> "ಆವಕ"
        AppLanguage.HINDI -> "आवक"
    }

    fun range(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Range"
        AppLanguage.KANNADA -> "ಶ್ರೇಣಿ"
        AppLanguage.HINDI -> "सीमा"
    }

    // AI Advisor & Decision Support
    fun holdRecommendation(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "RECOMMENDED TO HOLD"
        AppLanguage.KANNADA -> "ದಾಸ್ತಾನು ಇರಿಸಲು ಶಿಫಾರಸು"
        AppLanguage.HINDI -> "स्टॉक रोकने की सलाह"
    }

    fun sellUrgentRecommendation(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "SELL NOW (RISK AHEAD)"
        AppLanguage.KANNADA -> "ಈಗಲೇ ಮಾರಿ (ಅಪಾಯವಿದೆ)"
        AppLanguage.HINDI -> "तुरंत बेचें (नुकसान का जोखिम)"
    }

    fun favorableSellRecommendation(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "GOOD TIME TO SELL"
        AppLanguage.KANNADA -> "ಮಾರಾಟಕ್ಕೆ ಸೂಕ್ತ ಸಮಯ"
        AppLanguage.HINDI -> "बेचने का अच्छा समय"
    }

    fun storageCalculator(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Storage ROI Calculator"
        AppLanguage.KANNADA -> "ಶೇಖರಣಾ ಲಾಭ ಕ್ಯಾಲ್ಕುಲೇಟರ್"
        AppLanguage.HINDI -> "भंडारण लाभ कैलकुलेटर"
    }

    fun weatherAnalytics(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Weather Analytics"
        AppLanguage.KANNADA -> "ಹವಾಮಾನ ವಿಶ್ಲೇಷಣೆ"
        AppLanguage.HINDI -> "मौसम विश्लेषण"
    }

    fun calculateButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Calculate Storage Profitability"
        AppLanguage.KANNADA -> "ಶೇಖರಣಾ ಲಾಭ ಲೆಕ್ಕಾಚಾರ ಮಾಡಿ"
        AppLanguage.HINDI -> "भंडारण लाभ की गणना करें"
    }

    // Commodity Localized Names
    fun localizedCropName(lang: AppLanguage, originalName: String): String {
        return when (originalName.lowercase()) {
            "wheat" -> when (lang) {
                AppLanguage.ENGLISH -> "Wheat"
                AppLanguage.KANNADA -> "ಗೋಧಿ (Wheat)"
                AppLanguage.HINDI -> "गेहूं (Wheat)"
            }
            "paddy (basmati)", "paddy" -> when (lang) {
                AppLanguage.ENGLISH -> "Paddy (Rice)"
                AppLanguage.KANNADA -> "ಭತ್ತ / ಅಕ್ಕಿ (Paddy)"
                AppLanguage.HINDI -> "धान / चावल (Paddy)"
            }
            "onion" -> when (lang) {
                AppLanguage.ENGLISH -> "Onion"
                AppLanguage.KANNADA -> "ಈರುಳ್ಳಿ (Onion)"
                AppLanguage.HINDI -> "प्याज (Onion)"
            }
            "tomato" -> when (lang) {
                AppLanguage.ENGLISH -> "Tomato"
                AppLanguage.KANNADA -> "ಟೊಮೇಟೊ (Tomato)"
                AppLanguage.HINDI -> "टमाटर (Tomato)"
            }
            "soybean" -> when (lang) {
                AppLanguage.ENGLISH -> "Soybean"
                AppLanguage.KANNADA -> "ಸೋಯಾಬೀನ್ (Soybean)"
                AppLanguage.HINDI -> "सोयाबीन (Soybean)"
            }
            "cotton" -> when (lang) {
                AppLanguage.ENGLISH -> "Cotton"
                AppLanguage.KANNADA -> "ಹತ್ತಿ (Cotton)"
                AppLanguage.HINDI -> "कपास (Cotton)"
            }
            "mustard" -> when (lang) {
                AppLanguage.ENGLISH -> "Mustard"
                AppLanguage.KANNADA -> "ಸಾಸಿವೆ (Mustard)"
                AppLanguage.HINDI -> "सरसों (Mustard)"
            }
            "chana", "chana (bengal gram)" -> when (lang) {
                AppLanguage.ENGLISH -> "Chana (Gram)"
                AppLanguage.KANNADA -> "ಕಡಲೆ (Chana)"
                AppLanguage.HINDI -> "चना (Chana)"
            }
            "potato" -> when (lang) {
                AppLanguage.ENGLISH -> "Potato"
                AppLanguage.KANNADA -> "ಆಲೂಗಡ್ಡೆ (Potato)"
                AppLanguage.HINDI -> "आलू (Potato)"
            }
            "red chilli" -> when (lang) {
                AppLanguage.ENGLISH -> "Red Chilli"
                AppLanguage.KANNADA -> "ಒಣ ಮೆಣಸಿನಕಾಯಿ (Chilli)"
                AppLanguage.HINDI -> "लाल मिर्च (Red Chilli)"
            }
            "turmeric" -> when (lang) {
                AppLanguage.ENGLISH -> "Turmeric"
                AppLanguage.KANNADA -> "ಅರಿಶಿನ (Turmeric)"
                AppLanguage.HINDI -> "हल्दी (Turmeric)"
            }
            "cumin (jeera)", "cumin" -> when (lang) {
                AppLanguage.ENGLISH -> "Cumin (Jeera)"
                AppLanguage.KANNADA -> "ಜೀರಿಗೆ (Jeera)"
                AppLanguage.HINDI -> "जीरा (Jeera)"
            }
            "areca nut", "arecanut", "adike", "supari", "betel nut" -> when (lang) {
                AppLanguage.ENGLISH -> "Areca Nut (Adike)"
                AppLanguage.KANNADA -> "ಅಡಿಕೆ (Areca Nut)"
                AppLanguage.HINDI -> "सुपारी (Areca Nut)"
            }
            "coconut", "coco nut", "copra", "tender coconut", "ball copra" -> when (lang) {
                AppLanguage.ENGLISH -> "Coconut & Copra"
                AppLanguage.KANNADA -> "ತೆಂಗಿನಕಾಯಿ / ಕೊಬ್ಬರಿ (Coconut)"
                AppLanguage.HINDI -> "नारियल / गोला (Coconut)"
            }
            "cocoa", "cocova", "cacao", "campco" -> when (lang) {
                AppLanguage.ENGLISH -> "Cocoa (Cocova)"
                AppLanguage.KANNADA -> "ಕೋಕೋ (Cocoa)"
                AppLanguage.HINDI -> "कोको (Cocoa)"
            }
            "banana", "bale", "yelakki", "nendran", "robusta" -> when (lang) {
                AppLanguage.ENGLISH -> "Banana (Bale)"
                AppLanguage.KANNADA -> "ಬಾಳೆಹಣ್ಣು (Banana)"
                AppLanguage.HINDI -> "केला (Banana)"
            }
            "pineapple", "pinapple", "ananas" -> when (lang) {
                AppLanguage.ENGLISH -> "Pineapple (Ananas)"
                AppLanguage.KANNADA -> "ಅನಾನಸ್ (Pineapple)"
                AppLanguage.HINDI -> "अनानास (Pineapple)"
            }
            "coffee", "arabica", "robusta" -> when (lang) {
                AppLanguage.ENGLISH -> "Coffee"
                AppLanguage.KANNADA -> "ಕಾಫಿ ಬೀಜ (Coffee)"
                AppLanguage.HINDI -> "कॉफ़ी (Coffee)"
            }
            "black pepper", "pepper", "menasu" -> when (lang) {
                AppLanguage.ENGLISH -> "Black Pepper"
                AppLanguage.KANNADA -> "ಕಾಳುಮೆಣಸು (Black Pepper)"
                AppLanguage.HINDI -> "काली मिर्च (Black Pepper)"
            }
            "cardamom", "elaichi", "elakki" -> when (lang) {
                AppLanguage.ENGLISH -> "Cardamom (Green)"
                AppLanguage.KANNADA -> "ಏಲಕ್ಕಿ (Cardamom)"
                AppLanguage.HINDI -> "इलायची (Cardamom)"
            }
            "cashew nut", "cashew", "kaju", "geru" -> when (lang) {
                AppLanguage.ENGLISH -> "Cashew Nut"
                AppLanguage.KANNADA -> "ಗೇರುಬೀಜ (Cashew)"
                AppLanguage.HINDI -> "काजू (Cashew)"
            }
            "ginger", "shunti", "adrak" -> when (lang) {
                AppLanguage.ENGLISH -> "Ginger (Shunti)"
                AppLanguage.KANNADA -> "ಶುಂಠಿ (Ginger)"
                AppLanguage.HINDI -> "अदरक / सोंठ (Ginger)"
            }
            "sugarcane & jaggery", "sugarcane", "jaggery", "gur", "bella", "kabbu" -> when (lang) {
                AppLanguage.ENGLISH -> "Sugarcane & Jaggery"
                AppLanguage.KANNADA -> "ಕಬ್ಬು / ಬೆಲ್ಲ (Sugarcane)"
                AppLanguage.HINDI -> "गन्ना / गुड़ (Sugarcane)"
            }
            "rubber", "natural rubber" -> when (lang) {
                AppLanguage.ENGLISH -> "Natural Rubber"
                AppLanguage.KANNADA -> "ರಬ್ಬರ್ (Rubber)"
                AppLanguage.HINDI -> "रबर (Rubber)"
            }
            else -> originalName
        }
    }

    // Category Localized Display
    fun localizedCategoryName(lang: AppLanguage, category: com.example.data.model.CommodityCategory): String = when (category) {
        com.example.data.model.CommodityCategory.ALL -> when (lang) {
            AppLanguage.ENGLISH -> "🌾 All Categories"
            AppLanguage.KANNADA -> "🌾 ಎಲ್ಲಾ ಬೆಳೆಗಳು"
            AppLanguage.HINDI -> "🌾 सभी फसलें"
        }
        com.example.data.model.CommodityCategory.COMMERCIAL_PLANTATION -> when (lang) {
            AppLanguage.ENGLISH -> "🌴 Commercial Crops (Areca, Coconut, Cocoa, Banana...)"
            AppLanguage.KANNADA -> "🌴 ವಾಣಿಜ್ಯ ಬೆಳೆಗಳು (ಅಡಿಕೆ, ತೆಂಗು, ಕೋಕೋ, ಬಾಳೆ...)"
            AppLanguage.HINDI -> "🌴 वाणिज्यिक फसलें (सुपारी, नारियल, कोको, केला...)"
        }
        com.example.data.model.CommodityCategory.FRUITS -> when (lang) {
            AppLanguage.ENGLISH -> "🍌 Fruits & Horticulture"
            AppLanguage.KANNADA -> "🍌 ಹಣ್ಣುಗಳು (ಬಾಳೆ, ಅನಾನಸ್...)"
            AppLanguage.HINDI -> "🍌 फल (केला, अनानास...)"
        }
        com.example.data.model.CommodityCategory.SPICES -> when (lang) {
            AppLanguage.ENGLISH -> "🌶️ Spices & Condiments"
            AppLanguage.KANNADA -> "🌶️ ಮಸಾಲೆಗಳು (ಮೆಣಸು, ಏಲಕ್ಕಿ, ಶುಂಠಿ...)"
            AppLanguage.HINDI -> "🌶️ मसाले (मिर्च, इलायची, अदरक...)"
        }
        com.example.data.model.CommodityCategory.CEREALS -> when (lang) {
            AppLanguage.ENGLISH -> "🌾 Cereals & Grains"
            AppLanguage.KANNADA -> "🌾 ಧಾನ್ಯಗಳು (ಗೋಧಿ, ಭತ್ತ)"
            AppLanguage.HINDI -> "🌾 अनाज (गेहूं, धान)"
        }
        com.example.data.model.CommodityCategory.PULSES -> when (lang) {
            AppLanguage.ENGLISH -> "🫘 Pulses (Dal)"
            AppLanguage.KANNADA -> "🫘 ಬೇಳೆಕಾಳುಗಳು (ಕಡಲೆ)"
            AppLanguage.HINDI -> "🫘 दालें (चना)"
        }
        com.example.data.model.CommodityCategory.OILSEEDS -> when (lang) {
            AppLanguage.ENGLISH -> "🌻 Oilseeds"
            AppLanguage.KANNADA -> "🌻 ಎಣ್ಣೆಕಾಳುಗಳು (ಸೋಯಾಬೀನ್)"
            AppLanguage.HINDI -> "🌻 तिलहन (सोयाबीन, सरसों)"
        }
        com.example.data.model.CommodityCategory.VEGETABLES -> when (lang) {
            AppLanguage.ENGLISH -> "🥦 Vegetables"
            AppLanguage.KANNADA -> "🥦 ತರಕಾರಿಗಳು (ಈರುಳ್ಳಿ, ಟೊಮೇಟೊ)"
            AppLanguage.HINDI -> "🥦 सब्जियां (प्याज, टमाटर)"
        }
        com.example.data.model.CommodityCategory.CASH_CROPS -> when (lang) {
            AppLanguage.ENGLISH -> "💰 Cash Crops (Cotton, Sugarcane)"
            AppLanguage.KANNADA -> "💰 ನಗದು ಬೆಳೆಗಳು (ಹತ್ತಿ, ಕಬ್ಬು)"
            AppLanguage.HINDI -> "💰 नकदी फसलें (कपास, गन्ना)"
        }
    }

    // Language switch prompt
    fun selectLanguageTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Select App Language"
        AppLanguage.KANNADA -> "ಅಪ್ಲಿಕೇಶನ್ ಭಾಷೆಯನ್ನು ಆಯ್ಕೆಮಾಡಿ"
        AppLanguage.HINDI -> "ऐप की भाषा चुनें"
    }

    fun languageChangeNotice(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Language changed to English"
        AppLanguage.KANNADA -> "ಭಾಷೆಯನ್ನು ಕನ್ನಡಕ್ಕೆ ಬದಲಾಯಿಸಲಾಗಿದೆ"
        AppLanguage.HINDI -> "भाषा बदलकर हिन्दी कर दी गई है"
    }

    // Variety Sub-list strings
    fun selectVariety(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Select Variety (Sub-list)"
        AppLanguage.KANNADA -> "ತಳಿ ಆಯ್ಕೆಮಾಡಿ (ಉಪ ಪಟ್ಟಿ)"
        AppLanguage.HINDI -> "किस्म चुनें (उप-सूची)"
    }

    fun allVarieties(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "All Varieties"
        AppLanguage.KANNADA -> "ಎಲ್ಲಾ ತಳಿಗಳು"
        AppLanguage.HINDI -> "सभी किस्में"
    }

    fun variety(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Variety"
        AppLanguage.KANNADA -> "ತಳಿ"
        AppLanguage.HINDI -> "किस्म"
    }

    fun selectCrop(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Select Crop"
        AppLanguage.KANNADA -> "ಬೆಳೆ ಆಯ್ಕೆಮಾಡಿ"
        AppLanguage.HINDI -> "फसल चुनें"
    }

    fun allCrops(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "All Crops"
        AppLanguage.KANNADA -> "ಎಲ್ಲಾ ಬೆಳೆಗಳು"
        AppLanguage.HINDI -> "सभी फसलें"
    }

    fun commercialCropsOnly(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "🌴 Commercial Crops Only"
        AppLanguage.KANNADA -> "🌴 ವಾಣಿಜ್ಯ ಬೆಳೆಗಳು ಮಾತ್ರ"
        AppLanguage.HINDI -> "🌴 केवल वाणिज्यिक फसलें"
    }

    // Sort Options
    fun sortDefault(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Default (A-Z)"
        AppLanguage.KANNADA -> "ಡೀಫಾಲ್ಟ್ (ಅ-ಕ್ಷ)"
        AppLanguage.HINDI -> "डिफ़ॉल्ट (अ-ह)"
    }

    fun sortHighestPrice(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Highest Price"
        AppLanguage.KANNADA -> "ಅತ್ಯಧಿಕ ದರ (ಹೆಚ್ಚಿನಿಂದ)"
        AppLanguage.HINDI -> "उच्चतम भाव (घटते क्रम)"
    }

    fun sortLowestPrice(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Lowest Price"
        AppLanguage.KANNADA -> "ಕನಿಷ್ಠ ದರ (ಕಡಿಮೆಯಿಂದ)"
        AppLanguage.HINDI -> "न्यूनतम भाव (बढ़ते क्रम)"
    }

    fun sortTopGainers(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Top % Gainers"
        AppLanguage.KANNADA -> "ಹೆಚ್ಚು ಲಾಭ ಗಳಿಸಿದವು (%)"
        AppLanguage.HINDI -> "शीर्ष बढ़त (%)"
    }

    fun sortHighestArrivals(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Highest Arrivals"
        AppLanguage.KANNADA -> "ಅತ್ಯಧಿಕ ಆವಕ (ಪರಿಮಾಣ)"
        AppLanguage.HINDI -> "सर्वाधिक आवक (मात्रा)"
    }

    // Alerts Screen
    fun priceAlertsTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Price Target Alerts"
        AppLanguage.KANNADA -> "ದರ ಗುರಿ ಎಚ್ಚರಿಕೆಗಳು"
        AppLanguage.HINDI -> "मूल्य लक्ष्य अलर्ट"
    }

    fun priceAlertsSubtitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Instant notifications when mandi prices hit your harvest target"
        AppLanguage.KANNADA -> "ಮಂಡಿ ದರಗಳು ನಿಮ್ಮ ಬೆಳೆ ಗುರಿ ತಲುಪಿದಾಗ ತ್ವರಿತ ಅಧಿಸೂಚನೆಗಳು"
        AppLanguage.HINDI -> "मंडी भाव आपके तय लक्ष्य तक पहुंचने पर तुरंत सूचना"
    }

    fun noAlertsTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "No Price Alerts Set Yet"
        AppLanguage.KANNADA -> "ಇನ್ನೂ ಯಾವುದೇ ದರ ಎಚ್ಚರಿಕೆಗಳನ್ನು ಹೊಂದಿಸಲಾಗಿಲ್ಲ"
        AppLanguage.HINDI -> "अभी कोई मूल्य अलर्ट सेट नहीं है"
    }

    fun noAlertsSubtitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Tap the '+' button to set target price notifications for your crops & varieties."
        AppLanguage.KANNADA -> "ನಿಮ್ಮ ಬೆಳೆಗಳು ಮತ್ತು ತಳಿಗಳಿಗೆ ಗುರಿ ದರ ಎಚ್ಚರಿಕೆ ಹೊಂದಿಸಲು '+' ಬಟನ್ ಒತ್ತಿರಿ."
        AppLanguage.HINDI -> "अपनी फसलों और किस्मों के लिए मूल्य अलर्ट सेट करने के लिए '+' बटन दबाएं।"
    }

    fun createAlert(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Create Price Alert"
        AppLanguage.KANNADA -> "ದರ ಎಚ್ಚರಿಕೆ ರಚಿಸಿ"
        AppLanguage.HINDI -> "मूल್ಯ अलर्ट बनाएं"
    }

    fun targetRisesAbove(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Target: Rises Above (>=)"
        AppLanguage.KANNADA -> "ಗುರಿ: ದರ ಏರಿದಾಗ (>=)"
        AppLanguage.HINDI -> "लक्ष्य: भाव बढ़ने पर (>=)"
    }

    fun targetDropsBelow(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Target: Drops Below (<=)"
        AppLanguage.KANNADA -> "ಗುರಿ: ದರ ಇಳಿದಾಗ (<=)"
        AppLanguage.HINDI -> "लक्ष्य: भाव गिरने पर (<=)"
    }

    fun monitoring247(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Monitoring 24/7"
        AppLanguage.KANNADA -> "24/7 ಪರಿಶೀಲಿಸಲಾಗುತ್ತಿದೆ"
        AppLanguage.HINDI -> "24/7 निगरानी जारी"
    }

    fun paused(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Paused"
        AppLanguage.KANNADA -> "ವಿರಾಮಗೊಳಿಸಲಾಗಿದೆ"
        AppLanguage.HINDI -> "रोका गया"
    }

    fun triggered(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "TRIGGERED"
        AppLanguage.KANNADA -> "ಪ್ರೇರೇಪಿಸಲಾಗಿದೆ"
        AppLanguage.HINDI -> "अलर्ट चालू हुआ"
    }

    fun delete(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Delete"
        AppLanguage.KANNADA -> "ಅಳಿಸಿ"
        AppLanguage.HINDI -> "हटाएं"
    }

    fun cancel(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Cancel"
        AppLanguage.KANNADA -> "ರದ್ದುಮಾಡಿ"
        AppLanguage.HINDI -> "रद्द करें"
    }

    fun save(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Save"
        AppLanguage.KANNADA -> "ಉಳಿಸಿ"
        AppLanguage.HINDI -> "सहेजें"
    }

    fun targetPrice(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Target Price (₹ / Qtl)"
        AppLanguage.KANNADA -> "ಗುರಿ ದರ (₹ / ಕ್ವಿಂಟಾಲ್)"
        AppLanguage.HINDI -> "लक्ष्य मूल्य (₹ / क्विंटल)"
    }

    fun selectMandi(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Select Mandi Market"
        AppLanguage.KANNADA -> "ಮಂಡಿ ಮಾರುಕಟ್ಟೆ ಆಯ್ಕೆಮಾಡಿ"
        AppLanguage.HINDI -> "मंडी बाजार चुनें"
    }

    // Advisor Screen
    fun tabSellInsights(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Sell Timing Insights"
        AppLanguage.KANNADA -> "ಮಾರಾಟ ಸಮಯದ ಒಳನೋಟಗಳು"
        AppLanguage.HINDI -> "बिक्री समय अंतर्दृष्टि"
    }

    fun tabWeatherAnalytics(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Weather Analytics"
        AppLanguage.KANNADA -> "ಹವಾಮಾನ ವಿಶ್ಲೇಷಣೆ"
        AppLanguage.HINDI -> "मौसम विश्लेषण"
    }

    fun tabStorageCalculator(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Storage ROI Calculator"
        AppLanguage.KANNADA -> "ಶೇಖರಣಾ ಲಾಭ ಕ್ಯಾಲ್ಕುಲೇಟರ್"
        AppLanguage.HINDI -> "भंडारण लाभ कैलकुलेटर"
    }

    fun switchMandi(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Switch Mandi"
        AppLanguage.KANNADA -> "ಮಂಡಿ ಬದಲಾಯಿಸಿ"
        AppLanguage.HINDI -> "मंडी बदलें"
    }

    // Reports Screen
    fun tabMandiBulletin(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Mandi Bulletin"
        AppLanguage.KANNADA -> "ಮಂಡಿ ಬುಲೆಟಿನ್"
        AppLanguage.HINDI -> "मंडी बुलेटिन"
    }

    fun tabCsvSpreadsheet(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "CSV Spreadsheet"
        AppLanguage.KANNADA -> "ಸಿಎಸ್‌ವಿ ಸ್ಪ್ರೆಡ್‌ಶೀಟ್"
        AppLanguage.HINDI -> "सीएसवी स्प्रेडशीट"
    }

    fun exportWhatsApp(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Share WhatsApp Bulletin"
        AppLanguage.KANNADA -> "ವಾಟ್ಸಾಪ್ ವರದಿ ಹಂಚಿಕೊಳ್ಳಿ"
        AppLanguage.HINDI -> "व्हाट्सएप बुलेटिन साझा करें"
    }

    fun copyCsv(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Copy CSV Data"
        AppLanguage.KANNADA -> "ಸಿಎಸ್‌ವಿ ಡೇಟಾ ನಕಲಿಸಿ"
        AppLanguage.HINDI -> "सीएसवी डेटा कॉपी करें"
    }

    fun reportOverview(lang: AppLanguage, count: Int): String = when (lang) {
        AppLanguage.ENGLISH -> "Report Overview ($count Markets)"
        AppLanguage.KANNADA -> "ವರದಿ ಅವಲೋಕನ ($count ಮಾರುಕಟ್ಟೆಗಳು)"
        AppLanguage.HINDI -> "रिपोर्ट अवलोकन ($count मंडियां)"
    }

    fun avgModalPrice(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Avg Modal Price"
        AppLanguage.KANNADA -> "ಸರಾಸರಿ ಮಾದರಿ ದರ"
        AppLanguage.HINDI -> "औसत मॉडल भाव"
    }

    fun totalArrivals(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Total Arrivals"
        AppLanguage.KANNADA -> "ಒಟ್ಟು ಆವಕ"
        AppLanguage.HINDI -> "कुल आवक"
    }

    fun highestRate(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Highest Rate"
        AppLanguage.KANNADA -> "ಅತ್ಯಧಿಕ ದರ"
        AppLanguage.HINDI -> "उच्चतम भाव"
    }

    // Profile Settings
    fun preferencesSaved(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Preferences saved successfully!"
        AppLanguage.KANNADA -> "ಆದ್ಯತೆಗಳನ್ನು ಯಶಸ್ವಿಯಾಗಿ ಉಳಿಸಲಾಗಿದೆ!"
        AppLanguage.HINDI -> "प्राथमिकताएं सफलतापूर्वक सहेजी गईं!"
    }

    fun savePreferences(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Save & Update Preferences"
        AppLanguage.KANNADA -> "ಆದ್ಯತೆಗಳನ್ನು ಉಳಿಸಿ & ನವೀಕರಿಸಿ"
        AppLanguage.HINDI -> "प्राथमिकताएं सहेजें व अपडेट करें"
    }

    fun yourName(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Your Name"
        AppLanguage.KANNADA -> "ನಿಮ್ಮ ಹೆಸರು"
        AppLanguage.HINDI -> "आपका नाम"
    }

    fun roleLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Role / Profession"
        AppLanguage.KANNADA -> "ಪಾತ್ರ / ವೃತ್ತಿ"
        AppLanguage.HINDI -> "भूमिका / पेशा"
    }

    fun stateLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "State"
        AppLanguage.KANNADA -> "ರಾಜ್ಯ"
        AppLanguage.HINDI -> "राज्य"
    }

    fun districtLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "District"
        AppLanguage.KANNADA -> "ಜಿಲ್ಲೆ"
        AppLanguage.HINDI -> "जिला"
    }

    fun farmSizeLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Total Landholding (Acres)"
        AppLanguage.KANNADA -> "ಒಟ್ಟು ಭೂಮಿ (ಎಕರೆ)"
        AppLanguage.HINDI -> "कुल जोत (एकड़)"
    }

    fun selectPrimaryCrops(lang: AppLanguage): String = when (lang) {
        AppLanguage.ENGLISH -> "Select Primary Crops for Custom Dashboard:"
        AppLanguage.KANNADA -> "ಕಸ್ಟಮ್ ಡ್ಯಾಶ್‌ಬೋರ್ಡ್‌ಗಾಗಿ ಪ್ರಮುಖ ಬೆಳೆಗಳನ್ನು ಆಯ್ಕೆಮಾಡಿ:"
        AppLanguage.HINDI -> "कस्टम डैशबोर्ड के लिए प्राथमिक फसलें चुनें:"
    }
}
