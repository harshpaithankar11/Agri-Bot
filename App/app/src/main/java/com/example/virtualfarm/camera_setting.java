package com.example.virtualfarm;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class camera_setting {


    private Integer bri;
    private Integer con;

    private ImageView brit,cont;

    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility", "RtlHardcoded"})
    public void showPopupWindow(final View view) {



        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.camera_setting, null);

        boolean focusable = true;
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        popupWindow.showAtLocation(view, Gravity.CENTER, 100, 0);


        popupView.setOnTouchListener((v, event) -> {
            popupWindow.dismiss();
            return true;
        });

}
}
