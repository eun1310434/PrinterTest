import java.awt.CheckboxMenuItem;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;



class Round20_Ex05_Sub extends Frame implements MouseListener ,MouseMotionListener,ItemListener ,ActionListener{
	
	private MenuBar mb = new MenuBar();
	
	private Menu file = new Menu("FILE");
	private MenuItem fnew = new MenuItem("NEW");
	private MenuItem fopen = new MenuItem("OPEN");
	private MenuItem fsave = new MenuItem("SAVE");
	private MenuItem fexit = new MenuItem("EXIT");
	
	private Menu option = new Menu("OPTION");
	private Menu odraw = new Menu("DRAW");
	private CheckboxMenuItem odpen = new CheckboxMenuItem("PEN", true);
	private CheckboxMenuItem odline = new CheckboxMenuItem("LINE");
	private CheckboxMenuItem odrect = new CheckboxMenuItem("RECT");
	private CheckboxMenuItem odcircle = new CheckboxMenuItem("CIRCLE");
	private Menu ocolor = new Menu("COLOR");
	private CheckboxMenuItem ocred = new CheckboxMenuItem("RED");
	private CheckboxMenuItem ocblue = new CheckboxMenuItem("BLUE");
	private CheckboxMenuItem ocgreen = new CheckboxMenuItem("GREEN");
	private Menu oprop = new Menu("PROPERTY");
	private CheckboxMenuItem opdraw = new CheckboxMenuItem("DRAW", true);
	private CheckboxMenuItem opfill = new CheckboxMenuItem("FILL");
	
	private int x;
	private int y;
	private int x1;
	private int y1;
	private int dist;
	
	private Color color;
	private boolean fill;
	
	// 도형을 저장하기 위한 객체
	private Vector vc = new Vector();
	
	//
	public Round20_Ex05_Sub(){
		super("그림판");
		this.init();
		this.start();
		this.setVisible(true);
		//this.setBackground(new Color(0.0f, 0.0f, 2.0f));
		this.setSize(500, 500);
		this.createBufferStrategy(2);
	}
	
	public void init(){
		this.setMenuBar(mb);
		mb.add(file);
		file.add(fnew);
		file.addSeparator();
		file.add(fopen);
		file.add(fsave);
		file.addSeparator();
		file.add(fexit);
		
		mb.add(option);
		option.add(odraw);
		odraw.add(odpen);
		odraw.add(odline);
		odraw.add(odrect);
		odraw.add(odcircle);
		option.addSeparator();
		option.add(ocolor);
		ocolor.add(ocred);
		ocolor.add(ocgreen);
		ocolor.add(ocblue);
		option.addSeparator();		
		option.add(oprop);
		oprop.add(opdraw);
		oprop.add(opfill);
	}		
	
