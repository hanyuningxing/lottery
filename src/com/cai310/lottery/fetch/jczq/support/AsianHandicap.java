package com.cai310.lottery.fetch.jczq.support;

public enum AsianHandicap {
	
	o4("受四球",-4),
	o3751("受三半/四",-3.75),
	o3752("受三球半/四球",-3.75),
	o351("受三半",-3.5),
	o352("受三球半",-3.5),
	o3251("受三/三半",-3.25),
	o3252("受三球/三球半",-3.25),
	o3("受三球",-3),
	o2751("受两半/三",-2.75),
	o2752("受两球半/三球",-2.75),
	o251("受两半",-2.5),
	o252("受两球半",-2.5),
	o2251("受两/两半",-2.25),
	o2252("受两球/两球半",-2.25),
	o2("受两球",-2),	
	o1751("受球半/两",-1.75),
	o1752("受球半/两球",-1.75),
	o151("受球半",-1.5),
	o152("受一球半",-1.5),
	o1251("受一/球半",-1.25),
	o1252("受一球/球半",-1.25),
	o1("受一球",-1),
	o0751("受半/一",-0.75),
	o0752("受半球/一球",-0.75),
	o05("受半球",-0.5),
	o0251("受平/半",-0.25),
	o0252("受平手/半球",-0.25),
	
	a1("平手",0),
	
	u4("四球",4),
	u3751("三半/四",3.75),
	u3752("三球半/四球",3.75),
	u351("三半",3.5),
	u352("三球半",3.5),
	u3251("三/三半",3.25),
	u3252("三球/三球半",3.25),
	u3("三球",3),
	u2751("两半/三",2.75),
	u2752("两球半/三球",2.75),
	u251("两半",2.5),
	u252("两球半",2.5),
	u2251("两/两半",2.25),
	u2252("两球/两球半",2.25),
	u2("两球",2),	
	u1751("球半/两",1.75),
	u1752("球半/两球",1.75),
	u151("球半",1.5),
	u152("一球半",1.5),
	u1251("一/球半",1.25),
	u1252("一球/球半",1.25),
	u1("一球",1),
	u0751("半/一",0.75),
	u0752("半球/一球",0.75),
	u05("半球",0.5),
	u0251("平/半",0.25),
	u0252("平手/半球",0.25);
	private final String name;
	private final Double value;
	
	private AsianHandicap( String name,double value){
		this.name = name;
		this.value = value;
		
	}

	public String getName() {
		return name;
	}

	public Double getValue() {
		return value;
	}
	public static Double getAsianHandicapValue(String name){
		if(name==null)return null;
		AsianHandicap[] ops = AsianHandicap.values();
		for(AsianHandicap op:ops){
			if(op.name.equals(name))
				return op.value;
		}
		return null;
	}
	public static String getAsianHandicapName(Double value){
		AsianHandicap[] ops = AsianHandicap.values();
		for(AsianHandicap op:ops){
			if(op.value.equals(value))
				return op.name;
		}
		return null;
	}

	public static AsianHandicap getAsianHandicap(Double value) {
		AsianHandicap[] ops = AsianHandicap.values();
		for(AsianHandicap op:ops){
			if(op.value.equals(value))
				return op;
		}
		return null;
	}

}
