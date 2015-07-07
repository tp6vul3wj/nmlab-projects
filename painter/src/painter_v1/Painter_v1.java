package painter_v1;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


class Painter extends JFrame implements ActionListener {
   //private Container c = getContentPane();
    
    // set menu bar
    private String menuBar[]={"檔案","編輯","檢視","說明"};
    private String menuItem[][]={
        {"開新檔案","開啟舊檔","儲存檔案","另存新檔","結束"},
        {"復原","重複","剪下","複製","貼上"},
        {"工具箱","色塊"},
        {"關於小畫家(A)"}
    };
    private JMenuItem jMenuItem[][]=new JMenuItem[menuBar.length][5];
    private JMenu jMenu[];
    private JCheckBoxMenuItem jCheckBoxMenuItem[] = new JCheckBoxMenuItem[menuItem[2].length];
    private int i,j; // for for loop set
    private int draw_panel_width=700,draw_panel_height=700; // for 
    
    private JPanel jPanel[]=new JPanel[5];
    
    
    JToolBar jToolBar; // setting tool
    private JToggleButton jToggleButton[];
    private ButtonGroup buttonGroup;
    private Icon tool[]=new ImageIcon[10];
    private String toolname[]={"painter\\tb0.png","painter\\tb1.png","painter\\tb2.png","painter\\tb3.png","painter\\tb4.png","painter\\tb5.png","painter\\tb6.png","painter\\tb7.png"};
    
    JToolBar jToolBar2; // setting tool width
    private JToggleButton jToggleButton2[];
    private ButtonGroup buttonGroup2;
    private Icon tool2[]=new ImageIcon[8];
    private String toolname2[]={"painter\\tb20.png","painter\\tb21.png","painter\\tb22.png","painter\\tb23.png","painter\\tb24.png","painter\\tb25.png","painter\\tb26.png","painter\\tb27.png"};
    private Stroke stroke[]={new BasicStroke(1f),new BasicStroke(2f),new BasicStroke(3f),new BasicStroke(4f),new BasicStroke(5f),new BasicStroke(6f),new BasicStroke(7f),new BasicStroke(8),};
    
    JToolBar jToolBar3; // colorbar
    private JButton jButton3[];
    private ButtonGroup buttonGroup3;
    private Color colorbar[]={Color.BLACK,Color.WHITE,Color.DARK_GRAY,Color.RED,Color.MAGENTA,Color.PINK,Color.ORANGE,Color.YELLOW,Color.GREEN,Color.BLUE,Color.CYAN,Color.BLACK};
    
    JToolBar jToolBar4; //噴漆
    private JToggleButton jToggleButton4[];
    private ButtonGroup buttonGroup4;
    private Icon tool4[]=new ImageIcon[8];
    private String toolname4[]={"D:\\painter\\tb20.png","D:\\painter\\tb21.png","D:\\painter\\tb22.png","D:\\painter\\tb23.png","D:\\painter\\tb24.png","D:\\painter\\tb25.png","D:\\painter\\tb26.png","D:\\painter\\tb27.png"};
    private Stroke stroke2[]={new BasicStroke(1f),new BasicStroke(2f),new BasicStroke(3f),new BasicStroke(4f),new BasicStroke(5f),new BasicStroke(6f),new BasicStroke(7f),new BasicStroke(8),};
    
    private DrawPanel drawPanel=new DrawPanel();
    
    // setting for action
    int drawMethod=0; // default for pencil
    String filename;
    

    // for drawing recordint drawType[] = new int[500];
    int drawType[] = new int[500]; 
    int[] max = new int[500];  // pen
    int cur,tag=0;
    
    Point p1,p2;   
    Point pPen[] = new Point[5000];    
    Point pPenOld[][] = new Point[500][5000];   
    Point pStart[] = new Point[500];   
    Point pEnd[] = new Point[500];   
    Point tStart[] = new Point[500]; // text
    boolean isDraw = false;   
    int count  = 0; // for shape
    int count3 = 0; // for text
    int count4 = 0; // for 寬度
    int[] count5 = new int [5000]; 
    int count6 = 0; // for 顏色
    int[] count7 = new int[5000]; 
    
    String inputText[] = new String[500]; // text
    int sizeWord[] = new int [500];
    Font fontWord[] = new Font[500];
    Color colorWord[] = new Color[500];

    PaintMouse MHandler1 = new PaintMouse();   
    PaintMouseMotion MHandler2 = new PaintMouseMotion(); 
    
