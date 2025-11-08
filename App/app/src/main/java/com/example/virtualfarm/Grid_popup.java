package com.example.virtualfarm;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
public class Grid_popup {
    TableLayout GridTable;
    private  String pos;
    private  boolean send;

    private PopupWindow popupWindow;

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }

    public boolean isSend() {
        return send;
    }

    public void setSend(boolean send) {
        this.send = send;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String showPopupWindow(final View view) {


        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.grid_popup, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;


        popupWindow = new PopupWindow(popupView, width, height, focusable);
        int paddingPx = (int) (100);
        int paddingPxH = (int) (210);
        int clrt = Color.BLACK;
        GradientDrawable clr = new GradientDrawable();
        clr.setColor(Color.LTGRAY); // Background color
        clr.setStroke(2, Color.BLACK);


        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        GridTable = popupView.findViewById(R.id.tableGrid);


        for (int i = 1; i < 4; i++) {
            TableRow tableRow = new TableRow(view.getContext());
            GridTable.addView(tableRow);
            for (int j = 1; j < 4; j++) {
                TextView text = new TextView(view.getContext());
                text.setText("G" + i + j);
                text.setPadding(paddingPxH, paddingPx, paddingPxH, paddingPx);
                text.setBackground(clr);
                text.setTextColor(clrt);
                tableRow.addView(text);
                ;
                int I = i;
                int J = j;

                text.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        pos = String.valueOf(I) + String.valueOf(J);
                        Toast.makeText(view.getContext(), pos, Toast.LENGTH_SHORT).show();
                        popupWindow.dismiss();
                        return false;
                    }
                });
            }

        }


        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return true;
            }
        });
        return pos;
    }
}
