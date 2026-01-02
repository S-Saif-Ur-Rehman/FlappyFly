import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 1200;
    int boardHeight = 640;
    private static final String HIGH_SCORE_FILE = "highscore.txt";

    //images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    //bird class
    int birdX = boardWidth / 12;
    int birdY = boardHeight / 5;
    int birdWidth = 50;
    int birdHeight = 35;

    class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
    }

    //pipe class
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        Pipe(Image img) {
            this.img = img;
        }
    }

    //game logic
    Bird bird;
    int velocityX = -6;
    double velocityY = 0;          // changed from int to double
    double gravity = 0.2;          // reduced gravity
    ArrayList<Pipe> pipes;
    Random random = new Random();

    Timer gameLoop;
    Timer placePipeTimer;
    boolean gameOver = false;
    boolean gameStarted = false;  // game does NOT start immediately, waits for space tap
    double score = 0;
    double highScore = 0;

    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        bird = new Bird(birdImg);
        pipes = new ArrayList<Pipe>();

        placePipeTimer = new Timer(1250, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameStarted && !gameOver) {
                    placePipes();
                }
            }
        });
        placePipeTimer.start();

        gameLoop = new Timer(1000 / 170, this);
        gameLoop.start();

        loadHighScore();
    }

    private void loadHighScore() {
        try {
            File file = new File(HIGH_SCORE_FILE);
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                if (scanner.hasNextDouble()) {
                    highScore = scanner.nextDouble();
                }
                scanner.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Could not load high score: " + e.getMessage());
        }
    }

    private void saveHighScore() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(HIGH_SCORE_FILE));
            writer.println(highScore);
            writer.close();
        } catch (IOException e) {
            System.err.println("Could not save high score: " + e.getMessage());
        }
    }

    void placePipes() {
        int randomPipeY = (int) (pipeY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = boardHeight / 4;

        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, this.boardWidth, this.boardHeight, null);
        g.drawImage(birdImg, bird.x, bird.y, bird.width, bird.height, null);

        for (Pipe pipe : pipes) {
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font .PLAIN, 32));
        g.drawString("Score: " + (int) score, 10, 35);
        g.drawString("High Score: " + (int) highScore, 10, 70);

        if (!gameStarted && !gameOver) {
            // Show "Tap SPACE to Start!" before first start
            String startMsg = "Tap SPACE to Start!";
            Font fontStart = new Font("Verdana", Font.BOLD, 48);
            g.setFont(fontStart);

            // Shadow
            g.setColor(Color.black);
            g.drawString(startMsg, boardWidth / 2 - g.getFontMetrics(fontStart).stringWidth(startMsg) / 2 + 3,
                    boardHeight / 2 + 120 + 3);

            // Main text
            g.setColor(Color.orange);
            g.drawString(startMsg, boardWidth / 2 - g.getFontMetrics(fontStart).stringWidth(startMsg) / 2,
                    boardHeight / 2 + 120);
        }

        if (gameOver) {
            // Game Over message
            String gameOverMsg = "GAME OVER!";
            Font fontGO = new Font("Impact", Font.BOLD, 60);
            g.setFont(fontGO);

            // Shadow
            g.setColor(Color.red.darker());
            g.drawString(gameOverMsg, boardWidth / 2 - g.getFontMetrics(fontGO).stringWidth(gameOverMsg) / 2 + 4,
                    boardHeight / 2 + 50 + 4);

            // Main text
            g.setColor(Color.red);
            g.drawString(gameOverMsg, boardWidth / 2 - g.getFontMetrics(fontGO).stringWidth(gameOverMsg) / 2,
                    boardHeight / 2 + 50);

            // Tap to Start message below Game Over
            String startMsg = "Tap SPACE to Start!";
            Font fontStart = new Font("Verdana", Font.BOLD, 48);
            g.setFont(fontStart);

            // Shadow for tap message
            g.setColor(Color.black);
            g.drawString(startMsg, boardWidth / 2 - g.getFontMetrics(fontStart).stringWidth(startMsg) / 2 + 3,
                    boardHeight / 2 + 120 + 3);

            // Main tap text
            g.setColor(Color.orange);
            g.drawString(startMsg, boardWidth / 2 - g.getFontMetrics(fontStart).stringWidth(startMsg) / 2,
                    boardHeight / 2 + 120);
        }
    }

    public void move() {
        if (!gameStarted || gameOver)
            return; // Don't move if game not started or over

        velocityY += gravity;
        bird.y += (int) velocityY;
        bird.y = Math.max(bird.y, 0);

        for (Pipe pipe : pipes) {
            pipe.x += velocityX;

            if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                score += 0.5;
                pipe.passed = true;
            }

            if (collision(bird, pipe)) {
                gameOver = true;
            }
        }

        if (bird.y > boardHeight) {
            gameOver = true;
        }
    }

    boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width &&
               a.x + a.width > b.x &&
               a.y < b.y + b.height &&
               a.y + a.height > b.y;
               
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            if (score > highScore) {
                highScore = score;
                saveHighScore();
            }
            placePipeTimer.stop();
            gameLoop.stop();
            gameStarted = false; // pause game, show tap to start again
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (gameOver) {
                // Reset game and start again
                bird.y = birdY;
                velocityY = 0;
                pipes.clear();
                gameOver = false;
                score = 0;
                gameStarted = true;
                gameLoop.start();
                placePipeTimer.start();
            } else if (!gameStarted) {
                // Start game first time
                gameStarted = true;
                velocityY = -6; // initial jump on start
                gameLoop.start();
                placePipeTimer.start();
            } else {
                // Normal flap/jump during gameplay
                velocityY = -6;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
