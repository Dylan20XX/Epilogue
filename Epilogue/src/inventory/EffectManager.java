package inventory;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import alphaPackage.ControlCenter;
import creatures.Player;
import graphics.Assets;
import graphics.CT;
import graphics.CustomTextWritter;

public class EffectManager {

	public static ArrayList<Effect> effects = new ArrayList<Effect>();
	
	private ControlCenter c;
	private MessageBox messageBox;

	public EffectManager(ControlCenter c) {
		this.c = c;
		messageBox = c.getGameState().getWorldGenerator().getMessageBox();
	}

	public static void addEffect(Effect effect) {

		for(int i = 0; i < effects.size(); i++) {
			if(effects.get(i).getEffect().equals(effect.getEffect())) {
				effects.get(i).effectTimer = 0;
				if(effects.get(i).effectCooldown < effect.effectCooldown)
					effects.get(i).effectCooldown = effect.effectCooldown;
				return;
			}
		}
		effects.add(effect);

	}

	public void tick() {
		//System.out.println(Player.getPlayerData().getCurrentSpeed());
		if (!effects.isEmpty()) {
			for (int i = 0; i < effects.size(); i++) {
				
				if(effects.get(i).getEffect().equals("tired") && Player.getPlayerData().energy > Player.getPlayerData().ogEndurance/3) {
					effects.remove(effects.get(i));
					break;
				} else if(effects.get(i).getEffect().equals("exaust") && Player.getPlayerData().energy < Player.getPlayerData().ogEndurance/5 
						&& Player.getPlayerData().thirst <= Player.getPlayerData().ogEndurance/5 && Player.getPlayerData().hunger <= Player.getPlayerData().ogEndurance/5) {
					effects.remove(effects.get(i));
					break;
				}
				
				if (effects.get(i).getEffect().equals("poison")) {
					
					effects.get(i).impactTimer += System.currentTimeMillis() - effects.get(i).lastimpactTimer;
					effects.get(i).lastimpactTimer = System.currentTimeMillis();

					if (effects.get(i).impactTimer < effects.get(i).impactCooldown) 
						continue;
					
					Player.getPlayerData().setHealth(Player.getPlayerData().getHealth()-10);

					if(Player.getPlayerData().getHealth() < 0) {
						Player.getPlayerData().setHealth(0);
						Player.getPlayerData().Die();
					}
					
					effects.get(i).impactTimer = 0;
					
				} else if (effects.get(i).getEffect().equals("food poison")) {
					
					effects.get(i).impactTimer += System.currentTimeMillis() - effects.get(i).lastimpactTimer;
					effects.get(i).lastimpactTimer = System.currentTimeMillis();

					if (effects.get(i).impactTimer < effects.get(i).impactCooldown) 
						continue;
					
					Player.getPlayerData().hunger-=1;
					
					if(Player.getPlayerData().hunger < 0)
						Player.getPlayerData().hunger = 0;
					
					effects.get(i).impactTimer = 0;
					
				} else if (effects.get(i).getEffect().equals("stealth")) {
					
					Player.getPlayerData().stealth = true;
					
					effects.get(i).impactTimer += System.currentTimeMillis() - effects.get(i).lastimpactTimer;
					effects.get(i).lastimpactTimer = System.currentTimeMillis();

					if (effects.get(i).impactTimer < effects.get(i).impactCooldown) 
						continue;
					
					effects.get(i).impactTimer = 0;
					
				} else if (effects.get(i).getEffect().equals("contamination")) {
					
					effects.get(i).impactTimer += System.currentTimeMillis() - effects.get(i).lastimpactTimer;
					effects.get(i).lastimpactTimer = System.currentTimeMillis();

					if (effects.get(i).impactTimer < effects.get(i).impactCooldown) 
						continue;
					
					Player.getPlayerData().thirst-=1;
					
					if(Player.getPlayerData().thirst < 0)
						Player.getPlayerData().thirst = 0;
					
					effects.get(i).impactTimer = 0;
					
				} else if (effects.get(i).getEffect().equals("anger")) {
					
					effects.get(i).impactTimer += System.currentTimeMillis() - effects.get(i).lastimpactTimer;
					effects.get(i).lastimpactTimer = System.currentTimeMillis();

					if (effects.get(i).impactTimer < effects.get(i).impactCooldown) 
						continue;
					
					if(!effects.get(i).oneTime) {
						effects.get(i).oneTime = true;
						Player.getPlayerData().ogDamage += 30;
					}
					
					effects.get(i).impactTimer = 0;
					
				} else if (effects.get(i).getEffect().equals("strength")) {
					
					effects.get(i).impactTimer += System.currentTimeMillis() - effects.get(i).lastimpactTimer;
					effects.get(i).lastimpactTimer = System.currentTimeMillis();

					if (effects.get(i).impactTimer < effects.get(i).impactCooldown) 
						continue;
					
					if(!effects.get(i).oneTime) {
						effects.get(i).oneTime = true;
						Player.getPlayerData().ogDamage += 30;
					}
					
					effects.get(i).impactTimer = 0;
					
				} else if (effects.get(i).getEffect().equals("bleeding")) {
					
					effects.get(i).impactTimer += System.currentTimeMillis() - effects.get(i).lastimpactTimer;
					effects.get(i).lastimpactTimer = System.currentTimeMillis();

					if (effects.get(i).impactTimer < effects.get(i).impactCooldown) 
						continue;
					
					Player.getPlayerData().setHealth(Player.getPlayerData().getHealth()-15);

					if(Player.getPlayerData().getHealth() < 0) {
						Player.getPlayerData().setHealth(0);
						Player.getPlayerData().Die();
					}
					
					effects.get(i).impactTimer = 0;
					
				} else if (effects.get(i).getEffect().equals("burning")) {
					
					effects.get(i).impactTimer += System.currentTimeMillis() - effects.get(i).lastimpactTimer;
					effects.get(i).lastimpactTimer = System.currentTimeMillis();

					if (effects.get(i).impactTimer < effects.get(i).impactCooldown) 
						continue;
					
					Player.getPlayerData().setHealth(Player.getPlayerData().getHealth()-Player.getPlayerData().getResistance()/8);

					if(Player.getPlayerData().getHealth() < 0) {
						Player.getPlayerData().setHealth(0);
						Player.getPlayerData().Die();
					}
					
					effects.get(i).impactTimer = 0;
					
				} else if (effects.get(i).getEffect().equals("swiftness")) {
					
					effects.get(i).impactTimer += System.currentTimeMillis() - effects.get(i).lastimpactTimer;
					effects.get(i).lastimpactTimer = System.currentTimeMillis();

					if (effects.get(i).impactTimer < effects.get(i).impactCooldown) 
						continue;
					
					if(!effects.get(i).oneTime) {
						effects.get(i).oneTime = true;
						Player.getPlayerData().extraSpeed += 0.5;
					}
					
					effects.get(i).impactTimer = 0;
					
				} else if (effects.get(i).getEffect().equals("wounded")) {
					
					effects.get(i).impactTimer += System.currentTimeMillis() - effects.get(i).lastimpactTimer;
					effects.get(i).lastimpactTimer = System.currentTimeMillis();

					if (effects.get(i).impactTimer < effects.get(i).impactCooldown) 
						continue;
					
					if(!effects.get(i).oneTime) {
						effects.get(i).oneTime = true;
						effects.get(i).randAmt = CT.random(30, 50);
						if(effects.get(i).randAmt > Player.getPlayerData().getResistance())
							effects.get(i).randAmt = Player.getPlayerData().getResistance();
						Player.getPlayerData().setResistance(Player.getPlayerData().getResistance() - effects.get(i).randAmt);
					}
					
					effects.get(i).impactTimer = 0;
					
				} else if (effects.get(i).getEffect().equals("bloodlust")) {
					
					effects.get(i).impactTimer += System.currentTimeMillis() - effects.get(i).lastimpactTimer;
					effects.get(i).lastimpactTimer = System.currentTimeMillis();

					if (effects.get(i).impactTimer < effects.get(i).impactCooldown) 
						continue;
					
					if(!effects.get(i).oneTime) {
						effects.get(i).oneTime = true;
						effects.get(i).randAmt = CT.random(50, 150);
						Player.getPlayerData().bonusAttackSpeed += effects.get(i).randAmt;
					}
					
					effects.get(i).impactTimer = 0;
					
				} else if (effects.get(i).getEffect().equals("heavily armed")) {
					
					effects.get(i).impactTimer += System.currentTimeMillis() - effects.get(i).lastimpactTimer;
					effects.get(i).lastimpactTimer = System.currentTimeMillis();

					if (effects.get(i).impactTimer < effects.get(i).impactCooldown) 
						continue;
					
					if(!effects.get(i).oneTime) {
						effects.get(i).oneTime = true;
						Player.getPlayerData().setResistance(Player.getPlayerData().getResistance() + 30);
					}
					
					effects.get(i).impactTimer = 0;
					
				} else if (effects.get(i).getEffect().equals("invisibility")) {
					
					if(!effects.get(i).oneTime) {
						effects.get(i).oneTime = true;
						Player.getPlayerData().stealth = true;
					}
					
					if(!Player.getPlayerData().stealth) {
						effects.remove(effects.get(i));
						break;
					}
					
					effects.get(i).impactTimer += System.currentTimeMillis() - effects.get(i).lastimpactTimer;
					effects.get(i).lastimpactTimer = System.currentTimeMillis();

					if (effects.get(i).impactTimer < effects.get(i).impactCooldown) 
						continue;
					
					effects.get(i).impactTimer = 0;
					
				}
				
				if(!effects.get(i).perm) {
					effects.get(i).effectTimer += System.currentTimeMillis() - effects.get(i).lasteffectTimer;
					effects.get(i).lasteffectTimer = System.currentTimeMillis();
				}
				
				if (effects.get(i).effectTimer < effects.get(i).effectCooldown) 
					continue;
				
				effects.get(i).effectTimer = 0;
				
				if(effects.get(i).temp == 1) {
					if(effects.get(i).getEffect().equals("anger"))
						Player.getPlayerData().ogDamage -= 30;
					else if(effects.get(i).getEffect().equals("swiftness"))
						Player.getPlayerData().extraSpeed -= 0.5;
					else if(effects.get(i).getEffect().equals("wounded"))
						Player.getPlayerData().setSpeed(Player.getPlayerData().getResistance() + effects.get(i).randAmt);
					else if(effects.get(i).getEffect().equals("bloodlust"))
						Player.getPlayerData().bonusAttackSpeed -= effects.get(i).randAmt;
					else if(effects.get(i).getEffect().equals("heavily armed"))
						Player.getPlayerData().setResistance(Player.getPlayerData().getResistance() - effects.get(i).randAmt);
					else if(effects.get(i).getEffect().equals("invisibility")) {
						Player.getPlayerData().stealth = false;
						messageBox.addMessage("exiting stealth mode...");
					}
					if(!effects.get(i).perm)
						effects.remove(effects.get(i));
					i--;
				} else
					effects.get(i).temp++;
				
			}
		}

	}

