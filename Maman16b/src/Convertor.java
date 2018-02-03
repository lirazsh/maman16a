import java.util.HashMap;

public class Convertor {
	
	private HashMap<String, Float> currencies;
	
	String from, to;
	Float amount;
	
	public Convertor(String from, String to, float amount) {
		
		this.from = from;
		this.to = to;
		this.amount = amount;
		
		currencies = new HashMap<String, Float>() {{ 
			put("usd", 3.5f);
			put("euro", 5.0f);
			put("pesso", 0.5f);
			put("gbp", 7.2f);
			put("ils", 1.0f);
		}};
		
	}
	
	private float toNIS(String curr, float amm) {
		
		return amm * currencies.get(curr);
		
	}
	
	private float fromNIS(String curr, float amm) {
		
		return amm / currencies.get(curr);
	}
	
	public float convert() {
		float val = 0;
		val = toNIS(from, amount);
		return fromNIS(to, val);		
	}

}
