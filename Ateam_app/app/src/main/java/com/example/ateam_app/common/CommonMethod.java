
package com.example.ateam_app.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.ateam_app.MainActivity;

import java.io.File;
import java.io.IOException;

public class CommonMethod {
    public static String  ipConfig = "http://192.168.0.24:80";  //선생님 컴퓨터로 보낼때
    //public static String  ipConfig = "http://112.164.58.217:8999";  //시연용 ip

    // 네트워크에 연결되어 있는가
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info != null){
            if(info.getType() == ConnectivityManager.TYPE_WIFI){
                Log.d("isconnected : ", "WIFI 로 설정됨");
            }else if(info.getType() == ConnectivityManager.TYPE_MOBILE){
                Log.d("isconnected : ", "일반망으로 설정됨");
            }
            Log.d("isconnected : ", "OK => " + info.isConnected());
            return true;
        }else {
            Toast.makeText(context, "네트워크 연결이 원활하지 않습니다.", Toast.LENGTH_SHORT).show();
            Log.d("isconnected : ", "False => 데이터 통신 불가!!!" );
            return false;
        }
    }

    // 이미지 로테이트 및 사이즈 변경
    public static Bitmap imageRotateAndResize(String path){ // state 1:insert, 2:update
        BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inSampleSize = 8;

        File file = new File(path);
        if (file != null) {
            // 돌아간 앵글각도 알기
            int rotateAngle = setImageOrientation(file.getAbsolutePath());
            Bitmap bitmapTmp = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

            // 사진 바로 보이게 이미지 돌리기
            Bitmap bitmap = imgRotate(bitmapTmp, rotateAngle);

            return bitmap;
        }
        return null;
    }


    // 사진 찍을때 돌린 각도 알아보기 : 가로로 찍는게 기본임
    public static int setImageOrientation(String path){
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int oriention = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        return oriention;
    }

    // 이미지 돌리기
    public static Bitmap imgRotate(Bitmap bitmap, int orientation){

        Matrix matrix = new Matrix();

        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }

    }

    //긍정버튼 부정버튼 중립버튼 다이얼로그
    public void dialogMethod(Context context, String title, String message,
                             String positiveButton, DialogInterface.OnClickListener pdioc,
                             String negativeButton, DialogInterface.OnClickListener ndioc,
                             String neutralButton, DialogInterface.OnClickListener ntdioc){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton(positiveButton, pdioc);

        builder.setNegativeButton(negativeButton, ndioc);

        builder.setNeutralButton(neutralButton, ntdioc);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //긍정버튼 부정버튼 다이얼로그
    public void dialogMethod(Context context, String title, String message,
                             String positiveButton, DialogInterface.OnClickListener pdioc,
                             String negativeButton, DialogInterface.OnClickListener ndioc){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton(positiveButton, pdioc);

        builder.setNegativeButton(negativeButton, ndioc);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //알람만 있는 다이얼로그
    public void dialogMethod(Context context, String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //프래그먼트 갱신하는 메소드 굿굿
    public void replace(FragmentTransaction ft,Fragment fragment){
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(fragment).attach(fragment).commit();
    }

    //백버튼시 메인프래그먼트로 이동하는 메소드
    public void fragmentBackPress(MainActivity activity, FragmentActivity reqActivity, Fragment fragment, int bottomNaviID){
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                activity.bottomNavigationView.setSelectedItemId(bottomNaviID);
            }
        };
        reqActivity.getOnBackPressedDispatcher().addCallback(fragment, onBackPressedCallback);
    }

    public void mainFragmentBackPress(MainActivity activity, FragmentActivity reqActivity, Fragment fragment, Context context){
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            long backKeyPressedTime = 0;
            @Override
            public void handleOnBackPressed() {
                //뒤로 버튼을 한 번 누렀을 때 종료하시겠습니까? 알림
                //2초 안에 두 번을 눌렀을 때 앱 종료
                if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                    backKeyPressedTime = System.currentTimeMillis();
                    Toast.makeText(context, "뒤로 버튼을 한 번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
                } else {
                    ActivityCompat.finishAffinity(activity);
                    System.exit(0);
                }//if
            }
        };
        reqActivity.getOnBackPressedDispatcher().addCallback(fragment, onBackPressedCallback);
    }
}
