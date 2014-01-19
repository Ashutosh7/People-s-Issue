package com.peoplesissues.activities;

import com.becker666.resty.R;

import android.content.Context;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

// custom adapter for lists in solved and unsolvedActivity
public class CustomListAdapter extends BaseAdapter{

	private final Listdata[] values;
	private Listdata data;
	private Context context;
	private static LayoutInflater inflater=null;
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//return values.length;
		if(Globals.tabsolved)
			return Globals.Nsolved;
		else
			return Globals.Nunsolved;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		//if(position<Globals.Nunsolved)
		//{

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(convertView==null){
			v = inflater.inflate(R.layout.list_row, null);
		}
		ImageView thumb = (ImageView) v.findViewById(R.id.thumbImg) ;
		TextView title = (TextView) v.findViewById(R.id.titleTv);
		TextView subtext = (TextView) v.findViewById(R.id.subtextTv);
		TextView votes = (TextView)v.findViewById(R.id.votes);
		//set apppt values

		data = new Listdata();
		data = Globals.unsolved[position];

		System.out.println("position=" + position);

		thumb.setImageResource(R.drawable.logo);
		title.setText(data.title);
		subtext.setText(data.location);
		int vot = data.upvotes-data.downvotes;
		votes.setText(Integer.toString(vot));

		//}
		return v;

	}

	public CustomListAdapter(Context context, Listdata[] values){
		this.values = values;
		this.context = context;
	}
}
