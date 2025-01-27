package com.example.signify;

import static android.app.Activity.RESULT_CANCELED;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
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
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
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

    Bitmap img;
    public String all_signs;

    public Dictionary signs_dict = new Hashtable();
//    public ArrayList<String> imageUrlList =  new ArrayList<String>();
//    public ArrayList<String> mainDescriptionList = new ArrayList<String>();
//    public ArrayList<String> subDescruptionList = new ArrayList<String>();

    /** change made here **/
    public static ArrayList<SignHelper> sign_data_list;
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
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        myRef = FirebaseDatabase.getInstance().getReference();
//        sign_data_list = new ArrayList<>();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED)
        {
            System.out.println("Fragment Closed");
            return;
        }
        if(requestCode == 3){
            img  = (Bitmap) data.getExtras().get("data");
            received_image = true;

            if(received_image){
                Toast.makeText(getActivity().getApplicationContext(), "Check ⠇☰ for the detected signs", Toast.LENGTH_LONG).show();
                new MyAsyncTask().execute();
                received_image = false;
            }

        }
        else{
            System.out.println("Entered Gallery");
            Uri dat = data.getData();
            try {
                img = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), dat);
                received_image = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(received_image){
                new MyAsyncTask().execute();
                received_image = false;
//                sign_data_list = null;
            }
        }
    }
    private class MyAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {


            String postUrl = "http://ipv4-address:port-number/inputImage";

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
                        System.out.println(all_signs_list);
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
        sign_data_list = new ArrayList<>();
        Query query = myRef.child("AllSigns");
        System.out.println("Hello World 1");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("Hello World 2");
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    if(all_signs_list.contains(snapshot1.child("signName").getValue().toString())){
                        signs_dict.put(snapshot1.child("signImageUrl").getValue().toString(),snapshot1.child("signDescription1").getValue().toString());
                        SignHelper signHelper = new SignHelper();
                        signHelper.setImageUrl(snapshot1.child("signImageUrl").getValue().toString());
                        signHelper.setTitle(snapshot1.child("signName").getValue().toString());
                        signHelper.setDescription1(snapshot1.child("signDescription1").getValue().toString());
                        signHelper.setDescription2(snapshot1.child("signDescription2").getValue().toString());
                        signHelper.setSeverityValue(snapshot1.child("signDescription3").getValue().toString());
                        sign_data_list.add(signHelper);
                    }
                }
//                if(sign_data_list != null)
//                {
//                    Dashboard dashboard = new Dashboard();
//                    dashboard.bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.baseline_format_list_bulleted_24));
//                }
                System.out.println("Hello World 3");
                System.out.println(sign_data_list);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_tell_tail, container, false);
        Button cam_btn = (Button) view.findViewById(R.id.cameraBtn);
        Button gallery_btn = (Button) view.findViewById(R.id.galleryBtn);
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
        gallery_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(cameraIntent, 1);
            }
        });
        return view;
     }
}