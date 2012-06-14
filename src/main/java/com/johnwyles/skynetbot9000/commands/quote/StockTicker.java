package com.johnwyles.skynetbot9000.commands.quote;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.johnwyles.skynetbot9000.commands.Quote;

public class StockTicker {
	private static final Logger _log = LoggerFactory.getLogger(Quote.class);

	private String _name;
	private String _symbol;
	private String _marketCapitalization;
	private String _lastTradePriceOnly;
	private String _lastTradeTime;
	private String _change;
	private String _changeinPercent;
	private String _volume;
	private String _averageDailyVolume;

	private static Map<String, StockTicker> _instanceMap = new HashMap<String, StockTicker>();

	public StockTicker(String symbol) {
		_symbol = symbol;
	}
	
	public static StockTicker getInstance(String symbol) {
		if (_instanceMap.get(symbol) == null) {
			_log.debug("SYMBOL: " + symbol);
			_instanceMap.put(symbol, new StockTicker(symbol));
		}

		return _instanceMap.get(symbol);
	}

	public String toString() {
		// Zynga, Inc. (ZNGA) [$3.713B] [4:00pm]: $5.05 / $+0.07 (+1.31%) [21507137/19780600]
		String preformattedString = "%1$s (%2$s) [$%3$s] [%4$s]: $%5$s / $%6$s (%7$s) [%8$s / %9$s]";
		return String.format(preformattedString, getName(), getSymbol(), getMarketCapitalization(), getLastTradeTime(), getLastTradePriceOnly(), getChange(), getChangeinPercent(), getVolume(), getAverageDailyVolume());
	}

	public String getSymbol() {
		return _symbol;
	}

	public void setSymbol(String symbol) {
		_symbol = symbol;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getMarketCapitalization() {
		return _marketCapitalization;
	}

	public void setMarketCapitalization(String marketCapitalization) {
		this._marketCapitalization = marketCapitalization;
	}
	
	public String getLastTradePriceOnly() {
		return _lastTradePriceOnly;
	}

	public void setLastTradePriceOnly(String lastTradePriceOnly) {
		_lastTradePriceOnly = lastTradePriceOnly;
	}

	public String getLastTradeTime() {
		return _lastTradeTime;
	}

	public void setLastTradeTime(String lastTradeTime) {
		_lastTradeTime = lastTradeTime;
	}

	public String getChange() {
		return _change;
	}

	public void setChange(String change) {
		_change = change;
	}

	public String getChangeinPercent() {
		return _changeinPercent;
	}

	public void setChangeinPercent(String changeinPercent) {
		_changeinPercent = changeinPercent;
	}

	public String getVolume() {
		return _volume;
	}

	public void setVolume(String volume) {
		_volume = volume;
	}

	public String getAverageDailyVolume() {
		return _averageDailyVolume;
	}

	public void setAverageDailyVolume(String averageDailyVolume) {
		_averageDailyVolume = averageDailyVolume;
	}
}
