import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Vector;



public class Test extends Applet implements KeyListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final int DIR_UP = 1;
    private static final int DIR_DOWN = 2;
    private static final int DIR_LEFT = 3;
    private static final int DIR_RIGHT = 4;
    private static final int NODE_SIZE = 10;
    private static final int GRID_WIDTH = 600;
    private static final int GRID_HEIGHT = 600;

    Random random = new Random();
    private boolean isGameOver;
    private boolean isObjectCollected = false;
    private boolean isBonusCollected = false;
    private boolean isBonusShown = false;
    private int x;
    private int y;
    private int xbon;
    private int ybon;
    private int count;
    private int bonus;
    private int bon;
    private int score;
    private int btime;
    private Vector<Node> snake = new Vector<Node>();
    private Node head;
    public void init() {
        setSize(GRID_WIDTH + 100, GRID_HEIGHT);
        addKeyListener(this);
        initSnake();
        Thread t;
        t = new Thread(new Runnable() {
            
            @Override
            public void run() {
                while (!isGameOver) {
                    try {
                        if(isBonusShown && btime < 8000)
                        {
                            btime+=200;
                            System.out.println(""+btime);
                            }
                        if(btime >= 8000)
                        {
                            
                            isBonusCollected = true;
                            isBonusShown=false;
                        }
                        Thread.sleep(200);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    moveSnake();
                    if (isObjectCollected) {
                        x = random.nextInt(30) * NODE_SIZE;
                        y = random.nextInt(30) * NODE_SIZE;
                        isObjectCollected = false;
                    } else {
                        Node head = snake.get(0);
                        
                        if(count%4==0 && count!=0){
                            isBonusShown=true;
                            if(isBonusCollected){
                                System.out.print(""+btime);
                                count=0;
                                xbon = random.nextInt(30) * NODE_SIZE;
                                ybon = random.nextInt(30) * NODE_SIZE;
                                isBonusCollected=false;
                                isBonusShown=false;
                                btime=0;
                                                            
                            }
                            else{

                                if(xbon == head.getXpos() && ybon == head.getYpos()){
                            
                                count=0;
                                bon++;
                                bonus+=5 * ((8000-btime)/1000);
                                isBonusCollected = true;
                                isBonusShown=false;
                                    }
                        }
                        }
                        
                        if (x == head.getXpos() && y == head.getYpos()) {
                            isObjectCollected = true;
                            count++;
                            score++;
                            btime=0;
                            // length++;
                            Node last = snake.lastElement();
                            
                            switch (last.getDir()) {
                            case DIR_DOWN:
                                x = last.getXpos();
                                y = last.getYpos() + NODE_SIZE;
                                break;
                            case DIR_LEFT:
                                x = last.getXpos() - NODE_SIZE;
                                y = last.getYpos();
                                break;
                            case DIR_RIGHT:
                                x = last.getXpos() + NODE_SIZE;
                                y = last.getYpos();
                                break;
                            default:
                                x = last.getXpos();
                                y = last.getYpos() - NODE_SIZE;
                                break;
                            }
                            Node node = new Node(x, y, last.getDir(), last);
                            snake.add(node);
                            
                            
                            
                        }
                    }
                    repaint();
                }
            }

        });
        t.start();
    }

    private void initSnake() {
        head = new Node(random.nextInt(30) * NODE_SIZE, random.nextInt(30)
                * NODE_SIZE, DIR_RIGHT, null);
        snake.add(head);
    }

    private void moveSnake() {

        for (int i = snake.size() - 1; i >= 0; i--) {
            snake.get(i).moveNode();
        }

        for (int i = 1; i < snake.size(); i++) {
            Node node = snake.get(i);
            if (node.getXpos() == head.getXpos() && node.getYpos() == head.getYpos()) {
                System.out.print("GAME OVER");
                isGameOver = true;
                break;
            }
        }

    }

    class Node {

        private int xpos;
        private int ypos;
        private int dir;
        private Node parent;

        public Node(int xpos, int ypos, int dir, Node parent) {
            this.xpos = xpos;
            this.ypos = ypos;
            this.dir = dir;
            this.parent = parent;
        }

        public void setDir(int dir) {
            this.dir = dir;
        }

        public int getDir() {
            return dir;
        }

        public void moveNode() {
            if (parent == null) {
                switch (dir) {
                case DIR_DOWN:
                    if (ypos == GRID_HEIGHT) {
                        ypos = 0;
                    } else
                        ypos += NODE_SIZE;
                    break;
                case DIR_LEFT:
                    if (xpos == 0) {
                        xpos = GRID_WIDTH - NODE_SIZE;
                    } else
                        xpos -= NODE_SIZE;
                    break;
                case DIR_RIGHT:
                    if (xpos == GRID_WIDTH) {
                        xpos = 0;
                    } else
                        xpos += NODE_SIZE;
                    break;
                default:
                    if (ypos == 0) {
                        ypos = GRID_HEIGHT - NODE_SIZE;
                    } else
                        ypos -= NODE_SIZE;
                    break;
                }
            } else {
                this.xpos = parent.xpos;
                this.ypos = parent.ypos;
                this.dir = parent.dir;
            }

        }

        public void draw(Graphics g) {
            if (parent == null) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.BLUE);
            }
            g.fillRect(xpos, ypos, NODE_SIZE, NODE_SIZE);
            
            g.setColor(Color.WHITE);
            if(isGameOver==true)
            {g.drawString("GAME OVER",300, 300);
            g.drawString("Your Score :"+(bonus+score), 310, 310);
            }
        }

        public int getXpos() {
            return xpos;
        }

        public int getYpos() {
            return ypos;
        }
        
    }

    public void keyPressed(KeyEvent e) {
        System.out.println("key code: " + e.getKeyCode());
        System.out.println("Down " + KeyEvent.VK_DOWN);
        switch (e.getKeyCode()) {
        case KeyEvent.VK_LEFT:
            if(head.getDir()==DIR_DOWN || head.getDir()==DIR_UP)
            {head.setDir(DIR_LEFT);
            }
            break;
        case KeyEvent.VK_RIGHT:
            if(head.getDir()==DIR_DOWN || head.getDir()==DIR_UP)
            {head.setDir(DIR_RIGHT);
            }
            break;
        case KeyEvent.VK_UP:
            if(head.getDir()==DIR_RIGHT || head.getDir()==DIR_LEFT)
            {head.setDir(DIR_UP);
            }
            break;
        case KeyEvent.VK_DOWN:
            if(head.getDir()==DIR_RIGHT || head.getDir()==DIR_LEFT)
            {head.setDir(DIR_DOWN);
            }
            break;
        }

    }

    public void paint(Graphics g) {
        // grid
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, GRID_WIDTH, GRID_HEIGHT);

        // snake
        for (int i = 0; i < snake.size(); i++) {
            snake.get(i).draw(g);
        }
        
        //bonus
        if(count%4==0 && count!=0)
        {
        g.setColor(Color.WHITE);
        g.drawArc(xbon, ybon, NODE_SIZE, NODE_SIZE, 0, 360);
        g.fillArc(xbon, ybon, NODE_SIZE, NODE_SIZE, 0, 360);
        }        
        // object
        g.setColor(Color.BLACK);
        g.fillArc(x, y, NODE_SIZE, NODE_SIZE, 0, 360);

        // object count
        g.drawString("Objects Coll: " + score, 600, 600);
        g.drawString("Bonus Coll: " +bon,590,590);
    }

    public void update(Graphics g) {
        super.update(g);
        paint(g);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}
