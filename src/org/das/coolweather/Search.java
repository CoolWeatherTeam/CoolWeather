package org.das.coolweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Search extends Fragment {

	private EditText edtSearchTerm;
	private Button btnSearchTerm;
	
	public static Search newInstance() {
		Search fragment = new Search();
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_search, container,
				false);
		edtSearchTerm= (EditText) rootView.findViewById(R.id.edtSearchTerm);
		btnSearchTerm = (Button) rootView.findViewById(R.id.btnSearch);
		
		btnSearchTerm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intentDetails = new Intent(getActivity(), DetailsActivity.class);
				intentDetails.putExtra("City", edtSearchTerm.getText().toString());
				startActivity(intentDetails);
				
			}
		});
		return rootView;
	}

}