    boolean undo;
    
    // function_1--constructor
    
    public Painter(){
        super();   
        initializeComponent();  

    }
    
    private void initializeComponent(){  
        // menubar setting
        JMenuBar bar = new JMenuBar();
        jMenu=new JMenu[menuBar.length];
        for(i=0;i<menuBar.length;i++){
            jMenu[i] = new JMenu(menuBar[i]);
            bar.add(jMenu[i]);
        }       
      
        for(i=0;i<menuItem.length;i++){
            for(j=0;j<menuItem[i].length;j++){
                
                //normal menubar setting
                if(i!=2){ 
                    jMenuItem[i][j] = new JMenuItem(menuItem[i][j]);
                    jMenuItem[i][j].addActionListener(this);
                    jMenu[i].add(jMenuItem[i][j]);
                }
                
                // checkbox menubar setting
                else{
                    jCheckBoxMenuItem[j] = new JCheckBoxMenuItem(menuItem[i][j]);
                    jCheckBoxMenuItem[j].addActionListener(this);
                    jCheckBoxMenuItem[j].setSelected( true );
                    jMenu[i].add(jCheckBoxMenuItem[j]);
                }
            }
        }
        this.setJMenuBar( bar );
        setLayout( new BorderLayout() );
        
        // toolbox setting
        for(i=0;i<5;i++)
            jPanel[i]=new JPanel();

        
        // set icon for drawing tool
        buttonGroup = new ButtonGroup(); // same group just can enable one button in them
        jToolBar=new JToolBar("工具箱",JToolBar.VERTICAL);
        jToggleButton=new JToggleButton[toolname.length];

        for(i=0;i<toolname.length;i++){
            tool[i] = new ImageIcon(toolname[i]);
            jToggleButton[i] = new JToggleButton(tool[i]);
            UIManager.put("ToggleButton.background",Color.white);
            //UIManager.put("ToggleButton.border",new javax.swing.border.SoftBevelBorder(BevelBorder.RAISED));
            jToggleButton[i].addActionListener( this );
            jToggleButton[i].setFocusable( false ); //--> 點選的時候的小框框
            buttonGroup.add(jToggleButton[i]);
            jToolBar.add(jToggleButton[i]);
        }
        
        jToggleButton[0].setSelected(true); // default tool --> set pen
        jToolBar.setLayout( new GridLayout( 2, 2, 2, 2 ) ); // format of toolbox
        jPanel[2].add(jToolBar);
        jToolBar.setFloatable(false);// cannot move
        
        // set icon for width
        buttonGroup2 = new ButtonGroup(); // same group just can enable one button in them
        jToolBar2=new JToolBar("工具箱",JToolBar.VERTICAL);
        jToggleButton2=new JToggleButton[toolname2.length];
        
        for(i=0;i<toolname2.length;i++){
            tool2[i] = new ImageIcon(toolname2[i]);
            jToggleButton2[i] = new JToggleButton(tool2[i]);
            //UIManager.put("ToggleButton.border",new javax.swing.border.SoftBevelBorder(BevelBorder.RAISED));
            jToggleButton2[i].addActionListener( this );
            jToggleButton2[i].setFocusable( false ); //--> 點選的時候的小框框
            buttonGroup2.add(jToggleButton2[i]);
            jToolBar2.add(jToggleButton2[i]);

        }
        
        jToggleButton2[0].setSelected(true); // default tool --> set pen
        jToolBar2.setLayout( new GridLayout( 2, 2, 2, 2 ) ); // format of toolbox
        jPanel[4].setLayout(new FlowLayout(FlowLayout.CENTER));
        jPanel[4].add(jToolBar2);
        jToolBar2.setFloatable(false);// cannot move
        
        // colorbar
        int numberofcolor=colorbar.length;
        buttonGroup3 = new ButtonGroup(); // same group just can enable one button in them
        jToolBar3=new JToolBar("調色盤",JToolBar.VERTICAL);
        jButton3=new JButton[numberofcolor];
        
        for(i=0;i<numberofcolor;i++){
            jButton3[i] = new JButton();
            UIManager.put("Button.background",colorbar[i]);
            jButton3[i].setPreferredSize(new java.awt.Dimension(40,40));
            jButton3[i].addActionListener( this );
            jButton3[i].setFocusable( true ); //--> 點選的時候的小框框
            buttonGroup3.add(jButton3[i]);
            jToolBar3.add(jButton3[i]);

        }

        jToolBar3.setLayout( new GridLayout( 2, 2, 2, 2 ) ); // format of toolbox
        jPanel[3].setLayout(new FlowLayout(FlowLayout.LEFT));
        jPanel[3].add(jToolBar3);
        jToolBar3.setFloatable(false);// cannot move
        
        
         // set icon for 噴漆
        buttonGroup4 = new ButtonGroup(); // same group just can enable one button in them
        jToolBar4=new JToolBar("工具箱",JToolBar.VERTICAL);
        jToggleButton4=new JToggleButton[toolname4.length];
        
        for(i=0;i<toolname4.length;i++){
            tool4[i] = new ImageIcon(toolname4[i]);
            jToggleButton4[i] = new JToggleButton(tool4[i]);
            //UIManager.put("ToggleButton.border",new javax.swing.border.SoftBevelBorder(BevelBorder.RAISED));
            jToggleButton4[i].addActionListener( this );
            jToggleButton4[i].setFocusable( false ); //--> 點選的時候的小框框
            buttonGroup4.add(jToggleButton4[i]);
            jToolBar4.add(jToggleButton4[i]);

        }
        
        jToggleButton4[0].setSelected(true); // default tool --> set pen
        jToolBar4.setLayout( new GridLayout( 2, 2, 2, 2 ) ); // format of toolbox
        jPanel[1].setLayout(new FlowLayout(FlowLayout.LEFT));
        jPanel[1].add(jToolBar4);
        jToolBar4.setFloatable(false);// cannot move
        
        // drawingpane    
        drawPanel.setBounds(new Rectangle(2, 2, draw_panel_width-2, draw_panel_height-2));
        //drawPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2));
        drawPanel.addMouseListener(MHandler1);   
        drawPanel.addMouseMotionListener(MHandler2);   
        drawPanel.setLayout(null);
        add(drawPanel);
        
        // window setting
        Border eborder= BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        jPanel[1].setBorder(BorderFactory.createTitledBorder(eborder,"   SPRAY PAINT   ",TitledBorder.LEFT,TitledBorder.TOP));
        jPanel[2].setBorder(BorderFactory.createTitledBorder(eborder,"   DRAWING TOOL   ",TitledBorder.LEFT,TitledBorder.TOP));
        jPanel[3].setBorder(BorderFactory.createTitledBorder(eborder,"   COLORBAR   ",TitledBorder.LEFT,TitledBorder.TOP));
        jPanel[4].setBorder(BorderFactory.createTitledBorder(eborder,"   WIDTH OF TOOL   ",TitledBorder.LEFT,TitledBorder.TOP));
        jPanel[0].setLayout( new BorderLayout() );
        jPanel[0].add(jPanel[3],BorderLayout.EAST);
        jPanel[0].add(jPanel[2],BorderLayout.WEST);
        jPanel[0].add(jPanel[4],BorderLayout.CENTER);

        
        add(jPanel[0],BorderLayout.NORTH);
        add(drawPanel,BorderLayout.CENTER);
        //add(jPanel[3],BorderLayout.SOUTH);
        
        
        // set painter interface
        setSize(draw_panel_width,draw_panel_height);
        setTitle("Painter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);  
        //show();
        
 } 
    
    public class DrawPanel extends JPanel {

        @Override
        public void paintComponent(Graphics graphO){          
            Graphics2D graph=(Graphics2D)graphO;
            if(!isDraw) return;   
            graph.setPaint(Color.WHITE);
            graph.fill(new Rectangle2D.Double(0,0,draw_panel_width,draw_panel_height));
            Point tmp_p[] = new Point[2];
            
            //System.out.println("repaint() : "+count);
            // 畫回之前畫的
            for(int i=0;i<count;i++) // line & rec 
            {    
                tmp_p[0]=pStart[i];
                tmp_p[1]=pEnd[i];
                if(drawType[i] == 0 || drawType[i] == 1) 
                    drawing(graph,drawType[i],pPenOld[i],max[i],count5[i],count7[i]);
                else drawing(graph,drawType[i],tmp_p,0,count5[i],count7[i]);   
            }   


            for(int i=0;i<count3;i++)  //text   
            {   
                Font f = new Font("TimesRoman",Font.PLAIN,40);
                graph.setFont(f);
                graph.setColor(colorWord[i]);
                graph.drawString(inputText[i],tStart[i].x+5,tStart[i].y+10);   
            }  
            

            
            // 現在在畫的  
            if(drawMethod == 0 || drawMethod == 1||drawMethod==4||drawMethod==5||drawMethod==6||drawMethod==7)   
            {   
                tmp_p[0]=p1;
                tmp_p[1]=p2;
                if(drawMethod == 0 || drawMethod == 1) 
                {
                    if(drawMethod == 1) drawing(graph,1,pPen,cur,count4,1);
                    else drawing(graph,0,pPen,cur,count4,count6);  
                }
                else drawing(graph,drawMethod,tmp_p,0,count4,count6);   
                pStart[count] = p1;   
                pEnd[count] = p2;   
                drawType[count] = drawMethod;   
            }   
            
            
            else if(drawMethod == 3 && tag == 1) 
            {
                tmp_p[0]=p1;
                tmp_p[1]=p2;
                Font f = new Font("TimesRoman",Font.PLAIN,30);
                graph.setFont(f);
                graph.setColor(colorbar[count6]);
                colorWord[count3]=colorbar[count6];
                drawing(graph,drawMethod,tmp_p,0,0,0); 
            }
/*
            //else if (drawMethod==2)
            //{
    
                Point b=new Point();   
                pEnd[count]=b;
                graph.copyArea(p1.x, p1.y, p2.x-p1.x, p2.y-p1.y, 50, 50);
            ///}
  */          
            
        }
     
}  
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // togglebutton1_鉛筆\橡皮擦\直線\顏料\文字\選取
        for(i=0;i<toolname.length;i++)
        {
            if(e.getSource()==jToggleButton[i])
                drawMethod=i;
            
        }
        
        // togglebutton2_寬度選取
        
        for(i=0;i<toolname2.length;i++)
        { 
            if(e.getSource()==jToggleButton2[i])
                count4=i;
        }
        
        // togglebutton_color
        for(i=1;i<colorbar.length;i++)
        {
            if(e.getSource()==jButton3[i])
            {
                count6=i-1;
            }

        }
        
        
        if(e.getSource()==jMenuItem[0][0]){ //開新檔案
            new Painter();

        }
        else if(e.getSource()==jMenuItem[0][1]){//開啟舊檔
            FileDialog fileDialog = new FileDialog( new Frame() , "請指定一個檔名", FileDialog.LOAD);
            fileDialog.show();
            filename = fileDialog.getDirectory()+fileDialog.getFile();
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Image image = toolkit.getImage(filename);
            Graphics go=getGraphics();
            Graphics2D g=(Graphics2D)go; 
            g.drawImage(image, 50, 300, this);

        }
        
        else if(e.getSource()==jMenuItem[0][2]){//儲存檔案
            FileDialog fileDialog = new FileDialog( new Frame() , "請指定一個檔名", FileDialog.SAVE );
            fileDialog.show();
            if(fileDialog.getFile()==null) return;
            filename = fileDialog.getDirectory()+fileDialog.getFile();
        }
        
        else if(e.getSource()==jMenuItem[0][3]){//另存新檔
            
            

        }
        
        else if(e.getSource()==jMenuItem[0][4]){//離開
            System.exit(0);
        }   
        
        else if(e.getSource()==jMenuItem[1][0]){//復原
            
            count=count-1;
            repaint();
            //System.out.println("repaint() : ");
            //Graphics tmp=getGraphics();
            //tmp.setColor(Color.WHITE);
            //tmp.drawLine(0,0,1,1);
            

        }
    }
    
