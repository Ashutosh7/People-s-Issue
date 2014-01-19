package com.peoplesissues.activities;


import com.becker666.resty.R;
import com.peoplesissues.activities.ChangeLinkListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class SolvedActivity extends Fragment {

	/*Listdata ld = new Listdata("a","b");
	Listdata[] data = {ld,ld,ld,ld};*/
	Listdata[] data = new Listdata[Globals.Nunsolved + 2];
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.solved, container,false);
		Globals.tabsolved = true;

		//------ Customized List View;
		/*for(int i=0;i<Globals.Nunsolved;i++)
		{
			data[i] = Globals.unsolved[i];
		}*/
		CustomListAdapter adapter = new CustomListAdapter(getActivity(), Globals.solved);
		ListView list = (ListView)view.findViewById(R.id.listLv);
		list.setAdapter(adapter);
		//set OnclickListener
		// --- end of ListView implementation
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				//Toast.makeText(getActivity(), "hurray", Toast.LENGTH_LONG).show();
				( (ChangeLinkListener)  getActivity()).onLinkChange(Globals.solved[arg2]);
			}
		});
		return view;

	}

}