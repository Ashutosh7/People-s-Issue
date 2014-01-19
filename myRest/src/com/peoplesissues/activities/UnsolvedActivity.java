package com.peoplesissues.activities;


import com.becker666.resty.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class UnsolvedActivity extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.unsolved, container,false);
		Globals.tabsolved = false;
		Listdata[] data = Globals.unsolved;
		//data = Globals.unsolved;
		//------ Customized List View;
		CustomListAdapter adapter = new CustomListAdapter(getActivity(), Globals.unsolved);
		ListView list = (ListView)view.findViewById(R.id.list2Lv);
		list.setAdapter(adapter);
		//set Onclick
		//list.setOnClick;
		// --- end of ListView implementation
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				//Toast.makeText(getActivity(), "hurray", Toast.LENGTH_LONG).show();
				( (ChangeLinkListener)  getActivity()).onLinkChange(Globals.unsolved[arg2]);
			}
		});
		return view;

	}

}

