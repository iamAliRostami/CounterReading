package com.leon.reading_counter.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.leon.reading_counter.MyApplication;
import com.leon.reading_counter.R;
import com.leon.reading_counter.enums.SharedReferenceKeys;
import com.leon.reading_counter.enums.SharedReferenceNames;
import com.leon.reading_counter.infrastructure.ISharedPreferenceManager;
import com.leon.reading_counter.utils.CustomFile;
import com.leon.reading_counter.utils.CustomToast;
import com.leon.reading_counter.utils.SharedPreferenceManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

import static com.leon.reading_counter.MyApplication.CAMERA_REQUEST;
import static com.leon.reading_counter.MyApplication.GALLERY_REQUEST;
import static com.leon.reading_counter.utils.CustomFile.createImageFile;

public class TakePhotoActivity extends AppCompatActivity {
    com.leon.reading_counter.databinding.ActivityTakePhotoBinding binding;
    ISharedPreferenceManager sharedPreferenceManager;
    int imageNumber = 1, imageNumberTemp = 0;
    ArrayList<Bitmap> bitmaps;
    boolean replace = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferenceManager = new SharedPreferenceManager(getApplicationContext(),
                SharedReferenceNames.ACCOUNT.getValue());
        int theme = sharedPreferenceManager.getIntData(SharedReferenceKeys.THEME_STABLE.getValue());
        MyApplication.onActivitySetTheme(this, theme, true);
        binding = com.leon.reading_counter.databinding.ActivityTakePhotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (checkPermission())
            initialize();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void initialize() {
        bitmaps = new ArrayList<>();
        imageSetup();
        setOnButtonSendClickListener();
    }

