package com.nordstrom.interview1;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nik on 1/12/17.
 */
public class Solution {
//    $products = array("Shirt", "Watch");
//
//    $strData = ‘0|0|Small
//0|1|Medium
//0|2|Large
//1|0|Black Leather
//1|1|Brown Leather
//1|2|Stainless’;
//
//    Print out as follow:
//    Product 0: Shirt
//- Small
//- Medium
//- Large
//
//    Product 1: Watch
//- Black Leather
//- Brown Leather
//- Stainless





    public static void main(String [] args) {

        String [] products = {"Shirt", "Watch"};
        String strData = "0|0|Small\n0|1|Medium\n0|2|Large\n1|0|Black Leather\n1|1|Brown Leather\n1|2|Stainless";

        printProduct(products, strData);
    }

    private static void printProduct(String [] products, String strData) {

        Map<String, Map<String, String>> productOptions = parseData(strData);

        for(int i = 0; i < products.length; i++) {
            System.out.println("Product " + i + ": " + products[i]);
            Map<String, String> map = productOptions.get("" + i);
            for(String key : map.keySet()) {
                System.out.println("- " + map.get(key));
            }
        }

        System.out.println();
    }

    private static Map<String, Map<String, String>> parseData(String strData) {

        Map<String, Map<String, String>> map = new HashMap<>();
        for(String productValues : strData.split("\\n")) {
            String [] tokens = productValues.split("\\|");
            Map<String, String> productMap = map.get(tokens[0]);
            if(productMap == null) {
                productMap = new HashMap<>();
                productMap.put(tokens[1], tokens[2]);
                map.put(tokens[0], productMap);
            }
            else {
                productMap.put(tokens[1], tokens[2]);
            }
        }

        return map;

    }
}
