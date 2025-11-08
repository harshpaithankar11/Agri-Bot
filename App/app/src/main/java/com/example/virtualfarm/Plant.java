package com.example.virtualfarm;
import android.graphics.Bitmap;
import android.util.Base64;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
public class Plant {
    private String id;
    private String Date;
    private  String Temp;
    private  String Humi;
    private String Soil_cond;
    LocalDate currentDate;
    private String base64IMG;

    public String getBase64IMG() {
        return base64IMG;
    }

    public void setBase64IMG(String base64IMG) {
        this.base64IMG = base64IMG;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTemp() {
        return Temp;
    }

    public void setTemp(String temp) {
        Temp = temp;
    }

    public String getHumi() {
        return Humi;
    }

    public void setHumi(String humi) {
        Humi = humi;
    }

    public String getSoil_cond() {
        return Soil_cond;
    }

    public void setSoil_cond(String soil_cond) {
        Soil_cond = soil_cond;
    }

    public Bitmap getPlant_img() {
        return Plant_img;
    }

    public void setPlant_img(Bitmap plant_img) {
        Plant_img = plant_img;
    }

    private Bitmap Plant_img;





    String soil_Cond(String  Soil_moisture){
        if( Integer.parseInt(Soil_moisture)>2500){
            return "Dry";
        } else  {
            return "Wet";
        }
    }

    void Write_Database(String pos){

        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = sdf.format(currentDate);


        FirebaseDatabase database = FirebaseDatabase.getInstance();



        DatabaseReference myRef = database.getReference(formattedDate+"/G"+pos+"/img");
        base64IMG=convertBitmapToBase64(Plant_img);
        myRef.setValue(base64IMG);
        DatabaseReference myRef1 = database.getReference(formattedDate+"/G"+pos+"/Temp");
        myRef1.setValue("Temp");
        DatabaseReference myRef2 = database.getReference(formattedDate+"/G"+pos+"/Humi");
        myRef2.setValue("Humi");
        DatabaseReference myRef3 = database.getReference(formattedDate+"/G"+pos+"/Soil_cond");
        myRef3.setValue("Soil_cond");
    }
    public String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
