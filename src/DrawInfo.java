import java.awt.Color;
import java.io.Serializable;

class DrawInfo implements Serializable{
	private int x;
	private int y;
	private int x1;
	private int y1;
	private int type;
	private Color color;
	private boolean fill;
	
	
	//
	public DrawInfo(int x, int y, int x1, int y1, int type, Color color, boolean fill){
		this.x = x;
		this.y = y;
		this.x1 = x1;
		this.y1 = y1;
		this.type = type;
		this.color = color;
		this.fill = fill;
		
	}
	
	//
	public void setX(int x){
		this.x = x;
	}
		
	//
	public void setY(int y){
		this.y = y;
	}
	
	//
	public void setX1(int x1){
		this.x1 = x1;		
	}
	
	//
	public void setY1(int y1){
		this.y1 = y1;
	}
	
	//
	public void setType(int type){
		this.type = type;
	}
	
	//
	public void setColor(Color color){
		this.color = color;
	}
	
	//
	public void setFill(boolean fill){
		this.fill = fill;
	}
	
	//
	public int getX(){
		return x;
	}
	
	//
	public int getY(){
		return y;
	}
	
	//
	public int getX1(){
		return x1;
	}
	
	//
	public int getY1(){
		return y1;
	}
	
	//
	public int getType(){
		return type;
	}
	
	//
	public Color getColor(){
		return color;
	}
	
	//
	public boolean getFill(){
		return fill;
	}
}