	public void render(Graphics g) {

		for (int i = 0; i < effects.size(); i++) {

			if (i < 5) {
				effects.get(i).x = i * 55 + 40;
				effects.get(i).y = 40;
				g.drawImage(effects.get(i).getImg(), i * 55 + 40, 40, 50, 50, null);
			}
			else if (i < 10) {
				effects.get(i).x = (i - 5) * 55 + 40;
				effects.get(i).y = 95;
				g.drawImage(effects.get(i).getImg(), (i - 5) * 55 + 40, 95, 50, 50, null);
			}
			else if (i < 15) {
				effects.get(i).x = (i - 10) * 55 + 40;
				effects.get(i).y = 150;
				g.drawImage(effects.get(i).getImg(), (i - 10) * 55 + 40, 150, 50, 50, null);
			}

			if(c.getMouseManager().mouseBound().intersects(effects.get(i).getBounds())) {
				if(effects.get(i).timed)
					CustomTextWritter.drawString(g, effects.get(i).display + Math.round(effects.get(i).effectCooldown/1000 - effects.get(i).effectTimer/1000) + "s", 
							40, 30, false, Color.yellow, Assets.font21);
				else
					CustomTextWritter.drawString(g, effects.get(i).display, 
							40, 30, false, Color.yellow, Assets.font21);
			}
			
		}
		
	}

}
