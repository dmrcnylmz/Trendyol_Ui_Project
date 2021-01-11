package step;

import Base.BaseMethods;
import com.thoughtworks.gauge.Step;
import helper.ElementHelper;
import helper.StoreHelper;
import model.ElementInfo;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Random;

public class StepImplementation extends BaseMethods {



    @Step("<second> second wait")
    public void waitBySeconds(int seconds){
        waitByMilliSeconds(seconds * 1000);
    }


    @Step("Go to <url>")
    public void goToUrl(String url){

        driver.get(url);
        logger.info(url + " going to.");

    }

    @Step("Wait for <key> and click")
    public void checkElementVisibiltyAndClick(String key){
        isElementVisible(key,5);
        isElementClickable(key,10);
        clickElement(key);
    }

    @Step("Hover to <key>")
    public void hoverStep(String key){
          isElementVisible(key,5);
          hoverElement(key);
    }

    @Step("Is <key> element Visible ? <timeout>")
    public boolean isElementVisible(String key, int timeout){
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        try{
            WebDriverWait wait = new WebDriverWait(driver,timeout);
           waitVisibilityOfElementLocatedBy(ElementHelper.getElementInfoToBy(elementInfo));
            return true;
        }catch (Exception e){
            logger.info(key +" not visible");
            return false;
        }
    }
    @Step("Is <key> element Clickable ? <timeout>")
    public boolean isElementClickable(String key, int timeout){
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        try{
            WebDriverWait wait = new WebDriverWait(driver,timeout);
            waitClickableOfElementLocatedBy(ElementHelper.getElementInfoToBy(elementInfo));
            return true;
        }catch (Exception e){
            logger.info(key +" not visible");
            return false;
        }
    }

    @Step("Write <text> to the <key> and clear area")
    public void sendKeys(String text, String key){

        clearAndSendKey(text,key);
        logger.info(text+" written to "+key);
    }

    @Step("Go to <index> tab")
    public void goToCategoryTab(String tabKey){

        WebElement webElement = driver.findElement(By.xpath("(//ul[@class='main-nav']/li/a)["+tabKey+"]"));
        webElement.click();

    }
    @Step("Is tab text like this <tabText>")
    public void controlCategoryTab(String text ){
        isElementVisible("Active_Category_Tab_Control",5);
        System.out.println("Tab text : "+getElementText("Active_Category_Tab_Control"));
        Assert.assertEquals(text,getElementText("Active_Category_Tab_Control"));


    }
    @Step("Check page title text <tabText>")
    public final void assertPage(String expectedPageTitle){

        String titleText = driver.getTitle();
        System.out.println("Title "+titleText);
        if (expectedPageTitle.contains(driver.getTitle())) {
            System.out.println("Title "+titleText);

            throw new IllegalStateException(String.format("Expected '%s' page title. But found '%s'",expectedPageTitle ,driver.getTitle()));

        }

    }

    @Step("Choose random boutique")
    public void chooseRandomBoutique(){

        Random random = new Random();

        List<WebElement> imgSrcList = findElementsByKey("Category_Images");
        int randomBoutique = random.nextInt(imgSrcList.size());

      By by =  By.xpath("(//article[@class='component-item']/a)["+randomBoutique+"]");
      System.out.println("Random boutique by : "+by);
        logger.info("Random boutique by : "+by);

        System.out.println("Random boutique number : "+randomBoutique);
        if (randomBoutique==0)
        {
            chooseRandomBoutique();
        }

       driver.findElement(by).click();

    }

    @Step("Control boutique image with JS")
    public void controlBtqImg (){

    By boutiqueWebElement =By.xpath("//article[@class='component-item']/a/span/img");

    for(WebElement i: driver.findElements(boutiqueWebElement)) {

        controlImg(i);

    }}

    @Step("Control product image with JS")
    public void controlProductImage (){

        By productsWebElement =By.xpath("//img[contains(@class,'p-card-img')]");

        for(WebElement i: driver.findElements(productsWebElement)) {
            controlImg(i);
         }


    }
    @Step("Logger -> <text>")
    public void loggerInfo (String text){

        logger.info(text);

    }
    public void controlImg(WebElement imageWebElement){

        Object result = ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].complete && "+
                        "typeof arguments[0].naturalWidth != \"undefined\" && "+
                        "arguments[0].naturalWidth > 0", imageWebElement);

        if (result instanceof Boolean) {

            Boolean loaded = (Boolean) result;

            if(!loaded) {

                logger.info(String.format("\nImage of '%s' element at '%s' has not been loaded.\\n",imageWebElement, imageWebElement.getLocation()));

            }else {
                //log.info(String.format("\nImage at '%s' has been loaded successfully.\n",image, image.getLocation()));
            }

        }else {

            logger.info(String.format("\nImage of '%s' element at '%s' has not been loaded.\n",imageWebElement, imageWebElement.getLocation()));

        }

    }


    @Step("Find broken images with http <key>")
    public WebDriver findBrokenImagesWithHttp(String key) throws IOException {
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        List<WebElement> images = driver.findElements(ElementHelper.getElementInfoToBy(elementInfo));

        for (WebElement image : images) {
            String imageSrc = image.getAttribute("src");
            URL url = new URL(imageSrc);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() != 200)
                logger.warn(imageSrc + " >> " + httpURLConnection.getResponseCode() + " >> "
                        + httpURLConnection.getResponseMessage());

            httpURLConnection.disconnect();
        }
        return driver;
    }
    @Step("Javascript ile tıkla <key>")
    public void javaScriptClicker(String key){
        isElementVisible(key,5);
        isElementClickable(key,5);
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        WebElement element = driver.findElement(ElementHelper.getElementInfoToBy(elementInfo));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
    }
}
