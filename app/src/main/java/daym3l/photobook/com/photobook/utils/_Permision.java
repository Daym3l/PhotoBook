package daym3l.photobook.com.photobook.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daymel on 16/9/2018.
 */

public class _Permision {
    public static final int REQUEST_MULTIPLE_PERMISSIONS_ID = 456;

    public static boolean checkPermiso(Activity activity) {


        int permisoCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.INTERNET);
        int permisoCheck2 = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permisoCheck3 = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permisoCheck4 = ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE);
//        int permisoCheck5 = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS);
//        int permisoCheck6 = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CALL_LOG);

        List<String> lista_permisos = new ArrayList<>();


        if (permisoCheck != PackageManager.PERMISSION_GRANTED) {
            lista_permisos.add(Manifest.permission.INTERNET);
        }
//        if (permisoCheck5 != PackageManager.PERMISSION_GRANTED) {
//            lista_permisos.add(Manifest.permission.READ_CONTACTS);
//        }

        if (permisoCheck3 != PackageManager.PERMISSION_GRANTED) {
            lista_permisos.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permisoCheck2 != PackageManager.PERMISSION_GRANTED) {
            lista_permisos.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permisoCheck4 != PackageManager.PERMISSION_GRANTED) {
            lista_permisos.add(Manifest.permission.CALL_PHONE);
        }
//        if (permisoCheck6 != PackageManager.PERMISSION_GRANTED) {
//            lista_permisos.add(Manifest.permission.READ_CALL_LOG);
//        }
        if (!lista_permisos.isEmpty()) {
            ActivityCompat.requestPermissions(activity, lista_permisos.toArray(new String[lista_permisos.size()]), REQUEST_MULTIPLE_PERMISSIONS_ID);
            return false;
        }
        return true;

    }
}
