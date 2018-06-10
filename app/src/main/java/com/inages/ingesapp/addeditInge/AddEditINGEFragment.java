package com.inages.ingesapp.addeditInge;


import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.inages.ingesapp.R;
import com.inages.ingesapp.data.Inge;
import com.inages.ingesapp.data.IngesContract;
import com.inages.ingesapp.data.IngesDbHelper;

public class AddEditINGEFragment extends Fragment {
    private static final String ARG_INGE_ID = "arg_INGE_id";
    private String mINGEId;
    private IngesDbHelper mINGESDbHelper;

    private FloatingActionButton mSaveButton;
    private TextInputEditText mNameField;
    private TextInputEditText mPhoneNumberField;
    private TextInputEditText mSpecialtyField;
    private TextInputEditText mBioField;
    private TextInputLayout mNameLabel;
    private TextInputLayout mPhoneNumberLabel;
    private TextInputLayout mSpecialtyLabel;
    private TextInputLayout mBioLabel;


    public AddEditINGEFragment() {
        // Required empty public constructor
    }

    public static AddEditINGEFragment newInstance(String INGEId) {
        AddEditINGEFragment fragment = new AddEditINGEFragment();
        Bundle args = new Bundle();
        args.putString(ARG_INGE_ID, INGEId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        if (getArguments() != null) {
            mINGEId = getArguments().getString(ARG_INGE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_inge, container, false);

        // Referencias UI
        mSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mNameField = (TextInputEditText) root.findViewById(R.id.et_name);
        mPhoneNumberField = (TextInputEditText) root.findViewById(R.id.et_phone_number);
        mSpecialtyField = (TextInputEditText) root.findViewById(R.id.et_specialty);
        mBioField = (TextInputEditText) root.findViewById(R.id.et_bio);
        mNameLabel = (TextInputLayout) root.findViewById(R.id.til_name);
        mPhoneNumberLabel = (TextInputLayout) root.findViewById(R.id.til_phone_number);
        mSpecialtyLabel = (TextInputLayout) root.findViewById(R.id.til_specialty);
        mBioLabel = (TextInputLayout) root.findViewById(R.id.til_bio);

        // Eventos
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditINGE();
            }
        });

        mINGESDbHelper = new IngesDbHelper(getActivity());

        // Carga de datos
        if (mINGEId != null) {
            loadINGE();
        }

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditINGE();
            }
        });


        return root;
    }




    private void loadINGE() {
        new IngesLoadTask().execute();

    }

    private class IngesLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            if(mINGEId!=null)
                return mINGESDbHelper.getIngeById(mINGEId);
            else
                return null;
        }





        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToNext()){
                    String name = cursor.getString(cursor.getColumnIndex(IngesContract.IngeEntry.NAME));
                    String phoneNumber = cursor.getString(cursor.getColumnIndex(IngesContract.IngeEntry.PHONE_NUMBER));
                    String specialty = cursor.getString(cursor.getColumnIndex(IngesContract.IngeEntry.SPECIALTY));
                    String bio = cursor.getString(cursor.getColumnIndex(IngesContract.IngeEntry.BIO));

                    mNameField.setText(name);
                    mPhoneNumberField.setText(phoneNumber);
                    mSpecialtyField.setText(specialty);
                    mBioField.setText(bio);

                }
                Log.e("INGEs","se encontro inges");

            } else {
                Toast.makeText(getActivity(),"Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class AddEditINGETask extends AsyncTask<Inge, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Inge... INGES) {
            if (mINGEId != null) {
                return mINGESDbHelper.updateINGE(INGES[0], mINGEId) > 0;
            } else {
                return mINGESDbHelper.saveInge(INGES[0]) > 0;
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            showINGESScreen(result);
        }

    }



    private void showINGESScreen(Boolean requery) {
        if (!requery) {
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        } else {
            getActivity().setResult(Activity.RESULT_OK);
        }

        getActivity().finish();
    }

    private void showAddEditError() {
        Toast.makeText(getActivity(),
                "Error al agregar nueva información", Toast.LENGTH_SHORT).show();
    }

    private void addEditINGE() {
        boolean error = false;

        String name = mNameField.getText().toString();
        String phoneNumber = mPhoneNumberField.getText().toString();
        String specialty = mSpecialtyField.getText().toString();
        String bio = mBioField.getText().toString();

        if (TextUtils.isEmpty(name)) {
            mNameLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            mPhoneNumberLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(specialty)) {
            mSpecialtyLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(bio)) {
            mBioLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (error) {
            return;
        }
        Inge INGE = new Inge(name, specialty, phoneNumber, bio, "");
        new AddEditINGETask().execute(INGE);

    }


}
