package com.example.skycapitalcarrentalapplication.utils;

import android.text.TextUtils;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Class of generic converters
 */
public class Converters {

    /**
     * This is for converting a List<String> to a single string
     * Because Room can't persist List<String> directly
     */
    @TypeConverter
    public static String fromList(List<String> list) {
        return list == null ? "" : TextUtils.join("|", list);
    }

    /**
     * This is for converting a single string to a List<String>
     * Because Room can't persist List<String> directly
     */
    @TypeConverter
    public static List<String> toList(String data) {
        if (data == null || data.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(data.split("\\|")));
    }
}
