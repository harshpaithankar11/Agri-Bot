package com.example.virtualfarm;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;

public class Plant_Popup {
    ImageView plantImg;

    TextView posTitle,phumi,psoil,ptemp;
    LocalDate currentDate;


    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    public void showPopupWindow(final View view, String pos,String Date1) {



        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.plant_popup, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;


        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        plantImg=popupView.findViewById(R.id.plant_img);
        phumi=popupView.findViewById(R.id.phumi);

        posTitle=popupView.findViewById(R.id.posTitle);

        ptemp=popupView.findViewById(R.id.ptemp);
        psoil=popupView.findViewById(R.id.psoil);

        posTitle.setText("G"+pos);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDate = LocalDate.now();
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(Date1+"/G"+pos+"/img");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String value = dataSnapshot.getValue(String.class);
                    plantImg.setImageBitmap(convertBase64ToBitmap(value));}
                catch (Exception ignored){
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w( "Failed to read value.", error.toException());
            }
        });
        DatabaseReference myRef2 = database.getReference(Date1+"/G"+pos+"/Temp");

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try{String value = dataSnapshot.getValue(String.class);
                    ptemp.setText(value);}
                catch (Exception e){}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w( "Failed to read value.", error.toException());
            }
        });
        DatabaseReference myRef4 = database.getReference(Date1+"/G"+pos+"/Humi");

        myRef4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{String value = dataSnapshot.getValue(String.class);
                    phumi.setText(value);}
                catch (Exception e){}
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w( "Failed to read value.", error.toException());
            }
        });

        DatabaseReference soil = database.getReference(Date1+"/G"+pos+"/Soil_cond");
        soil.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try{String value = dataSnapshot.getValue(String.class);
                    psoil.setText(value);}
                catch (Exception e){}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w( "Failed to read value.", error.toException());
            }
        });



        popupView.setOnTouchListener((v, event) -> {
            popupWindow.dismiss();
            return true;
        });
    }
    public Bitmap convertBase64ToBitmap(String base64String) {
        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);}

}
