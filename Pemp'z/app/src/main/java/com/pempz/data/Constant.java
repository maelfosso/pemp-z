package com.pempz.data;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.pempz.model.Contact;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Mael FOSSO on 5/10/2016.
 */
public class Constant {


    public static Resources getStrRes(Context context){
        return context.getResources();
    }

    public static String formatTime(long time){
        // income time
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(time);

        // current time
        Calendar curDate = Calendar.getInstance();
        curDate.setTimeInMillis(System.currentTimeMillis());

        SimpleDateFormat dateFormat = null;
        if(date.get(Calendar.YEAR)==curDate.get(Calendar.YEAR)){
            if(date.get(Calendar.DAY_OF_YEAR) == curDate.get(Calendar.DAY_OF_YEAR) ){
                dateFormat = new SimpleDateFormat("h:mm a", Locale.US);
            }
            else{
                dateFormat = new SimpleDateFormat("MMM d", Locale.US);
            }
        }
        else{
            dateFormat = new SimpleDateFormat("MMM yyyy", Locale.US);
        }
        return dateFormat.format(time);
    }


    public static float getAPIVerison() {

        Float f = null;
        try {
            StringBuilder strBuild = new StringBuilder();
            strBuild.append(android.os.Build.VERSION.RELEASE.substring(0, 2));
            f = new Float(strBuild.toString());
        } catch (NumberFormatException e) {
            Log.e("", "erro ao recuperar a versão da API" + e.getMessage());
        }

        return f.floatValue();
    }

    public static String loadJSONFromAsset(Context ctx, String fjson) {
        String json = null;

        try {
            InputStream is = ctx.getAssets().open(fjson);
            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

    public static List<Contact> getContactsData(Context ctx) {
         String ISO_FORMAT = "dd-MM-yyyy'T'HH:mm";

        Type contactCollectionType = new TypeToken<List<Contact>>(){}.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(ISO_FORMAT);
        Gson gson = gsonBuilder.create();
        List<Contact> items = gson.fromJson(loadJSONFromAsset(ctx, "contacts.json"), contactCollectionType);

        return items;
    }
}
