package com.example.tripmanage;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;

public class PopupHelper {

    public static void showSimplePopup(Context context, String title, String message) {
        Context themedContext = new ContextThemeWrapper(context, R.style.AppTheme);

//        AlertDialog.Builder builder = new AlerdtDialog.Builder(themedContext);        builder.setTitle(title)
//               .setMessage(message)
//               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                   public void onClick(DialogInterface dialog, int id) {
//                       // Do something when OK button is clicked
//                   }
//               });
//        AlertDialog dialog = builder.create();
        Dialog dialog=new Dialog(themedContext);
        dialog.setContentView(R.layout.add_member);
        dialog.show();
    }
}