	//
	public void start(){
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		odpen.addItemListener(this);
		odline.addItemListener(this);
		odrect.addItemListener(this);
		odcircle.addItemListener(this);
		
		fnew.addActionListener(this);
		fopen.addActionListener(this);
		fsave.addActionListener(this);
		fexit.addActionListener(this);
		
		opdraw.addItemListener(this);
		opfill.addItemListener(this);
		
		//창닫기
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});		

		
		/*
		//전체화면
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		gd.setFullScreenWindow(this);
        if (!gd.isWindowTranslucencySupported(TRANSLUCENT)) {
            System.err.println(
                "Translucency is not supported");
                System.exit(0);
        }

*/
		
			
	}
	
	
	public void paint(Graphics g){
		
		
		//현재 마우스가 드래그된 지점까지의 그림을 표현
		Color c = new Color(ocred.getState()   ? 255 : 0
                                        ,ocgreen.getState() ? 255 : 0
                                        ,ocblue.getState()  ? 255 : 0);
		
		g.setColor(c);
		
		if (dist == 1 || dist == 0){
			g.drawLine(x, y, x1, y1);
		}else if(dist == 2){
			if(fill){
				g.fillRect(x, y, x1 - x, y1 - y);
			}
			else {
				g.drawRect(x, y, x1 - x, y1 - y);
			}
		}else if(dist == 3){
			if(fill){
				g.fillOval(x, y, x1 - x, y1 - y);
			}
			else{
				g.drawOval(x, y, x1 - x, y1 - y);
			}
		}
		
		
        
		for(int i = 0; i < vc.size(); i++){
			DrawInfo imsi = (DrawInfo) vc.elementAt(i);
			g.setColor(imsi.getColor());
			if(imsi.getType() == 1 || imsi.getType() == 0){
				g.drawLine(imsi.getX(), imsi.getY(), imsi.getX1(), imsi.getY1());
			}else if (imsi.getType() == 2){
				if(imsi.getFill()){
					g.fillRect(imsi.getX(), imsi.getY(), imsi.getX1() - imsi.getX(), imsi.getY1() - imsi.getY());
				}else{
					g.drawRect(imsi.getX(), imsi.getY(), imsi.getX1() - imsi.getX(), imsi.getY1() - imsi.getY());
				}
			}else if (imsi.getType() == 3){
				if(imsi.getFill()){
					g.fillOval(imsi.getX(), imsi.getY(), imsi.getX1() - imsi.getX(), imsi.getY1() - imsi.getY());
				}else{
					g.drawOval(imsi.getX(), imsi.getY(), imsi.getX1() - imsi.getX(), imsi.getY1() - imsi.getY());
				}
			}		
		}


		g.dispose();
		

	}
	
	public void mouseClicked(MouseEvent e){
		
	}
	
	//마우스를 누른 지점을 시작점으로 등록
	public void mousePressed(MouseEvent e){
		x = e.getX();
		y = e.getY();
	}
	
	//마우스를 뗀 지점을 끝점으로 등록한다. repaint()메서드를 호출하여 다시 그림을 그린다.
	public void mouseReleased(MouseEvent e){
		x1 = e.getX();
		y1 = e.getY();
		
		Color c = new Color(ocred.getState()   ? 255 : 0
		                   ,ocgreen.getState() ? 255 : 0
			        ,ocblue.getState()  ? 255 : 0 );
		
		DrawInfo di = new DrawInfo(x, y, x1, y1, dist, c, opfill.getState());
		vc.add(di);
		this.repaint();
	}
	
	public void mouseEntered(MouseEvent e){
		
	}
	
	public void mouseExited(MouseEvent e){
		
	}
	
	public void mouseMoved(MouseEvent e){
		
	}
	
	/*
	 * 마우스를 대르그할 때에는 움직이는 지점까지의 그림이 그때 그때 표현되어야 하기 때문에
	 * 해당 그림을 그려준다.
	 */
	public void mouseDragged(MouseEvent e){
		x1 = e.getX();
		y1 = e.getY();
		
		//PEN이 선택 되었을때 모든 움직임을 벡터에 저장한다.
		if (dist == 0){
			Color c = new Color(ocred.getState()   ? 255 : 0
			                   ,ocgreen.getState() ? 255 : 0
			                   ,ocblue.getState()  ? 255 : 0 );

			DrawInfo di = new DrawInfo(x, y, x1, y1, dist, c, opfill.getState());
			vc.add(di);
			x = x1;
			y = y1;
		}			
		this.repaint();
	}
	
	public void itemStateChanged(ItemEvent e){
		if(e.getSource() == odpen){
			dist = 0;
			odpen.setState(true);
			odline.setState(false);
			odrect.setState(false);
			odcircle.setState(false);
		}else if(e.getSource() == odline){
			dist = 1;
			odpen.setState(false);
			odline.setState(true);
			odrect.setState(false);
			odcircle.setState(false);						
		}else if(e.getSource() == odrect){
			dist = 2;
			odpen.setState(false);
			odline.setState(false);
			odrect.setState(true);
			odcircle.setState(false);						
		}else if(e.getSource()== odcircle){
			dist = 3;
			odpen.setState(false);
			odline.setState(false);
			odrect.setState(false);
			odcircle.setState(true);			
		}else if(e.getSource()== opdraw){
			opdraw.setState(true);
			opfill.setState(false);			
		}else if(e.getSource()== opfill){
			opdraw.setState(false);
			opfill.setState(true);			
		}
	}
	
	
	public void actionPerformed(ActionEvent e){
		
		if (e.getSource() == fnew){
			vc.clear();
			x = 0;
			y = 0;
			x1 = 0;
			y1 = 0;
			dist = 0;
			this.repaint();
						
		}else if(e.getSource() == fopen){
			//열기
			FileDialog fdlg = new FileDialog(this, "열기", FileDialog.LOAD);
			fdlg.setVisible(true);
			String dir = fdlg.getDirectory();
			String file = fdlg.getFile();
			
			if(dir == null || file == null) return;
			
			try{
				ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File(dir, file))));
				vc = (Vector)ois.readObject();
				ois.close();				
			}catch(IOException ee){				
			}catch(ClassNotFoundException ee){}
					
		}else if(e.getSource() == fsave){
			//저장
			FileDialog fdlg = new FileDialog(this,"저장",FileDialog.SAVE);
			fdlg.setVisible(true);
			String dir = fdlg.getDirectory();
			String file = fdlg.getFile();
			
			if (dir == null || file == null) return;
			
			try{
				ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File(dir, file))));
				oos.writeObject(vc);
				oos.close();
			}catch(IOException ee){			
			}			
		}else if(e.getSource() == fexit){
			System.exit(0);
		}
	}
}
