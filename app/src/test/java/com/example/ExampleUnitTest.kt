package com.example

import com.example.data.localization.AppLanguage
import com.example.data.localization.LocalizationStrings
import com.example.data.model.CommodityCategory
import com.example.data.model.isCommercialCrop
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun testCommercialCropsIdentification() {
    assertTrue(isCommercialCrop("Areca Nut"))
    assertTrue(isCommercialCrop("Coconut"))
    assertTrue(isCommercialCrop("Cocoa"))
    assertTrue(isCommercialCrop("Banana"))
    assertTrue(isCommercialCrop("Pineapple"))
    assertTrue(isCommercialCrop("Coffee"))
    assertTrue(isCommercialCrop("Rubber"))
    assertTrue(isCommercialCrop("Sugarcane & Jaggery"))
  }

  @Test
  fun testMultilingualTranslations() {
    assertEquals("KrishiMandi", LocalizationStrings.getAppName(AppLanguage.ENGLISH))
    assertEquals("ಕೃಷಿ ಮಂಡಿ", LocalizationStrings.getAppName(AppLanguage.KANNADA))
    assertEquals("कृषि मंडी", LocalizationStrings.getAppName(AppLanguage.HINDI))

    assertEquals("Areca Nut (Adike)", LocalizationStrings.localizedCropName(AppLanguage.ENGLISH, "Areca Nut"))
    assertEquals("ಅಡಿಕೆ (Areca Nut)", LocalizationStrings.localizedCropName(AppLanguage.KANNADA, "Areca Nut"))
    assertEquals("सुपारी (Areca Nut)", LocalizationStrings.localizedCropName(AppLanguage.HINDI, "Areca Nut"))

    assertEquals("Coconut & Copra", LocalizationStrings.localizedCropName(AppLanguage.ENGLISH, "Coconut"))
    assertEquals("ತೆಂಗಿನಕಾಯಿ / ಕೊಬ್ಬರಿ (Coconut)", LocalizationStrings.localizedCropName(AppLanguage.KANNADA, "Coconut"))
    assertEquals("नारियल / गोला (Coconut)", LocalizationStrings.localizedCropName(AppLanguage.HINDI, "Coconut"))
    assertEquals("Cocoa (Cocova)", LocalizationStrings.localizedCropName(AppLanguage.ENGLISH, "Cocoa"))
    assertEquals("ಕೋಕೋ (Cocoa)", LocalizationStrings.localizedCropName(AppLanguage.KANNADA, "Cocoa"))
    assertEquals("Banana (Bale)", LocalizationStrings.localizedCropName(AppLanguage.ENGLISH, "Banana"))
    assertEquals("Pineapple (Ananas)", LocalizationStrings.localizedCropName(AppLanguage.ENGLISH, "Pineapple"))
  }

  @Test
  fun testCropVarietyCatalogSubLists() {
    val arecaVarieties = com.example.data.model.CropVarietyCatalog.getVarietiesForCrop("Areca Nut")
    assertTrue(arecaVarieties.isNotEmpty())
    assertTrue(arecaVarieties.any { it.nameEn.contains("Rashi") })
    assertTrue(arecaVarieties.any { it.nameEn.contains("Bette") })

    val coconutVarieties = com.example.data.model.CropVarietyCatalog.getVarietiesForCrop("Coconut")
    assertTrue(coconutVarieties.isNotEmpty())
    assertTrue(coconutVarieties.any { it.nameEn.contains("Tiptur") })
    assertTrue(coconutVarieties.any { it.nameEn.contains("Ball") })

    val chilliVarieties = com.example.data.model.CropVarietyCatalog.getVarietiesForCrop("Red Chilli")
    assertTrue(chilliVarieties.isNotEmpty())
    assertTrue(chilliVarieties.any { it.nameEn.contains("Byadagi") })

    // Test localized names for Kannada and Hindi
    val rashi = arecaVarieties.first { it.nameEn.contains("Rashi") }
    assertTrue(rashi.localizedName(AppLanguage.KANNADA).contains("ರಾಶಿ"))
    assertTrue(rashi.localizedName(AppLanguage.HINDI).contains("राशी"))

    // Test variety options
    assertEquals("Select Variety (Sub-list)", LocalizationStrings.selectVariety(AppLanguage.ENGLISH))
    assertEquals("ತಳಿ ಆಯ್ಕೆಮಾಡಿ (ಉಪ ಪಟ್ಟಿ)", LocalizationStrings.selectVariety(AppLanguage.KANNADA))
    assertEquals("किस्म चुनें (उप-सूची)", LocalizationStrings.selectVariety(AppLanguage.HINDI))

    assertEquals("All Varieties", LocalizationStrings.allVarieties(AppLanguage.ENGLISH))
    assertEquals("ಎಲ್ಲಾ ತಳಿಗಳು", LocalizationStrings.allVarieties(AppLanguage.KANNADA))
    assertEquals("सभी किस्में", LocalizationStrings.allVarieties(AppLanguage.HINDI))
  }
}

