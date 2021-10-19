import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

class GameHandler extends JFrame implements KeyListener {
        private final int SCREEN_WIDTH = 85;
        private final int LEFT_PADDING = 2;
        private final int SCREEN_HEIGHT = 25;
        private final int FIELD_WIDTH = 60, FIELD_HEIGHT = 25;
        private char[][] buffer;
        private int field[][];

        private JTextArea textArea;

        private boolean isGameOver = false;
        private int score = 0;

        PlayerObject player;
        //EnemyObject enemy;
        BulletObject bullet;

        private int playerPosX = FIELD_WIDTH/2;
        private int playerPosY = FIELD_HEIGHT-2;
        private int enemyPosX = FIELD_WIDTH/4;
        private int enemyPosY = 0;
        private int enemyNum = 8;
        private final int enemySpace = 3; //적과 적 사이 띄우기 정도

        private EnemyObject[] enemyArray = new EnemyObject[enemyNum];
        private ArrayList bulletList = new ArrayList();
        //private ArrayList enemyList = new ArrayList();

        private boolean keyUp = false;
        private boolean keyDown = false;
        private boolean keyLeft = false;
        private boolean keyRight = false;

        public void GameHandler(JTextArea ta) {
                textArea = ta;
                textArea.addKeyListener(this);
                field = new int[FIELD_WIDTH][FIELD_HEIGHT];
                buffer = new char[SCREEN_WIDTH][SCREEN_HEIGHT];
                initData();
        }

        private void initData() {
                inputEnemy();
                for (int x = 0; x < FIELD_WIDTH; x++)
                        for (int y = 0; y < FIELD_HEIGHT; y++)
                                field[x][y] = (x == 0 || x == FIELD_WIDTH - 1) ? 1 : 0; //왼쪽 끝과 오른쪽 끝부분을 1로 저장 (#으로 저장)
                clearBuffer();
        }
        public void inputPlayer() { player = new PlayerObject(playerPosX, playerPosY, ">-o-<"); }

        private void inputBullet() {
                bullet = new BulletObject(playerPosX + 2, playerPosY, "!");
                bulletList.add(bullet);
        }

        private void inputEnemy() {
                //[0번째]   [2번째]   [4번째]   [6번째]
                //    [1번째]   [3번째]   [5번쨰]   [7번째]
                for (int i = 0; i < enemyNum; i++) {
                        if (i%2==0) { //0,짝수
                                enemyArray[i] = new EnemyObject(enemyPosX + (i * enemySpace),
                                        enemyPosY , "[XUX]");
                        }
                        else { //홀수
                                enemyArray[i] = new EnemyObject(enemyPosX + (i * enemySpace),
                                        enemyPosY+2, "[XUX]");
                        }
                }
        }

        private void clearBuffer() {
                for (int y = 0; y < SCREEN_HEIGHT; y++) {
                        for (int x = 0; x < SCREEN_WIDTH; x++) {
                                buffer[x][y] = '.';
                        }
                }
        }

        private void drawToBuffer(int px, int py, String c) {
                for (int x = 0; x < c.length(); x++) {
                        buffer[px + x + LEFT_PADDING][py] = c.charAt(x);
                }
        }

        private void drawToBuffer(int px, int py, char c) {
                buffer[px + LEFT_PADDING][py] = c;
        }
        public void gameTiming() {
                // Game tick
                try {
                        Thread.sleep(50);
                } catch (InterruptedException ex) {
                        ex.printStackTrace();
                }
        }
        private void drawScore() {
                drawToBuffer(FIELD_WIDTH + 3, 1, "┌───────────────┐");
                drawToBuffer(FIELD_WIDTH + 3, 2, "│               │");
                drawToBuffer(FIELD_WIDTH + 3, 3, "└───────────────┘");
                drawToBuffer(FIELD_WIDTH + 5, 2, "SCORE: " + score);
        }

        private void drawPlayer() {
                drawToBuffer(player.getX(), player.getY(), player.getImage());
        }

        private void drawEnemy() {
                for (int i = 0; i < enemyNum; i++) {
                        drawToBuffer(enemyArray[i].enemyPosX, enemyArray[i].enemyPosY, enemyArray[i].getImage());
                }
        }

        private void drawBullet() {
                for (int i = 0; i < bulletList.size(); i++) {
                        bullet = (BulletObject) bulletList.get(i);
                        if (bullet.bulletPosY == 0)
                                bulletList.remove(i);
                        else {
                                bullet.changePosY();
                                drawToBuffer(bullet.getX(), bullet.bulletPosY, bullet.getImage());
                        }
                }
        }

        public void drawAll() {
                for (int x = 0; x < FIELD_WIDTH; x++) {
                        for (int y = 0; y < FIELD_HEIGHT; y++) {
                                drawToBuffer(x, y, " #".charAt(field[x][y]));
                        }
                }
                drawToBuffer(66, 24, " by MinJi");
                drawPlayer();
                drawEnemy();
                drawBullet();
                drawScore();
                render();
        }
        public boolean isGameOver() {
                return isGameOver;
        }
        private void render() {
                StringBuilder sb = new StringBuilder();
                for (int y = 0; y < SCREEN_HEIGHT; y++) {
                        for (int x = 0; x < SCREEN_WIDTH; x++) {
                                sb.append(buffer[x][y]);
                        }
                        sb.append("\n");
                }
                textArea.setText(sb.toString());
        }
        private boolean range(int posX,int posY){
                if(posX == FIELD_WIDTH - player.getImage().length() || posX == 0 || posY == FIELD_HEIGHT || posY == 0)
                        return false;
                else
                        return true;
        }
        private void moveLeftBlock() {
                playerPosX -= range( playerPosX - 1, playerPosY) ? 1 : 0;
        }
        private void moveRightBlock() {
                playerPosX += range( playerPosX + 1, playerPosY) ? 1 : 0;
        }
        private void moveDownBlock() {
                playerPosY += range( playerPosX, playerPosY+1) ? 1 : 0;
        }
        private void moveUpBlock() {
                playerPosY -= range( playerPosX , playerPosY- 1) ? 1 : 0;
        }
        public void move() {
                if (keyUp == true) {
                        moveUpBlock();
                }
                if (keyDown == true) {
                        moveDownBlock();
                }
                if (keyLeft == true) {
                        moveLeftBlock();
                }
                if (keyRight == true) {
                        moveRightBlock();
                }
        }

        @Override
        public void keyPressed(KeyEvent e) {

                switch (e.getKeyCode()) {
                        case KeyEvent.VK_RIGHT:
                                keyRight = true;
                                break;
                        case KeyEvent.VK_LEFT:
                                keyLeft = true;
                                break;
                        case KeyEvent.VK_DOWN:
                                keyDown = true;
                                break;
                        case KeyEvent.VK_UP:
                                keyUp = true;
                                break;
                        case KeyEvent.VK_SPACE:
                                inputBullet();
                                break;
                }
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                                keyUp = false;
                                break;
                        case KeyEvent.VK_DOWN:
                                keyDown = false;
                                break;
                        case KeyEvent.VK_LEFT:
                                keyLeft = false;
                                break;
                        case KeyEvent.VK_RIGHT:
                                keyRight = false;
                                break;
                }
        }
}