    class PaintMouse extends MouseAdapter{  
        @Override
        public void mousePressed (MouseEvent e)
        {
            isDraw = false;   
            p1 = e.getPoint();   
            cur=0;   
            if(drawMethod == 0||drawMethod == 1) pPen[cur] = p1; 
            if(drawMethod == 3) tag = 1; 
                        
      /*      else if(drawMethod==2)
            {
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Image image = toolkit.getImage("D:\\painter\\tb20.png");
                Graphics go=getGraphics();
                Graphics2D g=(Graphics2D)go; 
                g.drawImage(image, p1.x, p1.y, drawPanel);
            }*/

        }
          
        @Override
        public void mouseReleased(MouseEvent e) 
        {
            
            if( drawMethod == 0||drawMethod == 1||drawMethod == 4|| drawMethod == 5||drawMethod==6||drawMethod==7)
            {
                if(isDraw)
                {
                    System.arraycopy(pPen, 0, pPenOld[count], 0, cur);
                    max[count] = cur;   
                    count5[count]=count4;
                    if(drawMethod == 1) 
                        count7[count]=1;
                    else 
                        count7[count]=count6;
                    count++;
                }  
            }

           else if(isDraw && drawMethod == 3 && p2.x>p1.x && p2.y>p1.y)
           {   
               
               
                tag = 0;
                inputText[count3] = JOptionPane.showInputDialog("Please input the text.");   
                tStart[count3] = p1;   
                repaint();   
                count3++;   
            }   
            
        }
        
    }
    
