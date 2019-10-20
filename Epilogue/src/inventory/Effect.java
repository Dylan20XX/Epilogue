package inventory;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import audio.AudioPlayer;
import creatures.Player;
import graphics.Assets;
import graphics.CT;

public class Effect {

	private BufferedImage img;
	private String effect;
	
	public long lasteffectTimer = 0, effectCooldown = 1000, effectTimer = 0;
	
	public long lastimpactTimer = 0, impactCooldown = 1000, impactTimer = 0;
	
	public int temp = 0, x = 0, y = 0;
	
	public boolean oneTime;
	
	public String display = "";
	
	public int randAmt = 0;
	
	public boolean timed = true, perm = false;
	
	public Effect(String effect, int length) {

		if (effect.equals("poison")) { //done
			if(Player.getPlayerData().getName().equals("Paris") || Player.getPlayerData().getName().equals("Ray")) {
				AudioPlayer.playAudio("audio/femaleSick.wav");
			} else {
				AudioPlayer.playAudio("audio/maleSick.wav");
			}
			img = Assets.poison;
		} else if (effect.equals("food poison")) { //done
			if(Player.getPlayerData().getName().equals("Paris") || Player.getPlayerData().getName().equals("Ray")) {
				AudioPlayer.playAudio("audio/femaleSick.wav");
			} else {
				AudioPlayer.playAudio("audio/maleSick.wav");
			}
			img = Assets.foodPoison;
		} else if (effect.equals("invisibility")) { //done
			img = Assets.invisibility;
			MessageBox.addMessage("entering stealth mode...");
			display = "stealth: ";
		} else if (effect.equals("contamination")) { //done
			if(Player.getPlayerData().getName().equals("Paris") || Player.getPlayerData().getName().equals("Ray")) {
				AudioPlayer.playAudio("audio/femaleSick.wav");
			} else {
				AudioPlayer.playAudio("audio/maleSick.wav");
			}
			img = Assets.contamination;
			display = "water contamination: ";
		} else if (effect.equals("tired")) { //done
			img = Assets.tired;
			display = "tired";
			timed = false;
			perm = true;
		} else if (effect.equals("anger")) { //done
			img = Assets.anger;
			display = "anger (increases strength): ";
		} else if (effect.equals("strength")) { //done
			img = Assets.anger;
			display = "strength: ";
		} else if (effect.equals("bloodlust")) { //done
			img = Assets.bloodlust;
			display = "bloodlust (increases melee attack speed): ";
		} else if (effect.equals("heavily armed")) { //done
			img = Assets.heavyArmor;
			display = "heavily armed (increases resistance): ";
		} else if (effect.equals("full")) {
			img = Assets.full;
			display = "ate too much (reduces speed)";
			timed = false;
		} else if (effect.equals("wounded")) { //done
			img = Assets.wound;
			display = "wounded (reduces resistance): ";
		} else if (effect.equals("swiftness")) { //done
			img = Assets.swiftness;
			display = "swiftness: ";
		} else if (effect.equals("bleeding")) { //done
			img = Assets.bleeding;
			display = "bleeding: ";
		} else if (effect.equals("high")) {
			img = Assets.high;
			int randMessage = CT.random(1, 2);
			if(randMessage == 1)
				MessageBox.addMessage("you're starting to see flowers...");
			else if(randMessage == 2)
				MessageBox.addMessage("you're now high...");
			display = "high: ";
		} else if (effect.equals("burning")) {
			img = Assets.burning;
			display = "burning: ";
		} else if (effect.equals("exausted")) { //done 
			img = Assets.exaust;
			display = "exausted";
			timed = false;
			perm = true;
		} else if (effect.equals("fever")) {
			img = Assets.feaver;
			display = "feaver";
			timed = false;
		} else if (effect.equals("odour")) {
			img = Assets.odorless;
			display = "odor (attracts creatures...):";
		} else if (effect.equals("bad odour")) {
			img = Assets.odor;
			display = "odor (smells disgusting...):";
		} else if (effect.equals("warmth")) {
			img = Assets.warmth;
			display = "warmth";
		} 

		this.effect = effect;
		effectCooldown = length;
		effectTimer = 0;

	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, 50, 50);
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

}