    void setOnButtonSendClickListener() {
        binding.buttonSaveSend.setOnClickListener(v -> {
            for (Bitmap bitmap : bitmaps) {
                CustomFile.saveTempBitmap(bitmap, getApplicationContext());
            }
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void imageSetup() {
        binding.imageView1.setImageDrawable(getResources().getDrawable(R.drawable.img_take_photo));
        binding.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.img_take_photo));
        binding.imageView3.setImageDrawable(getResources().getDrawable(R.drawable.img_take_photo));
        binding.imageView4.setImageDrawable(getResources().getDrawable(R.drawable.img_take_photo));
        setOnImageViewPickerClickListener();
        setOnImageViewDeleteClickListener();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void setOnImageViewPickerClickListener() {
        binding.imageView1.setOnClickListener(v -> {
            if (imageNumber > 1) {
                replace = true;
                imageNumberTemp = 1;
            } else {
                replace = false;
            }
            imagePicker();
        });
        binding.imageView2.setOnClickListener(v -> {
            if (imageNumber > 2) {
                replace = true;
                imageNumberTemp = 2;
            } else {
                replace = false;
            }
            imagePicker();

        });
        binding.imageView3.setOnClickListener(v -> {
            if (imageNumber > 3) {
                replace = true;
                imageNumberTemp = 3;
            } else {
                replace = false;
            }
            imagePicker();

        });
        binding.imageView4.setOnClickListener(v -> {
            if (imageNumber > 4) {
                replace = true;
                imageNumberTemp = 4;
            } else {
                replace = false;
            }
            imagePicker();
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void setOnImageViewDeleteClickListener() {
        binding.imageViewDelete1.setOnClickListener(v -> {
            if (imageNumber > 1) {
                imageNumber = imageNumber - 1;
                bitmaps.remove(0);
                binding.imageView1.setImageBitmap(((BitmapDrawable) binding.imageView2.getDrawable()).getBitmap());
                binding.imageView2.setImageBitmap(((BitmapDrawable) binding.imageView3.getDrawable()).getBitmap());
                binding.imageView3.setImageBitmap(((BitmapDrawable) binding.imageView4.getDrawable()).getBitmap());
                binding.imageView4.setImageDrawable(getDrawable(R.drawable.img_take_photo));
            }
        });
        binding.imageViewDelete2.setOnClickListener(v -> {
            if (imageNumber > 2) {
                imageNumber = imageNumber - 1;
                bitmaps.remove(1);
                binding.imageView2.setImageBitmap(((BitmapDrawable) binding.imageView3.getDrawable()).getBitmap());
                binding.imageView3.setImageBitmap(((BitmapDrawable) binding.imageView4.getDrawable()).getBitmap());
                binding.imageView4.setImageDrawable(getDrawable(R.drawable.img_take_photo));
            }
        });
        binding.imageViewDelete3.setOnClickListener(v -> {
            if (imageNumber > 3) {
                imageNumber = imageNumber - 1;
                bitmaps.remove(2);
                binding.imageView3.setImageBitmap(((BitmapDrawable) binding.imageView4.getDrawable()).getBitmap());
                binding.imageView4.setImageDrawable(getDrawable(R.drawable.img_take_photo));
            }
        });
        binding.imageViewDelete4.setOnClickListener(v -> {
            if (imageNumber > 4) {
                imageNumber = imageNumber - 1;
                bitmaps.remove(3);
                binding.imageView4.setImageDrawable(getDrawable(R.drawable.img_take_photo));
            }
        });
    }

    boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            askStoragePermission();
            return false;
        } else {
            initialize();
            return true;
        }
    }

    void askStoragePermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                CustomToast customToast = new CustomToast();
                customToast.info(getString(R.string.access_granted));
                initialize();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                forceClose();
            }
        };
        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage(getString(R.string.confirm_permission))
                .setRationaleConfirmText(getString(R.string.allow_permission))
                .setDeniedMessage(getString(R.string.if_reject_permission))
                .setDeniedCloseButtonText(getString(R.string.close))
                .setGotoSettingButtonText(getString(R.string.allow_permission))
                .setPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).check();
    }

    void forceClose() {
        CustomToast customToast = new CustomToast();
        customToast.error(getString(R.string.permission_not_completed));
        finish();
    }

    @SuppressLint("QueryPermissionsNeeded")
    void imagePicker() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TakePhotoActivity.this);
        builder.setTitle(R.string.choose_document);
        builder.setMessage(R.string.select_source);
        builder.setPositiveButton(R.string.gallery, (dialog, which) -> {
            dialog.dismiss();
            Intent intent = new Intent("android.intent.action.PICK");
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_REQUEST);
        });
        builder.setNegativeButton(R.string.camera, (dialog, which) -> {
            dialog.dismiss();
            Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
            if (cameraIntent.resolveActivity(TakePhotoActivity.this.getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {
                    Log.e("Main", e.toString());
                }
                if (photoFile != null) {
                    StrictMode.VmPolicy.Builder builderTemp = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builderTemp.build());
                    cameraIntent.putExtra("output", Uri.fromFile(photoFile));
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });
        builder.setNeutralButton("", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST && data != null) {
                Uri selectedImage = data.getData();
                Bitmap bitmap;
                try {
                    Uri uri = data.getData();
                    Objects.requireNonNull(uri);
                    InputStream inputStream = this.getContentResolver().openInputStream(
                            Objects.requireNonNull(selectedImage));
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    MyApplication.bitmapSelectedImage = bitmap;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == CAMERA_REQUEST) {
                ContentResolver contentResolver = this.getContentResolver();
                try {
                    MyApplication.bitmapSelectedImage = MediaStore.Images.Media.getBitmap(
                            contentResolver, Uri.parse(MyApplication.fileName));
                } catch (IOException e) {
                    Log.e("Error", e.toString());
                    e.printStackTrace();
                }
            }
            if (replace) {
                bitmaps.set(imageNumberTemp - 1, MyApplication.bitmapSelectedImage);
                if (imageNumberTemp == 1) {
                    binding.imageView1.setImageBitmap(MyApplication.bitmapSelectedImage);
                } else if (imageNumberTemp == 2) {
                    binding.imageView2.setImageBitmap(MyApplication.bitmapSelectedImage);
                } else if (imageNumberTemp == 3) {
                    binding.imageView3.setImageBitmap(MyApplication.bitmapSelectedImage);
                } else if (imageNumberTemp == 4) {
                    binding.imageView4.setImageBitmap(MyApplication.bitmapSelectedImage);
                }
            } else {
                bitmaps.add(MyApplication.bitmapSelectedImage);
                if (imageNumber == 1) {
                    binding.imageView1.setImageBitmap(MyApplication.bitmapSelectedImage);
                } else if (imageNumber == 2) {
                    binding.imageView2.setImageBitmap(MyApplication.bitmapSelectedImage);
                } else if (imageNumber == 3) {
                    binding.imageView3.setImageBitmap(MyApplication.bitmapSelectedImage);
                } else if (imageNumber == 4) {
                    binding.imageView4.setImageBitmap(MyApplication.bitmapSelectedImage);
                }
                imageNumber = imageNumber + 1;
            }
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        Runtime.getRuntime().totalMemory();
        Runtime.getRuntime().freeMemory();
        Runtime.getRuntime().maxMemory();
        Debug.getNativeHeapAllocatedSize();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.imageView1.setImageDrawable(null);
        binding.imageView2.setImageDrawable(null);
        binding.imageView3.setImageDrawable(null);
        binding.imageView4.setImageDrawable(null);
        MyApplication.bitmapSelectedImage = null;
        Runtime.getRuntime().totalMemory();
        Runtime.getRuntime().freeMemory();
        Runtime.getRuntime().maxMemory();
        Debug.getNativeHeapAllocatedSize();
    }
}