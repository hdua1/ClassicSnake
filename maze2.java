import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Vector;

public class app extends Applet implements KeyListener {

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
    private static final int OBSTRUCTION = 1;
    private static final int EMPTY_AREA = 0;

    Random random = new Random();
    private boolean isGameOver;
    private boolean isObjectCollected = false;
    private boolean isBonusCollected = false;
    private boolean isBonusShown = false;
    private int x;
    private int y;
    private int xpoint;
    private int ypoint;
    private int xbon;
    private int ybon;
    private int count;
    private int bonus;
    private int bon;
    private int score;
    private int btime;
    private Vector<Node> snake = new Vector<Node>();
    private Node head;
    private int[][] grid;

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
                        Point p = position();
                        xpoint = p.x;
                        ypoint = p.y;
                        isObjectCollected=false;
                        }

                    else {
                        Node head = snake.get(0);
                        
                        if(count%2==0 && count!=0){
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
                        
                        if (xpoint == head.getXpos() && ypoint == head.getYpos()) 
                        {
                            isObjectCollected = true;
                            count++;
                            score++;
                            btime=0;
                            Node last = snake.lastElement();
                            switch (last.getDir()) {
                            case DIR_DOWN:
                                xpoint = last.getXpos();
                                ypoint = last.getYpos() + NODE_SIZE;
                                break;
                            case DIR_LEFT:
                                xpoint = last.getXpos() - NODE_SIZE;
                                ypoint = last.getYpos();
                                break;
                            case DIR_RIGHT:
                                xpoint = last.getXpos() + NODE_SIZE;
                                ypoint = last.getYpos();
                                break;
                            default:
                                xpoint = last.getXpos();
                                ypoint = last.getYpos() - NODE_SIZE;
                                break;
                            }
                            Node node = new Node(xpoint, ypoint, last.getDir(), last);
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
        grid = new int[GRID_WIDTH / NODE_SIZE][GRID_HEIGHT / NODE_SIZE];

        // vertical right up1
        for (int i = 5; i < 20; i++) {
            grid[i][55] = OBSTRUCTION;
        }

        // horizontal right up1
        for (int i = 40; i < 55; i++) {
            grid[5][i] = OBSTRUCTION;
        }
        
        // vertical right up2
        for (int i = 10; i < 20; i++) {
            grid[i][50] = OBSTRUCTION;
        }

        // horizontal right up2
        for (int i = 40; i < 50; i++) {
            grid[10][i] = OBSTRUCTION;
        }

        // vertical right up3
                for (int i = 15; i < 20; i++) {
                    grid[i][45] = OBSTRUCTION;
                }

        // horizontal right up3
                for (int i = 40; i < 45; i++) {
                    grid[15][i] = OBSTRUCTION;
                }

        // vertical right down1
                for (int i = 40; i < 55; i++) {
                    grid[i][55] = OBSTRUCTION;
                }

        // horizontal right down1
                for (int i = 40; i < 56; i++) {
                    grid[55][i] = OBSTRUCTION;
                }

        // vertical right down2
                for (int i = 40; i < 50; i++) {
                    grid[i][50] = OBSTRUCTION;
                }

        // horizontal right down2
                for (int i = 40; i < 51; i++) {
                    grid[50][i] = OBSTRUCTION;
                }
                
        // vertical right down3
                for (int i = 40; i < 45; i++) {
                    grid[i][45] = OBSTRUCTION;
                }

        // horizontal right down3
                for (int i = 40; i < 46; i++) {
                    grid[45][i] = OBSTRUCTION;
                }
        
        // vertical left down1
                for (int i = 40; i < 55; i++) {
                    grid[i][5] = OBSTRUCTION;
                }

        // horizontal left down1
                for (int i = 5; i < 21; i++) {
                    grid[55][i] = OBSTRUCTION;
                }

        // vertical left down2
                for (int i = 40; i < 50; i++) {
                    grid[i][10] = OBSTRUCTION;
                }

        // horizontal left down2
                for (int i = 10; i < 21; i++) {
                    grid[50][i] = OBSTRUCTION;
                }
                
        // vertical left down3
                for (int i = 40; i < 45; i++) {
                    grid[i][15] = OBSTRUCTION;
                }

        // horizontal left down3
                for (int i = 15; i < 21; i++) {
                    grid[45][i] = OBSTRUCTION;
                }

        // vertical right up1
                for (int i = 5; i < 20; i++) {
                    grid[i][5] = OBSTRUCTION;
                }

        // horizontal right up1
                for (int i = 5; i < 21; i++) {
                    grid[5][i] = OBSTRUCTION;
                }
                
        // vertical right up2
                for (int i = 10; i < 20; i++) {
                    grid[i][10] = OBSTRUCTION;
                }

        // horizontal right up2
                for (int i = 10; i < 21; i++) {
                    grid[10][i] = OBSTRUCTION;
                }

        // vertical right up3
                        for (int i = 15; i < 20; i++) {
                            grid[i][15] = OBSTRUCTION;
                        }

                // horizontal right up3
                        for (int i = 15; i < 21; i++) {
                            grid[15][i] = OBSTRUCTION;
                        }

        snake.add(head);
    }

    private Point position() {
        int xcor = random.nextInt(30) * NODE_SIZE;
        int ycor = random.nextInt(30) * NODE_SIZE;
        if (xcor == head.getXpos() && ycor == head.getYpos() || grid[head.getYpos() / NODE_SIZE][head.getXpos() / NODE_SIZE] == OBSTRUCTION) {

            return position();
        }
        return new Point(xcor, ycor);
    }

    private void moveSnake() {

        for (int i = snake.size() - 1; i >= 0; i--) {
            snake.get(i).moveNode();
        }

        for (int i = 1; i < snake.size(); i++) {
            Node node = snake.get(i);
            if (node.getXpos() == head.getXpos()
                    && node.getYpos() == head.getYpos()) {
                System.out.print("GAME OVER");
                isGameOver = true;
                break;
            }
        }

        if (grid[head.getYpos() / NODE_SIZE][head.getXpos() / NODE_SIZE] == OBSTRUCTION) {
            isGameOver = true;
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
                    if (ypos == GRID_HEIGHT-10) {
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
                    if (xpos == GRID_WIDTH-10) {
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
            if (isGameOver == true) {
                g.drawString("GAME OVER", 300, 300);
                g.drawString("Score :" + (score+bonus), 310, 310);
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
            if (head.getDir() == DIR_DOWN || head.getDir() == DIR_UP) {
                head.setDir(DIR_LEFT);
            }
            break;
        case KeyEvent.VK_RIGHT:
            if (head.getDir() == DIR_DOWN || head.getDir() == DIR_UP) {
                head.setDir(DIR_RIGHT);
            }
            break;
        case KeyEvent.VK_UP:
            if (head.getDir() == DIR_RIGHT || head.getDir() == DIR_LEFT) {
                head.setDir(DIR_UP);
            }
            break;
        case KeyEvent.VK_DOWN:
            if (head.getDir() == DIR_RIGHT || head.getDir() == DIR_LEFT) {
                head.setDir(DIR_DOWN);
            }
            break;
        }

    }

    public void paint(Graphics g) {
        // grid
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, GRID_WIDTH, GRID_HEIGHT);

/*        // boundary
        g.setColor(Color.black);
        g.drawLine(0, 0, GRID_WIDTH, 0);
        g.drawLine(GRID_WIDTH, 0, GRID_WIDTH, GRID_HEIGHT);
        g.drawLine(GRID_WIDTH, GRID_HEIGHT, 0, GRID_HEIGHT);
        g.drawLine(0, GRID_HEIGHT, 0, 0);
*/
        //bonus
        if(count%2==0 && count!=0)
        {
        g.setColor(Color.WHITE);
        g.drawArc(xbon, ybon, NODE_SIZE, NODE_SIZE, 0, 360);
        g.fillArc(xbon, ybon, NODE_SIZE, NODE_SIZE, 0, 360);
        }
        
        // obstacle
        g.setColor(Color.black);
        int i = 0;
        while (i < grid.length) {
            int j = 0;
            while (j < grid[i].length) {
                if (grid[i][j] == OBSTRUCTION) {
                    g.fillRect(j * NODE_SIZE, i * NODE_SIZE, NODE_SIZE,
                            NODE_SIZE);
                }
                j++;
            }
            i++;
        }

        // snake
        for (i = 0; i < snake.size(); i++) {
            snake.get(i).draw(g);
        }

        // object
        g.setColor(Color.GRAY);
        g.fillArc(xpoint, ypoint, NODE_SIZE, NODE_SIZE, 0, 360);


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
