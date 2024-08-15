package database.mysql;

import misc.enums.CounterState;
import misc.objects.Counter;
import misc.objects.Time;

public class MySQL_Counter {
	private static final String CounterFields[] = {"counter_no", "active_time", "counter_state", "currentCart_no"};
	
	public static Counter selectCounter(int counter_no) {
		Object counter_result[][] = MySQL.select(CounterFields, "counter", "where counter_no=" + counter_no);
		
		return new Counter(
				(int)counter_result[0][0], 
				Time.parseTime((java.sql.Time)counter_result[0][1]), 
				CounterState.valueOf((String)counter_result[0][2]),
				(int)counter_result[0][3]
		);
	}
	public static Counter[] selectAllCounters() {
		Object counter_result[][] = MySQL.select(CounterFields, "counter", "");
		Counter counters[] = new Counter[counter_result.length];
		
		int counterNo, currentCart_id;
		Time activeTime;
		CounterState counterState;
		
		for(int i=0; i<counters.length; i++) {
			counterNo = (int)counter_result[i][0];
			activeTime = Time.parseTime((java.sql.Time)counter_result[i][1]);
			counterState = CounterState.valueOf((String)counter_result[i][2]);
			currentCart_id = (int)counter_result[0][3];
			
			counters[i] = new Counter(counterNo, activeTime, counterState, currentCart_id);
		}
		
		return counters;
	}
	public static Time selectCounterActiveTime(int counter_no) {
		Object result[][] = MySQL.select(new String[] {"active_time"}, "counter", "where counter_no=" + counter_no);
		return Time.parseTime((java.sql.Time)result[0][0]);
	}
	public static CounterState selectCounterState(int counter_no) {
		Object result[][] = MySQL.select(new String[]{"counter_state"}, "counter", "where counter_no=" + counter_no);
		return CounterState.valueOf((String)result[0][0]);
	}
	public static void updateCounter(Counter counter) {		
		Object values[] = {
				counter.getCounterNo(),
				counter.getActiveTime().toSQLTime(),
				counter.getCounterState().toString(),
				counter.getCurrentCartNo()
		};
		MySQL.update("counter", CounterFields, values, "where counter_no=" + (int)values[0]);
	}
	public static void updateCounterActiveTime(Counter counter) {
		counter.setActiveTime(new Time());
		
		String columns[] = {"active_time"};
		Object values[] = {counter.getActiveTime().toSQLTime()};
		
		MySQL.update("counter", columns, values, "where counter_no=" + counter.getCounterNo());
	}
	public static void updateCounterState(int counter_no, CounterState counter_state ) {
		MySQL.update("counter", new String[] {"counter_state"}, new Object[] {counter_state.toString()}, "where counter_no=" + counter_no);
	}
	public static void updateCounterCurrentCartNo(Counter counter) {
		int
		counter_no = counter.getCounterNo(),
		currentCart_no = MySQL.nextUID("currentCart_no", "counter where counter_no=" + counter_no);
		MySQL.update("counter", new String[] {"currentCart_no"}, new Object[] {currentCart_no}, "where counter_no=" + counter_no);
	}
}
