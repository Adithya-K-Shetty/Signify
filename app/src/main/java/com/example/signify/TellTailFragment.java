package com.example.signify;


import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.Manifest;
import android.content.ContextWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TellTailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TellTailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button pred_btn;

    Bitmap img;
    public String all_signs;

    public Dictionary signs_dict = new Hashtable();
    public Boolean received_image = false;

    private DatabaseReference myRef;

    public TellTailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TellTailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TellTailFragment newInstance(String param1, String param2) {
        TellTailFragment fragment = new TellTailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myRef = FirebaseDatabase.getInstance().getReference();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3){
            img  = (Bitmap) data.getExtras().get("data");
            received_image = true;

//            int dimension = Math.min(image.getWidth(), image.getHeight());
//            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);



            if(received_image){
                new MyAsyncTask().execute();
            }



//            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
//            classifyImage(image);
        }

        }
    }
    private class MyAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

            String postUrl = "http://192.168.43.101:5000/inputImage";

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            RequestBody postBodyImage = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("image", "androidFlask.jpg", RequestBody.create(MediaType.parse("image/*jpg"), byteArray))
                    .build();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(postUrl)
                    .post(postBodyImage)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    call.cancel();
                    Log.d("FAIL", e.getMessage());
                    System.out.println("FAIL : - "+ e.getMessage());
                    System.out.println("Failed to Connect to Server");
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    try {

                        all_signs = response.body().string();
                        System.out.println(all_signs);
                        String[] all_signs_arr = all_signs.split("@");
                        ArrayList<String> all_signs_list = new ArrayList<String>(
                                Arrays.asList(all_signs_arr));
                        get_data(all_signs_list);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return all_signs;
        }
        @Override
        protected void onPostExecute(String result) {
        }
    }
    private void get_data(ArrayList<String> all_signs_list) {
        Query query = myRef.child("AllSigns");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    if(all_signs_list.contains(snapshot1.child("signName").getValue().toString())){
                        signs_dict.put(snapshot1.child("signImageUrl").getValue().toString(),snapshot1.child("signDescription").getValue().toString());
                    }
                }
                System.out.println(signs_dict);
//                String url = "";
//                for(Enumeration enm = signs_dict.keys(); enm.hasMoreElements();)
//                {
////prints the keys
//                    url =  enm.nextElement().toString();
//                    System.out.println("Hello");
//                }
//                System.out.println(url);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private class MyAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

            String postUrl = "http://192.168.86.154:5000/inputImage";

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            RequestBody postBodyImage = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("image", "androidFlask.jpg", RequestBody.create(MediaType.parse("image/*jpg"), byteArray))
                    .build();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(postUrl)
                    .post(postBodyImage)
                    .build();

            /*********** TESTING NEW STUFF ***************/
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    call.cancel();
                    Log.d("FAIL", e.getMessage());
                    System.out.println("FAIL : - "+ e.getMessage());
                    System.out.println("Failed to Connect to Server");
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            System.out.println("Failed to Connect to Server");
//                        }
//                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                    try {

                        all_signs = response.body().string();
                        System.out.println(all_signs);
                        String[] all_signs_arr = all_signs.split("@");
                        ArrayList<String> all_signs_list = new ArrayList<String>(
                                Arrays.asList(all_signs_arr));
//                        get_data(all_signs_list);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    try {
//                                        all_signs = response.body().string();
//                                        System.out.println(all_signs);
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            });
                }
            });
            /********** END OF TESTING ******************/

            System.out.println(all_signs);
            return all_signs;
        }
        @Override
        protected void onPostExecute(String result) {
            System.out.println("Hello World");
            System.out.println(result);
            System.out.println(all_signs);
//            if (response_success){
//                String[] all_signs_arr = all_signs.split("@");
//                ArrayList<String> all_signs_list = new ArrayList<String>(
//                        Arrays.asList(all_signs_arr));
//                get_data(all_signs_list);
//            }
        }
    }
//    private void get_data(ArrayList<String> all_signs_list) {
//        Query query = myRef.child("AllSigns");
//
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot snapshot1 : snapshot.getChildren()){
//                    if(all_signs_list.contains(snapshot1.child("signName").getValue().toString())){
//                        signs_dict.put(snapshot1.child("signImageUrl").getValue().toString(),snapshot1.child("signDescription").getValue().toString());
//                    }
//                }
//                System.out.println(signs_dict);
////                String url = "";
////                for(Enumeration enm = signs_dict.keys(); enm.hasMoreElements();)
////                {
//////prints the keys
////                    url =  enm.nextElement().toString();
////                    System.out.println("Hello");
////                }
////                System.out.println(url);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_tell_tail, container, false);
        Button cam_btn = (Button) view.findViewById(R.id.cameraBtn);
        cam_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 3);
                } else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                }
            }
        });
        return view;
     }
}