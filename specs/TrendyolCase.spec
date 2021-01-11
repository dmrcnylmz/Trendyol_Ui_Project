Trendyol Case
=====================
Created by testinium on 10.01.2021

This is an executable specification file which follows markdown syntax.
Every heading in this file denotes a scenario. Every bulleted point denotes a step.
     
Go To Trendyol Check All Tab İmage (JS) And Add Basket Product
--------------------------------------------------
* Go to "https://www.trendyol.com/"
* Login
* Go to all category and check all tab image with JS
* Logger -> "Control all category image with js"
* Choose random boutique
* Logger -> "Choosen random boutique"
* Control product image with JS
* Logger -> "Control product image with js"
* Javascript ile tıkla "Any_Product_Boutique"
* Logger -> "Click any product to boutique"
* Wait for "Add_Basket_Button" and click
* Logger -> "Add to basket any product"

Go To Trendyol Check All Tab İmage (Http) And Add Basket Product
--------------------------------------------------
* Go to "https://www.trendyol.com/"
* Login
* Go to all category and check all tab image with HttpConnection
* Logger -> "Control all category image with httpConnection"
* Choose random boutique
* Logger -> "Choosen random boutique"
* Find broken images with http "Product_Images"
* Logger -> "Control product image with http"
* Javascript ile tıkla "Any_Product_Boutique"
* Logger -> "Click any product to boutique"
* Wait for "Add_Basket_Button" and click
* Logger -> "Add to basket any product"