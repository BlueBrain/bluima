package ch.epfl.bbp.range;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateSequence implements Sequence<Date> {

	private Date value;

	public DateSequence(Date date) {
		this.value = date;
	}

	public Sequence<Date> next() {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(value);
		cal.add(Calendar.DATE, 1);
		return new DateSequence(cal.getTime());
	}

	@Override
	public Date value() {
		return value;
	}

	@Override
	public Sequence<Date> previous() {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(value);
		cal.roll(Calendar.DATE, 1);
		return new DateSequence(cal.getTime());
	}
}
