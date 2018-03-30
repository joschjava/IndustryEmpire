package gui.objects;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import game.Game;
import game.TickListener;
import javafx.scene.control.Label;

public class DateLabel extends Label implements TickListener{
	private LocalDate localDate = LocalDate.of(1, 1, 1);
	private String dateStr;
	private DateTimeFormatter daysMonthsFormat = DateTimeFormatter.ofPattern("dd MMM");
	private DateTimeFormatter yearFormat = DateTimeFormatter.ofPattern("u");
	private String yearStr;
	
	public DateLabel(){
		super();
		Game.getInstance().addListener(this);
	}

	@Override
	public void onTick() {
		localDate = localDate.plusDays(1);
		dateStr = localDate.format(daysMonthsFormat);
		yearStr = localDate.format(yearFormat);
		setText(dateStr+"\nYear "+yearStr);
	}

	@Override
	public void onChgDate(long ticks) {
		localDate = LocalDate.of(1, 1, 1).plusDays(ticks);
	}

	
}
