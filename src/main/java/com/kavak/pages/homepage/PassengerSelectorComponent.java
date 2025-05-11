package com.kavak.pages.homepage;

import com.kavak.base.BasePage;
import com.kavak.base.Driver;
import com.kavak.utilities.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PassengerSelectorComponent extends BasePage {

    public void setPassengerCount(String passengerType, int targetCount) {
        waitMs(2000);
        var driver = Driver.getDriver();

        //Yolcu tipi container'ını bulucaz nested mantığı
        List<WebElement> containers = driver.findElements(By.cssSelector("div.FkqV"));
        WebElement targetContainer = containers.stream()
                .filter(container -> {
                    try {
                        WebElement labelEl = container.findElement(By.cssSelector(".FkqV-title"));
                        String labelText = labelEl.getText().replaceAll("\\s+", "");
                        return labelText.contains(passengerType.replaceAll("\\s+", ""));
                    } catch (NoSuchElementException e) {
                        return false;
                    }
                })
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Yolcu tipi bulunamadı: " + passengerType));


        // 2.olarak Mevcut değerleri alıp işleyeceğiz
        WebElement input = targetContainer.findElement(By.cssSelector("input.T_3c-input"));
        int currentValue = Integer.parseInt(input.getAttribute("aria-valuenow"));
        int max = Integer.parseInt(input.getAttribute("aria-valuemax"));
        int min = Integer.parseInt(input.getAttribute("aria-valuemin"));

        // 3. Hedef sayı uyar mı?
        if (targetCount < min || targetCount > max) {
            throw new IllegalArgumentException(passengerType + " için geçersiz sayı: " + targetCount + " (min: " + min + ", max: " + max + ")");
        }

        // 4. Zaten equals ise return
        if (currentValue == targetCount) {
            Log.pass(passengerType + " sayısı zaten " + targetCount);
            return;
        }

        // 5. olarak  Artır veya azalt butonlarını bul
        WebElement increaseBtn = targetContainer.findElement(By.cssSelector("button[aria-label='Artan']"));
        WebElement decreaseBtn = targetContainer.findElement(By.cssSelector("button[aria-label='Daha az']"));

        // 6. Sayıyı req'e göre eşitle
        int step = (targetCount > currentValue) ? 1 : -1;
        WebElement buttonToClick = (step > 0) ? increaseBtn : decreaseBtn;

        while (currentValue != targetCount) {
            waitUntilClickable(buttonToClick, 3).click();
            currentValue += step;
            Log.pass(passengerType + " yolcu sayısı güncellendi: " + currentValue);
        }
    }


}
