/**
 * 
 */
package com.johnwyles.skynetbot9000.commands;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.johnwyles.skynetbot9000.commands.quote.StockTicker;

/**
 *
 */
public class Quote extends Command {
    private static final Logger _log = LoggerFactory.getLogger(Quote.class);
    private static final SAXParserFactory _saxParserFactory = SAXParserFactory
	    .newInstance();

    // TODO: Break up to URI
    // TODO: Switch to JSON
    // TODO: Add bad ticker check
    private static final String _YQL_QUOTES_API_URL = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(__LIST_OF_TICKERS__)&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
    private static final String _YQL_QUOTELIST_API_URL = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quoteslist%20where%20symbol%20in%20(__LIST_OF_TICKERS__)&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

    @Override
    public String execute() {
	return "Please specify a stock ticker or list of tickers separated by a space (e.g. 'ZNGA' or 'ZNGA FB GRPN').";
    }

    @Override
    public String execute(String[] arguments) {
	// TODO: This needs to be cleaner / Different string replacement a la
	// URI Builder
	String stockTickersUrlString = "";
	try {

	    for (String ticker : arguments) {
		stockTickersUrlString += "\"" + ticker + "\", ";
	    }
	    stockTickersUrlString = stockTickersUrlString.substring(0,
		    stockTickersUrlString.length() - 2);
	    stockTickersUrlString = URLEncoder.encode(stockTickersUrlString,
		    "UTF-8");
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	}

	String yqlQuotesApiUrl = _YQL_QUOTES_API_URL.replaceAll(
		"__LIST_OF_TICKERS__", stockTickersUrlString);
	String yqlQuoteslistApiUrl = _YQL_QUOTELIST_API_URL.replaceAll(
		"__LIST_OF_TICKERS__", stockTickersUrlString);

	try {
	    SAXParser saxParser = _saxParserFactory.newSAXParser();
	    QuotesHandler quotesHandler = new QuotesHandler();
	    saxParser.parse(yqlQuotesApiUrl, quotesHandler);
	    saxParser.parse(yqlQuoteslistApiUrl, quotesHandler);

	    String quotesString = "";
	    for (Map.Entry<String, StockTicker> entry : quotesHandler.stockTickers
		    .entrySet()) {
		quotesString += entry.getValue().toString() + "\n";
	    }
	    quotesString = quotesString.trim();

	    return quotesString;
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	return null;
    }

    private static class QuotesHandler extends DefaultHandler {
	public Map<String, StockTicker> stockTickers = new HashMap<String, StockTicker>();

	private String _elementValue;
	private StockTicker _stockTicker;

	@Override
	public void startElement(String uri, String localName,
		String elementName, Attributes attributes) throws SAXException {
	    if (elementName.equalsIgnoreCase("quote")) {
		_stockTicker = StockTicker.getInstance(attributes
			.getValue("symbol"));
	    }
	}

	@Override
	public void characters(char[] ac, int i, int j) throws SAXException {
	    _elementValue = new String(ac, i, j);
	}

	@Override
	public void endElement(String s, String s1, String elementName)
		throws SAXException {
	    if (elementName.equalsIgnoreCase("quote")) {
		if (stockTickers.get(_stockTicker.getSymbol()) == null) {
		    stockTickers.put(_stockTicker.getSymbol(), _stockTicker);
		}
	    } else if (elementName.equalsIgnoreCase("Name")) {
		_stockTicker.setName(_elementValue);
	    } else if (elementName.equalsIgnoreCase("MarketCapitalization")) {
		_stockTicker.setMarketCapitalization(_elementValue);
	    } else if (elementName.equalsIgnoreCase("LastTradePriceOnly")) {
		_stockTicker.setLastTradePriceOnly(_elementValue);
	    } else if (elementName.equalsIgnoreCase("LastTradeTime")) {
		_stockTicker.setLastTradeTime(_elementValue);
	    } else if (elementName.equalsIgnoreCase("AverageDailyVolume")) {
		_stockTicker.setAverageDailyVolume(_elementValue);
	    } else if (elementName.equalsIgnoreCase("Volume")) {
		_stockTicker.setVolume(_elementValue);
	    } else if (elementName.equalsIgnoreCase("Change")) {
		_stockTicker.setChange(_elementValue);
	    } else if (elementName.equalsIgnoreCase("ChangeinPercent")) {
		_stockTicker.setChangeinPercent(_elementValue);
	    }
	}
    }
}
