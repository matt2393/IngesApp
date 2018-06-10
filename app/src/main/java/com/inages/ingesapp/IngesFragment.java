package com.inages.ingesapp;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.inages.ingesapp.IngeDetail.IngesDetailActivity;
import com.inages.ingesapp.addeditInge.AddEditINGEActivity;
import com.inages.ingesapp.data.IngesContract;
import com.inages.ingesapp.data.IngesCursorAdapter;
import com.inages.ingesapp.data.IngesDbHelper;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IngesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngesFragment extends Fragment {

    private ListView mIngesList;
    private IngesCursorAdapter mIngesAdapter;
    private FloatingActionButton mAddButton;
    private IngesDbHelper mIngesDbHelper;
    public final static int REQUEST_UPDATE_DELETE_INGE=111;

    public IngesFragment() {
        // Required empty public constructor
    }


    public static IngesFragment newInstance() {

        return new IngesFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_inges,container,false);

        mIngesList = (ListView) root.findViewById(R.id.INGES_list);
        mIngesAdapter = new IngesCursorAdapter(getActivity(), null);
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        mIngesList.setAdapter(mIngesAdapter);

        // Instancia de helper
        mIngesDbHelper = new IngesDbHelper(getActivity());


        mIngesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mIngesAdapter.getItem(i);
                String currentIngeId = currentItem.getString(
                        currentItem.getColumnIndex(IngesContract.IngeEntry.ID));
                showDetailScreen(currentIngeId);

            }
        });
        // Carga de datos
        loadInges();


        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddScreen();
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case REQUEST_UPDATE_DELETE_INGE:
                    loadInges();
                    break;
                case AddEditINGEActivity.REQUEST_ADD_INGE:
                    showSuccessfullSavedMessage();
                    loadInges();
                    break;

            }
        }
    }
    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Ingeniero guardado correctamente", Toast.LENGTH_SHORT).show();
    }


    private void loadInges() {
        new IngesLoadTask().execute();
    }

    private class IngesLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mIngesDbHelper.getAllInges();
        }





        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mIngesAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }


    private void showDetailScreen(String IngeId) {
        Intent intent = new Intent(getActivity(), IngesDetailActivity.class);
        intent.putExtra(IngesActivity.EXTRA_INGE_ID, IngeId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_INGE);
    }


    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditINGEActivity.class);
        startActivityForResult(intent, AddEditINGEActivity.REQUEST_ADD_INGE);
    }


}