     class PaintMouseMotion extends MouseMotionAdapter{  
             
        @Override
        public void mouseDragged (MouseEvent e)
        {
            p2 = e.getPoint();   
            isDraw = true;   
            if( drawMethod== 0 || drawMethod== 1)   
            {   
                cur++;   
                pPen[cur] = p2;   
            }  
            repaint(); 
        }         
     }

     
   private BufferedImage getImage()   
    {   
        int w = drawPanel.getWidth();   
        int h = drawPanel.getHeight();   
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);   
        Graphics2D g2 = bi.createGraphics();  
        //g2.setPaint(Color.white);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);   
        drawPanel.paint(g2);   
        g2.dispose();   
        return bi;   
    }
       
   public void drawing(Graphics2D graph,int type,Point tmp_p[],int max,int tmp, int tmp2){
       
       int x1=tmp_p[0].x;
       int y1=tmp_p[0].y;
       int x2=tmp_p[1].x;
       int y2=tmp_p[1].y;
       int recWidth, recHeith;
       
       
       
       
       if (type==0) // pen
       {
           for(i=0;i<max-1;i++) 
           {
               graph.setStroke(stroke[tmp]);
               graph.setColor(colorbar[tmp2]);
               graph.drawLine(tmp_p[i].x, tmp_p[i].y, tmp_p[i+1].x, tmp_p[i+1].y);
           }   
       }
       
       else if (type==1) // eraser
       {
           for(i=0;i<max-1;i++) 
           {
               graph.setStroke(stroke[tmp]);
               graph.setColor(colorbar[1]);
               graph.drawLine(tmp_p[i].x, tmp_p[i].y, tmp_p[i+1].x, tmp_p[i+1].y);
           }   
       }
       
       else if(type==2) // brush
       {
           
       }
       
       else if(type == 3) // text
       {
            graph.setStroke(stroke[0]);
            graph.setColor(colorbar[0]);
            graph.drawRect(x1, y1, x2-x1, y2-y1); 
       }
              
                
        else if(type == 4) // 直線
        {
            graph.setStroke(stroke[tmp]);
            graph.setColor(colorbar[tmp2]);
            //graph.fillRect(Color.pink);
            graph.drawLine(x1,y1,x2,y2); 
        }
               
        
        else if(type == 5) // 矩形
        {   
            graph.setStroke(stroke[tmp]);
            graph.setColor(colorbar[tmp2]);
            //graph.fillRect(Color.pink);
            if(x1>x2)
            {   
                recWidth = x1 - x2;   
                x1 = x2;   
            }           
            else recWidth = x2 - x1;   
            if(y1>y2)
            {   
                 recHeith = y1 - y2;   
                 y1 = y2;   
            }   
            else recHeith = y2 - y1;   
            graph.drawRect(x1,y1,recWidth,recHeith);   
        }   
        
        else if(type == 6) // 圓形
        {   
            graph.setStroke(stroke[tmp]);
            graph.setColor(colorbar[tmp2]);
            if(x1>x2)
            {   
                recWidth = x1 - x2;   
                x1 = x2;   
            }           
            else recWidth = x2 - x1;   
            if(y1>y2)
            {   
                 recHeith = y1 - y2;   
                 y1 = y2;   
            }   
            else recHeith = y2 - y1;   
            graph.drawOval(x1,y1,recWidth,recHeith);   
        } 
          
        
        else if(type == 7) // 圓方
        {   
            graph.setStroke(stroke[tmp]);
            graph.setColor(colorbar[tmp2]);
            if(x1>x2)
            {   
                recWidth = x1 - x2;   
                x1 = x2;   
            }           
            else recWidth = x2 - x1;   
            if(y1>y2)
            {   
                 recHeith = y1 - y2;   
                 y1 = y2;   
            }   
            else recHeith = y2 - y1;   
            
            graph.drawRoundRect(x1, y1, recWidth, recHeith, 30, 30);
   
        } 
    }  

}
    

    public class Painter_v1 {
        public static void main(String[] args) {       
        new Painter();
        // app.setExtendedState(Frame.MAXIMIZED_BOTH);
        }
    }
