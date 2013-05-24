package cop.ifree.test.autoservice.data.parameters;

public enum OrderOld {
	
	  ASCENDING("ascending"), 
	  DESCENDING("descending");
	  
	  private String text;

	  OrderOld(String text) {
	    this.text = text;
	  }

	  public String getText() {
	    return this.text;
	  }	
	  
	  public static OrderOld fromString(String text) {
		    if (text != null) {
		      for (OrderOld b : OrderOld.values()) {
		        if (text.equalsIgnoreCase(b.text)) {
		          return b;
		        }
		      }
		    }
		    return null;
	}
}