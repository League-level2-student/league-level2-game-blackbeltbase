import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class ObjectManager implements ActionListener {
	Player player;
	int score = 0;
	int currentState;
	ArrayList<Lasers> lasers = new ArrayList<Lasers>();
	boolean isInvincible = false;
	public ObjectManager(Player player, int currentState) {
		this.player = player;
		this.currentState = currentState;
	}

	public int getCurrentState() {
		return currentState;
	}

	void changeCurrentState(int currentState) {
		this.currentState = currentState;
	}

	void addLaser() {
		if(isInvincible == false) {
		Random random = new Random();
		lasers.add(new Lasers(random.nextInt(Dodger.WIDTH), Dodger.HEIGHT, 10, 20, currentState));}
	else if(isInvincible) {
		lasers.add(new Lasers(0, Dodger.HEIGHT, 10, 20, currentState));}
	}
int getProgress(int currentState) {
	if(currentState == 2) {
		return score/50;
	}
	else if(currentState ==3) {
		return score/150;
	}
	else if(currentState == 4) {
		return score/300;
	}
	else {
		return 0;
	}
}
	void draw(Graphics g) {
		player.draw(g);
		for (Lasers s : lasers) {
			s.draw(g);
		}
	}

	void purgeObjects() {
		for (int i = 0; i < lasers.size(); i++) {
			if (lasers.get(i).isActive == false) {
				score++;
				lasers.remove(i);
			}
		}

	}

	int getScore() {
		return score;
	}

	void update() {
		if (player.isActive) {
			player.update();

			for (Lasers s : lasers) {
				s.update();
				if (s.y > Dodger.HEIGHT || s.y < 0 || s.x > Dodger.WIDTH || s.x < 0) {
					s.isActive = false;
				}
			}
		}
		if (currentState == 5) {
			for (Lasers s : lasers) {
				s.isActive = false;
			}
		}
		checkCollision();
		purgeObjects();
	}

	void checkCollision() {
		for (int i = 0; i < lasers.size(); i++) {
			if (player.collisionBox.intersects(lasers.get(i).collisionBox)) {
				player.isActive = false;

			}
		}
	}

	void activateInvincibility() {
		isInvincible = true;
	}

	void deactivateInvincibility() {
		isInvincible = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (currentState == 2) {
			for (int i = 0; i < 5; i++) {
				addLaser();
			}
		} else if (currentState == 3) {
			for (int i = 0; i < 10; i++) {
				addLaser();
			}
		} else if (currentState == 4) {
			for (int i = 0; i < 25; i++) {
				addLaser();
			}
		}
		
	}
}
