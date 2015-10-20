package com.yjkj.choose_function;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.mobeta.android.dslv.DragSortListView;
import com.mobeta.android.dslv.DragSortListView.DropListener;
import com.mobeta.android.dslv.DragSortListView.RemoveListener;
import com.yjkj.choose_function.model.Function;

public class MainActivity extends Activity {
	private DragSortListView unchoose;
	private DragSortListView enchoose;
	private List<Function> uninfos=new ArrayList<Function>();
	private List<Function> eninfos=new ArrayList<Function>();
	
	private FunctionAdapter uninfoadapter;
	private FunctionAdapter eninfoadapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		unchoose = (DragSortListView) findViewById(R.id.unchoose);
		enchoose = (DragSortListView) findViewById(R.id.enchoose);
		if(Function.size()<1){
		initdata();
		}
		uninfos=Function.unchoose();
		eninfos=Function.enchoose();
		
		uninfoadapter=new FunctionAdapter(uninfos);
		eninfoadapter=new FunctionAdapter(eninfos);
		unchoose.setAdapter(uninfoadapter);
		enchoose.setAdapter(eninfoadapter);
		unchoose.setDropListener(unonDrop);
		enchoose.setDropListener(enonDrop);
		
		unchoose.setRemoveListener(unremove);
		enchoose.setRemoveListener(enremove);
		
	}

	private void initdata() {
		long t=System.currentTimeMillis();
		ActiveAndroid.beginTransaction();
		try {
			for (int i = 0; i < 10; i++) {
				Function function = new Function();
				function.Function_name="功能" + i;
				function.Function_content="数据库内功能介绍" + i;
				function.Function_class=MainActivity.class.getName()+i;
				function.Function_choose=false;
				function.Series=i;
				function.save();
			}
			ActiveAndroid.setTransactionSuccessful();
		} finally {
			ActiveAndroid.endTransaction();
		}
		System.out.println("endtime:"+(System.currentTimeMillis()-t));
	}
	
	private void updataall(){
		updadtaunlist();
		updadtaenlist();
	}
	
	/**
	 * 到数据库更新未选择功能列表
	 */
	private void updadtaunlist(){
		updatalist(uninfos,false);
	}
	/**
	 * 到数据库更新已选择功能列表
	 */
	private void updadtaenlist(){
		updatalist(eninfos,true);
	}
	
	/**更新数据库功能列表
	 * @param functions 功能列表集合
	 * @param choose 功能列表是否需要选中，true：选中，false：补选中
	 */
	private void updatalist(List<Function> functions,boolean choose){
		ActiveAndroid.beginTransaction();
		try {
			for (int i=0;i<functions.size();i++) {
				functions.get(i).Function_choose=choose;
				functions.get(i).Series=i;
				functions.get(i).save();
			}
			ActiveAndroid.setTransactionSuccessful();
		} finally {
			ActiveAndroid.endTransaction();
		}
	}
	//未选择列表拖拽排序监听
	private DropListener unonDrop=new DropListener() {
		
		@Override
		public void drop(int from, int to) {
			// TODO Auto-generated method stub
			if (from != to) {
				Function item = (Function) uninfoadapter.getItem(from);
				uninfoadapter.remove(from);
				uninfoadapter.insert(item, to);
				updadtaunlist();
			}
		}
	};
	//已选择列表拖拽
	DropListener enonDrop=new DropListener() {
		
		@Override
		public void drop(int from, int to) {
			// TODO Auto-generated method stub
			if (from != to) {
				Function item = (Function) uninfoadapter.getItem(from);
				eninfoadapter.remove(from);
				eninfoadapter.insert(item, to);
				updadtaenlist();
			}
		}
	};
	//未选择列表移除
	private RemoveListener unremove =new RemoveListener() {
		
		@Override
		public void remove(int which) {
			// TODO Auto-generated method stub
			Function item=(Function) uninfoadapter.getItem(which);
			eninfoadapter.additem(item);
			uninfoadapter.remove(which);
			updataall();
		}
	};
	
	//未选择列表移除
		private RemoveListener enremove =new RemoveListener() {
			
			@Override
			public void remove(int which) {
				// TODO Auto-generated method stub
				Function item=(Function) eninfoadapter.getItem(which);
				uninfoadapter.additem(item);
				eninfoadapter.remove(which);
				updataall();
			}
		};


	/**
	 * @author 李明
	 * 
	 * 功能列表适配器
	 *
	 */
	class FunctionAdapter extends BaseAdapter{
		List<Function> infos;
		
		public FunctionAdapter(List<Function> infos) {
			super();
			this.infos = infos;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return infos.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return infos.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}
		
		/**
		 * @param Item序列
		 * 移除某一个Item
		 */
		public void remove(int arg0) {
			infos.remove(arg0);  
	        this.notifyDataSetChanged();
	    }  
	      
	    /**
	     * @param item 功能对象
	     * @param arg0 序列位置
	     * 将功能对象插入到某哥位置
	     */
	    public void insert(Function item, int arg0) {
	    	infos.add(arg0, item);  
	        this.notifyDataSetChanged();  
	    }  
	    
	    /**
	     * @param item 功能对象
	     * 将功能对象插入到列表最尾部
	     */
	    public void additem(Function item){
	    	infos.add(item);
	    	this.notifyDataSetChanged(); 
	    }

		@Override
		public View getView(int Position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder viewholder;
			if(convertView==null){
				convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.funtion_item, null);
				viewholder=new ViewHolder(convertView);
				convertView.setTag(viewholder);
			}else{
				viewholder=(ViewHolder) convertView.getTag();
			}
			viewholder.settext(infos.get(Position));
			return convertView;
		}
		
		class ViewHolder{
			public ViewHolder(View convertView) {
				super();
				// TODO Auto-generated constructor stub
				this.titleview=(TextView) convertView.findViewById(R.id.funtion_title);
				this.contentview=(TextView) convertView.findViewById(R.id.funtion_content);
			}
			public void settext(Function info){
				this.titleview.setText(info.Function_name);
				this.contentview.setText(info.Function_content);
			}
			private TextView titleview;
			private TextView contentview;
		}
	}
}
