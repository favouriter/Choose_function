package com.yjkj.choose_function.model;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
@Table(name = "function")
public class Function extends Model {

	private static Delete delete=new Delete();
	private static Select select=new Select();
	private static Update updata=new Update(Function.class);
	
	
	
	public Function() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Function(String function_name, String function_content,
			String function_class, Boolean function_choose, int series) {
		super();
		Function_name = function_name;
		Function_content = function_content;
		Function_class = function_class;
		Function_choose = function_choose;
		Series = series;
	}
	/**
	 * 功能标题
	 */
	@Column(name = "Function_name",notNull=true)
	public String Function_name;
	
	/**
	 * 功能简介
	 */
	@Column(name = "Function_content",notNull=true)
	public String Function_content;
	
	/**
	 * 功能位置
	 */
	@Column(name = "Function_class",notNull=true,unique=true)
	public String Function_class;
	
	/**
	 * 该功能是否被选中
	 */
	@Column(name = "Function_choose",notNull=true)
	public boolean Function_choose;
	
	/**
	 * 该功能排序的序列号
	 */
	@Column(name = "Function_Series",notNull=true)
	public int Series;
	
	/**
	 * @return 数据库功能列表的大小
	 */
	public static int size(){
		return select.from(Function.class).count();
	}
	
	/**
	 * 清空功能列表
	 */
	public void deleteall(){
		delete.from(Function.class).execute();
	}
	
	/**
	 * @return 查询未选择的列表
	 */
	public static List<Function> unchoose(){
		return select.from(Function.class).where("Function_choose = ?", false).orderBy("Function_Series ASC").execute();
	}
	
	/**
	 * @return 查询已选择的列表
	 */
	public static List<Function> enchoose(){
		return select.from(Function.class).where("Function_choose = ?", true).orderBy("Function_Series ASC").execute();
	}
	
}
