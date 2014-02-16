RealmOfTheAncients
=========

A multiplayer battle arena based on [SlickGame](https://github.com/anubiann00b/SlickGame). I'm working on this as a side project.

=========
#### Game Information

*This is a preliminary idea of how things will work; things may change.*

##### Basic Gameplay
The game is multiplayer and persistant. Anyone can join at any time and play. The game is something like SlickGame, which is something like Realm of the Mad God with less projectiles and more melee attacks. Holding tab brings up the scoreboard. Score is measured in both literal KD, various statistics, and a score value which is calculated based on the skill level of people killed. The environment will have various hazards, such as trees and boulders, as well as caltrops and fires. Health does not regenerate, but there are various places where health potions spawn. Health potions take a small amount of time to consume, and can be cancelled by taking damage.

##### Class Overview
There will be three different classes: A fighter with a slash attack and a spin ability, a rogue with a small stab attack and a blink ability, and a archer with a short bow attack and a longer projectile ability. Archer counters fighter because he has more range and can avoid the slash attacks, fighter counters rogue because he can spin attack when the rogue tries to stab, and rogue counters archer because he can dodge arrows and go in for the kill.

##### Class Details
Fighter's attack hits 3 adjacent squares, similar to the current Slick Game character. His special hits all adjacent squares. Rogue has an attack that hits one square in one of the four directions. Due to the hard to hit nature of his attack, he gets extra damage on a succesful backstab. His special teleports him 2 squares in one of the four directions. Archer's attack hits forward 2 squares in one of the four directions. His special travels 5 sqaures, but slowly, and does a decent amount of damage.

##### Environmental Hazards
In addition to things like boulders and spikes and potions, there are also neutral monsters. They give a small amount of experience, and are usually not worth the risk, but good players can use them to their advantage to chain hits and maximise damage in fights.

##### Advanced Gameplay
Every once in a while an Ancient will spawn, which is a boss monster that is really hard to solo. Upon death, all nearby player are granted a score bonus, and the killer is granted a buff that increases damage until death to all nearby players. Players cannot respawn while the Ancient is alive. During the ancient phase, kills do not grant bonuses. Therefore players need to cooperate a little to kill it, but stabbing other players is still important to deny them the buff. If all players die during the Ancient phase, the Ancient leaves and all players respawn.

##### Ancients
Ancients have four phases, one to counter each character, and another more powerful phase. The anti-fighter phase gives it a spear that hits multiple sqaures in a line. The anti-rogue phase gives it a burst attack that hits all sqaures around it. The anti-archer phase gives it a powerful blink ability, and a small weapon. The final phase gives it a large amount of projectiles of different kinds, as well as a powerful flail that hits a large area.
There are also plans for different types of Ancients; each will probably house one of the properties above in greater strength.
