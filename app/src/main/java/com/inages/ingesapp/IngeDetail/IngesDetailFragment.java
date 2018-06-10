package com.inages.ingesapp.IngeDetail;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.inages.ingesapp.GlideApp;
import com.inages.ingesapp.IngesActivity;
import com.inages.ingesapp.IngesFragment;
import com.inages.ingesapp.R;
import com.inages.ingesapp.addeditInge.AddEditINGEActivity;
import com.inages.ingesapp.data.Inge;
import com.inages.ingesapp.data.IngesDbHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IngesDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngesDetailFragment extends Fragment {

    private String mIngeId;
    private final static String ARG_INGE_ID="arg_inge_id";
    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatar;
    private TextView mPhoneNumber;
    private TextView mSpecialty;
    private TextView mBio;
    private IngesDbHelper mIngesDbHelper;


    public IngesDetailFragment() {
        // Required empty public constructor
    }


    public static IngesDetailFragment newInstance(String IngeId) {
        IngesDetailFragment fragment = new IngesDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_INGE_ID, IngeId);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIngeId = getArguments().getString(ARG_INGE_ID);
        }

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_inges_detail, container, false);
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);




        mAvatar = (ImageView) getActivity().findViewById(R.id.iv_avatar);
        mPhoneNumber = (TextView) root.findViewById(R.id.tv_phone_number);
        mSpecialty = (TextView) root.findViewById(R.id.tv_specialty);
        mBio = (TextView) root.findViewById(R.id.tv_bio);
        mIngesDbHelper = new IngesDbHelper(getActivity());

        mIngesDbHelper = new IngesDbHelper(getActivity());
        loadINGE();


        return root;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Acciones
    }

    private void loadINGE() {
        new GetINGEByIdTask().execute();
    }

    private void showInge(Inge INGE) {
        mCollapsingView.setTitle(INGE.getName());
        GlideApp.with(this)
                .load(Uri.parse("file:///android_asset/" + INGE.getAvatarUri()))
                .centerCrop()
                .into(mAvatar);
        mPhoneNumber.setText(INGE.getPhoneNumber());
        mSpecialty.setText(INGE.getSpecialty());
        mBio.setText(INGE.getBio());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al cargar información", Toast.LENGTH_SHORT).show();
    }

    private class GetINGEByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mIngesDbHelper.getIngeById(mIngeId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showInge(new Inge(cursor));
            } else {
                showLoadError();
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                new DeleteINGETask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class DeleteINGETask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return mIngesDbHelper.deleteINGE(mIngeId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showINGESScreen(integer > 0);
        }

    }
    private void showINGESScreen(boolean requery) {
        if (!requery) {
            showDeleteError();
        }
        getActivity().setResult(requery ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    private void showDeleteError() {
        Toast.makeText(getActivity(),
                "Error al eliminar ingeniero", Toast.LENGTH_SHORT).show();
    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditINGEActivity.class);
        intent.putExtra(IngesActivity.EXTRA_INGE_ID, mIngeId);
        startActivityForResult(intent, IngesFragment.REQUEST_UPDATE_DELETE_INGE);
    }




}
