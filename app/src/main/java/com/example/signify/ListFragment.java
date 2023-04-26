package com.example.signify;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView signs;
    SignAdapter adapter;
    SignAdapter.ListItemClickListener signClick;
    ArrayList<SignHelper> signloc = new ArrayList<>();
    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_list, container, false);
        signs = view.findViewById(R.id.sign_list);
        signRecycle();
        return view;
    }
    private void signRecycle() {
        signs.setHasFixedSize(true);
        signs.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        signloc.add(new SignHelper(R.drawable.baseline_build_24,"settings"));
        signloc.add(new SignHelper(R.drawable.baseline_build_24,"settings"));
        signloc.add(new SignHelper(R.drawable.baseline_build_24,"settings"));
        signloc.add(new SignHelper(R.drawable.baseline_build_24,"settings"));
        signloc.add(new SignHelper(R.drawable.baseline_build_24,"settings"));
        signloc.add(new SignHelper(R.drawable.baseline_build_24,"settings"));
        signloc.add(new SignHelper(R.drawable.baseline_build_24,"settings"));
        signloc.add(new SignHelper(R.drawable.baseline_build_24,"settings"));
        signloc.add(new SignHelper(R.drawable.baseline_build_24,"settings"));
        signloc.add(new SignHelper(R.drawable.baseline_build_24,"settings"));
        signloc.add(new SignHelper(R.drawable.baseline_build_24,"settings"));



        adapter = new SignAdapter(signloc,signClick);
        signs.setAdapter(adapter);

    }
    public void signClick(int clickedItemIndex) {
//        Intent mIntent;
//        Bundle b=new Bundle()
        Toast.makeText(getContext(), "u clicked an item", Toast.LENGTH_SHORT).show();
    }
}