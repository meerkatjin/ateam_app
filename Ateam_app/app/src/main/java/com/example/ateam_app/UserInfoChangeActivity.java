package com.example.ateam_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.ateam_app.common.CommonMethod;
import com.example.ateam_app.user_pakage.atask.UserInfoUpdate;
import com.example.ateam_app.user_pakage.dto.UserDTO;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static com.example.ateam_app.common.CommonMethod.ipConfig;
import static com.example.ateam_app.common.CommonMethod.isNetworkConnected;

public class UserInfoChangeActivity extends AppCompatActivity {
    public static UserDTO loginDTO = null;

    EditText user_email, user_pw, user_nm, user_addr, user_phone_no;
    Button btnInfoChange, btnInfoChangeCancel, btnImgUpload, btnCameraUpload;
    ImageView user_pro_img;

    long id;
    String email, pw, name, addr, img, phone, grade, type;
    Intent mainIntent;

    public String imageRealPathU = "", imageDbPathU = "";

    final int CAMERA_REQUEST = 1010;
    final int LOAD_IMAGE = 1011;

    File file = null;
    long fileSize = 0;

    //관리자 모드에서 가져올것
    RadioGroup user_grade;
    RadioButton user_grade_user, user_grade_admin;
    EditText user_id, user_type;
    View adminView;
    TextView adminTitle;
    LinearLayout adminLayout1, adminLayout2, adminLayout3;
    int gradeCheck = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_change);

        btnInfoChange = findViewById(R.id.btnInfoChange);
        btnInfoChangeCancel = findViewById(R.id.btnInfoChangeCancel);
        btnImgUpload = findViewById(R.id.btnImgUpload);
        btnCameraUpload = findViewById(R.id.btnCameraUpload);

        user_email = findViewById(R.id.user_email);
        user_pw = findViewById(R.id.user_pw);
        user_nm = findViewById(R.id.user_nm);
        user_addr = findViewById(R.id.user_addr);
        user_pro_img = findViewById(R.id.user_pro_img);
        user_phone_no = findViewById(R.id.user_phone_no);
        user_pro_img = findViewById(R.id.user_pro_img);

        //보내온 값 파싱
        mainIntent = getIntent();
        UserDTO loginDTO = (UserDTO) mainIntent.getSerializableExtra("loginDTO");
        gradeCheck = mainIntent.getIntExtra("gradeCheck", 1); //등급 확인
        //Log.d("세라수정", "onCreate: email" + loginDTO.getUser_email());

        email = loginDTO.getUser_email();
        pw = loginDTO.getUser_pw();
        name = loginDTO.getUser_nm();
        addr = loginDTO.getUser_addr();
        phone = loginDTO.getUser_phone_no();
        img = loginDTO.getUser_pro_img();

        //가져온값 써넣기
        user_email.setText(email);
        user_pw.setText(pw);
        user_nm.setText(name);
        user_addr.setText(addr);
        user_phone_no.setText(phone);

        imageDbPathU = img;

        //선택된 이미지 보여주기
        Glide.with(this).load(img).into(user_pro_img);

        //사진찍기
        btnCameraUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    try{
                        file = createFile();
                        Log.d("FilePath ", file.getAbsolutePath());
                    }catch(Exception e){
                        Log.d("error1", "Something Wrong", e);
                    }

                    user_pro_img.setVisibility(View.VISIBLE);

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // API24 이상 부터
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                FileProvider.getUriForFile(getApplicationContext(),
                                        getApplicationContext().getPackageName() + ".fileprovider", file));
                        Log.d("sub1:appId", getApplicationContext().getPackageName());
                    }else {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                    }

                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, CAMERA_REQUEST);
                    }

                }catch(Exception e){
                    Log.d("Update:error2", "Something Wrong", e);
                }
            }
        });

        //사진앨범에서 올리기
        btnImgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_pro_img.setVisibility(View.VISIBLE);

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), LOAD_IMAGE);
            }
        });

        //관리자일때 작업
        if(gradeCheck == 2){
            adminLayout1 = findViewById(R.id.adminLayout1);
            adminLayout2 = findViewById(R.id.adminLayout2);
            adminLayout3 = findViewById(R.id.adminLayout3);
            user_id = findViewById(R.id.user_id);
            user_grade = findViewById(R.id.user_grade);
            user_grade_user = findViewById(R.id.user_grade_user);
            user_grade_admin = findViewById(R.id.user_grade_admin);
            user_type = findViewById(R.id.user_type);
            adminView = findViewById(R.id.adminView);
            adminTitle = findViewById(R.id.adminTitle);

            adminView.setVisibility(View.VISIBLE);
            adminTitle.setVisibility(View.VISIBLE);
            adminLayout1.setVisibility(View.VISIBLE);
            adminLayout2.setVisibility(View.VISIBLE);
            adminLayout3.setVisibility(View.VISIBLE);

            id = loginDTO.getUser_id();
            grade = loginDTO.getUser_grade();
            type = loginDTO.getUser_type();

            user_id.setText(String.valueOf(id));
            user_type.setText(type);
            if(grade.equals("1")){
                user_grade_user.setChecked(true);
            }else{
                user_grade_admin.setChecked(true);
            }
        }
    }//onCreate()

    public void btnUpdateClicked(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("안내");
        builder.setMessage("정말 수정하시겠습니까?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(isNetworkConnected(getApplicationContext()) == true){
                    if(fileSize <= 30000000) {  // 파일크기가 30메가 보다 작아야 업로드 할수 있음
                        UserInfoUpdate userInfoUpdate;
                        email = user_email.getText().toString();
                        pw = user_pw.getText().toString();
                        name = user_nm.getText().toString();
                        addr = user_addr.getText().toString();
                        phone = user_phone_no.getText().toString();

                        if(gradeCheck == 2){    //관리자일때
                            id = Long.parseLong(user_id.getText().toString());
                            type = user_type.getText().toString();
                            if( user_grade.getCheckedRadioButtonId() == R.id.user_grade_user){
                                grade = "1";
                            }else{
                                grade = "2";
                            }
                            userInfoUpdate = new UserInfoUpdate(id, email, pw, name, addr, img, phone, grade, type, gradeCheck);
                        }else{  //일반 유저가 접근할때
                            userInfoUpdate = new UserInfoUpdate(email, pw, name, addr, img, phone);
                        }

                        userInfoUpdate.execute();

                        Toast.makeText(UserInfoChangeActivity.this, "수정되었습니다!", Toast.LENGTH_SHORT).show();
                        Log.d("main:UserInfoChange : ", user_email + ", " + user_pw + "," + user_nm + ", " + user_addr + ", " + user_phone_no);
                        Intent showIntent = new Intent(getApplicationContext(), MainActivity.class);
                        showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |   // 이 엑티비티 플래그를 사용하여 엑티비티를 호출하게 되면 새로운 태스크를 생성하여 그 태스크안에 엑티비티를 추가하게 됩니다. 단, 기존에 존재하는 태스크들중에 생성하려는 엑티비티와 동일한 affinity(관계, 유사)를 가지고 있는 태스크가 있다면 그곳으로 새 엑티비티가 들어가게됩니다.
                                Intent.FLAG_ACTIVITY_SINGLE_TOP | // 엑티비티를 호출할 경우 호출된 엑티비티가 현재 태스크의 최상단에 존재하고 있었다면 새로운 인스턴스를 생성하지 않습니다. 예를 들어 ABC가 엑티비티 스택에 존재하는 상태에서 C를 호출하였다면 여전히 ABC가 존재하게 됩니다.
                                Intent.FLAG_ACTIVITY_CLEAR_TOP); // 만약에 엑티비티스택에 호출하려는 엑티비티의 인스턴스가 이미 존재하고 있을 경우에 새로운 인스턴스를 생성하는 것 대신에 존재하고 있는 엑티비티를 포그라운드로 가져옵니다. 그리고 엑티비티스택의 최상단 엑티비티부터 포그라운드로 가져올 엑티비티까지의 모든 엑티비티를 삭제합니다.
                        startActivity(showIntent);

                        finish();
                    }else{
                        // 알림창 띄움
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                        builder.setTitle("알림");
                        builder.setMessage("파일 크기가 30MB초과하는 파일은 업로드가 제한되어 있습니다.\n30MB이하 파일로 선택해 주십시요!!!");
                        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(), "인터넷이 연결되어 있지 않습니다.", Toast.LENGTH_SHORT).show();
                }//if(isNetworkConnected)
            }//onclick()
        });//builder.setPositiveButton()
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });//setNegativeButton()

        AlertDialog dialog = builder.create();
        dialog.show();

    }//btnUpdateClicked()
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

            try {
                // 이미지 돌리기 및 리사이즈
                Bitmap newBitmap = CommonMethod.imageRotateAndResize(file.getAbsolutePath());
                if(newBitmap != null){
                    user_pro_img.setImageBitmap(newBitmap);
                }else{
                    Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }

                imageRealPathU = file.getAbsolutePath();
                String uploadFileName = imageRealPathU.split("/")[imageRealPathU.split("/").length - 1];
                imageDbPathU = ipConfig + "/app/resources/" + uploadFileName;

                ImageView imageView = (ImageView) findViewById(R.id.user_pro_img);
                imageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));

                Log.d("Sub1Update:picPath", file.getAbsolutePath());

            } catch (Exception e){
                e.printStackTrace();

            }
        }else if (requestCode == LOAD_IMAGE && resultCode == RESULT_OK) {

            try {
                String path = "";
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    // Get the path from the Uri
                    path = getPathFromURI(selectedImageUri);
                }

                // 이미지 돌리기 및 리사이즈
                Bitmap newBitmap = CommonMethod.imageRotateAndResize(path);
                if(newBitmap != null){
                    user_pro_img.setImageBitmap(newBitmap);
                }else{
                    Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }

                imageRealPathU = path;
                String uploadFileName = imageRealPathU.split("/")[imageRealPathU.split("/").length - 1];
                imageDbPathU = ipConfig + "/app/resources/" + uploadFileName;

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private File createFile() throws IOException {
        java.text.SimpleDateFormat tmpDateFormat = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");

        String imageFileName = "My" + tmpDateFormat.format(new Date()) + ".jpg";
        File storageDir = Environment.getExternalStorageDirectory();
        File curFile = new File(storageDir, imageFileName);

        return curFile;
    }//createFile()

    public void btnCancelClicked(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("안내");
        builder.setMessage("정말 취소하시겠습니까?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });//setPositiveButton()

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), UserInfoChangeActivity.class);
                startActivity(intent);
            }
        });//setNegativeButton()

        AlertDialog dialog = builder.create();
        dialog.show();
    }//btnCancelClicked()

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }//getPathFromURI()
}//class