package com.example.fastfoodapp.App;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fastfoodapp.Activity.Address;
import com.example.fastfoodapp.R;
import com.example.fastfoodapp.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Custom_dialog extends AppCompatDialogFragment {

    private ExampleDialogListener listener;

    Spinner stateSpinner,districtSpinner,wardSpinner;
    String selectedState,selectedDistrict,selectedWard,AddressCustom;
    TextView tvWard,tvAddress;
    EditText edtDiachi;

    ArrayList<String> stateList = new ArrayList<>();
    ArrayList<String> districtList = new ArrayList<>();
    ArrayList<String> wardList = new ArrayList<>();
    ArrayAdapter<String> stateAdapter,districtAdapter,wardAdapter;
    RequestQueue requestQueue;
    String urlState = Utils.BASE_URL + "Android/address/populate_state.php";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.MyDialogTheme);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog,null);

        requestQueue = Volley.newRequestQueue(getContext());

        stateSpinner = view.findViewById(R.id.spinner1);
        districtSpinner = view.findViewById(R.id.spinner2);
        wardSpinner = view.findViewById(R.id.spinner3);
        tvWard = view.findViewById(R.id.textView4);
        tvAddress = view.findViewById(R.id.cuthe);
        edtDiachi = view.findViewById(R.id.edtdiachi);

        builder.setView(view)
                .setTitle(null)
                .setCancelable(false)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        selectedState = stateSpinner.getSelectedItem().toString();
                        selectedDistrict = districtSpinner.getSelectedItem().toString();
                        selectedWard = wardSpinner.getSelectedItem().toString();
                        AddressCustom = edtDiachi.getText().toString().trim();

                        listener.applyText(selectedState,selectedDistrict,selectedWard,AddressCustom);


                    }
                });
//        AlertDialog dialog = builder.create();
//        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
////                if (wardSpinner.getSelectedItem() == null){
////                    tvWard.setError("Chưa chọn!");
////                    tvWard.requestFocus();
////                }else {
//                    if(TextUtils.isEmpty(AddressCustom)){
////                        tvWard.setError(null);
//                        tvAddress.setError("Chưa điền!");
//                        tvAddress.requestFocus();
//                    }else{
////                        tvWard.setError(null);
//                        tvAddress.setError(null);
//
//
//                    }
////                }
//            }
//        });

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlState,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray(("state"));
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String stateName = jsonObject.optString("state_name");
                        stateList.add(stateName);
                        stateAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,stateList);
                        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        stateSpinner.setAdapter(stateAdapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);

        stateSpinner.setOnItemSelectedListener(state);
        districtSpinner.setOnItemSelectedListener(district);
        wardSpinner.setOnItemSelectedListener(ward);

        return builder.create();
    }
    private AdapterView.OnItemSelectedListener state = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if(i>0){
                districtList.clear();
                selectedDistrict = stateSpinner.getSelectedItem().toString();
                String urlDistrict = Utils.BASE_URL + "Android/address/populate_district.php?state_name="+selectedDistrict;
                JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, urlDistrict, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray jsonArray = response.getJSONArray("district");
                                    for (int i=0;i<jsonArray.length();i++){
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String districtName = jsonObject.optString("district_name");
                                        districtList.add(districtName);
                                        districtAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,districtList);
                                        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        districtSpinner.setAdapter(districtAdapter);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(jsonObjectRequest1);
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private AdapterView.OnItemSelectedListener district = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if(i>0){
                wardList.clear();
                selectedWard = districtSpinner.getSelectedItem().toString();
                String urlWard = Utils.BASE_URL + "Android/address/populate_ward.php?district_name="+selectedWard;

                JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.POST, urlWard, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray jsonArray = response.getJSONArray("ward");
                                    for (int i=0;i<jsonArray.length();i++){
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String wardName = jsonObject.optString("ward_name");
                                        wardList.add(wardName);
                                        wardAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,wardList);
                                        wardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        wardSpinner.setAdapter(wardAdapter);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(jsonObjectRequest2);
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    public AdapterView.OnItemSelectedListener ward = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener{
        void applyText(String state,String district,String ward,String addressCustom);
    }
}
