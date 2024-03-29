package net.chococraft.common.entities.breeding;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;
import net.chococraft.common.ChocoConfig;
import net.chococraft.common.entities.EntityChocobo;
import net.chococraft.common.entities.properties.ChocoboAttributes;
import net.chococraft.common.entities.properties.ChocoboColor;

public class BreedingHelper {
    public static ChocoboBreedInfo getBreedInfo(EntityChocobo mother, EntityChocobo father) {
        return new ChocoboBreedInfo(new ChocoboStatSnapshot(mother), new ChocoboStatSnapshot(father));
    }

    public static EntityChocobo createChild(ChocoboBreedInfo breedInfo, World world) {
        EntityChocobo chocobo = new EntityChocobo(world);

        ChocoboStatSnapshot mother = breedInfo.getMother();
        ChocoboStatSnapshot father = breedInfo.getFather();

        chocobo.setGeneration((int) (((mother.generation + father.generation) / 2f) + 1f));

        float health = Math.round(((mother.health + father.health) / 2f) * (ChocoConfig.breeding.poslossHealth + ((float) Math.random() * ChocoConfig.breeding.posgainHealth)));
        chocobo.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Math.min(health, ChocoConfig.breeding.maxHealth));

        float speed = ((mother.speed + father.speed) / 2f) * (ChocoConfig.breeding.poslossSpeed + ((float) Math.random() * ChocoConfig.breeding.posgainSpeed));
        chocobo.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(Math.min(speed, (ChocoConfig.breeding.maxSpeed / 100f)));

        float stamina = Math.round((mother.stamina + father.stamina) / 2f) * (ChocoConfig.breeding.poslossStamina + ((float) Math.random() * ChocoConfig.breeding.posgainStamina));
        chocobo.getEntityAttribute(ChocoboAttributes.MAX_STAMINA).setBaseValue(Math.min(stamina, ChocoConfig.breeding.maxStamina));

        float canFlyChance = calculateChance(0.005f, 0.15f, 0.35f, mother.canFly, father.canFly);
        float canflychancerandom = (float) Math.random();
        chocobo.setCanFly(canFlyChance > canflychancerandom);

        float canDiveChance = calculateChance(0.01f, 0.20f, 0.40f, mother.canDive, father.canDive);
        float candivechancerandom = (float) Math.random();
        chocobo.setCanDive(canDiveChance > candivechancerandom);

        float canGlideChance = calculateChance(0.01f, 0.20f, 0.45f, mother.canGlide, father.canGlide);
        float canglidechancerandom = (float) Math.random();
        chocobo.setCanGlide(canGlideChance > canglidechancerandom);

        float canSprintChance = calculateChance(0.03f, 0.25f, 0.5f, mother.canSprint, father.canSprint);
        float cansprintchancerandom = (float) Math.random();
        chocobo.setCanSprint(canSprintChance > cansprintchancerandom);

        chocobo.setMale(.50f > (float) Math.random());

        ChocoboColor color = ChocoboColor.YELLOW;

        if (mother.color == ChocoboColor.FLAME && father.color == ChocoboColor.FLAME) {
            color = ChocoboColor.FLAME;
        } else if (canFlyChance > canflychancerandom) {
            color = ChocoboColor.GOLD;
        } else if (canDiveChance > candivechancerandom) {
            color = ChocoboColor.BLUE;
        } else if (canGlideChance > canglidechancerandom) {
            color = ChocoboColor.WHITE;
        } else if (canSprintChance > cansprintchancerandom) {
            color = ChocoboColor.GREEN;
        } else if (.25f > (float) Math.random()) {
            if (mother.color == ChocoboColor.GREEN && father.color == ChocoboColor.BLUE || mother.color == ChocoboColor.BLUE && father.color == ChocoboColor.GREEN) {
                color = ChocoboColor.BLACK;
            } else if (mother.color == ChocoboColor.RED && father.color == ChocoboColor.WHITE || mother.color == ChocoboColor.WHITE && father.color == ChocoboColor.RED) {
                color = ChocoboColor.PINK;
            } else if (mother.color == ChocoboColor.FLAME && father.color == ChocoboColor.GOLD || mother.color == ChocoboColor.GOLD && father.color == ChocoboColor.FLAME) {
                color = ChocoboColor.RED;
            } else if (mother.color == ChocoboColor.RED && father.color == ChocoboColor.BLUE || mother.color == ChocoboColor.BLUE && father.color == ChocoboColor.RED) {
                color = ChocoboColor.PURPLE;
            }
        }

        chocobo.setChocoboColor(color);

        chocobo.setGrowingAge(-24000);

        return chocobo;
    }

    private static float calculateChance(float baseChance, float perParentChance, float bothParentsChance, boolean motherHasAbility, boolean fatherHasAbility) {
        return baseChance + (motherHasAbility || fatherHasAbility ? perParentChance : 0) + (motherHasAbility && fatherHasAbility ? bothParentsChance : 0);
    }
}
