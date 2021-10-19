import javax.swing.*;
import java.awt.*;

public class SpaceInvader extends JFrame {

    private JTextArea textArea = new JTextArea();
    GameHandler handler = new GameHandler();

    SpaceInvader() {
        setTitle("Space Invader Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//창을 닫았을때 main Thread도 닫기 위해
        setSize(800, 490);
        setLocationRelativeTo(null); //윈도우 창이 중앙에 위치하도록
        textArea.setFont(new Font("Courier New", Font.PLAIN, 15));
        handler.GameHandler(textArea); //GameHandler내에서 textArea를 접근하기 위해 레퍼런스를 인자로 받아온다.
        textArea.setEditable(false); //키보드로 textArea를 수정할 수 없도록 설정
        add(textArea); // textArea를 JFrame에 추가
        setVisible(true); //JFrame을 눈에 보이게 하기 위해서
        new Thread(new GameThread()).start(); // JFrame과 game loop이 별도의 thread에서 실행되도록 한다.
    }

    class GameThread implements Runnable {
        public void run() {

            while (!handler.isGameOver()) {
                handler.gameTiming();
                handler.inputPlayer();
                handler.drawAll();
                handler.move();
            }
        }
    }

    public static void main(String[] args) {
        new SpaceInvader();
    }
}

