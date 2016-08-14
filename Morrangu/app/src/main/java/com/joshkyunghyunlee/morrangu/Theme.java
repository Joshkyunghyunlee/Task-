package com.joshkyunghyunlee.morrangu;

import android.graphics.Color;

/**
 * Created by joshlee on 2016-06-30.
 */
public class Theme {

    public static int[] imagesArray = {R.drawable.background_safari, R.drawable.background_planet,
            R.drawable.background_sea, R.drawable.background_groceries, R.drawable.background_retrocity, R.drawable.background_streetview,
            R.drawable.background_family, R.drawable.background_birthday, R.drawable.background_temple
    };


    //TODO: FIND A NEW BIRTHDAY PICTURE
    public static int getImageID(int imageIndex) {
        return imagesArray[imageIndex];
    }

    public static int getThemeColor(int imageIndex) {
        switch (imageIndex) {
            case 0: //safari
                return Color.parseColor("#D94478");
            case 1: //planet
                return Color.parseColor("#415EAA");
            default: //sea
                return Color.parseColor("#6041AE");
        }
    }

    public static int getContextualImage(String categoryTitle) {
        String mTitle = categoryTitle.toLowerCase().replaceAll("\\s","");

        if (mTitle.contains("africa") || mTitle.contains("desert") || mTitle.contains("safari")) {
            return 0; //safari
        }
        else if (mTitle.contains("boat") || mTitle.contains("ocean") || mTitle.contains("sea") || mTitle.contains("tropical")) {
            return 2; //sea
        }
        else if (mTitle.contains("food") || mTitle.contains("groceries") || mTitle.contains("shop") || mTitle.contains("cook")) {
            return 3; //groceries
        }
        else if (mTitle.contains("city") || mTitle.contains("york") || mTitle.contains("trip") || mTitle.contains("vacation")) {
            return 4; //retrocity
        }
        else if (mTitle.contains("apartment") || mTitle.contains("furniture") || mTitle.contains("house")) {
            return 5; //streetview
        }
        else if (mTitle.contains("family") || mTitle.contains("camping") || mTitle.contains("cabin") || mTitle.contains("friend")) {
            return 6; //family
        }
        else if (mTitle.contains("birthday") || mTitle.contains("present") || mTitle.contains("gift")) {
            return 7; //gift
        }
        else {
            return 8; //temple
        }
    }
}




